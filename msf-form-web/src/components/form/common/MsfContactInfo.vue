<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="휴대폰번호" required helpText="※ 신청서 발송 요청시 추가로 발송">
        <MsfStack type="field">
          <MsfMobileInput
            ref="mobileNoRef"
            v-model:number1="model.mobileNo1"
            v-model:number2="model.mobileNo2"
            v-model:number3="model.mobileNo3"
            :readonly="props.disabled"
          />
          <!-- <MsfNumberInput
            ref="mobileNo1Ref"
            v-model="model.mobileNo1"
            id="inp-mobileNo1"
            placeholder="앞자리"
            maxlength="3"
            :disabled="true"
            @maxlength="mobileNo2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="mobileNo2Ref"
            v-model="model.mobileNo2"
            id="inp-mobileNo2"
            placeholder="가운데 4자리"
            maxlength="4"
            :readonly="props.disabled"
            @maxlength="mobileNo3Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="mobileNo3Ref"
            v-model="model.mobileNo3"
            id="inp-mobileNo3"
            placeholder="뒤 4자리"
            maxlength="4"
            :readonly="props.disabled"
          /> -->
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup
        v-if="showTelNo"
        label="전화번호"
        :required="['JP', 'GO'].includes(model.cstmrTypeCd)"
      >
        <MsfStack type="field">
          <MsfTelInput
            ref="telNoRef"
            v-model:telNo1="model.telNo1"
            v-model:telNo2="model.telNo2"
            v-model:telNo3="model.telNo3"
            :disabled="props.disabled"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup
        v-if="props.cstmrBillSendTypeCd === 'CB' || model?.cstmrBillSendTypeCd === 'CB'"
        label="이메일주소"
        helpText="※ 청구서를 이메일로 수신하기를 원하는경우 반드시 입력"
      >
        <MsfStack id="inp-emailAddr" type="field">
          <MsfEmailInput
            ref="emailAddrRef"
            v-model:emailId="model.emailAddr1"
            v-model:emailDomain="model.emailAddr2"
            :email-id-maxlength="100"
            :email-domain-maxlength="100"
            :disabled="false"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup
        v-if="showAddress && model.joinType !== 'HDN3' && model.joinType !== 'HCN3'"
        label="주소"
        tag="div"
        required
      >
        <MsfAddressInput
          ref="addressRef"
          v-model:address1="model.zipNo"
          v-model:address2="model.address"
          v-model:address3="model.detailAddress"
          :readonly="props.disabled"
          @search="onClickSearchAddressBtn"
        />
        <!-- <MsfStack type="field">
          <MsfInput
            v-model="model.zipNo"
            placeholder="우편번호"
            ariaLabel="우편번호 입력"
            disabled
          />
          <MsfButton variant="subtle" :disabled="props.disabled" @click="onClickSearchAddressBtn"
            >우편번호 찾기</MsfButton
          >
        </MsfStack>
        <MsfInput
          v-model="model.address"
          placeholder="주소"
          ariaLabel="주소 입력"
          class="ut-w100p"
          disabled
        />
        <MsfInput
          v-model="model.detailAddress"
          id="inp-detailAddress"
          placeholder="상세주소"
          ariaLabel="상세주소 입력"
          class="ut-w100p"
          maxlength="100"
          :readonly="props.disabled"
        /> -->
      </MsfFormGroup>
      <MsfFormGroup
        v-if="showForeignerInfo && ['FN', 'FM'].includes(model.cstmrTypeCd)"
        label="국가"
        tag="div"
        required
      >
        <MsfSelect
          ref="countryRef"
          title="국가"
          v-model="model.country"
          groupCode="NATIONLIST"
          placeholder="국가"
          class="ut-w-300"
          selectPopYn
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="
          showForeignerInfo &&
          ['FN', 'FM'].includes(model.cstmrTypeCd) &&
          !(model.productType === 'MM' && ['HDN3', 'HCN3'].includes(model.joinType))
        "
        label="체류기간"
        tag="div"
        required
      >
        <MsfDateRange
          ref="cstmrForeignerVdateRef"
          v-model:from="model.cstmrForeignerVdateStartDate"
          v-model:to="model.cstmrForeignerVdateEndDate"
          :disabled="props.disabled"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="showForeignerInfo && ['FN', 'FM'].includes(model.cstmrTypeCd)"
        label="비자"
        required
      >
        <MsfInput
          ref="visaTypeRef"
          v-model="model.visaType"
          placeholder="비자 입력"
          class="ut-w-300"
          maxlength="16"
          :readonly="props.disabled"
        />
      </MsfFormGroup>
    </MsfStack>

    <!-- 주소 검색 모달 -->
    <MsfAddressSearchPop
      v-model="showAddressSearchPop"
      :detail-address-required="props.detailAddressRequired"
      :address1="model.address"
      :address2="model.detailAddress"
      @confirm="onConfirmAddressSearchPop"
    />
  </div>
</template>
<script setup>
import { defineModel, defineProps, ref, watch, computed, onMounted } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'

const props = defineProps({
  emailIdMaxlength: { type: [Number, String], default: 100 },
  emailDomainMaxlength: { type: [Number, String], default: 100 },
  title: { type: String, default: '가입자 연락처' },
  cstmrBillSendTypeCd: { type: String, default: '' },
  detailAddressRequired: { type: Boolean, default: true },
  showTelNo: { type: Boolean, default: true },
  showAddress: { type: Boolean, default: true },
  showForeignerInfo: { type: Boolean, default: true },
  disabled: { type: Boolean, default: false },
})
const model = defineModel({ type: Object, required: true })

