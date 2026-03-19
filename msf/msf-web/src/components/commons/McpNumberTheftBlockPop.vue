<template>
  <Dialog v-model:open="isOpen" :modal="true" :close-on-click-away="false">
    <DialogContent class="max-w-lg" :show-close-button="true">
      <DialogHeader>
        <DialogTitle>번호도용 차단 서비스</DialogTitle>
        <DialogDescription>타 기관 정보 제공 동의가 필요합니다.</DialogDescription>
      </DialogHeader>
      <div class="grid gap-4">
        <div class="p-4 bg-gray-50 rounded-lg text-sm text-gray-700 space-y-2">
          <p>번호도용 차단 서비스를 이용하시려면 타 기관에 정보 제공에 대한 동의가 필요합니다.</p>
          <p class="text-xs text-gray-500">제공되는 정보는 서비스 제공 목적으로만 사용됩니다.</p>
        </div>
        <label class="flex items-start gap-3 cursor-pointer">
          <input
            v-model="agreed"
            type="checkbox"
            class="mt-1 rounded border-gray-300"
          />
          <span class="text-sm">타 기관 정보 제공에 동의합니다. (필수)</span>
        </label>
        <p v-if="!agreed && triedConfirm" class="text-sm text-red-600">
          동의를 선택해 주세요.
        </p>
      </div>
      <DialogFooter class="mt-4 gap-2 sm:gap-0">
        <DialogClose as-child>
          <Button variant="outline">취소</Button>
        </DialogClose>
        <Button @click="confirm">확인</Button>
      </DialogFooter>
    </DialogContent>
  </Dialog>
</template>

<script setup>
import { ref, watch } from 'vue'
import Dialog from '@/components/ui/dialog/Dialog.vue'
import DialogContent from '@/components/ui/dialog/DialogContent.vue'
import DialogHeader from '@/components/ui/dialog/DialogHeader.vue'
import DialogTitle from '@/components/ui/dialog/DialogTitle.vue'
import DialogDescription from '@/components/ui/dialog/DialogDescription.vue'
import DialogFooter from '@/components/ui/dialog/DialogFooter.vue'
import DialogClose from '@/components/ui/dialog/DialogClose.vue'
import Button from '@/components/ui/button/Button.vue'

const isOpen = defineModel('open', { type: Boolean, default: false })
const emit = defineEmits(['confirm'])

const agreed = ref(false)
const triedConfirm = ref(false)

function confirm() {
  triedConfirm.value = true
  if (!agreed.value) return
  emit('confirm', { agreed: true })
  isOpen.value = false
}

watch(isOpen, (v) => {
  if (!v) {
    agreed.value = false
    triedConfirm.value = false
  }
})
</script>
