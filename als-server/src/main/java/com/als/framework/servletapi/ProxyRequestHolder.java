package com.als.framework.servletapi;

import com.als.framework.protocol.AlsCipherUtils;
import com.als.framework.protocol.recordprotocol.ALSCiphertext;
import com.als.framework.protocol.securityparameters.ContextParameters;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by lvtu on 2017/6/30.
 */
public class ProxyRequestHolder {

    ALSHttpServletRequestWrapper alsHttpServletRequestWrapper;
    ALSHttpServletResponseWrapper alsHttpServletResponseWrapper;

    private ContextParameters contextParameters;

    private HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

    public ProxyRequestHolder(ALSHttpServletRequestWrapper alsHttpServletRequestWrapper, ALSHttpServletResponseWrapper alsHttpServletResponseWrapper, ContextParameters contextParameters) {
        this.alsHttpServletRequestWrapper = alsHttpServletRequestWrapper;
        this.alsHttpServletResponseWrapper = alsHttpServletResponseWrapper;
        this.contextParameters = contextParameters;
    }

    public ALSHttpServletRequestWrapper getAlsHttpServletRequestWrapper() {
        return alsHttpServletRequestWrapper;
    }

    public void setAlsHttpServletRequestWrapper(ALSHttpServletRequestWrapper alsHttpServletRequestWrapper) {
        this.alsHttpServletRequestWrapper = alsHttpServletRequestWrapper;
    }

    public ALSHttpServletResponseWrapper getAlsHttpServletResponseWrapper() {
        return alsHttpServletResponseWrapper;
    }

    public void setAlsHttpServletResponseWrapper(ALSHttpServletResponseWrapper alsHttpServletResponseWrapper) {
        this.alsHttpServletResponseWrapper = alsHttpServletResponseWrapper;
    }

    public void outputToSocket() {
        try {
            ALSCiphertext encrypt = AlsCipherUtils.encrypt(contextParameters.getSecurityParameters().getMaster_secret(), new String(alsHttpServletResponseWrapper.pipeline().toByteArray(), "utf-8"));
            ServerHttpResponse serverHttpResponse = new ServletServerHttpResponse((HttpServletResponse) alsHttpServletResponseWrapper.getResponse());
            alsHttpServletResponseWrapper.setContentLength(-1);//如果不修改这个值,浏览器会按照这个值截取,因为加密了数据,这个值不再正确。
            jsonConverter.write(encrypt, MediaType.APPLICATION_JSON_UTF8, serverHttpResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
