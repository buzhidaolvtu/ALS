package com.als.framework.servletapi;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by lvtu on 2017/6/30.
 */
public class ALSInputStream extends ServletInputStream {

    private ByteArrayInputStream buffer;

    public ALSInputStream(byte[] bytes) {
        this.buffer = new ByteArrayInputStream(bytes);
    }

    public ALSInputStream(String content){
        this.buffer = new ByteArrayInputStream(content.getBytes());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setReadListener(ReadListener listener) {
        throw new RuntimeException("not supported");
    }

    @Override
    public int read() throws IOException {
        return buffer.read();
    }
}
