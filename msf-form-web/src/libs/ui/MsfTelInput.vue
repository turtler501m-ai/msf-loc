<template>
  <!-- 전화번호 -->
  <MsfNumberInput
    v-model="telNo1"
    placeholder="지역번호"
    :ariaLabel="`${cleanLabel} 지역번호`"
    maxlength="3"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    v-model="telNo2"
    id="inp-telNo2"
    placeholder="가운데 3 또는 4자리"
    :ariaLabel="`${cleanLabel} 가운데 3 또는 4자리`"
    maxlength="4"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    v-model="telNo3"
    id="inp-telNo3"
    :type="secure ? 'password' : 'text'"
    placeholder="뒤 4자리"
    :ariaLabel="`${cleanLabel} 뒤 4자리`"
    maxlength="4"
  />
</template>

<script setup>
import { computed, watch } from 'vue'
import { validateTel } from '@/libs/utils/string.utils'

// 네이티브 속성(readonly, disabled, maxlength 등)을
// 최상위 태그가 아닌 input으로 깔끔하게 넘겨줍니다.
defineOptions({
  inheritAttrs: false,
})

// v-model 선언
const telNo1 = defineModel('telNo1', { default: '' })
const telNo2 = defineModel('telNo2', { default: '' })
const telNo3 = defineModel('telNo3', { default: '' })

const emit = defineEmits(['verify'])

// props 정의
const props = defineProps({
  secure: { type: Boolean, default: false },
  label: { type: String, default: '전화번호' },
})

// 모든 종류의 줄바꿈과 <br>, <br/> 태그를 빈 문자열로 교체
const cleanLabel = computed(() => {
  if (!props.label) return ''
  return props.label.replace(/(\r\n|\n|\r|<br\s*\/?>)/gm, '')
})

watch(
  () => telNo1.value,
  (newVal) => {
    emit('verify', validateTel(newVal + '-' + telNo2.value + '-' + telNo3.value))
  },
)
watch(
  () => telNo2.value,
  (newVal) => {
    emit('verify', validateTel(telNo1.value + '-' + newVal + '-' + telNo3.value))
  },
)
watch(
  () => telNo3.value,
  (newVal) => {
    emit('verify', validateTel(telNo1.value + '-' + telNo2.value + '-' + newVal))
  },
)
</script>
