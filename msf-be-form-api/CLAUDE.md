# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

> 상위 워크스페이스 가이드: `../CLAUDE.md` (전체 아키텍처·개발 원칙·연동 코드표 포함)

## 기술 스택

| 항목 | 값 |
|------|-----|
| Java | 25 (toolchain) |
| Spring Boot | 4.0.3 |
| MyBatis Spring Boot Starter | 4.0.1 |
| Lombok | 1.18.42 |
| MapStruct | 1.6.3 |
| Build | Gradle (Kotlin DSL, buildSrc 컨벤션) |
| DB | PostgreSQL (MSF) + Oracle (MSP, DB Link) |

## 빌드 & 실행

```bash
# 빌드 (테스트 제외)
./gradlew build -x test

# 실행 (port 8080)
./gradlew :app-boot:bootRun

# 모듈별 테스트
./gradlew :commons:common:test
./gradlew :domains:form:test

# 전체 테스트
./gradlew test
```

## 모듈 의존 관계

```
app-boot
  └── domains:form
        ├── commons:common
        ├── external:mybatis      ← MyBatis + SqlSession 설정
        └── external:websecurity
commons:common
  └── (공통 인프라: DataSource, Cache, Enum, Validation)
```

`settings.gradle.kts`의 `includeSubModules()`가 `commons/`, `domains/`, `external/` 하위를 자동 포함.

## 프로파일 & 설정 파일 구조

- `app-boot/src/main/resources/application.yaml` — 서버 포트, 기본 프로파일(`local`)
- `app-boot/src/main/resources/application-private.yaml` — 개인 DB 접속 정보 (git 제외)
- 각 모듈의 `application-{module}.yaml`은 `ModuleYamlEnvironmentPostProcessor`가 자동 로드
- DB (local): `localhost:5432/msf` (user: postgres / pw: `application-private.yaml` 참조)
- DB (DEV): `jdbc:postgresql://211.184.227.24:45432/msf_core` (user: smartform_dev / pw: dev!!12form)

## 도메인 패키지 (domains/form)

루트: `com.ktmmobile.msf.domains.form`

| 패키지 | 설명 |
|--------|------|
| `common/` | 공통 컨트롤러·DAO·DTO·캐시·상수·예외 |
| `form/newchange/` | 신규가입·변경 신청서 |
| `form/ownerchange/` | 명의변경 |
| `form/servicechange/` | 서비스변경 (요금제·부가서비스·결합·데이터쉐어링) |
| `form/termination/` | 서비스해지 |
| `form/appform_d/` | **헥사고날 아키텍처** 패턴 (adapter / application / domain) |
| `system/cert/` | 인증 |
| `system/faceauth/` | 안면인증 |

## MyBatis 두 가지 패턴 — 혼용 주의

### 패턴 A: SqlSession 직접 주입 (기존 도메인)
`newchange`, `ownerchange`, `servicechange`, `termination` 등 대부분의 DAO

```java
@Repository
public class XxxDaoImpl implements XxxDao {
    private final SqlSession sqlSession;  // 생성자 주입
    // sqlSession.selectOne("namespace.id", param)
}
```
- `@Mapper` 인터페이스 없음
- XML은 Java 소스와 같은 경로에 위치 (`mapper/XxxMapper.xml`)

### 패턴 B: @Mapper 인터페이스 (appform_d 도메인)
`form/appform_d/adapter/repository/mybatis/msp/mapper/` 하위

```java
@Mapper
public interface XxxMapper { ... }
```
- `FormMapperConfig.java`에 `@MapperScan`으로 등록 필요
- 신규 `@Mapper` 추가 시 반드시 `FormMapperConfig`의 `basePackages`에 패키지 추가

## XML Mapper 위치 규칙

MyBatis XML은 Java 소스 트리 내에 함께 위치 (`src/main/java/.../mapper/XxxMapper.xml`).  
`java-conventions.gradle.kts`의 `processResources { from("src/main/java") { include("**/*.xml") } }`로 빌드 시 classpath에 복사됨.

## 스캔 베이스 패키지

`FormApiApplication`의 `@SpringBootApplication(scanBasePackages = CommonBaseConst.BASE_PACKAGE)` → `"com.ktmmobile.msf"` 전체 스캔.

## 레이어별 명명 규칙

- **Controller**: `MsfXxxController` (접두사 `Msf`)
- **Service interface**: `MsfXxxSvc` 또는 `MsfXxxService`
- **Service impl**: `MsfXxxSvcImpl` / `MsfXxxServiceImpl`
- **DAO interface**: `XxxDao` (접두사 없음)
- **DAO impl**: `XxxDaoImpl`
- **DTO**: `XxxDto` / `XxxReqDto` / `XxxResVO`

## DataSource 구분

| DataSource | 용도 | 설정 위치 |
|-----------|------|----------|
| `smartform` | MSF PostgreSQL (메인 DB) | `SmartFormDataSourceConfig` |
| `msp` | M플랫폼 Oracle (읽기 전용 조회) | `MspDataSourceConfig` |

`AbstractGroupDataSourceSupport` / `RoutingDataSource`로 다중 DataSource 라우팅 처리.
