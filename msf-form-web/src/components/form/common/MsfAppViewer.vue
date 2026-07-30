<!-- 작성완료 : 신청서 열람 박스영역 -->
<template>
  <div>
    <MsfBox variant="outline" margin="0" class="application-box">
      <div class="box-title">
        <img src="@/assets/images/applyopenIcon.svg" alt="신청서 열람 아이콘" />
        <em class="tit">신청서 열람</em>
      </div>
      <MsfButton variant="subtle" @click="onClikViewBtn">신청서 열람</MsfButton>
    </MsfBox>

    <!-- 비밀번호 확인 모달 -->
    <MsfPasswordInputModal
      v-model="isModalOpen"
      :form-type="props.formType"
      :request-key="props.formKey"
      :document-id="documentIds"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { computed } from 'vue'

const props = defineProps({
  formKey: { type: String, required: true },
  formType: { type: String, required: true },
  documentId: {
    type: [String, Array],
    required: true,
  },
})

const documentIds = computed(() =>
  Array.isArray(props.documentId) ? props.documentId : [props.documentId],
)

const isModalOpen = ref(false)

const onClikViewBtn = () => {
  isModalOpen.value = true
}
</script>

<style lang="scss" scoped>
.application-box {
  .box-title {
    @include flex($d: column, $v: center) {
      gap: rem(8px);
    }
    .tit {
      font-size: var(--font-size-16);
      font-weight: var(--font-weight-medium);
      color: var(--color-gray-600);
    }
  }
}
</style>
