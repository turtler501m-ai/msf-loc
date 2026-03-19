# msf-api

스마트서식지(msf-web) 전용 REST API. M포털 X01(가입정보조회) 등과 동일 스펙으로 응답합니다.

## 기술 스택

- Java 11, Spring Boot 2.7

## 로컬 DB (PostgreSQL)

휴대폰 인증(join-info) 시 계약정보 조회에 로컬 PostgreSQL을 사용합니다.

### 1. postgres 비밀번호 설정 (연결 오류 시 필수)

**"password authentication failed for user postgres"** 가 나오면, 로컬 PostgreSQL의 `postgres` 사용자 비밀번호가 설정되지 않았거나 다를 때입니다.

- **Windows (설치 시 비밀번호를 안 정한 경우)**  
  - pgAdmin으로 접속하거나, "SQL Shell (psql)" 실행 후:
    ```sql
    ALTER USER postgres PASSWORD 'postgres';
    ```
  - 또는 [서비스]에서 PostgreSQL 중지 → `pg_hba.conf`에서 `postgres`에 대한 인증을 `trust`로 잠깐 바꾼 뒤 서비스 재시작 → psql로 접속해 위 `ALTER USER` 실행 → 다시 `md5`/`scram-sha-256`으로 되돌리고 재시작.
- **비밀번호를 `postgres`가 아닌 값으로 쓴 경우**  
  - `application.properties`의 `spring.datasource.password`를 그 비밀번호로 맞추거나, 아래처럼 환경변수로 넣어서 사용하세요.

### 2. DB 생성 및 스키마 적용

1) DB 생성 (비밀번호 입력 프롬프트에 본인 postgres 비밀번호 입력):

   ```bash
   createdb -U postgres msf
   ```

   또는 psql로 접속한 뒤:

   ```sql
   CREATE DATABASE msf;
   ```

2) `application.properties`에서 계정 확인:
   - `spring.datasource.url=jdbc:postgresql://localhost:5432/msf`
   - `spring.datasource.username=postgres`
   - `spring.datasource.password=postgres` ← **실제 postgres 비밀번호로 변경** (또는 아래처럼 환경변수 사용)

   비밀번호를 코드에 넣기 싫다면:
   ```properties
   spring.datasource.password=${DB_PASSWORD:postgres}
   ```
   실행 시 `-DDB_PASSWORD=실제비밀번호` 또는 환경변수 `DB_PASSWORD` 설정.

3) 테이블·샘플 데이터 적용 (단일 DDL: kcf_tab_create1.sql):
   ```bash
   # 워크스페이스 루트에서
   psql -U postgres -d msf -f ktm/doc/tabdoc/kcf_tab_create1.sql
   ```
   (비밀번호 물어보면 postgres 사용자 비밀번호 입력)

- 테스트 계정: 이름 **홍길동**, 휴대폰 **01012345678** 로 인증 시도 시 정상 처리됨.

## 빌드 및 실행

```bash
# 빌드
mvn -DskipTests package

# 실행 (port 8081)
java -jar target/msf-api-0.1.0-SNAPSHOT.jar
# 또는
mvn spring-boot:run
```

## API (1차 Mock)

| 메서드 | 경로 | 설명 |
|--------|------|------|
| POST | /api/v1/join-info | X01 가입정보조회 (ctn 필수, Mock) |
| POST | /api/v1/ident/join-info | 명의변경용 join-info (동일) |
| POST | /api/v1/cancel/join-info | 서비스해지용 join-info (동일) |
| POST | /api/v1/service-change/concurrent-check | 동시변경 불가 체크 (Mock) |
| POST | /api/v1/addition/current | X20 부가서비스 조회 (Mock) |

## msf-web 연동

- 개발 시: msf-web의 Vite가 `/api`를 `http://localhost:8081`로 프록시합니다. 백엔드를 8081에서 실행한 뒤 `npm run dev`로 프론트만 실행하면 됩니다.
- 별도 도메인 사용 시: msf-web에 `VITE_API_BASE_URL=http://localhost:8081/api` 설정.

## 참조

- 계획: `ktm/doc/스마트서식지_백엔드_프론트_연동_계획.md`
- M포털 연동 참조: `mcp/mcp-portal-was/.../MplatFormService.java`. 스마트서식지 연동: `MplatFormSvc`, `formComm`(X01/Y04 공통), `formSvcChg` (서비스변경), `formOwnChg` (명의변경), `formSvcCncl` (해지). 휴대폰 인증: formComm.JoinInfoSvc (M전산 + Y04), 부가서비스변경은 `/api/v1/addition/join-info` 사용.
