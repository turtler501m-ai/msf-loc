<template>
  <!-- DOM에 팝업 상태가 유지되도록 keepAlive 설정 추가 -->
  <MsfDialog
      v-bind="$attrs"
      :is-open="modelValue"
      title="신청서 확인"
      @open="onOpen"
      @close="onClose"
      keepAlive
  >
    <!-- 팝업 내용 -->
    <MsfCollapse defaultOpen>
      <template #header>안내 녹취</template>

      <MsfButtonGroup>
        <MsfButton variant="toggle">녹취</MsfButton>
        <MsfButton variant="toggle">녹취중</MsfButton>
        <MsfButton variant="toggle" active>녹취완료</MsfButton>
        <MsfButton variant="subtle">재생</MsfButton>
      </MsfButtonGroup>

      <MsfTextarea
          placeholder="안내 녹취 스크립트"
          class="ut-mt-8"
      />
    </MsfCollapse>

    <MsfTitleArea title="신청서" level="2" noline />

    <MsfFormEformsignViewer
        v-if="templateOption"
        v-model="showViewer"
        :options="templateOption"
        class="img-area"
        style="height: 468px"
    />

    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary">수정</MsfButton>
        <MsfButton variant="primary" @click="onClose">
          확인
        </MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { onBeforeUnmount, ref, watch } from 'vue'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits([
  'update:modelValue',
  'open',
  'close',
])

const showViewer = ref(false)
const templateOption = ref(null)

/**
 * 팝업 open 상태 동기화
 */
watch(
    () => props.modelValue,
    (val) => {
      showViewer.value = val
    },
    { immediate: true },
)

/**
 * 팝업 열릴 때 template_option 생성
 */
const onOpen = async () => {
  emit('open')

  // TODO: 실제 API 연동 값으로 교체
  const accessToken = 'ACCESS_TOKEN'
  const refreshToken = 'REFRESH_TOKEN'
  const pdfBase64 = 'JVBERi0xLjQK...' // 신청서 PDF Base64

  templateOption.value = {
    company: {
      id: '76440d70fae242e09c4b0fac40b6a6be',
      country_code: 'kr',
      user_key: 'USER001',
    },

    user: {
      id: 'user@email.com',
      access_token: accessToken,
      refresh_token: refreshToken,
    },

    mode: {
      type: '01',
      template_type: 'unstructured_form',
    },

    layout: {
      lang_code: 'ko',
      header: true,
      footer: false,
    },

    prefill: {
      template_name: '신청서',
      fields: [],
    },

    template_file: {
      name: '신청서.pdf',
      mime: '@file/octet-stream',
      data: pdfBase64,
    },
  }
}

/**
 * 닫힘 이벤트
 */
const onClose = () => {
  templateOption.value = null

  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const getToken = async () => {
  const result = await post('/api/form/common/eform-api-token/get', selectedRequestKey.value)
}

onBeforeUnmount(() => {
  getToken()
})
</script>

<style scoped lang="scss">
.img-area {
  background-color: var(--color-gray-75);
}
</style>