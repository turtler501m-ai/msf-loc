---
name: ktm-smartform
description: 스마트서식지(MSF) 프로젝트 개발 가이드 — Vue 3 + Spring Boot 4.0 + Gradle 멀티모듈 구조, M플랫폼 연동 패턴, 개발 현황 포함. msf-be-form-api / msf-form-web 작업 시 자동 참조.
---

# 스마트서식지(MSF) 프로젝트 개발 스킬 가이드

작성 기준: 현행 코드베이스 분석(2026-04-11) — msf-form-web(Vue 3) + msf-be-form-api(Spring Boot 4.0, Gradle 멀티모듈)

개발 완료/미완성 현황은 `dev-status.md`, SOC 코드 레퍼런스는 `soc-reference.md`, **테이블 접두어·DB 출처는 `db-table-guide.md`** 참조.

---

## 1. 기술 스택 전체 요약

### 1.1 프론트엔드 (msf-form-web)

| 분류 | 기술 | 버전 | 용도 |
|------|------|------|------|
| 언어 | JavaScript (TypeScript 미사용) | ES2022+ | 전체 코드 |
| 프레임워크 | Vue 3 (Composition API) | 3.5.x | SPA 구성 |
| 빌드 | Vite | 8.x | 개발서버 + 번들링 |
| 상태관리 | Pinia | 3.x | 폼 상태·전역 상태 |
| 라우터 | Vue Router | 5.x | 화면 전환 |
| CSS | SCSS | - | 스타일 (Tailwind 미사용) |
| UI 컴포넌트 | 자체 라이브러리 (`libs/ui/`) | - | Base/Block 컴포넌트 |
| HTTP 클라이언트 | axios | 1.x | API 통신 |
| 유틸 | @vueuse/core | 14.x | 컴포저블 유틸 |
| 자동 임포트 | unplugin-vue-components | 32.x | 컴포넌트 자동 등록 |
| 포트 | 7080 | - | 개발 서버 |

### 1.2 백엔드 (msf-be-form-api)

| 분류 | 기술 | 버전 | 용도 |
|------|------|------|------|
| 언어 | Java | 11 | 전체 코드 |
| 프레임워크 | Spring Boot | 4.0 | REST API 서버 |
| 빌드 | Gradle | 멀티모듈 | 의존성·모듈 관리 |
| ORM | MyBatis | - | DB 매퍼 (XML) |
| DB | PostgreSQL | - | MSF DB (localhost:5432/msf) |
| 포트 | 8080 | - | API 서버 |
| 루트 패키지 | `com.ktmmobile.msf.domains.form` | - | 핵심 업무 도메인 |
| 진입점 | `FormApiApplication.java` | - | `com.ktmmobile.msf.appboot` |

---

## 2. 프론트엔드 핵심 스킬

### 2.1 Vue 3 Composition API

현재 코드 전체가 `<script setup>` 방식으로 작성됨.

```js
// 기본 패턴 — TerminationCustomer.vue, ServiceChangeProduct.vue 등 모든 컴포넌트
import { ref, computed, watch, onMounted } from 'vue'

const form = ref({ name: '', phone: '' })            // 반응형 데이터
const isValid = computed(() => !!form.value.name)    // 파생값
watch(form, (v) => store.setCustomerForm(v), { deep: true }) // 감시
onMounted(async () => { await loadData() })           // 진입 시 조회

// defineEmits / defineExpose — 부모-자식 통신
const emit = defineEmits(['update:complete'])
defineExpose({ validate, save })  // 스텝 컴포넌트에서 호출
```

필수 숙지 패턴:
- `defineModel` — `v-model:open`으로 팝업 open 상태 동기화
- `defineEmits + defineExpose` — 스텝 컴포넌트 유효성 검사 구조
- `<Teleport to="body">` — 팝업을 body로 렌더링

### 2.2 Pinia 스토어 구조

신청서 종류별 별도 스토어 존재.

