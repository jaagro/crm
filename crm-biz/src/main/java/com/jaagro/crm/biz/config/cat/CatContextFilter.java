package com.jaagro.crm.biz.config.cat;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 链路
 * @author zhouao
 * @date 2018/12/21 11:07
 */
public class CatContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        catClinet(request);
        catServer(request);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }


    private void catClinet(HttpServletRequest request){
        //创建链路
        MyContext ctx = new MyContext();
        Cat.logRemoteCallClient(ctx);
        String traceid = MDC.get("X-B3-TraceId");
        if(StringUtils.isNotEmpty(traceid)){
            request.getSession().setAttribute(traceid, JSONObject.toJSONString(ctx));
        }else{
            request.getSession().setAttribute("ctx", JSONObject.toJSONString(ctx));
        }

    }

    private void catServer(HttpServletRequest request){
        String contextStr = request.getHeader("context");
        if (StringUtils.isNotEmpty(contextStr)){
            MyContext mycontext = JSONObject.parseObject(contextStr,MyContext.class);
            if(mycontext !=null){
                Cat.logRemoteCallServer(mycontext);
            }
        }
    }


}
