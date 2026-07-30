<template>
  <div v-if="['NM', 'FM'].includes(model.cstmrTypeCd)">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfStack type="field">
          <MsfInput
            ref="repNameRef"
            id="inp-repName"
            v-model="model.repName"
            placeholder="이름"
            class="ut-w-300"
            :maxlength="repNameMaxlength"
            :readonly="isRepBasicReadonly"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup
        label="주민등록번호/<br/>외국인등록번호"
        required
        v-if="!useBirthDate && !model.isTrCustomer"
      >
        <MsfStack type="field">
          <MsfRegNoInput
            ref="combinedNoRef"
            v-model:registNo1="combinedNo1"
            v-model:registNo2="combinedNo2"
            :type="model.cstmrTypeCd === 'FM' ? 'foreigner' : 'resident'"
            :readonly="
              props.disabled ||
              isRepAuthCompleted ||
              (model.identityCertTypeCd !== 'S' && model.isSaved) ||
              model.identityCertTypeCd !== 'S'
            "
          />
          <!-- <MsfNumberInput
            ref="combinedNo1Ref"
            id="inp-combinedNo1"
            v-model="combinedNo1"
            placeholder="앞 6자리"
            maxlength="6"
            :readonly="props.disabled || isRepAuthCompleted || model.isSaved || model.identityCertTypeCd !== 'S'"
            @maxlength="combinedNo2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="combinedNo2Ref"
            v-model="combinedNo2"
            id="inp-combinedNo2"
            type="password"
            placeholder="뒤 7자리"
            maxlength="7"
            :readonly="props.disabled || isRepAuthCompleted || model.isSaved || model.identityCertTypeCd !== 'S'"
          /> -->
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup v-if="useBirthDate && !model.isTrCustomer" label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            ref="repBirthDateRef"
            id="inp-repBirthDate"
            v-model="model.repBirthDate"
            length="8"
            class="ut-w-300"
            placeholder="8자리(YYYYMMDD)"
            :readonly="isRepBasicReadonly"
          />
          <MsfRadioGroup
            v-if="showBirthDateGender"
            :name="`${name}-rep-gender`"
            v-model="model.repGender"
            :options="[
              { value: 'M', label: '남' },
              { value: 'F', label: '여' },
            ]"
            class="ut-ml-16"
            :disabled="isRepBasicReadonly"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup v-if="model.isTrCustomer" label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            ref="minorUserBirthDateRef"
            v-model="model.minorUserBirthDate"
            length="8"
            class="ut-w-300"
            placeholder="8자리(YYYYMMDD)"
            :readonly="isRepAuthCompleted || (model.identityCertTypeCd !== 'S' && model.isSaved)"
          />
          <MsfRadioGroup
            :name="`${name}-user-gender`"
            v-model="model.minorUserGender"
            :options="[
              { value: 'M', label: '남' },
              { value: 'F', label: '여' },
            ]"
            class="ut-ml-16"
            :disabled="isRepAuthCompleted || (model.identityCertTypeCd !== 'S' && model.isSaved)"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="신청인과의 관계" tag="div" required>
        <MsfSelect
          ref="minorAgentRelTypeCdRef"
          id="inp-repRelation"
          title="신청인과의 관계"
          v-model="model.minorAgentRelTypeCd"
          groupCode="AGR"
          placeholder="선택"
          class="ut-w-300"
          :disabled="isRepAuthCompleted || props.disabled"
        />
      </MsfFormGroup>
      <div id="inp-repPhone">
        <MsfMobileAuthNumber
          ref="minorAgentTelNoRef"
          :key="props.resetKey"
          v-model:name="model.repName"
          v-model:phone1="model.minorAgentTelFnNo"
          v-model:phone2="model.minorAgentTelMnNo"
          v-model:phone3="model.minorAgentTelRnNo"
          form-type="F-1-VDP"
          :before-send="validateAdultForAuth"
          @complete="onComplete"
        />
      </div>
    </MsfStack>
    <MsfTitleArea :title="agreementTitle" />
    <MsfAgreementItem
      ref="repAgreeRef"
      type="default"
      v-model="model.repAgree"
      name="본인은 안내사항을 확인하였습니다"
      required="Y"
      :popTitle="agreementTitle"
      :content="termsItem?.content"
      :groupCode="termsItem?.groupCode"
      :code="termsItem?.code"
      :termsGroupCd="termsItem?.termsGroupCd"
      :termsItemCd="termsItem?.termsItemCd"
      :version="termsItem?.version"
      :disabled="props.disabled"
    />
  </div>
