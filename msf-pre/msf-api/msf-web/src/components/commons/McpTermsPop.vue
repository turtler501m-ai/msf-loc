<template>
  <Dialog v-model:open="isOpen" :modal="true" :close-on-click-away="true" :close-on-esc="true">
    <DialogContent class="max-w-lg max-h-[80vh] flex flex-col">
      <DialogHeader>
        <DialogTitle>{{ title }}</DialogTitle>
        <DialogDescription v-if="description">{{ description }}</DialogDescription>
      </DialogHeader>
      <div class="flex-1 overflow-y-auto rounded border border-gray-200 p-4 text-sm text-gray-700 whitespace-pre-wrap">
        {{ content }}
      </div>
    </DialogContent>
  </Dialog>
</template>

<script setup>
import Dialog from '@/components/ui/dialog/Dialog.vue'
import DialogContent from '@/components/ui/dialog/DialogContent.vue'
import DialogHeader from '@/components/ui/dialog/DialogHeader.vue'
import DialogTitle from '@/components/ui/dialog/DialogTitle.vue'
import DialogDescription from '@/components/ui/dialog/DialogDescription.vue'

const isOpen = defineModel('open', { type: Boolean, default: false })

defineProps({
  title: { type: String, default: '약관 상세' },
  description: { type: String, default: '' },
  content: { type: String, default: '약관 내용은 m포털 약관 테이블 연동 후 표시됩니다.' },
})
</script>

<style scoped></style>
