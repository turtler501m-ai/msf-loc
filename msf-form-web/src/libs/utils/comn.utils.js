import { post } from '@/libs/api/msf.api'
import { showAlertWithId } from '@/libs/utils/comp.utils'

/**
 * 공통코드 그룹별 공통코드 목록 조회
 * - 각 공통코드의 상세 데이터 포함
 * - 미사용 공통코드 포함
 *
 * @param {String | Array} groupIds 그룹ID 또는 그룹ID 배열
 * @returns {Object | Array} groupIds가 단일 String 값일 경우, 공통코드 목록 반환, groupIds가 다수 그룹ID 배열일 경우, 그룹ID별 공통코드 목록 반환
 */
export const getCommonCodeListAll = async (groupIds) => {
  return getCommonCodeList(groupIds, true, true)
}

/**
 * 공통코드 그룹별 공통코드 목록 조회
 * - 각 공통코드의 상세 데이터 포함
 *
 * @param {String | Array} groupIds 그룹ID 또는 그룹ID 배열
 * @param {boolean} includeAll 미사용 공통코드 포함 여부
 * @returns {Object | Array} groupIds가 단일 String 값일 경우, 공통코드 목록 반환, groupIds가 다수 그룹ID 배열일 경우, 그룹ID별 공통코드 목록 반환
 */
export const getCommonCodeListWithDetail = async (groupIds, includeAll = false) => {
  return getCommonCodeList(groupIds, true, includeAll)
}

/**
 * 공통코드 그룹별 공통코드 목록 조회
 * - 미사용 공통코드 포함
 *
 * @param {String | Array} groupIds 그룹ID 또는 그룹ID 배열
 * @param {boolean} includeDetail 공통코드 상세 데이터 포함 여부
 * @returns {Object | Array} groupIds가 단일 String 값일 경우, 공통코드 목록 반환, groupIds가 다수 그룹ID 배열일 경우, 그룹ID별 공통코드 목록 반환
 */
export const getCommonCodeListWithUseAll = async (groupIds, includeDetail = false) => {
  return getCommonCodeList(groupIds, includeDetail, true)
}

/**
 * 공통 코드 그룹별 공통 코드 목록 조회
 *
 * @param {String | Array} groupIds 그룹ID 또는 그룹ID 배열
 * @param {boolean} includeDetail 공통코드 상세 데이터 포함 여부
 * @param {boolean} includeAll 미사용 공통코드 포함 여부
 * @returns {Object | Array} groupIds가 단일 String 값일 경우, 공통코드 목록 반환, groupIds가 다수 그룹ID 배열일 경우, 그룹ID별 공통코드 목록 반환
 */
const commonCodeCache = {}

export const getCommonCodeList = async (groupIds, includeDetail = false, includeAll = false) => {
  const isArray = Array.isArray(groupIds)
  const reqIds = isArray ? groupIds : [groupIds]

  // 캐시에 없는 것만 필터링
  // 파라미터 조합별로 캐시 존재 여부 검사 (꼬임 방지)
  const missingIds = reqIds.filter((id) => !commonCodeCache[`${id}_${includeDetail}_${includeAll}`])

  if (missingIds.length > 0) {
    const res = await post('/api/shared/common/common-codes/list', {
      groupIds: missingIds,
      includeAll,
      includeDetail,
    })
    if (res.code !== '0000') {
      console.warn(res.message || groupIds + ' 그룹코드에 대한 공통코드 목록 조회에 실패했습니다.')
      return showAlertWithId(
        'msf-api-alert',
        res.message || groupIds + ' 그룹코드에 대한 공통코드 목록 조회에 실패했습니다.',
      )
    }
    // 동일 ID라도 파라미터가 다르면 캐시에 독립적으로 분리 저장
    if (res.data) {
      Object.keys(res.data).forEach((id) => {
        commonCodeCache[`${id}_${includeDetail}_${includeAll}`] = res.data[id]
      })
    }
  }

  // 데이터 추출 및 리턴
  const resultData = {}
  reqIds.forEach((id) => {
    // 꺼낼 땐 파라미터 키로 조회하고, 반환은 화면에서 쓰는 기존 ID 키로 매핑
    const cacheData = commonCodeCache[`${id}_${includeDetail}_${includeAll}`]
    if (cacheData) resultData[id] = cacheData
  })
  return isArray ? resultData : resultData?.[groupIds] || []
}

/**
 * 이용약관 항목 조회
 *
 * @param {String} agreementId
 * @returns {Object} agreementId에 해당하는 이용약관 내용
 */
export const getTermsAgreementItem = async (agreementId) => {
  const res = await post('/api/shared/form/common/terms/list', {
    groupCode: agreementId,
  })
  if (res.code !== '0000') {
    return []
  }
  return res.data?.codes || []
}

export const getFormTypeCode = (routePath) => {
  if (routePath.includes('/form/newchange')) return '1'
  if (routePath.includes('/form/servicechange')) return '2'
  if (routePath.includes('/form/ownerchange')) return '3'
  if (routePath.includes('/form/termination')) return '4'
  return '0'
}