</template>
<script setup>
import { ref, defineModel, defineProps, computed, onBeforeMount, watch } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  title: { type: String, default: '법정대리인 정보' },
  agreementTitle: { type: String, default: '법정대리인 안내사항 확인 및 동의' },
  name: { type: String, default: 'basic' },
  useBirthDate: { type: Boolean, default: false },
  showBirthDateGender: { type: Boolean, default: false },
  editableBasicFields: { type: Boolean, default: false },
  readonlyBasicFields: { type: Boolean, default: false },
  repNameMaxlength: { type: [Number, String], default: 100 },
  externalAuthFlags: { type: Object, default: null },
  lockFieldsOnAuth: { type: Boolean, default: false },
  resetKey: { type: Number, default: 0 },
  preCheckAuthFunc: { type: Function, default: null },
  authFlags: { type: Object, default: () => {} },
  disabled: Boolean,
})
const model = defineModel({ type: Object, required: true })

const repNameRef = ref(null)
// const combinedNo1Ref = ref(null)
// const combinedNo2Ref = ref(null)
const combinedNoRef = ref(null)
const repBirthDateRef = ref(null)
const minorAgentRelTypeCdRef = ref(null)
const minorUserBirthDateRef = ref(null)
const minorAgentTelNoRef = ref(null)
const repAgreeRef = ref(null)

const store = useMsfFormNewChgStore()
const termsItem = ref(null)
const effectiveAuthFlags = computed(() => props.externalAuthFlags || store.authFlags)
const isRepAuthCompleted = computed(() => {
  if (model.value.isTeCustomer && !model.value.isSaved) return false
  return effectiveAuthFlags.value?.repPhone === true
})

const isMinorCustomer = computed(() => ['NM', 'FM'].includes(model.value.cstmrTypeCd))

const isRepBasicReadonly = computed(() => {
  if (props.disabled) return true
  // 인증 예외('S')인 경우 임시저장(isSaved) 상태이더라도 무조건 수정 가능(readonly = false)
  if (model.value.identityCertTypeCd === 'S') return false

  if (model.value.isTrCustomer && !isRepAuthCompleted.value && !model.value.isSaved) return false

  if (model.value.isTeCustomer && !model.value.isSaved && model.value.identityCertTypeCd === 'S')
    return false

  return (
    props.disabled ||
    isRepAuthCompleted.value ||
    model.value.isSaved ||
    props.readonlyBasicFields ||
    (model.value.isTeCustomer && model.value.identityCertTypeCd !== 'S') ||
    (isMinorCustomer.value &&
      !props.editableBasicFields &&
      model.value.identityCertTypeCd !== 'S') ||
    (!isMinorCustomer.value &&
      !props.editableBasicFields &&
      model.value.identityCertTypeCd !== 'S' &&
      !model.value.isTrCustomer)
  )
})

watch(
  () => [props.showBirthDateGender, model.value.cstmrTypeCd],
  () => {
    if (props.showBirthDateGender && isMinorCustomer.value && !model.value.repGender) {
      model.value.repGender = 'M'
    }
  },
  { immediate: true },
)

// repName과 minorAgentNm 동기화 (기존 로직 및 서버 페이로드 호환성 유지)
watch(
  () => model.value.repName,
  (newVal) => {
    model.value.minorAgentNm = newVal
  },
)

