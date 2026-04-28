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
              v-model="formData.field"
              :options="[
                { label: '전체', value: '' },
                { label: '유형1', value: 'field2' },
                { label: '유형2', value: 'field3' },
              ]"
              placeholder="전체"
              class="ut-w-140"
            />
          </td>
        </tr>
        <tr>
          <th><label for="inp-title" class="label-txt required">제목</label></th>
          <td>
            <MsfInput id="inp-title" v-model="formData.title" placeholder="제목 입력" />
          </td>
        </tr>
        <tr>
          <th><label for="inp-content" class="label-txt required">문의내용</label></th>
          <td>
            <MsfTextarea id="inp-content" v-model="formData.content" placeholder="문의내용 입력" />
          </td>
        </tr>
        <tr>
          <th><span class="label-txt required">공개여부</span></th>
          <td>
            <MsfSelect
              title="선택"
              v-model="formData.openStatus"
              :options="[
                { label: '공개여부1', value: 'openStatus1' },
                { label: '공개여부2', value: 'openStatus2' },
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
        <MsfButton variant="secondary">취소</MsfButton>
        <MsfButton variant="primary">등록</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { reactive } from 'vue'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 퍼블샘플용
const formData = reactive({
  field: '', //유형
  title: '', //제목
  content: '', //문의내용
  openStatus: 'openStatus1', //공개여부
})
</script>

<style lang="scss" scoped></style>
