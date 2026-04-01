/**
 * 서비스변경 신청서 API (formSvcChg)
 * M포털 X01, X20, X21, X38 등 동일 스펙으로 백엔드(formSvcChg) 연동
 */

import { msfGet, msfPost } from './msf'
import { getAgencies as commGetAgencies } from './common'

/**
 * 대리점 목록 조회 — 업무공통 (GET /v1/comm/agencies) 위임.
 * @returns {Promise<{ success: boolean, agencies: Array<{value, label}> }>}
 */
export { commGetAgencies as getAgencies }

/**
 * X01 가입정보조회 (휴대폰 번호 인증)
 * @param {{ name: string (필수), ncn?: string, ctn: string, custId?: string }} params
 */
export async function getJoinInfo(params) {
  return msfPost('/v1/join-info', params)
}

/**
 * 서비스변경 대상 조회 (코드관리 기준, 동시처리 불가 그룹, 이미징 대상)
 */
export async function getChangeTargets() {
  return msfGet('/v1/service-change/base/targets')
}

/**
 * 서비스 체크 버튼 유효성 체크
 * @param {{ selectedCodes: string[], serviceCheckCompleted?: boolean }} params
 */
export async function validateServiceCheck(params) {
  return msfPost('/v1/service-change/base/check', params)
}

/**
 * 서비스변경 동시 처리 불가 체크
 * @param {string[]} selectedCodes - 선택한 서비스변경 업무 코드 목록
 */
export async function checkConcurrentChange(selectedCodes) {
  return msfPost('/v1/service-change/concurrent-check', { selectedCodes })
}

/**
 * X20 현재 부가서비스 조회
 * @param {{ ncn: string, ctn: string, custId?: string }} params
 */
export async function getCurrentAddition(params) {
  return msfPost('/v1/addition/current', params)
}

/**
 * X21 부가서비스 신청
 * @param {{ ncn: string, ctn: string, custId?: string, soc: string, ftrNewParam?: string }} params
 */
export async function regAddition(params) {
  return msfPost('/v1/addition/reg', params)
}

/**
 * X38 부가서비스 해지
 * @param {{ ncn: string, ctn: string, custId?: string, soc: string }} params
 */
export async function cancelAddition(params) {
  return msfPost('/v1/addition/cancel', params)
}

// ----- 요금상품 변경 (FarPriceController) -----

/**
 * 변경 가능 요금제 목록 조회 (추천요금제·요금제 조회 결과 콤보용)
 * @param {{ ncn?: string, ctn?: string, custId?: string }} params
 */
export async function getFarPriceList(params) {
  return msfPost('/v1/service-change/far-price/list', params || {})
}

/**
 * 현재 요금제 예약변경 조회 (X89)
 * @param {{ ncn?: string, ctn?: string, custId?: string }} params
 */
export async function getFarPriceReservation(params) {
  return msfPost('/v1/service-change/far-price/reservation', params || {})
}

/**
 * 요금상품 변경 처리 (즉시 / 예약 익월1일)
 * @param {{ ncn?: string, ctn?: string, custId?: string, soc: string, schedule: 'immediate'|'reserve', ftrNewParam?: string }} params
 */
export async function applyFarPriceChange(params) {
  return msfPost('/v1/service-change/far-price/change', params)
}

/**
 * 요금제 예약변경 취소 (X90)
 * @param {{ ncn?: string, ctn?: string, custId?: string }} params
 */
export async function cancelFarPriceReservation(params) {
  return msfPost('/v1/service-change/far-price/reservation/cancel', params || {})
}

/**
 * X18 실시간 사용요금 조회 — 즉시변경 확인 팝업용
 * @param {{ ncn: string, ctn: string, custId?: string }} params
 * @returns {Promise<{ success: boolean, searchTime: string, sumAmt: string, items: Array<{gubun, payment}> }>}
 */
export async function getFarPriceRemainCharge(params) {
  return msfPost('/v1/service-change/far-price/remain-charge', params)
}

// ----- 아무나 SOLO 결합 (분석 44·45) -----

/**
 * 아무나 SOLO 결합 가입 가능 여부 체크
 * @param {{ ncn: string, ctn: string, custId: string, rateCd?: string }} params
 */
export async function checkCombineSelf(params) {
  return msfPost('/v1/service-change/combine/check', params)
}

/**
 * 요금제 단위 아무나 SOLO 결합 가능 여부 (할인 부가명)
 * @param {string} rateCd
 */
export async function getCombineSoloType(rateCd) {
  const q = rateCd ? `?rateCd=${encodeURIComponent(rateCd)}` : ''
  return msfGet(`/v1/service-change/combine/solo-type${q}`)
}

/**
 * 아무나 SOLO 결합 신청 처리 (Y44)
 * @param {{ ncn: string, ctn: string, custId: string, rateCd?: string, rateNm?: string, subLinkName?: string, contractNum?: string }} params
 */
export async function regCombineSelf(params) {
  return msfPost('/v1/service-change/combine/reg', params)
}


/**
 * 분실복구/일시정지해제 조회 (X26, X28, X33 요약).
 * @param {{ ncn: string, ctn: string, custId: string }} params
 */
export async function checkPause(params) {
  return msfPost('/v1/service-change/pause/check', params)
}

/**
 * 분실복구/일시정지해제 처리 (분실복구 X35 또는 일시정지해제 X30).
 * @param {{ ncn: string, ctn: string, custId: string, password: string }} params
 */
export async function applyPause(params) {
  return msfPost('/v1/service-change/pause/apply', params)
}