//const phoneData = computed({
//  get() {
//    return {
//      phone1: model.value.minorAgentTelFnNo,
//      phone2: model.value.minorAgentTelMnNo,
//      phone3: model.value.minorAgentTelRnNo,
//    }
//  },
//  set(val) {
//    model.value.minorAgentTelFnNo = val.phone1
//    model.value.minorAgentTelMnNo = val.phone2
//    model.value.minorAgentTelRnNo = val.phone3
//  },
//})

const combinedNo1 = computed({
  get() {
    return model.value.repRegistrationNo1 || model.value.repForeignerNo1 || ''
  },
  set(val) {
    // 뒤자리가 아직 입력되지 않았거나 첫 자리가 내국인(1,2,3,4) 형태면 주민번호 필드 우선 사용
    const firstDigit = (combinedNo2.value || '').charAt(0)
    if (['5', '6', '7', '8'].includes(firstDigit)) {
      model.value.repForeignerNo1 = val
      model.value.repRegistrationNo1 = ''
    } else {
      model.value.repRegistrationNo1 = val
      model.value.repForeignerNo1 = ''
    }
  },
})

const combinedNo2 = computed({
  get() {
    return model.value.repRegistrationNo2 || model.value.repForeignerNo2 || ''
  },
  set(val) {
    const firstDigit = (val || '').charAt(0)
    if (['5', '6', '7', '8'].includes(firstDigit)) {
      model.value.repForeignerNo2 = val
      model.value.repForeignerNo1 = combinedNo1.value
      model.value.repRegistrationNo2 = ''
      model.value.repRegistrationNo1 = ''
    } else {
      model.value.repRegistrationNo2 = val
      model.value.repRegistrationNo1 = combinedNo1.value
      model.value.repForeignerNo2 = ''
      model.value.repForeignerNo1 = ''
    }
  },
})

const checkIsAdult = (birthStr) => {
  if (birthStr.length !== 8) return false
  const yyyy = parseInt(birthStr.substring(0, 4), 10)
  const mm = parseInt(birthStr.substring(4, 6), 10)
  const dd = parseInt(birthStr.substring(6, 8), 10)

  const today = new Date()
  const birthDate = new Date(yyyy, mm - 1, dd)

  let age = today.getFullYear() - birthDate.getFullYear()
  const m = today.getMonth() - birthDate.getMonth()
  if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
    age--
  }
  return age >= 19
}

const getLegalAgentBirthDate = () => {
  if (props.useBirthDate) {
    return String(model.value.repBirthDate || '').replace(/[^0-9]/g, '')
  }
  return getBirthDateFromRrn(combinedNo1.value, combinedNo2.value) || ''
}

const focusLegalAgentBirthField = () => {
  document.getElementById(props.useBirthDate ? 'inp-repBirthDate' : 'inp-combinedNo1')?.focus()
}

const validateAdultForAuth = () => {
  // 가입자 신분증인증 완료 여부 체크 (서류인증 'S'인 경우는 제외)
  if (model.value.identityCertTypeCd !== 'S' && !model.value.isVerified) {
    showAlert('가입자 신분증 인증을 먼저 완료해 주세요.')
    return false
  }

  // 명의변경 인증 사용
  if (props.preCheckAuthFunc) {
    return props.preCheckAuthFunc()
  }

  // 관계코드 입력 여부 먼저 체크
  if (!model.value.minorAgentRelTypeCd) {
    showAlert('신청인과의 관계를 선택해 주세요.', () => {
      minorAgentRelTypeCdRef.value?.focus()
    })
    return false
  }

  if (!['NM', 'FM'].includes(model.value.cstmrTypeCd)) return true

  if (model.value.minorUserBirthDate) return true

  const birthStr = getLegalAgentBirthDate()
  if (checkIsAdult(birthStr)) return true

  showAlert('법정대리인은 만 19세 이상 성인만 등록 가능합니다.', focusLegalAgentBirthField)
  return false
}

