package com.lw.cloud.fallback;

import com.lw.cloud.base.ResponseCode;
import com.lw.cloud.base.ResponseData;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;

@Component
public class ServiceConsumerFallbackProvider implements ZuulFallbackProvider {

    private Logger logger = LoggerFactory.getLogger(ServiceConsumerFallbackProvider.class);

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType("application","json",Charset.forName("UTF-8"));
                headers.setContentType(mt);
                return headers;
            }

            @Override
            public InputStream getBody() throws IOException {
                RequestContext ctx = RequestContext.getCurrentContext();
                Throwable throwable = ctx.getThrowable();
                if(!Objects.equals(null,throwable)){
                    logger.error("",throwable.getCause());
                }
                ResponseData data = ResponseData.fail("服务器内部错误",ResponseCode.SERVER_ERROR_CODE.getCode());
                return new ByteArrayInputStream(data.toString().getBytes());
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return this.getRawStatusCode();
            }

            @Override
            public String getStatusText() throws IOException {
                return this.getStatusText();
            }

            @Override
            public void close() {

            }
        };
    }
}
