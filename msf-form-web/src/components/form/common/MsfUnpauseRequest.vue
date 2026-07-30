<template>
  <MsfLoadingComp v-if="isAjaxChecking" />
  <MsfTitleArea title="분실복구/일시정지해제 신청" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="일시정지 해제<br/>비밀번호">
      <MsfStack type="field">
        <MsfNumberInput
          type="password"
          v-model="model.unLockPw"
          placeholder="일시정지 해제 비밀번호"
          class="ut-w-300"
          maxlength="8"
          :disabled="!isServiceFirstCheck"
        />
        <MsfButton
          variant="toggle"
          :disabled="!isServiceFirstCheck || model.unLockPw.length < 4"
          :active="store.authFlags.unpause && isServiceFirstCheck"
          @click="clickUnpauseCheck"
          >{{ confirmButtonLabel }}</MsfButton
        >
        <MsfButton v-if="isLocalMode" variant="toggle" @click="clickUnpauseActive"
          >일시정지[TEST]</MsfButton
        >
      </MsfStack>
    </MsfFormGroup>
  </MsfStack>
</template>

<script setup>
import { computed, defineModel, onMounted, ref, watch } from 'vue'
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { MsfNumberInput } from '@/libs/ui/index.js'
import { showAlert } from '@/libs/utils/comp.utils'

const emit = defineEmits(['update:modelValue', 'ready'])
const TEMP_DEFAULT_UNLOCK_PASSWORD = '12345678'
const isLocalMode = import.meta.env.MODE === 'loc'

// ─── 로그 접두사 ──────────────────────────────────────────────────────────────
const getLogPrefix = (task) => `[변경][분실복구/일시정지해제][${task}]`

// ─── 상태 (State) ─────────────────────────────────────────────────────────────
const model = defineModel({ type: Object, required: true })
const store = useMsfFormSvcChgStore()

// 첫 로딩 확인
const isServiceFirstCheck = ref(true)
// 확인완료 상태 (true → 일시 정지 확인 가능 완료 후)
const isServiceConfirmCompleted = ref(false)
// 신청 불가 상태
const isServiceConfirmIngNot = ref(false)
// AJAX 진행중 플래그
const isAjaxChecking = ref(false)

// 확인 버튼 레이블 (토글 상태에 따라 전환)
const confirmButtonLabel = computed(() => {
  if (isAjaxChecking.value) return '진행중...'
  return isServiceConfirmCompleted.value ? '확인 완료' : '확인'
})

watch(
  () => model.value.unpauseConfirmCompleted,
  (val) => {
    if (!val && isServiceConfirmCompleted.value) {
      isServiceConfirmCompleted.value = false
    }
  },
)

// ─── 확인 버튼 핸들러 ──────────────────────────────────────────────────────────

const createUnpauseCheckPayload = () => ({
  ncn: model.value.ncn || model.value.contractNum || '',
  ctn: `${model.value.deviceChgTel1 || ''}${model.value.deviceChgTel2 || ''}${model.value.deviceChgTel3 || ''}`,
  custId: model.value.custId || '',
  cpPwdInsert:
    model.value.unLockPw || (isLocalMode ? TEMP_DEFAULT_UNLOCK_PASSWORD : ''),

  // test active
  cpDateYn: 'N',
  stopRsnCd: 'CR01',
  reasonCode: '01',
  cpStartDt: '20260701',
  cpEndDt: '20260830',
})

// TEST 일시정지 비밀번호 설정
const clickUnpauseActive = async () => {
  isAjaxChecking.value = true
  try {
    const apiResult = await store.apiUnpauseActice(createUnpauseCheckPayload())
    console.log(`${getLogPrefix('X29 response')}`, apiResult.data)
    showAlert('X29 완료')
  } catch (error) {
    console.log(`${getLogPrefix('ERROR')}`, error)
  } finally {
    isAjaxChecking.value = false
  }
}

const clickUnpauseCheck = async () => {
  if (!isServiceFirstCheck.value) {
    return
  }

  isAjaxChecking.value = true
  try {
    const apiResult = await store.apiUnpauseCheck(createUnpauseCheckPayload())
    //console.log(`${getLogPrefix('X28 response')}`, apiResult.data)

    const checkResult = apiResult.data || {}
    const available = apiResult.success && checkResult.outDto?.rsltInd === 'Y'

    model.value.unpauseConfirmCompleted = available
    store.authFlags.unpause = available
    isServiceConfirmCompleted.value = available

    if (!available) {
      const message = checkResult.outDto?.rsltMsg || checkResult.message
      model.value.unLockPw = isLocalMode ? TEMP_DEFAULT_UNLOCK_PASSWORD : ''
      //isServiceFirstCheck.value = false
      //isServiceConfirmIngNot.value = true
      showAlert(message || '고객님께서는 정지 해제가 불가합니다.')
    } else {
      isServiceFirstCheck.value = false
    }
  } catch (error) {
    console.log(`${getLogPrefix('ERROR')}`, error)
    store.authFlags.unpause = false
    showAlert('분실복구/일시정지해제 가입 가능 여부 확인 중 오류가 발생했습니다.')
  } finally {
    isAjaxChecking.value = false
  }
}

// ─── 라이프사이클 & 이벤트 ─────────────────────────────────────────────────

onMounted(() => {
  //console.log(`${getLogPrefix('초기화')} mounted`)

  isServiceFirstCheck.value = true
  isServiceConfirmCompleted.value = false
  isAjaxChecking.value = false
  store.authFlags.unpause = false
  model.value.unpauseConfirmCompleted = false

  model.value.unLockPw = isLocalMode ? TEMP_DEFAULT_UNLOCK_PASSWORD : ''
  emit('ready')
})
</script>
