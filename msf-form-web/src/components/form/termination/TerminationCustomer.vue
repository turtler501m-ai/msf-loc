<template>
  <div class="page-step-panel">
    <!-- 고객 유형 -->
    <MsfTitleArea title="고객유형" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="고객 유형" tag="div" required>
        <MsfChip
          v-model="customer.customerType"
          name="inp-customerType"
          :data="[
            { value: 'customerType1', label: '내국인' },
            { value: 'customerType2', label: '미성년자(19세 미만)' },
            { value: 'customerType3', label: '외국인(Foeigner)' },
            { value: 'customerType4', label: '외국인(Foreigner) 미성년자' },
            { value: 'customerType5', label: '법인' },
            { value: 'customerType6', label: '공공기관' },
          ]"
        />
      </MsfFormGroup>
      <!-- 법인/공공기관만 항목 노출 -->
      <MsfFormGroup label="방문 고객" tag="div" required>
        <MsfChip
          v-model="customer.visitCustomer"
          name="inp-visitCustomer"
          :data="[
            { value: 'visitCustomer1', label: '본인/대표' },
            { value: 'visitCustomer2', label: '대리인' },
          ]"
        />
      </MsfFormGroup>
      <!-- // 법인/공공기관만 항목 노출 -->
    </MsfStack>
    <!-- // 고객 유형 -->
    <!-- 신분증 확인 -->
    <MsfTitleArea title="신분증 확인" />
    <MsfStack vertical type="formgroups">
      <!-- 신분증 스캔 -->
      <MsfFormGroup label="신분증 스캔" tag="div" required>
        <MsfStack type="field">
          <MsfSelect
            title="신분증 스캔"
            v-model="customer.idCardScan"
            :options="[
              { label: '주민등록증', value: 'idCardScan1' },
              { label: '운전면허증', value: 'idCardScan2' },
              { label: '국내여권', value: 'idCardScan3' },
              { label: '외국인등록증', value: 'idCardScan4' },
              { label: '영주증', value: 'idCardScan5' },
              { label: '국내거소신고증', value: 'idCardScan6' },
              { label: '국가보훈증', value: 'idCardScan7' },
            ]"
            class="ut-w-300"
          />
          <MsfButton variant="subtle">스캔하기</MsfButton>
        </MsfStack>
        <MsfStack type="field">
          <MsfDateInput v-model="datePickerValue" />
          <MsfSelect
            title="면허지역"
            v-model="customer.licenseRegion"
            :options="[
              { label: '면허지역1', value: 'licenseRegion1' },
              { label: '면허지역2', value: 'licenseRegion2' },
            ]"
            placeholder="면허지역"
            class="ut-w-200"
          />
          <MsfNumberInput
            v-model="customer.licenseNumber"
            maxlength="15"
            placeholder="면허번호"
            class="ut-w-240"
          />
        </MsfStack>
      </MsfFormGroup>
      <!-- // 신분증 스캔 -->
    </MsfStack>
    <!-- // 신분증 확인 -->
    <!-- 가입자 정보 -->
    <MsfTitleArea title="가입자 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput v-model="customer.userName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            v-model="customer.userBirthDate"
            length="8"
            class="ut-w-300"
            placeholder="8자리(YYYYMMDD)"
          />
          <MsfRadioGroup
            name="user-gender"
            v-model="customer.userGender"
            :options="[
              { value: 'userGender1', label: '남' },
              { value: 'userGender2', label: '여' },
            ]"
            class="ut-ml-16"
          />
        </MsfStack>
      </MsfFormGroup>
      <!-- 법인/공공기관만 항목 노출 -->
      <MsfFormGroup label="법인등록번호" required>
        <MsfStack type="field">
          <MsfNumberInput v-model="customer.corpRegNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="customer.corpRegNo2"
            id="inp-corpRegNo2"
            placeholder="뒤 7자리"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="사업자등록번호">
        <MsfStack type="field">
          <MsfNumberInput v-model="customer.bizNo1" placeholder="앞 3자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="customer.bizNo2"
            id="inp-bizNo2"
            placeholder="가운데 2자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput v-model="customer.bizNo3" id="inp-bizNo3" placeholder="뒤 5자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="대표자명" required>
        <MsfInput v-model="customer.repreName" placeholder="대표자명 입력" class="ut-w-300" />
      </MsfFormGroup>
      <!-- // 법인/공공기관만 항목 노출 -->
      <MsfFormGroup label="해지<br/>휴대폰번호" required>
        <MsfStack type="field">
          <MsfNumberInput v-model="customer.cancelPhone1" placeholder="앞 3자리" readonly />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="customer.cancelPhone2"
            id="inp-cancelPhone2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="customer.cancelPhone3"
            id="inp-cancelPhone3"
            placeholder="뒤 4자리"
          />
          <MsfButton variant="toggle" disabled>인증</MsfButton>
          <MsfButton variant="toggle">인증</MsfButton>
          <MsfButton variant="toggle" active>인증 완료</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 가입자 정보 -->
    <!-- 법정대리인 정보 -->
    <MsfTitleArea title="법정대리인 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput v-model="customer.repName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            v-model="customer.repBirthDate"
            length="8"
            class="ut-w-300"
            placeholder="8자리(YYYYMMDD)"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="신청인과의 관계" tag="div" required>
        <MsfSelect
          title="신청인과의 관계"
          v-model="customer.repRelation"
          :options="[
            { label: '관계1', value: 'nation1' },
            { label: '관계2', value: 'nation2' },
          ]"
          placeholder="선택"
          class="ut-w-300"
        />
      </MsfFormGroup>
      <MsfFormGroup label="연락처(휴대폰)" required>
        <MsfStack type="field">
          <MsfNumberInput
            v-model="customer.repPhone1"
            placeholder="앞자리"
            ariaLabel="연락처(휴대폰) 앞자리"
            readonly
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="customer.repPhone2"
            id="inp-repPhone2"
            placeholder="가운데 4자리"
            ariaLabel="연락처(휴대폰) 가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="customer.repPhone3"
            id="inp-repPhone3"
            placeholder="뒤 4자리"
            ariaLabel="연락처(휴대폰) 뒤 4자리"
          />
          <MsfButton variant="toggle" disabled>인증번호 발송</MsfButton>
          <MsfButton variant="toggle">인증번호 발송</MsfButton>
          <MsfButton variant="toggle">인증번호 재발송</MsfButton>
          <MsfButton variant="toggle" active>인증 완료</MsfButton>
        </MsfStack>
        <MsfStack type="field">
          <MsfInput
            v-model="customer.repPhoneAuth"
            id="inp-repPhoneAuth"
            placeholder="인증번호 입력"
          />
          <span class="remain-time">남은시간 <em>02:33</em></span>
          <MsfButton variant="toggle">인증번호 확인</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 법정대리인 정보 -->
    <!-- 법정대리인 안내사항 확인 및 동의 -->
    <MsfTitleArea title="법정대리인 안내사항 확인 및 동의" />
    <MsfAgreementItem
      type="default"
      v-model="customer.repAgree"
      label="본인은 안내사항을 확인하였습니다"
      required
      popTitle="법정대리인 안내사항 확인 및 동의"
      content="법정대리인 안내사항 확인 및 동의 내용"
    />
    <!-- // 법정대리인 안내사항 확인 및 동의 -->
    <!-- 대리인 위임정보 -->
    <MsfTitleArea title="대리인 위임정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="위임받은<br/>고객 이름" required>
        <MsfInput v-model="customer.agentName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            v-model="customer.agentBirthDate"
            placeholder="8자리(YYYYMMDD)"
            length="8"
            class="ut-w-300"
          />
          <MsfRadioGroup
            name="agent-gender"
            v-model="customer.agentGender"
            :options="[
              { value: 'agentGender1', label: '남' },
              { value: 'agentGender2', label: '여' },
            ]"
            class="ut-ml-16"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="신청인과의 관계" tag="div" required>
        <MsfSelect
          title="신청인과의 관계"
          v-model="customer.agentRelation"
          :options="[
            { label: '관계1', value: 'agentRelation1' },
            { label: '관계2', value: 'agentRelation2' },
          ]"
          placeholder="선택"
          class="ut-w-300"
        />
      </MsfFormGroup>
      <MsfFormGroup label="연락처" required>
        <MsfStack type="field">
          <MsfNumberInput v-model="customer.agentPhone1" placeholder="앞자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="customer.agentPhone2"
            id="inp-agentPhone2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="customer.agentPhone3"
            id="inp-agentPhone3"
            placeholder="뒤 4자리"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 대리인 위임정보 -->
    <!-- 구비서류 -->
    <MsfTitleArea title="구비서류" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="구비서류" tag="div" required>
        <MsfButton variant="subtle">스캔하기</MsfButton>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 구비서류 -->
    <!-- 해지 후 연락처 -->
    <MsfTitleArea title="해지 후 연락처" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="연락 전화번호" required helpText="※ 개통신청서 발송">
        <MsfStack type="field">
          <MsfNumberInput v-model="customer.afterTel1" placeholder="010/지역번호" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="customer.afterTel2"
            id="inp-afterTel2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput v-model="customer.afterTel3" id="inp-afterTel3" placeholder="뒤 4자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="해지 후 연락 수단" tag="div">
        <MsfChip
          v-model="customer.postMethod"
          name="inp-postMethod"
          :data="[
            { value: 'postMethod1', label: '우편' },
            { value: 'postMethod2', label: '이메일' },
          ]"
        />
      </MsfFormGroup>
    </MsfStack>
    <!-- // 해지 후 연락처 -->
    <!-- 가입유형 선택 -->
    <MsfTitleArea title="가입유형 선택" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="개통일자 확인">
        <MsfInput v-model="customer.openingDate" class="ut-w-300" disabled />
      </MsfFormGroup>
      <MsfFormGroup label="대리점" tag="div" required>
        <MsfSelect
          title="대리점 선택"
          v-model="customer.agencyName"
          :options="[
            { label: '대리점1', value: 'agencyName1' },
            { label: '대리점2', value: 'agencyName2' },
          ]"
          class="ut-w-300"
          placeholder="대리점 선택"
        />
      </MsfFormGroup>
    </MsfStack>
    <!-- // 가입유형 선택 -->
    <!-- 약관 동의  -->
    <MsfTitleArea title="약관 동의" />
    <MsfAgreementGroup policy="join" ref="agreementRef" required />
    <!-- // 약관 동의 -->

    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발해주신 부분 - 화면 프로세스</p>
        <select v-model="isComplete">
          <option value="">고객 저장</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { customer } = terminationStore

const isComplete = ref('')

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(
  () => isComplete.value,
  (newVal) => {
    isComplete.value = newVal
    emit('complete', newVal ? true : false)
  },
)

const data = async (code /* 임시저장 코드 */) => {
  // 임시저장 정보 조회
  if (code) {
    // 임시저장된 데이터 조회 후, 임시저장단계코드 리턴
    // 결과값 - null 또는 0: 임시저장 없음, 고객: 1, 상품: 2, 동의: 3
    return '1'
  }

  return '0' // 결과값 - null 또는 0: 임시저장 없음, 고객: 1, 상품: 2, 동의: 3
}

const save = async () => {
  if (isComplete.value !== 'true') return false
  // 다음 단계 진입 전 X18 잔여요금 조회
  await terminationStore.apiGetRemainCharge()
  return true
}

defineExpose({ data, save })

// 퍼블 샘플
const datePickerValue = ref() // 날짜선택 (MsfDateInput)
const agreementRef = ref(null) // 약관 동의
</script>

<style scoped></style>