```js
// stores/msf_termination.js — 서비스해지
// stores/msf_newchange.js  — 신규가입·변경
// stores/msf_step.js       — 스텝 진행 관리
// stores/msf_menu.js       — 메뉴/네비게이션
// stores/msf_user.js       — 사용자 세션
```

주요 패턴:
- 각 단계 컴포넌트에서 `store.setXxxForm()`으로 값 저장
- 다음 단계 이동 시 스토어 데이터를 API 요청에 활용
- `store.reset()` — 신청 완료 후 상태 초기화

### 2.3 라우터 구조

```js
// router/index.js — 핵심 뷰
MsfFormView      // 신청서 폼 라우팅
MsfMainView      // 메인 화면
MsfLoginView     // 로그인
MsfDeviceAuthView    // 기기 인증
MsfDeviceRegisterView // 기기 등록
MsfExtraView     // 부가 화면 (영수증·간편신청 등)
MsfNotFoundView  // 404
```

### 2.4 API 호출 패턴

```js
// libs/api/msf.api.js — 공통 axios 래퍼
import { msfPost, msfGet } from '@/libs/api/msf.api.js'

// 사용 예
const data = await msfPost('/api/v1/cancel/consult', { ctn, ncn, custId })
// 응답: { success, resultCode, message, ... }
```

에러 처리 규칙:
- `try/catch` 필수
- 백엔드 응답 `success: false` 또는 `RESULT_CODE: 'E'` 체크
- `alert` 대신 MsfAlertDialog 컴포넌트 활용

### 2.5 SCSS 스타일 작성법

```html
<!-- scoped style 사용 -->
<style scoped lang="scss">
.form-row {
  display: flex;
  align-items: flex-start;
  gap: 1rem;

  .label {
    width: 140px;
    flex-shrink: 0;
    font-size: 0.875rem;
  }
}
</style>
```

### 2.6 자체 UI 라이브러리 컴포넌트 패턴

```vue
<!-- libs/ui/block/MsfDialog.vue — 팝업 구조 -->
<MsfDialog v-model:open="isOpen">
  <!-- 내용 -->
  <MsfButtonGroup>
    <MsfButton variant="outline" @click="isOpen = false">취소</MsfButton>
    <MsfButton @click="confirm">확인</MsfButton>
  </MsfButtonGroup>
</MsfDialog>

<!-- libs/ui/block/MsfAlertDialog.vue — 알림 팝업 -->
<MsfAlertDialog v-model:open="alertOpen" :message="alertMsg" />

<script setup>
const isOpen = defineModel('open', { type: Boolean, default: false })
const emit = defineEmits(['confirm'])
</script>
```

---

## 3. 백엔드 핵심 스킬

### 3.1 Spring Boot 4.0 REST Controller 패턴

```java
// 현재 코드 패턴 — MsfCancelConsultController.java
@RestController
@RequestMapping("/api/v1")
public class MsfCancelConsultController {

    private final MsfCancelConsultSvc cancelConsultSvc;

    // 생성자 주입 (현재 전체 코드가 이 방식 사용)
    public MsfCancelConsultController(MsfCancelConsultSvc cancelConsultSvc) {
        this.cancelConsultSvc = cancelConsultSvc;
    }

    @PostMapping("/cancel/consult")
    public ResponseEntity<?> getCancelConsult(
            @RequestBody CancelConsultReqDto req) {
        return ResponseEntity.ok(cancelConsultSvc.getCancelConsult(req));
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

### 3.2 MyBatis — SqlSession 주입 방식 (DaoImpl 패턴)

현재 프로젝트는 `@Mapper` 인터페이스가 아닌 `XxxDaoImpl.java`에서 `SqlSession`을 직접 주입하는 방식을 사용한다.

```java
// CancelConsultDaoImpl.java
@Repository
public class CancelConsultDaoImpl implements CancelConsultDao {

    private final SqlSession sqlSession;

