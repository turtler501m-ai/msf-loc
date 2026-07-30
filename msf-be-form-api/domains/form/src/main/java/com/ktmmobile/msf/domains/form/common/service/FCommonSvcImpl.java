package com.ktmmobile.msf.domains.form.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.cache.DbCacheHandler;
import com.ktmmobile.msf.domains.form.common.dao.FCommonDao;
import com.ktmmobile.msf.domains.form.common.dto.CdGroupBean;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;

import static com.ktmmobile.msf.domains.form.common.constants.CacheConstants.CACHE_CODE;

@Service
public class FCommonSvcImpl implements FCommonSvc {

    @Autowired
    private FCommonDao fCommonDao;

    private DbCacheHandler dbCacheHandler;


    //캐시-공통코드
    @Override
    public void getCodeCahe() {
        List<CdGroupBean> list = fCommonDao.getCodeAllList();
        Map<String, List<NmcpCdDtlDto>> codeMap = new HashMap<>();
        //Map<String, Map<String, String>> codeValueMap = new HashMap<String, Map<String, String>>();

        for (CdGroupBean cdGroupBean: list) {
            //Map<String, String> code = new HashMap<String, String>();

            //for(NmcpCdDtlDto cdBean : cdGroupBean.getListCdBean()){
            //    code.put(cdBean.getDtlCd(), cdBean.getDtlCdNm());
            //}
            //codeValueMap.put(cdGroupBean.getCdGroupId(), code);
            codeMap.put(cdGroupBean.getCdGroupId(), cdGroupBean.getListCdBean());
        }

        if (dbCacheHandler.getElement(CACHE_CODE) == null) {
            dbCacheHandler.put(CACHE_CODE, codeMap);
        } else {
            dbCacheHandler.replace(CACHE_CODE, codeMap);
        }
        //        if(dbCacheHandler.getElement(CACHE_CODE_VALUE) == null) {
        //            dbCacheHandler.put(CACHE_CODE_VALUE, codeValueMap);
        //        } else {
        //            dbCacheHandler.replace(CACHE_CODE_VALUE, codeValueMap);
        //        }
    }

    @Override
    public List<NmcpCdDtlDto> getCodeList(NmcpCdDtlDto nmcpCdDtlDto) {
        return fCommonDao.getCodeList(nmcpCdDtlDto);
    }

    @Override
    public NmcpCdDtlDto getCodeNm(NmcpCdDtlDto nmcpCdDtlDto) {
        return fCommonDao.getCodeNm(nmcpCdDtlDto);
    }


    @Override
    public MspRateMstDto getMspRateMst(String rateCd) {
        return fCommonDao.getMspRateMst(rateCd);
    }

    /**
     * <pre>
     * 설명 : 상세코드 조회 (사용여부/기간 조건없이 조회)
     * @param cdGroupId
     * @return List<NmcpCdDtlDto>
     * </pre>
     */
    @Override
    public List<NmcpCdDtlDto> getAllDtlCdList(String cdGroupId) {
        return fCommonDao.getAllDtlCdList(cdGroupId);
    }
}
