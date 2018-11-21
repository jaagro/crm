
package com.jaagro.crm.biz.schedule;

import com.alibaba.druid.util.StringUtils;
import com.jaagro.crm.api.constant.ExpiryDateType;
import com.jaagro.crm.api.dto.request.message.CreateMessageDto;
import com.jaagro.crm.api.dto.response.truck.DriverReturnDto;
import com.jaagro.crm.api.dto.response.truck.GetTruckDto;
import com.jaagro.crm.biz.jpush.JpushClientUtil;
import com.jaagro.crm.biz.mapper.TruckMapperExt;
import com.jaagro.crm.biz.service.DriverClientService;
import com.jaagro.crm.biz.service.MessageClientService;
import com.jaagro.crm.biz.service.SmsClientService;
import com.jaagro.utils.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: @Gao.
 * app中加入年检 保险 驾驶证 过期提醒
 */
@Service
@Slf4j
public class CertificateOverdueNoticeService {

    @Autowired
    private TruckMapperExt truckMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private DriverClientService driverClientService;
    @Autowired
    private SmsClientService smsClientService;
    @Autowired
    private MessageClientService messageClientService;

    @Scheduled(fixedRate = 2000)
    //每天上午10:15触发一次
    //@Scheduled(cron = "0 15 10 * * ?")
    public void certificateOverdueNoticeBySystem() {
        //提前一个月查询保险过期时间
        certificateOverdueNotice(ExpiryDateType.EXPIRY_DATE, "expiryDate");
        //查询保险已经过期
        certificateOverdueNotice(ExpiryDateType.OVER_DATE, "overDate");
        //提前一个月查询年检过期时间
        certificateOverdueNotice(ExpiryDateType.EXPIRY_ANNUAL, "expiryAnnual");
        //查询年检已经过期
        certificateOverdueNotice(ExpiryDateType.OVER_ANNUAL, "overAnnual");
        //提前一个月查询驾驶证过期时间
        driverCertificateOverdueNotice(ExpiryDateType.EXPIRY_DRIVING_LICENSE, "expiryDrivingLicense");
        //查询驾驶证过期
        driverCertificateOverdueNotice(ExpiryDateType.OVER_DRIVING_LICENSE, "overDrivingLicense");
    }

    /**
     * 年检 保险 过期 提醒
     *
     * @param expiryDateType
     * @param redisKey
     */
    private void certificateOverdueNotice(Integer expiryDateType, String redisKey) {
        List<GetTruckDto> expiryDateTruck = truckMapper.listCertificateOverdueNotice(expiryDateType);
        for (GetTruckDto expiryDate : expiryDateTruck) {
            String expiryDateTruckId = redisTemplate.opsForValue().get(redisKey + expiryDate.getId());
            if (StringUtils.isEmpty(expiryDateTruckId)) {
                List<DriverReturnDto> drivers = driverClientService.listByTruckId(expiryDate.getId());
                sendMessage(drivers, expiryDate, expiryDate.getExpiryDate(), expiryDate.getId(), expiryDateType);
            } else {
                setRefIdToRedis(redisKey + expiryDate.getId(), expiryDate.getId().toString());
            }
        }

    }

    /**
     * 驾驶证 过期 提醒
     *
     * @param driverExpiryDateType
     * @param redisKey
     */
    private void driverCertificateOverdueNotice(Integer driverExpiryDateType, String redisKey) {
        List<DriverReturnDto> driverReturnDtos = (List<DriverReturnDto>) driverClientService.listDriverCertificateOverdueNotice(driverExpiryDateType).getData();
        List<DriverReturnDto> drivers = new ArrayList<>();
        for (DriverReturnDto driverReturnDto : driverReturnDtos) {
            String driverId = redisTemplate.opsForValue().get(redisKey + driverReturnDto.getId());
            if (StringUtils.isEmpty(driverId)) {
                drivers.add(driverReturnDto);
            } else {
                setRefIdToRedis(redisKey + driverReturnDto.getId(), driverReturnDto.getId().toString());
            }
        }
        sendMessage(drivers, null, null, null, driverExpiryDateType);
    }

    /**
     * 将truckId 存入到redis中
     *
     * @param key
     * @param value
     */
    private void setRefIdToRedis(String key, String value) {
        redisTemplate.opsForValue().set(key, value, 31, TimeUnit.DAYS);
    }

