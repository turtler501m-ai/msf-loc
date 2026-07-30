<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="고객 유형" tag="div" required>
        <MsfChip
          ref="cstmrTypeCdRef"
          v-model="model.cstmrTypeCd"
          id="MsfCustomerType-cstmrTypeCd"
          :name="`${name}-inp-customerType`"
          :data="customerTypeData"
          :readonly="isReadonly || props.disabled"
        />
      </MsfFormGroup>
      <MsfFormGroup v-if="showVisitType && !isTrCustomer" label="방문 유형" tag="div" required>
        <MsfChip
          ref="cstmrVisitTypeCdRef"
          v-model="model.cstmrVisitTypeCd"
          :name="`${name}-inp-visitType`"
          :data="visitTypeOptions"
          :readonly="props.disabled || newChgStore.customer.isVerified || newChgStore.preChecked"
        />
      </MsfFormGroup>
      <MsfFormGroup label="대리점" tag="div" required v-if="!isTrCustomer">
        <MsfSelect
          ref="selectedAgentRef"
          v-model="selectedAgent"
          :options="agentOptions"
          :disabled="props.disabled || (disableAgentWhenAuthLocked && isAuthLocked)"
          class="ut-w-300"
          placeholder="대리점 선택"
          title="대리점 선택"
        />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>

<script setup>
import { computed, defineModel, defineProps, watch, defineExpose, ref, onMounted } from 'vue'
import { getCommonCodeList, getCommonCodeListWithDetail } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { post } from '@/libs/api/msf.api'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'

const props = defineProps({
  title: { type: String, default: '고객 유형' },
  name: { type: String, default: 'base' },
  visitTypeCodes: { type: Array, default: () => ['JP', 'GO'] },
  allowedCodes: { type: Array, default: () => [] },
  disableAgentWhenAuthLocked: { type: Boolean, default: false },
  authFlags: { type: Object, default: () => ({}) },
  agentValueField: { type: String, default: 'agent' },
  disabled: Boolean,
})
const emit = defineEmits(['change-customer-type'])

const model = defineModel({ type: Object, required: true })
const newChgStore = useMsfFormNewChgStore()

const isTrCustomer = computed(() => {
  if (model.value?.formType === 'NEWCHANGE') return false
  return !!model.value?.isTrCustomer
})

const rawCustomerTypes = ref([])
const agentOptions = ref([])
const visitTypeOptions = ref([])
const showVisitType = ref(false)
const isReadonly = computed(() => isTrCustomer.value && props.authFlags?.deviceChgTel)
const selectedAgent = computed({
  get() {
    if (props.agentValueField === 'agentCd') {
      const agentCd = String(model.value.agentCd || '')
      const matched = agentOptions.value.find(
        (option) =>
          String(option.value) === agentCd ||
          String(option.orgnId) === agentCd ||
          String(option.ktOrgId) === agentCd,
      )
      return matched?.value || ''
    }
    return model.value.agent || ''
  },
  set(value) {
    if (!value) {
      model.value[props.agentValueField] = ''
      return
    }
    applyAgentMeta(value)
  },
})

const test = async () => {
  const list = await getCommonCodeList('CSTMR_TYPE_CD')
  rawCustomerTypes.value = list?.map((item) => ({ value: item.code, label: item.title })) || []
}

/**
 * 방문 유형 조회 (고객 유형별 동적 조회)
 */
