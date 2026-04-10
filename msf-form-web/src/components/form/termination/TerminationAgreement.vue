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
import { ref, watch, onMounted } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'

/**
 * Step 3: 고객 안내 사항 동의 컴포넌트
 *
 * 역할:
 *  - 혜택 소멸 동의 체크박스(agreeCheck1) 표시
 *  - 필수 항목 완료 여부를 부모(MsfFormView)에 emit('complete') 로 전달
 *  - save() 호출 시 최종 신청 API(apiCompleteApplication) 실행
 *
 * 부모와의 인터페이스:
 *  - emit('complete', boolean) : 다음/작성완료 버튼 활성화 여부 제어
 *  - defineExpose({ save })    : 부모가 save() 를 직접 호출하여 최종 저장 처리
 */

// 부모(MsfFormView)로 현재 스텝 완료 여부를 전달하는 이벤트
const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { agreement } = terminationStore

/**
 * [TEST] 화면 테스트용 — 개발/검증 완료 후 '' 로 초기화 필요
 * 실제 운영: 고객이 agreeCheck1 체크 후 select 값 변경으로 완료 처리
 * 'true'  → 성공 (작성완료 버튼 활성화)
 * 'false' → 실패
 * ''      → 미선택 (초기 상태)
 */
const isComplete = ref('true')

/**
 * isComplete 값 변경 감지 → 부모에 완료 여부 전달
 * immediate: true — 컴포넌트 로드 시 즉시 실행하여 초기 버튼 상태 반영
 * (기본값 'true' 이므로 로드 즉시 작성완료 버튼 활성화됨)
 */
watch(
  () => isComplete.value,
  (newVal) => {
    isComplete.value = newVal
    emit('complete', newVal ? true : false)
  },
  { immediate: true },
)

/**
 * onMounted 시점에 초기 완료 상태를 부모에 한 번 더 전달
 * immediate watch는 setup() 중 실행되나, 부모의 이벤트 리스너 연결 타이밍에 따라
 * 이벤트가 누락될 수 있어 mount 후 한 번 더 emit 하여 버튼 활성화를 보장.
 */
onMounted(() => {
  emit('complete', isComplete.value ? true : false)
})

/**
 * 작성완료 버튼 클릭 시 부모(MsfFormView.onClickCompelteBtn)에서 호출
 * 1. isComplete 검증 실패 → false 반환 (부모에서 실패 알림 처리)
 * 2. 검증 성공 → apiCompleteApplication() 호출 → 신청 완료 API POST
 * @returns {Promise<boolean>} 성공 true / 실패 false
 */
const save = async () => {
  if (isComplete.value !== 'true') return false
  return await terminationStore.apiCompleteApplication()
}

defineExpose({ save })
</script>

<style scoped></style>
