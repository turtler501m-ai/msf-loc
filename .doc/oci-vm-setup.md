# Oracle Cloud VM.Standard.A1.Flex 자동 생성 설정

> 작성일: 2026-05-15  
> 목적: Free Tier ARM 인스턴스(VM.Standard.A1.Flex) 용량 부족 시 자동 재시도

---

## 설정 정보

| 항목 | 값 |
|------|----|
| 리전 | ap-chuncheon-1 (춘천) |
| Shape | VM.Standard.A1.Flex |
| OCPU | 4 |
| Memory | 24 GB |
| OS | Canonical Ubuntu 22.04 aarch64 |
| Availability Domain | FGUx:AP-CHUNCHEON-1-AD-1 |

### OCID 참조

| 항목 | OCID |
|------|----|
| Tenancy / Compartment | `ocid1.tenancy.oc1..aaaaaaaalothf7xvvx3ikjvckywbotogujezvdlijc5cykicuqyyulixzxoq` |
| Subnet | `ocid1.subnet.oc1.ap-chuncheon-1.aaaaaaaawlvdgpwaiz63uwp6wzj55scx5ba3qetgxh4ibefohzs5ymq5hbta` |
| Image (Ubuntu 22.04 aarch64, 2026.04.30) | `ocid1.image.oc1.ap-chuncheon-1.aaaaaaaauvxjy5tclxvl5nz34arovhumdt37bctdqf23sjuqrlr24vuthkvq` |

---

## 설치 과정 요약

### 1. OCI CLI 설치

Windows Long Path 활성화 필요 (관리자 권한):
```powershell
New-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Control\FileSystem" -Name "LongPathsEnabled" -Value 1 -PropertyType DWORD -Force
```

설치:
```powershell
(New-Object System.Net.WebClient).DownloadFile("https://raw.githubusercontent.com/oracle/oci-cli/master/scripts/install/install.ps1", "$env:TEMP\install.ps1")
powershell -ExecutionPolicy Bypass -File "$env:TEMP\install.ps1" -AcceptAllDefaults
```

PATH 등록:
```powershell
[Environment]::SetEnvironmentVariable("PATH", "$env:USERPROFILE\bin;" + [Environment]::GetEnvironmentVariable("PATH", "User"), "User")
```

### 2. OCI API Key 설정

- `oci setup config` 실행 (대화형 설정)
- 생성된 공개키(`~/.oci/oci_api_key_public.pem`)를 OCI 콘솔 → 내 프로파일 → API 키에 등록
- config 파일: `C:\Users\bok\.oci\config`
- 키 파일: `C:\Users\bok\.oci\oci_api_key.pem` (패스프레이즈 없음)

> **주의**: `Set-Content`로 config 저장 시 UTF-16 BOM 문제 발생.  
> 반드시 `[IO.File]::WriteAllText($path, $content, [Text.UTF8Encoding]::new($false))` 사용.

### 3. SSH 키 생성

```powershell
ssh-keygen -t ed25519 -f "$env:USERPROFILE\.ssh\id_ed25519" -N '""' -C "oci-instance"
```

---

## 재시도 스크립트

파일 위치: `C:\Users\bok\oci-retry.ps1`

실행:
```powershell
powershell -ExecutionPolicy Bypass -File "C:\Users\bok\oci-retry.ps1"
```

- 용량 부족(Out of capacity) 시 60초 후 자동 재시도
- 성공 시 인스턴스 정보 출력 후 종료

---

## 트러블슈팅

| 오류 | 원인 | 해결 |
|------|------|------|
| Long Path 오류 | Windows 경로 260자 제한 | HKLM LongPathsEnabled=1 설정 |
| `oci` 명령어 없음 | PATH 미등록 | `$env:PATH = "$env:USERPROFILE\bin;"+$env:PATH` |
| MissingSectionHeaderError | config BOM 문제 | UTF-8 without BOM으로 재저장 |
| UnicodeEncodeError | 패스프레이즈 특수문자 | 패스프레이즈 없는 새 키 재생성 |
| NotAuthenticated | region 불일치 | config region → ap-chuncheon-1 |
| shape_config 오류 | JSON 이스케이프 오류 | `{\"ocpus\":4,\"memoryInGBs\":24}` 형식 사용 |
