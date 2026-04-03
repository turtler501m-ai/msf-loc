package com.ktmmobile.msf.system.common.util;

import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.ktmmobile.msf.system.common.exception.McpCommonException;

import nl.captcha.audio.Sample;

    public class CaptchaFileUtil {

    public static final InputStream readResource(String filename) {

        //InputStream jarIs = CaptchaFileUtil.class.getResourceAsStream(filename);
        InputStream jarIs = null;

        try {
            jarIs = new FileInputStream(new File(filename));
        } catch (FileNotFoundException e1) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }


        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        byte[] data = new byte[16384];
        int nRead;

        try {
            while ((nRead = jarIs.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            jarIs.close();
        } catch (IOException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        } finally {
            if(jarIs != null) {
                try {
                    jarIs.close();
                } catch (IOException e) {
                    throw new McpCommonException(COMMON_EXCEPTION);
                }
            }
        }

        return new ByteArrayInputStream(buffer.toByteArray());
    }

    public static final Sample readSample(String filename) {
        InputStream is = readResource(filename);
        return new Sample(is);
    }
}