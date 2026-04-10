package com.ktis.msp.org.csanalyticmgmt.service;

import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.csanalyticmgmt.mapper.AcenCnsltHistMapper;
import com.ktis.msp.org.csanalyticmgmt.vo.AcenCnsltHistVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AcenCnsltHistService {

    @Autowired
    private MaskingService maskingService;

    @Autowired
    private AcenCnsltHistMapper acenCnsltHistMapper;

    /** A'Cen 상담이력 SR 목록 조회 */
    public List<EgovMap> getAcenCnsltSrList(AcenCnsltHistVO searchVO, Map<String, Object> paramMap) {

        List<EgovMap> list = acenCnsltHistMapper.getAcenCnsltSrList(searchVO);
        HashMap<String, String> maskFields = getMaskFields();
        maskingService.setMask(list, maskFields, paramMap);
        return list;
    }

    /** A'Cen 상담이력 SR 월 통계 조회 */
    public List<EgovMap> getAcenCnsltSrStatsList(AcenCnsltHistVO searchVO) {

        List<EgovMap> list = acenCnsltHistMapper.getAcenCnsltSrStatsList(searchVO);
        return list;
    }

    private HashMap<String, String> getMaskFields() {
        HashMap<String, String> maskFields = new HashMap<String, String>();
        maskFields.put("customerNm", "CUST_NAME");
        maskFields.put("cnsltNm", "CUST_NAME");
        return maskFields;
    }

}
