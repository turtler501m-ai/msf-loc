# login-core

`login-core`는 로그인 기능의 공통 인증 엔진 모듈입니다.
애플리케이션별 사용자 조회, DB 테이블, Admin/Form 정책은 알지 않고, 토큰 생명주기와 공통 인증 정책만 담당합니다.

이 모듈은 여러 애플리케이션 저장소에서 공유하는 공통 모듈입니다.
각 애플리케이션은 자체 `:login` 모듈에서 사용자 조회, 앱별 진입점, 앱별 정책을 구현하고 `login-core`의 포트를 연결합니다.

## Responsibility

- Access Token 발급
- Refresh Token 발급
- Refresh Token Rotation
- Logout 시 토큰 폐기
- Redis 기반 Access/Refresh Token `jti` 저장
- Access Token whitelist 검증
- 공통 로그인 인증 흐름 템플릿 제공
- 공통 로그인 완료 정책 제공
- 공통 로그인 후속 조치 정책 제공
- 공통 로그인 실패 정책 제공
- 공통 로그인 세션 및 2FA 검증
- Refresh Token Cookie 생성/조회/삭제
- 공통 인증 방식 코드 제공
- 공통 로그인 응답 DTO 제공
- 사용자 정보 캐시 제공
- 생체인증 challenge TTL 정책 제공

## Package Layout

```text
application
- port/out
- service

domain
- policy

support
- properties
```

공통 로그인 완료 정책은 `domain/policy`에 둡니다.
설정 바인딩 객체는 `support/properties`에 둡니다.

## Module Boundary

`login-core`는 앱별 DB, 외부 진입점, target 정책을 직접 알지 않습니다.
대신 공통 인증 흐름 템플릿을 제공하고, 각 앱은 인증용 사용자 조회/사용자 정보 조회/성공 기록/실패 기록 포트를 구현합니다.

```text
LoginCoreService
-> LoginAuthenticator
-> DefaultLoginAuthenticationService
-> LoginUserFinder
-> LoginCompletionPolicyComposite
-> LoginFailurePolicyComposite
-> LoginTokenService
-> LoginTokenStore
-> RedisService
```

`LoginUserFinder` 구현체는 각 애플리케이션의 `:login` 모듈에 둡니다.
`findByCredential`은 비밀번호 검증과 로그인 정책 판단에 필요한 최소 정보만 조회하고, 로그인 성공 후 `findUserInfo`로 화면 표시용 사용자 정보를 별도 조회합니다.
앱별 완료 정책은 각 애플리케이션의 `:login` 모듈에서 `LoginCompletionPolicy` Bean으로 추가합니다.

## Token Storage

Redis에는 토큰 원문을 저장하지 않고 JWT의 `jti`만 저장합니다.
JWT claim에는 인증/검증에 필요한 최소 정보만 저장합니다.

```text
sub      -> userId
jti      -> token id
type     -> access|refresh
userType -> USER|ADMIN
```

사용자명, 휴대폰 번호 같은 화면 표시용 개인정보는 JWT에 저장하지 않습니다.
필요한 경우 앱별 사용자 정보 조회 API에서 DB를 통해 조회합니다.
반복 조회를 줄이기 위해 `LoginUserInfoCacheService`가 사용자 정보를 캐시합니다.
현재 구현은 Redis 미연결 단계라 인메모리 캐시를 사용하며, Redis 연결 후 동일 서비스 내부 구현을 교체합니다.

```text
token:{userId}:access  -> {accessTokenJti}
token:{userId}:refresh -> {refreshTokenJti}
```

실제 Redis key에는 `RedisService`와 `CacheUtils`가 앱별 prefix를 붙입니다.

```text
{appPrefix}:token:{userId}:access
{appPrefix}:token:{userId}:refresh
```

## Token Flow

```text
LoginCoreService.login()
-> DefaultLoginAuthenticationService.authenticate()
-> LoginUserFinder.findByCredential()
-> LoginCompletionPolicyComposite.verify()
-> PasswordEncoder 검증
-> 실패 시 LoginFailurePolicyComposite.shouldLock()
-> 성공/실패 기록
-> LoginRequiredActionPolicyComposite.resolve()
-> VERIFY_2FA requiredAction이 있으면 loginSessionId 반환
-> VERIFY_2FA가 없으면 필수 requiredAction 또는 토큰 발급 가능 상태 반환
```

2FA가 필요한 경우 Access/Refresh Token을 발급하지 않습니다.
2FA 검증이 성공한 뒤 남은 필수 조치가 없으면 `loginSessionId` 기반 토큰 발급 가능 상태를 반환합니다.

