package com.ktmmobile.mcp.requestReview.dao;

import java.util.List;

import com.ktmmobile.mcp.requestReview.dto.ReviewDataInfo;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.requestReview.dto.McpRequestReviewImgDto;
import com.ktmmobile.mcp.requestReview.dto.RequestReviewDto;

@Repository
public class RequestReviewDaoImpl implements RequestReviewDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public int selectUsimRequestReviewTotalCnt(RequestReviewDto requestReviewDto) {
        return (Integer)sqlSessionTemplate.selectOne("RequestReviewMapper.selectUsimRequestReviewTotalCnt", requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> selectUsimRequestReviewList(RequestReviewDto requestReviewDto,int skipResult,int maxResult) {
        return sqlSessionTemplate.selectList("RequestReviewMapper.selectUsimRequestReviewList", requestReviewDto,new RowBounds(skipResult, maxResult));
    }

    @Override
    public RequestReviewDto selectMcpRequestData(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("contractNum", contractNum);
        return restTemplate.postForObject(apiInterfaceServer + "/requestreview/mcpRequestData", params, RequestReviewDto.class); // RequestReviewMapper.selectMcpRequestData
    }

    @Override
    public RequestReviewDto selectMspJuoSubInfo(String contractNum) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("contractNum", contractNum);
        return restTemplate.postForObject(apiInterfaceServer + "/requestreview/RequestReviewDto", params, RequestReviewDto.class); // RequestReviewMapper.selectMspJuoSubInfo
    }

    @Override
    public int selectCustReviewCnt(String contractNum) {
        return (Integer)sqlSessionTemplate.selectOne("RequestReviewMapper.selectCustReviewCnt", contractNum);
    }

    @Override
    public int insertMcpRequestReview(RequestReviewDto requestReviewDto) {
        return (Integer)sqlSessionTemplate.insert("RequestReviewMapper.insertMcpRequestReview", requestReviewDto);
    }

    @Override
    public int insertMcpRequestReviewImg(McpRequestReviewImgDto mcpRequestReviewImgDto) {
        return (Integer)sqlSessionTemplate.insert("RequestReviewMapper.insertMcpRequestReviewImg", mcpRequestReviewImgDto);
    }


    @Override
    public boolean updateMcpRequestReview(RequestReviewDto requestReviewDto) {
        return     0 < sqlSessionTemplate.insert("RequestReviewMapper.updateMcpRequestReview",requestReviewDto);
    }

    @Override
    public boolean deleteMcpRequestReview(RequestReviewDto requestReviewDto) {
        return 0 < sqlSessionTemplate.delete("RequestReviewMapper.deleteMcpRequestReview",requestReviewDto);
    }
    @Override
    public boolean deleteMcpRequestReviewImg(RequestReviewDto requestReviewDto) {
        return 0 < sqlSessionTemplate.delete("RequestReviewMapper.deleteMcpRequestReviewImg",requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> selectMypageReviewList(RequestReviewDto requestReviewDto, int skipResult, int maxResult) {
        return sqlSessionTemplate.selectList("RequestReviewMapper.selectMypageReviewList", requestReviewDto, new RowBounds(skipResult, maxResult));
    }


    @Override
    public RequestReviewDto selectReviewLimit(RequestReviewDto requestReviewDto) {
        return sqlSessionTemplate.selectOne("RequestReviewMapper.selectReviewLimit", requestReviewDto);
    }

    @Override
    public List<McpRequestReviewImgDto> selectUsimRequestReviewImgList(RequestReviewDto requestReviewDto) {
        return sqlSessionTemplate.selectList("RequestReviewMapper.selectUsimRequestReviewImgList", requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> selectRequestReviewList(RequestReviewDto requestReviewDto, int skipResult,
            int maxResult) {
        return sqlSessionTemplate.selectList("RequestReviewMapper.selectRequestReviewList", requestReviewDto,new RowBounds(skipResult, maxResult));
    }


    @Override
    public List<RequestReviewDto> selectReviewDataList(ReviewDataInfo reviewDataInfo, int skipResult, int maxResult) {
        return sqlSessionTemplate.selectList("RequestReviewMapper.selectReviewDataList", reviewDataInfo, new RowBounds(skipResult, maxResult));
    }

    @Override
    public void modifyReviewHit(RequestReviewDto requestReviewDto) {

        sqlSessionTemplate.update("RequestReviewMapper.modifyReviewHit", requestReviewDto);

    }

    @Override
    public RequestReviewDto selectRequestReview(RequestReviewDto requestReviewDto) {
        return sqlSessionTemplate.selectOne("RequestReviewMapper.selectRequestReview", requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> reviewCodeList(RequestReviewDto requestReviewDto) {
        return sqlSessionTemplate.selectList("RequestReviewMapper.reviewCodeList", requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> reviewAnswerList(RequestReviewDto requestReviewDto) {
        return sqlSessionTemplate.selectList("RequestReviewMapper.reviewAnswerList", requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> reviewMakeList(RequestReviewDto requestReviewDto) {
         return sqlSessionTemplate.selectList("RequestReviewMapper.reviewMakeList", requestReviewDto);
    }

    @Override
    public int insertReviewMake(RequestReviewDto requestReviewDto) {
          return (Integer)sqlSessionTemplate.insert("RequestReviewMapper.insertReviewMake", requestReviewDto);
    }

    @Override
    public String selectUserInfo(String contractNum) {
        return sqlSessionTemplate.selectOne("RequestReviewMapper.selectUserInfo", contractNum);
    }

    @Override
    public List<RequestReviewDto> selectBestReviewList(RequestReviewDto requestReviewDto) {
          return sqlSessionTemplate.selectList("RequestReviewMapper.selectBestReviewList", requestReviewDto);
    }

    @Override
    public boolean deleteAnswerReview(RequestReviewDto requestReviewDto) {
          return 0 < sqlSessionTemplate.delete("RequestReviewMapper.deleteAnswerReview",requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> reviewIdList(RequestReviewDto requestReviewDto) {
        return sqlSessionTemplate.selectList("RequestReviewMapper.reviewIdList", requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> selectReviewList(RequestReviewDto requestReviewDto){
        return sqlSessionTemplate.selectList("RequestReviewMapper.selectReviewList", requestReviewDto);
    }





}