const onComplete = (result) => {
  if (effectiveAuthFlags.value) {
    effectiveAuthFlags.value.repPhone = result
  }
  if (result) {
    model.value.isVerified = true
  }
  if (store.authFlags) {
    store.authFlags.repPhone = result
  }
}

const getBirthDateFromRrn = (rrn1, rrn2) => {
  if (rrn1.length !== 6 || rrn2.length < 1) return null
  const genderDigit = rrn2.charAt(0)
  const yy = parseInt(rrn1.substring(0, 2), 10)
  const currentYearShort = new Date().getFullYear() % 100 // 2026년 기준 26
  let yyyy

  if (['1', '2', '5', '6'].includes(genderDigit)) {
    yyyy = `19${rrn1.substring(0, 2)}`
  } else if (['3', '4', '7', '8'].includes(genderDigit)) {
    // 성별 코드가 3, 4, 7, 8(2000년대생)이더라도 앞자리 연도가 현재 연도보다 크면 1900년대생으로 판단 (예: 84년생인데 뒷자리를 3 또는 4로 입력한 경우 방어)
    if (yy > currentYearShort) {
      yyyy = `19${rrn1.substring(0, 2)}`
    } else {
      yyyy = `20${rrn1.substring(0, 2)}`
    }
  } else {
    // 기타 예외 케이스는 연도 대소 비교로 19xx / 20xx 결정
    yyyy = (yy > currentYearShort ? '19' : '20') + rrn1.substring(0, 2)
  }
  const mmdd = rrn1.substring(2, 6)
  return `${yyyy}${mmdd}`
}

const validate = () => {
  if (['NM', 'FM'].includes(model.value.cstmrTypeCd)) {
    if (model.value.isTrCustomer) {
      if (!model.value.repName) return false
      if (!model.value.minorAgentRelTypeCd) return false
      if (!model.value.minorUserBirthDate) return false
      if (!effectiveAuthFlags.value?.repPhone) return false
      if (!model.value.repAgree) return false
    } else {
      if (!model.value.repName) return false
      let birthStr = ''
      if (props.useBirthDate) {
        if (!model.value.repBirthDate) return false
        birthStr = model.value.repBirthDate.replace(/[^0-9]/g, '')
        if (props.showBirthDateGender && !model.value.repGender) return false
      } else {
        if (!combinedNo1.value || !combinedNo2.value) {
          return false
        }
        const parsedBirth = getBirthDateFromRrn(combinedNo1.value, combinedNo2.value)
        if (parsedBirth) {
          birthStr = parsedBirth
        }
      }
      console.log('birthStr:', birthStr)

      // // 성인 검증
      // if (birthStr && birthStr.length === 8) {
      //   if (!checkIsAdult(birthStr)) {
      //     showAlert('법정대리인은 만 19세 이상 성인만 등록 가능합니다.', () => {
      //       if (props.useBirthDate) {
      //         repBirthDateRef.value?.focus()
      //       } else {
      //         combinedNo1Ref.value?.focus()
      //       }
      //     })
      //     return false
      //   }
      // }

      if (!model.value.minorAgentRelTypeCd) return false
      if (!effectiveAuthFlags.value?.repPhone) return false
      if (!model.value.repAgree) return false
    }
  }
  return true
}

onBeforeMount(async () => {
  try {
    const res = await post('/api/shared/form/common/terms/list', {
      groupCode: 'CLAUSE_MINOR_AGENT',
    })
    // 응답 데이터 구조에 맞춰 유연하게 처리
    const list = res.data || []
    const codes = Array.isArray(list) ? list : list.codes || []

    if (codes.length > 0) {
      termsItem.value = codes[0]
    }
  } catch (e) {
    console.error('Failed to fetch legal agent terms:', e)
  }
})

