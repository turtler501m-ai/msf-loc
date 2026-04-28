<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="휴대폰 정보 스캔(eSIM)"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <p class="ut-text-caution ut-weight-medium">
      휴대폰의 '정보' 또는 '기기 정보' 화면을 촬영해주세요.<br />
      (IMEI, EID 정보가 포함되어야 합니다.)
    </p>
    <div class="doc-list-wrap">
      <ul class="doc-list">
        <li>
          <p>
            기기 정보 화면
            <MsfFlag v-if="docFile" data="완료" color="accent2" size="small" />
          </p>
          <MsfStack type="field">
            <MsfButton variant="subtle" @click="openCamera">
              {{ docFile ? '재촬영' : '촬영하기' }}
            </MsfButton>
          </MsfStack>
        </li>
      </ul>
    </div>

    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm" :disabled="!docFile">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'
import { post } from '@/libs/api/msf.api.js'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const docFile = ref(null)

const onOpen = () => {
  emit('open')
}

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 촬영 버튼 클릭 시 eSIM 스캔 API 호출 (임시 목업 데이터 세팅)
const openCamera = async () => {
  // 실제 환경에서는 카메라 호출 및 OCR API 연동
  // const res = await post('/api/shared/common/esim/scan')
  // if (res && res.data) {
  //   docFile.value = res.data
  // }

  // 테스트를 위한 임의의 값 생성
  const generateRandomDigits = (len) => {
    let res = ''
    for (let i = 0; i < len; i++) {
      res += Math.floor(Math.random() * 10)
    }
    return res
  }

  const mockData = {
    imei1: '35' + generateRandomDigits(13),
    imei2: '35' + generateRandomDigits(13),
    eid: '8904' + generateRandomDigits(28),
    modelNm: 'Mock-iPhone-15',
    serialNo: generateRandomDigits(10),
  }

  console.log('[eSIM 스캔 목업 데이터]:', mockData)
  docFile.value = mockData
}

const onConfirm = () => {
  emit('confirm', docFile.value)
  onClose()
}
</script>

<style lang="scss" scoped>
.doc-list-wrap {
  width: 100%;
  margin-top: rem(16px);
  border-top: var(--border-width-base) solid var(--color-gray-150);
  border-bottom: var(--border-width-base) solid var(--color-gray-75);
  .doc-list {
    & > li {
      padding-block: rem(16px);
      padding-inline: rem(24px);
      @include flex($v: center, $h: space-between) {
        gap: rem(16px);
      }
      border-top: var(--border-width-base) solid var(--color-gray-75);
      & > p:first-child {
        flex: 1;
        @include flex($v: center, $w: wrap) {
          gap: rem(4px);
        }
      }
      & > :last-child {
        flex-shrink: 0;
        flex-grow: 0;
      }
    }
  }
}
</style>
