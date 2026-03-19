<template>
  <li class="msf-tree-node">
    <div class="msf-tree-node-content" :class="{ 'is-folder': isFolder }" @click="onClickTreeNode">
      <i v-if="isFolder" :class="['fa-solid', isOpen ? 'fa-angle-down' : 'fa-angle-right']"> </i>
      <label class="checkbox-label">
        <input
          :type="mode === 'single' ? 'radio' : 'checkbox'"
          :checked="isChecked"
          :name="mode === 'single' ? 'tree-radio-group' : undefined"
          @change="onCheckboxChange"
        />
        <span class="msf-tree-node-name">{{ node.name }}</span>
      </label>
    </div>

    <ul v-show="isOpen" v-if="isFolder" class="msf-tree-children-list">
      <MsfTreeNode
        v-for="child in node.children"
        :key="child"
        :node="child"
        :default-expanded="defaultExpanded"
      />
    </ul>
  </li>
</template>

<script setup>
import { ref, computed, watch, inject } from 'vue'

const props = defineProps({
  // 트리 하나하나의 데이터를 담은 객체입니다.
  node: {
    type: Object,
    required: true,
  },
  // 💡 추가됨: 부모 컴포넌트에서 전체 트리의 기본 열림/닫힘 상태를 제어하는 속성
  defaultExpanded: {
    type: Boolean,
    default: false, // 아무것도 안 넘기면 기본적으로 닫혀 있도록 설정
  },
})

// 💡 TreeRoot에서 제공한 데이터와 함수를 주입(inject) 받습니다.
const checkedIds = inject('checkedIds')
const toggleCheck = inject('toggleCheck')
// 💡 TreeRoot에서 제공한 현재 선택 모드를 주입받습니다.
const mode = inject('mode')

// 현재 노드가 체크된 상태인지 확인 (체크박스 v-bind용)
const isChecked = computed(() => checkedIds.value.includes(props.node.id))

// 체크박스 상태 변경 이벤트 핸들러
const onCheckboxChange = (event) => {
  // TreeRoot의 toggleCheck 함수를 호출하여 전역 상태 업데이트
  toggleCheck(props.node, event.target.checked)
}

// 💡 초기 열림 상태 결정 로직:
// 1. 개별 노드 데이터(JSON)에 명시된 'expanded' 값이 있다면 최우선으로 적용합니다.
// 2. 개별 값이 없다면 부모가 넘겨준 'defaultExpanded' 속성을 따릅니다.
const isOpen = ref(props.node.expanded !== undefined ? props.node.expanded : props.defaultExpanded)

// 하위 자식(children)이 있는지 확인하여 폴더인지 일반 파일인지 구분합니다.
const isFolder = computed(() => {
  return props.node.children && props.node.children.length > 0
})

// 클릭 시 열림/닫힘 상태를 전환합니다.
const onClickTreeNode = () => {
  if (isFolder.value) {
    isOpen.value = !isOpen.value
  }
}

watch(
  () => props.node.expanded,
  (newVal) => {
    if (newVal !== undefined) isOpen.value = newVal
  },
)
watch(
  () => props.defaultExpanded,
  (newVal) => {
    if (props.node.expanded === undefined) isOpen.value = newVal
  },
)
</script>

<style scoped>
@reference "tailwindcss";

/* 트리의 기본 리스트 스타일 제거 */
.msf-tree-children-list {
  @apply list-none pl-5 m-0 border-l border-l-gray-200 ml-2;
}

.msf-tree-node {
  @apply my-1 mx-0;
}

.msf-tree-node-content {
  @apply flex items-center p-1.5 rounded-sm cursor-default transition-colors;
}

/* 하위 항목이 있는 폴더일 경우 마우스를 올리면 클릭 가능함을 표시 */
.msf-tree-node-content.is-folder {
  @apply cursor-pointer;
}

.msf-tree-node-content.is-folder:hover {
  @apply bg-neutral-50;
}

.msf-tree-node-name {
  @apply text-sm text-slate-700;
}
</style>
