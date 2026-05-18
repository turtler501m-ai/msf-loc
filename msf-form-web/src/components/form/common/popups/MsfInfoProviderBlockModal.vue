<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="정보제공사업자번호차단서비스"
    @open="emit('open')"
    @close="onClose"
  >
    <MsfTitleArea level="2" noline>
      <template #title>
        <span>수신차단 번호 입력(<span class="ut-color-point">{{ blockRows.length }}</span>/{{ maxCount }})</span>
      </template>
      <template #actions>
        <MsfButton variant="subtle" @click="addBlockRow">
          번호추가
        </MsfButton>
      </template>
    </MsfTitleArea>
    <MsfCustomScroll height="268px" class="block-list-wrap">
      <ul class="block-list">
        <li v-for="(row, index) in blockRows" :key="row.id">
          <MsfStack vertical gap="4">
            <MsfStack type="field">
              <MsfNumberInput
                v-model="row.number1"
                placeholder="앞자리"
                ariaLabel="수신차단 번호 앞자리"
                maxlength="3"
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
            <MsfRadioGroup
              :name="`block-type-${row.id}`"
              v-model="row.blockType"
              :options="blockTypeOptions"
            />
          </MsfStack>
          <div class="side">
            <MsfButton
              iconOnly="clear"
              variant="ghost"
              size="small"
              @click="removeBlockRow(index)"
            />
          </div>
        </li>
      </ul>
    </MsfCustomScroll>
    <MsfBox>
      <MsfTextList
        :items="[
          `수신차단 번호는 최대 ${maxCount}개까지 설정 가능하며, 설정한 음성, 문자 여부에 따라 차단합니다.`,
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
  settingData: {
    type: Object,
    default: () => ({}),
  },
  maxCount: { type: Number, default: 10 },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const blockTypeOptions = [
  { value: '1', label: '음성only' },
  { value: '2', label: '문자only' },
  { value: '3', label: '음성+문자' },
]

const createBlockRow = () => ({
  id: `${Date.now()}-${Math.random()}`,
  number1: '',
  number2: '',
  number3: '',
  blockType: '3', // 기본값: 음성+문자
})

const blockRows = ref([createBlockRow()])

const normalizeDigits = normalizePhone

const toBlockRow = (blockNumber = '', blockType = '3') => {
  const value = String(blockNumber || '').trim()
  const digits = normalizeDigits(value)

  return {
    type: getBlockNumberType(value),
    value: digits,
    blockType: String(blockType || '3'),
  }
}

const getBlockNumberType = (value) => {
  if (value.startsWith('*') && value.endsWith('*')) return 'middle'
  if (value.startsWith('*')) return 'rear'
  return 'front'
}

const createRowsFromBlockData = (blockNumbers = [], blockTypes = []) => {
  const rows = []
  let currentRow = createBlockRow()
  let hasValue = false

  blockNumbers
    .map((num, idx) => ({
      ...toBlockRow(num, blockTypes[idx]),
      originalBlockType: blockTypes[idx],
    }))
    .filter((item) => item.value)
    .forEach(({ type, value, originalBlockType }) => {
      const fieldName =
        type === 'middle' ? 'number2' : type === 'rear' ? 'number3' : 'number1'

      if (currentRow[fieldName]) {
        rows.push(currentRow)
        currentRow = createBlockRow()
        hasValue = false
      }

      currentRow[fieldName] = value
      currentRow.blockType = originalBlockType || '3'
      hasValue = true
    })

  if (hasValue) rows.push(currentRow)

  return rows.length > 0 ? rows : [createBlockRow()]
}

const getBlockNumbersFromFtrNewParam = (ftrNewParam = '') =>
  String(ftrNewParam || '')
    .split(':')
    .map((item) => item.trim())
    .filter(Boolean)

