<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="불법TM수신차단"
    @open="emit('open')"
    @close="onClose"
  >
    <MsfTitleArea level="2" noline>
      <template #title>
        <span>수신차단 번호 입력(<span class="ut-color-point">{{ phoneRows.length }}</span>/50)</span>
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
          '수신차단 번호는 최대 50개까지 설정 가능하며, 등록한 번호로 수신되는 음성통화, 문자메시지, 음성사서함 모두 차단합니다.',
          '각 자리별 입력된 번호를 포함하는 번호는 모두 차단합니다.(자리별 부분 차단 가능)',
        ]"
        level="2"
      />
    </MsfBox>
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref, watch } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  modelValue: Boolean,
  settingData: {
    type: Object,
    default: () => ({}),
  },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const createPhoneRow = () => ({
  id: `${Date.now()}-${Math.random()}`,
  number1: '',
  number2: '',
  number3: '',
})

const createTestPhoneRows = () => [
  {
    ...createPhoneRow(),
    number1: '010',
    number2: '3333',
    number3: '1234',
  },
]

const phoneRows = ref(createTestPhoneRows())

const normalizeDigits = (value) => String(value || '').replace(/\D/g, '')

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

  return rows.length > 0 ? rows : createTestPhoneRows()
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

  if (filledRows.length > 50) {
    return '번호 입력 항목은 최대 50개까지 설정 가능합니다.'
  }

  return ''
}

const createBlckNoParams = (blockNumbers = []) =>
  Array.from({ length: 50 }).reduce((params, _, index) => {
    params[`BLCK_NO${index + 1}`] = blockNumbers[index] || ''
    return params
  }, {})

const createParamSbst = (blckNoParams = {}) =>
  Object.entries(blckNoParams)
    .map(([key, value]) => `${key}=${value}`)
    .join('|')

const addPhoneRow = () => {
  if (phoneRows.value.length >= 50) {
    showAlert('번호 입력 항목은 최대 50개까지 추가 가능합니다.')
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

const onConfirm = () => {
  const validationMessage = getPhoneRowValidationMessage()

  if (validationMessage) {
    showAlert(validationMessage)
    return
  }

  const filledRows = getFilledPhoneRows()
  const blockNumbers = createBlockNumbers(filledRows)

  if (blockNumbers.length > 50) {
    showAlert('차단번호는 최대 50개까지 설정 가능합니다.')
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
    if (isOpen) resetPhoneRows()
  },
)
</script>

<style lang="scss" scoped></style>
