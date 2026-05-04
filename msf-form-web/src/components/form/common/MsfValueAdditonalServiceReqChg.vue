<script setup>
import { ref, onMounted, defineModel, watch } from 'vue'
import { post } from '@/libs/api/msf.api'

const model = defineModel({ type: Object, required: true })

const activeFreeServices = ref([])
const activePaidServices = ref([])

const fetchActiveServices = async () => {
  const phoneNo = `${model.value.deviceChgTel1 || ''}${model.value.deviceChgTel2 || ''}${model.value.deviceChgTel3 || ''}`
  const customerLinkName = (model.value.cstmrNm || '').trim()

  if (phoneNo.length < 10 || !customerLinkName) {
    return
  }

  try {
    const res = await post('/api/form/activeaddition/list', {
      subscriberNo: phoneNo,
      customerLinkName: customerLinkName,
    })

    if (res && res.code === '0000' && res.data?.[0]) {
      const result = res.data[0]
      activeFreeServices.value = result.freeAddition || []
      activePaidServices.value = result.paidAddition || []
    }
  } catch (error) {
    console.error('가입 중인 부가서비스 조회 실패:', error)
  }
}

// 정보 변경 시 재조회
watch(
  () => [
    model.value.deviceChgTel1,
    model.value.deviceChgTel2,
    model.value.deviceChgTel3,
    model.value.cstmrNm,
  ],
  () => {
    fetchActiveServices()
  },
)

onMounted(() => {
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
          <td class="ut-text-center">{{ Number(svc.baseAmt).toLocaleString() }} 원</td>
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
