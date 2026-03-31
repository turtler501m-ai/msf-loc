/**
 * 업무공통 API (formComm)
 * 서비스변경·명의변경·서비스해지 등 전 업무에서 공용으로 사용하는 API.
 */

import { msfGet, msfPost } from './msf'

/**
 * 대리점/판매점 목록 조회 (업무공통)
 * GET /api/v1/comm/agencies
 * @returns {Promise<{ success: boolean, agencies: Array<{value: string, label: string}> }>}
 */
export async function getAgencies() {
  return msfGet('/v1/comm/agencies')
}

/**
 * N7003 개통일자 조회 (업무공통)
 * POST /api/v1/comm/activation-date
 * @param {{ ncn: string }} params - 계약번호 포함 요청
 * @returns {Promise<{ success: boolean, activationDate: string, activationDateRaw: string }>}
 */
export async function getActivationDate(params) {
  return msfPost('/v1/comm/activation-date', params)
}

/**
 * N7002 접점코드로 대리점 코드·정보 조회 (업무공통)
 * ASIS: AppformMapper.selectAgentCode (ORG_ORGN_INFO_MST@DL_MSP)
 * POST /api/v1/comm/agent-code
 * @param {{ cntpntShopId: string }} params - 접점코드(ORGN_ID)
 * @returns {Promise<{ success: boolean, agentCode: { ktOrgId, orgnNm, roadnAdrZipcd, roadnAdrBasSbst, roadnAdrDtlSbst } }>}
 */
export async function getAgentCode(params) {
  return msfPost('/v1/comm/agent-code', params)
}
