package com.als.framework.protocol.securityparameters;

/**
 * Created by lvtu on 2017/6/27.
 */
public class ContextParameters {
    private String public_key;
    private String private_key;//仅在服务器端保存
    private String client_version;
    private String server_version;
    private String session_id;

    private ALSSecurityParameters securityParameters;

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getClient_version() {
        return client_version;
    }

    public void setClient_version(String client_version) {
        this.client_version = client_version;
    }

    public String getServer_version() {
        return server_version;
    }

    public void setServer_version(String server_version) {
        this.server_version = server_version;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public ALSSecurityParameters getSecurityParameters() {
        return securityParameters;
    }

    public void setSecurityParameters(ALSSecurityParameters securityParameters) {
        this.securityParameters = securityParameters;
    }

    @Override
    public String toString() {
        return "ContextParameters{" +
                "public_key='" + public_key + '\'' +
                ", private_key='" + private_key + '\'' +
                ", client_version='" + client_version + '\'' +
                ", server_version='" + server_version + '\'' +
                ", session_id='" + session_id + '\'' +
                ", securityParameters=" + securityParameters +
                '}';
    }
}
