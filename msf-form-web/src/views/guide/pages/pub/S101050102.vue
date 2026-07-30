<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="비밀번호 입력"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- 팝업 내용 -->
    <p class="ut-text-title3">고객 식별을 위한 비밀번호를 입력해 주세요.</p>
    <MsfBox>
      <MsfTextList type="none">
        <li>개인 고객<span class="ut-colon">:</span>생년월일(YYYYMMDD) 8자리</li>
        <li>
          <div class="ut-d-flex ut-ai-baseline">
            <div class="ut-flex-shrink-0">법인 및 공공기관 고객<span class="ut-colon">:</span></div>
            <div class="ut-flex-1">
              사업자번호 10자리
              <p class="ut-text-body2 ut-mt-2">(사업자번호 없는 경우 법인번호 앞 6자리)</p>
            </div>
          </div>
        </li>
      </MsfTextList>
      <MsfInput
        type="password"
        v-model="passwordValue"
        placeholder="비밀번호 입력"
        class="ut-mt-16"
      />
    </MsfBox>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="primary">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'

const passwordValue = ref()

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}
</script>

<style lang="scss" scoped></style>
