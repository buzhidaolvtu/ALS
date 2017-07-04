/**
 * Created by lvtu on 2017/6/21.
 */
'use strict';
(function (window, document) {

    var hashAlgorithm = {
        hash: hash
    };
    var signatureAlgorithm = {};
    var encryptAlgorithm = {
        AES_encrypt: AES_encrypt,
        AES_decrypt: AES_decrypt,
        RSA_encrypt: RSA_encrypt
    };

    var client_version = "1.0";

    function hash(data) {
        var shaObj = new jsSHA("SHA-256", "TEXT");
        for (var i = 0; i < arguments.length; i++) {
            shaObj.update(arguments[i]);
        }
        return shaObj.getHash("B64");
    }

    function MAC(secret, data) {
        var shaObj = new jsSHA("SHA-256", "TEXT");
        shaObj.setHMACKey(secret, "TEXT");
        shaObj.update(data);
        return shaObj.getHMAC("B64");
    }

    /**
     * from Uint8Array to Base64
     * @param buf
     * @returns {string}
     */
    function bufferToBase64(buf) {
        var binstr = Array.prototype.map.call(buf, function (ch) {
            return String.fromCharCode(ch);
        }).join('');
        return btoa(binstr);
    }

    /**
     * from Base64 to Uint8Array
     * @param base64
     * @returns {Uint8Array}
     */
    function base64ToBuffer(base64) {
        var binstr = atob(base64);
        var buf = new Uint8Array(binstr.length);
        Array.prototype.forEach.call(binstr, function (ch, i) {
            buf[i] = ch.charCodeAt(0);
        });
        return buf;
    }

    /**
     * Supports all key sizes (128-bit, 192-bit and 256-bit)
     * Supports all common modes of operation (CBC, CFB, CTR, ECB and OFB)
     * @param secret 48bytes
     * @param data
     * @constructor
     */
    function AES_encrypt(secret, data) {
        // // An 128-bit key
        // var key = CryptoJS.enc.Hex.parse(secret.substring(0, 16));
        // var iv  = CryptoJS.enc.Hex.parse(secret.substring(0, 8));
        // var encrypt = CryptoJS.AES.encrypt(data,key,{ iv: iv });
        // return CryptoJS.enc.Base64.stringify(encrypt.ciphertext);

        // An 128-bit key
        var key = aesjs.utils.utf8.toBytes(secret.substring(0, 16));

        // The initialization vector (must be 16 bytes)
        var iv = aesjs.utils.utf8.toBytes(secret.substring(0, 16));

        // Convert text to bytes (text must be a multiple of 16 bytes)
        var textBytes = aesjs.utils.utf8.toBytes(data);

        var aesCbc = new aesjs.ModeOfOperation.cbc(key, iv);
        return bufferToBase64(aesCbc.encrypt(padding16Bytes(textBytes)));
    }

    function padding16Bytes(bytes) {
        var modLength = bytes.length % 16;
        if (modLength === 0) {
            var b = new Uint8Array([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);
            var c = new Uint8Array(bytes.length + b.length);
            c.set(bytes);
            c.set(b, bytes.length);
            return c;
        } else {
            var padding = [];
            var padLength = 16 - modLength;
            for (var i = 0; i < padLength; i++) {
                padding[i] = 0;
            }
            padding = padding.concat([padLength, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);
            var b = new Uint8Array(padding);
            var c = new Uint8Array(bytes.length + b.length);
            c.set(bytes);
            c.set(b, bytes.length);
            return c;
        }
    }

    function depadding(bytesWithPadding, length) {
        length = length || 16;
        var paddingLength = bytesWithPadding[bytesWithPadding.length - length];
        return bytesWithPadding.subarray(0, bytesWithPadding.length - length - paddingLength);
    }

    function toBlobMessage(bytes) {
        var arr = new Uint8Array(bytes);
        return new Blob(arr, {type: 'application/octet-binary'});
    }

    /**
     * 默认48字节的数字字符串
     * @param length
     */
    function generateSecret() {
        var string = "";
        string
            .concat(Math.random().toString().replace("0.", ""))
            .concat(Math.random().toString().replace("0.", ""))
            .concat(Math.random().toString().replace("0.", ""));
    }

    function generateRandom(length) {
        var count = length / (Math.random().toString().replace("0.", "").length) + 1;
        var string = "";
        for (var i = 0; i < count; i++) {
            string = string.concat(Math.random().toString().replace("0.", ""));
        }
        return string.substring(0, length);
    }

    function AES_decrypt(secret, data) {
        // An 128-bit key
        var key = aesjs.utils.utf8.toBytes(secret.substring(0, 16));

        // The initialization vector (must be 16 bytes)
        var iv = aesjs.utils.utf8.toBytes(secret.substring(0, 16));

        // The cipher-block chaining mode of operation maintains internal
        // state, so to decrypt a new instance must be instantiated.
        var aesCbc = new aesjs.ModeOfOperation.cbc(key, iv);
        var decryptedBytes = aesCbc.decrypt(base64ToBuffer(data));

        // Convert our bytes back into text
        return aesjs.utils.utf8.fromBytes(depadding(decryptedBytes, 16));
    }

    function RSA_encrypt(publicKey, data) {
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(publicKey);
        return encrypt.encrypt(data);
    }

    function extractDataPart(message) {

    }

    function extractMACPart(message) {

    }


    var decrypt = function (message) {

    };

    /**
     * 通过MAC验证消息完整性
     */
    var verifyMAC = function (message) {
        return MAC(this.sharedSecret, extractDataPart(message)) === extractMACPart(message);
    };

    var verifySecret = function (hashValue) {
        return hash(this.sharedSecret) == hashValue;
    };

    var ALS = function (publicKey) {
        this.securityParameters = new SecurityParameters();
        this.contextParameters = new ContextParameters();
        this.contextParameters.public_key = publicKey;
        this.contextParameters.securityParameters = this.securityParameters;

        var securityParameters = this.securityParameters;
        var contextParameters = this.contextParameters;

        function createClientHello() {
            var clientHello = {};
            clientHello.client_version = client_version;
            clientHello.random = generateRandom(48);//48字节
            clientHello.session_id = null;//48字节
            return clientHello;
        }

        function ServerHello() {
            this.server_version = null;
            this.random = null;
            this.session_id = null;
        }


        function ClientKeyExchange(pre_master_secret) {
            this.exchange_keys = new EncryptedPreMasterSecret(pre_master_secret);
        }

        function createPreMasterSecret() {
            var pre_master_secret = {};
            pre_master_secret.client_version = client_version;
            pre_master_secret.random = generateRandom(48);
            pre_master_secret.getPreMasterSecret = function () {
                return this.random;
            };
            return pre_master_secret;
        }

        function EncryptedPreMasterSecret(pre_master_secret) {
            this.encrypted_pre_master_secret = RSA_encrypt(contextParameters.public_key, pre_master_secret.getPreMasterSecret());
        }


        function Finished() {
            this.verify_data = null;
        }

        function SecurityParameters() {
            this.pre_master_secret = null;
            this.master_secret = null;
            this.client_random = null;
            this.server_random = null;
        }

        function ContextParameters() {
            this.public_key = null;
            this.client_version = null;
            this.server_version = null;
            this.session_id = null;
            this.securityParameters = null;
        }

        /**
         * data必须是字符串类型
         * @param data
         * @constructor
         */
        function GenericBlockCipher(data) {
            if (typeof data === "string") {
                this.data = data;
            } else if (typeof data === "object") {
                this.data = JSON.stringify(data);
            }
            this.mac = MAC(securityParameters.master_secret, this.data);
        }

        function ALSCiphertext(obj) {
            this.version = client_version;
            this.cipher_data = AES_encrypt(securityParameters.master_secret, JSON.stringify(obj));
        }

        // ClientHello.prototype.toBlobMessage = function () {
        //     var secretBytes = aesjs.utils.utf8.toBytes(this.sharedSecret);
        //     var sessionBytes = aesjs.utils.utf8.toBytes(this.session_id);
        //     secretBytes.concat(sessionBytes);
        //     var arr = new Uint8Array(secretBytes);
        //     return new Blob(arr, {type: 'application/octet-binary'});
        // };

        function createMasterSecret() {
            return hash(securityParameters.client_random, securityParameters.server_random, securityParameters.pre_master_secret.getPreMasterSecret(), "master secret");
        }

        /**
         * jQuery defer的使用方法???
         * @returns {*}
         */
        var handshake = function () {
            var clientHello = createClientHello();
            contextParameters.client_version = clientHello.client_version;
            securityParameters.client_random = clientHello.random;
            return $.ajax({//send ClientHello message
                url: "http://localhost:8080/ClientHello",
                method: "POST",
                cache: false,
                contentType: "application/json; charset=UTF-8",
                data: JSON.stringify(clientHello)
            }).then(function (serverHello) {
                //received ServerHello message
                securityParameters.server_random = serverHello.random;
                contextParameters.session_id = serverHello.session_id;
                contextParameters.server_version = serverHello.server_version;

                securityParameters.pre_master_secret = createPreMasterSecret();
                var clientKeyExchange = new ClientKeyExchange(securityParameters.pre_master_secret);

                return $.ajax({//send clientKeyExchange message
                    url: "http://localhost:8080/clientKeyExchange?sessionId=" + getSessionId(),
                    method: "POST",
                    cache: false,
                    contentType: "application/json; charset=UTF-8",
                    data: JSON.stringify(clientKeyExchange)
                });
            }, function () {
                console.log("request failed.ClientHello");
            }).then(function () {
                var clientFinished = new Finished();
                clientFinished.verify_data = hash(securityParameters.client_random, securityParameters.server_random, securityParameters.pre_master_secret.getPreMasterSecret(), "client finished");
                return $.ajax({//send ClientFinished message
                    url: "http://localhost:8080/ClientFinished?sessionId=" + getSessionId(),
                    method: "POST",
                    cache: false,
                    contentType: "application/json; charset=UTF-8",
                    data: JSON.stringify(clientFinished)
                });
            }, function () {
                console.log("request failed.clientKeyExchange");
            }).then(function (serverFinished) {
                //receive ServerFinished message
                var defer = $.Deferred();
                if (serverFinished.verify_data == hash(securityParameters.client_random, securityParameters.server_random, securityParameters.pre_master_secret.getPreMasterSecret(), "server finished")) {
                    //handshake succeed;
                    securityParameters.master_secret = createMasterSecret();
                    securityParameters.pre_master_secret = null;
                    defer.resolve();
                } else {
                    defer.reject();
                }
                return defer;
            }, function () {
                console.log("request failed.ClientFinished");
            });
        };

        /**
         * @param originalData
         * @returns {*}
         */
        var encrypt = function (originalData) {
            var gbc = new GenericBlockCipher(originalData);
            var alsCipher = new ALSCiphertext(gbc);
            return alsCipher;
        };

        var decrypt = function (encryptedData) {
            var aesDecrypt = AES_decrypt(securityParameters.master_secret, encryptedData.cipher_data);
            var genericBlockCipher = JSON.parse(aesDecrypt);
            var data = genericBlockCipher.data;
            var mac = MAC(securityParameters.master_secret, data);
            if (mac === genericBlockCipher.mac) {
                console.log("message from server is correct.");
            }
            return data;
        };

        var getSessionId = function () {
            return encodeURIComponent(contextParameters.session_id);
        };

        this.handshake = handshake;
        this.encrypt = encrypt;
        this.decrypt = decrypt;
        this.getSessionId = getSessionId;
    };


    window.ALS = ALS;
})(window, window.document);

