<!-- 작성완료 : 신청서 발송 박스영역 -->
<template>
  <MsfBox variant="outline" margin="0" class="application-box">
    <div class="box-title">
      <img src="@/assets/images/applySendIcon.svg" alt="신청서 발송 아이콘" />
      <em class="tit">신청서 발송</em>
    </div>
    <MsfStack type="field" nowrap>
      <MsfInput v-model="phoneNumber" readonly class="ut-w-200" />
      <MsfButton variant="subtle" :disabled="invalid" @click="onClickSendForm">발송</MsfButton>
    </MsfStack>
  </MsfBox>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { post } from '@/libs/api/msf.api'
import { getFormTypeCode } from '@/libs/utils/comn.utils'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import { validateMobile, isEmpty } from '@/libs/utils/string.utils'

const route = useRoute()

const props = defineProps({
  formKey: { type: String, required: true },
  phone: { type: String, required: true },
})

const smsType = ref('')

const phoneNumber = ref(props.phone)

const invalid = computed(() => {
  if (isEmpty(phoneNumber.value)) return true
  return !validateMobile(phoneNumber.value)
})

const onClickSendForm = async () => {
  if (isEmpty(phoneNumber.value)) {
    showAlert('휴대폰번호를 입력해 주세요.')
    return false
  }
  if (!invalid.value) {
    showAlert('휴대폰번호는 11자리 숫자로 입력해 주세요.')
    return false
  }

  const formTypeCd = getFormTypeCode(route.path)
  smsType.value = `F-${formTypeCd}-CMP`

  showConfirm('신청서를 발송하시겠습니까?', async () => {
    const result = await post('/api/shared/common/sms/send', {
      type: smsType.value,
      path: route.path,
      phone: phoneNumber.value,
    })
    if (result?.code !== '0000') {
      showAlert('신청서 발송이 실패하였습니다.\n다시 시도해 주세요.')
      return false
    }

    showAlert('신청서 발송이 완료되었습니다.')
  })
}

watch(
  () => props.phone,
  (newVal) => {
    phoneNumber.value = newVal
  },
  { immediate: true },
)
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
