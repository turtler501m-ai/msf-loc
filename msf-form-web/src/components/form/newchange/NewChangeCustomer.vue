<template>
  <div class="page-step-panel">
    <!-- 가입유형 선택 -->
    <MsfTitleArea title="가입유형 선택" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="상품" tag="div" required>
        <MsfChip
          v-model="formData.product"
          name="inp-product"
          :data="[
            { value: 'product1', label: '휴대폰' },
            { value: 'product2', label: 'USIM' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="가입유형" tag="div" required>
        <MsfChip
          v-model="formData.joinType"
          name="inp-joinType"
          :data="[
            { value: 'joinType1', label: '번호이동' },
            { value: 'joinType2', label: '신규가입' },
            { value: 'joinType3', label: '기기변경' },
          ]"
        />
      </MsfFormGroup>
    </MsfStack>
    <!-- // 가입유형 선택 -->
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
    <!-- 신분증 확인 -->
    <MsfTitleArea title="신분증 확인" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="신분증" tag="div" required>
        <MsfChip
          v-model="formData.idCard"
          name="inp-idCard"
          :data="[
            { value: 'idCard1', label: '신분증 목록 조회' },
            { value: 'idCard2', label: '모바일 신분증' },
            { value: 'idCard3', label: '안면 인증' },
            { value: 'idCard4', label: '인증 예외' },
          ]"
        >
          <template #endSlot>
            <MsfButton variant="subtle">조회/인증</MsfButton>
          </template>
        </MsfChip>
      </MsfFormGroup>
      <!-- 신분증 스캔 -->
      <MsfFormGroup label="신분증 스캔" tag="div" required>
        <MsfStack type="field">
          <MsfSelect
            title="신분증 스캔"
            v-model="formData.idCardScan"
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
            v-model="formData.licenseRegion"
            :options="[
              { label: '면허지역1', value: 'licenseRegion1' },
              { label: '면허지역2', value: 'licenseRegion2' },
            ]"
            placeholder="면허지역"
            class="ut-w-200"
          />
          <MsfNumberInput
            v-model="licenseNumber"
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
        <MsfInput v-model="formData.userName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="주민등록번호" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.residentNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.residentNo2" id="inp-residentNo2" placeholder="뒤 7자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="외국인등록번호" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.foreignerNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.foreignerNo2" id="inp-foreignerNo2" placeholder="뒤 7자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="법인등록번호" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.corpRegNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.corpRegNo2" id="inp-corpRegNo2" placeholder="뒤 7자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="사업자등록번호" helpText="※ 개인사업자인 경우만 입력">
        <MsfStack type="field">
          <MsfInput v-model="formData.bizNo1" placeholder="앞 3자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.bizNo2" id="inp-bizNo2" placeholder="가운데 2자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.bizNo3" id="inp-bizNo3" placeholder="뒤 5자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="대표자명" required>
        <MsfInput v-model="formData.repreName" placeholder="대표자명 입력" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="업종/업태" required>
        <MsfStack type="field" class="ut-w100p">
          <MsfSelect
            title="업종 선택"
            v-model="formData.bizType"
            :options="[
              { label: '업종1', value: 'bizType1' },
              { label: '업종2', value: 'bizType2' },
            ]"
            placeholder="업종 선택"
            class="ut-w-300"
          />
          <MsfInput v-model="formData.bizItem" placeholder="업태 입력" class="ut-flex-1" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="기기변경<br/>휴대폰번호" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.deviceChgTel1" placeholder="앞 3자리" readonly />
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
      <MsfFormGroup label="주민등록번호" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.repRegistrationNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfInput
            v-model="formData.repRegistrationNo2"
            id="inp-repRegistrationNo2"
            placeholder="뒤 7자리"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="외국인등록번호" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.repForeignerNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfInput
            v-model="formData.repForeignerNo2"
            id="inp-repForeignerNo2"
            placeholder="뒤 7자리"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="신청인과의 관계" tag="div" required>
        <MsfSelect
          title="신청인과의 관계"
          v-model="formData.repRelation"
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
            v-model="formData.repPhone1"
            placeholder="앞 3자리"
            ariaLabel="연락처(휴대폰) 앞 3자리"
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
    <!-- 고객(실사용자) 정보 -->
    <MsfTitleArea title="고객(실사용자) 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="실사용자 이름" required>
        <MsfInput v-model="formData.realUserName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput v-model="formData.userBirthDate" length="8" class="ut-w-300" />
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
    </MsfStack>
    <!-- // 고객(실사용자) 정보 -->
    <!-- 대리인 위임정보 -->
    <MsfTitleArea title="대리인 위임정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="위임받은<br/>고객 이름" required>
        <MsfInput v-model="formData.agentName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput v-model="formData.agentBirthDate" length="8" class="ut-w-300" />
          <MsfRadioGroup
            name="agent-gender"
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
    <!-- // 대리인 위임정보 -->
    <!-- 구비서류 -->
    <MsfTitleArea title="구비서류" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="구비서류" tag="div" required>
        <MsfButton variant="subtle">스캔하기</MsfButton>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 구비서류 -->
    <!-- 가입자 연락처 -->
    <MsfTitleArea title="가입자 연락처" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="휴대폰번호" required helpText="※ 신청서 발송 요청시 추가로 발송">
        <MsfStack type="field">
          <MsfInput v-model="formData.mobileNo1" placeholder="앞 3자리" readonly />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.mobileNo2" id="inp-mobileNo2" placeholder="가운데 4자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.mobileNo3" id="inp-mobileNo3" placeholder="뒤 4자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="전화번호">
        <MsfStack type="field">
          <MsfInput v-model="formData.telNo1" placeholder="지역번호" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.telNo2" id="inp-telNo2" placeholder="가운데 4자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.telNo3" id="inp-telNo3" placeholder="뒤 4자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="이메일주소" required>
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
      <MsfFormGroup label="국가" tag="div" required>
        <MsfSelect
          title="국가"
          v-model="formData.country"
          :options="[
            { label: '국가1', value: 'nation1' },
            { label: '국가2', value: 'nation2' },
          ]"
          placeholder="국가"
          class="ut-w-300"
        />
      </MsfFormGroup>
      <MsfFormGroup label="체류기간" tag="div" required>
        <MsfDateRange
          v-model:from="rangeDatePickerValue.start"
          v-model:to="rangeDatePickerValue.end"
        />
      </MsfFormGroup>
      <MsfFormGroup label="비자" required>
        <MsfInput v-model="formData.visaType" placeholder="비자 입력" class="ut-w-300" />
      </MsfFormGroup>
    </MsfStack>
    <!-- // 가입자 연락처 -->
    <!-- 휴대폰 및 요금제 정보 -->
    <MsfTitleArea title="휴대폰 및 요금제 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="개통 유형" tag="div" required>
        <MsfChip
          v-model="formData.openingType"
          name="inp-openingType"
          :data="[
            { value: 'openingType1', label: '휴대폰' },
            { value: 'openingType2', label: 'eSIM' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="휴대폰" tag="div" required>
        <MsfSelect
          title="휴대폰"
          v-model="formData.deviceModel"
          :options="[
            { label: '갤럭시 A36', value: 'deviceModel1' },
            { label: '갤럭시 A366', value: 'deviceModel2' },
          ]"
          class="ut-w-300"
        />
      </MsfFormGroup>
      <MsfFormGroup label="용량" tag="div" required>
        <MsfSelect
          title="용량"
          v-model="formData.capacity"
          :options="[
            { label: '64GB', value: 'capacity1' },
            { label: '128GB', value: 'capacity2' },
          ]"
          class="ut-w-300"
        />
      </MsfFormGroup>
      <MsfFormGroup label="색상" tag="div" required>
        <MsfSelect
          title="색상"
          v-model="formData.color"
          :options="[
            { label: '화이트', value: 'color1' },
            { label: '블랙', value: 'color2' },
          ]"
          class="ut-w-300"
        />
      </MsfFormGroup>
      <MsfFormGroup label="약정기간" tag="div" required>
        <MsfChip
          v-model="formData.contractPeriod"
          name="inp-contractPeriod"
          :data="[
            { value: 'contractPeriod1', label: '무약정' },
            { value: 'contractPeriod2', label: '24개월' },
            { value: 'contractPeriod3', label: '30개월' },
            { value: 'contractPeriod4', label: '36개월' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="단말기 할부기간" tag="div" required>
        <MsfChip
          v-model="formData.installmentMonth"
          name="inp-installmentMonth"
          :data="[
            { value: 'installmentMonth1', label: '0개월(없음)' },
            { value: 'installmentMonth2', label: '24개월' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="할인유형" tag="div" required>
        <MsfChip
          v-model="formData.discountType"
          name="inp-discountType"
          :data="[
            { value: 'discountType1', label: '단말 할인' },
            { value: 'discountType2', label: '알뜰스폰서(약정지원금)' },
            { value: 'discountType3', label: '알뜰스폰서(요금할인)' },
            { value: 'discountType4', label: '심플할인' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="요금제" tag="div" required>
        <MsfSelect
          title="요금제"
          v-model="formData.planName1"
          :options="[
            { label: '추천 요금제1', value: 'planName1-1' },
            { label: '추천 요금제2', value: 'planName1-2' },
          ]"
          class="ut-w100p"
          placeholder="추천 요금제"
        />
        <MsfSelect
          title="요금제"
          v-model="formData.planName2"
          :options="[
            {
              label:
                '5G 단말 (2GB/200분) / 데이터 2GB(+보답프로그램 50GB(6개월)) | 음성 200분 | 문자 100건1',
              value: 'planName2-1',
            },
            {
              label:
                '5G 단말 (2GB/200분) / 데이터 2GB(+보답프로그램 50GB(6개월)) | 음성 200분 | 문자 100건2',
              value: 'planName2-2',
            },
          ]"
          class="ut-w100p"
        />
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
    <!-- // 휴대폰 및 요금제 정보 -->
    <!-- 약관 동의  -->
    <MsfTitleArea title="약관 동의" />
    <MsfAgreementGroup policy="join" ref="agreementRef" description="" required />
    <MsfButtonGroup margin="1">
      <MsfButton variant="toggle" disabled>가입조건 조회</MsfButton>
      <MsfButton variant="toggle">가입조건 조회</MsfButton>
      <MsfButton variant="toggle" active>가입조건 조회</MsfButton>
    </MsfButtonGroup>
    <!-- // 약관 동의 -->
    <!-- 개통 진행 결과 -->
    <div class="step-result">
      <p class="result-title">
        <MsfIcon name="titleInfo" />
        <span
          >가입조건 조회 결과 개통 진행이
          <em class="ut-color-accent">가능(3회선 중 2회선 추가 가능)</em> 합니다.</span
        >
      </p>
      <p class="result-title">
        <MsfIcon name="titleInfo" />
        <span
          >가입조건 조회 결과 개통 진행이
          <em class="ut-color-point">불가능(3회선 중 2회선 추가 가능)</em> 합니다.</span
        >
      </p>
      <ul class="result-list">
        <li><span class="result-txt">가입제한</span><MsfFlag data="가능" color="accent" /></li>
        <li><span class="result-txt">가입한도</span><MsfFlag data="불가능" color="gray" /></li>
        <li><span class="result-txt">미납</span><MsfFlag data="가능" color="accent" /></li>
        <li><span class="result-txt">상습해지이력</span><MsfFlag data="가능" color="accent" /></li>
        <li><span class="result-txt">할부할인</span><MsfFlag data="불가능" color="gray" /></li>
      </ul>
    </div>
    <!-- // 개통 진행 결과 -->

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
// vue-datepicker
const datePickerValue = ref() // 날짜선택 (MsfDateInput)
const rangeDatePickerValue = ref({ start: '', end: '' })
const licenseNumber = ref() // 면허 번호 (MsfNumberInput)

// const birth6Value = ref()

const formData = reactive({
  birth6Value: '',
  /* 가입유형 선택 */
  product: 'product1', //상품
  joinType: 'joinType1', //가입유형
  /* 고객 유형 */
  customerType: 'customerType1', //고객유형
  /* 신분증 확인 */
  idCard: 'idCard1', //신분증
  idCardScan: '', //신분증 스캔
  licenseRegion: 'licenseRegion1', //면허 지역
  /* 가입자 정보 */
  userName: '', //이름
  residentNo1: '', //주민등록번호1
  residentNo2: '', //주민등록번호2
  foreignerNo1: '', //외국인등록번호1
  foreignerNo2: '', //외국인등록번호2
  corpRegNo1: '', //법인등록번호1
  corpRegNo2: '', //법인등록번호2
  bizNo1: '', //사업자등록번호1
  bizNo2: '', //사업자등록번호2
  bizNo3: '', //사업자등록번호3
  repreName: '', //대표자명
  bizType: '', //업종
  bizItem: '', //업태
  deviceChgTel1: '010', //기기변경 휴대폰번호1
  deviceChgTel2: '', //기기변경 휴대폰번호2
  deviceChgTel3: '', //기기변경 휴대폰번호3
  /* 법정대리인 정보 */
  repName: '', //이름
  repRegistrationNo1: '', //주민등록번호1
  repRegistrationNo2: '', //주민등록번호2
  repForeignerNo1: '', //외국인등록번호1
  repForeignerNo2: '', //외국인등록번호2
  repRelation: '', //관계
  repPhone1: '', // 연락처1
  repPhone2: '', // 연락처2
  repPhone3: '', // 연락처3
  repPhoneAuth: '', //인증번호입력
  repAgree: false, //동의
  /** 고객(실사용자) 정보 */
  realUserName: '', //실사용자 이름
  userBirthDate: '', //생년월일
  userGender: '', //성별
  /** 대리인 위임정보 */
  agentName: '', //위임받은 고객 이름
  agentBirthDate: '', //생년월일
  agentGender: '', //성별
  agentRelation: '', //신청인과의 관계
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
  country: '', //국가
  stayPeriod: '', //체류기간
  visaType: '', //비자
  /* 휴대폰 및 요금제 정보 */
  openingType: '', //개통 유형
  deviceModel: '', //휴대폰
  capacity: '', //용량
  color: '', //색상
  contractPeriod: '', //약정기간
  installmentMonth: '', //단말기 할부기간
  discountType: '', //할인유형
  planName1: '', //요금제1
  planName2: '', //요금제2
  agency: '', //대리점
})

// 약관 동의
const agreementRef = ref(null)
</script>

<style lang="scss" scoped></style>
