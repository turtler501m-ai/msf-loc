<template>
  <div class="page-step-panel">
    <!-- 고객 안내 사항 -->
    <MsfTitleArea title="고객 안내 사항" />
    <p class="ut-text-desc">다음 사항을 고객님께 설명하고 서명을 받아주세요.</p>
    <MsfBox>
      <ul class="agree-list">
        <li>
          <p class="agree-tit">※ 개통정보 녹음거부 동의</p>
          <MsfCheckbox
            v-model="formData.agreeCheck1"
            label="개통 정보에 대해 모두 확인하였고, 신청내용에 이의가 없으며 더 이상의 설명을 거부합니다."
          />
        </li>
        <li>
          <p class="agree-tit">※ 판매자 확인 안내</p>
          <MsfCheckbox
            v-model="formData.agreeCheck2"
            label="고객 보호를 위해서 통신상품 가입 시 본인확인 및 가입의사, 추가이용에 대해서 성실히 안내 하였습니다."
          >
            <template #label-prepend><em class="accent-mark">[판매자]</em></template>
          </MsfCheckbox>
        </li>
        <li>
          <p class="agree-tit">※ 가입자 확인 안내</p>
          <MsfCheckbox
            v-model="formData.agreeCheck3"
            label="본인 명의의 통신상품을 타인에게 제공하거나 매개하는 경우 법률에 따라 처벌 받을 수 있습니다."
          >
            <template #label-prepend><em class="accent-mark">[가입자]</em></template>
          </MsfCheckbox>
        </li>
      </ul>
    </MsfBox>
    <!-- // 고객 안내 사항 -->
    <!-- 신청서 확인 -->
    <MsfAppConfirm
      title="등록된 판매자 서명과<br/>다른 판매자 직원인 경우,"
      @confirm="console.log('신청서 확인 버튼 클릭!!')"
    />
    <!-- // 신청서 확인 -->
    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발해주신 부분 - 화면 프로세스</p>
        <select v-model="isComplete">
          <option value="">동의 저장</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
  </div>
</template>

<script setup>
import { ref, watch, reactive } from 'vue'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const isComplete = ref('')

const formData = reactive({
  agreeCheck1: false,
  agreeCheck2: false,
  agreeCheck3: false,
})

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(
  () => isComplete.value,
  (newVal) => {
    isComplete.value = newVal
    emit('complete', newVal ? true : false)
  },
)

const save = async () => {
  //  최종 데이터 저장
  return isComplete.value === 'true'
}

defineExpose({ save })
</script>

<style lang="scss" scoped></style>