const fetchVisitTypes = async (customerType) => {
  if (!customerType) return

  const groupId = `VISIT_CUST_TYPE_CD_${customerType}`
  try {
    const list = await getCommonCodeListWithDetail(groupId)
    if (!list || list.length === 0) {
      visitTypeOptions.value = []
      showVisitType.value = false
      return
    }

    // UI 노출 여부: 법인(JP), 공공기관(GO)만 노출
    const isCorporateOrGov = ['JP', 'GO'].includes(customerType)
    showVisitType.value = isCorporateOrGov

    const allItems = list?.map((item) => ({
      value: item.code,
      label: item.title,
      visible: true, // 이제 노출/비노출 제어는 showVisitType.value가 전담하므로 옵션 자체는 항상 노출용으로 매핑
      disabled: isAuthLocked.value,
    }))

    visitTypeOptions.value = allItems

    // 고객유형별 기본 방문유형 정의
    const defaultVisitTypeMap = {
      NA: 'VMY', // 내국인 -> 본인
      FN: 'VMY', // 외국인 -> 본인
      NM: 'VDP', // 미성년자 -> 법정대리인
      FM: 'VDP', // 외국인 미성년자 -> 법정대리인
      JP: 'VMY', // 법인 -> 본인
      GO: 'VMY', // 공공기관 -> 본인
    }

    const defaultCode = defaultVisitTypeMap[customerType] || allItems[0].value

    // 현재 설정된 방문유형 코드가 전체 목록에 존재하는지 확인
    const hasCurrentCode = allItems.some((item) => item.value === model.value.cstmrVisitTypeCd)

    // 만약 현재 설정된 방문유형 코드가 없거나 유효하지 않은 경우, 혹은 비노출 대상(개인, 미성년자 등)인 경우 기본값으로 동기화 (임시저장 로드 중 리셋 방지)
    if (!model.value.cstmrVisitTypeCd || !hasCurrentCode || !isCorporateOrGov) {
      if (!newChgStore.isDraftLoading) {
        const matchedItem = allItems.find((item) => item.value === defaultCode)
        model.value.cstmrVisitTypeCd = matchedItem ? matchedItem.value : allItems[0].value
      }
    }
  } catch (e) {
    console.error(`Failed to fetch visit types for ${groupId}:`, e)
  }
}

/**
 * 대리점 목록 조회
 */
const fetchAgents = async () => {
  try {
    const res = await post('/api/form/agent/list', {})
    const extractData = (res) => {
      if (!res) return []
      if (Array.isArray(res)) return res
      if (res.data) {
        if (Array.isArray(res.data)) return res.data
        return [res.data]
      }
      return []
    }
    const list = extractData(res)

    agentOptions.value = list.map((item) => ({
      label: item.orgnNm || item.cntpntNm || '대리점명 없음',
      value: String(item.ktOrgId || item.shopOrgnId || item.orgnId || ''),
      orgnId: String(item.orgnId || item.shopOrgnId || ''),
      ktOrgId: String(item.ktOrgId || ''),
      shopOrgnId: String(item.shopOrgnId || ''),
      shopNm: item.shopNm || '',
      realShopNm: item.realShopNm || item.shopNm || '',
      managerNm: item.managerNm || '',
      telephone: item.telephone || item.telnum || '',
      representativeTelephone: item.representativeTelephone || item.repTelNo || '',
      canBulkCorporateOpenYn: item.canBulkCorporateOpenYn || 'N',
    }))

    // 결과 처리 및 초기값 세팅 (기존에 이미 agentCd가 있는 경우 대응)
    syncAgentWithAgentCd()

    if (!selectedAgent.value && agentOptions.value.length === 1) {
      // 대리점이 하나뿐이고 선택된 게 없으면 자동 선택
      const first = agentOptions.value[0]
      applyAgentMeta(first.value)
    }
  } catch (error) {
    console.error('Failed to fetch agents:', error)
  }
}

/**
 * agentCd와 대리점 목록 매칭 및 세팅 (유효하지 않으면 첫 번째 항목 선택)
 */
