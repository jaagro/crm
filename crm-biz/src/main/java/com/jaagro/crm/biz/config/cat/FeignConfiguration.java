package com.jaagro.crm.biz.config.cat;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * feign调用 获取调用链
 * @author aoleo
 * @date 2018/12/21 上午10:27
 */

@Configuration
@Slf4j
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes();
                template.header("context", getContext());
                removeSession();
            }
        };
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getContext() {
        try {
            String traceid = MDC.get("X-B3-TraceId");
            if(StringUtils.isNotEmpty(traceid)){
                return (String) getHttpServletRequest().getSession().getAttribute(traceid);
            }
            return (String) getHttpServletRequest().getSession().getAttribute("ctx");
        } catch (Exception e) {
            log.info("链路 ctx is null");
            e.printStackTrace();
            return null;
        }
    }


    private void removeSession() {
        String traceid = MDC.get("X-B3-TraceId");
        if(StringUtils.isNotEmpty(traceid)){
            getHttpServletRequest().getSession().removeAttribute(traceid);
        }else{
            getHttpServletRequest().getSession().removeAttribute("ctx");
        }

    }
}