    public CancelConsultDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public CancelConsultDto selectConsult(String ncn) {
        return sqlSession.selectOne(
            "com.ktmmobile.msf.domains.form.form.termination.mapper.CancelConsultMapper.selectConsult",
            ncn);
    }
}
```

XML mapper는 Java 소스와 같은 위치의 `mapper/` 서브폴더에 배치:

```
domains/form/src/main/java/com/ktmmobile/msf/domains/form/
  form/termination/mapper/CancelConsultMapper.xml
  form/servicechange/mapper/RegSvcMapper.xml
  form/ownerchange/mapper/MyNameChgMapper.xml
  form/newchange/mapper/AppFormMapper.xml
  form/common/mapper/OrderMapper.xml
  common/mapper/CommCodeMapper.xml
```

### 3.3 M플랫폼 연동 패턴

**가장 중요한 구조 — 신규 API 추가 시 항상 이 패턴 따라야 함.**

M플랫폼 서비스 위치: `com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService`

```java
// MsfMplatFormService.java — 모든 M플랫폼 연동의 진입점
public MpAddSvcInfoDto getAddSvcInfoDto(String ncn, String ctn, String custId) {
    MpAddSvcInfoDto vo = new MpAddSvcInfoDto();
    HashMap<String, String> param = getParamMap(ncn, ctn, custId, "X20");
    if ("LOCAL".equals(serverLocation)) {
        getVo(20, vo);          // ← LOCAL: Mock XML 응답
    } else {
        msfMplatFormServerAdapter.callService(param, vo);  // ← 실서버
    }
    return vo;
}
```

신규 M플랫폼 API 추가 절차:
1. `MsfMplatFormService`에 상수 추가 (`APP_EVENT_CD_XXX`)
2. `public` 메서드 추가 (`getParamMap` + LOCAL분기 + `callService`)
3. `getVo()`에 `case` 추가 (Mock XML)
4. VO 클래스 생성 (`extends CommonXmlVO`, `parse()` 오버라이드)

### 3.4 공통 요청 DTO 계층구조

```java
// 핵심 요청 DTO — 여러 서비스 공통 기반으로 활용
// 도메인별 ReqDto는 필요 필드를 포함하거나 상속
public class CancelConsultReqDto {
    private String name;        // 고객명
    private String ncn;         // 계약번호 9자리
    private String ctn;         // 휴대폰번호 11자리
    private String custId;      // 고객ID
}
```

---

## 4. 로컬 개발 환경 설정

### 4.1 백엔드 실행 (msf-be-form-api)

```bash
cd msf-be-form-api

# 빌드
./gradlew build -x test

