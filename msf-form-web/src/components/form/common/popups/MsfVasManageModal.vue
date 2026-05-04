<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="부가서비스 추가/삭제"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfStack vertical type="formgroups">
      <MsfSelect
        title="추천 부가서비스"
        v-model="service"
        :options="recommendOptions"
        placeholder="추천 부가서비스"
      />

      <MsfTitleArea title="무료 부가 서비스" level="2" class="ut-mt-20" />
      <MsfCheckboxGroup
        v-model="freeService"
        :options="freeVasOptions"
        grid
      />

      <MsfTitleArea title="유료 부가 서비스" level="2" class="ut-mt-20" />
      <MsfCheckboxGroup
        v-model="paidService"
        :options="paidVasOptions"
        grid
      />
    </MsfStack>

    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const service = ref('') // 추천 부가서비스 선택항목
const freeService = ref([]) // 무료부가서비스 선택 (코드값 배열)
const paidService = ref([]) // 유료부가서비스 선택 (코드값 배열)

const recommendOptions = ref([])
const freeVasOptions = ref([])
const paidVasOptions = ref([])

// 팝업 열릴 때 초기화 및 데이터 로드
const onOpen = () => {
  fetchRecommendCodes()
  fetchVasList()
  emit('open')
}

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 추천 부가서비스 공통코드 조회
const fetchRecommendCodes = async () => {
  try {
    const list = await getCommonCodeList('RATE_ADSVC_DIV_CD')
    recommendOptions.value = list.map(item => ({
      label: item.title,
      value: item.code
    }))
  } catch (error) {
    console.error('추천 부가서비스 코드 조회 실패:', error)
  }
}

// 유/무료 부가서비스 목록 조회
const fetchVasList = async () => {
  try {
    const payload = {
      operTypeCd: '',
      prodCtgTypeCd: 'R',
      categoryMstRequest: {
        prodCtgId: ['RFREESVC', 'RRATESVC'],
      },
    }

    const res = await post('/api/form/addition/list', payload)
    if (res && res.code === '0000' && res.data?.[0]) {
      const result = res.data[0]

      // 무료 부가서비스 목록 세팅
      const freeList = result.freeAddition || []
      freeVasOptions.value = freeList.map((v) => ({
        label: v.rateNm,
        value: v.rateCd,
      }))

      // 유료 부가서비스 목록 세팅
      const paidList = result.paidAddition || []
      paidVasOptions.value = paidList.map((v) => ({
        label: `${v.rateNm} (${Number(v.baseAmt || 0).toLocaleString()}원)`,
        value: v.rateCd,
      }))
    }
  } catch (error) {
    console.error('부가서비스 조회 실패:', error)
  }
}

const onConfirm = () => {
  // 선택된 항목들을 객체 배열 형태로 가공하여 전달
  const selectedList = [
    ...freeService.value.map(code => ({ prodId: code, isFree: true })),
    ...paidService.value.map(code => ({ prodId: code, isFree: false }))
  ]
  
  emit('confirm', {
    recommendService: service.value,
    selectedServices: selectedList,
    freeCodes: freeService.value,
    paidCodes: paidService.value
  })
  onClose()
}

onMounted(() => {
  if (props.modelValue) {
    onOpen()
  }
})
</script>

<style lang="scss" scoped>
.ut-mt-20 {
  margin-top: rem(20px);
}
</style>
