//////////package com.ktmmobile.mcp.cert.dao;
//////////
//////////import com.ktmmobile.mcp.cert.dto.CertDto;
//////////
//////////import java.util.List;
//////////import java.util.Map;
//////////
//////////public interface CertDao {
//////////	
//////////	//crtSeq 별 스텝 개수 가져오기
//////////	public int getStepCnt(long crtSeq);
//////////	
//////////	//같은 crtSeq의 인증정보 가져오기
//////////	public List<CertDto> getCompareList(CertDto certDto);
//////////	
//////////	//NMCP_CRT_VLD_DTL insert
//////////	public long insertCert(CertDto certDto);
//////////	
//////////	//MCP_USER_CNTR_MNG에서 계약번호 가져오기
//////////	public List<String> getContractNumByUserId(String userId);
//////////	
//////////	//NMCP_CRT_VLD_DTL delete
//////////	public int deleteCert(CertDto certDto);
//////////	
//////////	//NMCP_CRT_VLD_DTL merge
//////////	public int mergeCert(CertDto certDto);
//////////	
//////////	//NMCP_CRT_VLD_DTL crtSeq와 uploadPhoneSrlNo로 데이터 존재하는지 조회
//////////	public int getDataEsimSeq(long crtSeq);
//////////	
//////////	//NMCP_CRT_VLD_DTL uploadPhoneSrlNo update
//////////	public int updateEsimSeq(CertDto certDto);
//////////	
//////////	//NMCP_CRT_VLD_DTL 에서 파라미터 step 이상 delete
//////////	public int delStepInfo(CertDto certDto);
//////////
//////////	//NMCP_CRT_VLD_DTL nicePin update
//////////	int updateNicePin(CertDto certDto);
//////////
//////////	//한 시퀀스의 referer update
//////////	int updateCrtReferer(CertDto certDto);
//////////
//////////	//한 시퀀스에서 moduTyp 메인스텝 개수 get
//////////	int getModuTypeStepCnt(CertDto certDto);
//////////
//////////	// crtSeq에 해당하는 referer 조회
//////////	String getCertReferer(long crtSeq);
//////////
//////////	int getDataMobileNo(long crtSeq);
//////////
//////////	int updateMobileNo(CertDto certDto);
//////////}
