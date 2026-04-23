<script setup>
import { post } from '@/libs/api/msf.api'
import { defineModel, onMounted, ref } from 'vue'

const model = defineModel({ type: Object, required: true })

const agencyOptions = ref([])

// 대리점 목록 조회
const fetchAgencies = async () => {
  try {
    const res = await post('/api/form/agent/list', { shopOrgnId: 'V000001083' })
    const data = res.data || res
    const list = Array.isArray(data) ? data : (data && typeof data === 'object' ? [data] : [])

    agencyOptions.value = list.map((item) => ({
      label: item.orgnNm || item.cntpntNm || '대리점명 없음',
      value: item.ktOrgId || item.shopOrgnId || '',
    }))

    // 결과가 1개뿐이거나, 현재 선택된 값이 없으면 첫 번째 항목 자동 선택
    if (agencyOptions.value.length > 0) {
      if (!model.value.agency || agencyOptions.value.length === 1) {
        model.value.agency = agencyOptions.value[0].value
        model.value.agentCd = agencyOptions.value[0].value
      }
    }
  } catch (error) {
    console.error('Failed to fetch agencies:', error)
  }
}

function update(value) {
  const { serviceSelect, serviceList } = model.value

  // 현재 선택된 것 중 동시변경 불가 서비스 존재 여부
  const hasLocked = serviceSelect.some((selected) =>
    serviceList.some((item) => item.value === selected && item.notConcurrentChange),
  )

  model.value.serviceList = serviceList.map((item) => ({
    ...item,
    disabled: hasLocked && item.notConcurrentChange && !serviceSelect.includes(item.value),
  }))
}

onMounted(() => {
  fetchAgencies() // 대리점 조회
})
</script>
<template>
  <!-- 서비스 변경 선택 -->
  <MsfTitleArea title="서비스 변경 선택" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="서비스 선택" tag="div" required>
      <MsfStack vertical>
        <MsfCheckbox v-model="model.allCheck" label="전체 선택" />
        <MsfChip
          v-model="model.serviceSelect"
          name="inp-serviceSelect"
          :data="model.serviceList"
          multiple
          @change="update"
        />
        <MsfTextList margin="0">
          <li>
            동시 변경 불가능 업무
            <MsfTextList type="dash">
              <li>요금제 변경, 번호변경, 분실복구/일시정지해제 신청</li>
            </MsfTextList>
          </li>
          <li>번호변경 : 평일 오전10시~오후8시까지 가능 (주말 공휴일은 변경불가)</li>
        </MsfTextList>
      </MsfStack>
      <MsfButtonGroup borderTop align="left">
        <MsfButton variant="toggle" disabled>서비스 체크</MsfButton>
      </MsfButtonGroup>
    </MsfFormGroup>
    <MsfFormGroup label="대리점" tag="div" required>
      <MsfSelect
        title="대리점 선택"
        v-model="model.agency"
        :options="agencyOptions"
        class="ut-w-300"
        placeholder="대리점 선택"
      />
    </MsfFormGroup>
  </MsfStack>
  <!-- // 서비스 변경 선택 -->
</template>
