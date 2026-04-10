<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="부가서비스 추가/삭제"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfSelect
      title="추천 부가서비스"
      v-model="service"
      :options="[
        { label: '추천 부가서비스1', value: 'service1' },
        { label: '추천 부가서비스2', value: 'service2' },
      ]"
      placeholder="추천 부가서비스"
    />
    <MsfTitleArea title="무료 부가 서비스" level="2" />
    <MsfCheckboxGroup
      v-model="freeService"
      :options="[
        { value: 'select1', label: '무선데이터 차단' },
        { value: 'select2', label: '데이터로밍 원천 차단(MMS 가능)' },
        { value: 'select3', label: '국제전화발신제한' },
        { value: 'select4', label: '링투유알리미' },
        { value: 'select5', label: '익명호수신거부' },
        { value: 'select6', label: '음성로밍 차단' },
        { value: 'select7', label: '네트워크유해차단' },
      ]"
      grid
    />
    <MsfTitleArea title="유료 부가 서비스" level="2" />
    <MsfCheckboxGroup
      v-model="paidService"
      :options="[
        { value: 'select1', label: '링투유  990원' },
        { value: 'select2', label: '국제전화발신제한  550원' },
        { value: 'select3', label: '익명호수신거부  2,200원/1일' },
      ]"
      grid
    />
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

const service = ref('') // 부가서비스 선택항목
const freeService = ref([]) // 무료부가서비스 선택
const paidService = ref([]) // 유료부가서비스 선택

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}
</script>

<style lang="scss" scoped>
.ut-text-desc {
  padding-bottom: rem(12px);
  margin-bottom: rem(12px);
  border-bottom: var(--border-width-base) solid var(--color-gray-150);
}
</style>
