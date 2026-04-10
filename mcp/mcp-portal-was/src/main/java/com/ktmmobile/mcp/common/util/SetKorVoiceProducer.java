package com.ktmmobile.mcp.common.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.mcp.common.controller.LoginController;

import nl.captcha.audio.Sample;
import nl.captcha.audio.producer.VoiceProducer;
import nl.captcha.util.FileUtil;

public class SetKorVoiceProducer  implements VoiceProducer  {	//기존라이브러리(simplecaptcha-1.2.1.jar) 에 한글음성(.wav)을 추가한 라이브러리(simplecaptcha-1.2.1_exp.jar) 사용

    @Deprecated
	private static final Logger logger = LoggerFactory.getLogger(SetKorVoiceProducer.class);

	private static final Map<Integer, String> DEFAULT_VOICES_MAP;
    private final Map<Integer, String> _voices;

    static {
        DEFAULT_VOICES_MAP = new HashMap<Integer, String>();
        StringBuilder sb;

        for (int i = 0; i < 10; i++) {
            //sb = new StringBuilder("/jboss/nfs/mportal/default/resources/sounds/ko/numbers/");
//        	sb = new StringBuilder("\\com\\ktmmobile\\mcp\\common\\sounds\\ko\\numbers\\");
//        	sb = new StringBuilder("/com/ktmmobile/mcp/common/sounds/ko/numbers/");
//        	sb = new StringBuilder("/sounds/en/numbers/");
//        	sb = new StringBuilder(dir + "\\");

//        	String dir = ((ServletRequestAttributes) RequestContextHolder
//                    .currentRequestAttributes()).getRequest().getSession().getServletContext().getRealPath("/WEB-INF/classes/com/ktmmobile/mcp/common/sounds/ko/numbers/");
////
//        	sb = new StringBuilder(dir + "\\");
//        	sb = new StringBuilder("/com/ktmmobile/mcp/common/sounds/ko/numbers/");
        	sb = new StringBuilder("/com/ktmmobile/mcp/common/sounds/ko/numbers/");
        	sb = new StringBuilder("com").append(File.separator).append("ktmmobile").append(File.separator).append("mcp");
        	sb.append(File.separator).append("common").append(File.separator).append("sounds");
        	sb.append(File.separator).append("ko").append(File.separator).append("numbers").append(File.separator);
            sb.append(i);
            sb.append(".wav");

            DEFAULT_VOICES_MAP.put(i, sb.toString());
        }
    }


    public SetKorVoiceProducer() {
        this(DEFAULT_VOICES_MAP);
    }

    public SetKorVoiceProducer(Map<Integer, String> voices) {
        _voices = voices;
    }

    @Override
    public Sample getVocalization(char num) {
       try {
    	   Integer.parseInt(num + "");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Expected <num> to be a number, got '" + num + "' instead.",e);
        }

        int idx = Integer.parseInt(num + "");
        String filename = _voices.get(idx);

        File file = new File(filename);

        return FileUtil.readSample(filename);

    }
}
