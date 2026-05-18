<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    :title="dialogTitle"
    @open="emit('open')"
    @close="onClose"
    size="medium"
    class-name="roaming-share-main-dialog"
  >
    <MsfTitleArea title="이용기간 설정(한국시간 기준)" level="2" color="black" noline bold>
      <template #content>
        <MsfTextList
          :items="['신청한 시간부터 24시간 동안 적용']"
          type="none"
          margin="0"
          color="mint"
        />
      </template>
    </MsfTitleArea>
    <MsfStack type="field" vertical>
      <MsfDateRange
        v-model:from="formData.startDate"
        v-model:to="formData.endDate"
        class="ut-w100p"
      />
      <MsfSelect
        title="시작 시간 선택"
        v-model="formData.startHour"
        :options="timeOptions"
        placeholder="시작 시간 선택"
        class="ut-w100p"
      />
    </MsfStack>

    <MsfTitleArea title="회선 설정" level="2" color="black" noline bold />
    <MsfStack type="field" vertical>
      <MsfFormGroup label="<em>대표번호</em>" vertical>
        <MsfNumberInput :model-value="mainPhoneNumber" disabled class="ut-w100p" />
      </MsfFormGroup>
      <MsfFormGroup label="<em class='ut-w100p ut-nowrap'>서브번호 (데이터를 함께 이용할 추가 휴대폰 번호 등록)</em>" vertical tag="div">
        <MsfStack type="field" vertical>
          <MsfNumberInput
            v-for="(_, index) in subNumbers"
            :key="index"
            v-model="subNumbers[index]"
            :placeholder="getSubNumberPlaceholder(index)"
            class="ut-w100p"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>

    <MsfBox>
      <MsfTextList :items="[guideText]" margin="0" level="2" />
    </MsfBox>

    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton v-if="props.settingData?.addSvcSettingCompleted" variant="tertiary" @click="onReset">초기화</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { computed, reactive, ref, watch } from 'vue'
import { addMonths, isAfter, isBefore } from 'date-fns'
import { toDate } from '@/libs/utils/date.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { normalizePhone, isValidMobileNumber } from '@/libs/utils/string.utils'

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  variant: { type: String, default: 'main1' }, // main1 | main2
  mainPhoneNumber: { type: String, default: '' },
  settingData: { type: Object, default: () => ({}) },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const formData = reactive({
  startDate: '',
  endDate: '',
  startHour: '',
})
const subNumbers = ref([])
const isFormReset = ref(false)

const isMain2 = computed(() => props.variant === 'main2')
const maxSubCount = computed(() => 3)
const minSubCount = computed(() => (isMain2.value ? 1 : 0))
const dialogTitle = computed(() =>
  isMain2.value ? '하루종일 로밍 베이직 투게더 대표 설정' : '함께쓰는 로밍 대표 설정',
)
const guideText = '추가 휴대폰 번호는 kt M모바일 사용 휴대폰 번호에 한해 최대 3개까지 등록 가능합니다.'

const timeOptions = computed(() =>
  Array.from({ length: 24 }, (_, hour) => {
    const value = String(hour).padStart(2, '0')
    return { label: `${value}:00`, value }
  }),
)

const today = () => {
  const date = new Date()
  date.setHours(0, 0, 0, 0)
  return date
}

const formatYmd = (value) => String(value || '').replace(/\D/g, '')
const getSubNumberPlaceholder = (index) =>
  isMain2.value && index === 0 ? '서브 휴대폰번호 입력 (필수)' : '서브 휴대폰번호 입력 (선택)'

const setEmptySubNumbers = () => {
  subNumbers.value = Array.from({ length: maxSubCount.value }, () => '')
}

