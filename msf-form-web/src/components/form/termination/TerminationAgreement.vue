<template>
  <div class="page-step-panel">
    <!-- 고객 안내 사항 -->
    <MsfTitleArea title="고객 안내 사항" />
    <MsfBox>
      <ul class="agree-list">
        <li>
          <p class="agree-tit">혜택 소멸사항 동의</p>
          <MsfTextList
            :items="[
              '고객님께 제공되었던 평생할인 및 각종 결합할인 등이 모두 소멸되어 다양한 혜택을 제공받을 수 없게 됩니다.',
              '해지 시 DB 단체보험, 제주항공, 티머니 등 케이티엠모바일의 제휴서비스가 제공되지 않습니다.',
            ]"
            type="number"
            level="2"
            margin="0"
            bottomDivider
          />
          <MsfCheckbox
            v-model="agreement.agreeCheck1"
            label="본인은 위 혜택에 대한 설명을 듣고, 케이티엠모바일해지시 혜택 소멸되는 사항에 대해 동의합니다."
          />
        </li>
      </ul>
    </MsfBox>
    <!-- // 고객 안내 사항 -->
    <!-- 신청서 확인 -->
    <MsfAppConfirm @confirm="console.log('신청서 확인 버튼 클릭!!')" />
    <!-- // 신청서 확인 -->

    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발해주신 부분 - 화면 프로세스</p>
        <select v-model="isComplete">
          <option value="">동의 저장</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { agreement } = terminationStore

const isComplete = ref('')

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(
  () => isComplete.value,
  (newVal) => {
    isComplete.value = newVal
    emit('complete', newVal ? true : false)
  },
)

const save = async () => {
  if (isComplete.value !== 'true') return false
  return await terminationStore.apiCompleteApplication()
}

defineExpose({ save })
</script>

<style scoped></style>
