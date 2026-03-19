<template>
  <Dialog v-model:open="isOpen" :modal="true" :close-on-click-away="false">
    <DialogContent class="max-w-lg" :show-close-button="true">
      <DialogHeader>
        <DialogTitle>불법TM수신차단</DialogTitle>
        <DialogDescription>수신 차단할 번호를 등록하세요. (최대 50개)</DialogDescription>
      </DialogHeader>
      <div class="grid gap-4">
        <div class="flex gap-2">
          <input
            v-model="newNumber"
            type="text"
            placeholder="010-1234-5678 또는 자리별 부분 입력"
            maxlength="20"
            class="flex-1 rounded border border-gray-300 px-3 py-2 text-sm"
            @keydown.enter.prevent="addNumber"
          />
          <button
            type="button"
            class="px-4 py-2 rounded bg-teal-600 text-white text-sm whitespace-nowrap disabled:opacity-50"
            :disabled="blockList.length >= 50"
            @click="addNumber"
          >
            번호추가
          </button>
        </div>
        <p v-if="listError" class="text-sm text-red-600">{{ listError }}</p>
        <p class="text-xs text-gray-500">자리별 부분 차단 가능 (예: 010-****-1234). 등록 수: {{ blockList.length }}/50</p>
        <ul class="max-h-48 overflow-y-auto border border-gray-200 rounded divide-y text-sm">
          <li
            v-for="(item, idx) in blockList"
            :key="idx"
            class="flex items-center justify-between px-3 py-2 hover:bg-gray-50"
          >
            <span class="font-mono">{{ item }}</span>
            <button
              type="button"
              class="text-red-600 hover:underline"
              @click="removeNumber(idx)"
            >
              삭제
            </button>
          </li>
          <li v-if="!blockList.length" class="px-3 py-4 text-gray-500 text-center">
            등록된 번호가 없습니다.
          </li>
        </ul>
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

const newNumber = ref('')
const blockList = ref([])
const listError = ref('')

function validateNumber(num) {
  const t = num.replace(/\s/g, '').trim()
  if (!t) return { ok: false, msg: '번호를 입력하세요.' }
  const digitCount = (t.match(/\d/g) || []).length
  if (digitCount < 4 || digitCount > 11) return { ok: false, msg: '유효한 번호 형식이 아닙니다.' }
  return { ok: true, normalized: t }
}

function addNumber() {
  listError.value = ''
  const { ok, msg, normalized } = validateNumber(newNumber.value)
  if (!ok) {
    listError.value = msg
    return
  }
  if (blockList.value.length >= 50) {
    listError.value = '최대 50개까지 등록 가능합니다.'
    return
  }
  if (blockList.value.includes(normalized)) {
    listError.value = '이미 등록된 번호입니다.'
    return
  }
  blockList.value = [...blockList.value, normalized]
  newNumber.value = ''
}

function removeNumber(idx) {
  blockList.value = blockList.value.filter((_, i) => i !== idx)
}

function confirm() {
  emit('confirm', { blockList: [...blockList.value] })
  isOpen.value = false
}

watch(isOpen, (v) => {
  if (!v) {
    newNumber.value = ''
    blockList.value = []
    listError.value = ''
  }
})
</script>
