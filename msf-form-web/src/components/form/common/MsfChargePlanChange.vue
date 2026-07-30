<script setup>
import { post } from '@/libs/api/msf.api'
import { defineModel, onMounted, ref, watch } from 'vue'
import MsfRealtimeChargeInfoModal from './popups/MsfRealtimeChargeInfoModal.vue'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'

const model = defineModel({ type: Object, required: true })

const planCategoryOptions = ref([])
const planOptions = ref([])
const isRealtimeChargeInfoModalOpen = ref(false)

// 요금제 카테고리 조회
const fetchPlanCategories = async () => {
  try {
    const res = await post('/api/form/price/category/list', {
      rateAdsvcDivCd: 'P',
      agentCd: model.value.agentCd || '',
      sprtTp: model.value.discountType || '',
    })

    planCategoryOptions.value = res?.data?.map((item) => ({
      label: item.ctgNm,
      value: item.ctgCd,
    }))
  } catch (e) {
    console.error('Failed to fetch plan categories:', e)
  }
}

// 요금제 목록 조회 (카테고리 선택 시)
const fetchPlans = async (ctgCd) => {
  try {
    const res = await post('/api/form/rate/list', {
      sprtTp: model.value.discountType || '',
      prodCtgId: ctgCd, // 카테고리 ID 추가
      reqBuyTypeCd: model.value.productType || 'UU', // 상품유형(MM/UU) 전달
      salePlcyCd: '', // 단말기 판매정책 코드
    })
    planOptions.value =
      res?.data?.map((item) => ({
        label: item.rateNm + ' (' + Number(item.baseAmt).toLocaleString() + '원)' || item.ctgNm,
        value: item.rateCd || item.ctgCd || item.prodId,
        raw: item,
      })) || []

    if (planOptions.value.length > 0) {
      const isExist = planOptions.value.some((opt) => opt.value === model.value.planName2)
      if (!model.value.planName2 || !isExist) {
        model.value.planName2 = planOptions.value[0].value
      }
    } else {
      model.value.planName2 = ''
    }
  } catch (e) {
    console.error('Failed to fetch plans:', e)
  }
}

const getPhoneNo = () =>
  `${model.value.deviceChgTel1 || ''}${model.value.deviceChgTel2 || ''}${model.value.deviceChgTel3 || ''}`

const fetchCurrentPlan = async () => {
  // 가입중인 요금제 조회(Y02)
  try {
    const { ncn, custId, parentScanId } = model.value
    const res = await post('/api/form/pricechange/currentpriceplan', {
      ctn: getPhoneNo(),
      ncn: ncn,
      custId: custId,
      parentScanId: parentScanId,
    })
    if (res.code === '0000' && res.data.resCode === '0000') {
      const { outDto } = res.data.resData
      model.value.currentProdId = outDto?.prodId
      model.value.currentProdNm = outDto?.prodNm
      model.value.currentProdStdt = outDto?.efctStDt
      model.value.currentProdAmt = outDto?.famtTarifAmt
    }
  } catch (e) {
    console.error('Failed to fetch plans:', e)
  }
}

const fetchReservedPlan = async () => {
  // 요금제 변경 예약 조회(X89)
  try {
    const { ncn, custId, parentScanId } = model.value
    const res = await post('/api/form/pricechange/reservedpriceplan', {
      ctn: getPhoneNo(),
      ncn: ncn,
      custId: custId,
      parentScanId: parentScanId,
    })
    console.log(res.data)
    if (res.code === '0000' && res.data.resCode === '0000') {
      console.log(res.data)
      const { outDto } = res.data.resData
      model.value.reservedProdId = outDto?.prdcCd
      model.value.reservedProdNm = outDto?.prdcNm
      // btnCheck()
    }
  } catch (e) {
    console.error('Failed to fetch plans:', e)
  }
}

// const fetchReservedPlanChange = async () => {
//   // 요금제 변경 예약 (X88)
//   try {
//     const { ncn, custId, parentScanId } = model.value
//     const res = await post('/api/form/pricechange/reservedpriceplanchange', {
//       ctn: getPhoneNo(),
//       ncn: ncn,
//       custId: custId,
//       parentScanId: parentScanId,
//       planSoc: model.value.planName2, //reservedpricechange
//       planFtrNewParam: '', //reservedpricechange
//     })
//     if (res.code === '0000' && res.data.resCode === '0000') {
//       const { outDto } = res.data.resData
//       model.value.reservedProdId = outDto?.prodNm
//       model.value.reservedProdNm = outDto?.prodId
//     }
//   } catch (e) {
//     console.error('Failed to fetch plans:', e)
//   }
// }

