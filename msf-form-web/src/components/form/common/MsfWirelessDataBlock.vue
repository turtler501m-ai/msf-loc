<script setup>
import { computed, defineModel, onMounted, ref, watch } from 'vue'
import { useMsfUserStore } from '@/stores/msf_user'
import { showAlert } from '@/libs/utils/comp.utils'

const model = defineModel({ type: Object, required: true })
const userStore = useMsfUserStore()

const wirelessBlockInUse = ref(null)
const isConfirmCompleted = ref(false)
const isLoading = ref(false)

const hasSelection = computed(() => !!model.value.blockService)
const confirmButtonLabel = computed(() => (isConfirmCompleted.value ? '확인완료' : '확인'))

const statusMessage = computed(() => {
  if (wirelessBlockInUse.value === null) return ''
  return wirelessBlockInUse.value
    ? '현재 무선데이터차단 서비스 이용 중입니다.'
    : '현재 무선데이터 이용 중입니다.'
})

const chipData = computed(() => [
  {
    value: 'blockService1',
    label: '무선데이터 이용',
    disabled: wirelessBlockInUse.value !== true,
  },
  {
    value: 'blockService2',
    label: '무선데이터차단 서비스 이용',
    disabled: wirelessBlockInUse.value !== false,
  },
])

const setConfirmState = (completed) => {
  isConfirmCompleted.value = completed
  model.value.wirelessBlockConfirmCompleted = completed
}

const isSelectionAvailable = () => {
  if (model.value.blockService === 'blockService1') {
    return wirelessBlockInUse.value === true
  }
  if (model.value.blockService === 'blockService2') {
    return wirelessBlockInUse.value === false
  }
  return false
}

const fetchAvailableList = async () => {
  const phoneNo = `${model.value.deviceChgTel1 || ''}${model.value.deviceChgTel2 || ''}${model.value.deviceChgTel3 || ''}`
  const ncn = model.value.ncn || model.value.contractNum || ''
  if (!ncn) return null

  try {
    const baseUrl = `${import.meta.env.VITE_MSF_API_URL || ''}`.replace(/\/$/, '')
    const headers = { 'Content-Type': 'application/json', Accept: 'application/json' }
    if (userStore.token) headers.Authorization = `Bearer ${userStore.token}`

    const response = await fetch(`${baseUrl}/api/form/servicechange/availablelist`, {
      method: 'POST',
      headers,
      credentials: 'include',
      body: JSON.stringify({ ncn, ctn: phoneNo, custId: model.value.custId || '' }),
    })
    const data = await response.json().catch(() => null)
    const resData = data?.data?.resData
    if (resData != null) {
      wirelessBlockInUse.value = resData.wirelessBlockInUse === true
      setConfirmState(false)
      return resData
    }
  } catch (e) {
    console.error('[무선데이터차단] availablelist 조회 실패', e)
  }

  return null
}

const handleConfirm = async () => {
  if (!hasSelection.value) return

  if (isConfirmCompleted.value) {
    setConfirmState(false)
    return
  }

  isLoading.value = true
  try {
    console.log('[무선데이터차단] 확인 요청', {
      currentBlockState: wirelessBlockInUse.value,
      selectedBlockService: model.value.blockService,
    })

    await fetchAvailableList()

    if (!isSelectionAvailable()) {
      showAlert('가입이 불가한 무선데이터차단 서비스입니다.', () => {
        model.value.blockService = null
        setConfirmState(false)
      })
      return
    }

    setConfirmState(true)
    console.log('[무선데이터차단] 확인 완료', {
      selectedBlockService: model.value.blockService,
      wirelessBlockConfirmCompleted: model.value.wirelessBlockConfirmCompleted,
    })
  } finally {
    isLoading.value = false
  }
}

watch(
  () => model.value.blockService,
  () => {
    if (isConfirmCompleted.value) {
      setConfirmState(false)
    }
  },
)

onMounted(() => {
  model.value.blockService = null
  setConfirmState(false)
  fetchAvailableList()
})
</script>

<template>
  <MsfTitleArea title="무선데이터차단 서비스" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="무선데이터차단<br/>이용 여부" tag="div" required>
      <MsfChip
        v-model="model.blockService"
        name="inp-blockService"
        :data="chipData"
      >
        <template #endSlot>
          <MsfButton
            variant="toggle"
            :active="isConfirmCompleted"
            :disabled="!hasSelection || isLoading"
            @click="handleConfirm"
          >
            {{ confirmButtonLabel }}
          </MsfButton>
        </template>
      </MsfChip>
      <p v-if="statusMessage" class="wireless-status-msg">{{ statusMessage }}</p>
    </MsfFormGroup>
  </MsfStack>
</template>

<style scoped lang="scss">
.wireless-status-msg {
  margin-top: 8px;
  font-size: 13px;
  color: #374151;
}
</style>