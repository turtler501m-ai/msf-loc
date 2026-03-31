---
name: ktm-smartform
description: 스마트서식지(mform) 프로젝트 개발 가이드 — Vue 3 + Spring Boot 2.7 구조, M플랫폼 연동 패턴, 개발 현황 포함. msf-api / msf-web 작업 시 자동 참조.
---

# 스마트서식지(mform) 프로젝트 개발 스킬 가이드

작성 기준: 현재 소스 분석(2026.03.25) — mform-web(Vue 3) + mform-api(Spring Boot 2.7)

개발 완료/미완성 현황은 `dev-status.md`, SOC 코드 레퍼런스는 `soc-reference.md`, **테이블 접두어·DB 출처는 `db-table-guide.md`** 참조.

---

## 1. 기술 스택 전체 요약

### 1.1 프론트엔드 (mform-web)

| 분류 | 기술 | 버전 | 용도 |
|------|------|------|------|
| 언어 | JavaScript (TypeScript 미사용) | ES2022+ | 전체 코드 |
| 프레임워크 | Vue 3 (Composition API) | 3.5.x | SPA 구성 |
| 빌드 | Vite | 7.x | 개발서버 + 번들링 |
| 상태관리 | Pinia | 3.x | 폼 상태·전역 상태 |
| 라우터 | Vue Router | 5.x | 화면 전환 |
| CSS | Tailwind CSS | 4.x | 유틸리티 스타일 |
| UI 컴포넌트 | reka-ui | 2.x | Dialog / AlertDialog |
| 아이콘 | lucide-vue-next, FontAwesome | 7.x | 아이콘 |
| 유틸 | @vueuse/core | 14.x | 컴포저블 유틸 |
| Node 요구사항 | Node.js | ^20.19.0 또는 ≥22.12.0 | - |

### 1.2 백엔드 (mform-api)

| 분류 | 기술 | 버전 | 용도 |
|------|------|------|------|
| 언어 | Java | 11 | 전체 코드 |
| 프레임워크 | Spring Boot | 2.7.18 | REST API 서버 |
| ORM | MyBatis | 2.3.2 | DB 매퍼 (XML) |
| DB (로컬) | PostgreSQL | - | msp_juo_sub_info 조회 |
| DB (운영) | Oracle | - | M전산 @DL_MSP 연동 |
| 빌드 | Maven | - | 의존성 관리 |
| 포트 | 8081 | - | API 서버 |
| 프론트 프록시 | Vite proxy | - | /api → localhost:8081 |

---

## 2. 프론트엔드 핵심 스킬

### 2.1 Vue 3 Composition API

현재 코드 전체가 `<script setup>` 방식으로 작성됨.

```js
// 기본 패턴 — ChangeTypeCust.vue, ChangeProd.vue 등 모든 컴포넌트
import { ref, computed, watch, onMounted } from 'vue'

const form = ref({ name: '', phone: '' })            // 반응형 데이터
const isValid = computed(() => !!form.value.name)    // 파생값
watch(form, (v) => store.setCustomerForm(v), { deep: true }) // 감시
onMounted(async () => { await loadData() })           // 진입 시 조회

// defineEmits / defineExpose — 부모-자식 통신
const emit = defineEmits(['update:complete'])
defineExpose({ validate, save })  // McpStepService에서 호출
```

필수 숙지 패턴:
- `defineModel` — `v-model:open`으로 팝업 open 상태 동기화 (McpAdditionEditPop 등)
- `defineEmits + defineExpose` — 스텝 컴포넌트 유효성 검사 구조
- `<Teleport to="body">` — 팝업을 body로 렌더링 (ChangeAgree.vue 확인모달)

### 2.2 Pinia 스토어 구조

서식지 3종 신청서마다 별도 스토어 존재.