const fetchReservedPlanCancel = async () => {
  // 요금제 변경 예약 취소 (X90)
  try {
    const { ncn, custId, parentScanId } = model.value
    const res = await post('/api/form/pricechange/reservedpriceplancancel', {
      ctn: getPhoneNo(),
      ncn: ncn,
      custId: custId,
      parentScanId: parentScanId,
    })
    if (res.code === '0000' && res.data.resCode === '0000') {
      // const { outDto } = res.data.resData
      model.value.reservedProdId = ''
      model.value.reservedProdNm = ''
      return true
    }
    return false
  } catch (e) {
    console.error('Failed to fetch plans:', e)
    return false
  }
}

const jehuPartner = ref('')

// 요금제 카테고리가 변경될 때 하위 요금제 목록 다시 불러오기
watch(
  () => model.value.planName1,
  (newVal) => {
    if (!model.value.isSaved) {
      model.value.planName2 = ''
    }
    fetchPlans(newVal)
  },
)

// 요금제 선택 시 prodNm 및 prdtSctnCd 업데이트
watch(
  () => model.value.planName2,
  (newVal) => {
    if (newVal) {
      const selected = planOptions.value.find((opt) => opt.value === newVal)
      console.debug('[MsfChargePlanChange] selected plan', selected)
      if (selected) {
        model.value.prodNm = selected.label
        model.value.planNm = selected.label
        model.value.jehuPartnerTypeCd = selected.raw?.jehuProdType || ''
        model.value.jehuPartnerTypeNm = selected.raw?.jehuProdNm || ''
        model.value.jehuProdTypeCd = selected.raw?.jehuProdType || ''
        jehuPartner.value =
          '개인정보 제3자 제공 [' + model.value.jehuPartnerTypeNm + '] 동의합니다.'
      }
    } else {
      model.value.prodNm = ''
      model.value.planNm = ''
      model.value.jehuPartnerTypeCd = ''
      model.value.jehuPartnerTypeNm = ''
      model.value.jehuProdTypeCd = ''
      jehuPartner.value = ''
    }
    // 변경 시점 관련 value reset
    model.value.changeDate = ''
    model.value.planChangeConfirmCompleted = false
    isRealtimeChargeInfoModalOpen.value = false
  },
)

watch(
  () => model.value.changeDate,
  () => {
    model.value.planChangeConfirmCompleted = false
  },
)

// const fetchPossibleStateChange = async () => {
//   // 요금제 변경 처리 (Y25)
//   try {
//     // const { ncn, custId, userBirthDate, planName2, parentScanId, contractNum, lstComActvDate } = model.value
//     console.log('model:', model.value)
//     const planChange = {
//       planCategoryCd: model.value.planName1 || '',
//       planCd: model.value.planName2 || '',
//       changeTypeCd: model.value.changeDate || '',
//       actCode: model.value.actCode,
//       planSoc: model.value.planName2,
//       planFtrNewParam: model.value.ftrNewParam,
//       openingDate: model.value.lstComActvDate,
//     }
//     const payload = {
//       ...model.value,
//       ctn: getPhoneNo(),
//       planChange: planChange,
//     }
//     const res = await post('/api/msf/formServiceChange/possibleState/change', payload)
//     // {
//     //   ctn: getPhoneNo(),
//     //   ncn: ncn,
//     //   custId: custId,
//     //   customerSsn: userBirthDate,
//     //   contractNum: contractNum,
//     //   openingDate: lstComActvDate,
//     //   parentScanId: parentScanId,
//     //   // prdcList: [
//     //   //   {
//     //   //     prdcCd: planName2,
//     //   //   },
//     //   // ],
//     // })
//     if (res.code === '0000' && res.data.resCode === '0000') {
//       const data = res?.data || {}
//       const rescode = data?.resCode || '999'
//       if (rescode === '0000') {
//         return true
//       }
//     }
//     return false
//   } catch (e) {
//     console.error('fetchPossibleStateCheck:', e)
//   }
// }