    /**
     * 将时间字符串转化为Date
     *
     * @param expiryDrivingLicense
     * @return
     */
    private static Date stringToDate(String expiryDrivingLicense) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(expiryDrivingLicense);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("转化异常", e);
        }
        return date;
    }

    /**
     * 将date 转化为指定格式
     *
     * @param date
     * @return
     */
    private static String formatDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(date);
    }

    /**
     * 发送jpush 短信 消息
     *
     * @param
     * @param expiryDate
     */
    private void sendMessage(List<DriverReturnDto> drivers, GetTruckDto getTruckDto, Date expiryDate, Integer truckId, Integer msgType) {
        String alias = "";
        String msgTitle = "证件过期提醒消息";
        String msgContent;
        String regId;
        //xxx师傅，您的车号苏WNC133保险到期时间为2018年12月31日，请及时处理，以免影响您的正常使用！
        //xxx师傅，您的车号苏WNC133年检到期时间为2018年12月31日，请及时处理，以免影响您的正常使用！
        //xxx师傅，您的驾驶证到期时间为2018年12月31日，请及时处理，以免影响您的正常使用！
        //===================================================================
        //xxx师傅，您的车号苏WNC133保险到期时间已超期，请及时处理，以免影响您的正常使用！
        //xxx师傅，您的车号苏WNC133年检到期时间已超期，请及时处理，以免影响您的正常使用！
        //xxx师傅，您的驾驶证到期时间已超期，请及时处理，以免影响您的正常使用！
        for (DriverReturnDto driver : drivers) {
            //调用jpush接口给司机发送短息
            Map<String, String> extraParam = new HashMap<>();
            extraParam.put("driverId", driver.getId().toString());
            extraParam.put("expiryDate", expiryDate.toString());
            regId = driver.getRegistrationId();
            //在app消息插入一条司机记录
            CreateMessageDto createMessageDto = new CreateMessageDto();
            createMessageDto
                    .setHeader(msgTitle)
                    .setReferId(truckId)
                    .setMsgType(5)
                    .setMsgSource(1)
                    .setToUserType(2)
                    .setFromUserId(0)
                    .setToUserId(driver.getId());
            //发送短信给truckId 对应的司机
            Map<String, Object> templateMap = new HashMap<>();
            templateMap.put("driverName", driver.getName());
            //保险到期提醒
            if (ExpiryDateType.EXPIRY_DATE.equals(msgType)) {
                msgContent = driver.getName() + "师傅，您的车号" + getTruckDto.getTruckNumber() + "保险到期时间为" + formatDateToString(expiryDate) + "，请及时处理，以免影响您的正常使用！";
                createMessageDto
                        .setBody(msgContent);
                JpushClientUtil.sendPush(alias, msgTitle, msgContent, regId, extraParam);
                BaseResponse response = smsClientService.sendSMS(driver.getPhoneNumber(), "SMS_146803933", templateMap);
                log.trace("给司机发短信,driver" + "::::" + driver + ",短信结果:::" + response);
                messageClientService.createMessage(createMessageDto);
            }
            //保险过期提醒
            if (ExpiryDateType.OVER_DATE.equals(msgType)) {
                //xxx师傅，您的车号苏WNC133保险到期时间已超期，请及时处理，以免影响您的正常使用！
                msgContent = driver.getName() + "师傅，您的车号" + getTruckDto.getTruckNumber() + "保险到期时间已经超期，请及时处理，以免影响您的正常使用！";
                createMessageDto
                        .setBody(msgContent);
                JpushClientUtil.sendPush(alias, msgTitle, msgContent, regId, extraParam);
                BaseResponse response = smsClientService.sendSMS(driver.getPhoneNumber(), "SMS_146803933", templateMap);
                log.trace("给司机发短信,driver" + "::::" + driver + ",短信结果:::" + response);
                messageClientService.createMessage(createMessageDto);
            }
            //年检到期提醒
            if (ExpiryDateType.EXPIRY_ANNUAL.equals(msgType)) {
                msgContent = driver.getName() + "师傅，您的车号" + getTruckDto.getTruckNumber() + "年检到期时间为" + formatDateToString(expiryDate) + "，请及时处理，以免影响您的正常使用！";
                createMessageDto
                        .setBody(msgContent);
                JpushClientUtil.sendPush(alias, msgTitle, msgContent, regId, extraParam);
                BaseResponse response = smsClientService.sendSMS(driver.getPhoneNumber(), "SMS_146803933", templateMap);
                log.trace("给司机发短信,driver" + "::::" + driver + ",短信结果:::" + response);
                messageClientService.createMessage(createMessageDto);
            }
            //年检过期提醒
            if (ExpiryDateType.OVER_ANNUAL.equals(msgType)) {
                msgContent = driver.getName() + "师傅，您的车号" + getTruckDto.getTruckNumber() + "年检到期时间已经超期，请及时处理，以免影响您的正常使用！";
                createMessageDto
                        .setBody(msgContent);
                JpushClientUtil.sendPush(alias, msgTitle, msgContent, regId, extraParam);
                BaseResponse response = smsClientService.sendSMS(driver.getPhoneNumber(), "SMS_146803933", templateMap);
                log.trace("给司机发短信,driver" + "::::" + driver + ",短信结果:::" + response);
                messageClientService.createMessage(createMessageDto);
            }
            if (ExpiryDateType.EXPIRY_DRIVING_LICENSE.equals(msgType)) {
                msgContent = driver.getName() + "师傅，您的驾驶证到期时间为" + formatDateToString(stringToDate(driver.getExpiryDrivingLicense())) + "，请及时处理，以免影响您的正常使用！";
                createMessageDto
                        .setBody(msgContent);
                JpushClientUtil.sendPush(alias, msgTitle, msgContent, regId, extraParam);
                BaseResponse response = smsClientService.sendSMS(driver.getPhoneNumber(), "SMS_146803933", templateMap);
                log.trace("给司机发短信,driver" + "::::" + driver + ",短信结果:::" + response);
                messageClientService.createMessage(createMessageDto);
            }
            if (ExpiryDateType.OVER_DRIVING_LICENSE.equals(msgType)) {
                msgContent = driver.getName() + "师傅，您的驾驶证到期时间已超期，请及时处理，以免影响您的正常使用！";
                createMessageDto
                        .setBody(msgContent);
                JpushClientUtil.sendPush(alias, msgTitle, msgContent, regId, extraParam);
                BaseResponse response = smsClientService.sendSMS(driver.getPhoneNumber(), "SMS_146803933", templateMap);
                log.trace("给司机发短信,driver" + "::::" + driver + ",短信结果:::" + response);
                messageClientService.createMessage(createMessageDto);
            }
        }
    }
}