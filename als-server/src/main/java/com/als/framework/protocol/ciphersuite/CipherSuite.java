package com.als.framework.protocol.ciphersuite;

/**
 * Created by lvtu on 2017/7/4.
 */
public enum CipherSuite {
    /**
     * TLS_NULL_WITH_NULL_NULL is specified and is the initial state of a
     * TLS connection during the first handshake on that channel, but MUST
     * NOT be negotiated, as it provides no more protection than an
     * unsecured connection.
     */
    TLS_NULL_WITH_NULL_NULL,

    /**
     * The following CipherSuite definitions require that the server provide
     * an RSA certificate that can be used for key exchange. The server may
     * request any signature-capable certificate in the certificate request
     * message.
     */
    TLS_RSA_WITH_NULL_MD5,
    TLS_RSA_WITH_NULL_SHA,
    TLS_RSA_WITH_NULL_SHA256,
    TLS_RSA_WITH_RC4_128_MD5,
    TLS_RSA_WITH_RC4_128_SHA,
    TLS_RSA_WITH_3DES_EDE_CBC_SHA,
    TLS_RSA_WITH_AES_128_CBC_SHA,
    TLS_RSA_WITH_AES_256_CBC_SHA,
    TLS_RSA_WITH_AES_128_CBC_SHA256,
    TLS_RSA_WITH_AES_256_CBC_SHA256,

    /**
     * The following cipher suite definitions are used for serverauthenticated
     * (and optionally client-authenticated) Diffie-Hellman.
     * DH denotes cipher suites in which the server’s certificate contains
     * the Diffie-Hellman parameters signed by the certificate authority
     * (CA). DHE denotes ephemeral Diffie-Hellman, where the Diffie-Hellman
     * parameters are signed by a signature-capable certificate, which has
     * been signed by the CA. The signing algorithm used by the server is
     * specified after the DHE component of the CipherSuite name. The
     * server can request any signature-capable certificate from the client
     * for client authentication, or it may request a Diffie-Hellman
     * certificate. Any Diffie-Hellman certificate provided by the client
     * must use the parameters (group and generator) described by the
     * server.
     */
    TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA,
    TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA,
    TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA,
    TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA,
    TLS_DH_DSS_WITH_AES_128_CBC_SHA,
    TLS_DH_RSA_WITH_AES_128_CBC_SHA,
    TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
    TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
    TLS_DH_DSS_WITH_AES_256_CBC_SHA,
    TLS_DH_RSA_WITH_AES_256_CBC_SHA,
    TLS_DHE_DSS_WITH_AES_256_CBC_SHA,
    TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
    TLS_DH_DSS_WITH_AES_128_CBC_SHA256,
    TLS_DH_RSA_WITH_AES_128_CBC_SHA256,
    TLS_DHE_DSS_WITH_AES_128_CBC_SHA256,
    TLS_DHE_RSA_WITH_AES_128_CBC_SHA256,
    TLS_DH_DSS_WITH_AES_256_CBC_SHA256,
    TLS_DH_RSA_WITH_AES_256_CBC_SHA256,
    TLS_DHE_DSS_WITH_AES_256_CBC_SHA256,
    TLS_DHE_RSA_WITH_AES_256_CBC_SHA256,


    /**
     * The following cipher suites are used for completely anonymous
     * Diffie-Hellman communications in which neither party is
     * authenticated. Note that this mode is vulnerable to man-in-themiddle
     * attacks. Using this mode therefore is of limited use: These
     * cipher suites MUST NOT be used by TLS 1.2 implementations unless the
     * application layer has specifically requested to allow anonymous key
     * exchange. (Anonymous key exchange may sometimes be acceptable, for
     * example, to support opportunistic encryption when no set-up for
     * authentication is in place, or when TLS is used as part of more
     * complex security protocols that have other means to ensure
     * authentication.)
     */
    TLS_DH_anon_WITH_RC4_128_MD5,
    TLS_DH_anon_WITH_3DES_EDE_CBC_SHA,
    TLS_DH_anon_WITH_AES_128_CBC_SHA,
    TLS_DH_anon_WITH_AES_256_CBC_SHA,
    TLS_DH_anon_WITH_AES_128_CBC_SHA256,
    TLS_DH_anon_WITH_AES_256_CBC_SHA256;

}
