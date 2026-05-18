<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="구비서류"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <p class="ut-text-caution ut-weight-medium">
      촬영 버튼을 선택하신 후, 구비서류를 촬영해주세요.
    </p>
    <div class="doc-list-wrap">
      <ul class="doc-list">
        <li>
          <p>가족관계증명서</p>
          <MsfButton variant="subtle">촬영하기</MsfButton>
        </li>
        <li>
          <p>
            외국인등록증/거소신고증
            <MsfFlag data="완료" color="accent2" size="small" />
          </p>
          <MsfButton variant="subtle">촬영하기</MsfButton>
        </li>
        <li>
          <p>사업자등록증<MsfFlag data="완료" color="accent2" size="small" /></p>
          <MsfButton variant="subtle">촬영하기</MsfButton>
        </li>
        <li>
          <p>법인인감증명서</p>
          <MsfButton variant="subtle">촬영하기</MsfButton>
        </li>
        <li>
          <p>위임장(법인인감 날인 포함)</p>
          <MsfButton variant="subtle">촬영하기</MsfButton>
        </li>
        <li>
          <p>대리인 신분증</p>
          <MsfButton variant="subtle">촬영하기</MsfButton>
        </li>
        <li>
          <p>대리인 재직증명서</p>
          <MsfButton variant="subtle">촬영하기</MsfButton>
        </li>
      </ul>
    </div>
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
// doc-list-wrap 스타일 _common.scss로 이동__20260514
</style>
