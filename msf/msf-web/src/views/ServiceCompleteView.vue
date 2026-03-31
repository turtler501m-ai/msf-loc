<template>
  <div class="complete-view p-6 max-w-lg mx-auto">
    <div class="text-center py-6">
      <div class="text-6xl text-teal-500 mb-4">✓</div>
      <h2 class="text-xl font-semibold mb-2">{{ title }}</h2>
      <p class="text-gray-600 mb-6">{{ message }}</p>
      <p v-if="applicationNo" class="text-sm font-medium text-gray-700 mb-2">
        접수번호: <span class="text-teal-600">{{ applicationNo }}</span>
      </p>

      <!-- 서비스변경/명의변경/서비스해지 신청완료: 신청서 열람·발송·앱안내발송 -->
      <template v-if="hasSendUI">
        <div class="text-left space-y-4 mb-6 p-4 bg-gray-50 rounded-lg">
          <p class="text-sm text-gray-600">고객님께 신청서를 발송해 주세요.</p>

          <!-- B1 열람 -->
          <div>
            <button type="button" class="text-teal-600 underline font-medium" @click="openPreview">
              신청서 열람
            </button>
            <span class="text-sm text-gray-500 ml-1">(팝업 연동 예정)</span>
          </div>

          <!-- B2 신청서 발송 (포시에스 신청서 교부 API) -->
          <div class="space-y-1 text-sm">
            <div class="flex flex-wrap items-center gap-2">
              <input
                v-model="sendPhone"
                type="tel"
                placeholder="휴대폰번호 '-' 없이 입력"
                class="flex-1 min-w-[160px] rounded border px-2 py-1.5"
              />
              <button type="button" class="px-4 py-1.5 rounded bg-teal-600 text-white text-sm" @click="sendForm">
                발송
              </button>
            </div>
            <p class="text-xs text-gray-500">
              ※ 개통한 전화번호와 가입자 연락처 휴대폰번호로 동시에 발송됩니다.<br />
              ※ 고객님께서 신청서 출력을 원하실 경우 관리자 사이트에서 출력하여 제공해 주시기 바랍니다.
            </p>
          </div>

          <!-- B3 kt M모바일 앱 안내 발송 -->
          <div class="pt-3 border-t border-gray-200 space-y-2 text-sm">
            <p class="text-gray-700">kt M모바일에서 제공하는 서비스를 이용할 수 있도록 고객에게 안내 발송해 주세요.<br />
              신규가입에서 부가서비스의 신청/변경, 실시간 사용량 조회까지 다양한 서비스를 이용하실 수 있습니다.</p>
            <div class="flex flex-wrap items-center gap-2">
              <input
                v-model="appGuidePhone"
                type="tel"
                placeholder="휴대폰번호 '-' 없이 입력"
                class="flex-1 min-w-[160px] rounded border px-2 py-1.5"
              />
              <button type="button" class="px-4 py-1.5 rounded bg-gray-200 text-sm" @click="sendAppGuide">
                발송
              </button>
            </div>
          </div>
        </div>
      </template>

      <router-link
        to="/"
        class="inline-block px-6 py-3 rounded-lg bg-teal-600 text-white font-medium"
        @click="onGoHome"
      >
        홈으로
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRoute } from 'vue-router'
import { useServiceChangeFormStore } from '@/stores/service_change_form'
import { useIdentFormStore } from '@/stores/ident_form'
import { useCancelFormStore } from '@/stores/cancel_form'

const route = useRoute()
const domain = route.params.domain || ''
const serviceChangeStore = useServiceChangeFormStore()
const identStore = useIdentFormStore()
const cancelStore = useCancelFormStore()

const sendPhone = ref(domain === 'cancel' ? (cancelStore.lastContactPhone || '') : '')
const appGuidePhone = ref('')

// 고객명 마스킹 (홍길*)
function maskName(name) {
  if (!name || typeof name !== 'string') return '고객'
  const t = name.trim()
  if (t.length <= 1) return t + '*'
  return t.slice(0, 1) + '*'.repeat(Math.min(t.length - 1, 2))
}

function getCompletedName() {
  if (domain === 'change') return serviceChangeStore.lastCompletedCustomerName
  if (domain === 'ident') return identStore.lastCompletedCustomerName
  if (domain === 'cancel') return cancelStore.lastCompletedCustomerName
  return ''
}

const maskedName = computed(() => maskName(getCompletedName()))

const titles = {
  change: '서비스변경 신청이 완료되었습니다.',
  ident: '명의변경 신청이 완료되었습니다.',
  cancel: '서비스해지 신청이 완료되었습니다.',
}

const messages = {
  change: computed(() =>
    maskedName.value
      ? `${maskedName.value} 님이 작성하신 신청서가 작성 완료되었습니다.`
      : '신청서가 작성 완료되었습니다.'
  ),
  ident: computed(() =>
    maskedName.value
      ? `${maskedName.value} 님이 작성하신 신청서가 작성 완료되었습니다.`
      : '신청서가 작성 완료되었습니다.'
  ),
  cancel: computed(() =>
    maskedName.value
      ? `${maskedName.value} 님이 작성하신 신청서가 작성 완료되었습니다.`
      : '신청서가 작성 완료되었습니다.'
  ),
}

const title = computed(() => titles[domain] || '신청이 완료되었습니다.')
const message = computed(() => {
  if (domain === 'change') return messages.change.value
  if (domain === 'ident') return messages.ident.value
  if (domain === 'cancel') return messages.cancel.value
  return '신청서가 작성 완료되었습니다.'
})

const hasSendUI = computed(() => ['change', 'ident', 'cancel'].includes(domain))

const applicationNo = computed(() => {
  if (domain === 'change') return serviceChangeStore.lastApplicationNo
  if (domain === 'ident') return identStore.lastApplicationNo
  if (domain === 'cancel') return cancelStore.lastApplicationNo
  return ''
})

function openPreview() {
  alert('신청서 열람 팝업은 이미징 연동 후 제공됩니다.')
}

function sendForm() {
  if (!sendPhone.value.trim()) {
    alert('휴대폰번호를 입력해 주세요.')
    return
  }
  alert('신청서 발송(포시에스 신청서 교부 API 연동 예정): ' + sendPhone.value.replace(/-/g, ''))
}

function sendAppGuide() {
  if (!appGuidePhone.value.trim()) {
    alert('휴대폰번호를 입력해 주세요.')
    return
  }
  alert('kt M모바일 앱 안내 발송(M모바일 SMS 발송 모듈 연동 예정): ' + appGuidePhone.value.replace(/-/g, ''))
}

function onGoHome() {
  if (domain === 'change') {
    serviceChangeStore.clearLastCompletedName()
    serviceChangeStore.clearLastApplicationNo()
  }
  if (domain === 'ident') identStore.clearLastCompletedName()
  if (domain === 'cancel') {
    cancelStore.clearLastCompletedName()
    cancelStore.clearLastApplicationNo()
    cancelStore.clearLastContactPhone()
  }
}
</script>

<style scoped></style>
