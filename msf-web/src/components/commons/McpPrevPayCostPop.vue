<template>
  <Dialog v-model:open="isOpen" :modal="true" :close-on-click-away="true" :close-on-esc="true">
    <DialogContent>
      <DialogHeader>
        <DialogTitle>예상 납부금액</DialogTitle>
        <DialogDescription></DialogDescription>
      </DialogHeader>
      <div class="grid gap-4">
        <div class="cost-box">
          <p>월 단말요금</p>
          <p class="text-end"><span>0</span> 원</p>
          <p>단말기 출고가</p>
          <p class="text-end"><span>499,400</span> 원</p>
        </div>
        <Separator />
        <div class="cost-box">
          <p>월 통신요금</p>
          <p class="text-end"><span>21,000</span> 원</p>
          <p>기본요금</p>
          <p class="text-end"><span>21,000</span> 원</p>
        </div>
        <Separator />
        <p>※ 가입비 및 유심비 등 기타요금은 별도 청구됩니다.</p>
      </div>
    </DialogContent>
  </Dialog>
</template>

<script setup>
const isOpen = defineModel('isOpen', {
  type: Boolean,
  required: true,
})
</script>

<style scoped>
@reference "tailwindcss";

.cost-box {
  @apply grid grid-cols-2 gap-4 items-center justify-between;
}
</style>
