<script setup>
import { ref, onMounted, defineModel, watch } from 'vue'
import { post } from '@/libs/api/msf.api'

const model = defineModel({ type: Object, required: true })

const activeFreeServices = ref([])
const activePaidServices = ref([])

const toNumber = (value) => Number(String(value || 0).replace(/,/g, '')) || 0

const toServiceRow = (svc = {}) => {
  const baseAmt = svc.baseAmt ?? svc.socRateVatValue ?? svc.socRateVat ?? svc.socRateValue ?? 0

  return {
    ...svc,
    rateCd: svc.rateCd || svc.soc || '',
    rateNm: svc.rateNm || svc.socDescription || '',
    baseAmt,
    settingYn: svc.settingYn || 'N',
  }
}

const splitActiveServices = (list = []) => {
  const services = list.map(toServiceRow)

  return {
    freeAddition: services.filter((svc) => toNumber(svc.baseAmt) === 0),
    paidAddition: services.filter((svc) => toNumber(svc.baseAmt) !== 0),
  }
}

const fetchActiveServices = async () => {
  const phoneNo = `${model.value.deviceChgTel1 || ''}${model.value.deviceChgTel2 || ''}${model.value.deviceChgTel3 || ''}`
  const ncn = model.value.ncn || model.value.contractNum || ''
  const payload = {
    ncn,
    ctn: phoneNo,
    custId: model.value.custId || '',
  }

  console.log('[MsfServiceChangeAdditon] 이용중 부가서비스 조회 준비', payload)

  if (!ncn || phoneNo.length < 10) {
    console.log('[MsfServiceChangeAdditon] 이용중 부가서비스 조회 스킵', {
      hasNcn: !!ncn,
      phoneNoLength: phoneNo.length,
      hasCustId: !!payload.custId,
    })
    return
  }

  try {
    console.log('[MsfServiceChangeAdditon] 이용중 부가서비스 조회 요청', payload)
    const res = await post('/api/form/servicechange/myaddsvclist', payload)
    console.log('[MsfServiceChangeAdditon] 이용중 부가서비스 조회 응답', res)

    if (res && res.code === '0000' && res.data) {
      const result = Array.isArray(res.data) ? res.data[0] : res.data
      const normalized = result?.list ? splitActiveServices(result.list) : result
      activeFreeServices.value = normalized?.freeAddition || []
      activePaidServices.value = normalized?.paidAddition || []
      console.log('[MsfServiceChangeAdditon] 이용중 부가서비스 화면 반영', {
        freeCount: activeFreeServices.value.length,
        paidCount: activePaidServices.value.length,
        freeServices: activeFreeServices.value,
        paidServices: activePaidServices.value,
      })
    } else {
      console.log('[MsfServiceChangeAdditon] 이용중 부가서비스 응답 데이터 없음', res)
    }
  } catch (error) {
    console.error('[MsfServiceChangeAdditon] 이용중 부가서비스 조회 실패', error)
  }
}

// 정보 변경 시 재조회
watch(
  () => [
    model.value.deviceChgTel1,
    model.value.deviceChgTel2,
    model.value.deviceChgTel3,
    model.value.ncn,
    model.value.contractNum,
    model.value.custId,
  ],
  (newValue, oldValue) => {
    console.log('[MsfServiceChangeAdditon] 가입자 정보 변경 감지', {
      oldValue,
      newValue,
    })
    fetchActiveServices()
  },
)

onMounted(() => {
  console.log('[MsfServiceChangeAdditon] mounted')
  fetchActiveServices()
})
</script>
<template>
  <!-- 부가서비스 신청/변경 -->
  <MsfTitleArea title="부가서비스 신청/변경" />
  <MsfTable>
    <template #colgroup>
      <col />
      <col style="width: 120px" />
      <col style="width: 112px" />
    </template>
    <template #tbody>
      <tr>
        <th colspan="3" class="ut-text-left">무료 부가서비스</th>
      </tr>
      <template v-if="activeFreeServices.length > 0">
        <tr v-for="svc in activeFreeServices" :key="svc.rateCd">
          <td>{{ svc.rateNm }}</td>
          <td class="ut-text-center">무료</td>
          <td class="ut-text-center">
            <MsfButton variant="subtle" v-if="svc.settingYn === 'Y'">설정</MsfButton>
          </td>
        </tr>
      </template>
      <tr v-else>
        <td colspan="3">
          <div class="nodata-wrap">선택한 무료 서비스가 없습니다.</div>
        </td>
      </tr>

      <tr>
        <th colspan="3" class="ut-text-left">유료 부가서비스</th>
      </tr>
      <template v-if="activePaidServices.length > 0">
        <tr v-for="svc in activePaidServices" :key="svc.rateCd">
          <td>{{ svc.rateNm }}</td>
          <td class="ut-text-center">{{ toNumber(svc.baseAmt).toLocaleString() }} 원</td>
          <td class="ut-text-center">
            <MsfButton variant="subtle" v-if="svc.settingYn === 'Y'">설정</MsfButton>
          </td>
        </tr>
      </template>
      <tr v-else>
        <td colspan="3">
          <div class="nodata-wrap">선택한 유료 서비스가 없습니다.</div>
        </td>
      </tr>
    </template>
  </MsfTable>
  <!-- 합계박스 -->
  <MsfBox>
    <div class="total-box">
      <dl>
        <dt>합계(VAT 포함)</dt>
        <dd>
          <em>{{
            activePaidServices
              .reduce((acc, cur) => acc + Number(cur.baseAmt || 0), 0)
              .toLocaleString()
          }}</em
          ><span class="unit">원</span>
        </dd>
      </dl>
    </div>
    <MsfButtonGroup class="total-btns">
      <MsfButton variant="subtle">추가/삭제</MsfButton>
      <MsfButton variant="toggle" disabled>확인</MsfButton>
      <MsfButton variant="toggle" active>확인 완료</MsfButton>
    </MsfButtonGroup>
  </MsfBox>
  <!-- // 합계박스 -->
  <!-- // 부가서비스 신청/변경 -->
</template>
