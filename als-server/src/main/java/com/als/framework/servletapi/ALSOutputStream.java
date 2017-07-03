package com.als.framework.servletapi;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by lvtu on 2017/6/30.
 */
public class ALSOutputStream extends ServletOutputStream {

    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener listener) {
        throw new RuntimeException("not supported");
    }

    @Override
    public void write(int b) throws IOException {
        buffer.write(b);
    }

    public ByteArrayOutputStream getBuffer() {
        return buffer;
    }
}
