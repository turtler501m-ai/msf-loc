/**
 * 서비스해지 신청서 API (formSvcCncl)
 * X01, X22, X67 등 M포털 동일 스펙 연동
 */

import { msfPost } from './msf'

export async function getJoinInfo(params) {
  return msfPost('/v1/cancel/join-info', params)
}

export async function getRemainCharge(params) {
  return msfPost('/v1/cancel/remain-charge', params)
}

export async function checkCancelEligible(params) {
  return msfPost('/v1/cancel/eligible', params)
}

/**
 * 서비스해지 신청서 등록 (POST /v1/cancel/apply)
 * @param {object} payload - { agencyCode, customerForm, productForm }
 * @returns {Promise<{ success: boolean, applicationNo?: string, message?: string }>}
 */
export async function submitCancelApply(payload) {
  return msfPost('/v1/cancel/apply', payload)
}
