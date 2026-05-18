package com.ktmmobile.msf.domains.form.form.servicechange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionAvailableResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionMyListResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionPreCheckReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionPreCheckResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfRegSvcServiceImpl;

@RestController
public class MsfRegSvcController {

    @Autowired
    private MsfRegSvcServiceImpl regSvcService;

    /**
     * 이용중 부가서비스 목록 조회
     *
     * M플랫폼 X97(가입중인 부가서비스 조회) 호출 후
     * MSP_RATE_MST@DL_MSP에서 해지 가능 여부(onlineCanYn) / 해지 안내 문구(canCmnt) 세팅.
     * "PL249Q800"(아무나SOLO 더미 SOC) 자동 제거.
     *
     * ASIS: POST /mypage/myAddSvcListAjax.do (X97 기반, 세션 검증 포함)
     * TOBE: POST /api/v1/addition/my-list (Stateless, 요청 바디에서 ncn/ctn/custId 수신)
     *
     * @param req ncn(계약번호), ctn(전화번호), custId(고객번호)
     * @return 이용중 부가서비스 목록 (AdditionMyListResVO.list)
     */

    @PostMapping("/api/form/servicechange/myaddsvclist")
    public CommonResponse<FormResponse<AdditionMyListResVO>> myAddSvcList(@RequestBody AdditionReqDto req) {
        return ResponseUtils.ok(regSvcService.myAddSvcList(req));
    }

    /**
     * 가입가능 부가서비스 목록 조회
     *
     * M플랫폼 X97로 현재 이용중인 SOC 목록을 추출하고,
     * MSF DB(MSF_REG_SVC_MST 등)에서 관리 중인 전체 부가서비스와 비교하여 useYn 세팅.
     * listA(유료) / listC(무료 및 번들) 분리 반환.
     *
     * ASIS: POST /mypage/addSvcListAjax.do (X20 기반 → TOBE에서 X97로 교체)
     * TOBE: POST /api/v1/addition/available-list
     *
     * @param req ncn(계약번호), ctn(전화번호), custId(고객번호)
     * @return 가입가능 부가서비스 목록 (list/listA/listC)
     */
    @PostMapping("/api/form/servicechange/availablelist")
    public CommonResponse<FormResponse<AdditionAvailableResVO>> addSvcList(@RequestBody AdditionReqDto req) {
        return ResponseUtils.ok(regSvcService.selectAddSvcInfoDto(req));
    }

    /**
     * 부가서비스 가입/해지 사전체크
     *
     * 해지 건: MSP_RATE_MST 온라인 해지 가능 여부 사전 검증 후 M플랫폼 Y24 호출.
     * 가입 건: M플랫폼 Y24 직접 호출.
     *
     * ASIS: POST /mypage/moscPrdcTrtmPreChkAjax.do
     * TOBE: POST /api/form/servicechange/moscPrdcTrtmPreChk
     *
     * @param req ncn/ctn/custId/actCode/prdcList(상품목록 — prdcCd/prdcSbscTrtmCd/prdcTypeCd/prdcSeqNo)
     * @return 사전체크 결과 (resCode/resData)
     */
    @PostMapping("/api/form/servicechange/moscPrdcTrtmPreChk")
    public CommonResponse<FormResponse<AdditionPreCheckResVO>> moscPrdcTrtmPreChk(@RequestBody AdditionPreCheckReqDto req) {
        return ResponseUtils.ok(regSvcService.moscPrdcTrtmPreChk(req));
    }

    /**
     * 로밍 서브상품 신청 시 대표상품 일련번호 조회
     *
     * 대표 전화번호(mtPhone)로 cntrListNoLogin을 통해 계약정보를 취득하고,
     * X97로 대표회선의 이용중 부가서비스 목록을 조회하여 대표상품의 prodHstSeq를 반환.
     * 서브상품 신청 시 ftrNewParam에 포함할 mtProdHstSeq를 확인하는 용도.
     *
     * ASIS: RateAdsvcGdncServiceImpl.getMtProdHstSeq()
     * TOBE: POST /api/form/servicechange/roaming/mainProdHstSeq
     *
     * @param req ncn(신청자 계약번호)/mtPhone/mtCd/strtDt/endDt
     * @return mtProdHstSeq(대표상품 일련번호), mtNcn(대표 계약번호)
     */
    @PostMapping("/api/form/servicechange/roaming/mainProdHstSeq")
    public CommonResponse<FormResponse<AdditionApplyResVO>> getRoamingMainProdHstSeq(@RequestBody AdditionApplyReqDto req) {
        return ResponseUtils.ok(regSvcService.getMtProdHstSeq(req));
    }

}