```js
// stores/service_change_form.js
export const useServiceChangeFormStore = defineStore('serviceChangeForm', {
  state: () => ({
    selectedOptions: [],    // 서비스 선택
    customerForm: { ... },  // 고객 정보
    productForm: { ... },   // 상품 정보
    agreeForm: {},          // 동의 정보
  }),
  actions: {
    setCustomerForm(form) { ... },
    setProductForm(form) { ... },
    reset() { ... },  // 신청 완료 후 초기화
  }
})
// 동일 구조: stores/ident_form.js (명의변경), stores/cancel_form.js (서비스해지)
```

주요 패턴:
- 각 단계 컴포넌트에서 `formStore.setXxxForm()`으로 값 저장
- 다음 단계 이동 시 스토어 데이터를 API 요청에 활용
- `formStore.reset()` — 신청 완료 후 상태 초기화

### 2.3 라우터 구조

```js
// router/index.js — 핵심 경로
{ path: 'mobile/:domain/:service', name: 'service-detail' }
// 예시: /mobile/change/ChangeTypeCust
//       /mobile/ident/IdentTypeCust
//       /mobile/cancel/CancelCust

{ path: 'mobile/complete/:domain', name: 'service-complete' }
// 신청 완료: /mobile/complete/change
```

`mcp_components.js`에서 `domain+service` 파라미터로 컴포넌트를 동적 로드.

### 2.4 API 호출 패턴

```js
// api/msf.js — 공통 fetch 래퍼
export function msfPost(path, body) { ... }
export function msfGet(path) { ... }

// api/serviceChange.js 사용 예
import { getCurrentAddition } from '@/api/serviceChange'

const data = await getCurrentAddition({ ncn, ctn, custId })
// 응답: { items, availableItems, wirelessBlockInUse, infoLimitAmount }
```

에러 처리 규칙:
- `try/catch` 필수
- 백엔드 응답 `success: false` 또는 `RESULT_CODE: 'E'` 체크
- `alert` 대신 인라인 에러 메시지 권장

### 2.5 Tailwind CSS 4 스타일 작성법

```html
<!-- 기존 스타일 패턴 (form-row 구조) -->
<div class="flex items-start gap-4">
  <span class="w-[140px] shrink-0 text-sm font-medium text-gray-700 pt-2">라벨</span>
  <div class="flex-1 min-w-0">입력 영역</div>
</div>

<!-- scoped style에서 @reference 사용 -->
<style scoped>
@reference "tailwindcss";
.form-row { @apply flex items-start gap-4; }
</style>
```

### 2.6 reka-ui Dialog 팝업 패턴

```vue
<!-- McpAdditionEditPop.vue 등 팝업 컴포넌트 구조 -->
<Dialog v-model:open="isOpen" :modal="true">
  <DialogContent>
    <DialogHeader><DialogTitle>제목</DialogTitle></DialogHeader>
    <!-- 내용 -->
    <DialogFooter>
      <DialogClose as-child><Button variant="outline">취소</Button></DialogClose>
      <Button @click="confirm">확인</Button>
    </DialogFooter>
  </DialogContent>
</Dialog>

<script setup>
const isOpen = defineModel('open', { type: Boolean, default: false })
const emit = defineEmits(['confirm'])
</script>
```

---

## 3. 백엔드 핵심 스킬

### 3.1 Spring Boot 2.7 REST Controller 패턴

```java
// 현재 코드 패턴 — SvcChgRegController.java
@RestController
@RequestMapping("/api/v1")
public class SvcChgRegController {

    private final SvcChgRegSvc regSvcService;

    // 생성자 주입 (현재 전체 코드가 이 방식 사용)
    public SvcChgRegController(SvcChgRegSvc regSvcService) {
        this.regSvcService = regSvcService;
    }

    @PostMapping("/addition/current")
    public ResponseEntity<AdditionCurrentResVO> additionCurrent(
            @RequestBody(required = false) SvcChgInfoDto searchVO) {
        AdditionCurrentResVO res = regSvcService.selectAdditionCurrent(searchVO);
        return ResponseEntity.ok(res);
    }
}
```

응답 규격 통일 (현재 프로젝트 기준):

