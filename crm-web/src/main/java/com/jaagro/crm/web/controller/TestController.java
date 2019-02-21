package com.jaagro.crm.web.controller;

import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.dto.request.contract.DriverContractSettleCondition;
import com.jaagro.crm.api.service.TestService;
import com.jaagro.crm.api.service.TruckTeamContractService;
import com.jaagro.crm.biz.mapper.TruckMapperExt;
import com.jaagro.crm.biz.schedule.CertificateOverdueNoticeService;
import com.jaagro.crm.biz.service.AuthClientService;
import com.jaagro.crm.biz.service.DriverClientService;
import com.jaagro.crm.biz.service.MessageClientService;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.crm.biz.service.impl.CurrentUserService;
import com.jaagro.utils.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tony
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    AuthClientService authClientService;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    CertificateOverdueNoticeService certificateOverdueNoticeService;
    @Autowired
    TruckMapperExt truckMapper;
    @Autowired
    DriverClientService driverClientService;
    @Autowired
    MessageClientService messageClientService;
    @Autowired
    TruckTeamContractService truckTeamContractService;
    @Autowired
    TestService testService;
    @GetMapping("/test")
    public UserInfo getToken() {
        String token = request.getHeader("token");
        return authClientService.getUserByToken(token);
    }

    @GetMapping("/test2")
    public UserInfo test() {
        return currentUserService.getCurrentUser();
    }

    @GetMapping("/debug")
    public BaseResponse debug() {
        int a = 10;
        try {
//            a = 10 / 0;
        } catch (Exception e) {
            throw new NullPointerException("我是提供方抛出的异常： " + e.getMessage());
        }
        return BaseResponse.successInstance(a);
    }


    @PostMapping("/test3")
    public BaseResponse test3() {
//        certificateOverdueNoticeService.certificateOverdueNoticeBySystem();
        // truckTeamContractService.createTruckTeamContractPrice(dto, 1, 1);
        DriverContractSettleCondition driverContractSettleCondition = new DriverContractSettleCondition();
        driverContractSettleCondition.setTruckTeamContractId(1).setTruckTypeId(8).setPricingMethod(2).setFlag(2);
        // List<ListDriverContractSettleDto> listDriverContractSettleDtos = truckTeamContractService.listTruckTeamContractPrice(driverContractSettleCondition);
        //System.out.println(listDriverContractSettleDtos.toString());
        return BaseResponse.successInstance(null);

    }

    @PostMapping("/testDelTransactional")
    public BaseResponse testDelTransactional(String token, String wxId, String userId) {
        log.info("O testDelTransactional token={},wxId={},userId={}",token,wxId,userId);
        testService.testDelTransactional(token,wxId,userId);
        //testService.testDelTransactionUseOneConnection(token,wxId,userId);
        return BaseResponse.successInstance("删除成功");
    }
}