```text
LoginCoreService.issueTwoFactorCode(loginSessionId)
-> LoginSessionService.issueTwoFactorCode(loginSessionId)
-> 인증번호 생성 및 login-session 저장
-> LoginTwoFactorCodeIssue 반환
```

실제 인증번호 발송은 앱 또는 업무 API의 별도 서비스가 담당합니다.

```text
LoginCoreService.verifyTwoFactor()
-> LoginSessionService.verifyTwoFactor()
-> LoginCoreService.resume(loginSessionId)
-> 필수 requiredAction이 남아 있으면 LoginActionRequired 반환
-> 필수 requiredAction이 없으면 LoginSessionReady 반환
```

Access/Refresh Token 발급은 별도 호출로 처리합니다.

```text
LoginCoreService.issue(loginSessionId)
-> 인증 완료 여부 확인
-> 필수 requiredAction 잔여 여부 확인
-> Access/Refresh Token 발급
```

토큰 발급 전 커스텀 조치 기능도 같은 로그인 세션 흐름을 사용합니다.

```text
앱별 조치 업무 처리
-> LoginCoreService.completeAction(loginSessionId, actionCode)
-> 다음 requiredAction 또는 LoginSessionReady 응답
```

Refresh Token은 rotation 방식으로 처리합니다.

```text
Refresh Token Cookie 수신
-> JWT 서명/만료 검증
-> type=refresh 확인
-> sub(userId), jti 추출
-> Redis refresh jti와 비교
-> 일치하면 Access/Refresh Token 재발급
-> Redis jti 갱신
-> 새 Refresh Token Cookie 반환
```

이미 rotation 된 Refresh Token이 다시 들어오면 Redis의 `jti`와 일치하지 않습니다.
이 경우 토큰 재사용 또는 탈취 가능성으로 보고 해당 사용자의 token key를 삭제한 뒤 인증을 거부합니다.

## Completion Policy

로그인 완료 가능 여부는 `LoginCompletionPolicy` 구현체 조합으로 확인합니다.
`login-core`는 앱을 몰라도 되는 공통 정책과 최소 계약을 제공합니다.

```text
LoginCompletionCredential
LoginAuthenticationCredential
LoginCompletionContext
LoginCompletionPolicy
LoginCompletionPolicyComposite
```

Credential 계약은 계층별로 분리합니다.

```text
LoginCompletionCredential
- 로그인 완료 정책이 필요로 하는 최소 입력 계약
- password(), deviceUuid(), isPasswordAuth(), isDeviceAuth(), clientIp() 제공
- 공통 정책이 앱별 LoginCredential 타입을 몰라도 검증할 수 있게 함

LoginAuthenticationCredential
- 최종 인증 및 토큰 발급까지 필요한 입력 계약
- LoginCompletionCredential을 확장
- userType()은 공통 UserType 타입으로 제공

앱별 LoginCredential
- 각 애플리케이션의 실제 로그인 요청 모델
- LoginAuthenticationCredential을 구현
- UserType, app-specific field 등을 자유롭게 포함
```

즉 공통 정책은 `LoginCompletionCredential`까지만 알고, 공통 인증 템플릿은 `LoginAuthenticationCredential`까지만 압니다.
앱 전용 값은 각 앱의 `LoginCredential`에 두고, 앱별 정책이 필요할 때만 구체 타입으로 확인합니다.

공통 정책 구현체는 다음과 같습니다.

```text
UserStatusLoginCompletionPolicy
PasswordLoginCompletionPolicy
DeviceAuthLoginCompletionPolicy
ClientIpAllowlistLoginCompletionPolicy
```

앱별 정책은 각 도메인 모듈에서 `LoginCompletionPolicy`를 추가 구현합니다.
등록된 Policy Bean은 기본 적용되지 않습니다.
적용 여부는 `login-core.policy` 아래 Composite별 whitelist로 제어합니다.
목록 값은 Spring 기본 bean name을 사용하며, YAML에 적힌 순서대로 실행됩니다.
아래 YAML은 구조를 설명하기 위한 예시이며, 실제 적용 값은 각 저장소의 설정 파일을 기준으로 확인합니다.

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

`LoginRequiredActionPolicy`는 YAML 순서가 후속 조치 우선순위가 됩니다.
YAML 순서대로 검사하다가 첫 조치가 나오면 바로 반환하므로, 응답에는 다음에 처리할 조치 하나만 내려갑니다.

로그인 실패 시 잠금 여부는 `LoginFailurePolicy` 구현체 조합으로 확인합니다.

```text
LoginFailureContext
LoginFailurePolicy
LoginFailurePolicyComposite
LoginFailureLimitPolicy
```

기본 실패 제한 횟수는 `login-core.failure.max-count`로 설정합니다.

## Required Actions

