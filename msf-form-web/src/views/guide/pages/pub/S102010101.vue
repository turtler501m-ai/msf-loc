<template>
  <MsfTitleBar title="서비스변경 신청서 > 고객 퍼블확인용" />
  <div class="page-step-panel">
    <!-- 고객 유형 -->
    <MsfTitleArea title="고객 유형" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="고객 유형" tag="div" required>
        <MsfChip
          v-model="formData.customerType"
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
    </MsfStack>
    <!-- // 고객 유형 -->
    <!-- 가입자 정보 -->
    <MsfTitleArea title="가입자 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput v-model="formData.userName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            v-model="formData.userBirthDate"
            length="8"
            class="ut-w-300"
            placeholder="8자리(YYYYMMDD)"
          />
          <MsfRadioGroup
            name="user-gender"
            v-model="formData.userGender"
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
          <MsfNumberInput v-model="formData.corpRegNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.corpRegNo2"
            id="inp-corpRegNo2"
            placeholder="뒤 7자리"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="사업자등록번호" helpText="※ 개인사업자인 경우만 입력">
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.bizNo1" placeholder="앞 3자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput v-model="formData.bizNo2" id="inp-bizNo2" placeholder="가운데 2자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput v-model="formData.bizNo3" id="inp-bizNo3" placeholder="뒤 5자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="대표자명" required>
        <MsfInput v-model="formData.repreName" placeholder="대표자명 입력" class="ut-w-300" />
      </MsfFormGroup>
      <!-- // 법인/공공기관만 항목 노출 -->
      <MsfFormGroup label="변경 휴대폰번호" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.deviceChgTel1" placeholder="앞자리" readonly />
          <span class="unit-sep">-</span>
          <MsfInput
            v-model="formData.deviceChgTel2"
            id="inp-deviceChgTel2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfInput
            v-model="formData.deviceChgTel3"
            id="inp-deviceChgTel3"
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
        <MsfInput v-model="formData.repName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            v-model="formData.repBirthDate"
            length="8"
            class="ut-w-300"
            placeholder="8자리(YYYYMMDD)"
          />
          <MsfRadioGroup
            name="user-gender"
            v-model="formData.repGender"
            :options="[
              { value: 'repGender1', label: '남' },
              { value: 'repGender2', label: '여' },
            ]"
            class="ut-ml-16"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="신청인과의 관계" tag="div" required>
        <MsfSelect
          title="신청인과의 관계"
          v-model="formData.repRelation"
          :options="[
            { label: '관계1', value: 'repRelation1' },
            { label: '관계2', value: 'repRelation2' },
          ]"
          placeholder="선택"
          class="ut-w-300"
        />
      </MsfFormGroup>
      <MsfFormGroup label="연락처(휴대폰)" required>
        <MsfStack type="field">
          <MsfNumberInput
            v-model="formData.repPhone1"
            placeholder="앞자리"
            ariaLabel="연락처(휴대폰) 앞자리"
            readonly
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.repPhone2"
            id="inp-repPhone2"
            placeholder="가운데 4자리"
            ariaLabel="연락처(휴대폰) 가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.repPhone3"
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
            v-model="formData.repPhoneAuth"
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
      v-model="formData.repAgree"
      label="본인은 안내사항을 확인하였습니다"
      required
      popTitle="법정대리인 안내사항 확인 및 동의"
      content="법정대리인 안내사항 확인 및 동의 내용"
    />
    <!-- // 법정대리인 안내사항 확인 및 동의 -->
    <!-- 대리인 위임 정보 -->
    <MsfTitleArea title="대리인 위임 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="위임받은<br/>고객 이름" required>
        <MsfInput v-model="formData.agentName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            v-model="formData.agentBirthDate"
            length="8"
            class="ut-w-300"
            placeholder="8자리(YYYYMMDD)"
          />
          <MsfRadioGroup
            name="user-gender"
            v-model="formData.agentGender"
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
          v-model="formData.agentRelation"
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
          <MsfInput v-model="formData.agentPhone1" placeholder="앞자리" />
          <span class="unit-sep">-</span>
          <MsfInput
            v-model="formData.agentPhone2"
            id="inp-agentPhone2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.agentPhone3" id="inp-agentPhone3" placeholder="뒤 4자리" />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 대리인 위임 정보 -->
    <!-- 가입자 연락처 -->
    <MsfTitleArea title="가입자 연락처" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="휴대폰번호" required helpText="※ 신청서 발송 요청시 추가로 발송">
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.mobileNo1" placeholder="앞 3자리" readonly />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.mobileNo2"
            id="inp-mobileNo2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput v-model="formData.mobileNo3" id="inp-mobileNo3" placeholder="뒤 4자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="전화번호">
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.telNo1" placeholder="지역번호" />
          <span class="unit-sep">-</span>
          <MsfNumberInput v-model="formData.telNo2" id="inp-telNo2" placeholder="가운데 4자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput v-model="formData.telNo3" id="inp-telNo3" placeholder="뒤 4자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="이메일주소" tag="div">
        <MsfStack type="field">
          <MsfInput v-model="formData.emailAddr1" placeholder="이메일 아이디" />
          <span>@</span>
          <MsfInput
            v-model="formData.emailAddr2"
            id="inp-emailAddr2"
            placeholder="이메일 도메인"
            class="ut-w-300"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="주소" tag="div" required>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.address1"
            placeholder="우편번호"
            ariaLabel="우편번호 입력"
            disabled
          />
          <MsfButton variant="subtle">우편번호 찾기</MsfButton>
        </MsfStack>
        <MsfInput
          id="address2"
          placeholder="주소"
          ariaLabel="주소 입력"
          class="ut-w100p"
          disabled
        />
        <MsfInput id="address3" placeholder="상세주소" ariaLabel="상세주소 입력" class="ut-w100p" />
      </MsfFormGroup>
    </MsfStack>
    <!-- // 가입자 연락처 -->
    <!-- 서비스 변경 선택_type01 -->
    <MsfTitleArea title="서비스 변경 선택_type01__디자인미확정" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="서비스 선택" tag="div" required>
        <MsfStack vertical>
          <MsfCheckbox v-model="formData.allCheck" label="전체 선택" />
          <MsfChip
            v-model="formData.serviceSelect"
            name="inp-serviceSelect"
            :data="[
              { value: 'serviceSelect1', label: '무선데이터차단 서비스' },
              { value: 'serviceSelect2', label: '정보료 상한금액 설정/변경' },
              { value: 'serviceSelect3', label: '부가서비스 신청/변경' },
              { value: 'serviceSelect4', label: '요금제 변경' },
              { value: 'serviceSelect5', label: '번호변경' },
              { value: 'serviceSelect6', label: '분실복구/일시정지해제 신청' },
              { value: 'serviceSelect7', label: '단말보험 가입' },
              { value: 'serviceSelect8', label: 'USIM 변경' },
              { value: 'serviceSelect9', label: '데이터쉐어링 가입/해지' },
              { value: 'serviceSelect10', label: '아무나 SOLO 결합' },
            ]"
          />
          <MsfTextList margin="0">
            <li>
              동시 변경 불가능 업무
              <MsfTextList type="dash">
                <li>요금제 변경, 번호변경, 분실복구/일시정지해제 신청</li>
              </MsfTextList>
            </li>
            <li>
              번호변경 : 평일 오전10시~오후8시까지 가능 (주말 공휴일은 변경불가)<br />
              데이터쉐어링 : 오전 08시~오후9:50까지 가능
            </li>
          </MsfTextList>
        </MsfStack>
        <MsfButtonGroup borderTop align="left">
          <MsfButton variant="toggle" disabled>서비스 체크</MsfButton>
        </MsfButtonGroup>
      </MsfFormGroup>
      <MsfFormGroup label="대리점" tag="div" required>
        <MsfSelect
          title="대리점 선택"
          v-model="formData.agency"
          :options="[
            { label: '대리점1', value: 'agency1' },
            { label: '대리점2', value: 'agency2' },
          ]"
          class="ut-w-300"
          placeholder="대리점 선택"
        />
      </MsfFormGroup>
    </MsfStack>
    <!-- // 서비스 변경 선택_type01 -->
    <!-- 서비스 변경 선택_type02 -->
    <MsfTitleArea title="서비스 변경 선택_type02__디자인미확정" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="요금제/부가<br/>서비스" tag="div">
        <MsfChip
          v-model="formData.addonService"
          name="inp-addonService"
          :data="[
            { value: 'addonService1', label: '무선데이터차단 서비스' },
            { value: 'addonService2', label: '정보료 상한금액 설정/변경' },
            { value: 'addonService3', label: '부가서비스 신청/변경' },
            { value: 'addonService4', label: '요금제 변경' },
            { value: 'addonService5', label: '단말보험 가입' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="결합서비스" tag="div">
        <MsfChip
          v-model="formData.combinedService"
          name="inp-combinedService"
          :data="[
            { value: 'combinedService1', label: '데이터쉐어링 가입/해지' },
            { value: 'combinedService2', label: '아무나 SOLO 결합' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="일시/분실정지" tag="div">
        <MsfChip
          v-model="formData.loseLock"
          name="inp-loseLock"
          :data="[{ value: 'loseLock', label: '분실복구/일시정지해제 신청' }]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="가입정보 변경" tag="div">
        <MsfStack vertical>
          <MsfChip
            v-model="formData.joinInfoChange"
            name="inp-joinInfoChange"
            :data="[
              { value: 'joinInfoChange1', label: '번호변경' },
              { value: 'joinInfoChange2', label: 'USIM 변경' },
            ]"
          />
          <MsfTextList margin="0">
            <li>
              동시 변경 불가능 업무
              <MsfTextList type="dash">
                <li>요금제 변경, 번호변경, 분실복구/일시정지해제 신청</li>
              </MsfTextList>
            </li>
            <li>
              번호변경 : 평일 오전10시~오후8시까지 가능 (주말 공휴일은 변경불가)<br />
              데이터쉐어링 : 오전 08시~오후9:50까지 가능
            </li>
          </MsfTextList>
        </MsfStack>
        <MsfButtonGroup borderTop align="left">
          <MsfButton variant="toggle">서비스 체크</MsfButton>
        </MsfButtonGroup>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 서비스 변경 선택_type02 -->
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
  //  데이터 임시저장
  return isComplete.value === 'true'
}

