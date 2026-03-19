<template>
  <div class="msf-radio-buttons-wrapper">
    <label
      v-for="v in props.values"
      :key="v.code"
      :aria-label="v.name"
      class="group msf-radio-buttons-label"
    >
      <input
        v-bind="$attrs"
        type="radio"
        :name="`${props.name}-radio`"
        :value="v.code"
        :disabled="!props.disabled && v.disabled"
        :checked="v.code === active"
        class="msf-radio-buttons-input"
        @change="onChangeRadioInput"
      />
      <span class="msf-radio-buttons-name">
        {{ v.name }}
      </span>
    </label>
  </div>
</template>

<script setup>
// 네이티브 속성(readonly, disabled, maxlength 등)을
// 최상위 태그가 아닌 input으로 깔끔하게 넘겨줍니다.
defineOptions({
  inheritAttrs: false,
})

const active = defineModel('active', { default: '' })

const props = defineProps({
  values: Array,
  radioName: {
    type: String,
    default: 'msf-radio-name',
  },
  disabled: {
    type: Boolean,
    default: false,
  },
})

const onChangeRadioInput = (e) => {
  if (e.target.checked) {
    active.value = e.target.value
  }
}
</script>

<style scoped>
@reference "tailwindcss";

.msf-radio-buttons-wrapper {
  @apply w-full flex flex-nowrap gap-1;
}

.msf-radio-buttons-label {
  @apply flex-1 relative flex items-center justify-center rounded-md border border-gray-300 bg-white py-1.5 px-1 has-checked:border-indigo-600 has-checked:bg-indigo-600 has-focus-visible:outline-2 has-focus-visible:outline-offset-2 has-focus-visible:outline-indigo-600 has-disabled:border-gray-400 has-disabled:bg-gray-200 has-disabled:opacity-25;
}

.msf-radio-buttons-input {
  @apply absolute inset-0 appearance-none focus:outline-none disabled:cursor-not-allowed;
}

.msf-radio-buttons-name {
  @apply text-sm font-medium text-gray-900 uppercase group-has-checked:text-white;
}
</style>
