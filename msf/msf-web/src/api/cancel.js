/**
 * 서비스해지 신청서 API (formSvcCncl)
 */

import { msfPost } from './msf'
import { getAgencies as commGetAgencies } from './common'

export async function getJoinInfo(params) {
  return msfPost('/v1/cancel/join-info', params)
}

export async function getRemainCharge(params) {
  return msfPost('/v1/cancel/remain-charge', params)
}

/**
 * 해지 사전체크 (POST /v1/cancel/cancelConsult)
 * ASIS: CancelConsultController.cancelConsultAjax() 동일 체크 로직.
 * @param {{ ncn, ctn, birthDate, phoneAuthCompleted }} params
 * @returns {Promise<{ success: boolean, resultCode: string, message: string }>}
 */
export async function cancelConsult(params) {
  return msfPost('/v1/cancel/cancelConsult', params)
}

/**
 * 대리점 목록 조회 — 업무공통 (GET /v1/comm/agencies) 위임.
 * @returns {Promise<{ success: boolean, agencies: Array<{value, label}> }>}
 */
export { commGetAgencies as getAgencies }

/**
 * 서비스해지 신청서 등록 (POST /v1/cancel/apply)
 * @param {object} payload - { agencyCode, customerForm, productForm }
 * @returns {Promise<{ success: boolean, applicationNo?: string, message?: string }>}
 */
export async function submitCancelApply(payload) {
  return msfPost('/v1/cancel/apply', payload)
}
