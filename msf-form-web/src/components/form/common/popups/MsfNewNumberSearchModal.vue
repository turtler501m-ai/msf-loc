<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신규번호 검색"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <p class="ut-text-desc">번호를 선택해 주세요.</p>

    <!-- 컨텐츠 로딩형태 추가_20260610 -->
    <MsfLoadingComp v-if="loading" inline height="400" />

    <template v-else>
      <MsfRadioGroup
        v-if="numberOptions.length > 0"
        name="number-select"
        v-model="numberSelect"
        :options="numberOptions"
        grid
      />
      <!-- 데이터 없는 경우 추가_20260415 -->
      <div v-else class="nodata-wrap">
        희망번호에 해당하는 신규 번호가 없습니다.<br />다른 번호로 다시 조회해 주세요.
      </div>
    </template>

    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm" :disabled="!numberSelect">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { nextTick, ref } from 'vue'
import { post } from '@/libs/api/msf.api'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { formatTelephone } from '@/libs/utils/string.utils'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  modelValue: Boolean,
  searchParams: { type: Object, default: () => ({}) },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])
const store = useMsfFormNewChgStore()

// 신규번호 선택항목
const numberSelect = ref('')
const numberOptions = ref([])
const loading = ref(false)

const onOpen = async () => {
  numberOptions.value = []
  numberSelect.value = ''
  emit('open')
  await fetchNumbers()
}

const fetchNumbers = async () => {
  loading.value = true
  try {
    console.log('props.searchParams.pageMode', props.searchParams.pageMode)
    if (props.searchParams.pageMode !== undefined && props.searchParams.pageMode === 'CHG') {
      // 변경 신청
      await fetchNumbersChg()
    } else {
      // 신규 변경
      await fetchNumbersNew()
    }
  } catch (error) {
    console.error('Search number error:', error)
    numberOptions.value = []
  } finally {
    loading.value = false
  }
}

const fetchNumbersNew = async () => {
  try {
    const payload = {
      reqWantNumber: props.searchParams.reqWantRnNo || '', // 뒤 4자리 검색
      requestKey: store.applicationKey || '',
      operTypeCd: props.searchParams.operTypeCd || store.customer?.joinType || '',
    }
    const res = await post('/api/form/hopenumber/get', payload, { skipAlert: true })
    if (res && res.code === '0000') {
      const list = res.data?.resData || []

      numberOptions.value = list.map((num) => {
        return {
          value: num.tlphNo,
          label: num.tlphNo ? formatTelephone(num.tlphNo) : '',
          raw: num,
        }
      })
      if (numberOptions.value.length > 0) {
        numberSelect.value = numberOptions.value[0].value
      } else {
        numberSelect.value = ''
      }
    } else {
      numberOptions.value = []
      numberSelect.value = ''
    }
  } catch (error) {
    console.error('Search number error:', error)
  }
}

/* 서비스 변경 시 사용 */
const fetchNumbersChg = async () => {
  try {
    const payload = {
      chkCtn: props.searchParams.reqWantRnNo || '', // 뒤 4자리 검색
      ...props.searchParams,
    }
    const res = await post('/api/msf/formServiceChange/numChge/list', payload, { skipAlert: true })
    if (res && res.code === '0000') {
      if (res.data.resCode !== '0000') {
        showAlert(res.data.resMessage)
      }

      const list = res.data?.resData?.outDtoList || []
      numberOptions.value = list.map((num) => {
        return {
          value: num.ctn,
          label: num.ctn ? formatTelephone(num.ctn) : '',
          raw: num,
        }
      })
      if (numberOptions.value.length > 0) {
        numberSelect.value = numberOptions.value[0].value
      } else {
        numberSelect.value = ''
      }
    } else {
      numberOptions.value = []
      numberSelect.value = ''
    }
  } catch (error) {
    console.error('Search number error:', error)
  }
}

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onConfirm = async () => {
  const selected = numberOptions.value.find((opt) => opt.value === numberSelect.value)
  onClose()
  await nextTick()
  emit('confirm', selected?.raw || numberSelect.value)
}
</script>

<style lang="scss" scoped>
.ut-text-desc {
  padding-bottom: rem(12px);
  margin-bottom: rem(12px);
  border-bottom: var(--border-width-base) solid var(--color-gray-150);
}
</style>