const getBlockDataFromRows = (rows = []) => {
  const blockNumbers = []
  const blockTypes = []

  rows.forEach((row) => {
    const blockType = row.blockType || '3'
    const frontNumber = normalizeDigits(row.number1)
    const middleNumber = normalizeDigits(row.number2)
    const rearNumber = normalizeDigits(row.number3)

    if (frontNumber) {
      blockNumbers.push(frontNumber)
      blockTypes.push(blockType)
    }
    if (middleNumber) {
      blockNumbers.push(`*${middleNumber}*`)
      blockTypes.push(blockType)
    }
    if (rearNumber) {
      blockNumbers.push(`*${rearNumber}`)
      blockTypes.push(blockType)
    }
  })

  return { blockNumbers, blockTypes }
}

const getBlockDataFromSettingData = (settingData = {}) => {
  if (
    Array.isArray(settingData.blockRows) &&
    settingData.blockRows.length > 0
  ) {
    return getBlockDataFromRows(settingData.blockRows)
  }

  const ftrNewParamValue = settingData.ftrNewParam || ''
  if (ftrNewParamValue) {
    const parts = getBlockNumbersFromFtrNewParam(ftrNewParamValue)
    const blockNumbers = []
    const blockTypes = []

    for (let i = 0; i < parts.length; i += 2) {
      if (parts[i]) {
        blockNumbers.push(parts[i])
        blockTypes.push(parts[i + 1] || '3')
      }
    }

    return { blockNumbers, blockTypes }
  }

  return { blockNumbers: [], blockTypes: [] }
}

const resetBlockRows = () => {
  const { blockNumbers, blockTypes } = getBlockDataFromSettingData(props.settingData)
  blockRows.value = createRowsFromBlockData(blockNumbers, blockTypes)
  console.log('[부가서비스설정][MsfInfoProviderBlockModal.vue] 팝업 초기화', {
    popupId: 'MsfInfoProviderBlockModal.vue',
    screenId: 'S102030112',
    settingData: props.settingData,
    blockNumbers,
    blockTypes,
    rows: blockRows.value.map(({ number1, number2, number3, blockType }) => ({
      number1,
      number2,
      number3,
      blockType,
    })),
  })
}

const getFilledBlockRows = () =>
  blockRows.value
    .map((row) => ({
      ...row,
      frontNumber: normalizeDigits(row.number1),
      middleNumber: normalizeDigits(row.number2),
      rearNumber: normalizeDigits(row.number3),
    }))
    .filter(({ frontNumber, middleNumber, rearNumber }) => frontNumber || middleNumber || rearNumber)

const getBlockRowValidationMessage = () => {
  const filledRows = getFilledBlockRows()

  console.log('[부가서비스설정][MsfInfoProviderBlockModal.vue] 입력번호 검증 시작', {
    popupId: 'MsfInfoProviderBlockModal.vue',
    screenId: 'S102030112',
    rawRows: blockRows.value.map(({ number1, number2, number3, blockType }) => ({
      number1,
      number2,
      number3,
      blockType,
    })),
    normalizedRows: filledRows.map(({ frontNumber, middleNumber, rearNumber, blockType }) => ({
      frontNumber,
      middleNumber,
      rearNumber,
      totalDigits: (frontNumber?.length || 0) + (middleNumber?.length || 0) + (rearNumber?.length || 0),
      blockType,
    })),
  })

  if (filledRows.length === 0) {
    console.warn('[부가서비스설정][MsfInfoProviderBlockModal.vue] 입력번호 검증 실패', {
      reason: 'empty rows',
    })
    return '앞자리, 중간자리, 뒷자리 중 하나 이상 입력해 주세요.'
  }

  // NOSPAM3: 앞자리(010 등)는 제외하고 중간자리+뒷자리 합산 8자리 기준으로 검증한다.
  for (const row of filledRows) {
    const bodyDigits = (row.middleNumber?.length || 0) + (row.rearNumber?.length || 0)
    const invalidBodyFields = [
      { field: 'middleNumber', value: row.middleNumber },
      { field: 'rearNumber', value: row.rearNumber },
    ].filter(({ value }) => value && value.length < 3)

    if (invalidBodyFields.length > 0 || bodyDigits > 8) {
      console.warn('[부가서비스설정][MsfInfoProviderBlockModal.vue] 입력번호 검증 실패', {
        reason: invalidBodyFields.length > 0 ? 'invalid body field digit length' : 'body digit length exceeded',
        row: {
          frontNumber: row.frontNumber,
          middleNumber: row.middleNumber,
          rearNumber: row.rearNumber,
          bodyDigits,
          blockType: row.blockType,
        },
        invalidBodyFields,
      })
      return '번호는 3자리 이상 8자리 이하로 입력해 주세요.'
    }
  }

  if (filledRows.length > props.maxCount) {
    console.warn('[부가서비스설정][MsfInfoProviderBlockModal.vue] 입력번호 검증 실패', {
      reason: 'max count exceeded',
      rowCount: filledRows.length,
      maxCount: props.maxCount,
    })
    return `번호 입력 항목은 최대 ${props.maxCount}개까지 설정 가능합니다.`
  }

  console.log('[부가서비스설정][MsfInfoProviderBlockModal.vue] 입력번호 검증 통과', {
    rowCount: filledRows.length,
  })
  return ''
}

