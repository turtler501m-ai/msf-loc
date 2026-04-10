package com.ktis.msp.voc.giftvoc.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.voc.giftvoc.mapper.GiftVocMapper;
import com.ktis.msp.voc.giftvoc.vo.GiftPayStatVo;
import com.ktis.msp.voc.giftvoc.vo.GiftVocVo;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GiftVocService extends BaseService {

    @Autowired
    private MaskingService maskingService;

    @Autowired
    private GiftVocMapper giftVocMapper;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;


    /** 사은품 VOC 리스트 조회 */
    public List<?> getGiftVocList(GiftVocVo searchVO, Map<String, Object> paramMap) throws MvnoServiceException {
        // 유효성 검사
        if (KtisUtil.isEmpty(searchVO.getSearchGbn())) {
            if (KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt())) {
                throw new MvnoServiceException("접수일자는 필수값 입니다.");
            }
        }
        List<EgovMap> list = giftVocMapper.getGiftVocList(searchVO);
        HashMap<String, String> maskFields = getMaskFields();
        maskingService.setMask(list, maskFields, paramMap);
        return list;
    }

    /** 사은품 VOC 상세 조회 */
    public GiftVocVo getGiftVocDtl(GiftVocVo searchVO, Map<String, Object> paramMap) throws MvnoServiceException {
        GiftVocVo vocDtl = giftVocMapper.getGiftVocDtl(searchVO);
        HashMap<String, String> maskFields = getMaskFields();
        maskingService.setMask(vocDtl, maskFields, paramMap);
        return vocDtl;
    }

    /** 사은품 VOC 수정 */
    @Transactional(rollbackFor=Exception.class)
    public void updateGiftVoc(GiftVocVo searchVO) {

        GiftVocVo vocInfo = giftVocMapper.getGiftVocDtl(searchVO);
        if(vocInfo == null){
            throw new MvnoRunException(-1, "잘못된 파라미터입니다.");
        }

        // 답변 상태가 답변완료, 지급불가, 지급예정 인 경우 수정 불가
        if ("D".equals(vocInfo.getAnsStat()) || "E2".equals(vocInfo.getAnsStat()) || "E3".equals(vocInfo.getAnsStat())) {
            throw new MvnoRunException(-1, "답변 상태가 답변완료, 지급불가, 지급예정 인 경우에는 수정이 불가합니다.");
        }

        if (giftVocMapper.updateGiftVoc(searchVO) < 1) {
            throw new MvnoRunException(-1, "수정 중 오류가 발생하였습니다.<br>잠시 후 이용바랍니다.");
        }
    }

    /** 사은품 VOC 답변 수정 */
    @Transactional(rollbackFor=Exception.class)
    public void updateGiftVocAns(GiftVocVo searchVO) {

        GiftVocVo vocInfo = giftVocMapper.getGiftVocDtl(searchVO);
        if(vocInfo == null){
            throw new MvnoRunException(-1, "잘못된 파라미터입니다.");
        }

        // VOC 상태 확인완료로 변경
        searchVO.setVocStat("D");
        if (giftVocMapper.updateGiftVocStat(searchVO) < 1) {
            throw new MvnoRunException(-1, "수정 중 오류가 발생하였습니다.<br>잠시 후 이용바랍니다.");
        }

        if (giftVocMapper.updateGiftVocAns(searchVO) < 1) {
            throw new MvnoRunException(-1, "수정 중 오류가 발생하였습니다.<br>잠시 후 이용바랍니다.");
        }
    }

    /** 30일 이내 등록된 VOC 가 있는지 체크 */
    public int dupChkGiftVoc(GiftVocVo searchVO, Map<String, Object> paramMap) throws MvnoServiceException {
        return giftVocMapper.dupChkGiftVoc(searchVO);
    }

    /** 고객정보 가져오기 */
    public GiftVocVo getCustInfo(GiftVocVo searchVO, Map<String, Object> paramMap) throws MvnoServiceException {
        return giftVocMapper.getCustInfo(searchVO);
    }

    /** 사은품 VOC 등록 */
    public void insertGiftVoc(GiftVocVo searchVO) {

        GiftVocVo custInfo = giftVocMapper.getCustInfo(searchVO);
        if(custInfo == null){
            throw new MvnoRunException(-1, "잘못된 파라미터입니다.");
        }

        // VOC 등록
        if (giftVocMapper.insertGiftVoc(searchVO) < 1) {
            throw new MvnoRunException(-1, "등록 중 오류가 발생하였습니다.<br>잠시 후 이용바랍니다.");
        }
    }

    /** 사은품 지급 리스트 목록 조회 */
    public List<?> giftPayStatList(GiftPayStatVo searchVO, Map<String, Object> paramMap) throws MvnoServiceException {
        // 유효성 검사
        if (KtisUtil.isEmpty(searchVO.getSearchGbn())) {
            if (KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt())) {
                throw new MvnoServiceException("지급월은 필수값 입니다.");
            }
        }
        List<EgovMap> list = giftVocMapper.giftPayStatList(searchVO);
        HashMap<String, String> maskFields = getMaskFields();
        maskingService.setMask(list, maskFields, paramMap);
        return list;
    }

    /** 사은품 지급 리스트 엑셀등록 */
    @Transactional
    public int regGiftPayStatExcel(GiftPayStatVo vo){

        int insertCnt = 0;
        List<GiftPayStatVo> itemList = vo.getItems();
        if (KtisUtil.isEmpty(itemList)) {
            throw new MvnoRunException(-1,"등록할 데이터가 없습니다.");
        }
        for (int i = 0; i < itemList.size(); i ++){
            GiftPayStatVo item = itemList.get(i);

            GiftVocVo paramVo = new GiftVocVo();
            paramVo.setContractNum(item.getContractNum());
            GiftVocVo custInfo = giftVocMapper.getCustInfo(paramVo);
            if(custInfo == null){
                throw new MvnoRunException(-1, "[" + paramVo.getContractNum() + "] 존재하지 않는 계약번호입니다.");
            }

            item.setRegstId(vo.getRegstId());

            try {
                giftVocMapper.insertGiftPayStat(item);
                insertCnt++;
            } catch (Exception e){
                throw new MvnoRunException(-1,"엑셀업로드 중 오류가 발생했습니다.");
            }

        }

        return insertCnt;
    }

    private HashMap<String, String> getMaskFields() {
        HashMap<String, String> maskFields = new HashMap<String, String>();
        maskFields.put("custNm", "CUST_NAME");
        maskFields.put("regstNm", "CUST_NAME");
        maskFields.put("rvisnNm", "CUST_NAME");
        maskFields.put("ansProcNm", "CUST_NAME");
        maskFields.put("subLinkName", "CUST_NAME");

        maskFields.put("ctn", "MOBILE_PHO");
        maskFields.put("vocCtn", "MOBILE_PHO");
        maskFields.put("subscriberNo", "MOBILE_PHO");

        maskFields.put("userId", "SYSTEM_ID");
        maskFields.put("recommId", "SYSTEM_ID");
        maskFields.put("regstId", "SYSTEM_ID");
        maskFields.put("rvisnId", "SYSTEM_ID");
        maskFields.put("ansProcId", "SYSTEM_ID");
        maskFields.put("recommendInfo", "SYSTEM_ID");
        return maskFields;
    }
}
