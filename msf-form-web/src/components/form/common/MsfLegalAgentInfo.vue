<template>
  <div v-if="['NM', 'FM'].includes(model.cstmrTypeCd)">
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput
          id="inp-repName"
          v-model="model.repName"
          placeholder="이름"
          class="ut-w-300"
          :readonly="model.isSaved || (model.identityCertTypeCd !== 'S' && !model.isTrCustomer)"
        />
      </MsfFormGroup>
      <MsfFormGroup
        label="주민등록번호/<br/>외국인등록번호"
        required
        v-if="!useBirthDate && !model.isTrCustomer"
      >
        <MsfStack type="field">
          <MsfNumberInput
            ref="combinedNo1Ref"
            id="inp-combinedNo1"
            v-model="combinedNo1"
            placeholder="앞 6자리"
            maxlength="6"
            :readonly="model.isSaved || model.identityCertTypeCd !== 'S'"
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
            :readonly="model.isSaved || model.identityCertTypeCd !== 'S'"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup v-if="useBirthDate && !model.isTrCustomer" label="생년월일" required>
        <MsfBirthdayInput
          id="inp-repBirthDate"
          v-model="model.repBirthDate"
          length="8"
          class="ut-w-300"
          placeholder="8자리(YYYYMMDD)"
          :readonly="model.isSaved || model.identityCertTypeCd !== 'S'"
        />
      </MsfFormGroup>
      <MsfFormGroup v-if="model.isTrCustomer" label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            v-model="model.minorUserBirthDate"
            length="8"
            class="ut-w-300"
            placeholder="8자리(YYYYMMDD)"
          />
          <MsfRadioGroup
            :name="`${name}-user-gender`"
            v-model="model.minorUserGender"
            :options="[
              { value: 'M', label: '남' },
              { value: 'F', label: '여' },
            ]"
            class="ut-ml-16"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="신청인과의 관계" tag="div" required>
        <MsfSelect
          id="inp-repRelation"
          title="신청인과의 관계"
          v-model="model.minorAgentRelTypeCd"
          groupCode="AGR"
          placeholder="선택"
          class="ut-w-300"
          :disabled="model.isSaved"
        />
      </MsfFormGroup>
      <div id="inp-repPhone">
        <MsfMobileAuthNumber
          v-model:name="model.repName"
          v-model:phone1="phoneData.phone1"
          v-model:phone2="phoneData.phone2"
          v-model:phone3="phoneData.phone3"
          form-type="F-1-VDP"
          @complete="onComplete"
        />
      </div>
    </MsfStack>
    <MsfTitleArea :title="agreementTitle" />
    <MsfAgreementItem
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
      :disabled="model.isSaved"
    />
  </div>
</template>
<script setup>
import { ref, defineModel, defineProps, computed, onBeforeMount, watch } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { post } from '@/libs/api/msf.api'
const props = defineProps({
  title: { type: String, default: '법정대리인 정보' },
  agreementTitle: { type: String, default: '법정대리인 안내사항 확인 및 동의' },
  name: { type: String, default: 'basic' },
  useBirthDate: { type: Boolean, default: false },
})
const model = defineModel({ type: Object, required: true })
const combinedNo1Ref = ref(null)
const combinedNo2Ref = ref(null)
const store = useMsfFormNewChgStore()
const termsItem = ref(null)

// repName과 minorAgentNm 동기화 (기존 로직 및 서버 페이로드 호환성 유지)
watch(
  () => model.value.repName,
  (newVal) => {
    model.value.minorAgentNm = newVal
  },
)

const phoneData = computed({
  get() {
    return {
      phone1: model.value.minorAgentTelFnNo,
      phone2: model.value.minorAgentTelMnNo,
      phone3: model.value.minorAgentTelRnNo,
    }
  },
  set(val) {
    model.value.minorAgentTelFnNo = val.phone1
    model.value.minorAgentTelMnNo = val.phone2
    model.value.minorAgentTelRnNo = val.phone3
  },
})

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

const onComplete = (result) => {
  if (store.authFlags) {
    store.authFlags.repPhone = result
  }
}

const validate = () => {
  if (['NM', 'FM'].includes(model.value.cstmrTypeCd)) {
    if (model.value.isTrCustomer) {
      if (!model.value.repName) return false
      if (!model.value.minorAgentRelTypeCd) return false
      if (!model.value.minorUserBirthDate) return false
      if (!store.authFlags?.repPhone) return false
      if (!model.value.repAgree) return false
    } else {
      if (!model.value.repName) return false
      if (props.useBirthDate) {
        if (!model.value.repBirthDate) return false
      } else if (!combinedNo1.value || !combinedNo2.value) {
        return false
      }
      if (!model.value.minorAgentRelTypeCd) return false
      if (!store.authFlags?.repPhone) return false
      if (!model.value.repAgree) return false
    }
  }
  return true
}

defineExpose({ validate })

onBeforeMount(async () => {
  try {
    const res = await post('/api/shared/form/common/terms/list', {
      groupCode: 'CLAUSE_MINOR_AGENT',
    })
    // 응답 데이터 구조에 맞춰 유연하게 처리
    const list = res.data || []
    const codes = Array.isArray(list) ? list : (list.codes || [])
    
    if (codes.length > 0) {
      termsItem.value = codes[0]
    }
  } catch (e) {
    console.error('Failed to fetch legal agent terms:', e)
  }
})
</script>
