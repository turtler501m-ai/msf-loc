<!-- 신청서 확인 타이틀 + (메시지 + 박스영역) -->
<template>
  <MsfTitleArea title="신청서 확인" />
  <p class="ut-text-desc">
    신청서 확인을 눌러서, 신청서 작성 내용을 확인하신 후 가입자(대리인) 서명을 등록해주세요.
  </p>
  <MsfBox variant="outline">
    <div class="inner-info-wrap">
      <img
        src="@/assets/images/applyConfirmIcon.svg"
        alt="신분증 스캔 가이드: 빛 반사에 주의하세요."
        class="info-img"
      />
      <div class="info-msg">
        <strong class="info-tit">
          <slot name="title">
            <span v-html="props.title"></span>
          </slot>
        </strong>
        <span class="info-tit-desc" v-html="props.description"></span>
      </div>
    </div>
    <MsfButtonGroup align="center" margin="2">
      <MsfButton variant="subtle" @click="$emit('confirm')">신청서 확인</MsfButton>
    </MsfButtonGroup>
  </MsfBox>
</template>

<script setup>
defineEmits(['confirm'])

const props = defineProps({
  // 타이틀 문구 설정시 사용
  title: {
    type: String,
    default: '서명 후,<br/>작성한 내용이 변경된 경우',
  },
  // 설명문구 설정시 사용
  description: {
    type: String,
    default: '서명정보를 변경하여 작성하여 주시기 바랍니다.',
  },
})
</script>

<style lang="scss" scoped></style>