const createFtrNewParam = (rows = getFilledBlockRows()) => {
  const parts = []

  rows.forEach(({ frontNumber, middleNumber, rearNumber, blockType }) => {
    if (frontNumber) {
      parts.push(frontNumber)
      parts.push(blockType || '3')
    }
    if (middleNumber) {
      parts.push(`*${middleNumber}*`)
      parts.push(blockType || '3')
    }
    if (rearNumber) {
      parts.push(`*${rearNumber}`)
      parts.push(blockType || '3')
    }
  })

  return parts.join(':')
}

const addBlockRow = () => {
  if (blockRows.value.length >= props.maxCount) {
    showAlert(`번호 입력 항목은 최대 ${props.maxCount}개까지 추가 가능합니다.`)
    return
  }

  blockRows.value.push(createBlockRow())
}

const removeBlockRow = (index) => {
  blockRows.value.splice(index, 1)
  if (blockRows.value.length === 0) {
    blockRows.value.push(createBlockRow())
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
  blockRows.value = [createBlockRow()]
}

const onConfirm = () => {
  if (isFormReset.value) {
    emit('confirm', { isReset: true })
    onClose()
    return
  }
  console.log('[부가서비스설정][MsfInfoProviderBlockModal.vue] 확인 클릭', {
    popupId: 'MsfInfoProviderBlockModal.vue',
    screenId: 'S102030112',
    rawRows: blockRows.value.map(({ number1, number2, number3, blockType }) => ({
      number1,
      number2,
      number3,
      blockType,
    })),
  })

  const validationMessage = getBlockRowValidationMessage()

  if (validationMessage) {
    console.warn('[부가서비스설정][MsfInfoProviderBlockModal.vue] 확인 중단', {
      validationMessage,
    })
    showAlert(validationMessage)
    return
  }

  const filledRows = getFilledBlockRows()
  const ftrNewParam = createFtrNewParam(filledRows)

  console.log('[부가서비스설정][MsfInfoProviderBlockModal.vue] 확인 완료', {
    normalizedRows: filledRows.map(({ frontNumber, middleNumber, rearNumber, blockType }) => ({
      frontNumber,
      middleNumber,
      rearNumber,
      blockType,
    })),
    ftrNewParam,
  })

  emit('confirm', {
    blockRows: filledRows.map(({ frontNumber, middleNumber, rearNumber, blockType }) => ({
      number1: frontNumber,
      number2: middleNumber,
      number3: rearNumber,
      blockType,
    })),
    ftrNewParam,
  })
  onClose()
}

watch(
  () => props.modelValue,
  (isOpen) => {
    if (isOpen) {
      isFormReset.value = false
      resetBlockRows()
    }
  },
  { immediate: true },
)
</script>

<style lang="scss" scoped></style>
