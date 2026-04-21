<script setup>
import { reactive } from 'vue'

const formData = reactive({
  birth6Value: '',
  /* 가입유형 선택 */
  productType: 'product1', //상품
  joinType: 'joinType1', //가입유형
  /* 고객 유형 */
  cstmrTypeCd: 'customerType1', //고객유형
  /* 신분증 확인 */
  identityCertTypeCd: 'idCard1', //신분증
  identityTypeCd: '', //신분증 스캔
  identityIssuRegion: 'licenseRegion1', //면허 지역
  /* 가입자 정보 */
  cstmrNm: '', //이름
  cstmrNativeRrn1: '', //주민등록번호1
  cstmrNativeRrn2: '', //주민등록번호2
  cstmrForeignerRrn1: '', //외국인등록번호1
  cstmrForeignerRrn2: '', //외국인등록번호2
  cstmrJuridicalRrn1: '', //법인등록번호1
  cstmrJuridicalRrn2: '', //법인등록번호2
  cstmrJuridicalBizNo1: '', //사업자등록번호1
  cstmrJuridicalBizNo2: '', //사업자등록번호2
  cstmrJuridicalBizNo3: '', //사업자등록번호3
  cstmrJuridicalRepNm: '', //대표자명
  upjnCd: '', //업종
  bcuSbst: '', //업태
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
  minorAgentNm: '', //위임받은 고객 이름
  agentBirthDate: '', //생년월일
  agentGender: '', //성별
  minorAgentRelTypeCd: '', //신청인과의 관계
  minorAgentTelFnNo: '', //연락처1
  minorAgentTelMnNo: '', //연락처2
  minorAgentTelRnNo: '', //연락처3
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
</script>

<template>
  <!-- 휴대폰 및 요금제 정보 -->
  <MsfTitleArea title="휴대폰 및 요금제 정보" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="개통 유형" tag="div" required>
      <MsfChip
        v-model="formData.openTypeCd"
        name="inp-openTypeCd"
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
</template>

<style scoped lang="scss"></style>
