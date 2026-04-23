<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="명세서 수신 유형" tag="div" required>
        <MsfChip
          v-model="model.cstmrBillSendTypeCd"
          name="inp-stmtType"
          :data="[
            { value: 'stmtType1', label: '모바일 명세서' },
            { value: 'stmtType2', label: '이메일 명세서' },
            { value: 'stmtType3', label: '우편 명세서' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="요금 납부 방법" tag="div" required>
        <MsfChip
          v-model="model.reqPayTypeCd"
          name="inp-payMtd"
          :data="[
            { value: 'payMtd1', label: '자동이체' },
            { value: 'payMtd2', label: '신용카드' },
            { value: 'payMtd3', label: '통합청구' },
          ]"
        />
        <template v-if="model.reqPayTypeCd === 'payMtd1'">
          <hr class="ut-line" />
          <MsfStack type="field" class="ut-w100p">
            <MsfChip
              v-model="model.autoPayerType"
              name="inp-autoPayerType"
              :data="[
                { value: 'autoPayerType1', label: '본인납부' },
                { value: 'autoPayerType2', label: '타인납부' },
              ]"
            />
          </MsfStack>
          <MsfStack type="field">
            <MsfSelect
              title="은행 선택"
              v-model="model.reqBankCd"
              :options="[
                { label: '은행 선택1', value: 'autoBankCode1' },
                { label: '은행 선택2', value: 'autoBankCode2' },
              ]"
              placeholder="은행 선택"
              class="ut-w-300"
            />
            <MsfNumberInput
              v-model="model.reqAccountNo"
              id="inp-autoAcctNo"
              placeholder="계좌번호 입력"
              class="ut-w-200"
            />
            <MsfButton variant="toggle" v-if="autoAcctAuth.status.value === 'none'" disabled
              >계좌번호 유효성 체크</MsfButton
            >
            <MsfButton
              variant="toggle"
              v-else-if="autoAcctAuth.status.value === 'ready'"
              @click="autoAcctAuth.verify()"
              >계좌번호 유효성 체크</MsfButton
            >
            <MsfButton variant="toggle" v-else-if="autoAcctAuth.status.value === 'verified'" active
              >계좌번호 유효성 체크 완료</MsfButton
            >
          </MsfStack>
          <MsfStack type="field">
            <MsfInput
              v-model="model.reqAccountNm"
              id="inp-autoPayerName"
              placeholder="납부 고객명"
              class="ut-w-300"
            />
            <MsfBirthdayInput
              v-model="model.reqAccountRrn"
              id="inp-autoPayerBirth"
              length="8"
              class="ut-w-200"
            />
            <MsfSelect
              title="관계"
              v-model="model.reqAccountRelTypeCd"
              :options="[
                { label: '가족 선택1', value: 'autoRelation1' },
                { label: '가족 선택2', value: 'autoRelation2' },
              ]"
              placeholder="관계"
            />
          </MsfStack>
          <MsfCheckbox
            v-model="model.isAutoAgree"
            label="본인(예금주 또는 가입고객)은 납부해야 할 요금에 대해 위 계좌(카드)에서 지정된 출금(결제)일에 인출(결제)되는 것에 동의합니다."
            :invalid="!model.isAutoAgree"
            class="ut-mt-8"
          />
        </template>
        <template v-if="model.reqPayTypeCd === 'payMtd2'">
          <hr class="ut-line" />
          <MsfStack type="field" class="ut-w100p">
            <MsfChip
              v-model="model.cardPayerType"
              name="inp-cardPayerType"
              :data="[
                { value: 'cardPayerType1', label: '본인납부' },
                { value: 'cardPayerType2', label: '타인납부' },
              ]"
            />
          </MsfStack>
          <MsfStack type="field">
            <MsfSelect
              title="카드사 선택"
              v-model="model.reqCardCompanyCd"
              :options="[
                { label: '카드사 선택1', value: 'cardCorp1' },
                { label: '카드사 선택2', value: 'cardCorp2' },
              ]"
              placeholder="카드사 선택"
              class="ut-w-300"
            />
            <MsfNumberInput
              v-model="model.reqCardNo"
              id="inp-cardNo"
              placeholder="카드번호 입력"
              class="ut-w-200"
            />
            <MsfButton variant="toggle" v-if="cardAuth.status.value === 'none'" disabled
              >신용카드 유효성 체크</MsfButton
            >
            <MsfButton
              variant="toggle"
              v-else-if="cardAuth.status.value === 'ready'"
              @click="cardAuth.verify()"
              >신용카드 유효성 체크</MsfButton
            >
            <MsfButton variant="toggle" v-else-if="cardAuth.status.value === 'verified'" active
              >신용카드 유효성 체크 완료</MsfButton
            >
          </MsfStack>
          <MsfStack type="field">
            <MsfSelect
              title="유효기간(MM) 선택"
              v-model="model.reqCardMm"
              :options="[
                { label: '유효기간(MM)', value: 'cardExpMm1' },
                { label: '유효기간(YY)', value: 'cardExpMm2' },
              ]"
              placeholder="MM"
            />
            <MsfSelect
              title="유효기간(YY) 선택"
              v-model="model.reqCardYy"
              :options="[
                { label: '유효기간(YY)', value: 'cardExpYy1' },
                { label: '유효기간(YY)', value: 'cardExpYy2' },
              ]"
              placeholder="YY"
            />
          </MsfStack>
          <MsfStack type="field">
            <MsfInput
              v-model="model.reqCardNm"
              id="inp-cardPayerName"
              placeholder="납부 고객명"
              class="ut-w-300"
            />
            <MsfBirthdayInput
              v-model="model.reqCardRrn"
              id="inp-cardPayerBirth"
              length="8"
              class="ut-w-200"
            />
            <MsfSelect
              title="관계"
              v-model="model.cardRelation"
              :options="[
                { label: '관계1', value: 'cardRelation1' },
                { label: '관계2', value: 'cardRelation2' },
              ]"
              placeholder="관계"
            />
          </MsfStack>
        </template>
        <template v-if="model.reqPayTypeCd === 'payMtd3'">
          <MsfStack type="field">
            <MsfInput
              v-model="model.combId"
              id="inp-combId"
              placeholder="청구계정ID 입력"
              class="ut-w-300"
            />
            <MsfButton variant="toggle" v-if="combAuth.status.value === 'none'" disabled
              >청구계정 체크</MsfButton
            >
            <MsfButton
              variant="toggle"
              v-else-if="combAuth.status.value === 'ready'"
              @click="handleCombVerify"
              >청구계정 체크</MsfButton
            >
            <MsfButton variant="toggle" v-else-if="combAuth.status.value === 'verified'" active
              >청구계정 체크 완료</MsfButton
            >
          </MsfStack>
          <MsfCheckbox
            v-model="model.combAgree"
            label="본인은 신청한 회선과 통합하여 요금이 청구되는 것에 동의합니다."
            :invalid="!model.combAgree"
            class="ut-mt-8"
          />
        </template>
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps, computed } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  title: { type: String, default: '납부 정보' },
  customerData: { type: Object, default: () => ({}) },
  authFlags: { type: Object, default: () => ({}) },
})
const model = defineModel({ type: Object, required: true })