```java
// 성공/실패 공통 필드 (Map 반환 시)
rtnMap.put("success", true/false);      // Boolean
rtnMap.put("resultCode", "00"/"E");     // 결과코드
rtnMap.put("message", "...");           // 메시지
rtnMap.put("globalNo", "...");          // M플랫폼 전역번호

// VO 반환 시 → isSuccess(), getSvcMsg(), getGlobalNo() 활용
```

### 3.2 MyBatis XML Mapper 패턴

```xml
<!-- mapper/ContractInfoMapper.xml — 현재 유일한 DB 조회 -->
<select id="selectContractInfo" parameterType="map"
        resultType="com.ktmmobile.msf.formComm.dto.ContractInfoDto">
    SELECT contract_num AS ncn, customer_id AS custId
    FROM msp_juo_sub_info
    WHERE sub_status &lt;&gt; 'C'
      AND TRIM(subscriber_no) = TRIM(#{mobileNo})
      AND UPPER(REPLACE(COALESCE(sub_link_name,''),' ',''))
        = UPPER(REPLACE(COALESCE(#{name},''),' ',''))
</select>
```

```java
// mapper/ContractInfoMapper.java
@Mapper
public interface ContractInfoMapper {
    ContractInfoDto selectContractInfo(Map<String, String> params);
}
```

### 3.3 M플랫폼 연동 패턴

**가장 중요한 구조 — 신규 API 추가 시 항상 이 패턴 따라야 함.**

```java
// MplatFormSvc.java — 모든 M플랫폼 연동의 진입점
public MpAddSvcInfoDto getAddSvcInfoDto(String ncn, String ctn, String custId) {
    MpAddSvcInfoDto vo = new MpAddSvcInfoDto();
    HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X20");
    if ("LOCAL".equals(serverLocation)) {
        getVo(20, vo);          // ← LOCAL: Mock XML 응답
    } else {
        mplatFormSvc.callService(param, vo);  // ← 실서버: juice.url 호출
    }
    return vo;
}

// getVo(int eventCode, CommonXmlVO vo) — Mock 응답 등록
private void getVo(int param, CommonXmlVO vo) {
    String responseXml = "";
    switch(param) {
        case 20: responseXml = "<return>...X20 Mock XML...</return>"; break;
        case 21: responseXml = "<return>...X21 Mock XML...</return>"; break;
        // 신규 이벤트 추가 시 여기에 case 추가
    }
    XmlParseUtil.parse(responseXml, vo);
}
```

신규 M플랫폼 API 추가 절차:
1. `MplatFormSvc`에 상수 추가 (`APP_EVENT_CD_XXX`)
2. `public` 메서드 추가 (`getParamMap` + LOCAL분기 + `callService`)
3. `getVo()`에 `case` 추가 (Mock XML)
4. VO 클래스 생성 (`extends CommonXmlVO`, `parse()` 오버라이드)

### 3.4 SvcChgInfoDto 계층구조

```java
// 핵심 요청 DTO — 모든 서비스변경 API의 기반
public class SvcChgInfoDto {
    private String name;        // 고객명
    private String ncn;         // 계약번호 9자리
    private String ctn;         // 휴대폰번호 11자리
    private String custId;      // 고객ID
    private String contractNum; // 계약번호(M전산)
}

// 확장 패턴
public class AdditionRegReqDto extends SvcChgInfoDto {
    private String soc;          // SOC 코드
    private String ftrNewParam;  // 부가파람
}
```

---

## 4. 로컬 개발 환경 설정

### 4.1 mform-api 실행

```bash
# PostgreSQL DB 생성
createdb mform

# 테이블 생성 (ktm/doc/tabdoc/kcf_tab_create1.sql 실행)
psql -d mform -f kcf_tab_create1.sql

# application.properties 로컬 설정
DB_PASSWORD=your_password
# 또는 환경변수: export DB_PASSWORD=postgres

# DB 없이 Mock으로 개발 시 (인증 항상 성공)
# application.properties에 추가:
# mform.join-info.mock-when-no-db=true
# SERVER_NAME=LOCAL  ← 이미 설정됨

# 실행
cd msf/msf-api && mvn spring-boot:run
# 또는
mvn package && java -jar target/mform-api-0.1.0-SNAPSHOT.jar
```

