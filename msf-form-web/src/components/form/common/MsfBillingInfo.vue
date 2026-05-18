<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="명세서 수신 유형" tag="div" required>
        <MsfChip v-model="model.cstmrBillSendTypeCd" name="inp-stmtType" groupCode="STRE" />
      </MsfFormGroup>
      <MsfFormGroup label="요금 납부 방법" tag="div" required>
        <MsfChip 
          v-model="model.reqPayTypeCd" 
          name="inp-payMtd" 
          groupCode="PAYM" 
          :data="paymentMethodOptions"
        />
      </MsfFormGroup>

      <template v-if="['AA', 'D'].includes(model.reqPayTypeCd)">
        <MsfFormGroup label="납부자 구분" tag="div" required>
          <MsfChip
            v-model="model.othersPaymentYn"
            name="inp-othersPaymentYn"
            :data="[
              { value: 'N', label: '본인납부' },
              { value: 'Y', label: '타인납부' },
            ]"
          />
        </MsfFormGroup>
        <MsfFormGroup label="은행" required>
          <MsfSelect
            title="은행 선택"
            v-model="model.reqBankCd"
            groupCode="BNK"
            placeholder="은행 선택"
            class="ut-w-300"
          />
        </MsfFormGroup>
        <MsfFormGroup label="계좌번호" required>
          <MsfStack type="field">
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
              @click="handleAccountVerify"
              >계좌번호 유효성 체크</MsfButton
            >
            <MsfButton variant="toggle" v-else-if="autoAcctAuth.status.value === 'verified'" active
              >계좌번호 유효성 체크 완료</MsfButton
            >
          </MsfStack>
        </MsfFormGroup>
        <template v-if="model.othersPaymentYn === 'Y'">
          <MsfFormGroup label="납부 고객명" required>
            <MsfInput
              v-model="model.reqAccountNm"
              id="inp-autoPayerName"
              placeholder="납부 고객명"
              class="ut-w-300"
            />
          </MsfFormGroup>
          <MsfFormGroup label="생년월일" required>
            <MsfBirthdayInput
              v-model="model.reqAccountRrn"
              id="inp-autoPayerBirth"
              length="8"
              class="ut-w-200"
            />
          </MsfFormGroup>
          <MsfFormGroup label="관계" required>
            <MsfSelect
              title="관계"
              v-model="model.reqAccountRelTypeCd"
              groupCode="AGR"
              placeholder="관계"
            />
          </MsfFormGroup>
        </template>
        <MsfFormGroup label="출금 동의" required v-if="model.othersPaymentYn === 'Y'">
          <MsfCheckbox
            v-model="model.isAutoAgree"
            label="본인(예금주 또는 가입고객)은 납부해야 할 요금에 대해 위 계좌(카드)에서 지정된 출금(결제)일에 인출(결제)되는 것에 동의합니다."
            :invalid="!model.isAutoAgree"
          />
        </MsfFormGroup>
      </template>

      <template v-if="['C'].includes(model.reqPayTypeCd)">
        <MsfFormGroup label="납부자 구분" tag="div" required>
          <MsfChip
            v-model="model.othersPaymentYn"
            name="inp-othersPaymentYn"
            :data="[
              { value: 'N', label: '본인납부' },
              { value: 'Y', label: '타인납부' },
            ]"
          />
        </MsfFormGroup>
        <MsfFormGroup label="카드사" required>
          <MsfSelect
            title="카드사 선택"
            v-model="model.reqCardCompanyCd"
            groupCode="CRD"
            placeholder="카드사 선택"
            class="ut-w-300"
          />
        </MsfFormGroup>
        <MsfFormGroup label="카드번호" required>
          <MsfStack type="field">
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
              @click="handleCardVerify"
              >신용카드 유효성 체크</MsfButton
            >
            <MsfButton variant="toggle" v-else-if="cardAuth.status.value === 'verified'" active
              >신용카드 유효성 체크 완료</MsfButton
            >
          </MsfStack>
        </MsfFormGroup>
        <MsfFormGroup label="유효기간" required>
          <MsfStack type="field">
            <MsfSelect
              title="유효기간(MM) 선택"
              v-model="model.reqCardMm"
              :options="cardMonthOptions"
              placeholder="MM"
            />
            <MsfSelect
              title="유효기간(YY) 선택"
              v-model="model.reqCardYy"
              :options="cardYearOptions"
              placeholder="YY"
            />
          </MsfStack>
        </MsfFormGroup>
        <template v-if="model.othersPaymentYn === 'Y'">
          <MsfFormGroup label="납부 고객명" required>
            <MsfInput
              v-model="model.reqCardNm"
              id="inp-cardPayerName"
              placeholder="납부 고객명"
              class="ut-w-300"
            />
          </MsfFormGroup>
          <MsfFormGroup label="생년월일" required>
            <MsfBirthdayInput
              v-model="model.reqCardRrn"
              id="inp-cardPayerBirth"
              length="8"
              class="ut-w-200"
            />
          </MsfFormGroup>
          <MsfFormGroup label="관계" required>
            <MsfSelect
              title="관계"
              v-model="model.cardRelation"
              groupCode="AGR"
              placeholder="관계"
            />
          </MsfFormGroup>
        </template>
        <MsfFormGroup label="출금 동의" required v-if="model.othersPaymentYn === 'Y'">
          <MsfCheckbox
            v-model="model.isAutoAgree"
            label="본인(예금주 또는 가입고객)은 납부해야 할 요금에 대해 위 계좌(카드)에서 지정된 출금(결제)일에 인출(결제)되는 것에 동의합니다."
            :invalid="!model.isAutoAgree"
          />
        </MsfFormGroup>
      </template>

      <template v-if="['R', 'VA'].includes(model.reqPayTypeCd)">
        <MsfFormGroup label="청구계정ID" required>
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
        </MsfFormGroup>
        <MsfFormGroup label="통합 청구 동의" required>
          <MsfCheckbox
            v-model="model.combAgree"
            label="본인은 신청한 회선과 통합하여 요금이 청구되는 것에 동의합니다."
            :invalid="!model.combAgree"
          />
        </MsfFormGroup>
      </template>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps, computed, onMounted, ref, watch } from 'vue'
