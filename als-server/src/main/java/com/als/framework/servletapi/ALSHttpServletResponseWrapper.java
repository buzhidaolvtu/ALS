package com.als.framework.servletapi;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by lvtu on 2017/6/30.
 */
public class ALSHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private ALSOutputStream alsOutputStream;

    public ALSHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
        alsOutputStream = new ALSOutputStream();
    }

    @Override
    public ALSOutputStream getOutputStream() throws IOException {
        return alsOutputStream;
    }

    public ByteArrayOutputStream pipeline(){
        return alsOutputStream.getBuffer();
    }
}
