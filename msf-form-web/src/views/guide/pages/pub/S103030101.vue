<template>
  <MsfTitleBar title="명의변경 신청서 > 동의 퍼블확인용" />
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
          <p class="agree-tit">※ 명의 대여 위험 안내</p>
          <MsfCheckbox
            v-model="formData.agreeCheck1"
            label="요금 상품 및 부가 서비스를 고객의사와 상관없이 무단 가입 및 의무기간을 설정하지 않으며 공시지원금 및 요금할인(선택약정)은 개통시점 공시된 기준으로 지급되며, 이에 동의합니다. 신청자 본인은 대출업자 등 제3자에게 고객명의의 휴대폰을 개통해 주거나, 개통에 필요한 신청서류를 제공하는 경우 휴대폰 대출 사기 등에 악용되어 심각한 경제적 피해를 입을 수 있음을 안내 받았으며, 아울러 신규계약서 내용, 뒷면의 이동전화 서비스 이용약관 및 유의사항, 관련 약관의 중요내용에 대한 명시와 설명을 듣고 이에 동의하여 개인정보 처리방침, 이용약관, 위치기반 서비스/위치정보사업/통신과금 서비스/본인확인서비스 이용약관에 따라 위와 같이 신규계약을 체결합니다."
          />
        </li>
        <li>
          <p class="agree-tit">※ 통신범죄예방 안내</p>
          <MsfCheckbox
            v-model="formData.agreeCheck2"
            label="타인으로부터 전화나 문자 요청을 받고 그에 따라 휴대폰 또는 USIM 개통을 하지 않았음을 확인합니다. (전기통신사업법 등 관련 법률에 따라 형사처벌을 받을 수 있습니다.)"
          />
        </li>
        <li>
          <p class="agree-tit">※ 명의도용방지 서비스(M-Safer) 안내</p>
          <MsfCheckbox
            v-model="formData.agreeCheck3"
            label="명의도용방지서비스(M-Safer)는 본인명의 통신서비스 가입 현황을 실시간 조회 가능하며, 본인동의 없이 통신서비스에 가입하지 못하도록 차단서비스를 제공합니다.(이용 및 신청방법 : 홈페이지 또는 PASS앱 제공)"
          />
        </li>
        <li>
          <p class="agree-tit">※ 판매자 확인 안내</p>
          <MsfCheckbox
            v-model="formData.agreeCheck3"
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
    <MsfAppConfirm @confirm="console.log('신청서 확인 버튼 클릭!!')" />
    <!-- // 신청서 확인 -->
  </div>
</template>

<script setup>
import { ref, watch, reactive } from 'vue'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const isComplete = ref('')

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

// 퍼블샘플
const formData = reactive({
  agreeCheck1: false,
  agreeCheck2: false,
  agreeCheck3: false,
})
</script>

<style scoped></style>
