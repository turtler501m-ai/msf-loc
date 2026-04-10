package com.ktmmobile.mcp.common.util;


import static com.ktmmobile.mcp.common.constant.CacheConstants.CACHE_CODE;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

import com.ktmmobile.mcp.common.cache.DbCacheHandler;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;

public class NmcpServiceUtils {

    private static final Logger logger = LoggerFactory.getLogger(NmcpServiceUtils.class);

    public final static String MNP800_GROUP_ID  		= "MNP800"; // MNP800 그룹 ID
    public final static String MNP800_DTL_CD      		= "01"; // MNP800 상세 CD


    /**
     * <pre>
     * 설명     :  코드명을 조회
     * @param grpCd 그룹코드
     * @param dtlCd 상세코드
     * @return
     * @return: NmcpCdDtlDto
     * </pre>
     */
    public static NmcpCdDtlDto getCodeNmDto(String grpCd, String dtlCd) {

        NmcpCdDtlDto result = null;

        List<NmcpCdDtlDto> codeList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        Map<String, List<NmcpCdDtlDto>> codeMap = (Map<String, List<NmcpCdDtlDto>>) dbCacheHandler.getValue(CACHE_CODE);
        if (codeMap != null) {
            codeList = codeMap.get(grpCd);
            if (codeList != null) {
                for (NmcpCdDtlDto bean : codeList) {
                    if (bean.getDtlCd().equals(dtlCd)) {
                        return bean;
                    }
                }
            }
        }
        return null;
    }

    public static <T> T getBean( Class<T> calssType) {
        WebApplicationContext applicationContext = ContextLoaderListener.getCurrentWebApplicationContext();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(applicationContext.getServletContext(),FrameworkServlet.SERVLET_CONTEXT_PREFIX +"appServlet");
        return context.getBean(calssType);
    }
    
    /**
     * <pre>
     * 설명     :  코드명을 조회
     * @param grpCd 그룹코드
     * @param dtlCd 상세코드
     * @return
     * @return: NmcpCdDtlDto
     * </pre>
     */
    public static NmcpCdDtlDto getCodeNmObj(NmcpCdDtlDto nmcpCdDtlDto) {
        List<NmcpCdDtlDto> codeList = null;
        DbCacheHandler dbCacheHandler = getBean(DbCacheHandler.class);

        @SuppressWarnings("unchecked")
        Map<String, List<NmcpCdDtlDto>> codeMap = (Map<String, List<NmcpCdDtlDto>>) dbCacheHandler.getValue(CACHE_CODE);
        if (codeMap != null) {
            codeList = codeMap.get(nmcpCdDtlDto.getCdGroupId());
            if (codeList != null) {
                for (NmcpCdDtlDto bean : codeList) {
                    if (bean.getDtlCd().equals(nmcpCdDtlDto.getDtlCd())) {
                        return bean;
                    }
                }
            }
        }
        return null;
    }


}
