package com.ktis.mcpif.common;

import com.ktis.mcpif.mPlatform.service.XmlParse;
import org.apache.commons.codec.binary.Base64;
import org.jdom.Element;

public class KisaSeedUtil {

	private static String SEED_KEY = "kMvno0725Kis^_is";
    private static byte[] IV_SPEC = {(byte)0x26, (byte)0x8D, (byte)0x66, (byte)0xA7, 
									(byte)0x35, (byte)0xA8, (byte)0x1A, (byte)0x81, 
									(byte)0x6F, (byte)0xBA, (byte)0xD9, (byte)0xFA, 
									(byte)0x36, (byte)0x16, (byte)0x25, (byte)0x01};

	public static String encrypt(String str) {
		try {
			if (!"".equals(str) && str != null) {
				byte[] encArr = KisaSeedCbc.SEED_CBC_Encrypt(SEED_KEY.getBytes(), IV_SPEC, str.getBytes(), 0, str.getBytes().length);
				Base64 base64 = new Base64();
				return new String(base64.encode(encArr));
			} else {
				return str;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String decrypt(String str) {
		try {
			if (!"".equals(str) && str != null) {
				Base64 base64 = new Base64();
				byte[] encArr = KisaSeedCbc.SEED_CBC_Decrypt(SEED_KEY.getBytes(), IV_SPEC, base64.decode(str), 0, base64.decode(str).length);
				return new String(encArr);
			} else {
				return str;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setDecryptText(Element parentDto, String childNm) {
		if(parentDto == null) {
			return;
		}
		Element child = new Element(childNm);
		child.setText(decryptValue(XmlParse.getChildValue(parentDto, childNm)));
		parentDto.removeChild(childNm);
		parentDto.addContent(child);
	}

	public static String encryptValue(String value, String encryptYn) {
		String result = value;
		if ("Y".equals(encryptYn)) {
			result = KisaSeedUtil.encrypt(value);
		}
		if (result == null || result == "null") {
			result = "";
		}
		return result;
	}

	private static String decryptValue(String value) {
		String result = KisaSeedUtil.decrypt(value);
		if (result == null || result == "null") {
			result = "";
		}
		return result;
	}
}
