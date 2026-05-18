<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    :title="title"
    @open="emit('open')"
    @close="onClose"
  >
    <MsfTitleArea level="2" noline>
      <template #title>
        <span>수신차단 번호 입력(<span class="ut-color-point">{{ phoneRows.length }}</span>/{{ maxCount }})</span>
      </template>
      <template #actions>
        <MsfButton variant="subtle" @click="addPhoneRow">
          번호추가
        </MsfButton>
      </template>
    </MsfTitleArea>
    <MsfCustomScroll height="268px" class="block-list-wrap">
      <ul class="block-list">
        <li v-for="(row, index) in phoneRows" :key="row.id">
          <MsfStack type="field">
            <MsfNumberInput
              v-model="row.number1"
              placeholder="앞자리"
              ariaLabel="수신차단 번호 앞자리"
              maxlength="11"
              class="ut-w-140"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              v-model="row.number2"
              placeholder="중간 자리"
              ariaLabel="수신차단 번호 중간 자리"
              maxlength="4"
              class="ut-w-140"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              v-model="row.number3"
              placeholder="뒷 자리"
              ariaLabel="수신차단 번호 뒷 자리"
              maxlength="4"
              class="ut-w-140"
            />
          </MsfStack>
          <div class="side">
            <MsfButton
              iconOnly="clear"
              variant="ghost"
              size="small"
              @click="removePhoneRow(index)"
            />
          </div>
        </li>
      </ul>
    </MsfCustomScroll>
    <MsfBox>
      <MsfTextList
        :items="[
          `수신차단 번호는 최대 ${maxCount}개까지 설정 가능하며, 등록한 번호로 수신되는 음성통화, 문자메시지, 음성사서함 모두 차단합니다.`,
          '각 자리별 입력된 번호를 포함하는 번호는 모두 차단합니다.(자리별 부분 차단 가능)',
        ]"
        level="2"
      />
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
import { ref, watch } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'
import { normalizePhone } from '@/libs/utils/string.utils'

