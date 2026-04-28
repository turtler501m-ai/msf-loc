<template>
  <MsfTitleBar title="명의변경 신청서 > 고객 퍼블확인용" />
  <div class="page-step-panel">
    <!-- 고객(양도고객) 유형 -->
    <MsfTitleArea title="고객(양도고객) 유형" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="고객 유형" tag="div" required>
        <MsfChip
          v-model="formData.tr_customerType"
          name="inp-tr_customerType"
          :data="[
            { value: 'tr_customerType1', label: '내국인' },
            { value: 'tr_customerType2', label: '미성년자(19세 미만)' },
            { value: 'tr_customerType3', label: '외국인(Foeigner)' },
            { value: 'tr_customerType4', label: '외국인(Foreigner) 미성년자' },
            { value: 'tr_customerType5', label: '법인' },
            { value: 'tr_customerType6', label: '공공기관' },
          ]"
        />
      </MsfFormGroup>
    </MsfStack>
    <!-- // 고객(양도고객) 유형 -->
    <!-- 고객(양도고객) 신분증 확인 -->
    <MsfTitleArea title="고객(양도고객) 신분증 확인" />
    <MsfStack vertical type="formgroups">
      <!-- <MsfFormGroup label="신분증" tag="div" required>
        <MsfChip
          v-model="formData.tr_idCardScan"
          name="inp-tr_idCardScan"
          :data="[
            { value: 'tr_idCardScan1', label: '신분증 목록 조회' },
            { value: 'tr_idCardScan2', label: '모바일 신분증' },
            { value: 'tr_idCardScan3', label: '안면 인증' },
            { value: 'tr_idCardScan4', label: '인증 예외' },
          ]"
        >
          <template #endSlot>
            <MsfButton variant="subtle">조회/인증</MsfButton>
          </template>
        </MsfChip>
      </MsfFormGroup> -->
      <!-- 신분증 스캔 -->
      <MsfFormGroup label="신분증 스캔" tag="div" required>
        <MsfStack type="field">
          <MsfSelect
            title="신분증 스캔"
            v-model="formData.tr_idCardScan"
            :options="[
              { label: '주민등록증', value: 'tr_idCardScan1' },
              { label: '운전면허증', value: 'tr_idCardScan2' },
              { label: '국내여권', value: 'tr_idCardScan3' },
              { label: '외국인등록증', value: 'tr_idCardScan4' },
              { label: '영주증', value: 'tr_idCardScan5' },
              { label: '국내거소신고증', value: 'tr_idCardScan6' },
              { label: '국가보훈증', value: 'tr_idCardScan7' },
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
    <!-- // 고객(양도고객) 신분증 확인 -->
    <!-- 고객(양도고객) 정보 -->
    <MsfTitleArea title="고객(양도고객) 정보, 미성년자, 법인" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput v-model="formData.tr_userName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="생년월일" required>
        <MsfStack type="field">
          <MsfBirthdayInput
            v-model="formData.tr_userBirthDate"
            length="8"
            class="ut-w-300"
            placeholder="8자리(YYYYMMDD)"
          />
          <MsfRadioGroup
            name="user-gender"
            v-model="formData.tr_userGender"
            :options="[
              { value: 'tr_userGender1', label: '남' },
              { value: 'tr_userGender2', label: '여' },
            ]"
            class="ut-ml-16"
          />
        </MsfStack>
      </MsfFormGroup>
      <!-- 법인/공공기관만 항목 노출 -->
      <MsfFormGroup label="법인등록번호" required>
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.tr_corpRegNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.tr_corpRegNo2"
            id="inp-tr_corpRegNo2"
            placeholder="뒤 7자리"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="사업자등록번호">
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.tr_bizNo1" placeholder="앞 3자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.tr_bizNo2"
            id="inp-tr_bizNo2"
            placeholder="가운데 2자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput v-model="formData.tr_bizNo3" id="inp-tr_bizNo3" placeholder="뒤 5자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="대표자명" required>
        <MsfInput v-model="formData.tr_repreName" placeholder="대표자명 입력" class="ut-w-300" />
      </MsfFormGroup>
      <!-- // 법인/공공기관만 항목 노출 -->
      <MsfFormGroup label="명의변경<br/>휴대폰번호" required>
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.tr_transferorPhone1" placeholder="앞자리" readonly />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.tr_transferorPhone2"
            id="inp-tr_transferorPhone2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.tr_transferorPhone3"
            id="inp-tr_transferorPhone3"
            placeholder="뒤 4자리"
          />
          <MsfButton variant="toggle" disabled>인증</MsfButton>
          <MsfButton variant="toggle">인증</MsfButton>
          <MsfButton variant="toggle" active>인증 완료</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 고객(양도고객) 정보 -->
    <!-- 고객(양도고객) 법정대리인 정보 -->
    <MsfTitleArea title="고객(양도고객) 법정대리인 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput v-model="formData.repName" placeholder="이름" class="ut-w-300" />
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
            placeholder="앞자리"
            ariaLabel="연락처(휴대폰) 앞자리"
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
    <!-- // 고객(양도고객) 법정대리인 정보 -->
    <!-- 고객(양도고객) 법정대리인 안내사항 확인 및 동의 -->
    <MsfTitleArea title="고객(양도고객) 법정대리인 안내사항 확인 및 동의" />
    <MsfAgreementItem
      type="default"
      v-model="formData.repAgree"
      label="본인은 안내사항을 확인하였습니다"
      required
      popTitle="법정대리인 안내사항 확인 및 동의"
      content="법정대리인 안내사항 확인 및 동의 내용"
    />
    <!-- // 고객(양도고객) 법정대리인 안내사항 확인 및 동의 -->
    <!-- 고객(양수고객) 유형 -->
    <MsfTitleArea title="고객(양수고객) 유형" />
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
      <MsfFormGroup label="방문고객" tag="div" required>
        <MsfChip
          v-model="formData.te_visitCustomer"
          name="inp-te_visitCustomer"
          :data="[
            { value: 'te_visitCustomer1', label: '본인/대표' },
            { value: 'te_visitCustomer2', label: '대리인' },
          ]"
        />
      </MsfFormGroup>
    </MsfStack>
    <!-- // 고객(양수고객) 유형 -->
    <!-- 고객(양수고객) 신분증 확인 -->
    <MsfTitleArea title="고객(양수고객) 신분증 확인" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="신분증" tag="div" required>
        <MsfChip
          v-model="formData.te_idCard"
          name="inp-te_idCard"
          :data="[
            { value: 'te_idCard1', label: '신분증 목록 조회' },
            { value: 'te_idCard2', label: '모바일 신분증' },
            { value: 'te_idCard3', label: '안면 인증' },
            { value: 'te_idCard4', label: '인증 예외' },
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
            v-model="formData.te_idCardScan"
            :options="[
              { label: '주민등록증', value: 'te_idCardScan1' },
              { label: '운전면허증', value: 'te_idCardScan2' },
              { label: '국내여권', value: 'te_idCardScan3' },
              { label: '외국인등록증', value: 'te_idCardScan4' },
              { label: '영주증', value: 'te_idCardScan5' },
              { label: '국내거소신고증', value: 'te_idCardScan6' },
              { label: '국가보훈증', value: 'te_idCardScan7' },
            ]"
            class="ut-w-300"
          />
          <MsfButton variant="subtle">스캔하기</MsfButton>
        </MsfStack>
        <MsfStack type="field">
          <MsfDateInput v-model="datePickerValue" />
          <MsfSelect
            title="면허지역"
            v-model="formData.te_licenseRegion"
            :options="[
              { label: '면허지역1', value: 'te_licenseRegion1' },
              { label: '면허지역2', value: 'te_licenseRegion2' },
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
    <!-- // 고객(양수고객) 신분증 확인 -->
    <!-- 고객(양수고객) 정보 -->
    <MsfTitleArea title="고객(양수고객) 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="고객명" required>
        <MsfInput v-model="formData.te_customerName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="이름" required>
        <MsfInput v-model="formData.te_userName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="주민등록번호" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.te_residentNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfInput
            v-model="formData.te_residentNo2"
            id="inp-te_residentNo2"
            placeholder="뒤 7자리"
          />
        </MsfStack>
      </MsfFormGroup>
      <!-- 외국인일 경우 -->
      <MsfFormGroup label="외국인등록번호" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.te_foreignerNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfInput
            v-model="formData.te_foreignerNo2"
            id="inp-te_foreignerNo2"
            placeholder="뒤 7자리"
          />
        </MsfStack>
      </MsfFormGroup>
      <!-- // 외국인일 경우 -->
      <MsfFormGroup label="법인등록번호" required>
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.te_corpRegNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.te_corpRegNo2"
            id="inp-te_corpRegNo2"
            placeholder="뒤 7자리"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="사업자등록번호" helpText="※ 개인사업자인 경우만 입력">
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.te_bizNo1" placeholder="앞 3자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.te_bizNo2"
            id="inp-te_bizNo2"
            placeholder="가운데 2자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput v-model="formData.te_bizNo3" id="inp-te_bizNo3" placeholder="뒤 5자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="대표자명" required>
        <MsfInput
          v-model="formData.te_repreName"
          placeholder="대표자명 입력"
          class="ut-w-300"
          disabled
        />
      </MsfFormGroup>
      <MsfFormGroup label="업종/업태" required>
        <MsfStack type="field" class="ut-w100p">
          <MsfSelect
            title="업종 선택"
            v-model="formData.te_bizType"
            :options="[
              { label: '업종1', value: 'te_bizType1' },
              { label: '업종2', value: 'te_bizType2' },
            ]"
            placeholder="업종 선택"
            class="ut-w-300"
          />
          <MsfInput v-model="formData.te_bizItem" placeholder="업태 입력" class="ut-flex-1" />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 고객(양수고객) 정보 -->
    <!-- 고객(양수고객) 법정대리인 정보-->
    <MsfTitleArea title="고객(양수고객) 법정대리인 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="이름" required>
        <MsfInput v-model="formData.te_repName" placeholder="이름" class="ut-w-300" />
      </MsfFormGroup>
      <MsfFormGroup label="주민등록번호" required>
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.te_repRegistrationNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.te_repRegistrationNo2"
            id="inp-te_repRegistrationNo2"
            placeholder="뒤 7자리"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="외국인등록번호" required>
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.te_repforeignerNo1" placeholder="앞 6자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.te_repforeignerNo2"
            id="inp-te_repforeignerNo2"
            placeholder="뒤 7자리"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="신청인과의 관계" tag="div" required>
        <MsfSelect
          title="신청인과의 관계"
          v-model="formData.te_repRelation"
          :options="[
            { label: '관계1', value: 'te_repRelation1' },
            { label: '관계2', value: 'te_repRelation2' },
          ]"
          placeholder="선택"
          class="ut-w-300"
        />
      </MsfFormGroup>
      <MsfFormGroup label="연락처(휴대폰)" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.te_repPhone1" placeholder="앞 3자리" readonly />
          <span class="unit-sep">-</span>
          <MsfInput
            v-model="formData.te_repPhone2"
            id="inp-te_repPhone2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.te_repPhone3" id="inp-te_repPhone3" placeholder="뒤 4자리" />
          <MsfButton variant="toggle" disabled>인증번호 발송</MsfButton>
          <MsfButton variant="toggle">인증번호 발송</MsfButton>
          <MsfButton variant="toggle">인증번호 재발송</MsfButton>
          <MsfButton variant="toggle" active>인증 완료</MsfButton>
        </MsfStack>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.te_repPhoneAuth"
            id="inp-te_repPhoneAuth"
            placeholder="인증번호 입력"
          />
          <span class="remain-time">남은시간 <em>02:33</em></span>
          <MsfButton variant="toggle">인증번호 확인</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- 고객(양수고객) 법정대리인 정보 -->
    <!-- 고객(양수고객) 법정대리인 안내사항 확인 및 동의 -->
    <MsfTitleArea title="고객(양수고객) 법정대리인 안내사항 확인 및 동의" />
    <MsfAgreementItem
      type="default"
      v-model="formData.te_repAgree"
      label="본인은 안내사항을 확인하였습니다"
      required
      popTitle="고객(양수고객) 법정대리인 안내사항 확인 및 동의"
      content="고객(양수고객) 법정대리인 안내사항 확인 및 동의 내용"
    />
    <!-- // 고객(양수고객) 법정대리인 안내사항 확인 및 동의 -->
    <!-- 고객(실사용자) 정보 -->
    <MsfTitleArea title="고객(실사용자) 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="실사용자 이름" required>
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
    </MsfStack>
    <!-- // 고객(실사용자) 정보 -->
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
            name="agent-gender"
            v-model="formData.agentGender1"
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
          <MsfNumberInput v-model="formData.agentPhone1" placeholder="앞자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.agentPhone2"
            id="inp-agentPhone2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.agentPhone3"
            id="inp-agentPhone3"
            placeholder="뒤 4자리"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 대리인 위임 정보 -->
    <!-- 구비서류 -->
    <MsfTitleArea title="구비서류" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="구비서류" tag="div" required>
        <MsfButton variant="subtle">스캔하기</MsfButton>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 구비서류 -->
    <!-- 고객(양수고객) 연락처 -->
    <MsfTitleArea title="고객(양수고객) 연락처" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="휴대폰번호" required helpText="※ 신청서 발송 요청시 추가로 발송">
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.te_mobileNo1" placeholder="앞 3자리" readonly />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.te_mobileNo2"
            id="inp-te_mobileNo2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.te_mobileNo3"
            id="inp-te_mobileNo3"
            placeholder="뒤 4자리"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="전화번호">
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.te_telNo1" placeholder="지역번호" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.te_telNo2"
            id="inp-te_telNo2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput v-model="formData.te_telNo3" id="inp-te_telNo3" placeholder="뒤 4자리" />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="이메일주소" tag="div">
        <MsfStack type="field">
          <MsfInput v-model="formData.te_emailAddr1" placeholder="이메일 아이디" />
          <span>@</span>
          <MsfInput
            v-model="formData.te_emailAddr2"
            id="inp-te_emailAddr2"
            placeholder="이메일 도메인"
            class="ut-w-300"
          />
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="주소" tag="div" required>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.te_address1"
            placeholder="우편번호"
            ariaLabel="우편번호 입력"
            disabled
          />
          <MsfButton variant="subtle">우편번호 찾기</MsfButton>
        </MsfStack>
        <MsfInput
          id="te_address2"
          placeholder="주소"
          ariaLabel="주소 입력"
          class="ut-w100p"
          disabled
        />
        <MsfInput
          id="te_address3"
          placeholder="상세주소"
          ariaLabel="상세주소 입력"
          class="ut-w100p"
        />
      </MsfFormGroup>
      <!-- 외국인인 경우 노출 -->
      <MsfFormGroup label="국가" tag="div" required>
        <MsfSelect
          title="국가"
          v-model="formData.te_country"
          :options="[
            { label: '국가1', value: 'te_country1' },
            { label: '국가2', value: 'te_country2' },
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
        <MsfInput v-model="formData.te_visaType" placeholder="비자 입력" class="ut-w-300" />
      </MsfFormGroup>
      <!-- // 외국인인 경우 노출 -->
    </MsfStack>
    <!-- // 고객(양수고객) 연락처 -->
    <!-- 요금제 정보 -->
    <MsfTitleArea title="요금제 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="요금제" tag="div" required>
        <MsfChip
          v-model="formData.product"
          name="inp-product"
          :data="[
            { value: 'product1', label: '요금제 변경' },
            { value: 'product2', label: '현재 요금제 사용' },
          ]"
        />
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
    <!-- // 요금제 정보 -->
    <!-- 약관 동의  -->
    <MsfTitleArea title="약관 동의" />
    <MsfAgreementGroup policy="join" ref="agreementRef" description="" required />
    <!-- // 약관 동의 -->
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

