<!-- 작성완료 : 신청서 발송 박스영역 -->
<template>
  <MsfBox variant="outline" margin="0" class="application-box">
    <div class="box-title">
      <img src="@/assets/images/applySendIcon.svg" alt="신청서 발송 아이콘" />
      <em class="tit">신청서 발송</em>
    </div>
    <MsfStack type="field" nowrap>
      <MsfInput v-model="phoneNumber" readonly />
      <MsfButton variant="subtle">발송</MsfButton>
    </MsfStack>
  </MsfBox>
</template>

<script setup>
import { ref } from 'vue'
const phoneNumber = ref('010-1111-2222')
</script>

<style lang="scss" scoped>
.application-box {
  .box-title {
    @include flex($d: column, $v: center) {
      gap: rem(8px);
    }
    .tit {
      font-size: var(--font-size-16);
      font-weight: var(--font-weight-medium);
      color: var(--color-gray-600);
    }
  }
}
</style>
