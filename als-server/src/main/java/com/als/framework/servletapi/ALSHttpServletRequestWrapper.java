package com.als.framework.servletapi;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Created by lvtu on 2017/6/30.
 */
public class ALSHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private ALSInputStream alsInputStream;

    public ALSHttpServletRequestWrapper(HttpServletRequest request,byte[] decryptedData) {
        super(request);
        this.alsInputStream = new ALSInputStream(decryptedData);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return alsInputStream;
    }
}
