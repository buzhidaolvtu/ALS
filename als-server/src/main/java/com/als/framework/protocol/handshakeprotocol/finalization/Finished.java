package com.als.framework.protocol.handshakeprotocol.finalization;

import com.als.framework.protocol.handshakeprotocol.AbstractHandshakeTypeMessage;

/**
 * Created by lvtu on 2017/7/4.
 */
public class Finished extends AbstractHandshakeTypeMessage {
    byte[] verify_data;
}