const fetchPossibleStateCheck = async () => {
  // 요금제 변경 사전 체크 (Y24)
  try {
    const {
      ncn,
      custId,
      userBirthDate,
      planName2,
      currentProdId,
      currentProdAmt,
      parentScanId,
      contractNum,
      lstComActvDate,
      actCode,
    } = model.value
    const res = await post('/api/msf/formServiceChange/possibleState/check', {
      ctn: getPhoneNo(),
      ncn: ncn,
      custId: custId,
      customerSsn: userBirthDate,
      planSoc: planName2,
      beforePlanSoc: currentProdId,
      beforePlanAmt: currentProdAmt,
      contractNum: contractNum,
      openingDate: lstComActvDate,
      parentScanId: parentScanId,
      actCode: actCode,
      // prdcList: [
      //   {
      //     prdcCd: planName2,
      //   },
      // ],
    })
    if (res.code === '0000' && res.data.resCode === '0000') {
      const data = res?.data || {}
      const rescode = data?.resCode || '999'
      if (rescode === '0000') {
        const resData = data?.resData
        console.log(resData)
        if (resData.rsltCd === '0000' && resData.ruleList != null && resData.ruleList.length > 0) {
          const chkMsg = resData.ruleList
            .map((item) => {
              return `상품명: ${item.prdcNm}
            안내: ${item.ruleMsgSbst}
            `
            })
            .join('\n')
          showAlert(chkMsg)
        }

        return true
      }
    }
    return false
  } catch (e) {
    console.error('fetchPossibleStateCheck:', e)
    return false
  }
}

const reserveBtn = ref(false)
const directBtn = ref(false)

onMounted(async () => {
  await fetchPlanCategories()
  await fetchPlans(model.value.prodCtgId) // 초기 로드
  await fetchCurrentPlan()
  await fetchReservedPlan()
})

// const btnCheck = async () => {
//   const currentYear = new Date().getFullYear()
//   const currentMonth = String(new Date().getMonth() + 1).padStart(2, '0')
//   const thisMonth = currentYear + currentMonth
//   // 초기화
//   reserveBtn.value = false
//   directBtn.value = false
//   // 개통 당월은 예약변경만 가능
//   if (model?.value.lstComActvDate && model?.value.lstComActvDate.length > 6) {
//     var chkMonth1 = model?.value.lstComActvDate.substring(0, 6)
//     if (chkMonth1 == thisMonth) {
//       directBtn.value = true
//     }
//   }
//   // 당월 요금제를 변경한 경우 예약변경만 가능
//   if (model?.value.currentProdStdt && model?.value.currentProdStdt.length > 6) {
//     var chkMonth2 = model?.value.currentProdStdt.substring(0, 6)
//     if (chkMonth2 == thisMonth) {
//       directBtn.value = true
//     }
//   }
//   console.debug('[MsfChargePlanChange] direct change', { direct: directBtn.value })
// }

// 즉시변경 - 실시간 요금 Modal Open Check
const directPlanCheck = async () => {
  const boolChk = await fetchPossibleStateCheck()
  if (boolChk) {
    model.value.planChangeConfirmCompleted = true
    // 즉시 변경일 시 Modal
    if (model.value.changeDate === 'changeDate2') {
      isRealtimeChargeInfoModalOpen.value = true
    }
  }
}

// 예약변경
const reservePlanCheck = async () => {
  const boolChk = await fetchPossibleStateCheck()
  if (boolChk) {
    model.value.planChangeConfirmCompleted = true
  }
}

