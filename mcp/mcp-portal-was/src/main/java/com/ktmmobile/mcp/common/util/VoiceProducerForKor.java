package com.ktmmobile.mcp.common.util;

import nl.captcha.audio.Sample;
import nl.captcha.audio.producer.VoiceProducer;

/**
 * @Class Name : VoiceProducerForKor
 * @Description : simplecaptcha 의 한글음성 지원을 위한 구현 클래스
 * @author : ant
 * @Create Date : 2016. 3. 11.
 */
public class VoiceProducerForKor implements VoiceProducer {

	private final String server;

	private final String wavePath;

	private final String ext;

	public VoiceProducerForKor() {
		this.server = "LOCAL";
		this.wavePath = "/sounds/ko/numbers/";
		this.ext = ".wav";
	}

	public VoiceProducerForKor(String wavPath,String server) {
		this.server = server;
		this.wavePath = wavPath;
		this.ext = ".wav";
	}

	@Override
	public Sample getVocalization(char paramChar) {
		String waveFilePath = wavePath + paramChar + ext;
		if("LOCAL".equals(this.server)) {
			return nl.captcha.util.FileUtil.readSample(waveFilePath);
		}else {
			return CaptchaFileUtil.readSample(waveFilePath);
		}
	}
}