const syncAgentWithAgentCd = () => {
  if (agentOptions.value.length === 0) return

  const targetCd = model.value.agentCd ? String(model.value.agentCd).trim() : ''
  let found = null

  // 1. agentCd가 있다면 목록에서 검색
  if (targetCd) {
    found =
      agentOptions.value.find(
        (opt) =>
          String(opt.value) === targetCd ||
          String(opt.orgnId) === targetCd ||
          String(opt.ktOrgId) === targetCd,
      ) ||
      agentOptions.value.find(
        (opt) => String(opt.value).includes(targetCd) || targetCd.includes(String(opt.value)),
      )
  }

  if (found) {
    // 매칭되는 대리점을 찾은 경우 (값이 다르거나 명칭이 누락된 경우 업데이트)
    const needsAgentMeta =
      String(selectedAgent.value) !== String(found.value) ||
      !model.value.agentNm ||
      !model.value.ktOrgId || //20260716 KT조직코드 추가 셋팅
      !model.value.shopNm ||
      !model.value.realShopNm ||
      (!model.value.telephone && found.telephone) ||
      (!model.value.representativeTelephone && found.representativeTelephone) ||
      model.value.canBulkCorporateOpenYn !== found.canBulkCorporateOpenYn
    if (needsAgentMeta) {
      applyAgentMeta(found.value)
    }
  } else {
    // 2. 매칭되는 것이 없으면 현재 선택값이 유효한지 확인 후, 유효하지 않으면 첫 번째 항목 강제 선택
    const isCurrentValid = agentOptions.value.some(
      (opt) => String(opt.value) === String(selectedAgent.value),
    )
    if (!isCurrentValid) {
      const first = agentOptions.value[0]
      console.log('>>> [MsfCustomerType] Defaulting to first agent:', first.label)
      applyAgentMeta(first.value)
    }
  }
}

/**
 * 선택된 대리점의 상세 정보를 모델에 반영
 */
const applyAgentMeta = (agentValue) => {
  if (!agentValue || agentOptions.value.length === 0) return

  const selected = agentOptions.value.find((v) => String(v.value) === String(agentValue))
  if (!selected) return

  const orgId = selected.orgnId || selected.value
  const ktId = selected.ktOrgId || selected.value
  const shopOrgId = selected.shopOrgnId || orgId
  const shopName = selected.realShopNm || selected.shopNm || selected.label

  // 스토어 데이터 업데이트
  //model.value.agentCd = String(ktId) 신규변경 저장오류발생으로 인해 원복해두었습니다
  model.value.agentCd = String(orgId)
  model.value.ktOrgId = String(ktId) //20260716 KT조직코드 추가 셋팅
  model.value.agentNm = selected.label
  model.value.canBulkCorporateOpenYn = selected.canBulkCorporateOpenYn || 'N'
  if (props.agentValueField === 'agent') {
    model.value.agent = String(agentValue)
  }

  // 추가 필드 동기화 (DTO 구조 대응)
  model.value.shopCd = String(shopOrgId)
  model.value.shopNm = shopName
  model.value.realShopNm = shopName
  model.value.cpntId = String(ktId || orgId)
  model.value.cpntNm = selected.label
  model.value.cntpntShopCd = String(orgId)
  model.value.cntpntShopNm = selected.label
  model.value.telephone = selected.telephone || ''
  model.value.managerNm = selected.managerNm || ''
  model.value.representativeTelephone = selected.representativeTelephone || ''

  if (!model.value.managerCd || model.value.managerCd === 'M0001') {
    model.value.managerCd = String(ktId || 'M0001')
  }

  console.log('[MsfCustomerType][applyAgentMeta] organization mapping', {
    source: {
      agentValue,
      ktOrgId: selected.ktOrgId,
      orgnId: selected.orgnId,
      orgnNm: selected.label,
      shopOrgnId: selected.shopOrgnId,
      shopNm: selected.shopNm,
      realShopNm: selected.realShopNm,
    },
    applied: {
      agentCd: model.value.agentCd,
      ktOrgId: model.value.ktOrgId, //20260716 KT조직코드 추가 셋팅
      agentNm: model.value.agentNm,
      shopCd: model.value.shopCd,
      shopNm: model.value.shopNm,
      realShopNm: model.value.realShopNm,
      cpntId: model.value.cpntId,
      cpntNm: model.value.cpntNm,
      cntpntShopCd: model.value.cntpntShopCd,
      cntpntShopNm: model.value.cntpntShopNm,
      managerCd: model.value.managerCd,
      managerNm: model.value.managerNm,
    },
  })
}