로그인이 성공했지만 사용자가 후속 조치를 해야 하는 상태는 `requiredAction`으로 응답합니다.
후속 조치는 일반 토큰 발급 전에 반드시 해결해야 하는 조치와, 토큰 발급 후 화면에서 안내해도 되는 조치로 구분합니다.

```text
LoginRequiredActionPolicy
LoginRequiredActionPolicyComposite
LoginRequiredAction
```

`LoginRequiredAction.tokenIssuable=false`인 조치가 하나라도 있으면 `LoginActionRequired`를 반환하고 Access/Refresh Token을 발급하지 않습니다.
2FA가 필요한 경우에는 먼저 `VERIFY_2FA` requiredAction과 `loginSessionId`를 반환하고, 2FA 검증 이후 남은 필수 조치를 다시 응답합니다.
`tokenIssuable=true`인 조치는 토큰 발급 이후 응답 메타데이터로 함께 내려줍니다.
응답에는 조치 존재 여부와 토큰 발급 가능 여부를 별도 boolean으로 제공합니다.

```text
tokenIssuable        -> 현재 로그인 상태에서 토큰 발급 호출이 가능한지 여부
requiredAction.exists -> action이 존재
requiredAction.action -> 다음 후속 조치
```

업무 정책성 체크는 Policy로 둡니다.
계정 상태, 로그인 방식 제한, 2FA 필요 여부, 비밀번호 변경 필요 여부처럼 앱별로 달라질 수 있는 판단이 여기에 해당합니다.
DB 조회 실패, 비밀번호 hash 불일치, Redis 세션 만료, JWT 검증 실패, 인증번호 값 검증 같은 인증/인프라 primitive 실패는 Service에서 처리합니다.

공통 정책 구현체는 다음과 같습니다.

```text
PasswordChangeRequiredActionPolicy
-> PASSWORD_CHANGE(tokenIssuable=false)
```

각 앱은 `LoginRequiredActionPolicy` Bean을 추가해 2FA, 단말 인증, 약관 동의, 개인정보 확인, MFA 등록 등 앱별 후속 조치를 더할 수 있습니다.
정책은 YAML 순서대로 평가하며, 내부적으로 여러 조치를 보관할 수 있습니다. 응답에는 현재 처리할 첫 번째 조치만 내려줍니다.

## Two Factor Authentication

2FA는 토큰 발급 전 단계입니다.
2FA 필요 여부도 `LoginRequiredActionPolicy` 구현체가 `VERIFY_2FA` 조치를 반환하는 방식으로 판단합니다.
`LoginCoreService.login()`은 `VERIFY_2FA`가 있으면 토큰 대신 `loginSessionId`와 `VERIFY_2FA` requiredAction을 반환합니다.

```text
LoginRequiredActionPolicy
LoginRequiredActionPolicyComposite
LoginSessionService
VerificationCodeService
```

인증번호는 `VerificationCodeService`가 생성/검증 규칙을 제공합니다.
로그인 2FA에서는 인증번호와 인증 완료 여부를 로그인 세션에 함께 저장합니다.
별도 API나 업무 서비스는 `LoginCoreService.issueTwoFactorCode(loginSessionId)`를 호출해 인증번호를 발급받고, SMS 발송은 해당 서비스에서 처리합니다.
인증번호 입력 검증은 `LoginCoreService.verifyTwoFactor(loginSessionId, verificationCode)`를 호출해 처리합니다.
업무 로직에서 법정 대리인 등 휴대폰 인증이 필요하면 같은 `VerificationCodeService`를 호출해 인증번호 발급/검증을 처리할 수 있습니다.

```text
login-session:{loginSessionId} -> {userId, userName, userType, verificationCode, verificationCompleted, requiredActions}
verification-code:{purpose}:{verificationId} -> {subject, verificationCode, verified}
TTL: 3m
```

실제 SMS, Email, App Push 발송은 별도 API나 업무 서비스가 담당합니다.
인증번호는 SMS 발송 등 내부 처리용 결과에만 포함하고, API 응답에는 포함하지 않습니다.

## User Info Cache

사용자 정보 캐시는 로그인 1차 인증 성공 시점과 2FA 검증 완료 시점에 미리 저장합니다.
ID/PW 인증 조회에서는 사용자명, 휴대폰번호 같은 화면 표시용 정보를 조회하지 않고, 비밀번호 검증 성공 후 별도 사용자 정보 조회 결과를 캐시에 저장합니다.
사용자 정보 조회 기능은 캐시를 먼저 조회하고, 캐시가 없거나 만료되면 앱별 DB 조회 결과를 다시 캐시에 저장합니다.

```text
LoginUserInfoCacheService
-> key: {userType}:{userId}
-> default TTL: 10m
-> current storage: in-memory ConcurrentHashMap
```