import { useAuthButton } from '@/hooks/useAuthButton'
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils.js'

const props = defineProps({
  title: { type: String, default: '납부 정보' },
  customerData: { type: Object, default: () => ({}) },
  authFlags: { type: Object, default: () => ({}) },
})
const model = defineModel({ type: Object, required: true })

const paymentMethodOptions = ref([])

const currentYear = new Date().getFullYear() % 100
const currentMonth = new Date().getMonth() + 1

const cardYearOptions = computed(() => {
  const years = []
  for (let i = 0; i < 15; i++) {
    const year = currentYear + i
    years.push({ label: String(2000 + year), value: String(year) })
  }
  return years
})

const cardMonthOptions = computed(() => {
  const months = []
  const startMonth =
    String(model.value.reqCardYy) === String(currentYear) ? currentMonth : 1
  for (let i = startMonth; i <= 12; i++) {
    const m = String(i).padStart(2, '0')
    months.push({ label: m, value: m })
  }
  return months
})

// 유효기간 자동 초기화
watch(
  () => model.value.reqCardYy,
  (newYy) => {
    if (String(newYy) === String(currentYear) && Number(model.value.reqCardMm) < currentMonth) {
      model.value.reqCardMm = String(currentMonth).padStart(2, '0')
    }
  }
)

onMounted(async () => {
  const codes = await getCommonCodeList('PAYM')
  const baseOptions = (codes || []).map(item => ({ label: item.title, value: item.code }))
  
  const updateOptions = () => {
    const isUpfront = String(model.value.installmentMonth) === '0'
    if (isUpfront) {
      paymentMethodOptions.value = baseOptions.filter(opt => opt.value === 'C')
      if (model.value.reqPayTypeCd !== 'C') {
        model.value.reqPayTypeCd = 'C'
      }
    } else {
      paymentMethodOptions.value = baseOptions
    }
  }

  watch(() => model.value.installmentMonth, updateOptions, { immediate: true })
})

