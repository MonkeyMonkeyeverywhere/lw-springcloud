package com.lw.cloud;


import com.lw.cloud.base.ResponseCode;
import com.lw.cloud.base.ResponseData;
import com.lw.cloud.util.JWTUtils;
import com.lw.cloud.util.JsonUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * api调用权限控制过滤器
 *
 * @author wei.liu
 */
public class HttpBasicAuthFilter implements Filter {

    JWTUtils jwtUtils = JWTUtils.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        String auth = httpRequest.getHeader("Authorization");
        //健康检查控制
        String uri = httpRequest.getRequestURI();
        if (uri.equals("/autoconfig") || uri.equals("/configprops") || uri.equals("/beans") || uri.equals("/dump")
                || uri.equals("/env") || uri.equals("/health") || uri.equals("/info") || uri.equals("/mappings")
                || uri.equals("/metrics") || uri.equals("/shutdown") || uri.equals("/trace")) {
            /*if(httpRequest.getQueryString() == null){
                PrintWriter print = httpResponse.getWriter();
                print.write(JsonUtils.toJson(ResponseData.fail("非法请求【缺少token信息】", ResponseCode.NO_AUTH_CODE.getCode())));
                return;
            }*/
            chain.doFilter(servletRequest, servletResponse);
        } else {
            //验证token
            if(StringUtils.isEmpty(auth)){
                PrintWriter writer = httpResponse.getWriter();
                writer.write(JsonUtils.toJson(ResponseData.fail("非法请求【缺少token信息】",ResponseCode.NO_AUTH_CODE.getCode())));
                return;
            }
            JWTUtils.JWTResult jwtResult = jwtUtils.checkToken(auth);
            if (!jwtResult.isStatus()) {
                PrintWriter print = httpResponse.getWriter();
                print.write(JsonUtils.toJson(ResponseData.fail(jwtResult.getMsg(), jwtResult.getCode())));
                return;
            }
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
