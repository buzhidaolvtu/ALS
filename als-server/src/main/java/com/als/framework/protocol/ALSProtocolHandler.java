package com.als.framework.protocol;

import com.als.framework.protocol.handshakeprotocol.ClientKeyExchange;
import com.als.framework.protocol.handshakeprotocol.clientkeyexchange.EncryptedPreMasterSecret;
import com.als.framework.protocol.handshakeprotocol.finalization.Finished;
import com.als.framework.protocol.handshakeprotocol.GenericBlockCipher;
import com.als.framework.protocol.handshakeprotocol.hellomessage.ALSClientHello;
import com.als.framework.protocol.handshakeprotocol.hellomessage.ALSServerHello;
import com.als.framework.protocol.recordprotocol.ALSCiphertext;
import com.als.framework.protocol.securityparameters.ContextParameters;
import com.als.framework.protocol.securityparameters.ALSSecurityParameters;
import com.als.framework.servletapi.ALSHttpServletRequestWrapper;
import com.als.framework.servletapi.ALSHttpServletResponseWrapper;
import com.als.framework.servletapi.ProxyRequestHolder;
import com.als.framework.tools.EncryptUtils;
import com.als.framework.tools.HashUtils;
import com.als.framework.tools.MACUtils;
import com.als.framework.tools.RandomUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lvtu on 2017/6/27.
 */
public class ALSProtocolHandler {

    private final static Logger logger = LoggerFactory.getLogger(ALSProtocolHandler.class);

    private final static String public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7Agb1PsYJfw9ixapGZmhrHY3DLRSjFZ7qNF8UC/H5poPFGv1kgu47CHGYmm7b5wz/OqVAWEt0ngFotC0lvGlscdMvLAAV1HIrdRptFYVinjUwGGdx+XVuHAjljHkbKazlyP62tVk3UnGtpX4OxzLHeNiqmnxrJpTZoTOs3HjoNJWD8KScHQ8GhbExOCDzfdGB7Jv4nFRoUa9oEDIfJj37vVI3yY+kHNZxAe2wnLCNFlZx0QnWhnuGgpH7+RMwiPuFJlZNWQWx1brzSehvRCJRnzxh4myZWMi7Zq8/jd/gofET/ss2nB6FzyYrE9RL8XRKcGK5Gls1vEhBRe4ePPpfwIDAQAB";