const handleAccountVerify = async () => {
  const payload = {
    strGbn: '1',
    svcGbn: '2',
    service: '2',
    svcCls: '1',
    name:
      model.value.othersPaymentYn === 'N' ? props.customerData.cstmrNm : model.value.reqAccountNm,
    resId: (model.value.othersPaymentYn === 'N'
      ? props.customerData.cstmrNativeRrn1
      : model.value.reqAccountRrn
    )?.substring(0, 6),
    bankCode: model.value.reqBankCd,
    accountNo: model.value.reqAccountNo,
    inqRsn: '90',
  }

  try {
    const res = await post('/api/form/accountCheck', payload)
    if (res && res.data?.resCode === '0000') {
      autoAcctAuth.verify()
    }
  } catch (e) {
    console.error(e)
  }
}

const handleCardVerify = async () => {
  const payload = {
    crdtCardNo: model.value.reqCardNo,
    crdtCardTermYear: model.value.reqCardYy,
    crdtCardTermMonth: model.value.reqCardMm,
    custNm:
      model.value.othersPaymentYn === 'N' ? props.customerData.cstmrNm : model.value.reqCardNm,
    brthDate:
      model.value.othersPaymentYn === 'N'
        ? props.customerData.cstmrNativeBirth
        : model.value.reqCardRrn,
    ncType: '',
  }

  try {
    const res = await post('/api/form/crdtCardAthnInfo', payload)
    if (res && res.data?.resCode === '0000') {
      cardAuth.verify()
    }
  } catch (e) {
    console.error(e)
  }
}

const autoAcctAuth = useAuthButton(() => [model.value?.reqBankCd, model.value?.reqAccountNo], {
  get value() {
    return props.authFlags?.autoAcct || false
  },
  set value(v) {
    if (props.authFlags) {
      props.authFlags.autoAcct = v
    }
  },
})

const cardAuth = useAuthButton(() => [model.value?.reqCardCompanyCd, model.value?.reqCardNo], {
  get value() {
    return props.authFlags?.reqCardNo || false
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
    if (res && res.data?.resCode === '0000') {
      combAuth.verify()
    }
  } catch (error) {
    console.error('Verify bill info error:', error)
  }
}

const validate = () => {
  if (!model.value.cstmrBillSendTypeCd) return false
  if (!model.value.reqPayTypeCd) return false

  // 자동이체 (은행)
  if (['AA', 'D'].includes(model.value.reqPayTypeCd)) {
    if (!model.value.othersPaymentYn || !model.value.reqBankCd || !model.value.reqAccountNo)
      return false
    // 계좌 유효성 체크 필수
    if (!props.authFlags?.autoAcct) return false
    // 타인납부인 경우 대리인 정보 및 출금 동의 필수
    if (model.value.othersPaymentYn === 'Y') {
      if (
        !model.value.reqAccountNm ||
        !model.value.reqAccountRrn ||
        !model.value.reqAccountRelTypeCd
      )
        return false
      if (!model.value.isAutoAgree) return false
    }
  }
  // 신용카드
  else if (['C'].includes(model.value.reqPayTypeCd)) {
    if (!model.value.othersPaymentYn || !model.value.reqCardCompanyCd || !model.value.reqCardNo)
      return false
    // 카드 유효성 체크 필수
    if (!props.authFlags?.reqCardNo) return false
    if (!model.value.reqCardMm || !model.value.reqCardYy) return false
    // 타인납부인 경우 대리인 정보 필수
    if (model.value.othersPaymentYn === 'Y') {
      if (!model.value.reqCardNm || !model.value.reqCardRrn || !model.value.cardRelation)
        return false
      // 출금 동의 체크 필수
      if (!model.value.isAutoAgree) return false
    }
  }
  // 지로/기타 통합청구
  else if (['R', 'VA'].includes(model.value.reqPayTypeCd)) {
    if (!model.value.combId) return false
    // 청구계정 체크 필수
    if (!props.authFlags?.combId) return false
    // 통합 청구 동의 필수
    if (!model.value.combAgree) return false
  }

  return true
}

defineExpose({ validate })
</script>
