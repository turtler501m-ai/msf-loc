package com.ktmmobile.mcp.telCounsel.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.telCounsel.dto.TelCounselDtlDto;
import com.ktmmobile.mcp.telCounsel.dto.TelCounselDto;

@Repository
public class TelCounselDaoImpl implements TelCounselDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

	/* (non-Javadoc)
	 * @see com.ktmmobile.mcp.telcounsel.dao.TelCounselDao#selectTelCounsel(int)
	 */
	@Override
	public TelCounselDto selectTelCounsel(int counselSeq) {
		return sqlSessionTemplate.selectOne("TelCounselMapper.selectTelCounsel", counselSeq);
	}

	/* (non-Javadoc)
	 * @see com.ktmmobile.mcp.telcounsel.dao.TelCounselDao#updateTelCounselDtl(com.ktmmobile.mcp.telcounsel.dto.TelCounselDtlDto)
	 */
	@Override
	public int updateTelCounselDtl(TelCounselDtlDto telCounselDtlDto) {
		return sqlSessionTemplate.update("TelCounselMapper"
				+ ".updateTelCounselDtl", telCounselDtlDto);
	}

	/* (non-Javadoc)
	 * @see com.ktmmobile.mcp.telcounsel.dao.TelCounselDao#selectTelCounselBasList(com.ktmmobile.mcp.telcounsel.dto.TelCounselDto, com.ktmmobile.mcp.common.util.PageInfoBean)
	 */
	@Override
	public List<TelCounselDto> selectTelCounselBasList(
			TelCounselDto telCounselDto, PageInfoBean pageInfoBean) {

		 // 현재 페이지 번호 초기화
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }
		// 카운트 조회
		int total = (Integer) sqlSessionTemplate.selectOne("TelCounselMapper.selectTelCounselListCount", telCounselDto);
		pageInfoBean.setTotalCount(total);

		int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();
		int maxResult  = pageInfoBean.getRecordCount();

		return sqlSessionTemplate.selectList("TelCounselMapper.selectTelCounselList", telCounselDto,new RowBounds(skipResult, maxResult));
	}

	/* (non-Javadoc)
	 * @see com.ktmmobile.mcp.telcounsel.dao.TelCounselDao#deleteTelCounselDtlBySeq(int)
	 */
	@Override
	public int deleteTelCounselDtlBySeq(int counselSeq) {
		return sqlSessionTemplate.delete("TelCounselMapper.deleteTelCounselDtlBySeq", counselSeq);

	}

	/* (non-Javadoc)
	 * @see com.ktmmobile.mcp.telcounsel.dao.TelCounselDao#deleteTelCounselBasBySeq(int)
	 */
	@Override
	public int deleteTelCounselBasBySeq(int counselSeq) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("TelCounselMapper.deleteTelCounselBasBySeq", counselSeq);
	}

	/* (non-Javadoc)
	 * @see com.ktmmobile.mcp.telcounsel.dao.TelCounselDao#insertTelCounselDtl(com.ktmmobile.mcp.telcounsel.dto.TelCounselDtlDto)
	 */
	@Override
	public int insertTelCounselDtl(TelCounselDtlDto telCounselDtlDto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("TelCounselMapper.insertTelCounselDtl", telCounselDtlDto);
	}

	/* (non-Javadoc)
	 * @see com.ktmmobile.mcp.telcounsel.dao.TelCounselDao#updateLastCounselDtlSeq(int)
	 */
	@Override
	public int updateLastCounselDtlSeq(int counselSeq) {
		return sqlSessionTemplate.update("TelCounselMapper.updateLastCounselDtlSeq", counselSeq);
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
		return sqlSessionTemplate.insert("TelCounselMapper.insertCtiTelCounsel", telCounselDto);
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
		return (Integer)sqlSessionTemplate.selectOne("TelCounselMapper.selectCtiTelCounselCnt", telCounselDto);
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
		return (Integer)sqlSessionTemplate.selectOne("TelCounselMapper.selectCtiReqCnt", telCounselDto);
	}

}