    private final static String private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDsCBvU+xgl/D2LFqkZmaGsdjcMtFKMVnuo0XxQL8fmmg8Ua/WSC7jsIcZiabtvnDP86pUBYS3SeAWi0LSW8aWxx0y8sABXUcit1Gm0VhWKeNTAYZ3H5dW4cCOWMeRsprOXI/ra1WTdSca2lfg7HMsd42KqafGsmlNmhM6zceOg0lYPwpJwdDwaFsTE4IPN90YHsm/icVGhRr2gQMh8mPfu9UjfJj6Qc1nEB7bCcsI0WVnHRCdaGe4aCkfv5EzCI+4UmVk1ZBbHVuvNJ6G9EIlGfPGHibJlYyLtmrz+N3+Ch8RP+yzacHoXPJisT1EvxdEpwYrkaWzW8SEFF7h48+l/AgMBAAECggEBANlhJnDiyHzDcNLWjeq76YByZ4TV1mjVhZIXTQx01QB2xoiiXUqibS9cc99TTYMfM2otign5PEBotZgb6xZRe67dxLjKx6Wj9tTRmoCx/6jGW/rKbIwcCteQv9WmIaBHb4M1aeV9PyXmupKSUEnw3MisEMSuShwTDdiPW+c8bs4+5JyRmeL/p2FpV6Qg3a/cXjyc0ENwp8DhRVSGHEPrPhOH7fiDs/WipBo7glT0tXoLyqKr8vG0JJQOTrwVlDwr3CCaSeJssWzpxQdHENDw7co9lewOGRi2qxUvafW51UzIX7IGr2quhPtkRZqsoBQ7prpGZuvPNqxKLAkQiu8VUdECgYEA+T7EQv9CSjwrmvtSz5i0ynPGAgBJtlnn1ZCu607XIOVNqmNk/XpNndqpJbiSOjda0NhAflW1l0ROnNIoKahbwcE3+RcLoj3rjbscpIffy4hmKnrZMNi3Jgr4oL50Pi9DYLbXV6hri/JDRfCh8Wfc3mgYHCzVZJVPJ+SGWZX4K+0CgYEA8m2rF8jU/358JTnR9xUthYHGSYaA5iqM8yYK+hZ1De9BqtluUz4KAVBCCWo1/iY7XJEq4KGffxn9YXWDRIG4ZN6bAUdZOlAlwi3ZUFr5N/tnm9K6GoX/NnDv7zfMXqfouzKS5BOmwat+/Vm8Gv2qhRVusWyf1pEUCfXN07ZWdZsCgYEAiZIAZ+5WW30coF1p1tW1rCz8tKwOA41aLgG+2nhYu3p1RL+5xULVRnSjegOHCoT1JmLGLjUGluiH73z72ApqIuaQC3eR30oIEuGdLJ8mgkPWCEpoSNLFPEU/+BFHPe9+ZApnj6WRPtxID3mDGSi3uzZ/YEXKvsq+twkmspnZMf0CgYEAk+e06t3slbRlqZCNOWICU+HJSDJ3kfWD6RTcvZfV8pp2RfZCCSdgk0yT/Yp/Rn+s19z5e/MfjDwfCsbXP4SlK2VkzHLrTVh4sO+oFg5q3mzT2Y3HXUdDXcOpnxk75sQqiYthcyVKJ/6pi4ij6xO4ZVmeH0FWg2IgB7Ofz/r+5OECgYAj+bT88Wq/m24Eo3tAyL6/LDlnY9cYRr2Uzjy30PJuKmuSfVYY4tkz8FRQN1pU2s6uHsXDz2d213yglnkX+GIEdvdkWmt7ha85lwMQFyYhd6Bx24xf79ba00FR+ZYpqrYs5ZxIIzdU3VGlolOVDA+i2r50QFuLx73qOyrnenVVEw==";

