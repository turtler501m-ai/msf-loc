<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="번호도용 차단 서비스"
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
          <th scope="row">제공 받는 자</th>
          <td>KISA</td>
        </tr>
        <tr>
          <th scope="row">이용 목적</th>
          <td>차단</td>
        </tr>
        <tr>
          <th scope="row">제공 항목</th>
          <td>전화번호</td>
        </tr>
        <tr>
          <th scope="row">이용/보유기간</th>
          <td>
            서비스 가입기간(가입일~해지일)동안 이용하고, 요금정산, 요금과오납 등 분쟁 대비를 위해
            해지 후 6개월까지 보유할 수 있으며, 요금/과납이 있을 경우 해결 시까지 보유 (단, 법령에
            특별한 규 정이 있을 경우 관련 법령에 따라 보관합니다.)
          </td>
        </tr>
      </template>
    </MsfTable>
    <div class="checkbox-wrap">
      <MsfCheckbox
        v-model="formData.agreeCheck1"
        label="고객정보를 KISA에 제공하는 것을 동의합니다."
      />
    </div>
    <MsfBox>
      <MsfTextList
        :items="[
          '‘번호도용 차단 서비스’는 타 기관으로 고객정보를 제공하여 차단하는 서비스입니다.',
          '타 기관에 고객정보를 제공하는데 동의하셔야 서비스를 이용하실 수 있습니다.',
        ]"
        margin="0"
        level="2"
      />
    </MsfBox>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary">취소</MsfButton>
        <MsfButton variant="primary">확인</MsfButton>
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

// 퍼블샘플
const formData = reactive({
  agreeCheck1: false,
})
</script>

<style lang="scss" scoped></style>