defineExpose({ data, save })

// 퍼블 샘플
const formData = reactive({
  /** 고객유형 */
  customerType: '', //고객유형
  /* 가입자 정보 */
  userName: '', //이름
  userBirthDate: '', //생년월일
  userGender: '', //성별
  corpRegNo1: '', //법인등록번호1
  corpRegNo2: '', //법인등록번호2
  bizNo1: '', //사업자등록번호1
  bizNo2: '', //사업자등록번호2
  bizNo3: '', //사업자등록번호3
  repreName: '', //대표자명
  deviceChgTel1: '', //변경휴대폰번호1
  deviceChgTel2: '', //변경휴대폰번호2
  deviceChgTel3: '', //변경휴대폰번호3
  /* 법정대리인 정보 */
  repName: '', //이름
  repBirthDate: '', //생년월일
  repGender: '', //성별
  repRelation: '', //관계
  repPhone1: '', // 연락처1
  repPhone2: '', // 연락처2
  repPhone3: '', // 연락처3
  repPhoneAuth: '', //인증번호입력
  repAgree: false, //동의
  /** 대리인 위임 정보 */
  agentName: '', //위임받은고객이름
  agentBirthDate: '', //생년월일
  agentGender: '', //성별
  agentRelation: '', //관계
  agentPhone1: '', //연락처1
  agentPhone2: '', //연락처2
  agentPhone3: '', //연락처3
  /* 가입자 연락처 */
  mobileNo1: '010', //휴대폰번호1
  mobileNo2: '', //휴대폰번호2
  mobileNo3: '', //휴대폰번호3
  telNo1: '', //전화번호1
  telNo2: '', //전화번호2
  telNo3: '', //전화번호3
  emailAddr1: '', //이메일주소1
  emailAddr2: '', //이메일주소2
  address1: '', //주소1
  address2: '', //주소2
  address3: '', //주소3
  /** 서비스 변경 선택_type01__디자인미확정 */
  allCheck: '', //전체 선택
  serviceSelect: '', //서비스선택
  agency: '', //대리점
  /** 서비스 변경 선택_type02__디자인미확정 */
  addonService: '', //요금제/부가 서비스
  combinedService: '', //결합서비스
  loseLock: '', //일시/분실정지
  joinInfoChange: '', //가입정보 변경
})
</script>

<style scoped></style>
