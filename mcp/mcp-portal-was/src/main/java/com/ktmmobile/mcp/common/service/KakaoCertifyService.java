package com.ktmmobile.mcp.common.service;


import com.ktmmobile.mcp.common.dto.KakaoDto;
import org.json.JSONException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface KakaoCertifyService {
    Map<String, Object> cellKakaoCertify(KakaoDto kakaoDto) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, JSONException;
    Map<String, Object> selectCertVerify(KakaoDto kakaoDto) throws SocketTimeoutException, JSONException, InvalidAlgorithmParameterException, InvalidKeyException;
}
