<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="휴대폰번호" required helpText="※ 신청서 발송 요청시 추가로 발송">
        <MsfStack type="field">
          <MsfNumberInput
            ref="mobileNo1Ref"
            v-model="model.mobileNo1"
            id="inp-mobileNo1"
            placeholder="앞자리"
            maxlength="3"
            :readonly="model.isSaved"
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
            :readonly="model.isSaved"
            @maxlength="mobileNo3Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="mobileNo3Ref"
            v-model="model.mobileNo3"
            id="inp-mobileNo3"
            placeholder="뒤 4자리"
            maxlength="4"
            :readonly="model.isSaved"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="전화번호">
        <MsfStack type="field">
          <MsfNumberInput
            ref="telNo1Ref"
            v-model="model.telNo1"
            id="inp-telNo1"
            placeholder="앞 3자리"
            maxlength="3"
            :readonly="model.isSaved"
            @maxlength="telNo2Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="telNo2Ref"
            v-model="model.telNo2"
            id="inp-telNo2"
            placeholder="가운데 4자리"
            maxlength="4"
            :readonly="model.isSaved"
            @maxlength="telNo3Ref?.focus()"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            ref="telNo3Ref"
            v-model="model.telNo3"
            id="inp-telNo3"
            placeholder="뒤 4자리"
            maxlength="4"
            :readonly="model.isSaved"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="이메일주소" required>
        <MsfStack id="inp-emailAddr" type="field">
          <MsfEmailInput
            v-model:emailId="model.emailAddr1"
            v-model:emailDomain="model.emailAddr2"
            :readonly="model.isSaved"
            :email-id-maxlength="emailIdMaxlength"
            :email-domain-maxlength="emailDomainMaxlength"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="주소" tag="div" required>
        <MsfStack type="field">
          <MsfInput
            v-model="model.zipNo"
            placeholder="우편번호"
            ariaLabel="우편번호 입력"
            disabled
          />
          <MsfButton variant="subtle" @click="onClickSearchAddressBtn" :disabled="model.isSaved"
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
          :readonly="model.isSaved"
        />
      </MsfFormGroup>
      <MsfFormGroup v-if="['FN', 'FM'].includes(model.cstmrTypeCd)" label="국가" tag="div" required>
        <MsfSelect
          title="국가"
          v-model="model.country"
          groupCode="NATIONLIST"
          placeholder="국가"
          class="ut-w-300"
          :disabled="model.isSaved"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="['FN', 'FM'].includes(model.cstmrTypeCd)"
        label="체류기간"
        tag="div"
        required
      >
        <MsfDateRange
          v-model:from="rangeDatePickerValue.start"
          v-model:to="rangeDatePickerValue.end"
          :disabled="model.isSaved"
        />
      </MsfFormGroup>
      <MsfFormGroup v-if="['FN', 'FM'].includes(model.cstmrTypeCd)" label="비자" required>
        <MsfInput
          v-model="model.visaType"
          placeholder="비자 입력"
          class="ut-w-300"
          maxlength="16"
          :readonly="model.isSaved"
        />
      </MsfFormGroup>
    </MsfStack>

    <!-- 주소 검색 모달 -->
    <MsfAddressSearchPop v-model="showAddressSearchPop" @confirm="onConfirmAddressSearchPop" />
  </div>
</template>
<script setup>
import { defineModel, defineProps, ref, watch } from 'vue'

const props = defineProps({
  emailIdMaxlength: { type: [Number, String], default: 13 },
  emailDomainMaxlength: { type: [Number, String], default: 20 },
  title: { type: String, default: '가입자 연락처' },
})
const model = defineModel({ type: Object, required: true })
const rangeDatePickerValue = ref({ start: '', end: '' })

const mobileNo1Ref = ref(null)
const mobileNo2Ref = ref(null)
const mobileNo3Ref = ref(null)
const telNo1Ref = ref(null)
const telNo2Ref = ref(null)
const telNo3Ref = ref(null)

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

const validate = () => {
  if (!model.value.mobileNo1 || !model.value.mobileNo2 || !model.value.mobileNo3) return false
  if (!model.value.emailAddr1 || !model.value.emailAddr2) return false
  if (!model.value.zipNo || !model.value.address || !model.value.detailAddress) return false
  if (['FN', 'FM'].includes(model.value.cstmrTypeCd)) {
    if (!model.value.country || !model.value.visaType) return false
    if (!rangeDatePickerValue.value.start || !rangeDatePickerValue.value.end) return false
  }
  return true
}

defineExpose({ validate })
</script>
