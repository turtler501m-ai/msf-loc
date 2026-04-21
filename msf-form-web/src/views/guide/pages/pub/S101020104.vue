<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="안면인증"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfBox margin="0">
      <MsfFormGroup
        label="휴대폰번호"
        required
        helpText="※ 입력하신 휴대폰번호로 안면인증 URL이 발송됩니다."
        tag="div"
      >
        <MsfStack type="field">
          <MsfMobileInput
            v-model:number1="userPhone.p1"
            v-model:number2="userPhone.p2"
            v-model:number3="userPhone.p3"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfBox>
    <MsfButtonGroup align="center" margin="1">
      <MsfButton>안면인증 URL 받기</MsfButton>
      <MsfButton @click="isPopinPopOpen = true">안면인증 QR 생성</MsfButton>
    </MsfButtonGroup>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary">안면인증 결과확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
  <!-- QR코드 팝업 : 별도 파일로 빼셔도 됩니다. -->
  <MsfDialog
    size="medium"
    :isOpen="isPopinPopOpen"
    title="QR코드"
    showClose
    @close="isPopinPopOpen = false"
  >
    <p class="ut-text-title3 ut-text-center">QR Code를 조회해 주세요.</p>
    <!-- qr영역 -->
    <div class="qr-wrapper">
      <div class="qr-area">
        <!-- QR 들어감 -->
        <img src="@/assets/images/dummy/@sample_qr.png" alt="qr샘플이미지" />
      </div>
      <p class="qr-conut">QR 유효시간 <em class="qr-count-txt">00:14</em></p>
    </div>
    <!-- // qr영역 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary">취소</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
  <!-- // QR코드 팝업 : 별도 파일로 빼셔도 됩니다. -->
</template>

<script setup>
import { ref, reactive } from 'vue'

const isPopinPopOpen = ref(false)
const userPhone = reactive({
  p1: '010',
  p2: '',
  p3: '',
})
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

<style lang="scss" scoped>
.doc-list-wrap {
  width: 100%;
  margin-top: rem(16px);
  border-top: var(--border-width-base) solid var(--color-gray-150);
  border-bottom: var(--border-width-base) solid var(--color-gray-75);
  .doc-list {
    & > li {
      padding-block: rem(16px);
      padding-inline: rem(24px);
      @include flex($v: center, $h: space-between) {
        gap: rem(16px);
      }
      border-top: var(--border-width-base) solid var(--color-gray-75);
      & > p:first-child {
        flex: 1;
        @include flex($v: center, $w: wrap) {
          gap: rem(4px);
        }
      }
      & > :last-child {
        flex-shrink: 0;
        flex-grow: 0;
      }
    }
  }
}
</style>
