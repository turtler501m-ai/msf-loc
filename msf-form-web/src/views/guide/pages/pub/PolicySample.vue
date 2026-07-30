<!-- 
    MsfAgreementDetail 에 적용할 약관 스타일 보기용 샘플 팝업
 -->
<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    :title="currentPolicy?.title"
    @open="emit('open')"
    @close="onClose"
    :maximize="props.maximize"
  >
    <!-- 해당 키에 맞는 데이터가 있을 때만 렌더링 -->
    <div class="terms-content">
      <div v-if="currentPolicy" class="one-source" v-html="currentPolicy.content"></div>
    </div>
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="primary" @click="onConfirm">동의 후 닫기</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { computed, onMounted } from 'vue'

const props = defineProps({
  modelValue: Boolean,
  policy: {
    type: String,
    required: true,
  },
  /** MsfDialog maximize (간편신청서 화면에서 꽉채우는 팝업으로 설정시 사용) */
  maximize: { type: Boolean, default: false },
})

const emit = defineEmits(['update:modelValue', 'confirm', 'open', 'close'])

// 데이터 소스를 computed로 관리하여 props.policy가 변경될 때마다 자동으로 업데이트
const currentPolicy = computed(() => MOCK_POLICIES[props.policy] || null)

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 확인(동의) 버튼 클릭 시 실행
const onConfirm = () => {
  emit('confirm')
  onClose()
}

onMounted(() => {
  console.log('-d------------------')
  console.log(props.policy)
})