const props = defineProps({
  modelValue: Boolean,
  title: { type: String, default: '불법TM수신차단' },
  settingData: {
    type: Object,
    default: () => ({}),
  },
  maxCount: { type: Number, default: 50 },
  minLength: { type: Number, default: 9 },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const createPhoneRow = () => ({
  id: `${Date.now()}-${Math.random()}`,
  number1: '',
  number2: '',
  number3: '',
})

const phoneRows = ref([createPhoneRow()])

const normalizeDigits = normalizePhone

const getBlockNumberType = (value) => {
  if (value.startsWith('*') && value.endsWith('*')) return 'middle'
  if (value.startsWith('*')) return 'rear'
  return 'front'
}

const toPhoneRow = (blockNumber = '') => {
  const value = String(blockNumber || '').trim()
  const digits = normalizeDigits(value)

  return {
    type: getBlockNumberType(value),
    value: digits,
  }
}

const createRowsFromBlockNumbers = (blockNumbers = []) => {
  const rows = []
  let currentRow = createPhoneRow()
  let hasValue = false

  blockNumbers
    .map(toPhoneRow)
    .filter((item) => item.value)
    .forEach(({ type, value }) => {
      const fieldName =
        type === 'middle' ? 'number2' : type === 'rear' ? 'number3' : 'number1'

      if (currentRow[fieldName]) {
        rows.push(currentRow)
        currentRow = createPhoneRow()
        hasValue = false
      }

      currentRow[fieldName] = value
      hasValue = true
    })

  if (hasValue) rows.push(currentRow)

  return rows.length > 0 ? rows : [createPhoneRow()]
}

const getBlockNumbersFromParamSbst = (paramSbst = '') =>
  String(paramSbst || '')
    .split('|')
    .map((item) => item.split('='))
    .filter(([key]) => /^BLCK_NO\d+$/.test(String(key || '').trim()))
    .sort(([leftKey], [rightKey]) => Number(leftKey.replace(/\D/g, '')) - Number(rightKey.replace(/\D/g, '')))
    .map(([, value]) => String(value || '').trim())
    .filter(Boolean)

const getBlockNumbersFromBlckNoParams = (blckNoParams = {}) =>
  Object.entries(blckNoParams || {})
    .filter(([key]) => /^BLCK_NO\d+$/.test(key))
    .sort(([leftKey], [rightKey]) => Number(leftKey.replace(/\D/g, '')) - Number(rightKey.replace(/\D/g, '')))
    .map(([, value]) => String(value || '').trim())
    .filter(Boolean)

const getBlockNumbersFromSettingData = (settingData = {}) => {
  if (Array.isArray(settingData.blockNumbers) && settingData.blockNumbers.length > 0) {
    return settingData.blockNumbers
  }

  if (Array.isArray(settingData.phoneNumbers) && settingData.phoneNumbers.length > 0) {
    return settingData.phoneNumbers.flatMap(({ number1, number2, number3 }) => {
      const blockNumbers = []
      if (normalizeDigits(number1)) blockNumbers.push(normalizeDigits(number1))
      if (normalizeDigits(number2)) blockNumbers.push(`*${normalizeDigits(number2)}*`)
      if (normalizeDigits(number3)) blockNumbers.push(`*${normalizeDigits(number3)}`)
      return blockNumbers
    })
  }

  const blckNoNumbers = getBlockNumbersFromBlckNoParams(settingData.blckNoParams || settingData)
  if (blckNoNumbers.length > 0) return blckNoNumbers

  const paramSbstNumbers = getBlockNumbersFromParamSbst(settingData.paramSbst || settingData.paramSbstCtt || '')
  if (paramSbstNumbers.length > 0) return paramSbstNumbers

  return String(settingData.ftrNewParam || '')
    .split(':')
    .map((value) => value.trim())
    .filter(Boolean)
}

const resetPhoneRows = () => {
  const blockNumbers = getBlockNumbersFromSettingData(props.settingData)
  phoneRows.value = createRowsFromBlockNumbers(blockNumbers)
}

const getFilledPhoneRows = () =>
  phoneRows.value
    .map((row) => ({
      ...row,
      frontNumber: normalizeDigits(row.number1),
      middleNumber: normalizeDigits(row.number2),
      rearNumber: normalizeDigits(row.number3),
    }))
    .filter(({ frontNumber, middleNumber, rearNumber }) => frontNumber || middleNumber || rearNumber)

const createBlockNumbers = (rows = getFilledPhoneRows()) =>
  rows.flatMap(({ frontNumber, middleNumber, rearNumber }) => {
    const blockNumbers = []

    if (frontNumber) blockNumbers.push(frontNumber)
    if (middleNumber) blockNumbers.push(`*${middleNumber}*`)
    if (rearNumber) blockNumbers.push(`*${rearNumber}`)

    return blockNumbers
  })

const getPhoneRowValidationMessage = () => {
  const filledRows = getFilledPhoneRows()

  if (filledRows.length === 0) {
    return '앞자리, 중간자리, 뒷자리 중 하나 이상 입력해 주세요.'
  }

  if (filledRows.length > props.maxCount) {
    return `번호 입력 항목은 최대 ${props.maxCount}개까지 설정 가능합니다.`
  }

  // minLength 검증: 입력된 각 필드의 자리수 확인
  if (props.minLength > 0) {
    for (const row of filledRows) {
      const { frontNumber, middleNumber, rearNumber } = row
      const fields = [frontNumber, middleNumber, rearNumber].filter(Boolean)
      for (const field of fields) {
        if (field.length < props.minLength) {
          return `${props.minLength}자리 이상 입력해 주세요.`
        }
      }
    }
  }

  return ''
}

const createBlckNoParams = (blockNumbers = []) =>
  Array.from({ length: props.maxCount }).reduce((params, _, index) => {
    params[`BLCK_NO${index + 1}`] = blockNumbers[index] || ''
    return params
  }, {})

const createParamSbst = (blckNoParams = {}) =>
  Object.entries(blckNoParams)
    .map(([key, value]) => `${key}=${value}`)
    .join('|')

const addPhoneRow = () => {
  if (phoneRows.value.length >= props.maxCount) {
    showAlert(`번호 입력 항목은 최대 ${props.maxCount}개까지 추가 가능합니다.`)
    return
  }

  phoneRows.value.push(createPhoneRow())
}

const removePhoneRow = (index) => {
  phoneRows.value.splice(index, 1)
  if (phoneRows.value.length === 0) {
    phoneRows.value.push(createPhoneRow())
  }
}

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const isFormReset = ref(false)

const onReset = () => {
  isFormReset.value = true
  phoneRows.value = [createPhoneRow()]
}

const onConfirm = () => {
  if (isFormReset.value) {
    emit('confirm', { isReset: true })
    onClose()
    return
  }
  const validationMessage = getPhoneRowValidationMessage()

  if (validationMessage) {
    showAlert(validationMessage)
    return
  }

  const filledRows = getFilledPhoneRows()
  const blockNumbers = createBlockNumbers(filledRows)

  if (blockNumbers.length > props.maxCount) {
    showAlert(`차단번호는 최대 ${props.maxCount}개까지 설정 가능합니다.`)
    return
  }

  const blckNoParams = createBlckNoParams(blockNumbers)

  emit('confirm', {
    phoneNumbers: filledRows.map(({ frontNumber, middleNumber, rearNumber }) => ({
      number1: frontNumber,
      number2: middleNumber,
      number3: rearNumber,
    })),
    blockNumbers,
    ftrNewParam: blockNumbers.join(':'),
    blckNoParams,
    paramSbst: createParamSbst(blckNoParams),
  })
  onClose()
}

watch(
  () => props.modelValue,
  (isOpen) => {
    if (isOpen) {
      isFormReset.value = false
      resetPhoneRows()
    }
  },
  { immediate: true },
)
</script>

<style lang="scss" scoped></style>
