package com.ktis.msp.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktis.msp.base.exception.MvnoErrorException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class StringUtil extends StringUtils{
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

	public static boolean chkString(String t) {
		boolean bRtn = false;
		
		if(t.indexOf('>') > -1) {
			bRtn = true;
		}
		if(t.indexOf('<') > -1) {
			bRtn = true;
		}
		
		return bRtn;
	}
	
	public boolean chkContinuePass(String pwd) {
		int cnt = 0;
		int maxCnt = 0;
		
		for(int i=0; i < pwd.length()-1; i++) {
			if(pwd.charAt(i) == (pwd.charAt(i+1)-1)) {
				cnt++;
			} else {
				if(cnt > maxCnt) {
					maxCnt = cnt;
				}
				
				cnt = 0;
			}
		}
		
		if (cnt > maxCnt) {
			maxCnt = cnt;
		}
		
		if (maxCnt > 1) {
			return false;
		} else {
			cnt = 0;
			maxCnt = 0;
		}
		
		for(int i=0; i < pwd.length()-1; i++) {
			if(pwd.charAt(i) == (pwd.charAt(i+1)+1)) {
				cnt++;
			} else {
				if(cnt > maxCnt) {
					maxCnt = cnt;
				}
				
				cnt = 0;
			}
		}
		
		if (cnt > maxCnt) {
			maxCnt = cnt;
		}
				
		return maxCnt < 2;
	}
	
	public boolean chkSamePass(String pwd) {
		int cntSame = 0;
		int maxCnt = 0;
				
		for(int i=0; i < pwd.length()-1; i++) {
			if(pwd.substring(i,(i+1)).equals(pwd.substring((i+1),(i+2)))) {
				cntSame++;
			} else {
				if(cntSame > maxCnt) {
					maxCnt = cntSame;
				}
				
				cntSame = 0;
			}
		}
		
		if(cntSame > maxCnt) {
			maxCnt = cntSame;
		}
		
		return maxCnt < 2;
	}
	
	public boolean chkKeyboardContinuePass(String pwd) {
		String keyword = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm|"
				+ "poi|oiu|iuy|uyt|ytr|tre|rew|ewq|lkj|kjh|jhg|hgf|gfd|fds|dsa|mnb|nbv|bvc|vcx|cxz";
		String[] keywordList = keyword.split("\\|");
				
		for(int i=0; i < keywordList.length; i++) {
			if(pwd.toLowerCase().matches(".*" + keywordList[i] + ".*")) {
				return false;
			}
		}
		
		return true;
	}
	
	public static String decode(String str1, String str2, String ifStr, String elseStr){
		String result = str1;
		try{
			if( StringUtil.equalsIgnoreCase(str1, str2) ) result = ifStr;
			else result = elseStr;
		}catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		return result;
	}

	public static String decode(boolean flag, String ifStr, String elseStr){
		String result = ifStr;
		try{
			if(flag) result = ifStr;
			else result = elseStr;
		}catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		return result;
	}

    public static String generateNumbers(int num) throws NoSuchAlgorithmException {
        int iterations;
        if (num < 1 || num > 6) {
            iterations = 6;
        } else {
            iterations = num;
        }

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