// 약관 퍼블리싱 스타일 테스트용
// policy.title = 팝업타이틀
// policy-content = 팝업내용 (one-source안에 들어가는 내용)
const MOCK_POLICIES = {
  policy01: {
    title: '번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법 동의',
    content: `
      <ul class="c-text-list c-bullet c-bullet--dot">
            <li class="c-text-list__item">번호이동 시 본인은 변경 전 통신회사의 이동전화서비스를 해지하고 잔여 단말기 구입대금 분할납부를 신청함에 있어서 변경 전 통신회사에 대한 할부금 납입의무가 가입자 본인에게 있음을 확인합니다.</li>
            <li class="c-text-list__item">본인은 변경 전 사업자에게 돌려받을 금액이 있는 경우 (주)케이티엠모바일에서 본인의 사용요금을 청구할 때 해당금액 상당의 요금을 (주)케이티엠모바일에 미리 납부한 것으로 간주하고, 잔여 요금에 대해 요금을 청구하는 것에 동의합니다.</li>
            <li class="c-text-list__item">이달 사용요금과 단말 잔여 분할상환금은 이전 통신회사에 납부하며, 이전 통신회사로부터 돌려받을 금액이 있는 경우 (주)케이티엠모바일이 요금 청구시 해당금액을 선 차감 뒤 잔여금을 청구하는 것에 동의합니다.</li>
            <li class="c-text-list__item">관련 요금 납부방법의 변경을 원하시면 고객센터로 연락 바랍니다.<ul class="c-text-list c-bullet c-bullet--fyr">
                <li class="c-text-list__item">(주)케이티엠모바일로 이동하신 후, 이전 통신화사와의 계약관계는 자동 해지되고, (주)케이티엠모바일의 이용조건과 통화요금이 적용됩니다.</li>
                <li class="c-text-list__item">변경 전 사업자의 이동전화서비스를 중도 해지함에 따라 위약금이 발생할 수 있습니다.</li>
                <li class="c-text-list__item">미납금이 존재할 경우 그 금액을 전 사업자에게 납부하여야 하며, 미납 시 이용이 정지될 수 있습니다.</li>
                <li class="c-text-list__item">잔여 분할상환내역 고지 및 분할상환금 청구는 변경 전 통신회사에서 문자청구서로 통보됩니다. 통보 받으실 이동전화번호가 변경될 경우 변경 전 통신회사 고객센터로 연락 바랍니다.</li>
            </ul>
            </li>
        </ul>
    `,
  },
  policy02: {
    title: '고유식별정보 수집·이용 동의',
    content: `
      <div class="c-table c-table--x-scroll u-mt--16">
        <table>
            <caption>고유식별정보 수집·이용 동의(필수동의) 정보를 포함한 표</caption>
            <colgroup>
                <col>
            </colgroup>
            <tbody>
                <tr>
                    <td class="u-ta-left">
                        kt M mobile은 가입의사 확인, 명의도용 방지, 미환급금 반환, 복지 할인, 본인확인 서비스, 단말기 분할상환 대금채권 등을 기초자산으로 한 자산유동화거래 목적과 같이 법적 근거가 있는 경우 <b>고유식별정보(주민등록번호/외국인등록번호/여권번호/운전면허번호)</b>를 처리합니다.
                        <p class="c-bullet c-bullet--fyr">본인, 대리인 신분확인 이미지 및 기재사항 포함</p>
                    </td>
                </tr>
            </tbody>
        </table>
        <p class="c-bullet c-bullet--fyr">위와 같이 고유식별정보 처리에 동의를 거부할 권리가 있습니다. 그러나 동의를 거부할 경우 서비스 이용이 제한될 수 있습니다.</p>
    </div>
    `,
  },
  policy03: {
    title: '개인정보/신용정보 수집·이용 동의',
    content: `
    <div class="c-table c-table--x-scroll u-mt--16">
        <table>
            <caption>수집·이용 목적, 수집 항목, 보유기간 정보를 포함한 표</caption>
            <colgroup>
                <col style="width: 300px;">
                <col style="width: 300px;">
                <col style="width: 300px;">
            </colgroup>
            <thead>
                <tr>
                    <th scope="col">수집·이용 목적</th>
                    <th scope="col">수집 항목</th>
                    <th scope="col">보유기간</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="u-ta-left">이동전화 서비스 가입/변경/해지, 개통/AS 처리, 청구서 발송, 물품(단말기/경품 등) 배송 및 배송안내 SMS(LMS/MMS)발송 서비스, 본인확인, 개인식별, 가입의사확인, 고지사항전달, 서비스제공 관련 안내, 명의도용 방지를 위한 등록된 이동전화로 가입사실 통보, 이용관련 문의 불만처리, 인구통계학적 분석(연령/성별/지역 분석 등), 이용형태, 선호도 분석, 구매경로 및 불법/부당영업행위 확인, 서비스 성능 향상, 품질향상을 위한 고객만족도 조사, 서비스 제공에 필요한 서버구축 운영, 개인맞춤형 서비스 제공, 관련 법률에 따르거나 서비스 이용 또는 업무처리 과정에서 생성되어 수집/이용, 신용정보집중기관 요금연체 정보 등록 전 및 신용정보회사로 채권추심 위탁 전 연체사실 통지(기존에 가입한 상품/서비스 포함)</td>
                    <td class="u-ta-left">고유식별정보, 신청인 또는 (법정)대리인의 성명, 생년월일, 연계정보(CI), 중복가입 확인정보(DI), 성별, 주소, 신용도 판단정보(번호변작, 불법스팸, 보이스피싱, 불법대부광고 등으로 인한 서비스 정지 등), 이동전화번호, 이메일주소, 단말기 정보(모델명, IMEI번호, USIM 정보 등), 신분증 기재사항, 납부정보, 서비스 이용시간/이용기록, 이용정지기록, 이용정지/해지 사유, 결제기록, 착/발신전화번호, 개통정보, 접속 IP/MAC, 접속로그, 이용컨텐츠, 서비스 이용 및 실행을 위한 사용자 음성명령 언어정보, 쿠키 등 서비스 이용정보, 기타 요금 과금에 필요한 데이터, 위의 개인정보를 조합하여 생성되는 정보, 개인위치정보(기지국, GPS 등 LBS플랫폼 수집 정보), 본인확인을 위한 얼굴 사진, 신분증 얼굴사진</td>
                    <td class="u-ta-left" rowspan="5">서비스 계약 해지 후 요금정산/과오납 등 분쟁대비를 위해 해지 후 6개월 까지(단, 아래의 경우는 각 정한바에 따름)<br><br>가. 요금정산이 미완료된 경우, 정산완료일로부터 6개월까지<br>나. 요금 관련 분쟁이 지속될 경우 분쟁이 종료되는 때까지<br>다. IMEI, 개통일자, 지원금 수혜여부는 요금할인(선택약정) 대상 및 분실 단말기 확인을 위해 서비스 해지 후 5년간 보유<br>라. 법령에 특별한 규정이 있을 경우 관련 법령에 따라 보관</td>
                </tr>
                <tr>
                    <td class="u-ta-left">이용요금 관련 상담/할인/청구(개별/통합/합산)/고지/결제 및 추심, 위탁수수료 정산</td>
                    <td class="u-ta-left">은행(카드사)명, 계좌(카드)번호, 예금(카드)주의 성명/생년월일/관계, 카드유효기간, 이동전화번호</td>
                </tr>
                <tr>
                    <td class="u-ta-left">복지요금제 가입 ※ 해당자에 한함</td>
                    <td class="u-ta-left">국가유공자증명/복지할인 등 제증명 정보(※장애인/복지단체/국가유공자/기초생활수급자/차상위계층 여부 등)</td>
                </tr>
                <tr>
                    <td class="u-ta-left">공인전자주소 생성 및 공인전자문서 중계자를 통한 모바일 전자문서 발송</td>
                    <td class="u-ta-left">성명, 생년월일, 이동전화번호, 전자문서 유통 연계정보(CI/DI)</td>
                </tr>
                <tr>
                    <td class="u-ta-left">명의도용과 부정가입 사용자 확인, 명의도용 발생된 아이피의 추가 명의도용 개통 방지를 위한 아이피 차단</td>
                    <td class="u-ta-left">다이렉트몰 가입 신청서를 작성한 아이피</td>
                </tr>
                <tr>
                    <td class="u-ta-left">단말 내 명의 일치 여부 확인</td>
                    <td class="u-ta-left">고유식별정보, 성명(법인명), 사업자(법인)등록번호, 이동전화번호, 단말기 정보(모델명, 일련번호, IMEI), 개통일자, 국적(외국인)</td>
                    <td class="u-ta-left">서비스 이용기간 동안</td>
                </tr>
                <tr>
                    <td class="u-ta-left">IMEI 사전등록 서비스</td>
                    <td class="u-ta-left">단말기 정보(IMEI)</td>
                    <td class="u-ta-left">서비스 이용기간 동안</td>
                </tr>
                <tr>
                    <td class="u-ta-left">본인확인서비스 제공</td>
                    <td class="u-ta-left">신청인의 성명, 성별, 생년월일, 내/외국인 구분, 이동전화번호, 본인확인 사용 웹사이트, 인증일시, 연계정보(CI), 중복가입확인정보(DI)</td>
                    <td class="u-ta-left">본인확인 시점으로부터 1년<br>※ 단, 연계정보(CI)/중복가입 확인정보(DI)는 별도 보관하지 않음</td>
                </tr>
                <tr>
                    <td class="u-ta-left">스팸메시지로부터 고객보호 및 불법 스팸 전송자에 대한 재가입 제한</td>
                    <td class="u-ta-left">성명, 생년월일(법인등록번호, 사업자등록번호), 성별, 이동전화번호, 정지 및 해지 사유, 스팸메세지 원문</td>
                    <td class="u-ta-left">스팸메시지 전송자 재가입 방지를 위해 1년간 보유</td>
                </tr>
                <tr>
                    <td class="u-ta-left">(주)케이티 위치정보사업 및 위치기반서비스 제공·요금정산, 서비스 품질 확인·개선, 상담·불만처리 등<br>※ KT 이동통신망 무선상품/서비스 가입시 필수</td>
                    <td class="u-ta-left">개인위치정보(기지국, GPS 등 LBS 플랫폼 수집정보)</td>
                    <td class="u-ta-left">서비스 이용기간 동안 고객이 동의한 목적 범위 내에서의 이용 및 고객 불만 응대를 위해 수집일로부터 3개월 간</td>
                </tr>
                <tr>
                    <td class="u-ta-left" colspan="3">
                        ※ KT 서비스 제공을 위해서 필요한 최소한의 개인정보이므로 동의를 해주셔야 서비스를 이용하실 수 있습니다.<br>
                        ※ 위와 같이 개인(신용)정보 수집·이용에 거부할 권리가 있습니다. 그러나 동의를 거부할 경우 서비스 이용이 제한될 수 있습니다.<br>
                        ※ 위치정보사업 및 위치기반서비스의 이용약관 전문 및 세부 내용은 shop.kt.com을 통해 확인하실 수 있습니다.
                    </td>
                </tr>
                <tr>
                    <td class="u-ta-left" colspan="3">본인은 상기 내용에 대하여 충분한 설명을 듣고, 내용을 읽어 보았으며 이를 이해하여 동의합니다.</td>
                </tr>
            </tbody>
        </table>
    </div>
    `,
  },
  policy04: {
    title: '개인정보 제3자 제공 동의',
    content: `
    <div class="c-table c-table--x-scroll u-mt--16">
        <table>
            <caption>제공받는 자, 제공항목, 제공목적, 보유 및 이용기간 정보를 포함한 표</caption>
            <colgroup>
                <col style="width: 180px;">
                <col style="width: 250px;">
                <col style="width: 250px;">
                <col style="width: 150px;">
            </colgroup>
            <thead>
                <tr>
                    <th scope="col">제공받는 자</th>
                    <th scope="col">제공항목</th>
                    <th scope="col">제공목적</th>
                    <th scope="col">보유 및 이용기간</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="u-ta-left">(주)KT</td>
                    <td class="u-ta-left">성명, 본인확인 식별에 필요한 정보(고유식별번호), 생년월일, 내/외국인 여부, 성별, 휴대폰번호, 청구지 정보, 이메일, 전화번호, 계좌(카드)정보 , 대리인 정보(성별, 신청인과의 관계 등), 서비스 이용 또는 업무처리 과정에서 생성되어 수집되는 정보(이동전화번호, 개통일자, 단말정보, USIM정보 등), 접속IP/MAC, 접속로그, 선택정보(부가서비스 이용과 관련된 정보 등), 위의 개인정보를 조합하여 생성되는 정보</td>
                    <td class="u-ta-left">고액사용자 다량회선사용자 가입제한 및 리스크 관리 휴대폰 대출 방지 휴대폰 본인인증 서비스, 통신과금 서비스(소액결제), 미납관리 대행(채권추심사 재위탁), 과금/청구/수납/미납고객관리, 통신과금서비스 업무대행, 본인확인서비스 제공 및 기타 법령에서 요구하는 근거에 따라 제공이 필요한 정보</td>
                    <td class="u-ta-left">서비스 계약 해지 후 6개월까지</td>
                </tr>
                <tr>
                    <td class="u-ta-left">한국인터넷진흥원(KISA)</td>
                    <td class="u-ta-left">신청서 작성된 단말기 IP 주소</td>
                    <td class="u-ta-left">신청서 작성된 IP기반 국내/국외 국가코드 확인</td>
                    <td class="u-ta-left">서비스 계약 해지 후 6개월까지</td>
                </tr>
                <tr>
                    <td class="u-ta-left" rowspan="7">한국정보통신진흥협회(KAIT) / 한국인터넷진흥원(KISA) / 과학기술정보통신부</td>
                    <td class="u-ta-left">성명, ESN번호, MODEL-ID, SERIAL-NO, 휴대폰번호, 국적, 등록사유, 등록사유일자, 체납금액, BAN/계약번호, 주소, 이동전화번호, IP주소</td>
                    <td class="u-ta-left">명의 도용 방지, 체납관리 등 불량회원의 부정이용 방지, 체납관리, 외국인 실명인증, 사망자정보확인, 방송통신신용정보공동관리(식별,연체,복지,이용정지 등), 국제전화사기 공동대응, 분실단말기 공동관리, 휴대폰 대출 방지, 복지요금감면 (자격확인, 변동정보확인, 공동관리 등)</td>
                    <td class="u-ta-left" rowspan="7">서비스 계약 해지 후 6개월까지</td>
                </tr>
                <tr>
                    <td class="u-ta-left">고유식별정보, 성명</td>
                    <td class="u-ta-left">명의 도용 관리</td>
                </tr>
                <tr>
                    <td class="u-ta-left">주민등록번호, 성명, 법인(사업자)등록번호, 이동전화번호, 이용정지/해지 사유</td>
                    <td class="u-ta-left">불법스팸 발송으로 이용정지/직권해지 사실의 타 통신사에 의한 확인</td>
                </tr>
                <tr>
                    <td class="u-ta-left">단말기모델명, 단말기 일련번호, 단말기정보, 생년월일, 성명, 이동전화번호, 주소</td>
                    <td class="u-ta-left">분실단말기 공동관리</td>
                </tr>
                <tr>
                    <td class="u-ta-left">IMEI, 개통일자, 지원금 수혜여부</td>
                    <td class="u-ta-left">요금할인(지원금)대상 단말기 확인</td>
                </tr>
                <tr>
                    <td class="u-ta-left">고유식별정보, 성명, 신분증 기재사항, 외국인거소신고번호, 당사 가입자여부, 이용내역, 연체금액</td>
                    <td class="u-ta-left">법정대리인 관계확인, 명의도용 예방, 명의도용 방지 SMS 안내 및 명의도용 분쟁조정</td>
                </tr>
                <tr>
                    <td class="u-ta-left">외국인등록번호, 여권번호, 성명, 국적, 생년월일, 회선번호</td>
                    <td class="u-ta-left">외국인 출국사실 정보 확인</td>
                </tr>
                <tr>
                    <td class="u-ta-left" rowspan="2">NICE 평가정보(주)</td>
                    <td class="u-ta-left">중복가입 확인정보(DI)</td>
                    <td class="u-ta-left">공공기록 체납정보 확인/금융질서 문란자 정보 확인, 채무불이행 정보 확인</td>
                    <td class="u-ta-left" rowspan="8">해당업무 처리 완료 시 까지</td>
                </tr>
                <tr>
                    <td class="u-ta-left">고유식별정보, 성명, 이동전화번호, 통신사 정보</td>
                    <td class="u-ta-left">실명인증, 채무불이행정보</td>
                </tr>
                <tr>
                    <td class="u-ta-left">(주)비바리퍼블리카</td>
                    <td class="u-ta-left">성명, 생년월일, 이동전화번호</td>
                    <td class="u-ta-left">실명인증</td>
                </tr>
                <tr>
                    <td class="u-ta-left">(주) 더즌, 주식회사 카카오</td>
                    <td class="u-ta-left">고유식별정보, 성명, 이동전화번호, 통신사 정보</td>
                    <td class="u-ta-left">실명인증</td>
                </tr>
                <tr>
                    <td class="u-ta-left">이동통신사(SK텔레콤, LG유플러스)</td>
                    <td class="u-ta-left">고유식별정보, 성명, 이용정지 사실, 이동전화번호</td>
                    <td class="u-ta-left">불법 스팸 발송으로 이용정지/직권해지 사실의 타 통신사에 의한 확인, 휴대폰 고액사용자, 다량회선 사용자 가입제한 및 리스크 관리, 휴대폰 대출방지</td>
                </tr>
                <tr>
                    <td class="u-ta-left">한국정보통신진흥협회, 행정자치부, 경찰청, 법무부, 보건복지부, 국가보훈처</td>
                    <td class="u-ta-left">주민등록번호(외국인의 경우 외국인등록번호, 여권번호, 거소신고번호, 생년월일, 국적), 운전면허번호, 성명, 신분증 발급일자, 사진 등 신분증 기재사항( 대리인포함 )</td>
                    <td class="u-ta-left">신분증 진위확인</td>
                </tr>
                <tr>
                    <td class="u-ta-left">한국정보통신진흥협회, 행정자치부, 법무부</td>
                    <td class="u-ta-left">주민등록번호(외국인등록번호), 성명</td>
                    <td class="u-ta-left">사망, 완전출국 확인</td>
                </tr>
                <tr>
                    <td class="u-ta-left">한국정보통신진흥협회, 행정자치부</td>
                    <td class="u-ta-left">주민등록번호(외국인의 경우 외국인등록번호, 외국인 거소신고번호 ), 고객 및 부모의 성명</td>
                    <td class="u-ta-left">미성년자 부모 여부 확인</td>
                </tr>
                <tr>
                    <td class="u-ta-left">한국정보통신진흥협회, 행정자치부, 법원행정처</td>
                    <td class="u-ta-left">주민등록번호(외국인의 경우 외국인등록번호, 외국인 거소신고번호 ), 고객 및 법정대리인의 성명</td>
                    <td class="u-ta-left">법정대리인 관계 확인</td>
                </tr>

            </tbody>
        </table>
    </div>
    `,
  },
  policy05: {
    title: '민감정보(생체인식정보) 수집 및 이용 동의',
    content: `
    <div class="c-table c-table--x-scroll u-mt--16">
        <table>
            <caption>수집·이용 목적, 수집 항목, 보유기간 정보를 포함한 표</caption>
            <colgroup>
                <col style="width: 100px;">
                <col style="width: 200px;">
                <col style="width: 230px;">
            </colgroup>
            <thead>
                <tr>
                    <th scope="col">목적</th>
                    <th scope="col">수집 항목</th>
                    <th scope="col">보유 및 이용기간</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="u-ta-left">가입의사 확인 및 본인확인</td>
                    <td class="u-ta-left">본인확인을 위한 얼굴사진 및 그로부터 추출되어 생성된 특징정보, 신분증 얼굴사진 원본정보와 그로부터 추출되어 생성된 특징정보</td>
                    <td class="u-ta-left">특징정보: 해당업무 처리 완료시까지<br>원본정보: 해당업무 처리 완료시까지<br>(본인확인을 위한 사진 및 그로부터 추출되어 생성된 특징정보는 본인확인 후 즉시삭제)</td>
                </tr>
            </tbody>
        </table>
    </div>
    `,
  },
  policy06: {
    title: '민감정보(생체인식정보) 조회 및 이용 / 3자 제공에 대한 동의',
    content: `
    <div class="c-table c-table--x-scroll u-mt--16">
        <table>
            <caption>제공받는자, 조회·이용 목적, 수집 항목, 보유기간 정보를 포함한 표</caption>
            <colgroup>
                <col style="width: 200px;">
                <col style="width: 200px;">
                <col style="width: 230px;">
                                        <col style="width: 230px;">
            </colgroup>
            <thead>
                <tr>
                    <th scope="col">제공받는자</th>
                    <th scope="col">항목</th>
                    <th scope="col">목적</th>
                                                <th scope="col">보유 및 이용기간</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="u-ta-left">과학기술정보통신부, 한국정보통신진흥협회, 행정안전부, 경찰청, 국가보훈부, 법무부, 보건복지부</td>
                    <td class="u-ta-left">신분증 얼굴사진 원본정보와 그로부터 추출되어 생성된 특징정보</td>
                    <td class="u-ta-left">부정가입 방지를 위한 본인확인(신분증 진위 여부 확인)</td>
                    <td class="u-ta-left">특징정보 : 해당업무 처리 완료시까지<br>원본정보 : 해당업무 처리 완료시까지</td>
                </tr>
            </tbody>
        </table>
    </div>
    `,
  },
  policy07: {
    title: '서비스 이용약관',
    content: `
  <h3 class="c-heading c-title--type2">(고지)<span class="u-co-mint">서비스 이용약관</span></h3>
  <p class="c-text c-text--type3 u-mt--16">
    아래 내용은 KT M mobile 이용약관 중 주요사항에 대한 설명으로, 이용약관 전문 내용과 다소 상이할 수 있고 변경될 수 있으므로, 이용약관 전문 내용은 홈페이지(<a href="http://www.ktmmobile.com)">www.ktmmobile.com)</a> 하단의 서비스 이용약관 또는 고객센터(1899-5000)를 통해 확인하시기 바랍니다.
  </p>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">
      계약의 성립
      <ul class="c-text-list c-bullet c-bullet--dot">
        <li class="c-text-list__item">고객은 구비서류 제출과 함께 이용계약서 정본을 종이나 홈페이지를 통해 작성하시고, 회사는 이용계약서 및 구비서류의 이미지(사본)를 보관하며, 고객은 이용계약서를 정본 또는 사본(사본이란 고객이 작성한 이용계약서의 이미지가 첨부된 MMS 또는 e-mail 또는 이용계약서 이미지의 출력본, 어플 형태 등을 의미)의 형태로 제공받습니다.</li>
      </ul>
    </li>
    <li class="c-text-list__item">
      복지요금제의 시행
      <p class="c-text c-text--type3 u-mt--16">
        장애인, 국가유공자, 기초생활수급자 및 차상위계층에 대한 복지요금제를 시행중입니다.
      </p>
    </li>
    <li class="c-text-list__item">
      계약의 변경
      <p class="c-text c-text--type3 u-mt--16">
        주소, 부가서비스, 요금제 변경 등은 계약을 체결한 구매처 방문 외에 고객센터를 통해 팩스, 전화 등으로 신청할 수 있으며, 서비스 종류 및 핸드폰 변경의 경우에도 KT M mobile 위탁대리점(대리점과 재위탁 계약을 체결한 판매점) 방문 또는 고객센터를 통해 구비서류를 제출해야 합니다. 요금 미납, 약정 내용 위반, 휴대폰 압류, 가압류의 경우에는 계약 내용의 변경이 제한됩니다.
      </p>
    </li>
    <li class="c-text-list__item">
      이용정지
      <p class="c-text c-text--type3 u-mt--16">
		이용요금을 2회(7만원 이상은 1회) 미납한 경우, 타인 명의(이름), 예금계좌, 신용카드를 무단으로(허락받지 않고) 사용하여 가입한 경우, 대량으로 스팸을 전송한 경우 등 고객이 각종 위반행위를 한 경우에는 위반행위를 한 경우 회사는 서비스 이용을 정지시킬 수 있습니다.</p>
    </li>
    <li class="c-text-list__item">
      해지(일반, 직권해지)
      <ul class="c-text-list c-bullet c-bullet--dot">
        <li class="c-text-list__item">고객이 전화, FAX 또는 우편 등에 의한 해지 신청 시에는 고객센터에서만 가능하며, 고객은 해지 신청일까지 미납요금을 납입 또는 정산해야 합니다.</li>
        <li class="c-text-list__item">해지요청 시점에서 아직 청구되지 않은 금액은 익월 정산 및 청구될 수 있으며, 타인 명의도용, 이용요금 미납, 스팸메시지 발송 등 고객이 정상적으로 서비스를 이용하지 않는 경우 경우 서비스가 직권해지 될 수 있습니다.</li>
      </ul>
    </li>
  </ul>

  <h3 class="c-heading c-title--type2">(고지)<span class="u-co-mint">약정할인 안내사항</span></h3>
  <ul class="c-text-list c-bullet c-bullet--dot">
    <li class="c-text-list__item">약정할인 가입 시 요금제별 약정기간 가입이 일부 제한되며, 가입 후 약정기간 변경 불가(12개월↔24개월)</li>
    <li class="c-text-list__item">약정할인은 가입완료 시점부터 적용되며, 약정기간 만료일까지만 제공</li>
    <li class="c-text-list__item">
      약정할인 대상 요금제간 변경 시 약정유지로 간주되며, 할인 혜택은 일할계산 및 자동 변경
      <ul class="c-text-list c-bullet c-bullet--hyphen">
        <li class="c-text-list__item">1개월내 요금제 재변경 및 재가입불가</li>
      </ul>
    </li>
    <li class="c-text-list__item">약정할인 대상 요금제중 3G ↔ LTE간 변경은 불가</li>
    <li class="c-text-list__item">중고단말기로 대상요금제 가입시 요금할인 미적용</li>
    <li class="c-text-list__item">
      월 중 가입, 약정만료, 가입해지, 요금상품 변경, 일시정지, 이용정지 및 명의변경시 요금할인은 일할계산됨
      <ul class="c-text-list c-bullet c-bullet--hyphen">
        <li class="c-text-list__item">단, 명의변경시 양수인이 약정 미승계 하는 경우 할인반환금 발생</li>
      </ul>
    </li>
    <li class="c-text-list__item">정지기간은 약정이용 기간에 미 산정되며, 정지기간 만큼 약정 만료일 연장</li>
    <li class="c-text-list__item">
      할인반환금
      <ul class="c-text-list c-bullet c-bullet--hyphen">
        <li class="c-text-list__item">약정할인 대상 요금제를 약정기간내 약정 해지하는 경우(요금제해지, 대상외 요금제로 변경, 약정기간 및 할인프로그램 변경등)에는 할인혜택이 적용되지 않으며, 할인반환금이 부과될 수 있음</li>
        <li class="c-text-list__item">할인반환금 = ∑ [약정이용기간 별 총 할인금액 X (1 ? 약정 이용기간 별 할인반환금 할인율)]</li>
        <li class="c-text-list__item">할인반환금 산정을 위한 약정할인액은 해당 월 고객이 가입한 요금제 월정액의 28.2%를 초과할 수 없음</li>
        <li class="c-text-list__item">월 할인액이 요금제 월정액의 28.2% 미만인 경우, “할인반환금 산정대상 할인액 = 월 할인액”</li>
        <li class="c-text-list__item">월 할인액이 요금제 월정액의 28.2% 이상인 경우, “할인반환금 산정대상 할인액 = 월정액 X 0.282</li>
        <li class="c-text-list__item">약정 이용기간 별 총 할인금액 : 약정 이용기간 별로 제공받은 약정 할인액의 합산금액</li>
        <li class="c-text-list__item">
          약정 이용기간 별 할인반환금 할인율 : 약정 이용기간 별로 각 제공받은 약정할인액의 합산금액에 대해 해당 할인율을 적용하여 할인반환금 부과
          <div class="c-table c-table--x-scroll u-mt--16 u-mb--24">
            <table>
              <caption>
                약정기간, 약정 이용기간, 할인반환금 부과율 항목이 포함된 표
              </caption>
              <colgroup>
                <col>
                <col>
                <col>
            
              </colgroup>
              <thead>
                <tr>
                  <th scope="col">약정기간</th>
                  <th scope="col">약정 이용기간</th>
                  <th scope="col">할인반환금 부과율</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td rowspan="3">12개월</td>
                  <td class="u-ta-left">약정 후 1 ~ 3개월 이내</td>
                  <td class="u-ta-left">0%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 4 ~ 9개월 이내</td>
                  <td class="u-ta-left">50%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 10 ~ 12개월 이내</td>
                  <td class="u-ta-left">110%</td>
                </tr>
                <tr>
                  <td rowspan="5">24개월</td>
                  <td class="u-ta-left">약정 후 1 ~ 6개월 이내</td>
                  <td class="u-ta-left">0%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 7 ~ 12개월 이내</td>
                  <td class="u-ta-left">40%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 13 ~ 16개월 이내</td>
                  <td class="u-ta-left">70%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 17 ~ 20개월 이내</td>
                  <td class="u-ta-left">120%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 21 ~ 24개월 이내</td>
                  <td class="u-ta-left">145%</td>
                </tr>
                <tr>
                  <td rowspan="6">30개월</td>
                  <td class="u-ta-left">약정 후 1 ~ 6개월 이내</td>
                  <td class="u-ta-left">0%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 7 ~ 12개월 이내</td>
                  <td class="u-ta-left">40%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 13 ~ 16개월 이내</td>
                  <td class="u-ta-left">70%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 17 ~ 20개월 이내</td>
                  <td class="u-ta-left">120%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 21 ~ 24개월 이내</td>
                  <td class="u-ta-left">145%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 25 ~ 30개월 이내</td>
                  <td class="u-ta-left">160%</td>
                </tr>
                <tr>
                  <td rowspan="7">36개월</td>
                  <td class="u-ta-left">약정 후 1 ~ 6개월 이내</td>
                  <td class="u-ta-left">0%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 7 ~ 12개월 이내</td>
                  <td class="u-ta-left">40%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 13 ~ 16개월 이내</td>
                  <td class="u-ta-left">70%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 17 ~ 20개월 이내</td>
                  <td class="u-ta-left">120%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 21 ~ 24개월 이내</td>
                  <td class="u-ta-left">145%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 25 ~ 30개월 이내</td>
                  <td class="u-ta-left">160%</td>
                </tr>
                <tr>
                  <td class="u-ta-left">약정 후 30 ~ 36개월 이내</td>
                  <td class="u-ta-left">170%</td>
                </tr>
              </tbody>
            </table>
          </div>
        </li>
      </ul>
    </li>
    <li class="c-text-list__item">
      신규가입일로부터 14일 이내 주 생활지에서의 통화품질 불량의 사유로 해지(단, 단말기 등 구입시 지급받은 물품일체를 손상된 부분 없이 반납해야 함)하거나, 고객의 사망, 이민 등의 사유로 해지(단, 해당 사유를 증명할 수 있는 서류 제출 필요)시,
      약정 해지 위약금(할인반환금) 부과 면제됨
    </li>
    <li class="c-text-list__item">본 요금제에만 월정액에서 약정할인을 매월 제공하며, 최대 할인 금액은 월정액을 초과할 수 없음</li>
  </ul>
  <h3 class="c-heading c-title--type2">(고지)<span class="u-co-mint">핸드폰 할부매매 약정안내</span></h3>
  <p class="c-text c-text--type3 u-mt--16">
    케이티엠모바일 서비스 이용을 위해 핸드폰을 할부로 구입한 “본인”(이하 ‘갑’이라 함)과 서비스 제공자인 “주식회사 케이티 엠모바일”(이하 ‘을’이라 함) 및 핸드폰 판매자인 “케이티 엠모바일 대리점”(이하 ‘병’이라 함)은 “핸드폰 할부대금 및
    할부수수료”(이하 ‘할부금’이라 함)와 관련하여 다음과 같이 약정을 체결합니다.
  </p>
  <h4 class="c-heading c-title--type3">제 1조 (할부매매약정 및 계약상 지위 이전)</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">
      ‘갑’은 핸드폰 할부매매약정서에 의하여 ‘병’으로부터 할부로 핸드폰을 구입하고, ‘병’은 핸드폰 할부매매로 취득한 할부채권 및 할부매매와 관련된 모든 계약상 지위를 ‘을’에게 양도하며 이에 대하여 ‘갑’은 승낙합니다.
    </li>
    <li class="c-text-list__item">본 약정은 ‘을’ 또는 ‘병’이 핸드폰의 할부매매, 할부금 채권의 양도 등 약정사항을 직접 확인하는 소정의 절차를 거쳐 ‘갑’에게 매매약정 승낙을 통지하는 시점에 성립합니다.</li>
    <li class="c-text-list__item">‘갑’의 연대보증인은 ‘갑’이 ‘을’, ‘병’과 약정한 본 매매 약정서의 모든 조항을 확인하고 본 약정에 의하여 발생하는 모든 채무를 ‘갑’과 연대하여 이행할 것을 확약합니다.</li>
  </ul>
  <h4 class="c-heading c-title--type3">제 2조 (매매약정 조건의 결정)</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">핸드폰 할부매매는 ‘갑’이 ‘을’의 서비스에 가입하는 조건으로 이루어지며 ‘갑’은 핸드폰만의 구매를 위하여 할부매매 방식을 이용할 수 없습니다.</li>
    <li class="c-text-list__item">할부매매에 따른 제반 수수료 및 소요비용은 ‘갑’이 부담하여야 합니다.</li>
    <li class="c-text-list__item">제2항의 규정에 따라 ‘갑’은 핸드폰 할부 구매시 할부금액 및 할부기간에 따른 할부 제반 수수료를 ‘을’에게 할부 종료시까지 매월 납부하여야 합니다.</li>
    <li class="c-text-list__item">‘을’은 할부매매와 관련하여 어떠한 의무사용기간, 해지, 제한기간 또는 명의변경 제한 등을 설정할 수 없으며,할부매매 기간 중에 서비스 이용을 해지하고자 하는 ‘갑’의 신청에 대해서는 반드시 응합니다.</li>
  </ul>
  <h4 class="c-heading c-title--type3">제 3조 (구매자의 의무)</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">‘갑’은 ‘병’과 약정한 구매조건에 따라 할부금을 ‘을’에게 납입하여야 하며, ‘갑’의 할부금 납입의무는 핸드폰의분실, 도난, 파손, 화재, 재해 및 기타의 사유로 인한 훼손 또는 멸실 시에도 계속됩니다.</li>
    <li class="c-text-list__item">
      ‘갑’은 할부금을 완납하기 전에 우편물 및 대금 청구주소, 할부금을 납입할 자동이체계좌, 신용카드 등이 변경되었을 때 지체없이 ‘을’에게 통보하여야합니다. 단, ‘갑’이 최후로 통보한 주소에 ‘을’이 발송한 송부서류는 ‘갑’에게 도달된 것으로
      간주합니다.
    </li>
    <li class="c-text-list__item">‘갑’은 제2항의 의무를 태만히 하여 ‘을’로부터 통지 및 송부 서류들을 접수 받지 못해 불이익을 받은 것에 대하여 이의를 제기할 수 없습니다.</li>
  </ul>
  <h4 class="c-heading c-title--type3">제 4조 (할부금의 납입방법)</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">
      ‘갑’은 ‘핸드폰 할부매매 약정’에 따라 발생한 ‘핸드폰 할부대금 및 할부수수료(이하 ‘할부금’이라 함)’를 ‘을’이 정하는 청구방법으로 납기일까지 납입하여야 합니다. ‘할부금’은 연 5.9%의 할부수수료가 부가되며, (할부대금+할부수수료)가 매월 분할
      청구됩니다.
    </li>
    <li class="c-text-list__item">‘갑’이 납입해야 할 할부금의 월 납입금을 ‘을’의 서비스에 가입한 다음 달부터 납입하여야 하며, ‘갑’은 할부기간의 최종 납입기한이 도래하기 전이라도 나머지 할부금을 일시에 납입할 수 있습니다.</li>
    <li class="c-text-list__item">할부금의 납입 중에 ‘갑’이 ‘을’의 서비스 제공을 일시정지하고자 할 경우도 ‘갑’은 월 납입금을 납부해야 합니다.</li>
    <li class="c-text-list__item">
      ‘갑’이 납입한 금액에 대하여 납입일 현재 ‘을’의 서비스 이용요금, 핸드폰 할부금 등을 포함한 채무전액을 충당하기에 부족한 경우 ‘을’은 ‘갑’이 납입한 금액에 대하여 납기일 기준으로 먼저 도래한 채무액부터 우선 충당합니다.
    </li>
    <li class="c-text-list__item">할부금이 제1항에서 정한 납기일까지 전액 납입되지 아니할 경우 ‘을’이 제공하는 이동전화 서비스의 이용이 제한될 수 있으며 기타 사유로 할부금 미납시 발생하는 손해는 ‘갑’의 부담으로 합니다.</li>
    <li class="c-text-list__item">‘을’의 청구금액이 이의가 있는 경우 ‘갑’은 ‘을’에게 즉시 그 취지를 알려 잘못이 있는 경우 정산을 하도록 합니다.</li>
  </ul>
  <h4 class="c-heading c-title--type3">제 5조 (지연손해금)</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">‘갑’이 약정 납기일까지 할부금을 납부하지 못한 경우 ‘을’은 연체한 금액의 월 2%에 해당하는 지연손해금을 부과합니다.</li>
  </ul>
  <h4 class="c-heading c-title--type3">제 6조 (지위양도 승낙)</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">‘갑’은 제1조 1항의 규정에 따라 본 약정에 의한 ‘을’의 권리청구에 응할 의무를 부담합니다.</li>
    <li class="c-text-list__item">할부기간 중에 ‘갑’이 핸드폰을 타인에게 양도하고자 하는 경우 즉시 ‘을’에게 통보하고 ‘을’의 동의하에 명의변경의 절차를 거쳐 소유권을 양도할 수 있습니다.</li>
    <li class="c-text-list__item">제2항의 규정에 의해 명의변경하고자 하는 때에 ‘갑’은 채권보전 적격자에게만 핸드폰 및 ‘을’이 제공하는 서비스 일체를 양도할 수 있으며, 모든 할부매매 약정조건은 명의변경의 양수인에게 승계됩니다.</li>
    <li class="c-text-list__item">‘갑’은 할부기간 중에 이동전화서비스를 제외한 할부매매계약만을 타인에게 양도할 수 없으며, ‘갑’이 명의변경 절차 없이 핸드폰을 타인에게 양도한 경우라도 할부금 납입의무는 양도할 수 없습니다.</li>
    <li class="c-text-list__item">
      ‘갑’이 핸드폰의 소유권을 타인에게 양도하고자 할 때 양수인(양수인의 연대보증인 포함)이 할부매매계약의 승계를 거부 하거나 양수인(양수인의 연대보증인 포함)이 신용상의 부적격 사유로 할부매매계약의 승계가 불가한 경우 명의변경 절차에 앞서
      잔여 할부금 전액을 ‘을’에게 일시 상환하여야 합니다.
    </li>
  </ul>
  <h4 class="c-heading c-title--type3">제 7조 (구매자의 철회권)</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">‘갑’은 본 약정서를 교부받은 날 또는 약정서를 교부받지 아니한 경우에는 핸드폰을 인도받은 날로부터 7일 이내에 할부매매 약정에 관한 청약을 철회할 수 있습니다.</li>
    <li class="c-text-list__item">
      ‘갑’이 제1항에 의하여 할부약정을 철회하고자 하는 때에는 제1항에서 정한 기간내에 ‘을’에게 철회의 의사표시가 기재된 서면을 발송하여야 하며, 청약의 철회는 서면을 ‘을’에게 발송한 날에 그 효력이 발생한 것으로 봅니다.
    </li>
    <li class="c-text-list__item">제1항의 규정에도 ‘갑’의 책임있는 사유로 핸드폰이 멸실 또는 훼손된 경우에는 할부약정을 철회할 수 없습니다.</li>
  </ul>
  <h4 class="c-heading c-title--type3">제 8조 (기한의 이익상실)</h4>
  <p class="c-text c-text--type3 u-mt--16">‘갑’은 다음 각 호에 해당하는 경우 할부금의 납입에 대한 기한의 이익을 상실하고, ‘을’은 나머지 할부금 전액에 대하여 ‘갑’에게 일시에 납입하도록 청구할 수 있습니다.</p>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">‘갑’이 납입금의 납입을 연속하여 2회 이상 연체하고, 그 연체금액이 할부금액의 10의 1을 초과하는 경우</li>
    <li class="c-text-list__item">‘갑’이 생업에 종사하기 위하여 외국에 이주하거나 외국인과 결혼 및 연고 관계로 이주하는 경우</li>
  </ul>
  <h4 class="c-heading c-title--type3">제 9조 (약정의 해제)</h4>
  <p class="c-text c-text--type3 u-mt--16">
    ‘갑’이 할부금 납입의 의무를 이행하지 않아 ‘을’이 14일 이상의 기간을 정하여 서면으로 최고하였음에도 납입하지 않을 경우에 ‘을’은 본 약정을 해제할 수 있습니다. 이 경우 ‘갑’은 할부거래에 관한 법률 제9조에 따른 손해배상 책임을 부담합니다.
  </p>
  <h4 class="c-heading c-title--type3">제 10조 (이동전화서비스 계약 해지)</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">할부기간 중에 ‘을’의 서비스 이용을 해지한 ‘갑’은 잔여 할부금에 대하여 잔여 할부기간동안 분할 납입하거나 일시 납입하는 방법 중 하나를 선택할 수 있습니다.</li>
    <li class="c-text-list__item">제1항의 규정에 따라 핸드폰의 소유권은 ‘갑’에게 귀속됩니다. 다만, ‘갑’은 할부대금을 완납하기 전에는 ‘을’의 승낙 없이 핸드폰을 타인에게 양도, 대여, 질권설정 등 임의처분을 할 수 없습니다.</li>
  </ul>
  <h4 class="c-heading c-title--type3">제 11조 (신용보험 가입 및 지급보증계약 체결)</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">
      ‘을’은 ‘갑’에 대한 할부금(요금) 채권보전을 위하여 보증보험사의 신용보험에 가입하거나, ‘갑’을 대리하여 캐피탈사와 핸드폰할부대금 지급보증계약을 채결할 수 있습니다. ‘갑’의 연대보증인에 대한 신용보험 가입 및 지급보증계약 체결은 연대보증
      채무를 주 채무로 하고 주채무자는 ‘갑’의 연대보증인으로 하여 체결합니다.
    </li>
    <li class="c-text-list__item">‘갑’은 상기 1항 지급보증계약 체결에 필요한 모든 권한을 ‘을’에게 완전하게 양도함에 동의합니다.</li>
    <li class="c-text-list__item">‘갑’은 할부수수료를 할부 종료 시까지 매월 ‘을’에게 납부하여야 한다.</li>
    <li class="c-text-list__item">
      제1항의 경우 ‘갑’이 ‘을’에 대하여 할부금(요금) 납입의무를 이행하지 아니한 경우에 ‘을’은 ‘갑’의 납입하지 않은 할부금(요금)을 보증보헙사 또는 캐피탈사에 청구하여 보상받게 되며, ‘갑’의 미납할부금(미납요금)을 대납한 보증보험사 또는
      캐피탈사는 ‘갑’에게 대납금액[미납할부금(미납요금)]과 연체이자(연 6%)를 합한 금액을 청구하게 됩니다.
    </li>
    <li class="c-text-list__item">‘갑’이 계속하여 채무 이행을 하지 아니할 경우 신용보험 가입 및 지급보증 계약 여부와 상관없이 은행권 등 각 금융기관에 채무연체로 통보되어 금융상의 불이익을 받게 됩니다.</li>
  </ul>
  <h4 class="c-heading c-title--type3">제 12 조 (관할법원)</h4>
  <p class="c-text c-text--type3 u-mt--16">본 약정에 관하여”갑”,”을”,”병” 사이의 소송이 필요가 발생할 때에는 법이 정하는 관할법원 또는 “을” 의 본사를 주소지로 하는 관할법원을 합의 법원으로 합니다.</p>
  <h4 class="c-heading c-title--type3">제 13 조 (해석)</h4>
  <p class="c-text c-text--type3 u-mt--16">이 약정에 규정되어 있지 아니한 사항에 관하여는 관계법령, “을” 의 이동전화 서비스 이용약관에 따르기로 합니다.</p>
  <h3 class="c-heading c-title--type2">
    <span class="u-co-mint">[요금지급보증 약정안]</span>
  </h3>
  <h4 class="c-heading c-title--type3">제 1 조(지급보증 계약체결)</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">
      ‘을’은 ‘갑’의 통신요금 미납에 따른 채권보전을 위하여 약관에 신고된 바에 따라 캐피탈사와 요금지급 보증계약을 ‘갑’을 대리하여 체결할 수 있습니다. ‘갑’은 지급보증료(개인2만원, 법인4만원)를 본 계약 체결시점에 ‘을’에게 납부하여야 합니다.
    </li>
    <li class="c-text-list__item">핸드폰 할부매매 약정안 제11조 2항, 4항, 5항의 사항은 본 약정에도 동일하게 적용됩니다.</li>
  </ul>
  <h4 class="c-heading c-title--type3">제 2 조 (기타)</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">이 약정에 규정되어 있지 아니한 사항에 관하여는 관계법령, ‘을’의 이동전화 서비스이용약관에 따르기로 합니다.</li>
  </ul>
  <h3 class="c-heading c-title--type2">(고지)<span class="u-co-mint">미성년고객 이용안내</span></h3>
  <h4 class="c-heading c-title--type3">이용안내</h4>
  <ul class="c-text-list c-bullet c-bullet--number">
    <li class="c-text-list__item">M mobile 서비스를 이용하는 미성년고객님의 미납요금채무(미납 또는 핸드폰 할부금 포함) 발생시 법정대리인은 상기 미납요금(미납 핸드폰할부금 포함)에 대한 상환채무를 연대하여 보증합니다.</li>
    <li class="c-text-list__item">미납 핸드폰할부금에 대한 연대보증채무를 담보하기 위하여 회사는 법정대리인을 주채무자로 하여 보증보험에 가입합니다.</li>
    <li class="c-text-list__item">
      M mobile 서비스를 이용중인 미성년고객님이 핸드폰 할부금을 납부하지 않을 경우 보증보험사는 kt M mobile에 대신 변제를 하고 법정대리인에게 대위권(회사에 변제한 미납 핸드폰할부금을 법정대리인에게 청구하는 행위)을 행사합니다.
    </li>
    <li class="c-text-list__item">보증보험사는 미납 핸드폰할부금으로 대위 변제한 금액에 대하여 법정대리인이 보증보험사에 변제하지 않을 경우 은행 등 각 금융기관에 할부채무연체로 통보합니다.</li>
    <li class="c-text-list__item">
      kt M mobile은 미납요금(핸드폰할부금 포함)을 통지하는데 있어서 법정대리인에 대한 통지는 kt M mobile 서비스를 이용중인 미성년고객님에 대한 통지로 갈음합니다.
      <ul class="c-text-list c-bullet c-bullet--dot">
        <li class="c-text-list__item">무선인터넷 차단서비스는 차단서비스 적용 요금제 가입자가 별도 가입신청하여 이용할 수 있습니다.</li>
        <li class="c-text-list__item">법정대리인의 별도 해제 요청이 있어야만 무선인터넷 차단서비스를 해제할 수 있습니다.</li>
        <li class="c-text-list__item">성년이 되어 타 요금제로 전환될 경우 차단서비스는 자동해제 됩니다.</li>
      </ul>
    </li>
    <li class="c-text-list__item">
      수신자부담전화 차단서비스 이용안내
      <ul class="c-text-list c-bullet c-bullet--dot">
        <li class="c-text-list__item">대표번호 : 1644-1739(LG유플러스, SK텔레콤, SK텔링크, SK브로드밴드, 세종 적용) 1566-0011(sk텔레콤 수신자부담전화 : 1541)</li>
        <li class="c-text-list__item">단, 고객님께서 상기 대표번호로 직접 신청하셔야 하며, 발신요금은 유료입니다.</li>
        <li class="c-text-list__item">신청일 이후 영업일 기준 2일 이내에 적용됩니다. 신청 후 반드시 적용 여부 확인 부탁드립니다.</li>
      </ul>
    </li>
    <h3 class="c-heading c-title--type2">(고지)<span class="u-co-mint">청소년 보호를 위한 이용약관[12장 청소년 보호 등]</span></h3>
    <h4 class="c-heading c-title--type3">제 53조(청소년 이용계약)</h4>
    <ul class="c-text-list c-bullet c-bullet--number">
      <li class="c-text-list__item">만 18 세 이하의 ‘청소년’이용자는 보호자(민법상 법정대리인)의 동의를 득하여야만 이용신청을 할 수 있으며, 회사는 보호자의 동의 여부를 확인하기 위해 별도 구비서류 제출을 요구할 수 있습니다.</li>
      <li class="c-text-list__item">회사는 만 4 세 미만 영유아의 명의로 신청하는 경우 이용신청을 승낙하지 아니합니다.(단, 어린이의 안전 등을 위해 회사가 정한 특정 상품은 예외적으로 이용신청을 승낙합니다)</li>
      <li class="c-text-list__item">회사는 청소년 및 보호자와의 계약 체결 시 청소년 전용 이용계약서를 사용할 수 있습니다.</li>
      <li class="c-text-list__item">회사는 청소년 보호 관련 본 약관의 주요 부분, 청소년 요금제, 성인물 컨텐츠 등 유해매체물 차단 신청 등에 대한 내용을 청소년 이용계약서에 명시합니다.</li>
    </ul>
    <h4 class="c-heading c-title--type3">제 54조(청소년 보호)</h4>
    <ul class="c-text-list c-bullet c-bullet--number">
      <li class="c-text-list__item">회사는 청소년이 이동통신을 이용하여 청소년 유해컨텐츠에 접근할 수 없도록 노력을 해야 합니다.</li>
      <li class="c-text-list__item">회사는 청소년이 이용하는 이동전화는 청소년 명의로 신청할 것을 권고합니다.</li>
      <li class="c-text-list__item">청소년 명의로 가입된 단말기는 무선인터넷의 성인물 등 청소년 유해컨텐츠에 접근을 원천적으로 차단할 수 있습니다.</li>
      <li class="c-text-list__item">
        회사는 청소년 보호를 위해 보호자가 원할 경우 다음 각호의 서비스를 제공합니다.
        <ul class="c-text-list c-bullet c-bullet--dot">
          <li class="c-text-list__item">무선인터넷 차단서비스</li>
          <li class="c-text-list__item">휴대폰 소액결제 차단서비스</li>
          <li class="c-text-list__item">무선인터넷 이용요금 통보서비스(보호자가 당사 가입자일 경우에 한함)</li>
          <li class="c-text-list__item">수신자 부담서비스 차단서비스</li>
        </ul>
      </li>
    </ul>
    <h4 class="c-heading c-title--type3">제 55조(청소년 이용계약 해지)</h4>
    <ul class="c-text-list c-bullet c-bullet--number">
      <li class="c-text-list__item">
        회사는 다음 각호에 해당하는 청소년 이용계약을 체결한 경우에는 그 이해 당사자가 해지를 요구할 경우 이에 반드시 응해야 하며, 기 납부한 요금(가입비, 보증금 또는 보증보험료)을 환불하고 미납요금 및 잔여 위약금에 대하여 청구행위를 할 수
        없습니다.
        <ul class="c-text-list c-bullet c-bullet--dot">
          <li class="c-text-list__item">청소년 가입 시 보호자의 동의(동의서 및 인감증명서 등의 구비서류)를 받지 아니하고 체결한 이용계약</li>
          <li class="c-text-list__item">청소년이 타인(부모, 친인척, 지인관계 등)의 명의를 도용하여 체결한 이용계약</li>
        </ul>
      </li>
    </ul>
  </ul>
    `,
  },
  policy08: {
    title: '개인정보 제3자제공 동의((주)밀리의 서재 요금제 가입고객 필수 동의)',
    content: `
    <div class="c-table c-table--x-scroll u-mt--16">
  <table>
   <caption>제공받는 자, 제공항목, 제공목적, 보유 및 이용기간 정보를 포함한 표</caption>
   <colgroup>
    <col style="width: 180px;">
    <col style="width: 120px;">    
    <col style="width: 200px;">
    <col style="width: 200px;">
    <col style="width: 150px;">
   </colgroup>
   <thead>
    <tr>
     <th scope="col">제공받는 자</th>
     <th scope="col">제공하는 자</th>
     <th scope="col">이용목적</th>       
     <th scope="col">제공하는 항목</th>
     <th scope="col">보유기간</th>
    </tr>
   </thead>
   <tbody>
    <tr>
     <td class="u-ta-left">(주)밀리의 서재(밀리의 서재)</td>
     <td class="u-ta-left">kt M mobile, kt</td>       
     <td class="u-ta-left">MVNO 제휴요금제 가입에 따른 혜택 제공</td>
     <td class="u-ta-left">성명, 이동전화번호, 요금제 정보, 부가상품 정보</td>
     <td class="u-ta-left">서비스 가입기간</td>
    </tr>
    <tr>
     <td class="u-ta-left" colspan="5">
      ※ 위와 같이 개인정보·신용정보 제 3자 제공에 거부할 권리가 있습니다. 그러나 동의를 거부할 경우 서비스 이용이 제한될 수 있습니다.<br>
      ※ 개인정보는 해당 요금제 가입에 따른 혜택 제공 대상 위탁사에 한해 제공됩니다.
     </td>
    </tr>
    <tr>
     <td class="u-ta-left" colspan="5">제휴 요금제에 기본으로 제공되는 제휴 서비스에 대한 안내를 위해 서비스를 미사용중인 고객님 대상으로 안내 문자가 주기적으로 발송될 수 있습니다.</td>
    </tr>
   </tbody>
  </table>
 </div>
    `,
  },
  policy09: {
    title: '고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의',
    content: `
    <div class="c-table c-table--x-scroll u-mt--16 u-pr--0">
		<table>
			<caption>수집·이용 목적, 수집 항목, 보유기간 정보를 포함한 표</caption>
			<colgroup>
				<col style="width: 300px;">
				<col style="width: 300px;">
				<col style="width: 150px;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">이용 목적</th>
					<th scope="col">수집 항목</th>
					<th scope="col">보유기간</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="u-ta-left">
						kt M mobile 통신 서비스와 kt M mobile이 제공하는 다른 상품 서비스간 개인정보의 결합 분석 및 이를 통한 개인맞춤 연계 서비스제공<br>
						kt M mobile 및 제 3자(KT, KT그룹사, 협력사, 제휴사 등)의 상품 서비스 혜택(통신,금융,교육,커머스 등)에 대한 개인 맞춤 추천, 정보 제공과 안내, 할인제도와 소개 가입의사 확인, 리텐션, 이벤트 안내 등 마케팅<br>
						신규 서비스 개발, 서비스 개선<br>
						고객 세분화, 선호도 라이프스타일 사회적관계 추정, 우량 등급 고객 선정<br>
						상기 목적을 위한 개인정보 분석
					</td>
					<td class="u-ta-left">
						위 개인정보 수집ㆍ이용 동의(필수동의)대상 개인정보 항목<br>
						위 개인(신용)정보 조회 및 이용/제공에 대한 동의(필수동의) 대상 항목<br>
						서비스 이용기록, Application 사용 정보, 접속IP주소(도메인 및 URL 포함), 통신과금서비스 거래정보(결제정보, 사용처, 상품정보, 금액 등), 구글 GAID 혹은 애플 IDFA 및 이를 조합하여 생성된 정보<br>
						고객 본인 명의로 가입한 kt M mobile 제공 서비스(이동전화, 부가서비스 등)신청 시 수집 및 이용에 동의한 모든 항목<br>
						상기 본 항의 정보들을 조합하여 생성된 정보
					</td>
					<td class="u-ta-left">
						서비스 이용기간 동안
					</td>
				</tr>
				<tr>
					<td class="u-ta-left" colspan="3">
						본인은 상기 내용과 같이 귀사가 본인의 개인정보ㆍ신용정보ㆍ위치정보를 수집 이용함에 동의합니다. (동의를 거부할 수 있으며, 거부에 따른 불이익은 없습니다.)<br>
						단, 주민등록번호는 가입의사확인, 명의도용 방지, 미환급금 반환, 복지할인, 본인확인서비스, 단말기 분할상환 대금채권 등을 기초 자산으로 한 자산유동화거래 목적과 같이 법적 근거가 있는 경우만 제한적으로 이용합니다.<br>
						구글 GAID, 애플 IDFA 수집 거부 방법 안내<br>
						-Android인 경우 [설정(일반) → Google → 광고] 또는 [설정 → 개인정보보호 → 광고 설정]<br>
						-IOS인 경우[설정 → 개인정보 → 광고]
					</td>
				</tr>
			</tbody>
		</table>
	</div>
    `,
  },
  policy10: {
    title: '개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의',
    content: `
    <div class="c-table c-table--x-scroll u-mt--16 u-pr--0">
		<table>
			<caption>개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의 정보를 포함한 표</caption>
			<colgroup>
				<col>
			</colgroup>
			<tbody>
				<tr>
					<td class="u-ta-left">
						본인이 수집 및 이용에 동의한 개인정보ㆍ신용정보ㆍ위치정보를 활용하여, kt M mobile 및 제3자의 상품 및 서비스에 대한 광고ㆍ홍보ㆍ프로모션ㆍ이벤트 제공 및 고객관리와 관련한 개인정보 처리 업무를 kt M mobile 의 수탁사(KTIS/KTCS 등 상담채널, KT SERVICE, KT Engineering, KT MOS 등 개통/AS 법인, 유통점, ㈜케이티알파 등)에 위탁하는 것과 전자적 전송매체(전화, 우편, SMS, LMS, MMS, APP PUSH, 팩스, 스마트마케팅플랫폼 등 포함)를 통해 정보/광고를 전송하는 데 동의합니다<br><br>
						※ 본 동의를 거부할 시, kt M mobile 요금 할인, 제휴 상품 할인 정보 등 고객 맞춤형 혜택 정보를 수신할 수 없습니다.<br>
						※ 본 동의를 거부할 수 있으며, 거부에 따른 불이익은 없습니다. 동의를 철회하고자 하는 경우 고객센터(국번 없이 1899-5000(타사 유료)) 및 회사의 홈페이지(<a href="http://www.ktmmobile.com)를">www.ktmmobile.com)를</a> 통해 철회할 수 있습니다.<br>
						※ 회사의 서비스 제공 등 계약의 이행에 필요한 경우는 해당 위탁업무와 수탁자(증감, 변동 가능)를 회사의 홈페이지(<a href="http://www.ktmmobile.com)의">www.ktmmobile.com)의</a> ‘개인정보처리방침’에 공개함으로써, 처리위탁 동의에 갈음합니다.
					</td>
				</tr>
			</tbody>
		</table>
	</div>
    `,
  },
  policy11: {
    title: '혜택 제공을 위한 제3자 제공 동의',
    content: `
    <div class="c-table c-table--x-scroll u-mt--16 u-pr--0">
		<table>
			<caption>제공하는 자, 제공받는 자, 제공목적, 제공하는 항목, 이용기간 정보를 포함한 표</caption>
			<colgroup>
				<col style="width: 60px;">
				<col style="width: 120px;">
				<col style="width: 180px;">
				<col style="width: 250px;">
				<col style="width: 250px;">
				<col style="width: 150px;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">동의</th>
					<th scope="col">제공하는 자</th>
					<th scope="col">제공받는 자</th>
					<th scope="col">제공목적</th>
					<th scope="col">제공하는 항목</th>
					<th scope="col">이용기간</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="u-ta-center">
						<input class="c-checkbox c-checkbox--type2 c-checkbox--type-td agreeCheck" id="othersTrnsAgree" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeClick(this)" required-agree-id="agreeWrap5">
						<label class="c-label" for="othersTrnsAgree"> </label>
					</td>
					<td class="u-ta-left">kt M mobile</td>
					<td class="u-ta-left">(주)KT 및 KT그룹사(ktis, kt service, Bccard, Smartro, K bank, (주)브이피(후후), kt skylife, kt alpha, GENIE MUSIC, kt sports, Storywiz, kt estate, kt telecop, kt cs)</td>
					<td class="u-ta-left">
						제공받는자의 상품·서비스(금융·여행상품 포함)안내 및 이용권유<br>
						개인맞춤형 부가혜택 (이벤트,쿠폰,할인,경품제공,포인트 적립 등)제공 및 안내<br>
						이를 위한 고객분석 및 제3자 제공·정보·제공 받는자 보유 정보 간 결합분석과 상품· 서비스 연구개발<br>
						제공받는자의 마이데이터 사업 소개 및 이용권유<br>
					</td>
					<td class="u-ta-left">성명, 본인확인식별에 필요한 정보, 생년월일, 내/외국인여부, 성별, 휴대폰번호, 청구지정보, 이메일, 전화번호, 계좌(카드)정보, 대리인 정보(성별, 신청인과의 관계 등), 서비스이용 또는 업무처리과정에서 생성되어 수집되는 정보(휴대폰번호, 개통일자, 단말정보, USIM정보 등), 선택정보(부가서비스 이용과 관련된 정보 등)</td>
					<td class="u-ta-left">서비스 이용기간 동안</td>
				</tr>
				<tr>
					<td class="u-ta-center">
						<input class="c-checkbox c-checkbox--type2 c-checkbox--type-td agreeCheck" id="othersTrnsKtAgree" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeClick(this)" required-agree-id="agreeWrap5">
						<label class="c-label" for="othersTrnsKtAgree"> </label>
					</td>
					<td class="u-ta-left">KT</td>
					<td class="u-ta-left">kt M mobile</td>
					<td class="u-ta-left">KT 서비스 정보와의 결합·분석 정보 등 제공항목 정보의 재제공</td>
					<td class="u-ta-left">KT 상품·서비스 가입 정보</td>
					<td class="u-ta-left">서비스 이용기간 동안</td>
				</tr>
			</tbody>
		</table>
	</div>
    `,
  },
  policy12: {
    title: '제3자 제공관련 광고 수신 동의',
    content: `
    <div class="c-table c-table--x-scroll u-mt--16 u-pr--0">
		<table>
			<caption>제3자 제공관련 광고 수신 동의 정보를 포함한 표</caption>
			<colgroup>
				<col>
			</colgroup>
			<tbody>
				<tr>
					<td class="u-ta-left">
						본인은 (주)KT 및 KT그룹사(ktis, kt service, Bccard, Smartro, K bank, (주)브이피(후후), kt skylife, kt alpha, GENIE MUSIC, kt sports, Storywiz, kt estate, kt telecop, kt cs)가 본인의 정보(수집 또는 제공받은 정보)를 이용하여 본인에게 (주)KT 및 KT그룹사(상기 동일)의 상품, 서비스 혜택, 이벤트에 대한 정보, 광고를 전자적 전송매체를 포함한 각종 통신 방법(전화, 문자, 이메일, DM 등)으로 전송하는 것에 동의합니다<br><br>
						※ 본 동의서에 동의를 체크하지 않더라도 기존 개인정보처리 동의 의사 내용은 유지됩니다.<br>
						※ 본 동의를 거부할 시 동의를 통해 제공 가능한 각종 우대서비스 혜택, 경품 및 이벤트 안내를 받아 보실 수 없습니다.<br>
						※ 본 동의를 거부할 수 있으며 거부에 따른 불이익은 없습니다. 동의를 철회하고자 하는 경우 고객센터(국번 없이 1899-5000(타사 유료))를 통해 상담원으로부터 철회할 수 있습니다.
					</td>
				</tr>
			</tbody>
		</table>
	</div>
    `,
  },
  policy13: {
    title: '개인위치정보 제3자 제공 동의',
    content: `
    <div class="c-table c-table--x-scroll u-mt--16 u-pr--0">
		<table>
			<caption>제공받는 자, 제공항목, 제공목적, 보유기간 정보를 포함한 표</caption>
			<colgroup>
				<col style="width: 120px;">
				<col style="width: 380px;">
				<col style="width: 180px;">
				<col style="width: 180px;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">제공받는 자</th>
					<th scope="col">제공항목</th>
					<th scope="col">제공목적</th>
					<th scope="col">보유기간 </th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="u-ta-center">(주)KT</td>
					<td class="u-ta-left">개인위치정보(기지국, GPS 등 LBS플랫폼 수집 정보), 연령, 성별</td>
					<td class="u-ta-left">인구통계학적 분석</td>
					<td class="u-ta-left">서비스 이용기간 동안</td>
				</tr>
				<tr>
					<td class="u-ta-left" colspan="4">
						※ 위와 같이 개인정보·신용정보 제 3자 제공에 거부할 권리가 있습니다. 그러나 동의를 거부할 경우 서비스 이용이 제한될 수 있습니다.(거부에 따른 불이익은 없습니다.)
					</td>
				</tr>
			</tbody>
		</table>
	</div>
    `,
  },
  policy14: {
    title: '청소년 유해정보차단 APP 설치 동의',
    content: `
      <h4 class="c-heading c-title--type3">청소년 유해정보 차단 앱(엑스키퍼가드, 무료)</h4>
      <ul class="c-text-list c-bullet c-bullet--dot">
        <li class="c-text-list__item">
          유해 웹사이트 및 유해 앱 접속 시 필터링 앱을 통해 이를 차단해주는 서비스(어플리케이션 별도 설치가 필요)
          <ul class="c-text-list c-bullet c-bullet--fyr">
            <li class="c-text-list__item">전기통신사업법 제32조의7 청소년 유해매체물 등의 차단에 의거, 가입신청일 기준으로 만 19세 미만은 의무적으로 청소년 유해정보 차단에 가입해야 합니다.</li>
          </ul>
        </li>
      </ul>
    `,
  },
  policy15: {
    title: '청소년 유해정보 네트워크차단 동의',
    content: `
      <h4 class="c-heading c-title--type3">네트워크차단</h4>
      <ul class="c-text-list c-bullet c-bullet--dot">
        <li class="c-text-list__item">이동통신망(3G,LTE) 을 사용하여 유해웹사이트 접속과 유해App. 다운로드 시 자동으로 차단해주는 무료서비스</li>
      </ul>
    `,
  },
}
</script>

<style scoped>
/* 약관의 스타일을 넣는다. */
</style>