캐시 대상 정보는 JWT에 넣지 않는 화면 표시용 정보입니다.

```text
userId
userName
phoneNumber
userType
```

## Common Auth Type

공통 인증 방식은 `LoginAuthType`으로 제공합니다.
각 앱은 지원할 인증 방식을 정책으로 제한합니다.

```text
PASSWORD
BIOPASS
```

## Refresh Token Cookie

Refresh Token Cookie 처리는 `LoginRefreshTokenCookieService`가 담당합니다.

```text
getRefreshToken()
findRefreshToken()
createRefreshTokenCookie()
deleteRefreshTokenCookie()
```

## Biometric Challenge

신규 구축에서는 `deviceUuid`만으로 기기를 신뢰하지 않습니다.
모바일 생체인증은 OS가 수행하고, 서버는 기기 개인키로 서명된 challenge를 공개키로 검증합니다.

생체인증 challenge는 `LoginBiometricChallengeService`가 생성하고 Redis에 짧은 TTL로 저장합니다.

```text
auth-challenge:{challengeId} -> {nonce}
TTL: 3m
```

실제 Redis key에는 앱 prefix가 붙습니다.

```text
{appPrefix}:auth-challenge:{challengeId}
```

## Configuration

README에는 설정값을 그대로 복제하지 않습니다.
실제 값은 `application-login-core.yaml`과 각 앱 저장소의 `application-login.yaml`을 기준으로 확인합니다.
이 문서에서는 설정 항목의 의미만 설명합니다.

### Property Reference

`login-core.failure`

```text
max-count
-> 로그인 실패 허용 횟수입니다.
-> LoginFailureLimitPolicy가 이 값을 기준으로 계정 잠금 여부를 판단합니다.
```

`login-core.two-factor`

```text
challenge-time-to-live
-> 2FA 인증번호 자체의 유효시간입니다.
-> 발송 시점 기준으로 verificationCodeExpiresAt을 계산합니다.

session-time-to-live
-> login-session Redis key의 유지 시간입니다.
-> 인증번호 만료 후에도 세션을 잠깐 보존하여 "인증번호 만료"와 "세션 만료"를 구분하기 위한 값입니다.
-> challenge-time-to-live보다 길어야 합니다.

```

`login-core.biometric`

```text
challenge-time-to-live
-> 생체인증 challenge nonce의 유효시간입니다.
-> auth-challenge:{challengeId} 캐시 TTL로 사용합니다.
```

`login-core.token`

```text
access-time-to-live
-> Access Token 유효시간입니다.
-> Redis access token jti whitelist TTL도 같은 값을 사용합니다.

refresh-time-to-live
-> Refresh Token 유효시간입니다.
-> Redis refresh token jti whitelist TTL과 Refresh Token Cookie Max-Age에 사용합니다.
```

`login-core.user-info-cache`

```text
time-to-live
-> 사용자 정보 일반 캐시 TTL입니다.

stale-time-to-live
-> 일반 캐시 만료 후 stale 캐시를 추가로 유지하는 시간입니다.
-> 캐시 갱신 중인 요청이 오래 걸릴 때 stale 데이터를 임시 반환하는 데 사용합니다.

lock-time-to-live
-> 캐시 스탬피드 방지용 분산 lock TTL입니다.
-> 갱신 요청이 비정상 종료되어도 lock이 오래 남지 않도록 짧게 둡니다.

lock-wait-time
-> 다른 인스턴스가 캐시를 갱신 중일 때 대기할 최대 시간입니다.
-> 이 시간 안에 새 캐시가 채워지지 않으면 stale 캐시 반환을 시도합니다.

lock-retry-interval
-> lock 대기 중 일반 캐시를 다시 조회하는 간격입니다.
```

`login-core.cookie`

```text
refresh-token-name
-> Refresh Token Cookie 이름입니다.

same-site
-> Refresh Token Cookie SameSite 정책입니다.
-> 동일 호스트 Vue.js 구조에서는 Strict 또는 Lax를 우선 고려합니다.

path
-> Refresh Token Cookie path입니다.

secure
-> Refresh Token Cookie Secure 속성입니다.
-> HTTPS 환경에서는 활성화합니다.
```

`login-core.policy`

```text
completion
-> LoginCompletionPolicy 적용 whitelist입니다.
-> Spring bean name을 YAML 순서대로 적고, 해당 순서대로 실행합니다.

failure
-> LoginFailurePolicy 적용 whitelist입니다.
-> Spring bean name을 YAML 순서대로 적고, 해당 순서대로 실행합니다.

required-action
-> LoginRequiredActionPolicy 적용 whitelist입니다.
-> YAML 순서가 후속 조치 우선순위입니다.
-> 첫 번째로 반환된 조치 하나만 응답에 내려갑니다.
```
