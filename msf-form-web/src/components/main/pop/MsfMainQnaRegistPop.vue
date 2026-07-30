<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="Q&A 등록"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfTable>
      <template #colgroup>
        <col style="width: 160px" />
        <col />
      </template>
      <template #tbody>
        <tr>
          <th><span class="label-txt required">유형</span></th>
          <td>
            <MsfSelect
              title="선택"
              v-model="formData.category"
              group-code="QNA_CTG_CD"
              placeholder="유형"
            />
          </td>
        </tr>
        <tr>
          <th><label for="inp-title" class="label-txt required">제목</label></th>
          <td>
            <MsfInput
              id="inp-title"
              v-model="formData.title"
              maxlength="60"
              placeholder="제목 입력"
            />
          </td>
        </tr>
        <tr>
          <th><label for="inp-content" class="label-txt required">문의내용</label></th>
          <td>
            <MsfTextarea id="inp-content" v-model="formData.contents" placeholder="문의내용 입력" />
          </td>
        </tr>
        <tr>
          <th><span class="label-txt required">공개여부</span></th>
          <td>
            <MsfSelect
              title="선택"
              v-model="formData.publicStatus"
              :options="[
                { label: '공개', value: 'Y' },
                { label: '비공개', value: 'N' },
              ]"
              placeholder="공개여부"
              class="ut-w-140"
            />
          </td>
        </tr>
      </template>
    </MsfTable>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onClickRegist">등록</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { onBeforeUnmount, reactive, watch } from 'vue'
import { post } from '@/libs/api/msf.api'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import { isEmpty } from '@/libs/utils/string.utils'
import { useMsfNetworkStore } from '@/stores/msf_network'

const NETWORK_CHECK_SCOPE = 'qna-regist'
const networkStore = useMsfNetworkStore()

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

const syncNetworkMonitoring = (isOpen) => {
  if (isOpen) {
    networkStore.startMonitoring(NETWORK_CHECK_SCOPE)
    return
  }

  networkStore.stopMonitoring({ scope: NETWORK_CHECK_SCOPE, resetStatus: true })
}

const closePopup = (result) => {
  if (props.modelValue) {
    Object.assign(formData, {
      category: '', //유형
      title: '', //제목
      contents: '', //문의내용
      publicStatus: 'Y', //공개여부
    })
    emit('update:modelValue', false)
    emit('close', result)
  }
}

// 닫힘 이벤트
const onClose = () => {
  closePopup(false)
}

// 퍼블샘플용
const formData = reactive({
  category: '', //유형
  title: '', //제목
  contents: '', //문의내용
  publicStatus: 'Y', //공개여부
})

const onClickRegist = () => {
  if (isEmpty(formData.category)) {
    showAlert('유형을 선택하세요.')
    return false
  }
  if (isEmpty(formData.title)) {
    showAlert('제목을 입력하세요.')
    return false
  }
  if (isEmpty(formData.contents)) {
    showAlert('문의내용을 입력하세요.')
    return false
  }
  if (isEmpty(formData.publicStatus)) {
    showAlert('공개여부를 선택하세요.')
    return false
  }
  showConfirm('Q&A를 등록하시겠습니까?', async () => {
    const result = await post('/api/main/qna/regist', formData, { skipAlert: true })
    if (result.code !== '0000') {
      showAlert('Q&A 등록이 실패하였습니다.\n다시 시도해 주세요.')
      return false
    }
    showAlert('Q&A 등록이 완료되었습니다.', () => {
      closePopup(true)
    })
  })
}

watch(
  () => props.modelValue,
  (isOpen) => {
    syncNetworkMonitoring(isOpen)
  },
  { immediate: true },
)

onBeforeUnmount(() => {
  networkStore.stopMonitoring({ scope: NETWORK_CHECK_SCOPE, resetStatus: true })
})
</script>

<style lang="scss" scoped></style>
