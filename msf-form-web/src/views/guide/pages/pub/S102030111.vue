<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="특정번호 수신차단서비스"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfTitleArea level="2" noline>
      <template #title>
        <span>수신차단 번호 입력(<span class="ut-color-point">3</span>/50)</span>
      </template>
      <template #actions>
        <MsfButton variant="subtle">번호추가</MsfButton>
      </template>
    </MsfTitleArea>
    <!-- 수신차단 번호 입력 목록 -->
    <MsfCustomScroll height="268px" class="block-list-wrap">
      <ul class="block-list">
        <li v-for="n in 20" :key="n">
          <MsfStack type="field">
            <MsfNumberInput
              v-model="formData.numberValue1"
              placeholder="앞자리"
              ariaLabel="수신차단 번호 앞자리"
              maxlength="3"
              class="ut-w-140"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              v-model="formData.numberValue2"
              placeholder="중간 자리"
              ariaLabel="수신차단 번호 중간 자리"
              maxlength="4"
              class="ut-w-140"
            />
            <span class="unit-sep">-</span>
            <MsfNumberInput
              v-model="formData.numberValue3"
              placeholder="뒷 자리"
              ariaLabel="수신차단 번호 뒷 자리"
              maxlength="4"
              class="ut-w-140"
            />
          </MsfStack>
          <div class="side">
            <MsfButton iconOnly="clear" variant="ghost" size="small"></MsfButton>
          </div>
        </li>
      </ul>
    </MsfCustomScroll>
    <!-- // 수신차단 번호 입력 목록 -->
    <MsfBox>
      <MsfTextList
        :items="[
          '수신차단 번호는 최대 100개까지 설정 가능하며, 등록한 번호로 수신되는 음성통화, 문자메시지, 음성사서함 모두 차단합니다.',
          '각 자리별 입력된 번호를 포함하는 번호는 모두 차단합니다.(자리별 부분 차단 가능)',
        ]"
        level="2"
      />
    </MsfBox>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary">취소</MsfButton>
        <MsfButton variant="primary">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { reactive } from 'vue'

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

// 퍼블샘플
const formData = reactive({
  numberValue1: '',
  numberValue2: '',
  numberValue3: '',
})
</script>

<style lang="scss" scoped></style>
