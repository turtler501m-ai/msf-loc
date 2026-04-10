<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="번호이동 사전동의 실패"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <div class="info-msg">
      <img src="@/assets/images/noticeIcon.svg" alt="알림" />
      <div class="info-txt">
        <strong>번호이동 사전동의 확인이 되지 않습니다.</strong>
        <p class="desc">아래 내용 확인 후 다시 시도해주세요.</p>
      </div>
    </div>
    <MsfBox>
      <MsfTextList type="dash">
        <li>현재 사용 중인 통신사를 맞게 선택했는지 확인해 주세요.</li>
        <li>고객정보가 틀리지 않았는지 확인해 주세요.</li>
        <li>인증항목(납부 카드, 계좌 또는 단말 일련번호 4자리 수)이 맞는지 확인해 주세요.</li>
        <li>번호이동을 한 지 3개월 이내일 경우 번호이동이 불가합니다.</li>
        <li>
          전 통신사에 미납금액이 있을 경우(할부 금액, 로밍 요금 등), 전 통신사에 잔여 금액을
          납부해야 번호이동이 가능합니다.
          <p>※ www.credit.or.kr에서 납부 방법 조회 또는 미납 통신사에 문의</p>
        </li>
      </MsfTextList>
    </MsfBox>
    <MsfTextList
      :items="[
        '상기 항목 정확이 이행하였으나 실패가 뜰 경우 10분 후에 다시 시도해 주세요.',
        '이후에도 실패가 계속될 경우 개통센터(1899-0034)로 연락해 주세요.',
      ]"
      type="dash"
    />
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="primary" @click="onClose">확인</MsfButton>
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

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}
</script>

<style lang="scss" scoped>
.info-msg {
  padding-top: 24px;
  & > img {
    width: rem(96px);
    height: rem(96px);
  }
  @include flex($d: column, $v: center, $h: center) {
    gap: rem(16px);
  }
  text-align: center;
  .info-txt {
    @include flex($d: column, $v: center, $h: center) {
      gap: rem(4px);
    }
    strong {
      color: var(--color-accent-base);
      font-size: var(--font-size-24);
      line-height: var(--line-height-heading);
      font-weight: var(--font-weight-bold);
    }
  }
}
</style>
