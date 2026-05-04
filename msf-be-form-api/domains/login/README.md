# login

`login` 모듈은 각 애플리케이션 저장소에서 로그인 정책과 외부 연동을 구현하기 위한 자리입니다.
공통 토큰 발급, Refresh Token rotation, Redis token store, 사용자 정보 캐시, 인증번호 발급/검증, 공통 로그인 정책 인터페이스는 `:commons:login-core`가 담당합니다.

이 `template` 저장소의 `:domains:login`에는 FORM-API나 ADMIN-API에 종속된 구현을 두지 않습니다.
실제 서비스 저장소는 이 모듈 아래에 앱 특성에 맞는 컨트롤러, 요청 DTO, Repository adapter, MyBatis mapper, 앱 전용 정책을 작성합니다.

## Responsibility

- 앱별 로그인 진입점 제공
- 앱별 `LoginAuthenticationCredential` 구현
- 사용자 DB 조회 및 로그인 성공/실패 갱신
- 앱별 `LoginUserFinder` 구현
- SMS 발송, 외부 인증, 단말 인증 같은 외부 연동 adapter 구현
- 앱별 `LoginCompletionPolicy`, `LoginRequiredActionPolicy` 구현
- 앱에서 사용할 policy whitelist 설정

## Package Layout

```text
adapter
- client
- controller
- repository
- repository/mybatis/{datasource}/mapper

application
- dto
- port/out
- service

domain
- code
- policy

support
- config
- exception
- properties
```

`domain/policy`에는 외부 기술에 의존하지 않는 앱별 정책 구현체를 둡니다.
공통으로 재사용 가능한 정책 인터페이스와 기본 구현체는 `:commons:login-core`에 둡니다.

## Implementation Guide

앱 저장소의 `:login` 모듈은 보통 다음 구성을 갖습니다.

```text
LoginAuthController
-> LoginCoreService (:commons:login-core)
-> LoginAuthenticator / LoginUserFinder 구현체
-> 앱별 Repository adapter
-> 앱별 DB mapper 또는 외부 client
```

앱별 요청 DTO는 외부 요청을 앱 전용 `LoginAuthenticationCredential`로 변환합니다.
FORM-API처럼 일반 사용자만 다루는 저장소는 `userType()`을 `FORM_USER`로 고정하고, ADMIN-API처럼 관리자만 다루는 저장소는 `ADMIN_USER`로 고정합니다.
공통 `template` 저장소에서는 두 앱을 모두 처리하는 분기 구현을 유지하지 않습니다.

## Policy Configuration

`login-core.policy`는 앱 저장소의 `application-login.yaml`에서 명시적인 whitelist 방식으로 설정합니다.
정책은 YAML에 작성된 순서대로 적용됩니다.
아래 YAML은 작성 방식 예시이며, 실제 적용 값은 앱 저장소의 설정 파일을 기준으로 확인합니다.

```yaml
login-core:
  policy:
    completion:
      - someCompletionPolicyBean
    failure:
      - someFailurePolicyBean
    required-action:
      - someRequiredActionPolicyBean
```

공통 정책 bean 이름은 `:commons:login-core`의 각 정책 클래스에 정의된 `BEAN_NAME`을 기준으로 맞춥니다.
앱 전용 정책은 앱 저장소의 `:login` 모듈에서 구현하고, 필요한 앱에서만 whitelist에 추가합니다.

## User Info

JWT에는 사용자명과 휴대폰번호 같은 화면 표시용 개인정보를 저장하지 않습니다.
사용자 정보 조회 API와 사용자 정보 캐시 사용 방식은 각 앱 저장소의 `:login` 모듈에서 구현합니다.
캐시 저장과 조회에는 `:commons:login-core`의 `LoginUserInfoCacheService`를 사용합니다.

## Two Factor Authentication

인증번호 발급/검증의 공통 기능은 `:commons:login-core`가 제공합니다.
로그인 2FA가 필요한 경우 별도 API나 업무 서비스는 `LoginCoreService.issueTwoFactorCode(loginSessionId)`와
`LoginCoreService.verifyTwoFactor(loginSessionId, verificationCode)`를 호출합니다.

실제 SMS 발송은 별도 API 또는 업무 서비스가 담당합니다.
`:login` 모듈은 로그인 시작, 토큰 발급, 사용자 정보 조회 같은 앱별 로그인 진입점과 정책 구현만 담당합니다.

## Biometric Authentication

생체인증 challenge 생성과 TTL 관리는 `:commons:login-core`에서 공통으로 제공합니다.
기기 공개키 저장, 서명 검증, 기기 승인 상태 조회, 단말 인증 필수 여부는 앱 저장소의 `:login` 모듈에서 구현합니다.
