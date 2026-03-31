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

// ----- 데이터쉐어링 (분석 43: X69 사전체크, X71 목록, X70 가입/해지) -----

/**
 * 데이터쉐어링 사전체크 (X69). 가입 가능 여부·대상 목록.
 * @param {{ ncn: string, ctn: string, custId: string }} params
 */
export async function checkDataSharing(params) {
  return msfPost('/v1/service-change/data-sharing/check', params)
}

/**
 * 데이터쉐어링 결합 중인 회선 목록 (X71).
 * @param {{ ncn: string, ctn: string, custId: string }} params
 */
export async function getDataSharingList(params) {
  return msfPost('/v1/service-change/data-sharing/list', params)
}

/**
 * 데이터쉐어링 가입(결합) (X70 A).
 * @param {{ ncn: string, ctn: string, custId: string, opmdSvcNo: string }} params
 */
export async function joinDataSharing(params) {
  return msfPost('/v1/service-change/data-sharing/join', params)
}

/**
 * 데이터쉐어링 해지 (X70 C).
 * @param {{ ncn: string, ctn: string, custId: string, opmdSvcNo: string }} params
 */
export async function cancelDataSharing(params) {
  return msfPost('/v1/service-change/data-sharing/cancel', params)
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
