<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="휴대폰번호" required helpText="※ 신청서 발송 요청시 추가로 발송">
        <MsfStack type="field">
          <MsfNumberInput v-model="model.mobileNo1" placeholder="앞자리" maxlength="3" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.mobileNo2"
            id="inp-mobileNo2"
            placeholder="가운데 4자리"
            maxlength="4"
            :readonly="model.isSaved"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
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
            v-model="model.telNo1"
            placeholder="지역번호"
            maxlength="3"
            :readonly="model.isSaved"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.telNo2"
            id="inp-telNo2"
            placeholder="가운데 4자리"
            maxlength="4"
            :readonly="model.isSaved"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.telNo3"
            id="inp-telNo3"
            placeholder="뒤 4자리"
            maxlength="4"
            :readonly="model.isSaved"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="이메일주소" required>
        <MsfStack type="field">
          <MsfInput
            v-model="model.emailAddr1"
            placeholder="이메일 아이디"
            :readonly="model.isSaved"
          />
          <span>@</span>
          <MsfInput
            v-model="model.emailAddr2"
            id="inp-emailAddr2"
            placeholder="이메일 도메인"
            class="ut-w-300"
            :readonly="model.isSaved"
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
          :options="[
            { label: '국가1', value: 'nation1' },
            { label: '국가2', value: 'nation2' },
          ]"
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
          :readonly="model.isSaved"
        />
      </MsfFormGroup>
    </MsfStack>

    <!-- 주소 검색 모달 -->
    <MsfAddressSearchPop v-model="showAddressSearchPop" @confirm="onConfirmAddressSearchPop" />
  </div>
</template>
<script setup>
import { defineModel, defineProps, ref } from 'vue'

const props = defineProps({
  title: { type: String, default: '가입자 연락처' },
})
const model = defineModel({ type: Object, required: true })
const rangeDatePickerValue = ref({ start: '', end: '' })

const showAddressSearchPop = ref(false)
const onClickSearchAddressBtn = () => {
  showAddressSearchPop.value = true
}
const onConfirmAddressSearchPop = (result) => {
  model.value.zipNo = result.zipNo
  model.value.address = result.address
  model.value.detailAddress = result.detailAddress
}

const validate = () => {
  return true
}

defineExpose({ validate })
</script>
