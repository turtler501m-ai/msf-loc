package com.ktmmobile.mcp.rate;

import com.ktmmobile.mcp.common.util.NmcpServiceUtils;

import java.io.File;

import static com.ktmmobile.mcp.common.util.NmcpServiceUtils.getLatestRateAdsvcGdncVersion;

public class RateAdsvcGdncUtil {

    public static String getRateAdsvcGdncPath(String fileUploadBase, String serverName) {
        // LOCAL 은 별도 DB가 존재하지 않아 버전 생성 어려워 기존 경로에 생성, 조회
        if ("LOCAL".equals(serverName)) {
            return fileUploadBase + File.separator + "rateAdsvc";
        }

        String isVersionedRateGdnc = NmcpServiceUtils.getCodeNm("Constant", "isVersionedRateGdnc");
        if ("Y".equals(isVersionedRateGdnc)) {
            return fileUploadBase + File.separator + "rateAdsvcVer" + File.separator + getLatestRateAdsvcGdncVersion();
        } else {
            return fileUploadBase + File.separator + "rateAdsvc";
        }
    }
}
