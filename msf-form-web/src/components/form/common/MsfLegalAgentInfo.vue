<template>
  <div v-if="['NM', 'FM'].includes(model.cstmrTypeCd)">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput
          v-model="model.repName"
          placeholder="이름"
          class="ut-w-300"
          :readonly="model.isSaved"
        />
      </MsfFormGroup>
      <MsfFormGroup label="주민등록번호/<br/>외국인등록번호" required>
        <MsfStack type="field">
          <MsfNumberInput v-model="combinedNo1" placeholder="앞 6자리" maxlength="6" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="combinedNo2"
            id="inp-combinedNo2"
            placeholder="뒤 7자리"
            maxlength="7"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="신청인과의 관계" tag="div" required>
        <MsfSelect
          title="신청인과의 관계"
          v-model="model.repRelation"
          :options="[
            { label: '관계1', value: 'nation1' },
            { label: '관계2', value: 'nation2' },
          ]"
          placeholder="선택"
          class="ut-w-300"
          :disabled="model.isSaved"
        />
      </MsfFormGroup>
      <MsfFormGroup label="연락처(휴대폰)" required>
        <MsfStack type="field">
          <MsfNumberInput
            v-model="model.repPhone1"
            placeholder="앞자리"
            maxlength="3"
            ariaLabel="연락처(휴대폰) 앞자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.repPhone2"
            id="inp-repPhone2"
            placeholder="가운데 4자리"
            maxlength="4"
            ariaLabel="연락처(휴대폰) 가운데 4자리"
            :readonly="model.isSaved"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="model.repPhone3"
            id="inp-repPhone3"
            placeholder="뒤 4자리"
            maxlength="4"
            ariaLabel="연락처(휴대폰) 뒤 4자리"
            :readonly="model.isSaved"
          />
          <MsfButton variant="toggle" v-if="repPhoneAuth.status.value === 'none'" disabled
            >인증번호 발송</MsfButton
          >
          <MsfButton
            variant="toggle"
            v-else-if="repPhoneAuth.status.value === 'ready'"
            @click="repPhoneAuth.send()"
            >인증번호 발송</MsfButton
          >
          <MsfButton
            variant="toggle"
            v-else-if="repPhoneAuth.status.value === 'sent'"
            @click="repPhoneAuth.send()"
            >인증번호 재발송</MsfButton
          >
          <MsfButton variant="toggle" v-else-if="repPhoneAuth.status.value === 'verified'" active
            >인증 완료</MsfButton
          >
        </MsfStack>
        <MsfStack type="field" v-if="repPhoneAuth.status.value === 'sent'">
          <MsfNumberInput
            v-model="model.repPhoneAuth"
            id="inp-repPhoneAuth"
            placeholder="인증번호 입력"
            maxlength="6"
          />
          <span class="remain-time">남은시간 <em>02:33</em></span>
          <MsfButton variant="toggle" @click="repPhoneAuth.verify()">인증번호 확인</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <MsfTitleArea :title="agreementTitle" />
    <MsfAgreementItem
      type="default"
      v-model="model.repAgree"
      label="본인은 안내사항을 확인하였습니다"
      required
      :popTitle="agreementTitle"
      content="법정대리인 안내사항 확인 및 동의 내용"
      :disabled="model.isSaved"
    />
  </div>
</template>
<script setup>
import { defineModel, defineProps, computed } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'

const props = defineProps({
  title: { type: String, default: '법정대리인 정보' },
  agreementTitle: { type: String, default: '법정대리인 안내사항 확인 및 동의' },
})
const model = defineModel({ type: Object, required: true })
const store = useMsfFormNewChgStore()

const combinedNo1 = computed({
  get() {
    return model.value.repRegistrationNo1 || model.value.repForeignerNo1 || ''
  },
  set(val) {
    const firstDigit = combinedNo2.value.charAt(0)
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
    const firstDigit = val.charAt(0)
    if (['5', '6', '7', '8'].includes(firstDigit)) {
      model.value.repForeignerNo2 = val
      model.value.repForeignerNo1 = combinedNo1.value // Sync No1 to foreigner
      model.value.repRegistrationNo2 = ''
      model.value.repRegistrationNo1 = ''
    } else {
      model.value.repRegistrationNo2 = val
      model.value.repRegistrationNo1 = combinedNo1.value // Sync No1 to registration
      model.value.repForeignerNo2 = ''
      model.value.repForeignerNo1 = ''
    }
  },
})

const repPhoneAuth = useAuthButton(
  () => [model.value?.repPhone1, model.value?.repPhone2, model.value?.repPhone3],
  {
    get value() {
      return store.authFlags?.repPhone || false
    },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.repPhone = v
      }
    },
  },
)

const validate = () => {
  return true
}

defineExpose({ validate })
</script>
