<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="이미지 미리보기"
    maximize
    @close="onClose"
  >
    <div class="preview-container" @click="onClose">
      <div class="image-wrapper" @click.stop>
        <img :src="imageUrl" alt="구비서류 미리보기" class="full-image" />
        <!-- <button type="button" class="close-btn" @click="onClose">
          <MsfIcon name="close" size="32" color="white" />
        </button> -->
      </div>
    </div>
  </MsfDialog>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  modelValue: Boolean,
  imageUrl: {
    type: String,
    default: '',
  },
})

const emit = defineEmits(['update:modelValue', 'close'])

const onClose = () => {
  emit('update:modelValue', false)
  emit('close')
}
</script>

<style lang="scss" scoped>
.preview-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  min-height: 100%;
  padding-block: rem(24px);
  background-color: rgba(0, 0, 0, 0.9);
  cursor: pointer;
}

.image-wrapper {
  position: relative;
  max-width: 90%;
  max-height: 90%;
  @include flex($h: center, $v: center);
}

.full-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
}

.close-btn {
  position: absolute;
  top: rem(-40px);
  right: rem(-10px);
  background: none;
  border: none;
  cursor: pointer;
  padding: rem(8px);
  z-index: 1;

  &:hover {
    opacity: 0.8;
  }
}

:deep(.msf-dialog__content) {
  padding: 0;
  overflow: hidden;
}
:deep(.msf-dialog__header) {
  display: none; // 전체화면 미리보기를 위해 헤더 숨김
}
</style>
