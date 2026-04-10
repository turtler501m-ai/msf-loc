package com.ktmmobile.mcp.telCounsel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.telCounsel.dao.TelCounselDao;
import com.ktmmobile.mcp.telCounsel.dto.TelCounselDtlDto;
import com.ktmmobile.mcp.telCounsel.dto.TelCounselDto;

@Service
public class TelCounselServiceImpl implements TelCounselService {

	@Autowired
	TelCounselDao telCounselDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ktmmobile.mcp.telcounsel.service.TelCounselService#
	 * findTelCounselByCounselSeq(int)
	 */
	@Override
	public TelCounselDto findTelCounselByCounselSeq(int counselSeq) {
		return telCounselDao.selectTelCounsel(counselSeq);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ktmmobile.mcp.telcounsel.service.TelCounselService#listTelCounsel
	 * (com.ktmmobile.mcp.telcounsel.dto.TelCounselDto)
	 */
	@Override
	public List<TelCounselDto> listTelCounsel(TelCounselDto telCounselDto) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ktmmobile.mcp.telcounsel.service.TelCounselService#modifyTelCounselDto
	 * (com.ktmmobile.mcp.telcounsel.dto.TelCounselDtlDto)
	 */
	@Override
	public int modifyTelCounselDto(TelCounselDtlDto telCounselDtlDto) {
		return telCounselDao.updateTelCounselDtl(telCounselDtlDto);
	}

	/* (non-Javadoc)
	 * @see com.ktmmobile.mcp.telcounsel.service.TelCounselService#listTelCounselBas(com.ktmmobile.mcp.telcounsel.dto.TelCounselDto, com.ktmmobile.mcp.common.util.PageInfoBean)
	 */
	@Override
	public List<TelCounselDto> listTelCounselBas(TelCounselDto telCounselDto,
			PageInfoBean pageInfoBean) {

		return telCounselDao.selectTelCounselBasList(telCounselDto,pageInfoBean);
	}

	/* (non-Javadoc)
	 * @see com.ktmmobile.mcp.telcounsel.service.TelCounselService#removeTelCounselBasWithDtlList(int)
	 */
	@Override
	@Transactional
	public int removeTelCounselBasWithDtlList(int counselSeq) {
		int r1 = telCounselDao.deleteTelCounselDtlBySeq(counselSeq);
		int r2 = telCounselDao.deleteTelCounselBasBySeq(counselSeq);

		return r1 + r2;
	}

	/* (non-Javadoc)
	 * @see com.ktmmobile.mcp.telcounsel.service.TelCounselService#insertTelCounselDtl(com.ktmmobile.mcp.telcounsel.dto.TelCounselDtlDto)
	 */
	@Override
	@Transactional
	public int insertTelCounselDtl(TelCounselDtlDto telCounselDtlDto) {
		telCounselDao.updateLastCounselDtlSeq(telCounselDtlDto.getCounselSeq());
		return telCounselDao.insertTelCounselDtl(telCounselDtlDto);
	}

	/* (non-Javadoc)
	 * @see com.ktmmobile.mcp.telcounsel.service.TelCounselService#modifyTelCounselDtlDto(com.ktmmobile.mcp.telcounsel.dto.TelCounselDtlDto)
	 */
	@Override
	public int modifyTelCounselDtlDto(TelCounselDtlDto telCounselDtlDto) {
		// TODO Auto-generated method stub
		return telCounselDao.updateTelCounselDtl(telCounselDtlDto);
	}

	/**
	* @Description : 전화상담 신청정보 저장
	* @param telCounselDto
	* @return
	* @Author
	* @Create Date : 2019. 3. 20.
	*/
	@Override
	public int insertCtiTelCounsel(TelCounselDto telCounselDto) {
		// TODO Auto-generated method stub
		return telCounselDao.insertCtiTelCounsel(telCounselDto);
	}

	/**
	* @Description : 전화상담 30일이내 신청정보 확인
	* @param telCounselDto
	* @return
	* @Author
	* @Create Date : 2019. 3. 20.
	*/
	@Override
	public int selectCtiTelCounselCnt(TelCounselDto telCounselDto) {
		// TODO Auto-generated method stub
		return telCounselDao.selectCtiTelCounselCnt(telCounselDto);
	}

	/**
	* @Description : 직영온라인 30일 이내 신청정보 확인
	* @param telCounselDto
	* @return
	* @Author
	* @Create Date : 2019. 3. 20.
	*/
	@Override
	public int selectCtiReqCnt(TelCounselDto telCounselDto) {
		// TODO Auto-generated method stub
		return telCounselDao.selectCtiReqCnt(telCounselDto);
	}
}
