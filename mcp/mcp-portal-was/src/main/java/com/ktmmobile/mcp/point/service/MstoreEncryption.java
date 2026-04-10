package com.ktmmobile.mcp.point.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class MstoreEncryption {

	public static final Logger log = LoggerFactory.getLogger(MstoreEncryption.class);
	private static final String SECRET_KEY = "(')jz~)mR(&Wf3@EHmce'Zsg8UW_glZ("; // 이제너두 제공 시크릿 키 설정.

	public String decode(String str){
		String decryptedText = "";
		try{
			decryptedText = MstoreAES256.decrypt(str.replaceAll(" ", "+"),SECRET_KEY);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return decryptedText;
	}

	public static String encode(String str) {
		String encryptedText = "";
		try{
			encryptedText = MstoreAES256.encrypt(str, SECRET_KEY);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return encryptedText;
	}

//	public static void main(String args[]) throws UnsupportedEncodingException
//	{
//		MstoreEncryption e = new MstoreEncryption();
//		String plainText 			= "testcitestcitestcitestcitestcitestcitestcitestcitestcitestcitestcitestcitestcitestcitest";
//		String encryptedText 		= e.encode(plainText);
//		String decryptedText 		= e.decode(encryptedText);
//
//		log.info("+------------------------------------------+");
//		log.info("|                Encryption                |");
//		log.info("+------------------------------------------+");
//		log.info("| plainText      [" + plainText + "]");
//		log.info("| encryptedText  [" + encryptedText + "]");
//		log.info("| decryptedText  [" + decryptedText + "]");
//		log.info("+------------------------------------------+");
//
//	}
}