const checkValidation = (preCheck = false) => {
  if (!['NM', 'FM'].includes(model.value.cstmrTypeCd)) {
    return true
  }

  if (!model.value.repName) {
    showAlert('법정대리인 이름을 입력하세요', () => {
      repNameRef.value?.focus()
    })
    return false
  }

  if (!props.useBirthDate && !model.value.isTrCustomer && !combinedNoRef.value?.isValid) {
    showAlert('법정대리인 주민등록번호/외국인등록번호를 입력하세요', () => {
      combinedNoRef.value?.focus()
    })
    return false
  }
  if (!props.useBirthDate && !model.value.isTrCustomer && !combinedNoRef.value?.isAdult) {
    showAlert(
      '법정대리인 주민등록번호/외국인등록번호는 만 19세 이상만 입력 가능합니다. 법정대리인 주민등록번호/외국인등록번호를 다시 입력하세요',
      () => {
        combinedNoRef.value?.focus()
      },
    )
    return false
  }

  if (props.useBirthDate && !model.value.isTrCustomer && !model.value.repBirthDate) {
    showAlert('법정대리인 생년월일을 입력하세요', () => {
      repBirthDateRef.value?.focus()
    })
    return false
  }
  if (props.useBirthDate && !model.value.isTrCustomer && !repBirthDateRef.value?.isValid) {
    showAlert(`법정대리인 생년월일을 ${repBirthDateRef.value?.length}자리로 입력하세요`, () => {
      repBirthDateRef.value?.focus()
    })
    return false
  }
  if (props.useBirthDate && !model.value.isTrCustomer && !repBirthDateRef.value?.isAdult) {
    showAlert(
      '법정대리인 주민등록번호/외국인등록번호는 만 19세 이상만 입력 가능합니다. 법정대리인 생년월일을 다시 입력하세요',
      () => {
        repBirthDateRef.value?.focus()
      },
    )
    return false
  }

  if (model.value.isTrCustomer && !model.value.minorUserBirthDate) {
    showAlert('법정대리인 생년월일을 입력하세요', () => {
      minorUserBirthDateRef.value?.focus()
    })
    return false
  }
  if (model.value.isTrCustomer && !minorUserBirthDateRef.value?.isValid) {
    showAlert(
      `법정대리인 생년월일을 ${minorUserBirthDateRef.value?.length}자리로 입력하세요`,
      () => {
        minorUserBirthDateRef.value?.focus()
      },
    )
    return false
  }
  if (model.value.isTrCustomer && !minorUserBirthDateRef.value?.isAdult) {
    showAlert(
      '법정대리인 주민등록번호/외국인등록번호는 만 19세 이상만 입력 가능합니다. 법정대리인 생년월일을 다시 입력하세요',
      () => {
        minorUserBirthDateRef.value?.focus()
      },
    )
    return false
  }

  if (!model.value?.minorAgentRelTypeCd) {
    showAlert('신청인과의 관계를 선택하세요', () => {
      minorAgentRelTypeCdRef.value?.focus()
    })
    return false
  }

  if (
    !model.value?.minorAgentTelFnNo ||
    !model.value?.minorAgentTelMnNo ||
    !model.value?.minorAgentTelRnNo
  ) {
    showAlert('법정대리인 연락처(휴대폰)를 입력하세요', () => {
      minorAgentTelNoRef.value?.focus()
    })
    return false
  }

  if (!preCheck) {
    if (!effectiveAuthFlags.value?.repPhone) {
      showAlert('법정대리인 연락처(휴대폰)를 인증하세요', () => {
        minorAgentTelNoRef.value?.focus()
      })
      return false
    }
    if (!model.value.repAgree) {
      showAlert('법정대리인 안내사항 동의를 선택하세요', () => {
        repAgreeRef.value?.focus()
      })
      return false
    }
  }

  return true
}

defineExpose({ validate, checkValidation })
</script>
