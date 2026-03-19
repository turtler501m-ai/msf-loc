# 테이블 접두어 및 DB 출처 가이드

테이블명 접두어로 어느 DB에서 오는 데이터인지 식별.

---

## 1. 접두어별 DB 출처

| 접두어 | 소속 DB | 설명 | 환경 |
|--------|---------|------|------|
| `MSF_` | **MSF DB** | 신규 서식지 전용 테이블 (설계서 기준 92개) | PostgreSQL(로컬) / Oracle(운영) |
| `NMCP_` | **M전산 DB** | 기존 M포탈(MCP) 데이터 — 명의변경 신청서 등 | Oracle @DL_MCP |
| `MSP_XXX@DL_MSP` | **M플랫폼 DB** | M플랫폼 마스터 데이터 — DB링크 방식 접근 | Oracle DB링크 |

---

## 2. MSF DB 테이블 (`MSF_`)

신규 스마트서식지 전용. 테이블 정의서(`MMSP-DS-05`) 기준.

### 주요 테이블

| 테이블명 | 역할 |
|----------|------|
| `MSF_REQUEST` | 신청서 마스터 (서비스변경·명의변경·해지 통합) |
| `MSF_REQUEST_CSTMR` | 신청서 고객정보 |
| `MSF_CUST_REQUEST_MST` | 서비스변경 신청 상세 |
| `MSF_CUST_REQUEST_PROD` | 신청 상품 목록 |
| `MSF_CUST_REQUEST_ADD` | 신청 부가서비스 목록 |
| `MSF_CUST_REQUEST_INSR` | 단말보험 가입신청 정보 |
| `MSF_REQUEST_CANCEL` | 서비스해지 신청 정보 |
| `MSF_REQUEST_NAME_CHG` | 명의변경 신청 정보 |
| `MSF_CD_DTL` | 공통코드 상세 (ASIS `NMCP_CD_DTL` 대응) |
| `MSF_STORE_INFO` | 대리점 정보 |

### 로컬(PostgreSQL) vs 운영(Oracle) 차이

```properties
# 로컬
spring.datasource.url=jdbc:postgresql://localhost:5432/msf
spring.datasource.driver-class-name=org.postgresql.Driver

# 운영 (Oracle)
spring.datasource.url=jdbc:oracle:thin:@[host]:1521/[sid]
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
```

> **주의**: `NOW()` (PostgreSQL) → `SYSDATE` (Oracle) 차이 있음. 운영 배포 시 Mapper XML 확인 필요.

---

## 3. M전산 DB 테이블 (`NMCP_`)

기존 M포탈(MCP) DB. 명의변경 신청 등 일부 업무에서 ASIS와 동일하게 참조.

| 테이블명 | 역할 | TOBE 대응 |
|----------|------|-----------|
| `NMCP_CUST_REQUEST_MST` | 명의변경 신청 마스터 | `MSF_CUST_REQUEST_MST` |
| `NMCP_CD_DTL` | 공통코드 상세 | `MSF_CD_DTL` |
| `msp_juo_sub_info` | 가입자 정보 (인증용) | 그대로 참조 (`ContractInfoMapper`) |

> ASIS 참조 필드: `RVISN_ID → AMD_ID`, `_FLAG → _YN`, `_NAME → _NM`, `_CODE → _CD`, `_TYPE → _TYPE_CD`
> (상세 매핑: `.doc/intdoc/MCP_REQUEST_MSF_REQUEST_컬럼매핑.md`)

---

## 4. M플랫폼 DB 링크 테이블 (`MSP_XXX@DL_MSP`)

M플랫폼 DB를 Oracle DB링크(`@DL_MSP`)로 직접 조회. ASIS와 동일 방식 유지.

| 테이블명 | 역할 | 사용 위치 |
|----------|------|-----------|
| `MSP_INTM_INSR_MST@DL_MSP` | 단말보험 상품 마스터 | `SvcChgInsrMapper.xml` → `selectInsrProdList` |

### Mapper XML 작성 예시

```xml
<!-- DB링크 테이블은 @DL_MSP 그대로 사용 -->
<select id="selectInsrProdList" parameterType="map"
        resultType="com.ktmmobile.msf.formSvcChg.dto.InsrProductDto">
    SELECT DISTINCT
        T1.INSR_PROD_CD  AS insrProdCd,
        T1.CMPN_LMT_AMT  AS cmpnLmtAmt,
        T2.DTL_CD_NM     AS insrProdNm
    FROM MSP_INTM_INSR_MST@DL_MSP T1
    LEFT JOIN MSF_CD_DTL T2
        ON T1.INSR_PROD_CD = T2.DTL_CD
       AND T2.CD_GROUP_ID = 'IntmInsrRelNm'
    WHERE T1.USG_YN = 'Y'
</select>
```

### LOCAL 모드 처리 규칙

DB링크는 Oracle 운영 환경에서만 동작하므로 `SERVER_NAME=LOCAL` 시 반드시 Mock 분기 필요.

```java
@Value("${SERVER_NAME:LOCAL}")
private String serverName;

public Map<String, Object> getInsuranceProducts(String reqBuyType) {
    if ("LOCAL".equals(serverName)) {
        // Mock 데이터 반환
        ...
        return result;
    }
    // 실서버: DB링크 조회
    List<InsrProductDto> list = insrMapper.selectInsrProdList(params);
    ...
}
```

---

## 5. 테이블 개발 체크리스트

신규 테이블 관련 코드 작성 시 확인 사항:

- [ ] **접두어 확인**: `MSF_`(신규) / `NMCP_`(M전산) / `MSP_@DL_MSP`(M플랫폼 DB링크)
- [ ] **DDL 위치**: `MSF_` 테이블 → `.doc/intdoc/script/msf_create_tables.sql` 반영
- [ ] **@MapperScan 등록**: 신규 `@Mapper` 패키지 → `MsfApplication.java`에 추가
- [ ] **DB링크 Mock 처리**: `@DL_MSP` 사용 시 `SERVER_NAME=LOCAL` 분기 필수
- [ ] **날짜 함수**: PostgreSQL `NOW()` ↔ Oracle `SYSDATE` 운영 시 확인
- [ ] **매핑 문서 참조**: ASIS↔TOBE 컬럼 네이밍 변경 → `.doc/intdoc/MCP_REQUEST_MSF_REQUEST_컬럼매핑.md`
