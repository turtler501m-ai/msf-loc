<template>
  <MsfTitleBar title="서비스해지 신청서 > 동의 퍼블확인용" />
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
            v-model="formData.agreeCheck1"
            label="본인은 위 혜택에 대한 설명을 듣고, 케이티엠모바일해지시 혜택 소멸되는 사항에 대해 동의합니다."
          />
        </li>
      </ul>
    </MsfBox>
    <!-- // 고객 안내 사항 -->
    <!-- 신청서 확인 -->
    <MsfAppConfirm @confirm="console.log('신청서 확인 버튼 클릭!!')" />
    <!-- // 신청서 확인 -->
  </div>
</template>

<script setup>
import { ref, watch, reactive } from 'vue'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

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
  //  최종 데이터 저장
  return isComplete.value === 'true'
}

defineExpose({ save })

// 퍼블샘플
const formData = reactive({
  agreeCheck1: false,
})
</script>

<style scoped></style>
