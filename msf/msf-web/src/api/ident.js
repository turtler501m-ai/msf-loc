/**
 * 명의변경 신청서 API (formOwnChg)
 * X01, X20, MC0 등 AS-IS M포털 스펙 기반 연동
 */

import { msfPost } from './msf'

// X01 가입정보조회 (휴대폰 번호 인증)
export async function getJoinInfo(params) {
  return msfPost('/v1/ident/join-info', params)
}

// X20 현재 부가서비스 조회
export async function getCurrentAddition(params) {
  return msfPost('/v1/ident/addition/current', params)
}

// MC0 명의변경 가능 여부 (stub)
export async function checkIdentEligible(params) {
  return msfPost('/v1/ident/eligible', params)
}

// AS-IS grantorReqChk: 양도인 신청가능여부 체크
export async function checkGrantorAvailable(params) {
  return msfPost('/v1/ident/grantor-req-chk', params)
}

// AS-IS nameChgChkTelNo: 명의변경 회선 vs 연락처 상이 검증
export async function checkIdentTelNo(params) {
  return msfPost('/v1/ident/check-tel-no', params)
}

/**
 * 명의변경 신청서 등록 (작성 완료 시)
 * @param {object} payload - { agencyCode, customerForm, productForm } (store 구조)
 * @returns {Promise<{ success: boolean, applicationNo?: string, message?: string }>}
 */
export async function submitIdentApply(payload) {
  return msfPost('/v1/ident/apply', payload)
}
