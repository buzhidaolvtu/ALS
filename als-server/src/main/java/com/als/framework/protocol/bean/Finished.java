package com.als.framework.protocol.bean;

/**
 * Created by lvtu on 2017/6/27.
 */
public class Finished {
    private String verify_data;

    public String getVerify_data() {
        return verify_data;
    }

    public void setVerify_data(String verify_data) {
        this.verify_data = verify_data;
    }

    @Override
    public String toString() {
        return "Finished{" +
                "verify_data='" + verify_data + '\'' +
                '}';
    }
}
