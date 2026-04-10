package com.ktis.msp.org.prmtmgmt.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.prmtmgmt.mapper.EventCodePrmtMapper;
import com.ktis.msp.org.prmtmgmt.vo.EventCodePrmtVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventCodePrmtService extends BaseService {

    @Autowired
    private EventCodePrmtMapper eventCodePrmtMapper;

    @Autowired
    private MaskingService maskingService;


    /**
     * 이벤트코드 이력관리 목록 조회
     */
    public List<EgovMap> getEventCodePrmtList(EventCodePrmtVO searchVO, Map<String, Object> paramMap) throws MvnoServiceException {
        // 유효성 검사
        if (KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt())) {
            throw new MvnoServiceException("개통일자는 필수값 입니다.");
        }

        List<EgovMap> list = eventCodePrmtMapper.getEventCodePrmtList(searchVO);

        HashMap<String, String> maskFields = getMaskFields();
        maskingService.setMask(list, maskFields, paramMap);

        return list;
    }

    private HashMap<String, String> getMaskFields() {
        HashMap<String, String> maskFields = new HashMap<String, String>();

        maskFields.put("subLinkName", "CUST_NAME");
        maskFields.put("subscriberNo", "MOBILE_PHO");
        maskFields.put("recommendInfo", "SYSTEM_ID");

        return maskFields;
    }
}
