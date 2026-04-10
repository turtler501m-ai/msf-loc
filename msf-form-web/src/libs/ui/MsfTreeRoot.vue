<template>
  <ul class="msf-tree-root" v-bind="$attrs">
    <MsfTreeNode
      v-for="node in props.data"
      :key="node"
      :node="node"
      :default-expanded="props.defaultExpanded"
    />
  </ul>
</template>

<script setup>
import { computed, provide } from 'vue'

const props = defineProps({
  data: Array,
  modelValue: {
    type: Array,
    default: () => [],
  },
  defaultExpanded: {
    type: Boolean,
    default: false,
  },
  mode: {
    type: String,
    default: 'single',
    validator: (value) => ['single', 'multi'].includes(value),
  },
})

const emit = defineEmits(['update:modelValue'])

// 현재 체크된 노드들의 ID 목록을 빠르게 확인하기 위한 계산된 속성
const checkedIds = computed(() => props.modelValue.map((node) => node.id))

// 체크박스가 클릭되었을 때 실행될 함수 (provide를 통해 하위로 전달됨)
const toggleCheck = (node, isChecked) => {
  // 💡 단일 선택 모드일 때
  if (props.mode === 'single') {
    if (isChecked) {
      // 기존 배열을 무시하고, 방금 선택한 단 하나의 노드만 배열에 담아 보냅니다.
      emit('update:modelValue', [node])
    } else {
      // 선택 해제 시 빈 배열 전달
      emit('update:modelValue', [])
    }
  } else {
    let newSelection = [...props.modelValue]

    if (isChecked) {
      // 체크됨: 배열에 추가
      newSelection.push(node)
    } else {
      // 체크 해제됨: 배열에서 제거
      newSelection = newSelection.filter((item) => item.id !== node.id)
    }

    // 부모 컴포넌트로 업데이트된 배열 전달
    emit('update:modelValue', newSelection)
  }
}

// 💡 하위의 모든 TreeItem 컴포넌트들이 props 전달 없이 바로 쓸 수 있도록 공급(provide)합니다.
const currentMode = computed(() => props.mode)
provide('mode', currentMode)
provide('checkedIds', checkedIds)
provide('toggleCheck', toggleCheck)

// 💡 트리 구조를 1차원 배열로 평탄화하여 모든 노드를 추출하는 재귀 헬퍼 함수
const getAllNodes = (nodes) => {
  let result = []
  for (const node of nodes) {
    result.push(node)
    if (node.children && node.children.length > 0) {
      result = result.concat(getAllNodes(node.children))
    }
  }
  return result
}

// 💡 전체 선택 함수: 모든 노드를 찾아 부모(v-model)로 전달합니다.
const selectAll = () => {
  if (props.mode === 'single') {
    console.warn('단일 선택 모드에서는 전체 선택을 사용할 수 없습니다.')
    return
  }
  emit('update:modelValue', getAllNodes(props.data))
}

// 💡 전체 해제 함수: 배열을 비워버립니다.
const deselectAll = () => {
  emit('update:modelValue', [])
}

// 💡 외부(부모 컴포넌트)에서 이 함수들을 호출할 수 있도록 노출시킵니다.
defineExpose({
  selectAll,
  deselectAll,
})
</script>

<style scoped>
/* @reference "tailwindcss";

.msf-tree-root {
  @apply border-gray-200 p-1 bg-white overflow-auto;
} */
</style>
