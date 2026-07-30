<template>
  <MsfDialog
    :is-open="modelValue"
    :title="props.title"
    :maximize="props.maximize"
    show-close
    @open="openDialog"
    @close="emit('update:modelValue', false)"
  >
    <div class="terms-content">
      <template v-if="Array.isArray(displayContent)">
        <ul class="text-list">
          <li v-for="(text, i) in displayContent" :key="i" v-html="text"></li>
        </ul>
      </template>
      <template v-else>
        <div class="plain-text" v-html="displayContent"></div>
      </template>
    </div>
    <template #footer>
      <MsfButtonGroup align="center">
        <MsfButton variant="primary" class="confirm-btn" @click="onClickConfirmBtn">
          동의 후 닫기
        </MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref, watch, computed } from 'vue'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  modelValue: Boolean,
  title: String,
  groupCode: String,
  code: String,
  id1: String,
  id2: String,
  version: [String, Array],
  specTerms: Object,
  content: [String, Array],
  maximize: { type: Boolean, default: false }, // 화면 꽉채우는 팝업으로 설정시 사용
})
const emit = defineEmits(['update:modelValue', 'confirm'])

const showDialog = ref(false)
const fetchedContent = ref('')

const displayContent = computed(() => props.content || fetchedContent.value)

const openDialog = () => {
  if (showDialog.value && props.id1 && props.id2 && !props.content) {
    post('/api/shared/form/common/terms/content', {
      groupCode: props.groupCode,
      code: props.code,
      contentGroup: props.id1,
      contentCode: props.id2,
      version: Array.isArray(props.version)
        ? props.version[props.version.length - 1]
        : props.version,
      specTermsList: [
        {
          code: props.code,
          specType: props.specTerms?.specType,
          specCode: props.specTerms?.specCode,
          specName: props.specTerms?.specName,
        },
      ],
    })
      .then((res) => {
        if (res.code !== '0000') {
          return false
        }
        fetchedContent.value = res.data.content
      })
      .catch((err) => {
        console.log('err:', err)
      })
  }
}

const onClickConfirmBtn = () => {
  emit('update:modelValue', false)
  emit('confirm', true)
}

watch(
  () => props.modelValue,
  (newVal) => {
    showDialog.value = newVal
  },
  { immediate: true },
)
</script>

<style lang="scss" scoped></style>
