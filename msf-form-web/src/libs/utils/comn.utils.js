import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'
import { ref, unref } from 'vue'

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
export const getCommonCodeList = async (groupIds, includeDetail = false, includeAll = false) => {
  const isArray = Array.isArray(groupIds)
  const res = await post('/api/common-codes/list', {
    groupIds: isArray ? groupIds : [groupIds],
    includeAll,
    includeDetail,
  })
  if (res.code !== '0000') {
    return showAlert(
      res.message || groupIds + ' 그룹코드에 대한 공통코드 목록 조회에 실패했습니다.',
    )
  }
  return isArray ? res.data : res.data?.[groupIds] || []
}

const normalizeCommonCode = (item) => ({
  ...item,
  value: item?.value ?? item?.code ?? item?.cd ?? item?.dtlCd ?? item?.DTL_CD ?? '',
  label:
    item?.label ??
    item?.title ??
    item?.codeNm ??
    item?.cdNm ??
    item?.dtlCdNm ??
    item?.DTL_CD_NM ??
    '',
})

export const useCommonCode = (groupId, modelRef = null, modelKey = null, defaultValue = null) => {
  const codeList = ref([])

  const load = async () => {
    if (!groupId) {
      codeList.value = []
      return
    }
    const list = await getCommonCodeList(groupId)
    codeList.value = (Array.isArray(list) ? list : []).map(normalizeCommonCode)

    if (!modelRef || !modelKey) return
    const model = unref(modelRef)
    if (!model || typeof model !== 'object') return
    if (model[modelKey] !== undefined && model[modelKey] !== null && model[modelKey] !== '') return

    const hasDefault = codeList.value.some((item) => item.value === defaultValue)
    model[modelKey] = hasDefault ? defaultValue : codeList.value[0]?.value
  }

  load()

  return { codeList, reload: load }
}

export const extractData = (res) => {
  if (!res) return []
  if (Array.isArray(res)) return res
  if (res.code && res.code !== '0000') return []
  if (Array.isArray(res.data)) return res.data
  if (res.data && typeof res.data === 'object') {
    const firstArray = Object.values(res.data).find((v) => Array.isArray(v))
    return firstArray || []
  }
  return []
}
