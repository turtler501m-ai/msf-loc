<template>
  <!-- 신청 완료 페이지 -->
  <div class="completed-page">
    <div class="completed-msg">
      <img src="@/assets/images/completedIcon.svg" alt="완료" />
      <p class="user-name">
        <em class="ut-color-accent"> {{ displayName }} </em>님
      </p>
      <div class="info-txt">
        <strong> 작성하신 신청서가 작성 완료되었습니다.</strong>
        <p class="desc">고객님께 신청서를 발송해 주세요.</p>
      </div>
    </div>
    <div class="box-layout">
      <MsfAppViewer
        class="box-item"
        :form-key="data.requestKey"
        :form-type="data.formType"
        :document-id="data.documentId"
      />
      <MsfAppSender class="box-item" :form-data="data" />
    </div>
    <MsfTextList
      :items="[
        '개통한 전화번호와 가입자 연락처 휴대폰번호로 동시에 발송됩니다.',
        '고객님께서 신청서 출력을 원하실 경우 관리자 사이트에서 출력하여 제공해 주시기 바랍니다.',
      ]"
    />
    <MsfBox class="kt-box-wrap">
      <strong class="box-tit">KT M모바일</strong>
      <div class="section-wrap">
        <div class="sec left">
          <div class="ktmmobile-info">
            <img src="@/assets/images/ktmmobile.svg" alt="kt m mobile" />
            <p class="desc">
              KT M 모바일에서 제공하는 서비스를 이용할수 있도록 고객에게 안내 발송해 주세요.<br />
              신규가입에서 부가서비스의 신청/변경, 실시간 사용량 조회까지 다양한 서비스를 이용하실
              수 있습니다.
            </p>
          </div>
        </div>
        <div class="sec right">
          <MsfStack type="field" nowrap>
            <MsfNumberInput
              v-model="phoneNumber"
              id="inp-sendPhoneForApp"
              maxlength="11"
              placeholder="휴대폰번호 ‘-’ 없이 입력"
              ariaLabel="휴대폰번호 ‘-’ 없이 입력"
              class="ut-flex-1"
            />
            <MsfButton
              variant="subtle"
              label="ios 발송"
              class="send-btn"
              prefixIcon="ios"
              :disabled="invalid"
              @click="() => onClickSendKTMessage('I')"
            >
              발송
            </MsfButton>
            <MsfButton
              variant="subtle"
              label="안드로이드 발송"
              class="send-btn"
              prefixIcon="android"
              :disabled="invalid"
              @click="() => onClickSendKTMessage('A')"
            >
              발송
            </MsfButton>
          </MsfStack>
        </div>
      </div>
    </MsfBox>
  </div>
</template>

<script setup>
import { computed, onBeforeMount, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { post } from '@/libs/api/msf.api'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import { validateMobile, isEmpty, maskingName } from '@/libs/utils/string.utils'

const route = useRoute()

const props = defineProps({
  requestKey: { type: String, required: true },
})

const smsType = ref('')
const data = ref({
  formType: '',
  requestKey: '',
  documentId: [],
  name: '',
  mobiles: [],
})
const phoneNumber = ref('')

const displayName = computed(() => maskingName(data.value?.name || ''))

const invalid = computed(() => {
  if (isEmpty(phoneNumber.value)) return true
  return !validateMobile(phoneNumber.value)
})

const onClickSendKTMessage = async (type) => {
  if (isEmpty(phoneNumber.value)) {
    showAlert('휴대폰번호를 입력해 주세요.')
    return false
  }
  if (invalid.value) {
    showAlert('휴대폰번호는 11자리 숫자로 입력해 주세요.')
    return false
  }

  smsType.value = `F-${data.value.formType}-${type}DN`

  showConfirm('SMS를 발송하시겠습니까?', async () => {
    const result = await post('/api/shared/common/sms/send', {
      type: smsType.value,
      path: route.path,
      phone: phoneNumber.value,
    })
    if (result?.code !== '0000') {
      showAlert('SMS 발송이 실패하였습니다.\n다시 시도해 주세요.')
      return false
    }

    showAlert('SMS 발송이 완료되었습니다.')
  })
}

watch(
  () => props.requestKey,
  async (newVal) => {
    if (!isEmpty(newVal)) {
      const result = await post('/api/form/common/complete/form', {
        requestKey: newVal,
      })
      data.value = result.data
    } else {
      data.value = {}
    }
  },
)

onBeforeMount(async () => {
  const result = await post('/api/form/common/complete/form', {
    requestKey: props.requestKey,
  })
  if (result.code !== '0000') {
    data.value = {}
    return
  }
  data.value = result.data
})
</script>

<style lang="scss" scoped></style>
