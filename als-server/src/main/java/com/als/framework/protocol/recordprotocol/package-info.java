/**
 * client write MAC key
 * server write MAC key
 * client write encryption key
 * server write encryption key
 * client write IV
 * server write IV
 *
 * Once the security parameters have been set and the keys have been
 * generated, the connection states can be instantiated by making them
 * the current states. These current states MUST be updated for each
 * record processed. Each connection state includes the following
 * elements:
 *
 * compression state
 *      The current state of the compression algorithm.
 *
 * cipher state
 *      The current state of the encryption algorithm. This will consist
 * of the scheduled key for that connection. For stream ciphers,
 * this will also contain whatever state information is necessary to
 * allow the stream to continue to encrypt or decrypt data.
 *
 * MAC key
 *      The MAC key for this connection, as generated above.
 *
 * sequence number
 *      Each connection state contains a sequence number, which is
 * maintained separately for read and write states. The sequence
 * number MUST be set to zero whenever a connection state is made the
 * active state. Sequence numbers are of type uint64 and may not
 * exceed 2^64-1. Sequence numbers do not wrap. If a TLS
 * implementation would need to wrap a sequence number, it must
 * renegotiate instead. A sequence number is incremented after each
 * record: specifically, the first record transmitted under a
 * particular connection state MUST use sequence number 0.
 *
 *
 * Created by lvtu on 2017/7/4.
 */
package com.als.framework.protocol.recordprotocol;