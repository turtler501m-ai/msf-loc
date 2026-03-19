# /doc-update

코드 변경 후 `.doc/` 문서 업데이트 체크리스트를 확인합니다.

## 체크리스트

현재 변경된 코드를 기준으로 아래 항목을 확인하고 업데이트가 필요한 문서를 알려주세요.

### 1. 15번 문서 (`15.개발기능목록_ASIS_TOBE_기능분석명세서.md`)
- [ ] TOBE_Controller 컬럼에 실제 구현된 클래스명·메서드명 반영
- [ ] TOBE_Service 컬럼 업데이트
- [ ] TOBE_Mapper(DTO) 컬럼 업데이트
- [ ] TOBE_연동 컬럼 (미구현 연동은 "(연동 예정)" 표시)

### 2. 18번 문서 (`18.요구사항ID별_ASIS_TOBE_개발진행명세.md`)
- [ ] 해당 REQ 절의 TOBE Controller 항목 수정
- [ ] 구현 완료 상태 명시 ("구현 완료", "일부 구현됨" 등)
- [ ] 미구현 연동(MC0, X18 등) 예정 사항 명시
- [ ] 20절(미구현 항목)에서 해당 항목을 "일부 구현됨"으로 업데이트

### 3. 신규 도메인 패키지 생성 시 추가 확인
- [ ] `MsfApplication.java`의 `@MapperScan`에 새 패키지 등록 여부
- [ ] `mapper/` 아래 XML 파일 경로 (`mapper/{domain}/{MapperName}.xml`) 확인

## 관련 문서 경로
- `.doc/15.개발기능목록_ASIS_TOBE_기능분석명세서.md`
- `.doc/18.요구사항ID별_ASIS_TOBE_개발진행명세.md`
- `msf/msf-api/src/main/java/com/ktmmobile/msf/MsfApplication.java`