// ----- 데이터쉐어링 신규 개통 (saveDataSharingSimple → Step0~4) -----

/**
 * 신청서 저장 (OSST 미포함). ASIS: AppformController.saveDataSharing()
 * @param {{ ncn: string, ctn: string, custId: string, reqUsimSn: string }} params
 */
export async function saveDataSharingApply(params) {
  return msfPost('/v1/service-change/data-sharing/apply/save', params)
}

/**
 * PC0 사전체크 및 고객생성. ASIS: AppformController.saveDataSharingStep1()
 * @param {{ requestKey: number, resNo: string, ncn: string, custId: string }} params
 */
export async function saveDataSharingStep1(params) {
  return msfPost('/v1/service-change/data-sharing/apply/step1', params)
}

/**
 * ST1 폴링(PC2) + Y39 CI 조회. ASIS: AppformController.conPreCheck()
 * @param {{ requestKey: number, resNo: string, custId: string, prgrStatCd?: string }} params
 */
export async function conPreCheck(params) {
  return msfPost('/v1/service-change/data-sharing/apply/con-pre-check', params)
}

/**
 * NU1 번호조회 + NU2 번호예약. ASIS: AppformController.saveDataSharingStep2()
 * @param {{ requestKey: number, resNo: string, ctn: string, custId: string }} params
 */
export async function saveDataSharingStep2(params) {
  return msfPost('/v1/service-change/data-sharing/apply/step2', params)
}

/**
 * OP0 개통 및 수납. ASIS: AppformController.saveDataSharingStep3()
 * @param {{ requestKey: number, resNo: string, custId: string, billAcntNo?: string }} params
 */
export async function saveDataSharingStep3(params) {
  return msfPost('/v1/service-change/data-sharing/apply/step3', params)
}

/**
 * 데이터쉐어링 Step2 화면 데이터 (X71 결합목록 + 개통가능시간).
 * ASIS: MyShareDataController.dataSharingStep2()
 * @param {{ ncn: string, ctn: string, custId: string }} params
 */
export async function getDataSharingStep2Info(params) {
  return msfPost('/v1/service-change/data-sharing/step2-info', params)
}

/**
 * 데이터쉐어링 개통 신청 (X69 사전체크 + X70 가입).
 * ASIS: MyShareDataController.doinsertOpenRequestAjax()
 * @param {{ ncn: string, ctn: string, custId: string, opmdSvcNo: string, selfShareYn: string }} params
 */
export async function insertOpenRequest(params) {
  return msfPost('/v1/service-change/data-sharing/insert-open-request', params)
}

// ----- USIM 변경 (X85 유효성 체크, UC0 변경) -----

/**
 * USIM 번호 유효성 체크 (X85) — 공통 엔드포인트 사용
 * @param {{ ncn: string, ctn: string, custId: string, usimNo: string }} params
 */
export async function checkUsim(params) {
  return msfPost('/v1/comm/usim-check', params)
}

// ----- 무선데이터차단 / 정보료상한 사전체크 (Y24) -----

/**
 * 부가서비스 변경 사전체크 (Y24 — 무선데이터차단, 정보료상한, 부가서비스 공용)
 * @param {{ ncn: string, ctn: string, custId: string, socList: string[] }} params
 */
export async function preCheckAddition(params) {
  return msfPost('/v1/addition/pre-check', params)
}

// ----- 부가서비스 신청 팝업 분기 / 직접 신청 (5007-04/06) -----

/**
 * 5007-04/07/08/09 addSvcViewPop — 부가서비스 신청 팝업 분기 정보 조회.
 * SOC에 따라 popType(TM/STEAL/GENERAL 등)과 부가 정보(요금·명칭) 반환.
 * @param {{ ncn: string, ctn: string, custId?: string, soc: string }} params
 * @returns {{ popType: string, rateAdsvcCd: string, baseVatAmt: string, rateAdsvcNm: string }}
 */
export async function getAddSvcViewPop(params) {
  return msfPost('/v1/addition/add-svc-view-pop', params)
}

/**
 * 5007-06 regSvcChgAjax — 부가서비스 직접 신청 (Y25).
 * 불법TM차단(NOSPAM4) 등 상품파람정보가 있는 부가서비스 신청 시 사용.
 * @param {{ ncn: string, ctn: string, custId?: string, soc: string, ftrNewParam?: string }} params
 * @returns {{ resultCode: string, message: string }}
 */
export async function regSvcChgAjax(params) {
  return msfPost('/v1/addition/reg-svc-chg', params)
}

// ----- 서비스변경 통합 신청 (apply) -----

/**
 * 서비스변경 통합 신청 처리.
 * 선택된 모든 항목 M플랫폼 처리 후 MSF_REQUEST_SVC_CHG 신청서 DB INSERT.
 * @param {{
 *   ncn: string, ctn: string, custId: string, name: string,
 *   selectedOptions: string[],
 *   wirelessBlock?: string,
 *   infoLimit?: string,
 *   ratePlanSoc?: string,
 *   rateChangeSchedule?: string,
 *   usimChange?: string,
 *   usimSimType?: string,
 *   numChange?: string,
 *   pausePassword?: string,
 *   additions?: Array<{soc: string, action: string, socDescription?: string, ftrNewParam?: string}>,
 *   custType?: string,
 *   memo?: string,
 *   managerCd?: string,
 *   agentCd?: string
 * }} params
 * @returns {{ success: boolean, requestKey: number, applicationNo: string, message: string }}
 */
export async function applyServiceChange(params) {
  return msfPost('/v1/service-change/apply', params)
}