// isEmailRequired computed 프로퍼티는 다음버튼 전용 검증 분기로 대체되어 제거되었습니다.

// const mobileNo1Ref = ref(null)
// const mobileNo2Ref = ref(null)
// const mobileNo3Ref = ref(null)
const mobileNoRef = ref(null)
const telNoRef = ref(null)
const emailAddrRef = ref(null)
const addressRef = ref(null)
const countryRef = ref(null)
const cstmrForeignerVdateRef = ref(null)
const visaTypeRef = ref(null)

const showAddressSearchPop = ref(false)
const onClickSearchAddressBtn = () => {
  showAddressSearchPop.value = true
}
const onConfirmAddressSearchPop = (result) => {
  model.value.zipNo = result.zipNo
  model.value.address = result.address
  model.value.detailAddress = result.detailAddress
}

watch(
  () => model.value?.mobileNo1,
  () => {
    // 가입자 연락처 휴대폰번호 앞자리는 010으로 고정한다.
    if (model.value.mobileNo1 !== '010') {
      model.value.mobileNo1 = '010'
    }
  },
  { immediate: true },
)

const nationList = ref([])
onMounted(async () => {
  if (props.showForeignerInfo && ['FN', 'FM'].includes(model.value.cstmrTypeCd)) {
    nationList.value = await getCommonCodeList('NATIONLIST')
  }
})

watch(
  () => model.value.country,
  (newVal) => {
    if (!newVal) return
    model.value.cstmrForeignerCountryCd = newVal
    const nation = nationList.value.find((item) => item.code === newVal)
    if (nation) {
      model.value.cstmrForeignerNation = nation.title
    } else {
      model.value.cstmrForeignerNation = newVal
    }
  },
)

watch(
  () => model.value.visaType,
  (newVal) => {
    model.value.cstmrForeignerVisaNo = newVal
  },
)

const validate = () => {
  if (!model.value.mobileNo1 || !model.value.mobileNo2 || !model.value.mobileNo3) return false
  if (
    model.value.mobileNo1.length < 3 ||
    model.value.mobileNo2.length < 3 ||
    model.value.mobileNo3.length !== 4
  ) {
    return false
  }
  if (props.showTelNo && ['JP', 'GO'].includes(model.value.cstmrTypeCd)) {
    if (!model.value.telNo1 || !model.value.telNo2 || !model.value.telNo3) return false
  }
  if (props.showAddress && model.value.joinType !== 'HDN3' && model.value.joinType !== 'HCN3') {
    if (!model.value.zipNo || !model.value.address) return false
    if (props.detailAddressRequired && !model.value.detailAddress) return false
  }

  if (props.showForeignerInfo && ['FN', 'FM'].includes(model.value.cstmrTypeCd)) {
    if (!model.value.country || !model.value.visaType) return false
    const isMobileDeviceChange =
      model.value.productType === 'MM' && ['HDN3', 'HCN3'].includes(model.value.joinType)
    if (!isMobileDeviceChange) {
      if (!model.value.cstmrForeignerVdateStartDate || !model.value.cstmrForeignerVdateEndDate)
        return false
    }
  }
  return true
}

const checkValidation = (preCheck = false) => {
  if (!mobileNoRef.value?.isValid) {
    showAlert(`${props.title} 휴대폰번호를 입력하세요`, () => {
      mobileNoRef.value?.focus()
    })
    return false
  }
  const telNoValid = telNoRef.value?.isValid
  if (props.showTelNo && ['JP', 'GO'].includes(model.value.cstmrTypeCd) && !telNoValid) {
    showAlert(`${props.title} 전화번호를 입력하세요`, () => {
      telNoRef.value?.focus()
    })
    return false
  }

  if (props.showAddress && model.value.joinType !== 'HDN3' && model.value.joinType !== 'HCN3') {
    if (!model.value.zipNo || !model.value.address) {
      showAlert(`${props.title} 주소를 입력하세요`, () => {
        addressRef.value?.focus()
      })
      return false
    }
    if (props.detailAddressRequired && !model.value.detailAddress) {
      showAlert(`${props.title} 주소 상세정보를 입력하세요`, () => {
        addressRef.value?.focus()
      })
      return false
    }
  }
  if (props.showForeignerInfo && ['FN', 'FM'].includes(model.value.cstmrTypeCd)) {
    if (!model.value.country) {
      showAlert(`${props.title} 국가를 선택하세요`, () => {
        countryRef.value?.focus()
      })
      return false
    }
    if (
      !(model.value.productType === 'MM' && ['HDN3', 'HCN3'].includes(model.value.joinType)) &&
      (!model.value.cstmrForeignerVdateStartDate || !model.value.cstmrForeignerVdateEndDate)
    ) {
      showAlert(`${props.title} 체류기간을 입력하세요`, () => {
        cstmrForeignerVdateRef.value?.focus()
      })
      return false
    }
    if (!model.value.visaType) {
      showAlert(`${props.title} 비자를 입력하세요`, () => {
        visaTypeRef.value?.focus()
      })
      return false
    }
  }

  return true
}

defineExpose({ validate, checkValidation })
</script>
