<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="명세서 수신 유형" tag="div" required>
        <MsfChip v-model="model.stmtType" name="inp-stmtType" :data="[{ value: 'stmtType1', label: '모바일 명세서' }, { value: 'stmtType2', label: '이메일 명세서' }, { value: 'stmtType3', label: '우편 명세서' }]" />
      </MsfFormGroup>
      <MsfFormGroup label="요금 납부 방법" tag="div" required>
        <MsfChip v-model="model.payMtd" name="inp-payMtd" :data="[{ value: 'payMtd1', label: '자동이체' }, { value: 'payMtd2', label: '신용카드' }, { value: 'payMtd3', label: '통합청구' }]" />
        <template v-if="model.payMtd === 'payMtd1'">
          <hr class="ut-line" />
          <MsfStack type="field" class="ut-w100p">
            <MsfChip v-model="model.autoPayerType" name="inp-autoPayerType" :data="[{ value: 'autoPayerType1', label: '본인납부' }, { value: 'autoPayerType2', label: '타인납부' }]" />
          </MsfStack>
          <MsfStack type="field">
            <MsfSelect title="은행 선택" v-model="model.autoBankCode" :options="[{ label: '은행 선택1', value: 'autoBankCode1' }, { label: '은행 선택2', value: 'autoBankCode2' }]" placeholder="은행 선택" class="ut-w-300" />
            <MsfInput v-model="model.autoAcctNo" id="inp-autoAcctNo" placeholder="계좌번호 입력" class="ut-w-200" />
            <MsfButton variant="toggle" v-if="autoAcctAuth.status.value === 'none'" disabled>계좌번호 유효성 체크</MsfButton>
            <MsfButton variant="toggle" v-else-if="autoAcctAuth.status.value === 'ready'" @click="autoAcctAuth.verify()">계좌번호 유효성 체크</MsfButton>
            <MsfButton variant="toggle" v-else-if="autoAcctAuth.status.value === 'verified'" active>계좌번호 유효성 체크 완료</MsfButton>
          </MsfStack>
          <MsfStack type="field">
            <MsfInput v-model="model.autoPayerName" id="inp-autoPayerName" placeholder="납부 고객명" class="ut-w-300" />
            <MsfBirthdayInput v-model="model.autoPayerBirth" id="inp-autoPayerBirth" length="8" class="ut-w-200" />
            <MsfSelect title="관계" v-model="model.autoRelation" :options="[{ label: '가족 선택1', value: 'autoRelation1' }, { label: '가족 선택2', value: 'autoRelation2' }]" placeholder="관계" />
          </MsfStack>
          <MsfCheckbox v-model="model.isAutoAgree" label="본인(예금주 또는 가입고객)은 납부해야 할 요금에 대해 위 계좌(카드)에서 지정된 출금(결제)일에 인출(결제)되는 것에 동의합니다." :invalid="!model.isAutoAgree" class="ut-mt-8" />
        </template>
        <template v-if="model.payMtd === 'payMtd2'">
          <hr class="ut-line" />
          <MsfStack type="field" class="ut-w100p">
            <MsfChip v-model="model.cardPayerType" name="inp-cardPayerType" :data="[{ value: 'cardPayerType1', label: '본인납부' }, { value: 'cardPayerType2', label: '타인납부' }]" />
          </MsfStack>
          <MsfStack type="field">
            <MsfSelect title="카드사 선택" v-model="model.cardCorp" :options="[{ label: '카드사 선택1', value: 'cardCorp1' }, { label: '카드사 선택2', value: 'cardCorp2' }]" placeholder="카드사 선택" class="ut-w-300" />
            <MsfInput v-model="model.cardNo" id="inp-cardNo" placeholder="카드번호 입력" class="ut-w-200" />
            <MsfButton variant="toggle" v-if="cardAuth.status.value === 'none'" disabled>신용카드 유효성 체크</MsfButton>
            <MsfButton variant="toggle" v-else-if="cardAuth.status.value === 'ready'" @click="cardAuth.verify()">신용카드 유효성 체크</MsfButton>
            <MsfButton variant="toggle" v-else-if="cardAuth.status.value === 'verified'" active>신용카드 유효성 체크 완료</MsfButton>
          </MsfStack>
          <MsfStack type="field">
            <MsfSelect title="유효기간(MM) 선택" v-model="model.cardExpMm" :options="[{ label: '유효기간(MM)', value: 'cardExpMm1' }, { label: '유효기간(YY)', value: 'cardExpMm2' }]" placeholder="MM" />
            <MsfSelect title="유효기간(YY) 선택" v-model="model.cardExpYy" :options="[{ label: '유효기간(YY)', value: 'cardExpYy1' }, { label: '유효기간(YY)', value: 'cardExpYy2' }]" placeholder="YY" />
          </MsfStack>
          <MsfStack type="field">
            <MsfInput v-model="model.cardPayerName" id="inp-cardPayerName" placeholder="납부 고객명" class="ut-w-300" />
            <MsfBirthdayInput v-model="model.cardPayerBirth" id="inp-cardPayerBirth" length="8" class="ut-w-200" />
            <MsfSelect title="관계" v-model="model.cardRelation" :options="[{ label: '관계1', value: 'cardRelation1' }, { label: '관계2', value: 'cardRelation2' }]" placeholder="관계" />
          </MsfStack>
        </template>
        <template v-if="model.payMtd === 'payMtd3'">
          <MsfStack type="field">
            <MsfInput v-model="model.combId" id="inp-combId" placeholder="청구계정ID 입력" class="ut-w-300" />
            <MsfButton variant="toggle" v-if="combAuth.status.value === 'none'" disabled>청구계정 체크</MsfButton>
            <MsfButton variant="toggle" v-else-if="combAuth.status.value === 'ready'" @click="combAuth.verify()">청구계정 체크</MsfButton>
            <MsfButton variant="toggle" v-else-if="combAuth.status.value === 'verified'" active>청구계정 체크 완료</MsfButton>
          </MsfStack>
          <MsfCheckbox v-model="model.combAgree" label="본인은 신청한 회선과 통합하여 요금이 청구되는 것에 동의합니다." :invalid="!model.combAgree" class="ut-mt-8" />
        </template>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'

const model = defineModel({ type: Object, required: true })
const store = useMsfFormNewChgStore()

const autoAcctAuth = useAuthButton(
  () => [model.value?.autoBankCode, model.value?.autoAcctNo],
  {
    get value() { return store.authFlags?.autoAcct || false },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.autoAcct = v
      }
    }
  }
)

const cardAuth = useAuthButton(
  () => [model.value?.cardCorp, model.value?.cardNo],
  {
    get value() { return store.authFlags?.cardNo || false },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.cardNo = v
      }
    }
  }
)

const combAuth = useAuthButton(
  () => [model.value?.combId],
  {
    get value() { return store.authFlags?.combId || false },
    set value(v) {
      if (store.authFlags) {
        store.authFlags.combId = v
      }
    }
  }
)
</script>
