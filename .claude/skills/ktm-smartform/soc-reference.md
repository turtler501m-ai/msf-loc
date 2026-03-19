# SOC 코드 레퍼런스 (부가서비스)

| SOC | 명칭 | 연동 API | 비고 |
|-----|------|----------|------|
| WIRELESSC | 무선데이터차단서비스 | X21/X38 | 서비스변경 WIRELESS_BLOCK |
| NOSPAM4 | 불법TM수신차단 | X21 + ftrNewParam | 팝업 설정 필요 |
| STLPVTPHN | 번호도용 차단 서비스 | X21 + ftrNewParam | 팝업 설정 필요 |
| PL2078760 | (신)로밍 하루종일ON | X21 + ftrNewParam | 팝업 설정 필요 |
| PL249Q800 | MVNO마스터결합전용더미 | - | 아무나SOLO 내부용, 목록 제외 |
| MPAYBLOCK | 휴대폰결제 이용거부 | X21/X38 | - |
| NOSPAM3 | 정보제공사업자번호차단 | X21/X38 | - |