const isAuthLocked = computed(
  () =>
    props.disabled ||
    model.value?.isVerified ||
    model.value?.isSaved ||
    model.value?.customerTypeLocked,
)

const customerTypeData = computed(() =>
  rawCustomerTypes.value.map((item) => {
    const isDeviceChange = ['HDN3', 'HCN3'].includes(model.value?.joinType)
    const isDisabledMinor = isDeviceChange && ['NM', 'FM'].includes(item.value)
    return {
      ...item,
      disabled:
        isAuthLocked.value ||
        isDisabledMinor ||
        (props.allowedCodes.length > 0 ? !props.allowedCodes.includes(item.value) : false),
    }
  }),
)

watch(
  () => model.value.cstmrTypeCd,
  (newVal, oldVal) => {
    if (oldVal !== undefined && oldVal !== newVal) {
      emit('change-customer-type', { newVal, oldVal, name: props.name })
    }

    // 고객 유형 변경 시 해당 유형에 맞는 방문 유형 목록 조회
    fetchVisitTypes(newVal)
  },
  { immediate: true },
)

// 기기변경(HDN3, HCN3)인 경우 미성년자(NM, FM) 선택 리셋
watch(
  () => model.value?.joinType,
  (newJoinType) => {
    if (['HDN3', 'HCN3'].includes(newJoinType)) {
      if (['NM', 'FM'].includes(model.value?.cstmrTypeCd)) {
        const isForeigner = model.value.cstmrTypeCd === 'FM'
        model.value.cstmrTypeCd = isForeigner ? 'FN' : 'NA'
      }
    }
  },
)

// 대리점 선택 시 상세 정보 업데이트
watch(
  () => selectedAgent.value,
  (newVal) => {
    if (newVal) {
      const isValid = agentOptions.value.some((opt) => String(opt.value) === String(newVal))
      if (isValid) {
        applyAgentMeta(newVal)
      } else {
        // 목록에 없는 값이 대입된 경우 첫 번째 값으로 복구 및 보정
        syncAgentWithAgentCd()
      }
    } else {
      // 대리점 선택값이 없어진 경우(초기화 또는 비동기 덮어쓰기), 목록이 있다면 첫 번째 값으로 복구
      syncAgentWithAgentCd()
    }
  },
)

// getdefault가 늦게 완료되어 agentCd가 나중에 들어올 경우 대응
watch(
  () => model.value.agentCd,
  () => {
    syncAgentWithAgentCd()
  },
)

// 대리점 목록이 나중에 로드될 경우 대응
watch(
  () => agentOptions.value,
  () => {
    syncAgentWithAgentCd()
  },
  { deep: true },
)

onMounted(() => {
  test()
  fetchAgents()
})

const validate = () => {
  if (!model.value.cstmrTypeCd) return false
  if (showVisitType.value && !model.value.cstmrVisitTypeCd) return false
  if (!isTrCustomer.value && !selectedAgent.value) return false
  return true
}

const cstmrTypeCdRef = ref(null)
const cstmrVisitTypeCdRef = ref(null)
const selectedAgentRef = ref(null)
const checkValidation = () => {
  if (!model.value.cstmrTypeCd) {
    showAlert('고객 유형을 선택해주세요.', () => {
      cstmrTypeCdRef.value?.focus()
    })
    return false
  }
  if (showVisitType.value && !isTrCustomer.value && !model.value.cstmrVisitTypeCd) {
    showAlert('방문 유형을 선택해주세요.', () => {
      cstmrVisitTypeCdRef.value?.focus()
    })
    return false
  }
  if (!isTrCustomer.value && !selectedAgent.value) {
    showAlert('대리점을 선택해주세요.', () => {
      selectedAgentRef.value?.focus()
    })
    return false
  }
  return true
}

defineExpose({ validate, checkValidation })
</script>
