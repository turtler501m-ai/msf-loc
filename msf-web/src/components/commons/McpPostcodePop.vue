<template>
  <Dialog v-model:open="isOpen" :modal="true" :close-on-click-away="true" :close-on-esc="true">
    <DialogContent class="max-w-md">
      <DialogHeader>
        <DialogTitle>우편번호 검색</DialogTitle>
        <DialogDescription>주소를 검색한 후 선택하세요.</DialogDescription>
      </DialogHeader>
      <div class="grid gap-4">
        <div class="flex gap-2">
          <input
            v-model="keyword"
            type="text"
            placeholder="도로명, 지번, 건물명"
            class="flex-1 rounded border border-gray-300 px-3 py-2"
            @keydown.enter="search"
          />
          <button type="button" class="px-4 py-2 rounded bg-teal-600 text-white" @click="search">
            검색
          </button>
        </div>
        <p v-if="message" class="text-sm text-gray-500">{{ message }}</p>
        <ul v-if="results.length" class="max-h-48 overflow-y-auto border border-gray-200 rounded divide-y">
          <li
            v-for="item in results"
            :key="item.zipcode + item.address"
            class="p-3 cursor-pointer hover:bg-teal-50 text-sm"
            @click="select(item)"
          >
            <span class="font-mono text-teal-600">{{ item.zipcode }}</span>
            {{ item.address }}
          </li>
        </ul>
        <p v-else-if="searched && !results.length" class="text-sm text-gray-500">검색 결과가 없습니다.</p>
      </div>
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

const isOpen = defineModel('open', { type: Boolean, default: false })

const emit = defineEmits(['select'])

const keyword = ref('')
const results = ref([])
const message = ref('')
const searched = ref(false)

// TODO: 실제 우편번호 API 연동 (예: Daum 우편번호, 공공 API 등)
function search() {
  if (!keyword.value.trim()) {
    message.value = '검색어를 입력하세요.'
    return
  }
  message.value = '실제 API 연동 후 검색 결과가 표시됩니다.'
  results.value = []
  searched.value = true
}

function select(item) {
  emit('select', { post: item.zipcode, address: item.address })
  isOpen.value = false
  keyword.value = ''
  results.value = []
  searched.value = false
}

watch(isOpen, (v) => {
  if (!v) {
    keyword.value = ''
    results.value = []
    message.value = ''
    searched.value = false
  }
})
</script>

<style scoped></style>
