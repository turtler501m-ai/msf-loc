package com.ktis.msp.base.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KISATEST extends SEED{

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		
//		Logger logger = LogManager.getLogger(KISATEST.class);
//		
//		// TODO Auto-generated method stub
//		SEED seed_bb = new SEED();
//		String sPlain = "11101112271510  ";	//
//		String key    = "2013070198765432";	// LG U+
//	
//		logger.info("------------------------------------------------");
//		logger.info("평문 주민번호 16자리를 암호화.");
//		logger.info("-----------------------------------------------");
//		
//		logger.info("PLAIN TEXT INPUT=["+sPlain+"]- len["+sPlain.length() + "]");
//		logger.info("CYPER KEY       =["+key+"]- len["+key.length() + "]");
//		
//		byte[] tempb = sPlain.getBytes();
//		
//		logger.info("-------------------");
//		logger.info("ENCRYPT STEP START.");
//		logger.info("-------------------");
//		
//		
//		logger.info("PLAIN TEXT  byte[]=[");
//		
//		for (int i=0; i<sPlain.length(); i++){
//		//for (int i=0; i<16; i++){
//			//logger.info(Integer.toHexString(0xff&tempb[i])+" ");
//			logger.info(tempb[i]+" ");
//		}
//		logger.info("]\n");
//		
//		//--------------------------------------------------//
//		// 암호화 모듈 호출  //
//		byte[] pbCipher = seed_bb.Encrypt(sPlain, key);		//
//		//--------------------------------------------------//
//		
//		StringBuffer sb = new StringBuffer();
//		
//		
//		logger.info("CIPHER      byte[]=[");
//		for (int i=0; i<pbCipher.length; i++){
//		//for (int i=0; i<16; i++){	
//			logger.info(seed_bb.appendLPad(Integer.toHexString(0xff&pbCipher[i]),2,"0") +" ");
//			sb.append(seed_bb.appendLPad(Integer.toHexString(0xff&pbCipher[i]),2,"0"));
//		}
////		sb.append("16c01887a43e993eaaa2a7438d6ad041");
//		
//		logger.info("]\n----------------\n");
//		logger.info("16진수 암호화 주민번호 현행화전문 =["+sb.toString()+"]- len[" + sb.toString().length() +"]");
//
//		logger.info("-------------------");
//		logger.info("ENCRYPT STEP END.");
//		logger.info("-------------------");
//		
//		
//		
//		
//		
////		String temp = new String(pbCipher);
//		
//		logger.info("------------------------------------------------");
//		logger.info("암호화된  주민번호 16진수   byte[]를 복호화.");
//		logger.info("-----------------------------------------------");		
//		
//		logger.info("-------------------");
//		logger.info("DECRYPT STEP START.");
//		logger.info("-------------------");
//		
//		//--------------------------------------------------//
//		// 복호화 모듈 호출  //
//		byte[] pbPlain = seed_bb.Decrypt(pbCipher, key);	//
//		//--------------------------------------------------//
//		
//		logger.info("CIPHER->PLAIN   byte[]=");
//		
//		for (int i=0; i<pbPlain.length; i++){
//			logger.info(seed_bb.appendLPad(Integer.toHexString(0xff&pbPlain[i]),2,"0")+" ");
//		}
//		
//		logger.info("]\n----------------\n");
//		
//		String temp2 = new String(pbPlain);
//		logger.info("복호화 주민번호 [" + temp2+"]- len["+temp2.length() + "]");
//		
//		if(sPlain.equals(temp2)){
//			logger.info("암호화 /복호화 주민번호 동일. ["+ sPlain +"]["+ temp2 + "]");
//		}else{
//			logger.info("암호화 /복호화 주민번호 불일치. ["+ sPlain +"]["+ temp2 + "]");
//		}
//		
//		//--------------------------------------------------//
//		//--------------------------------------------------//
//		//--------------------------------------------------//
//		logger.info("------------------------------------------------");
//		logger.info("전문으로 수신된 문자열을  byte로 변환하여 복호화 하기 ");
//		logger.info("-----------------------------------------------");
//		
//		//String sHexCipher = "9c50d37171a8d1120d30f7e40e7dd8d7";
//		String sHexCipher = sb.toString();
//		
//		
//		//암호화 된거 밖으면됨...sHexCipher
//		sHexCipher ="16c01887a43e993eaaa2a7438d6ad041";
//		
//		//--------------------------------------------------//
//		//byte[] bCipher = new byte[sHexCipher.length()/2];
//		byte[] bCipher = seed_bb.GetStringHextoByte(sHexCipher);
//		
//		logger.info("Hex Cipher TEXT =[" + sHexCipher + "]");
//		
//				
//		logger.info("Byte Cipher byte[]=[");
//		for (int i=0; i<bCipher.length; i++){
//			//for (int i=0; i<16; i++){
//			logger.info(seed_bb.appendLPad(Integer.toHexString(0xff&bCipher[i]),2,"0")+" ");
//		}
//		logger.info("]\n");
//		
//
//		
//		logger.info("--------------------");
//		logger.info("DECRYPT STEP Second.");
//		logger.info("--------------------");
//		  
//		//--------------------------------------------------//
//		// 복호화 모듈 호출  //
//		byte[] pbPlain2 = seed_bb.Decrypt(bCipher, key);	//
//		//--------------------------------------------------//
//		
//		logger.info("CIPHER->PLAIN   byte[]=");
//		
//		for (int i=0; i<pbPlain2.length; i++){
//			logger.info(seed_bb.appendLPad(Integer.toHexString(0xff&pbPlain2[i]),2,"0")+" ");
//		}
//		
//		logger.info("]\n----------------\n");
//		
//		String temp3 = new String(pbPlain2);
//		logger.info("복호화 주민번호.  [" + temp3+"]- len["+temp3.length() + "]");
//		
//		
//	}

}
