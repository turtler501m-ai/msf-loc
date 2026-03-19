<template>
  <Dialog v-model:open="isOpen" :modal="true" :close-on-click-away="false">
    <DialogContent class="max-w-md" :show-close-button="true">
      <DialogHeader>
        <DialogTitle>신규번호 검색</DialogTitle>
        <DialogDescription>희망번호 4자리를 입력 후 조회하세요. (일 50회 제한)</DialogDescription>
      </DialogHeader>
      <div class="grid gap-4">
        <div class="flex gap-2">
          <input
            v-model="prefixDisplay"
            type="text"
            placeholder="010-****-"
            maxlength="9"
            class="flex-1 rounded border border-gray-300 px-3 py-2 font-mono"
            readonly
          />
          <input
            v-model="suffix"
            type="text"
            placeholder="뒤 4자리"
            maxlength="4"
            class="w-24 rounded border border-gray-300 px-3 py-2 font-mono text-center"
            @keydown.enter.prevent="search"
          />
          <button
            type="button"
            class="px-4 py-2 rounded bg-teal-600 text-white disabled:opacity-50"
            :disabled="suffix.length !== 4"
            @click="search"
          >
            번호조회
          </button>
        </div>
        <p v-if="searchError" class="text-sm text-red-600">{{ searchError }}</p>
        <p v-if="remainCount !== null" class="text-xs text-gray-500">오늘 조회 가능 횟수: {{ remainCount }}회</p>
        <ul v-if="resultList.length" class="max-h-48 overflow-y-auto border border-gray-200 rounded divide-y text-sm">
          <li
            v-for="(item, idx) in resultList"
            :key="idx"
            class="flex items-center justify-between px-3 py-2 hover:bg-teal-50"
          >
            <span class="font-mono">{{ item }}</span>
            <div class="gap-2 flex">
              <button
                v-if="selectedNumber !== item"
                type="button"
                class="text-teal-600 hover:underline"
                @click="selectNumber(item)"
              >
                선택
              </button>
              <button
                v-else
                type="button"
                class="text-gray-500 hover:underline"
                @click="selectedNumber = null"
              >
                선택취소
              </button>
            </div>
          </li>
        </ul>
        <p v-else-if="searched && !resultList.length" class="text-sm text-gray-500 py-2">조회 결과가 없습니다.</p>
      </div>
      <DialogFooter class="mt-4 gap-2 sm:gap-0">
        <DialogClose as-child>
          <Button variant="outline">취소</Button>
        </DialogClose>
        <Button :disabled="!selectedNumber" @click="confirm">확인</Button>
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

const prefixDisplay = ref('010-****-')
const suffix = ref('')
const resultList = ref([])
const selectedNumber = ref(null)
const searchError = ref('')
const searched = ref(false)
const remainCount = ref(50)

function search() {
  searchError.value = ''
  if (suffix.value.length !== 4) {
    searchError.value = '뒤 4자리를 입력하세요.'
    return
  }
  if (!/^\d{4}$/.test(suffix.value)) {
    searchError.value = '숫자 4자리만 입력 가능합니다.'
    return
  }
  // TODO: 실제 번호조회 API 연동 (X32 등), remainCount 서버 연동
  const mock = ['010-1234-' + suffix.value, '010-5678-' + suffix.value]
  resultList.value = mock
  searched.value = true
  if (remainCount.value > 0) remainCount.value -= 1
  if (remainCount.value <= 0) searchError.value = '신규 번호 조회 가능 횟수를 초과 하셨습니다. 신청서를 재작성 해주세요.'
}

function selectNumber(num) {
  selectedNumber.value = selectedNumber.value === num ? null : num
}

function confirm() {
  if (!selectedNumber.value) return
  emit('confirm', { number: selectedNumber.value })
  isOpen.value = false
}

watch(isOpen, (v) => {
  if (!v) {
    suffix.value = ''
    resultList.value = []
    selectedNumber.value = null
    searchError.value = ''
    searched.value = false
  }
})
</script>
