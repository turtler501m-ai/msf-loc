//package com.ktmmobile.msf.system.common.util;
//
//import java.io.UnsupportedEncodingException;
//import JKTFCrypto.JKTFCrypto;
//
//public class JKTFCryptoUtil {
//
//    public static String decrypt(String encdata, String enckey) throws UnsupportedEncodingException {
//        JKTFCrypto Crypto = new JKTFCrypto ();
//        Crypto.setCipherAlgorithm(JKTFCrypto.CIPHER_SEED_ALGO);
//        Crypto.DecryptSessionKey(enckey);
//        byte[] decdata = Crypto.DecryptData(encdata);
//        String result = new String(decdata, "euc-kr");
//        Crypto.release();
//
//        return result;
//    }
//}
