package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formComm.dto.ConcurrentCheckReqDto;
import com.ktmmobile.msf.formComm.dto.ConcurrentCheckResVO;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseCategoryDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseCheckReqDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseCheckResVO;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseOptionDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseTargetResVO;
import com.ktmmobile.msf.formSvcChg.mapper.SvcChgBaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 서비스변경 기본 서비스 구현.
 */
@Service
public class SvcChgBaseSvcImpl implements SvcChgBaseSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgBaseSvcImpl.class);

    @Autowired
    private SvcChgBaseMapper svcChgBaseMapper;

    /**
     * 서비스변경 업무 목록 조회.
     * MSF_CD_GROUP_BAS + MSF_CD_DTL 조회. DB 없으면 Mock 데이터 반환.
     */
    @Override
    public SvcChgBaseTargetResVO getChangeTargets() {
        SvcChgBaseTargetResVO res = new SvcChgBaseTargetResVO();
        List<SvcChgBaseCategoryDto> categories;

        try {
            categories = svcChgBaseMapper.selectSvcChgCategories();
            for (SvcChgBaseCategoryDto cat : categories) {
                List<SvcChgBaseOptionDto> options = svcChgBaseMapper.selectSvcChgOptions(cat.getGroupCd());
                cat.setOptions(options);
            }
            logger.debug("[Base] 서비스변경 카테고리 조회 완료: {}개 카테고리", categories.size());
        } catch (Exception e) {
            logger.warn("[Base] 서비스변경 업무 코드 DB 조회 실패, Mock 데이터 반환: {}", e.getMessage());
            categories = getMockCategories();
        }

        res.setCategories(categories);
        // 프론트엔드 ChangeTypeCust.vue 용 categoryList + concurrentGroups 변환
        res.setCategoryList(buildCategoryList(categories));
        res.setConcurrentGroups(buildConcurrentGroups(categories));
        return res;
    }

    /**
     * DB categories → 프론트엔드 categoryList 형식으로 변환.
     * { category: {id, label}, options: [{id, label, concurrentBlockYn, imagingYn}] }
     */
    private List<Map<String, Object>> buildCategoryList(List<SvcChgBaseCategoryDto> categories) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SvcChgBaseCategoryDto cat : categories) {
            Map<String, Object> item = new LinkedHashMap<>();
            Map<String, String> catRef = new LinkedHashMap<>();
            catRef.put("id", cat.getGroupCd());
            catRef.put("label", cat.getGroupNm());
            item.put("category", catRef);
            List<Map<String, Object>> opts = new ArrayList<>();
            if (cat.getOptions() != null) {
                for (SvcChgBaseOptionDto opt : cat.getOptions()) {
                    Map<String, Object> optRef = new LinkedHashMap<>();
                    optRef.put("id", opt.getDtlCd());
                    optRef.put("label", opt.getDtlCdNm());
                    optRef.put("concurrentAvailYn", opt.getConcurrentAvailYn());
                    optRef.put("imagingYn", opt.getImagingYn());
                    opts.add(optRef);
                }
            }
            item.put("options", opts);
            list.add(item);
        }
        return list;
    }

    /**
     * concurrentBlockYn='Y' 인 옵션 ID들을 하나의 그룹으로 묶어 반환.
     * 프론트: res.concurrentGroups.flat() → coreChangeCodes
     */
    private List<List<String>> buildConcurrentGroups(List<SvcChgBaseCategoryDto> categories) {
        List<String> concurrentIds = new ArrayList<>();
        for (SvcChgBaseCategoryDto cat : categories) {
            if (cat.getOptions() != null) {
                for (SvcChgBaseOptionDto opt : cat.getOptions()) {
                    if ("N".equals(opt.getConcurrentAvailYn())) {
                        concurrentIds.add(opt.getDtlCd());
                    }
                }
            }
        }
        List<List<String>> groups = new ArrayList<>();
        if (!concurrentIds.isEmpty()) groups.add(concurrentIds);
        return groups;
    }

    @Override
    public SvcChgBaseCheckResVO validateServiceCheck(SvcChgBaseCheckReqDto req) {
        if (req == null || req.getSelectedCodes() == null || req.getSelectedCodes().isEmpty()) {
            return SvcChgBaseCheckResVO.fail("서비스 코드를 선택해 주세요.");
        }
        return SvcChgBaseCheckResVO.ok();
    }

    /**
     * 동시처리 불가 체크. ASIS 기준 불가 조합 검증.
     * 번호변경 + 분실복구/일시정지해제/요금상품변경/기변 동시 불가.
     */
    @Override
    public ConcurrentCheckResVO concurrentCheck(ConcurrentCheckReqDto req) {
        if (req == null || req.getSelectedCodes() == null) {
            return ConcurrentCheckResVO.ok();
        }
        List<String> codes = req.getSelectedCodes();
        // 동시선택 불가 코드: RATE_CHANGE, NUM_CHANGE, LOST_RESTORE (화면설계서 기준)
        List<String> coreChangeCodes = Arrays.asList("RATE_CHANGE", "NUM_CHANGE", "LOST_RESTORE");
        long coreCount = codes.stream().filter(coreChangeCodes::contains).count();
        if (coreCount >= 2) {
            return ConcurrentCheckResVO.fail("동시 처리 불가능한 업무가 있습니다. 요금제 변경, 번호변경, 분실복구/일시정지해제 신청은 동시에 선택할 수 없습니다.");
        }
        return ConcurrentCheckResVO.ok();
    }

    /** DB 없는 로컬 개발용 Mock 데이터. SVC_TGT_CD 공통코드 구조와 동일하게 유지. */
    private List<SvcChgBaseCategoryDto> getMockCategories() {
        List<SvcChgBaseCategoryDto> list = new ArrayList<>();
        SvcChgBaseCategoryDto cat = makeCategory("SVC_TGT_CD", "서비스변경 대상 업무코드", "00", 0,
            Arrays.asList(
                // concurrentAvailYn: Y=동시변경가능, N=동시변경불가
                // imagingYn: Y=별도신청서 이미지처리
                makeOption("WIRELESS_BLOCK", "무선데이터차단 서비스",        "Y", "Y",  1),
                makeOption("INFO_LIMIT",     "정보료 상한금액 설정/변경",    "Y", "Y",  2),
                makeOption("ADDITION",       "부가서비스 신청/변경",         "Y", "Y",  3),
                makeOption("RATE_CHANGE",    "요금제 변경",                  "N", "Y",  4),
                makeOption("INSURANCE",      "단말보험 가입",                "Y", "Y",  5),
                makeOption("ANY_SOLO",       "아무나 SOLO 결합",             "Y", "Y",  6),
                makeOption("DATA_SHARING",   "데이터쉐어링 가입/해지",       "Y", "Y",  7),
                makeOption("LOST_RESTORE",   "분실복구/일시정지해제 신청",   "N", "Y",  8),
                makeOption("USIM_CHANGE",    "USIM 변경",                    "Y", "Y",  9),
                makeOption("NUM_CHANGE",     "번호변경",                     "N", "Y", 10)
            ));
        list.add(cat);
        return list;
    }

    private SvcChgBaseCategoryDto makeCategory(String groupCd, String groupNm, String svcTgtCd, int sortSeq,
                                               List<SvcChgBaseOptionDto> options) {
        SvcChgBaseCategoryDto cat = new SvcChgBaseCategoryDto();
        cat.setGroupCd(groupCd);
        cat.setGroupNm(groupNm);
        cat.setSvcTgtCd(svcTgtCd);
        cat.setSortSeq(sortSeq);
        cat.setOptions(options);
        return cat;
    }

    private SvcChgBaseOptionDto makeOption(String dtlCd, String dtlCdNm, String concurrentAvailYn,
                                           String imagingYn, int sortSeq) {
        SvcChgBaseOptionDto opt = new SvcChgBaseOptionDto();
        opt.setDtlCd(dtlCd);
        opt.setDtlCdNm(dtlCdNm);
        opt.setConcurrentAvailYn(concurrentAvailYn);
        opt.setImagingYn(imagingYn);
        opt.setSortSeq(sortSeq);
        return opt;
    }
}
