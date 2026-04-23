<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { post } from '@/libs/api/msf.api'
import { formatDate } from '@/libs/utils/date.utils'

const formData = defineModel({ type: Object, required: true })

const agencyOptions = ref([])
const formattedLstComActvDate = computed({
  get() {
    return formatDate(formData.value.lstComActvDate) || formData.value.lstComActvDate || ''
  },
  set(value) {
    formData.value.lstComActvDate = value
  },
})

const applyAgencyMeta = (agencyValue) => {
  const selected = agencyOptions.value.find((v) => v.value === agencyValue)
  if (!selected) return

  formData.value.managerCd = selected.ktOrgId || selected.value
  formData.value.managerNm = selected.label
  formData.value.agentCd = selected.orgnId || selected.value
  formData.value.agentNm = selected.label
  formData.value.agencyName = selected.value

  // cpntId = KT_ORG_ID (ASIS 세션 접점 아이디와 동일 출처)
  // cntpntShopCd = ORGN_ID (채널점 판매점 코드)
  formData.value.cpntId = selected.ktOrgId || selected.orgnId || selected.value
  formData.value.cpntNm = selected.label
  formData.value.cntpntShopCd = selected.orgnId || selected.value
  formData.value.cntpntShopNm = selected.label
}

const fetchAgencies = async () => {
  try {
    // MsfServiceChangeSelection.vue 구현 참고
    const res = await post('/api/msf/formTermination/agent/list', { shopOrgnId: 'V000001083' })
    const data = res?.data || res
    const list = Array.isArray(data) ? data : (data && typeof data === 'object' ? [data] : [])

    agencyOptions.value = list.map((item) => ({
      label: item.orgnNm || item.cntpntNm || '대리점명 없음',
      value: item.ktOrgId || item.shopOrgnId || item.orgnId || '',
      ktOrgId: item.ktOrgId || '',
      orgnId: item.orgnId || item.shopOrgnId || '',
    }))

    if (agencyOptions.value.length > 0) {
      if (!formData.value.agencyName || agencyOptions.value.length === 1) {
        const first = agencyOptions.value[0]
        formData.value.agencyName = first.value
        applyAgencyMeta(first.value)
      } else {
        applyAgencyMeta(formData.value.agencyName)
      }
    }
  } catch (error) {
    console.error('[Termination] failed to fetch agencies', error)
  }
}

watch(
  () => formData.value?.agencyName,
  (agencyName) => {
    if (!agencyName) return
    applyAgencyMeta(agencyName)
  },
)

onMounted(() => {
  fetchAgencies()
})
</script>

<template>
  <MsfTitleArea title="가입유형 선택" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="개통일자 확인">
      <MsfInput v-model="formattedLstComActvDate" class="ut-w-300" disabled />
    </MsfFormGroup>
    <MsfFormGroup label="대리점" tag="div" required>
      <MsfSelect
        title="대리점 선택"
        v-model="formData.agencyName"
        :options="agencyOptions"
        class="ut-w-300"
        placeholder="대리점 선택"
      />
    </MsfFormGroup>
  </MsfStack>
</template>
