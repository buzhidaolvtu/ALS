package com.als.framework.protocol.bean;

/**
 * Created by lvtu on 2017/6/27.
 */
public class SecurityParameters {
    private String master_secret;
    private String client_random;
    private String server_random;
    private String pre_master_secret;//客户端不保留,服务端保留

    public String getMaster_secret() {
        return master_secret;
    }

    public void setMaster_secret(String master_secret) {
        this.master_secret = master_secret;
    }

    public String getClient_random() {
        return client_random;
    }

    public void setClient_random(String client_random) {
        this.client_random = client_random;
    }

    public String getServer_random() {
        return server_random;
    }

    public void setServer_random(String server_random) {
        this.server_random = server_random;
    }

    public String getPre_master_secret() {
        return pre_master_secret;
    }

    public void setPre_master_secret(String pre_master_secret) {
        this.pre_master_secret = pre_master_secret;
    }

    @Override
    public String toString() {
        return "SecurityParameters{" +
                "master_secret='" + master_secret + '\'' +
                ", client_random='" + client_random + '\'' +
                ", server_random='" + server_random + '\'' +
                ", pre_master_secret='" + pre_master_secret + '\'' +
                '}';
    }
}
