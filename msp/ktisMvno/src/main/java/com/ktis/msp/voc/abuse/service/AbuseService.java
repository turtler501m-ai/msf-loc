package com.ktis.msp.voc.abuse.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO;
import com.ktis.msp.rcp.canCustMgmt.vo.CanBatchVo;
import com.ktis.msp.util.StringUtil;
import com.ktis.msp.voc.abuse.mapper.AbuseMapper;
import com.ktis.msp.voc.abuse.vo.AbuseVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class AbuseService extends BaseService {

    /** 마스킹 처리 서비스 */
    @Autowired
    private MaskingService maskingService;

    @Autowired
    private AbuseMapper abuseMapper;

    /**
     * 부정사용주장 단말설정 목록 조회
     */
    public List<EgovMap> getAbuseList(AbuseVO searchVO, Map<String, Object> paramMap) {

        List<EgovMap> list = abuseMapper.getAbuseList(searchVO);

        HashMap<String, String> maskFields = getMaskFields();
        maskingService.setMask(list, maskFields, paramMap);

        return list;
    }

    /**
     * 부정사용주장 단말설정 목록 엑셀 다운로드
     */
    public List<EgovMap> getAbuseInfoListExcelDown(AbuseVO searchVO, Map<String, Object> paramMap) {

        List<EgovMap> list = abuseMapper.getAbuseInfoListExcelDown(searchVO);

        HashMap<String, String> maskFields = getMaskFields();
        maskingService.setMask(list, maskFields, paramMap);

        return list;
    }

    /**
     * 부정사용주장 단말설정 등록
     */
    @Transactional(rollbackFor=Exception.class)
    public void insertAbuseInfo(AbuseVO vo) throws MvnoRunException {

        abuseMapper.insertAbuseInfo(vo);
        abuseMapper.insertAbuseInfoHist(vo);

        List<String> newImeiList = vo.getNewImeiList();
        if(newImeiList.size() == 0 || newImeiList.size() > 10) {
            throw new MvnoRunException(-1 , "imei는 최소 1건 이상 최대 10건 이하만 가능합니다.");
        }

        if(StringUtil.isEmpty(vo.getReason())){
            throw new MvnoRunException(-1 , "등록사유를 선택해 주세요.");
        }

        if("09".equals(vo.getReason()) && StringUtil.isEmpty(vo.getReasonDesc())){
            throw new MvnoRunException(-1 , "상세 사유를 입력해 주세요.");
        }

        for (String imei : newImeiList) {
            this.validateImei(imei);
            AbuseVO imeiVo = new AbuseVO();
            imeiVo.setSessionUserId(vo.getSessionUserId());
            imeiVo.setImei(imei);
            imeiVo.setMstSeq(vo.getMstSeq());
            abuseMapper.insertAbuseImeiInfo(imeiVo);
        }
    }


    /**
     * 부정사용주장 imei 목록 조회
     */
    public List<EgovMap> getAbuseImeiList(AbuseVO searchVO) {

        List<EgovMap> list = abuseMapper.getAbuseImeiList(searchVO);

        return list;
    }

    /**
     * 부정사용주장 단말설정 수정
     */
    @Transactional(rollbackFor=Exception.class)
    public void updateAbuseInfo(AbuseVO vo) throws MvnoRunException {

        List<String> newImeiList = vo.getNewImeiList();
        List<String> orgImeiList = vo.getOrgImeiList();

        if(newImeiList.size() + orgImeiList.size() == 0 || newImeiList.size() + orgImeiList.size() > 10) {
            throw new MvnoRunException(-1 , "imei는 최소 1건 이상 최대 10건 이하만 가능합니다.");
        }

        if(StringUtil.isEmpty(vo.getReason())){
            throw new MvnoRunException(-1 , "등록사유를 선택해 주세요.");
        }

        if("09".equals(vo.getReason()) && StringUtil.isEmpty(vo.getReasonDesc())){
            throw new MvnoRunException(-1 , "상세 사유를 입력해 주세요.");
        }

        abuseMapper.updateAbuseInfo(vo);
        abuseMapper.insertAbuseInfoHist(vo);
        abuseMapper.updateAbuseOrgImei(vo);

        for (String imei : vo.getNewImeiList()) {
            this.validateImei(imei);
            AbuseVO imeiVo = new AbuseVO();
            imeiVo.setSessionUserId(vo.getSessionUserId());
            imeiVo.setMstSeq(vo.getMstSeq());
            imeiVo.setImei(imei);
            abuseMapper.insertAbuseImeiInfo(imeiVo);
        }
    }

    private void validateImei(String imei) throws MvnoRunException {
        if (StringUtils.isEmpty(imei)) {
            throw new MvnoRunException(-1 , "[imei : " + imei + "]imei를 입력해주세요");
        }

        boolean result = Pattern.matches("^[0-9]*$", imei);
        if(!result){
            throw new MvnoRunException(-1 , "[imei : " + imei + "]imei는 숫자만 입력 가능합니다.");
        }
        
        if(imei.length() != 15){
            throw new MvnoRunException(-1 , "[imei : " + imei + "]imei 15자리를 입력해 주세요.");
        }
    }

    public List<?> getContractInfo(AbuseVO vo) {

        // 계약정보조회
        List<?> list = abuseMapper.getContractInfo(vo);

        HashMap<String, String> maskFields = getMaskFields();
        Map<String, Object> pReqParamMap = new HashMap<String, Object>();
        pReqParamMap.put("SESSION_USER_ID", vo.getSessionUserId());

        maskingService.setMask(list, maskFields, pReqParamMap);

        return list;
    }

    private HashMap<String, String> getMaskFields() {
        HashMap<String, String> maskFields = new HashMap<String, String>();
        maskFields.put("subLinkName","CUST_NAME");
        maskFields.put("regstNm","CUST_NAME");
        maskFields.put("rvisnNm","CUST_NAME");
        maskFields.put("subscriberNo","MOBILE_PHO");
        maskFields.put("fstUsimNo",	"USIM");
        maskFields.put("usimNo", "USIM");
        maskFields.put("iccId", "USIM");

        return maskFields;
    }
}
