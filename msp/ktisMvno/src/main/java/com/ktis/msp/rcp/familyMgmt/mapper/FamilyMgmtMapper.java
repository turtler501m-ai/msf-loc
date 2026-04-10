package com.ktis.msp.rcp.familyMgmt.mapper;

import com.ktis.msp.rcp.familyMgmt.vo.FamilyMemberVO;
import com.ktis.msp.rcp.familyMgmt.vo.FamilyMgmtVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

@Mapper("familyMgmtMapper")
public interface FamilyMgmtMapper {

	List<EgovMap> getFamilyRelList(FamilyMgmtVO vo);

	List<EgovMap> getFamilyRelListExcel(FamilyMgmtVO vo);

	List<EgovMap> getFamilyMemberList(FamilyMgmtVO vo);

	String isFamilyMemberParent(FamilyMemberVO vo);

	String isFamilyMemberChild(FamilyMemberVO vo);

	int countFamilyMemberParent(FamilyMemberVO familyMemberVO);

	int countFamilyMemberChild(FamilyMemberVO familyMemberVO);

	int insertFamilyRel(FamilyMgmtVO familyMgmtVO);

	int insertFamilyParent(FamilyMemberVO familyMemberVO);

	int insertFamilyChild(FamilyMemberVO familyMemberVO);

	int cancelFamilyRel(FamilyMgmtVO familyMgmtVO);

	int cancelFamilyAllMember(FamilyMemberVO familyMemberVO);

	int cancelFamilyMember(FamilyMemberVO familyMemberVO);
}
