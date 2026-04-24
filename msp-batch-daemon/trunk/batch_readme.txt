* CMN_BATCH_INFO 테이블에 먼저 등록 후 실행가능함. (공통으로 배치로그를 생성하기 위함)

1. BatchLoader.java main 실행
	< 전체 스케줄러 실행 >
	 * args1 - batch.server.type (local, dev, stg, live)
	 * args2 - batch.run.type (schedule)
	
	< 하나 별도 실행 >
	 * args1 - batch.server.type (local, dev, stg, live)
	 * args2 - batch.run.type (onetime)
	 * args3 - batch.load.package (bnd, cls, lgs, org, ptnr) --> com.ktis.msp.batch.job 하위의 package명
	 * args4 - 실행할 스케줄러 class명 (BondSchedule, LgsInvtrDaySumSchedule, .....)
	
2. 기존 사용되던 각 업무별 로그(CLS, LGS..)를 공통배치로그(CMN_BATCH_EXEC_HST) 사용하도록 함.
3. Quartz 에서 Spring Schedule로 변경.
	- Quartz에서는 arguments 사용가능했지만, Schedule 파일 분리해서 사용하도록 함.


개발계에서는 runnable jar로 말아서 실행 shell을 작성하여 호출한다.
