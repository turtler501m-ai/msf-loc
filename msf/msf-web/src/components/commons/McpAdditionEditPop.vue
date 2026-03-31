<template>
  <Dialog v-model:open="isOpen" :modal="true" :close-on-click-away="false">
    <DialogContent class="max-w-lg" :show-close-button="true">
      <DialogHeader>
        <DialogTitle>부가서비스 추가/삭제</DialogTitle>
        <DialogDescription>가입할 부가를 선택하거나 해지할 부가를 선택하세요.</DialogDescription>
      </DialogHeader>
      <div class="grid gap-4">
        <p class="text-xs text-gray-500">API 연동 후 현재 가입 부가(X20) 및 가입 가능 부가 목록이 표시됩니다.</p>
        <!-- 현재 가입 (해지 대상) -->
        <div v-if="currentAdditions.length">
          <h4 class="text-sm font-medium mb-2">현재 가입 중 (해지 시 체크 해제)</h4>
          <ul class="border border-gray-200 rounded divide-y">
            <li
              v-for="(item, idx) in currentAdditions"
              :key="'cur-' + idx"
              class="flex items-center gap-3 px-3 py-2"
            >
              <input
                v-model="item.checked"
                type="checkbox"
                :disabled="item.noCancel"
                class="rounded border-gray-300"
              />
              <span class="flex-1 text-sm">{{ item.name }}</span>
              <span v-if="item.fee" class="text-xs text-gray-500">{{ item.fee }}</span>
            </li>
          </ul>
        </div>
        <!-- 가입 가능 (추가 대상) -->
        <div v-if="availableAdditions.length">
          <h4 class="text-sm font-medium mb-2">가입 가능 (추가 시 체크)</h4>
          <ul class="border border-gray-200 rounded divide-y">
            <li
              v-for="(item, idx) in availableAdditions"
              :key="'avail-' + idx"
              class="flex items-center gap-3 px-3 py-2"
            >
              <input
                v-model="item.checked"
                type="checkbox"
                class="rounded border-gray-300"
              />
              <span class="flex-1 text-sm">{{ item.name }}</span>
              <span v-if="item.fee" class="text-xs text-gray-500">{{ item.fee }}</span>
              <button
                v-if="item.hasOption"
                type="button"
                class="text-teal-600 text-xs"
                @click="openOption(item)"
              >
                설정
              </button>
            </li>
          </ul>
        </div>
        <p v-if="!currentAdditions.length && !availableAdditions.length" class="text-sm text-gray-500 py-4 text-center">
          목록을 불러오는 중이거나 표시할 부가가 없습니다.
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

const props = defineProps({
  /**
   * X20 조회 결과: 현재 가입 중인 부가서비스 목록.
   * 형식: [{ id, name, fee, checked, noCancel, hasOption, soc }]
   */
  currentItems: { type: Array, default: null },
  /**
   * 가입 가능한 부가서비스 목록.
   * null이면 기본 목록(불법TM·번호도용·로밍) 표시.
   */
  availableItems: { type: Array, default: null },
})

const isOpen = defineModel('open', { type: Boolean, default: false })
const emit = defineEmits(['confirm', 'open-option'])

const DEFAULT_AVAILABLE = [
  { id: 'tm_block', soc: 'NOSPAM4', name: '불법TM수신차단', fee: '무료', checked: false, hasOption: true },
  { id: 'num_theft', soc: 'STLPVTPHN', name: '번호도용 차단 서비스', fee: '무료', checked: false, hasOption: true },
  { id: 'roaming', soc: 'PL2078760', name: '(신)로밍 하루종일 ON', fee: '일당 과금', checked: false, hasOption: true },
]

const currentAdditions = ref([])
const availableAdditions = ref([])

function openOption(item) {
  emit('open-option', { item })
}

function confirm() {
  const toCancel = currentAdditions.value.filter((a) => !a.checked).map((a) => a.id)
  const toAdd = availableAdditions.value.filter((a) => a.checked).map((a) => a.id)
  emit('confirm', { toAdd, toCancel })
  isOpen.value = false
}

watch(isOpen, (v) => {
  if (v) {
    // props로 전달된 실제 X20 데이터 사용, 없으면 기본값
    currentAdditions.value = props.currentItems
      ? props.currentItems.map((it) => ({ ...it, checked: it.checked !== false }))
      : []
    availableAdditions.value = props.availableItems
      ? props.availableItems.map((it) => ({ ...it, checked: false }))
      : DEFAULT_AVAILABLE.map((it) => ({ ...it, checked: false }))
  }
})
</script>