### 4.2 mform-web 실행

```bash
cd msf/msf-web
npm install
npm run dev
# → http://localhost:9480 (vite.config.js 포트)
# /api → http://localhost:8081 자동 프록시
```

### 4.3 M플랫폼 연동 전환

```properties
# application.properties
SERVER_NAME=LOCAL    # Mock (기본값)
# ↓ 실서버 전환 시 변경
SERVER_NAME=DEV
juice.url=http://[msc-prx-host]:7006/mPlatform/serviceCall.do
mplatform.user-id=[사용자ID]
```

---

## 5. 자주 참조해야 할 소스 경로

| 목적 | 파일 경로 |
|------|-----------|
| M플랫폼 전체 연동 | `msf/msf-api/src/main/java/com/ktmmobile/msf/common/mplatform/MplatFormSvc.java` |
| 휴대폰 인증 로직 | `msf/msf-api/src/main/java/com/ktmmobile/msf/formComm/service/JoinInfoSvcImpl.java` |
| 서비스변경 Step1 화면 | `msf/msf-web/src/components/formSvcChg/FormSvcChgStep1.vue` |
| 서비스변경 Step2 화면 | `msf/msf-web/src/components/formSvcChg/FormSvcChgStep2.vue` |
| 서비스해지 Step1~3 화면 | `msf/msf-web/src/components/formSvcCncl/FormSvcCncl*.vue` |
| 명의변경 Step1~3 화면 | `msf/msf-web/src/components/formOwnChg/FormOwnChg*.vue` |
| ASIS 부가서비스 처리 | `mcp/mcp-portal-was/.../mypage/controller/RegSvcController.java` |
| ASIS 신청서 저장 | `mcp/mcp-portal-was/.../appform/service/AppformSvcImpl.java` |
| ASIS 명의변경 | `mcp/mcp-portal-was/.../mypage/controller/MyNameChgController.java` |
| ASIS M플랫폼 전체 | `mcp/mcp-portal-was/.../common/mplatform/MplatFormService.java` |
| 개발 가이드 문서 | `.doc/10.서식지프로젝트.md` |
| ASIS-TOBE 기능 명세 | `.doc/15.개발기능목록_ASIS_TOBE_기능분석명세서.md` |
| 요구사항별 개발 진행 | `.doc/18.요구사항ID별_ASIS_TOBE_개발진행명세.md` |
| ASIS 참조 분석 문서 | `.doc/asis/` (14, 21, 22번 문서) |
| 인터페이스 설계서 | `.doc/reference/MMSP-DS-06-인터페이스_설계서_20260325.md` |
| 테이블 정의서 | `.doc/reference/스마트서식지-DS-05-테이블 정의서_V1.0_20260318.md` |

---

## 6. 개발 시 주의사항

- **세션 없음** — ASIS MCP는 `SessionUtils.getUserCookieBean()`으로 ncn/custId 취득하지만, TOBE는 세션 없이 매 요청마다 ncn/ctn/custId를 직접 전달해야 함
- **SERVER_NAME=LOCAL** — `application.properties` 기본값. 실서버 전환 시 반드시 `juice.url` + `SERVER_NAME` 변경 필요
- **DB 로컬/운영 분리** — 로컬: PostgreSQL, 운영: Oracle. `application.properties`에서 드라이버/URL 전환
- **응답 규격 일관성** — `success`(Boolean) + `resultCode` + `message` 통일 (`RESULT_CODE`(String) 혼용 주의)
- **TypeScript 미사용** — JS 순수 사용. `@ts-check` 또는 JSDoc으로 타입 힌트 보완 가능
- **Tailwind 4 문법** — `@apply` 사용 시 `@reference "tailwindcss"` 선언 필수 (scoped style 내)
- **동시처리불가** — `RATE_CHANGE` / `NUM_CHANGE` / `LOST_RESTORE` / `PHONE_CHANGE` 2개 이상 선택 불가 (프론트 `CORE_CHANGE` 상수 기준)
