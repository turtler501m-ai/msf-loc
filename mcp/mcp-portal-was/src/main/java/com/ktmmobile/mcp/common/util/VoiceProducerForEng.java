package com.ktmmobile.mcp.common.util;

import nl.captcha.audio.Sample;
import nl.captcha.audio.producer.VoiceProducer;

/**
 * @Class Name : VoiceProducerForKor
 * @Description : simplecaptcha 의 한글음성 지원을 위한 구현 클래스
 * @author : ant
 * @Create Date : 2016. 3. 11.
 */
public class VoiceProducerForEng implements VoiceProducer {

    private final String wavePath;

    private final String ext;

    public VoiceProducerForEng() {
        this.wavePath = "/sounds/en/numbers/";
        this.ext = ".wav";
    }

    @Override
    public Sample getVocalization(char paramChar) {
        String waveFilePath = wavePath + paramChar +"-alex" +ext;
        return nl.captcha.util.FileUtil.readSample(waveFilePath);
    }
}