const autoAcctAuth = useAuthButton(() => [model.value?.autoBankCode, model.value?.autoAcctNo], {
  get value() {
    return props.authFlags?.autoAcct || false
  },
  set value(v) {
    if (props.authFlags) {
      props.authFlags.autoAcct = v
    }
  },
})

const cardAuth = useAuthButton(() => [model.value?.cardCorp, model.value?.cardNo], {
  get value() {
    return props.authFlags?.cardNo || false
  },
  set value(v) {
    if (props.authFlags) {
      props.authFlags.reqCardNo = v
    }
  },
})

const combAuth = useAuthButton(() => [model.value?.combId], {
  get value() {
    return props.authFlags?.combId || false
  },
  set value(v) {
    if (props.authFlags) {
      props.authFlags.combId = v
    }
  },
})

const handleCombVerify = async () => {
  const customer = props.customerData || {}
  const payload = {
    cstmrTypeCd: customer.cstmrTypeCd || 'NA',
    ban: model.value.combId,
    customerSsn:
      customer.cstmrNativeRrn1 + customer.cstmrNativeRrn2 ||
      customer.cstmrForeignerRrn1 + customer.cstmrForeignerRrn2 ||
      customer.cstmrJuridicalRrn1 + customer.cstmrJuridicalRrn2 ||
      '',
    customerLinkName: customer.cstmrNm,
  }

  try {
    const res = await post('/api/form/verifyBillInfo', payload)
    if (res && res.code === '0000') {
      combAuth.verify()
      alert('청구계정 확인이 완료되었습니다.')
    } else {
      alert(res.message || '청구계정 확인에 실패했습니다.')
    }
  } catch (error) {
    console.error('Verify bill info error:', error)
    alert('청구계정 확인 중 오류가 발생했습니다.')
  }
}

const validate = () => {
  if (!model.value.cstmrBillSendTypeCd) return false
  if (!model.value.reqPayTypeCd) return false

  if (model.value.reqPayTypeCd === 'payMtd1') {
    if (!model.value.autoPayerType || !model.value.reqBankCd || !model.value.reqAccountNo)
      return false
    if (!props.authFlags?.autoAcct) return false
    if (!model.value.reqAccountNm || !model.value.reqAccountRrn || !model.value.reqAccountRelTypeCd)
      return false
    if (!model.value.isAutoAgree) return false
  } else if (model.value.reqPayTypeCd === 'payMtd2') {
    if (!model.value.cardPayerType || !model.value.reqCardCompanyCd || !model.value.reqCardNo)
      return false
    if (!props.authFlags?.cardNo && !props.authFlags?.reqCardNo) return false
    if (
      !model.value.reqCardMm ||
      !model.value.reqCardYy ||
      !model.value.reqCardNm ||
      !model.value.reqCardRrn ||
      !model.value.cardRelation
    )
      return false
  } else if (model.value.reqPayTypeCd === 'payMtd3') {
    if (!model.value.combId) return false
    if (!props.authFlags?.combId) return false
    if (!model.value.combAgree) return false
  }

  return true
}

defineExpose({ validate })
</script>