const parseParams = (ftrNewParam) => {
  // 공통 형식: yyyyMMddHH:yyyyMMdd:번호1:번호2:번호3
  const parts = String(ftrNewParam || '').split(':')
  const startPart = parts[0] || ''
  const endPart = parts[1] || ''
  if (startPart.length >= 10) {
    formData.startDate = `${startPart.slice(0, 4)}-${startPart.slice(4, 6)}-${startPart.slice(6, 8)}`
    formData.startHour = startPart.slice(8, 10)
  }
  if (endPart.length === 8) {
    formData.endDate = `${endPart.slice(0, 4)}-${endPart.slice(4, 6)}-${endPart.slice(6, 8)}`
  }
  subNumbers.value = Array.from({ length: maxSubCount.value }, (_, index) => parts[index + 2] || '')
}

const initializeFromSettingData = () => {
  formData.startDate = props.settingData.startDate || ''
  formData.endDate = props.settingData.endDate || ''
  formData.startHour = props.settingData.startHour || ''
  setEmptySubNumbers()

  const ftrNewParam = props.settingData.ftrNewParam
  if (!ftrNewParam) return

  parseParams(ftrNewParam)
}

watch(
  () => props.modelValue,
  (isOpen) => {
    if (isOpen) {
      isFormReset.value = false
      initializeFromSettingData()
    }
  },
  { immediate: true },
)

watch(
  () => props.variant,
  () => {
    if (props.modelValue) initializeFromSettingData()
  },
)

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onReset = () => {
  isFormReset.value = true
  formData.startDate = ''
  formData.endDate = ''
  formData.startHour = ''
  setEmptySubNumbers()
}

const validateDates = () => {
  const start = toDate(formData.startDate)
  const end = toDate(formData.endDate)
  if (!formData.startDate || !start) return '시작일을 선택해 주세요.'
  if (!formData.endDate || !end) return '종료일을 선택해 주세요.'
  if (!formData.startHour) return '시작시간을 선택해 주세요.'
  if (isBefore(start, today())) return '시작일은 오늘 이후로 선택해 주세요.'
  if (isAfter(start, addMonths(today(), 2))) return '시작일은 2개월 이내로 선택해 주세요.'
  if (isAfter(end, addMonths(today(), 6))) return '종료일은 6개월 이내로 선택해 주세요.'
  if (!isBefore(start, end)) return '종료일은 시작일보다 이후로 선택해 주세요.'
  return ''
}

const getValidSubNumbers = () => {
  const numbers = subNumbers.value.map(normalizePhone)
  const filled = numbers.filter(Boolean)

  if (filled.length < minSubCount.value) {
    return { message: '추가 휴대폰 번호를 1개 이상 입력해 주세요.' }
  }

  const hasGap = numbers.some((number, index) => !number && numbers.slice(index + 1).some(Boolean))
  if (hasGap) {
    return { message: '추가 휴대폰 번호는 1번부터 순서대로 입력하세요.' }
  }

  if (filled.some((number) => !isValidMobileNumber(number))) {
    return { message: '유효한 휴대폰번호를 입력해 주세요.' }
  }

  if (new Set(filled).size !== filled.length) {
    return { message: '같은 번호를 중복으로 입력할 수 없습니다.' }
  }

  return { numbers: filled }
}

const createFtrNewParam = (numbers) => {
  // 공통 형식: yyyyMMddHH:yyyyMMdd:번호1:번호2:번호3
  const startYmd = formatYmd(formData.startDate)
  const endYmd = formatYmd(formData.endDate)
  const startHour = String(formData.startHour || '00').padStart(2, '0')
  return [`${startYmd}${startHour}`, endYmd, ...numbers].join(':')
}

const onConfirm = () => {
  if (isFormReset.value) {
    emit('confirm', { isReset: true })
    onClose()
    return
  }
  const dateMessage = validateDates()
  if (dateMessage) {
    showAlert(dateMessage)
    return
  }

  const subResult = getValidSubNumbers()
  if (subResult.message) {
    showAlert(subResult.message)
    return
  }

  emit('confirm', {
    ftrNewParam: createFtrNewParam(subResult.numbers),
    startDate: formData.startDate,
    endDate: formData.endDate,
    startHour: formData.startHour,
    subNumbers: subResult.numbers,
  })
  onClose()
}
</script>

<style lang="scss" scoped>
:global(.roaming-share-main-dialog.dialog-size-medium) {
  max-height: rem(700px);
}
</style>
