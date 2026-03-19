<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="isOpen"
    title="예상 납부금액"
    @open="onOpen"
    @close="onClose"
  >
    <div class="grid gap-4">
      <div class="cost-box">
        <p>월 단말요금</p>
        <p class="text-end"><span>0</span> 원</p>
        <p>단말기 출고가</p>
        <p class="text-end"><span>499,400</span> 원</p>
      </div>
      <div class="separator"></div>
      <div class="cost-box">
        <p>월 통신요금</p>
        <p class="text-end"><span>21,000</span> 원</p>
        <p>기본요금</p>
        <p class="text-end"><span>21,000</span> 원</p>
      </div>
      <div class="separator"></div>
      <p>※ 가입비 및 유심비 등 기타요금은 별도 청구됩니다.</p>
    </div>
  </MsfDialog>
</template>

<script setup>
defineOptions({
  inheritAttrs: false,
})

const isOpen = defineModel('is-open', { type: Boolean, default: false })

const emit = defineEmits(['open', 'close'])

const onOpen = () => {
  emit('open', true)
}

// 닫힘 이벤트
const onClose = () => {
  isOpen.value = false
  emit('close', false)
}
</script>

<style scoped>
@reference "tailwindcss";

.cost-box {
  @apply grid grid-cols-2 gap-4 items-center justify-between;
}
.separator {
  @apply border border-neutral-300 h-px w-full;
}
</style>
