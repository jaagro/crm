
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
        List<GetTruckDto> expiryDateTruck = truckMapper.listCertificateOverdueNotice(ExpiryDateType.EXPIRY_DATE);
        for (GetTruckDto expiryDate : expiryDateTruck) {
            String expiryDateTruckId = redisTemplate.opsForValue().get("expiryDate" + expiryDate.getId());
            if (StringUtils.isEmpty(expiryDateTruckId)) {
                List<DriverReturnDto> drivers = driverClientService.listByTruckId(expiryDate.getId());
                sendMessage(drivers, expiryDate.getExpiryDate(), expiryDate.getId(), 1);
            } else {
                setRefIdToRedis("expiryDate" + expiryDate.getId(), expiryDate.getId().toString());
            }
        }
        //提前一个月查询年检过期时间
        List<GetTruckDto> expiryAnnualTruck = truckMapper.listCertificateOverdueNotice(ExpiryDateType.EXPIRY_ANNUAL);
        for (GetTruckDto expiryAnnual : expiryAnnualTruck) {
            String expiryAnnualTruckId = redisTemplate.opsForValue().get("expiryAnnual" + expiryAnnual.getId());
            if (StringUtils.isEmpty(expiryAnnualTruckId)) {
                List<DriverReturnDto> drivers = driverClientService.listByTruckId(expiryAnnual.getId());
                sendMessage(drivers, expiryAnnual.getExpiryAnnual(), expiryAnnual.getId(), 2);
            } else {
                setRefIdToRedis("expiryAnnual" + expiryAnnual.getId(), expiryAnnual.getId().toString());
            }
        }
        //提前一个月查询驾驶证过期时间
        List<DriverReturnDto> driverReturnDtos = (List<DriverReturnDto>) driverClientService.listDriverCertificateOverdueNotice().getData();
        for (DriverReturnDto driverReturnDto : driverReturnDtos) {
            String driverId = redisTemplate.opsForValue().get("driverExpiryDate" + driverReturnDto.getId());
            List<DriverReturnDto> drivers = new ArrayList<>();
            if (StringUtils.isEmpty(driverId)) {
                drivers.add(driverReturnDto);
                sendMessage(drivers, stringToDate(driverReturnDto.getExpiryDrivingLicense()), null, 3);
            } else {
                setRefIdToRedis("driverExpiryDate" + driverReturnDto.getId(), driverReturnDto.getId().toString());
            }
        }
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

    private Date stringToDate(String expiryDrivingLicense) {
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
     * 发送jpush 短信 消息
     *
     * @param
     * @param expiryDate
     */
    private void sendMessage(List<DriverReturnDto> drivers, Date expiryDate, Integer truckId, Integer msgType) {
        String alias = "";
        String msgTitle = "证件过期提醒消息";
        String msgContent;
        String regId;
        for (DriverReturnDto driver : drivers) {
            //调用jpush接口给司机发送短息
            Map<String, String> extraParam = new HashMap<>();
            extraParam.put("driverId", driver.getId().toString());
            extraParam.put("expiryDate", expiryDate.toString());
            regId = driver.getRegistrationId();
            //在app消息插入一条司机记录
            CreateMessageDto createMessageDto = new CreateMessageDto();
            createMessageDto
                    .setReferId(truckId)
                    .setMsgType(5)
                    .setMsgSource(1)
                    .setToUserType(2)
                    .setFromUserId(0)
                    .setToUserId(driver.getId());
            //发送短信给truckId 对应的司机
            Map<String, Object> templateMap = new HashMap<>();
            templateMap.put("driverName", driver.getName());
            if (msgType == 1) {
                createMessageDto
                        .setHeader("========22===========")
                        .setBody("============22===========");
                msgContent = "=============================";
                JpushClientUtil.sendPush(alias, msgTitle, msgContent, regId, extraParam);
                BaseResponse response = smsClientService.sendSMS(driver.getPhoneNumber(), "SMS_146803933", templateMap);
                log.trace("给司机发短信,driver" + "::::" + driver + ",短信结果:::" + response);

            }
            if (msgType == 2) {
                createMessageDto
                        .setHeader("==========11===========")
                        .setBody("==============11=========");
                msgContent = "=============================";
                JpushClientUtil.sendPush(alias, msgTitle, msgContent, regId, extraParam);
                BaseResponse response = smsClientService.sendSMS(driver.getPhoneNumber(), "SMS_146803933", templateMap);
                log.trace("给司机发短信,driver" + "::::" + driver + ",短信结果:::" + response);
            }
            if (msgType == 3) {
                createMessageDto
                        .setHeader("========00=============")
                        .setBody("===========00============");
                msgContent = "=============================";
                JpushClientUtil.sendPush(alias, msgTitle, msgContent, regId, extraParam);
                BaseResponse response = smsClientService.sendSMS(driver.getPhoneNumber(), "SMS_146803933", templateMap);
                log.trace("给司机发短信,driver" + "::::" + driver + ",短信结果:::" + response);
            }
            messageClientService.createMessage(createMessageDto);
        }
    }
}