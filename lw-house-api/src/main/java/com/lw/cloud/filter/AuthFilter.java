package com.lw.cloud.filter;

import com.lw.cloud.conf.BasicConf;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private BasicConf basicConf;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String apiWhiteStr = basicConf.getApiWhiteStr();
        List<String> apiWhiteList = Arrays.asList(apiWhiteStr.split(","));
        String uri = ctx.getRequest().getRequestURI();
        if(apiWhiteList.contains(uri)){
            logger.info("=======白名单请求======");
            return null;
        }
        // path uri 处理
        for (String wapi : apiWhiteList) {
            if (wapi.contains("{") && wapi.contains("}")) {
                if (wapi.split("/").length == uri.split("/").length) {
                    String reg = wapi.replaceAll("\\{.*}", ".*{1,}");
                    System.err.println(reg);
                    Pattern r = Pattern.compile(reg);
                    Matcher m = r.matcher(uri);
                    if (m.find()) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