// 확인 버튼
const chkConfirm = async () => {
  if (model.value.planChangeConfirmCompleted) {
    model.value.changeDate = ''
    model.value.planChangeConfirmCompleted = false
    isRealtimeChargeInfoModalOpen.value = false
    return
  }

  if (!model.value.repAgree1) {
    showAlert('요금제 변경 동의가 필요합니다.')
    return
  }
  if (model.value.jehuPartnerTypeCd && !model.value.repAgree2) {
    showAlert('요금제 변경 동의[개인정보 제3자 제공]가 필요합니다.')
    return
  }
  if (model.value.currentProdId === model.value.planName2) {
    showAlert('현재 이용중인 요금제입니다.')
    return
  }

  const currentYear = new Date().getFullYear()
  const currentMonth = String(new Date().getMonth() + 1).padStart(2, '0')
  const thisMonth = currentYear + currentMonth
  // 개통 당월은 예약변경만 가능
  if (model?.value.lstComActvDate && model?.value.lstComActvDate.length > 6) {
    var chkMonth1 = model?.value.lstComActvDate.substring(0, 6)
    if (chkMonth1 == thisMonth) {
      if (model.value.changeDate == 'changeDate2') {
        showAlert('개통 당월은 예약변경만 가능합니다.')
        return
      }
    }
  }
  // 당월 요금제를 변경한 경우 예약변경만 가능
  if (model?.value.currentProdStdt && model?.value.currentProdStdt.length > 6) {
    var chkMonth2 = model?.value.currentProdStdt.substring(0, 6)
    if (chkMonth2 == thisMonth) {
      if (model.value.changeDate == 'changeDate2') {
        showAlert('당월 요금제를 변경한 경우에는 예약변경만 가능합니다.')
        return
      }
    }
  }

  if (model.value.reservedProdId && model.value.reservedProdId != '') {
    showConfirm(
      '예약 변경된 요금제가 있습니다.\n예약 변경 취소 후 즉시변경/예약변경이 가능합니다.\n예약을 취소하시겠습니까?',
      async () => {
        if (await fetchReservedPlanCancel()) {
          await handlePlanChangeProcess()
        } else {
          showAlert('예약 취소 처리에 실패했습니다. 다시 시도해 주세요.')
          return
        }
      },
    )
  } else {
    await handlePlanChangeProcess()
  }
}

const handlePlanChangeProcess = async () => {
  if (model.value.changeDate) {
    if (model.value.changeDate == 'changeDate1') {
      model.value.actCode = 'RSV'
      await reservePlanCheck()
    } else {
      // model.value.changeDate == 'changeDate2
      model.value.actCode = 'PCN'
      await directPlanCheck()
    }
  }
}

// const clickTest = () => {
// fetchCurrentPlan()
// fetchReservedPlan()
// fetchReservedPlanCancel()
// fetchPossibleStateCheck()
// fetchReservedPlanChange()
// fetchPossibleStateChange()
// }
</script>
<template>
  <!-- 요금제 변경 -->
  <MsfTitleArea title="요금제 변경" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="요금제" tag="div" required>
      <MsfSelect
        title="요금제"
        v-model="model.planName1"
        :options="planCategoryOptions"
        class="ut-w100p"
        placeholder="추천 요금제"
      />
      <MsfSelect
        title="요금제"
        v-model="model.planName2"
        :options="planOptions"
        class="ut-w100p"
        selectPopYn
      />
    </MsfFormGroup>
    <MsfFormGroup label="변경일시" tag="div" required>
      <MsfChip
        v-model="model.changeDate"
        name="inp-changeDate"
        :data="[
          { value: 'changeDate1', label: '예약(익월1일)', disabled: reserveBtn },
          { value: 'changeDate2', label: '즉시변경', disabled: directBtn },
        ]"
      >
        <template #endSlot>
          <MsfButton
            variant="toggle"
            :active="model.planChangeConfirmCompleted"
            :disabled="model.changeDate === '' || model.changeDate === null"
            @click="chkConfirm"
            >{{ model.planChangeConfirmCompleted ? '확인 완료' : '확인' }}</MsfButton
          >
          <!-- <br /><MsfButton
            variant="toggle"
            :disabled="model.changeDate === '' || model.changeDate === null"
            @click="clickTest"
            >테스트</MsfButton
          >{{ model.planName2 }} / {{ model.changeDate }} -->
        </template>
      </MsfChip>
    </MsfFormGroup>
  </MsfStack>
  <!-- 요금제 변경 동의 -->
  <MsfTitleArea title="요금제 변경 동의" />
  <MsfAgreementItem
    type="default"
    v-model="model.repAgree1"
    label="초과 사용료과금 우려 및 기타 안내사항을 모두 확인 하였으며, 변경 진행에 동의합니다."
    :required="true"
    popTitle="초과 사용료 과금 우려 및 기타 안내사항"
    content="초과 사용료 과금 우려 및 기타 안내사항 내용"
  />
  <MsfAgreementItem
    type="default"
    v-model="model.repAgree2"
    :label="jehuPartner"
    :required="true"
    :display
    popTitle="개인정보 제3자 제공 동의"
    content="개인정보 제3자 제공 동의 내용"
    v-if="model.jehuPartnerTypeCd"
  />
  <MsfRealtimeChargeInfoModal v-model="isRealtimeChargeInfoModalOpen" :form-data="model" />
</template>