# 실행 (port 8080)
./gradlew :app-boot:bootRun
# 또는
java -jar app-boot/build/libs/app-boot-1.0.0.jar
```

DB: `localhost:5432/msf` (user: postgres / pw: postgres 또는 `application-private.yaml`에서 설정)

### 4.2 프론트엔드 실행 (msf-form-web)

```bash
cd msf-form-web
npm install
npm run dev      # port 7080, LOC 모드 (VITE_MSF_API_URL=http://localhost:8080)
npm run dev:loc  # 동일
npm run build
npm run lint     # oxlint + eslint (--fix)
npm run format   # prettier
```

### 4.3 M플랫폼 연동 전환

```yaml
# application-private.yaml
server-location: LOCAL    # Mock (기본값)
# ↓ 실서버 전환 시 변경
server-location: DEV
juice.url: http://[msc-prx-host]:7006/mPlatform/serviceCall.do
mplatform.user-id: [사용자ID]
```

---

## 5. 자주 참조해야 할 소스 경로

| 목적 | 파일 경로 |
|------|-----------|
| M플랫폼 전체 연동 | `msf-be-form-api/domains/form/src/main/java/com/ktmmobile/msf/domains/form/common/mplatform/MsfMplatFormService.java` |
| 서비스해지 컨트롤러 | `msf-be-form-api/domains/form/src/main/java/com/ktmmobile/msf/domains/form/form/termination/controller/MsfCancelConsultController.java` |
| 서비스해지 서비스 | `msf-be-form-api/domains/form/src/main/java/com/ktmmobile/msf/domains/form/form/termination/service/MsfCancelConsultSvcImpl.java` |
| 서비스변경 컨트롤러 (부가서비스) | `msf-be-form-api/domains/form/src/main/java/com/ktmmobile/msf/domains/form/form/servicechange/controller/MsfRegSvcController.java` |
| 명의변경 컨트롤러 | `msf-be-form-api/domains/form/src/main/java/com/ktmmobile/msf/domains/form/form/ownerchange/controller/MyNameChgController.java` |
| 신규가입·변경 컨트롤러 | `msf-be-form-api/domains/form/src/main/java/com/ktmmobile/msf/domains/form/form/newchange/controller/AppformController.java` |
| 공통 컨트롤러 | `msf-be-form-api/domains/form/src/main/java/com/ktmmobile/msf/domains/form/common/controller/FCommonController.java` |
| API 공통 래퍼 | `msf-form-web/src/libs/api/msf.api.js` |
| 서비스해지 화면 | `msf-form-web/src/components/form/termination/` |
| 서비스변경 화면 | `msf-form-web/src/components/form/servicechange/` |
| 명의변경 화면 | `msf-form-web/src/components/form/ownerchange/` |
| 신규가입·변경 화면 | `msf-form-web/src/components/form/newchange/` |
| ASIS 부가서비스 처리 | `mcp/mcp-portal-was/.../mypage/controller/RegSvcController.java` |
| ASIS 신청서 저장 | `mcp/mcp-portal-was/.../appform/service/AppformSvcImpl.java` |
| ASIS 명의변경 | `mcp/mcp-portal-was/.../mypage/controller/MyNameChgController.java` |
| ASIS M플랫폼 전체 | `mcp/mcp-portal-was/.../common/mplatform/MplatFormService.java` |
| 개발 진행 현황 | `.doc/51.MSF_개발진행사항_현행화.md` |
| 인터페이스 설계서 | `.doc/reference/C1.MMSP-DS-06-인터페이스_설계서_20260325.md` |
| 테이블 정의서 | `.doc/reference/D1.스마트서식지-DS-05-테이블정의서_V1.0_20260318.md` |
| ASIS 참조 분석 문서 | `.doc/asis/` |

---

## 6. 개발 시 주의사항

- **세션 없음** — ASIS MCP는 `SessionUtils.getUserCookieBean()`으로 ncn/custId 취득하지만, TOBE는 세션 없이 매 요청마다 ncn/ctn/custId를 직접 전달해야 함
- **server-location=LOCAL** — 기본값. 실서버 전환 시 반드시 `juice.url` + `server-location` 변경 필요
- **MyBatis SqlSession 방식** — 현재 프로젝트는 `@Mapper` 인터페이스가 없고 `XxxDaoImpl`에서 `SqlSession` 직접 주입. 신규 `@Mapper` 인터페이스 도입 시 `FormApiApplication.java`의 `@MapperScan`에 패키지 추가 필수
- **XML mapper 위치** — `resources/` 가 아닌 Java 소스 폴더 내 `mapper/` 서브폴더에 위치 (`domains/form/src/main/java/.../mapper/*.xml`)
- **응답 규격 일관성** — `success`(Boolean) + `resultCode` + `message` 통일 (`RESULT_CODE`(String) 혼용 주의)
- **TypeScript 미사용** — JS 순수 사용. JSDoc으로 타입 힌트 보완 가능
- **HTTP 메서드** — GET / POST 두 가지만 사용. PUT / PATCH / DELETE 사용 금지
- **동시처리불가** — `RATE_CHANGE` / `NUM_CHANGE` / `LOST_RESTORE` / `PHONE_CHANGE` 2개 이상 선택 불가 (프론트 `CORE_CHANGE` 상수 기준)
- **Windows API 테스트** — `curl` 대신 Node.js `http.request()` 사용 (한글 UTF-8 인코딩 문제 방지)
