<template>
  <Dialog v-model:open="isOpen" :modal="true" :close-on-click-away="false">
    <DialogContent class="max-w-lg" :show-close-button="true">
      <DialogHeader>
        <DialogTitle>(신)로밍 하루종일 ON</DialogTitle>
        <DialogDescription>이용 기간을 선택하세요. (시작일 ~ 종료일, 1시간 단위)</DialogDescription>
      </DialogHeader>
      <div class="grid gap-4">
        <div>
          <label class="block text-sm font-medium mb-1">시작일시</label>
          <input
            v-model="startDate"
            type="date"
            :min="minStart"
            :max="maxStart"
            class="w-full rounded border border-gray-300 px-3 py-2"
          />
          <select v-model="startHour" class="mt-1 w-full rounded border border-gray-300 px-3 py-2">
            <option v-for="h in hourOptions" :key="h" :value="h">{{ h }}:00</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium mb-1">종료일시</label>
          <input
            v-model="endDate"
            type="date"
            :min="startDate"
            :max="maxEnd"
            class="w-full rounded border border-gray-300 px-3 py-2"
          />
          <select v-model="endHour" class="mt-1 w-full rounded border border-gray-300 px-3 py-2">
            <option v-for="h in hourOptions" :key="h" :value="h">{{ h }}:00</option>
          </select>
        </div>
        <p class="text-xs text-gray-500">시작일: 현재~2개월, 종료일: 현재~6개월</p>
        <p v-if="rangeError" class="text-sm text-red-600">{{ rangeError }}</p>
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
import { ref, computed, watch } from 'vue'
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

const hourOptions = Array.from({ length: 24 }, (_, i) => i)

function todayStr() {
  const d = new Date()
  return d.getFullYear() + '-' + String(d.getMonth() + 1).padStart(2, '0') + '-' + String(d.getDate()).padStart(2, '0')
}

const startDate = ref(todayStr())
const startHour = ref(0)
const endDate = ref(todayStr())
const endHour = ref(23)
const rangeError = ref('')

const minStart = computed(() => todayStr())
const maxStart = computed(() => {
  const d = new Date()
  d.setMonth(d.getMonth() + 2)
  return d.getFullYear() + '-' + String(d.getMonth() + 1).padStart(2, '0') + '-' + String(d.getDate()).padStart(2, '0')
})
const maxEnd = computed(() => {
  const d = new Date()
  d.setMonth(d.getMonth() + 6)
  return d.getFullYear() + '-' + String(d.getMonth() + 1).padStart(2, '0') + '-' + String(d.getDate()).padStart(2, '0')
})

function validateRange() {
  rangeError.value = ''
  const start = new Date(startDate.value + 'T' + String(startHour.value).padStart(2, '0') + ':00:00')
  const end = new Date(endDate.value + 'T' + String(endHour.value).padStart(2, '0') + ':00:00')
  if (end <= start) {
    rangeError.value = '종료일시는 시작일시보다 이후여야 합니다.'
    return false
  }
  return true
}

function confirm() {
  if (!validateRange()) return
  emit('confirm', {
    startDate: startDate.value,
    startHour: startHour.value,
    endDate: endDate.value,
    endHour: endHour.value,
  })
  isOpen.value = false
}

watch(isOpen, (v) => {
  if (v) {
    startDate.value = todayStr()
    endDate.value = todayStr()
    startHour.value = 0
    endHour.value = 23
    rangeError.value = ''
  }
})
</script>