const formData = reactive({
  /********** 양도고객 (tr) **********/
  /** 고객(양도고객)유형 */
  tr_customerType: '', //고객유형
  /** 고객(양도고객)신분증 확인 */
  tr_idCard: '', //신분증
  tr_idCardScan: '', //신분증 스캔
  tr_licenseRegion: '', //면허 지역
  /** 고객(양도고객)정보 */
  tr_userName: '', //이름
  tr_userBirthDate: '', //생년월일
  tr_userGender: '', //성별
  tr_corpRegNo1: '', //법인등록번호1
  tr_corpRegNo2: '', //법인등록번호2
  tr_bizNo1: '', //사업자등록번호1
  tr_bizNo2: '', //사업자등록번호2
  tr_bizNo3: '', //사업자등록번호3
  tr_repreName: '', //대표자명
  tr_transferorPhone1: '010', //명의변경 휴대폰번호1
  tr_transferorPhone2: '', //명의변경 휴대폰번호2
  tr_transferorPhone3: '', //명의변경 휴대폰번호3

  /** 고객(실사용자) 정보 */
  userName: '', //실사용자이름
  userBirthDate: '', //생년월일
  userGender: '', //성별
  /** 대리인 위임 정보 */
  agentName: '', //위임받은고객이름
  agentBirthDate: '', //생년월일
  agentGender: '', //성별
  agentRelation: '', //관계
  agentPhone1: '', //연락처1
  agentPhone2: '', //연락처2
  agentPhone3: '', //연락처3

  /********** 양수고객 (te) **********/
  /** 고객(양수고객)유형 */
  te_customerType: '', //고객유형
  te_visitCustomer: '', //방문고객
  /** 고객(양수고객)신분증 확인 */
  te_idCard: '', //신분증
  te_idCardScan: '', //신분증 스캔
  te_licenseRegion: '', //면허 지역
  /** 고객(양수고객) 정보 */
  te_customerName: '', //고객명
  te_userName: '', //이름
  te_residentNo1: '', //주민등록번호1
  te_residentNo2: '', //주민등록번호2
  te_foreignerNo1: '', //외국인등록번호1
  te_foreignerNo2: '', //외국인등록번호2
  te_corpRegNo1: '', //법인등록번호1
  te_corpRegNo2: '', //법인등록번호2
  te_bizNo1: '', //사업자등록번호1
  te_bizNo2: '', //사업자등록번호2
  te_bizNo3: '', //사업자등록번호3
  te_repreName: '', //대표자명
  te_bizType: '', //업종
  te_bizItem: '', //업태
  /* 법정대리인 정보 */
  te_repName: '', //이름
  te_repRegistrationNo1: '', //주민등록번호1
  te_repRegistrationNo2: '', //주민등록번호2
  te_repforeignerNo1: '', //외국인등록번호1
  te_repforeignerNo2: '', //외국인등록번호2
  te_repRelation: '', //관계
  te_repPhone1: '', // 연락처1
  te_repPhone2: '', // 연락처2
  te_repPhone3: '', // 연락처3
  te_repPhoneAuth: '', //인증번호입력
  te_repAgree: false, //동의
  /** 고객(양수고객) 연락처 */
  te_mobileNo1: '010', //휴대폰번호1
  te_mobileNo2: '', //휴대폰번호2
  te_mobileNo3: '', //휴대폰번호3
  te_telNo1: '', //전화번호1
  te_telNo2: '', //전화번호2
  te_telNo3: '', //전화번호3
  te_emailAddr1: '', //이메일주소1
  te_emailAddr2: '', //이메일주소2
  te_address1: '', //주소1
  te_address2: '', //주소2
  te_address3: '', //주소3
  te_country: '', //국가
  te_stayPeriod: '', //체류기간
  te_visaType: '', //비자
  /** 요금제 정보 */
  planName1: '', // 요금제1
  planName2: '', // 요금제2
  planName3: '', // 요금제3
  agency: '', //대리점
})
</script>

<style scoped></style>
