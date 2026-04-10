<template>
  <div class="page-step-panel">
    <!-- 무선데이터차단 서비스 -->
    <MsfTitleArea title="무선데이터차단 서비스" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="무선데이터차단<br/>이용 여부" tag="div" required>
        <MsfChip
          v-model="formData.blockService"
          name="inp-blockService"
          :data="[
            { value: 'blockService1', label: '무선데이터 이용' },
            { value: 'blockService2', label: '무선데이터차단 서비스 이용' },
          ]"
        >
          <template #endSlot>
            <MsfButton variant="toggle">확인</MsfButton>
          </template>
        </MsfChip>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 무선데이터차단 서비스 -->
    <!-- 정보료 상한금액 설정/변경 -->
    <MsfTitleArea title="정보료 상한금액 설정/변경" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="정보료 상한금액" tag="div" required>
        <MsfChip
          v-model="formData.infoFee"
          name="inp-infoFee"
          :data="[
            { value: 'infoFee1', label: '0원' },
            { value: 'infoFee2', label: '3천원' },
            { value: 'infoFee3', label: '1만원', disabled: true },
            { value: 'infoFee4', label: '2만원' },
            { value: 'infoFee5', label: '3만원' },
          ]"
        >
          <template #endSlot>
            <MsfButton variant="toggle">확인</MsfButton>
          </template>
        </MsfChip>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 정보료 상한금액 설정/변경 -->
    <!-- 부가서비스 신청/변경 -->
    <MsfTitleArea title="부가서비스 신청/변경" />
    <MsfTable>
      <template #colgroup>
        <col />
        <col style="width: 120px" />
        <col style="width: 112px" />
      </template>
      <template #tbody>
        <tr>
          <th colspan="3" class="ut-text-left">무료 부가서비스</th>
        </tr>
        <tr>
          <td>무선데이터 차단</td>
          <td class="ut-text-center">무료</td>
          <td class="ut-text-center"></td>
        </tr>
        <tr>
          <td>불법TM수신차단</td>
          <td class="ut-text-center">무료</td>
          <td class="ut-text-center">
            <MsfButton variant="subtle">설정</MsfButton>
          </td>
        </tr>
        <tr>
          <td colspan="3">
            <div class="nodata-wrap">선택한 무료 서비스가 없습니다.</div>
          </td>
        </tr>
        <tr>
          <th colspan="3" class="ut-text-left">유료 부가서비스</th>
        </tr>
        <tr>
          <td>링투유</td>
          <td class="ut-text-center">990 원</td>
          <td class="ut-text-center"></td>
        </tr>
        <tr>
          <td>캐치콜</td>
          <td class="ut-text-center">550 원</td>
          <td class="ut-text-center"></td>
        </tr>
        <tr>
          <td>(신)로밍 하루종일 ON</td>
          <td class="ut-text-center">2,200 원/1일</td>
          <td class="ut-text-center">
            <MsfButton variant="subtle">설정</MsfButton>
          </td>
        </tr>
        <tr>
          <td colspan="3">
            <div class="nodata-wrap">선택한 유료 서비스가 없습니다.</div>
          </td>
        </tr>
      </template>
    </MsfTable>
    <!-- 합계박스 -->
    <MsfBox>
      <div class="total-box">
        <dl>
          <dt>합계(VAT 포함)</dt>
          <dd><em>3,740</em><span class="unit">원</span></dd>
        </dl>
      </div>
      <MsfButtonGroup class="total-btns">
        <MsfButton variant="subtle">추가/삭제</MsfButton>
        <MsfButton variant="toggle" disabled>확인</MsfButton>
        <MsfButton variant="toggle" active>확인 완료</MsfButton>
      </MsfButtonGroup>
    </MsfBox>
    <!-- // 합계박스 -->
    <!-- // 부가서비스 신청/변경 -->
    <!-- 요금제 변경 -->
    <MsfTitleArea title="요금제 변경" />
    <MsfStack vertical type="formgroups">
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
      <MsfFormGroup label="변경일시" tag="div" required>
        <MsfChip
          v-model="formData.changeDate"
          name="inp-changeDate"
          :data="[
            { value: 'changeDate1', label: '예약(익월1일)' },
            { value: 'changeDate2', label: '즉시변경' },
          ]"
        >
          <template #endSlot>
            <MsfButton variant="toggle">확인</MsfButton>
          </template>
        </MsfChip>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 요금제 변경 -->
    <!-- 번호변경 -->
    <MsfTitleArea title="번호변경" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="번호예약" required>
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.numReserve1" placeholder="앞 3자리" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.numReserve2"
            id="inp-numReserve2"
            placeholder="가운데 4자리"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.numReserve3"
            id="inp-numReserve3"
            placeholder="뒤 4자리"
          />
          <MsfButton variant="subtle">번호조회</MsfButton>
        </MsfStack>
        <p class="ut-text-desc">
          <span class="ut-text-count">조회 가능 횟수 <em>20회</em></span
          >※ 조회 가능 횟수를 초과할 경우 신청서를 재작성 해야 합니다.
        </p>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.hopeNumber"
            id="inp-hopeNumber"
            placeholder="선택된 희망 신규번호"
            class="ut-w-300"
            disabled
          />
          <MsfButton variant="toggle">선택취소</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 번호변경 -->
    <!-- 분실복구/일시정지해제 신청 -->
    <MsfTitleArea title="분실복구/일시정지해제 신청" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="일시정지 해제<br/>비밀번호">
        <MsfStack type="field">
          <MsfInput
            type="password"
            v-model="formData.unLockPw"
            placeholder="일시정지 해제 비밀번호"
            class="ut-w-300"
          />
          <MsfButton variant="toggle" disabled>확인</MsfButton>
          <MsfButton variant="toggle">확인</MsfButton>
          <MsfButton variant="toggle" active>확인 완료</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 분실복구/일시정지해제 신청 -->
    <!-- 단말보험 가입 -->
    <MsfTitleArea title="단말보험 가입" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="안심 보험 가입" tag="div">
        <MsfStack type="field" class="ut-w100p">
          <MsfSelect
            title="추천 카테고리"
            v-model="formData.insuranceSelect1"
            :options="[
              { label: '추천 카테고리1', value: 'insuranceSelect1-1' },
              { label: '추천 카테고리2', value: 'insuranceSelect1-2' },
            ]"
            placeholder="추천 카테고리"
            class="ut-w-300"
          />
          <MsfSelect
            title="추천 카테고리"
            v-model="formData.insuranceSelect2"
            :options="[
              { label: '안드로이드 플래티넘 / 월 기본료 4,900원1', value: 'insuranceSelect2-1' },
              { label: '안드로이드 플래티넘 / 월 기본료 4,900원2', value: 'insuranceSelect2-2' },
            ]"
            placeholder="추천 카테고리"
            class="ut-flex-1"
          />
          <MsfButton variant="toggle">확인</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 단말보험 가입 -->
    <!-- 단말보험 가입 약관 동의 -->
    <MsfTitleArea title="단말보험 가입 약관 동의" />
    <MsfAgreementGroup policy="join" ref="agreementRef" required />
    <!-- // 단말보험 가입 약관 동의 -->
    <!-- USIM 변경 -->
    <MsfTitleArea title="USIM 변경" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="SIM 보유" tag="div" required>
        <MsfChip
          v-model="formData.hasSim"
          name="inp-hasSim"
          :data="[
            { value: 'hasSim1', label: 'USIM 보유' },
            { value: 'hasSim2', label: 'USIM 구매' },
            { value: 'hasSim3', label: 'eSIM' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="USIM 선택" tag="div" required>
        <MsfChip
          v-model="formData.simType"
          name="inp-simType"
          :data="[
            { value: 'simType1', label: '일반 6,600원' },
            { value: 'simType2', label: 'NFC 8,800원' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="USIM 번호" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.simNo" placeholder="USIM 번호 19자리" class="ut-w-300" />
          <MsfButton variant="subtle">스캔하기</MsfButton>
          <MsfButton variant="toggle" disabled>USIM 번호 유효성 체크</MsfButton>
          <MsfButton variant="toggle">USIM 번호 유효성 체크</MsfButton>
          <MsfButton variant="toggle" active>USIM 번호 유효성 체크</MsfButton>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="USIM 구매 방식" tag="div" required>
        <MsfChip
          v-model="formData.simPurchaseMethod"
          name="inp-simPurchaseMethod"
          :data="[
            { value: 'simPurchaseMethod1', label: '즉시납부' },
            { value: 'simPurchaseMethod2', label: '다음달 요금에 합산' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="휴대폰 정보" required>
        <MsfInput
          v-model="formData.phoneModel"
          placeholder="휴대폰 모델명"
          class="ut-w-300"
          disabled
        />
        <MsfInput
          v-model="formData.phoneEID"
          id="inp-phoneEID"
          placeholder="EID"
          class="ut-w-608"
          disabled
        />
        <MsfStack type="field">
          <MsfInput
            v-model="formData.phoneIMEI1"
            id="inp-phoneIMEI1"
            placeholder="IMEI1"
            class="ut-w-300"
            disabled
          />
          <MsfInput
            v-model="formData.phoneIMEI2"
            id="inp-phoneIMEI2"
            placeholder="IMEI2"
            class="ut-w-300"
            disabled
          />
        </MsfStack>
        <MsfButton variant="toggle">이미지 등록</MsfButton>
        <MsfButton variant="toggle" active>이미지 등록 완료</MsfButton>
      </MsfFormGroup>
    </MsfStack>
    <!-- // USIM 변경 -->
    <!-- 데이터쉐어링 가입/해지 -->
    <MsfTitleArea title="데이터쉐어링 가입/해지" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="데이터쉐어링<br/>이용 여부" tag="div" required>
        <MsfChip
          v-model="formData.shareUseState"
          name="inp-shareUseState"
          :data="[
            { value: 'shareUseState1', label: '데이터쉐어링 가입' },
            { value: 'shareUseState2', label: '데이터쉐어링 해지' },
          ]"
        />
      </MsfFormGroup>
      <MsfFormGroup label="휴대폰 번호" required>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.sharePhoneNum"
            placeholder="휴대폰번호 입력 (“-” 제외)"
            class="ut-w-300"
          />
          <MsfButton variant="toggle" disabled>인증</MsfButton>
          <MsfButton variant="toggle">인증</MsfButton>
          <MsfButton variant="toggle" active>인증 완료</MsfButton>
        </MsfStack>
      </MsfFormGroup>
      <MsfFormGroup label="USIM 번호" required>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.shareUsimNum"
            placeholder="USIM 번호 19자리"
            class="ut-w-300"
          />
          <MsfButton variant="subtle">스캔하기</MsfButton>
          <MsfButton variant="toggle" disabled>USIM 번호 유효성 체크</MsfButton>
          <MsfButton variant="toggle">USIM 번호 유효성 체크</MsfButton>
          <MsfButton variant="toggle" active>USIM 번호 유효성 체크 완료</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 데이터쉐어링 가입/해지 -->
    <!-- 데이터쉐어링 가입/해지 약관 동의 -->
    <MsfTitleArea title="데이터쉐어링 가입/해지 약관 동의" />
    <MsfAgreementGroup policy="join" ref="agreementRef" required />
    <!-- // 데이터쉐어링 가입/해지 약관 동의 -->
    <!-- 아무나 SOLO 결합 -->
    <MsfTitleArea title="아무나 SOLO 결합" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="데이터" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.soloData" placeholder="0 MB" />
          <MsfButton variant="toggle">확인</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 아무나 SOLO 결합 -->
    <!-- 아무나 SOLO 결합 약관 동의 -->
    <MsfTitleArea title="아무나 SOLO 결합 약관 동의" />
    <MsfAgreementGroup policy="join" ref="agreementRef" required />
    <!-- // 아무나 SOLO 결합 약관 동의 -->
    <!-- 메모 -->
    <MsfTitleArea title="메모" />
    <MsfFormGroup label="메모">
      <MsfTextarea v-model="formData.memo" placeholder="메모 입력" />
    </MsfFormGroup>
    <!-- // 메모 -->

    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발해주신 부분 - 화면 프로세스</p>
        <select v-model="isComplete">
          <option value="">상품 저장</option>
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

const save = async () => {
  //  데이터 임시저장
  return isComplete.value === 'true'
}

defineExpose({ save })

// 퍼블 샘플
const formData = reactive({
  /* 무선데이터차단 서비스 */
  blockService: '',
  /* 정보료 상한금액 설정/변경 */
  infoFee: '',
  /** 요금제 변경 */
  planName1: '', //추천요금제1
  planName2: '', //추천요금제2
  changeDate: '', //변경일시
  /* 번호변경 */
  numReserve1: '', //번호예약1
  numReserve2: '', //번호예약2
  numReserve3: '', //번호예약3
  hopeNumber: '', //희망신규번호
  /* 분실복구/일시정지해제 신청 */
  unLockPw: '', // 일시정지 해제 비밀번호
  /* 단말보험 가입 */
  insuranceSelect1: '', //안심보험가입선택1
  insuranceSelect2: '', //안심보험가입선택2
  /* USIM 변경 */
  hasSim: '', //SIM보유
  simType: '', //USIM 선택
  simNo: '', //USIM 번호
  simPurchaseMethod: '', //USIM 구매 방식
  phoneModel: '', //휴대폰 모델병
  phoneEID: '', //EID
  phoneIMEI1: '', //IMEI1
  phoneIMEI2: '', //IMEI2
  /** 데이터쉐어링 가입/해지 */
  shareUseState: '', //데이터쉐어링 이용여부
  sharePhoneNum: '', //휴대폰번호
  shareUsimNum: '', //USIM 번호
  /** 아무나 SOLO 결합 */
  soloData: '', //데이터
  /** 메모 */
  memo: '', //메모
})
</script>

<style scoped></style>
