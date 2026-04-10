<template>
  <div>
    <MsfTitleArea title="가입자 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput v-model="model.userName" placeholder="이름" class="ut-w-300" :readonly="model.idCard !== 'S'" />
      </MsfFormGroup>
      <MsfFormGroup v-if="['NA', 'NM'].includes(model.customerType)" label="주민등록번호" required>
        <MsfStack type="field">
          <MsfInput v-model="model.residentNo1" placeholder="앞 6자리" :readonly="model.idCard !== 'S'" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="model.residentNo2" id="inp-residentNo2" placeholder="뒤 7자리" :readonly="model.idCard !== 'S'" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup v-if="['FN', 'FM'].includes(model.customerType)" label="외국인등록번호" required>
        <MsfStack type="field">
          <MsfInput v-model="model.foreignerNo1" placeholder="앞 6자리" :readonly="model.idCard !== 'S'" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="model.foreignerNo2" id="inp-foreignerNo2" placeholder="뒤 7자리" :readonly="model.idCard !== 'S'" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup v-if="['JP', 'PU'].includes(model.customerType)" label="법인등록번호" required>
        <MsfStack type="field">
          <MsfInput v-model="model.corpRegNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="model.corpRegNo2" id="inp-corpRegNo2" placeholder="뒤 7자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup v-if="['NA', 'JP', 'PU'].includes(model.customerType)" label="사업자등록번호" helpText="※ 개인사업자인 경우만 입력" :required="['JP', 'PU'].includes(model.customerType)">
        <MsfStack type="field">
          <MsfInput v-model="model.bizNo1" placeholder="앞 3자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="model.bizNo2" id="inp-bizNo2" placeholder="가운데 2자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="model.bizNo3" id="inp-bizNo3" placeholder="뒤 5자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup v-if="['NA', 'JP', 'PU'].includes(model.customerType)" label="대표자명" :required="['JP', 'PU'].includes(model.customerType) || !!model.bizNo1">
        <MsfInput v-model="model.repreName" placeholder="대표자명 입력" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup v-if="['NA', 'JP', 'PU'].includes(model.customerType)" label="업종/업태" :required="['JP', 'PU'].includes(model.customerType) || !!model.bizNo1">
        <MsfStack type="field" class="ut-w100p">
          <MsfSelect title="업종 선택" v-model="model.bizType" :options="[{ label: '업종1', value: 'bizType1' }, { label: '업종2', value: 'bizType2' }]" placeholder="업종 선택" class="ut-w-300" />
          <MsfInput v-model="model.bizItem" placeholder="업태 입력" class="ut-flex-1" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup v-if="model.joinType === 'HDN3'" label="기기변경<br/>휴대폰번호" required>
        <MsfStack type="field">
          <MsfInput v-model="model.deviceChgTel1" placeholder="앞 3자리" readonly />
          <span class="unit-sep">-</span>
          <MsfInput v-model="model.deviceChgTel2" id="inp-deviceChgTel2" placeholder="가운데 4자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="model.deviceChgTel3" id="inp-deviceChgTel3" placeholder="뒤 4자리" />
          <MsfButton variant="toggle" v-if="deviceChgAuth.status.value === 'none'" disabled>인증</MsfButton>
          <MsfButton variant="toggle" v-else-if="deviceChgAuth.status.value === 'ready'" @click="deviceChgAuth.verify()">인증</MsfButton>
          <MsfButton variant="toggle" v-else-if="deviceChgAuth.status.value === 'verified'" active>인증 완료</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'

const props = defineProps({
  title: { type: String, default: '가입자 정보' }
})
const model = defineModel({ type: Object, required: true })
const store = useMsfFormNewChgStore()

const deviceChgAuth = useAuthButton(
  () => [model.value?.deviceChgTel2, model.value?.deviceChgTel3],
  // 스토어의 deviceChgTel 인증 상태를 관리할 수 있도록 플래그 전달
  {
    get value() { return store.authFlags?.deviceChgTel || false },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.deviceChgTel = v
      }
    }
  }
)
</script>
