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
      <MsfStack vertical type="formgroups">
        <MsfFormGroup label="신분증" required tag="div">
          <MsfSelect
            title="신분증 선택"
            v-model="fathRequest.identityType"
            group-code="RCP2006"
            placeholder="선택해주세요."
            class="ut-w-300"
            :disabled-items="['06']"
          />
        </MsfFormGroup>
        <MsfFormGroup
          label="휴대폰번호"
          required
          helpText="※ 입력하신 휴대폰번호로 안면인증 URL이 발송됩니다."
          tag="div"
        >
          <MsfStack type="field">
            <MsfMobileInput
              v-model:number1="fathRequest.phone1"
              v-model:number2="fathRequest.phone2"
              v-model:number3="fathRequest.phone3"
              @verify="onVerifyPhoneInput"
            />
          </MsfStack>
        </MsfFormGroup>
      </MsfStack>
    </MsfBox>
    <MsfButtonGroup align="center" margin="1">
      <MsfButton :disabled="!valid" @click="onClickOpenBrowserBtn">안면인증 URL 열기</MsfButton>
      <MsfButton :disabled="!valid" @click="onClickSendSmsBtn">안면인증 URL 받기</MsfButton>
      <MsfButton :disabled="!valid" @click="onClickShowQrBtn">안면인증 QR 생성</MsfButton>
    </MsfButtonGroup>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton v-if="isConfirmResultBtn" variant="primary" @click="onClickConfirmResultBtn">
          안면인증 결과확인
        </MsfButton>
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

const props = defineProps({
  modelValue: Boolean,
  joinType: { type: String, default: '' },
  customerType: { type: String, default: '' },
  bizNumber: { type: String, default: '' },
  minorAgentName: { type: String, default: '' },
  minorAgentBirth: { type: String, default: '' },
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

const isPopinPopOpen = ref(false)
const valid = ref(false) // 휴대폰번호 입력값 검증 여부
const isConfirmResultBtn = ref(false) // 안면인증 결과확인 버튼 노출 여부 (샘플에서는 항상 false로 유지)

const fathRequest = reactive({
  identityType: '',
  phone1: '010',
  phone2: '',
  phone3: '',
})

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onVerifyPhoneInput = (result) => {
  valid.value = result
}

const onClickOpenBrowserBtn = () => {}

const onClickSendSmsBtn = () => {}

const onClickShowQrBtn = () => {
  isPopinPopOpen.value = true
}

const onClickConfirmResultBtn = () => {}
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
