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
        <span>수신차단 번호 입력(<span class="ut-color-point">{{ filledPhoneRows.length }}</span>/{{ maxCount }})</span>
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
              :ref="(el) => setPhoneInputRef(index, 'number1', el)"
              :model-value="row.number1"
              @update:model-value="(value) => updatePhoneRow(index, 'number1', value, PHONE_NUMBER_LENGTHS.number1)"
              @maxlength="focusPhoneInput(index, 'number2')"
              placeholder="앞자리"
              ariaLabel="수신차단 번호 앞자리"
              :maxlength="PHONE_NUMBER_LENGTHS.number1"
              class="ut-w-140"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              :ref="(el) => setPhoneInputRef(index, 'number2', el)"
              :model-value="row.number2"
              @update:model-value="(value) => updatePhoneRow(index, 'number2', value, PHONE_NUMBER_LENGTHS.number2)"
              @maxlength="focusPhoneInput(index, 'number3')"
              placeholder="중간 자리"
              ariaLabel="수신차단 번호 중간 자리"
              :maxlength="PHONE_NUMBER_LENGTHS.number2"
              class="ut-w-140"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              :ref="(el) => setPhoneInputRef(index, 'number3', el)"
              :model-value="row.number3"
              @update:model-value="(value) => updatePhoneRow(index, 'number3', value, PHONE_NUMBER_LENGTHS.number3)"
              placeholder="뒷 자리"
              ariaLabel="수신차단 번호 뒷 자리"
              :maxlength="PHONE_NUMBER_LENGTHS.number3"
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
        <MsfButton v-if="props.settingData?.showChangeCancel" variant="tertiary" @click="onReset">변경취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'
import { normalizePhone } from '@/libs/utils/string.utils'

const props = defineProps({
  modelValue: Boolean,
  title: { type: String, default: '불법TM수신차단' },
  settingData: {
    type: Object,
    default: () => ({}),
  },
  initialSettingData: {
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
const phoneInputRefs = {}

const PHONE_NUMBER_LENGTHS = {
  number1: 3,
  number2: 4,
  number3: 4,
}

const normalizeDigits = normalizePhone

const normalizeInputDigits = (value, maxLength) =>
  normalizeDigits(value).slice(0, maxLength)

const updatePhoneRow = (index, fieldName, value, maxLength) => {
  const row = phoneRows.value[index]
  if (!row) return

  row[fieldName] = normalizeInputDigits(value, maxLength)
}

const setPhoneInputRef = (index, fieldName, element) => {
  phoneInputRefs[index] = {
    ...phoneInputRefs[index],
    [fieldName]: element,
  }
}

const focusPhoneInput = (index, fieldName) => {
  phoneInputRefs[index]?.[fieldName]?.focus?.()
}

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

      if (type === 'front' && value.length > PHONE_NUMBER_LENGTHS.number1) {
        if (hasValue) {
          rows.push(currentRow)
          currentRow = createPhoneRow()
        }

        currentRow.number1 = normalizeInputDigits(value.slice(0, 3), PHONE_NUMBER_LENGTHS.number1)
        currentRow.number2 = normalizeInputDigits(value.slice(3, 7), PHONE_NUMBER_LENGTHS.number2)
        currentRow.number3 = normalizeInputDigits(value.slice(7, 11), PHONE_NUMBER_LENGTHS.number3)
        rows.push(currentRow)
        currentRow = createPhoneRow()
        hasValue = false
        return
      }

      if (currentRow[fieldName]) {
        rows.push(currentRow)
        currentRow = createPhoneRow()
        hasValue = false
      }

      currentRow[fieldName] = normalizeInputDigits(value, PHONE_NUMBER_LENGTHS[fieldName])
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

const resetPhoneRows = (settingData = props.settingData) => {
  const blockNumbers = getBlockNumbersFromSettingData(settingData)
  phoneRows.value = createRowsFromBlockNumbers(blockNumbers)
}

const filledPhoneRows = computed(() =>
  phoneRows.value
    .map((row) => ({
      ...row,
      frontNumber: normalizeDigits(row.number1),
      middleNumber: normalizeDigits(row.number2),
      rearNumber: normalizeDigits(row.number3),
    }))
    .filter(({ frontNumber, middleNumber, rearNumber }) => frontNumber || middleNumber || rearNumber)
)

const getFilledPhoneRows = () => filledPhoneRows.value

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
    params[`BLCK_TYPE${index + 1}`] = blockNumbers[index] ? '3' : ''
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
}

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onReset = () => {
  resetPhoneRows(
    Object.keys(props.initialSettingData).length ? props.initialSettingData : props.settingData,
  )
}

const onConfirm = () => {
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
      resetPhoneRows()
    }
  },
  { immediate: true },
)
</script>

<style lang="scss" scoped></style>