    private Cache<String, ContextParameters> cache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).build();

    private HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

    private final static List<String> handshakeRequests = Lists.newArrayList(
            "ClientHello",
            "clientKeyExchange",
            "ClientFinished");

    public boolean isHandshakePhase(HttpServletRequest request) {
        final String requestURI = request.getRequestURI();
        return handshakeRequests.stream().anyMatch(requestSuffix -> requestURI.endsWith(requestSuffix));
    }

    public void processHandshake(HttpServletRequest request, HttpServletResponse response) {
        try {
            String requestURI = request.getRequestURI();
            ServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
            ServerHttpResponse serverHttpResponse = new ServletServerHttpResponse(response);
            if (requestURI.endsWith("ClientHello")) {
                ALSSecurityParameters sp = new ALSSecurityParameters();
                ContextParameters cp = new ContextParameters();
                cp.setSecurityParameters(sp);

                ALSClientHello ALSClientHello = (ALSClientHello) jsonConverter.read(ALSClientHello.class, serverHttpRequest);
                logger.debug("ClientHello message:{}", ALSClientHello);

                ALSServerHello ALSServerHello = new ALSServerHello();
                String server_random = RandomUtils.secureRandomString(48);
                ALSServerHello.setRandom(server_random);
                String sessionId = RandomUtils.secureRandomString(16);
                ALSServerHello.setSession_id(sessionId);
                logger.debug("ServerHello message:{}", ALSServerHello);

                sp.setClient_random(ALSClientHello.getRandom());
                sp.setServer_random(server_random);
                cp.setServer_version(ALSServerHello.getServer_version());
                cp.setClient_version(ALSClientHello.getClient_version());
                cp.setSession_id(sessionId);
                cp.setPublic_key(public_key);
                cp.setPrivate_key(private_key);
                cache.put(sessionId, cp);
                jsonConverter.write(ALSServerHello, MediaType.APPLICATION_JSON_UTF8, serverHttpResponse);
            } else if (requestURI.endsWith("clientKeyExchange")) {
                String sessionId = request.getParameter("sessionId");
                ContextParameters contextParameters = cache.getIfPresent(sessionId);
                if (contextParameters == null) {
                    logger.warn("Context not exist.");
                    return;
                }

                ClientKeyExchange clientKeyExchange = (ClientKeyExchange) jsonConverter.read(ClientKeyExchange.class, serverHttpRequest);
                logger.debug("ClientKeyExchange message:{}", clientKeyExchange);
                EncryptedPreMasterSecret exchange_keys = clientKeyExchange.getExchange_keys();
                byte[] decrypt = EncryptUtils.decrypt(Base64.decodeBase64(exchange_keys.getEncrypted_pre_master_secret()), EncryptUtils.EncryptAlgorithm.RSA, contextParameters.getPrivate_key());
                String pre_master_secret = new String(decrypt, "UTF-8");
                contextParameters.getSecurityParameters().setPre_master_secret(pre_master_secret);
            } else if (requestURI.endsWith("ClientFinished")) {
                String sessionId = request.getParameter("sessionId");
                ContextParameters contextParameters = cache.getIfPresent(sessionId);
                if (contextParameters == null) {
                    logger.warn("Context not exist.");
                    return;
                }
                ALSSecurityParameters sp = contextParameters.getSecurityParameters();
                Finished clientFinished = (Finished) jsonConverter.read(Finished.class, serverHttpRequest);
                logger.debug("clientFinished message:{}", clientFinished);
                String verify_data = clientFinished.getVerify_data();
                if (verify_data.equals(HashUtils.hash(HashUtils.HashAlgorithm.SHA_256,
                        sp.getClient_random(),
                        sp.getServer_random(),
                        sp.getPre_master_secret(),
                        "client finished"))) {
                    sp.setMaster_secret(HashUtils.hash(HashUtils.HashAlgorithm.SHA_256,
                            sp.getClient_random(),
                            sp.getServer_random(),
                            sp.getPre_master_secret(),
                            "master secret"));
                    Finished serverFinished = new Finished();
                    serverFinished.setVerify_data(HashUtils.hash(HashUtils.HashAlgorithm.SHA_256, sp.getClient_random(), sp.getServer_random(), sp.getPre_master_secret(), "server finished"));
                    jsonConverter.write(serverFinished, MediaType.APPLICATION_JSON_UTF8, serverHttpResponse);
                } else {
                    logger.error("verify error.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProxyRequestHolder processRequest(HttpServletRequest request, HttpServletResponse response) {
        ServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try {
            String sessionId = request.getParameter("sessionId");
            ContextParameters contextParameters = cache.getIfPresent(sessionId);
            if (contextParameters == null) {
                logger.warn("Context not exist.");
                return null;
            }

            ALSCiphertext alsCiphertext = (ALSCiphertext) jsonConverter.read(ALSCiphertext.class, serverHttpRequest);
            GenericBlockCipher genericBlockCipher = AlsCipherUtils.decrypt(contextParameters.getSecurityParameters().getMaster_secret(), alsCiphertext);
            logger.debug("GenericBlockCipher data:{}", genericBlockCipher);
            String hmac = MACUtils.hmac(MACUtils.MACAlgorithm.HmacSHA256, contextParameters.getSecurityParameters().getMaster_secret(), genericBlockCipher.getData());
            if (hmac.equals(genericBlockCipher.getMac())) {
                logger.debug("message is correct");
            } else {
                logger.error("message is not consistent.");
            }

            ALSHttpServletRequestWrapper requestWrapper = new ALSHttpServletRequestWrapper(request, genericBlockCipher.getData().getBytes());
            ALSHttpServletResponseWrapper responseWrapper = new ALSHttpServletResponseWrapper(response);
            return new ProxyRequestHolder(requestWrapper, responseWrapper, contextParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("processRequest");
    }
}
