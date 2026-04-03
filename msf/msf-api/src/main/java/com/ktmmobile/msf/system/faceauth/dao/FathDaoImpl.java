package com.ktmmobile.msf.system.faceauth.dao;

import com.ktmmobile.msf.form.newchange.dto.AppformReqDto;
import com.ktmmobile.msf.system.faceauth.dto.FathDto;
import com.ktmmobile.msf.system.faceauth.dto.FathFormInfo;
import com.ktmmobile.msf.form.servicechange.dto.MyNameChgReqDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class FathDaoImpl implements FathDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Value("${api.interface.server}")
	private String apiInterfaceServer;

	/*셀프안면인증 URL 정보 조회*/
	@Override
	public FathDto getFathSelfUrl(String uuid) {
		return sqlSessionTemplate.selectOne("FathMapper.getFathSelfUrl", uuid);
	}

	/*안면인증 결과 PUSH 조회*/
	@Override
	public FathDto selectFathResltPush(String fathTransacId) {
		return sqlSessionTemplate.selectOne("FathMapper.selectFathResltPush", fathTransacId);
	}

	/*안면인증 트랜잭션 ID 업데이트*/
	@Override
	public void updateFathTransacId(AppformReqDto appformReqDto) {
		sqlSessionTemplate.update("FathMapper.updateFathTransacId", appformReqDto);
	}

	/*안면인증 인증일자 업데이트*/
	@Override
	public void updateFathCmpltNtfyDt(AppformReqDto appformReqDto) {
		sqlSessionTemplate.update("FathMapper.updateFathCmpltNtfyDt", appformReqDto);
	}

	/*셀프안면인증 신청서정보조회*/
	@Override
	public FathFormInfo getFathAppForm(String resNo) {
		return sqlSessionTemplate.selectOne("FathMapper.getFathAppForm", resNo);
	}

	/*명의변경 신청서정보조회*/
	@Override
	public FathFormInfo getNameChgInfo(String resNo) {
		return sqlSessionTemplate.selectOne("FathMapper.getNameChgInfo", resNo);
	}

	/*고객신분증정보 변경*/
	@Override
	public void updateSelfCertInfo(AppformReqDto appformReqDto) {
		sqlSessionTemplate.update("FathMapper.updateSelfCertInfo", appformReqDto);
	}
	/*고객신분증정보 변경(법정대리인)*/
	@Override
	public void updateSelfCertInfoMinor(AppformReqDto appformReqDto) {
		sqlSessionTemplate.update("FathMapper.updateSelfCertInfoMinor", appformReqDto);
	}

	@Override
	public void updateNameChgFathTransacId(AppformReqDto appformReqDto) {
		sqlSessionTemplate.update("FathMapper.updateNameChgFathTransacId", appformReqDto);
	}

	@Override
	public void updateNameChgComplete(AppformReqDto appformReqDto) {
		sqlSessionTemplate.update("FathMapper.updateNameChgComplete", appformReqDto);
	}

	@Override
	public String getCpntId(String cntpntShopId) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/appform/getCpntId", cntpntShopId, String.class);
	}

	/*(명의변경)고객신분증정보 변경*/
	@Override
	public void updateNameChgSelfCertInfo(MyNameChgReqDto myNameChgReqDto) {
		sqlSessionTemplate.update("FathMapper.updateNameChgSelfCertInfo", myNameChgReqDto);
	}
	/*(명의변경)고객신분증정보 변경(법정대리인)*/
	@Override
	public void updateNameChgSelfCertInfoMinor(MyNameChgReqDto myNameChgReqDto) {
		sqlSessionTemplate.update("FathMapper.updateNameChgSelfCertInfoMinor", myNameChgReqDto);
	}

	@Override
	public String generateTempResNo() {
		return sqlSessionTemplate.selectOne("FathMapper.generateTempResNo");
	}

	@Override
	public void updateFathMcpRequestOsst(FathDto fathDto) {
		sqlSessionTemplate.update("FathMapper.updateFathMcpRequestOsst", fathDto);
	}

	@Override
	public int updateNameChgFathSkip(String resNo) {
		return sqlSessionTemplate.update("FathMapper.updateNameChgFathSkip", resNo);
	}

}
