<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신분증 스캔"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <p class="ut-text-caution ut-weight-medium">촬영 버튼을 선택하신 후, 신분증을 촬영해주세요.</p>
    <div class="doc-list-wrap">
      <ul class="doc-list">
        <li>
          <p>
            {{ props.identityTypeNm || '신분증 원본' }}
            <MsfFlag v-if="docFile" data="완료" color="accent2" size="small" />
          </p>
          <MsfStack type="field">
            <img
              v-if="previewUrl"
              :src="previewUrl"
              alt="미리보기"
              class="preview-img"
              @click="openCamera"
            />
            <MsfButton variant="subtle" @click="openCamera">
              {{ docFile ? '재촬영' : '촬영하기' }}
            </MsfButton>
          </MsfStack>
        </li>
      </ul>
    </div>

    <!-- TEST_SCAN_SAMPLE_START: 운영 반영 전 이 블록과 아래 TEST_SCAN_SAMPLE script/style 영역을 삭제 -->
    <div class="sample-list-wrap">
      <p class="sample-title">테스트용 스캔 결과</p>
      <ul class="sample-list">
        <li v-for="sample in scanSamples" :key="sample.scanId">
          <div
            class="sample-row"
            :class="{ active: selectedSample?.scanId === sample.scanId }"
            @click="selectSample(sample)"
          >
            <span class="sample-type">{{ sample.identityTypeNm }}</span>
            <MsfInput
              v-model="sample.cstmrNm"
              placeholder="이름"
              maxlength="100"
              class="sample-name"
              @click.stop
              @focus="selectSample(sample)"
            />
            <MsfNumberInput
              v-model="sample.rrn1"
              placeholder="생년월일"
              maxlength="8"
              class="sample-birth"
              @click.stop
              @focus="selectSample(sample)"
            />
            <MsfNumberInput
              v-model="sample.telNo"
              placeholder="휴대폰번호"
              maxlength="11"
              class="sample-tel"
              @click.stop
              @focus="selectSample(sample)"
            />
            <span class="sample-date">{{ sample.scanDate }}</span>
          </div>
        </li>
      </ul>
    </div>
    <!-- TEST_SCAN_SAMPLE_END -->

    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'
import { post } from '@/libs/api/msf.api.js'

const props = defineProps({
  modelValue: Boolean,
  identityTypeNm: String, // 신분증 명칭 추가
  identityTypeCd: String,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const docFile = ref(null)
const previewUrl = ref(null)

// TEST_SCAN_SAMPLE_START: 운영 반영 전 삭제
const selectedSample = ref(null)

const scanSamples = ref([
  {
    scanDate: '2024-04-20 14:20',
    scanDt: '2024-04-20 14:20',
    scanId: 'SCAN202404201420001',
    cstmrNm: '황순철',
    identityTypeNm: '주민등록증',
    identityTypeCd: '01',
    rrn1: '19861111',
    rrn2: '1000000',
    identityIssuDate: '2020.01.15',
    telNo: '01042496249',
  },
  {
    scanDate: '2024-04-21 10:15',
    scanDt: '2024-04-21 10:15',
    scanId: 'SCAN202404211015001',
    cstmrNm: '이선임',
    identityTypeNm: '운전면허증',
    identityTypeCd: '02',
    rrn1: '19700728',
    rrn2: '2000000',
    identityIssuDate: '2021.05.03',
    identityIssuRegion: '11',
    driveLicnsNo: '118512345678',
    telNo: '01052077586',
  },
  {
    scanDate: '2024-04-22 09:30',
    scanDt: '2024-04-22 09:30',
    scanId: 'SCAN202404220930001',
    cstmrNm: '박진용',
    identityTypeNm: '주민등록증',
    identityTypeCd: '01',
    rrn1: '19981223',
    rrn2: '1000000',
    identityIssuDate: '2019.12.10',
    telNo: '01043107338',
  },
])

const toScanData = (sample) => ({
  ...sample,
  scanSource: 'TEST_SAMPLE',
  isRealOcr: false,
  identityTypeCd: props.identityTypeCd || sample.identityTypeCd,
  identityTypeNm: props.identityTypeNm || sample.identityTypeNm,
  rrn: `${sample.rrn1 || ''}${sample.rrn2 || ''}`,
})

const selectSample = (sample) => {
  selectedSample.value = sample
  docFile.value = { data: toScanData(sample) }
}
// TEST_SCAN_SAMPLE_END

const onOpen = () => {
  emit('open')
}

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 촬영 버튼 클릭 시 카메라(파일 입력) 호출
const openCamera = async () => {
  docFile.value = await post('/api/shared/common/document/scan')
  emit('confirm', docFile.value?.data)
  onClose()
}

const onConfirm = () => {
  // 촬영된 파일 전송
  emit('confirm', selectedSample.value ? toScanData(selectedSample.value) : docFile.value?.data)
  onClose()
}
</script>

<style lang="scss" scoped>
// TEST_SCAN_SAMPLE_START: 운영 반영 전 삭제
.sample-list-wrap {
  margin-top: rem(12px);
  padding: rem(12px) rem(24px) 0;
  border-top: var(--border-width-base) solid var(--color-gray-75);
}
.sample-title {
  margin-bottom: rem(6px);
  font-size: rem(12px);
  font-weight: 600;
  color: var(--color-gray-700);
}
.sample-list {
  display: grid;
  gap: rem(4px);
}
.sample-row {
  min-height: rem(36px);
  padding: rem(4px) rem(8px);
  display: flex;
  align-items: center;
  gap: rem(10px);
  font-size: rem(12px);
  border: var(--border-width-base) solid var(--color-gray-150);
  border-radius: var(--border-radius-base);
  background: var(--color-white);
  cursor: pointer;
  &.active {
    border-color: var(--color-primary-base);
    background: var(--color-gray-50);
  }
}
.sample-type {
  width: rem(76px);
  flex-shrink: 0;
}
.sample-name {
  width: rem(30px);
  flex-shrink: 0;
}
.sample-birth {
  width: rem(82px);
  flex-shrink: 0;
}
.sample-tel {
  width: rem(140px);
  flex-shrink: 0;
}
.sample-date {
  margin-left: auto;
  flex-shrink: 0;
  text-align: right;
}
// TEST_SCAN_SAMPLE_END
</style>
