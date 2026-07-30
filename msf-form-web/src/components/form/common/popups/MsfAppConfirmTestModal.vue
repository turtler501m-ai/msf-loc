<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신청서 확인"
    size="xlarge"
    @open="onOpen"
    @close="onClose"
  >
    <div class="eformsign-flex-layout">
      <MsfLoadingComp :isOpen="isLoading" />
      <div class="eformsign-container">
        <MsfEformImgTest
          ref="eformImgRef"
          class="eform-frame"
          :form-type-code="props.formTypeCode"
          :request-key="props.requestKey"
          :form-parameters="props.formParameters"
          :use-new-change-template="props.useNewChangeTemplate"
          :device-os="props.deviceOs"
          :saved-file-data="savedFileData"
          :record-file-data="recordFileData"
        />
      </div>
    </div>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'
import { MsfDialog } from '@/libs/ui/index.js'

const props = defineProps({
  modelValue: Boolean,
  formTypeCode: {
    type: String,
    default: '',
  },
  requestKey: {
    type: String,
    default: '',
  },
  formParameters: {
    type: Array,
    default: () => [],
  },
  useNewChangeTemplate: {
    type: Boolean,
    default: false,
  },
  deviceOs: {
    type: String,
    default: '',
  },
  savedFileData: {
    type: Array,
    default: () => [],
  },
  useTest: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits([
  'update:modelValue',
  'open',
  'close',
  'confirm',
  'edit',
  'save-complete',
  'save-fail',
])

const eformImgRef = ref(null)
const recordFileData = ref(null)
const isLoading = ref(false)

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onOpen = async () => {
  emit('open')
}
</script>

<style lang="scss" scoped></style>
