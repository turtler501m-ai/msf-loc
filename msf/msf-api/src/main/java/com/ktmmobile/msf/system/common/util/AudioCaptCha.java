package com.ktmmobile.msf.system.common.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import nl.captcha.Captcha;
import nl.captcha.audio.AudioCaptcha;
import nl.captcha.audio.producer.VoiceProducer;
import nl.captcha.servlet.CaptchaServletUtil;

public class AudioCaptCha {

    @Deprecated
	private static final Logger logger = LoggerFactory.getLogger(AudioCaptCha.class);

	public void getAudioCaptCha(HttpServletRequest req, HttpServletResponse resp, String answer) throws IOException

    {
        HttpSession session = req.getSession();
        Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
        String getAnswer = "";
        getAnswer = answer;
        AudioCaptcha audiocaptcha = null;
        if ( getAnswer == null || "".equals(getAnswer) ) getAnswer = captcha.getAnswer();
//        String lan = req.getParameter("lan");

//       if( lan != null && "kor".equals(lan)) {
           // VoiceProducer vProd = new SetKorVoiceProducer(); //한글 음성을 생성해주는 객체 생성
        	VoiceProducer voiceProducerKor = new VoiceProducerForKor();
        	audiocaptcha = new AudioCaptcha.Builder()
            .addAnswer(new SetTextProducer(getAnswer))
            .addVoice(voiceProducerKor) //한글음성생성기를 AudioCaptcha에 적용
            .addNoise()
            .build();
/*        } else {
            audiocaptcha = new AudioCaptcha.Builder()
            .addAnswer(new SetTextProducer(getAnswer))
            .addNoise()
            .build();
        }*/
//      String agent = req.getParameter("agent"); //브라우저마다 응답을 달리해야할경우 이용.
        CaptchaServletUtil.writeAudio(resp, audiocaptcha.getChallenge());
    }
}
