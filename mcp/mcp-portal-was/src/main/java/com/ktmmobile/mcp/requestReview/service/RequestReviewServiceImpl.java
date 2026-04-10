package com.ktmmobile.mcp.requestReview.service;

import java.util.List;

import com.ktmmobile.mcp.requestReview.dto.ReviewDataInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.util.FileUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.requestReview.dao.RequestReviewDao;
import com.ktmmobile.mcp.requestReview.dto.McpRequestReviewImgDto;
import com.ktmmobile.mcp.requestReview.dto.RequestReviewDto;

@Service
public class RequestReviewServiceImpl implements RequestReviewService {

    @Autowired
    private RequestReviewDao requestReviewDao;

    @Autowired
    FileUtil fileUtil;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public int selectUsimRequestReviewTotalCnt(RequestReviewDto requestReviewDto) {
        return requestReviewDao.selectUsimRequestReviewTotalCnt(requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> selectUsimRequestReviewList(RequestReviewDto requestReviewDto,int skipResult,int maxResult) {
        return requestReviewDao.selectUsimRequestReviewList(requestReviewDto,skipResult,maxResult);
    }

    @Override
    public RequestReviewDto selectMcpRequestData(String contractNum) {
        return requestReviewDao.selectMcpRequestData(contractNum);
    }

    @Override
    public RequestReviewDto selectMspJuoSubInfo(String contractNum) {
        return requestReviewDao.selectMspJuoSubInfo(contractNum);
    }

    @Override
    public int selectCustReviewCnt(String contractNum) {
        return requestReviewDao.selectCustReviewCnt(contractNum);
    }

    @Override
    public int insertMcpRequestReview(RequestReviewDto requestReviewDto) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("contractNum", requestReviewDto.getContractNum());
        String soc = restTemplate.postForObject(apiInterfaceServer + "/requestreview/mcpJuoFeatSocInfo", params, String.class);
        requestReviewDto.setRateCd(soc);
        return requestReviewDao.insertMcpRequestReview(requestReviewDto);
    }

    @Override
    public int insertMcpRequestReviewImg(McpRequestReviewImgDto mcpRequestReviewImgDto) {
        return requestReviewDao.insertMcpRequestReviewImg(mcpRequestReviewImgDto);
    }


    @Override
    public List<RequestReviewDto> selectMypageReviewList(RequestReviewDto requestReviewDto, int skipResult, int maxResult) {
        return requestReviewDao.selectMypageReviewList(requestReviewDto, skipResult, maxResult);
    }

    @Override
    public RequestReviewDto selectReviewLimit(RequestReviewDto requestReviewDto) {
        // %퍼이상일때만 노출
        String strLimitPer = StringUtil.NVL(NmcpServiceUtils.getCodeNm("Constant",Constants.REVIEW_PERCENT),"0");
        int limitPer = Integer.parseInt(strLimitPer);
        requestReviewDto.setLimitPer(limitPer);
        return requestReviewDao.selectReviewLimit(requestReviewDto);
    }


    @Override
    public boolean updateMcpRequestReview(RequestReviewDto requestReviewDto) {
        return requestReviewDao.updateMcpRequestReview(requestReviewDto);
    }

    @Override
    public boolean deleteMcpRequestReview(RequestReviewDto requestReviewDto) {
        List<McpRequestReviewImgDto> reviewImgList = requestReviewDao.selectUsimRequestReviewImgList(requestReviewDto);
        if (requestReviewDao.deleteMcpRequestReview(requestReviewDto)) {
            requestReviewDto.setImdSeq(-1); //해당 후기 이미지 전체 삭제
            requestReviewDao.deleteMcpRequestReviewImg(requestReviewDto);
            for(McpRequestReviewImgDto reviewImg:reviewImgList){
                //실제 물리적경로에 해당 파일명을 삭제한다.
                  fileUtil.fileDelete(reviewImg.getFilePathNm());
              }

            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean deleteMcpRequestReviewImg(RequestReviewDto requestReviewDto) {

        List<McpRequestReviewImgDto> reviewImgList = requestReviewDao.selectUsimRequestReviewImgList(requestReviewDto);
        for(McpRequestReviewImgDto reviewImg:reviewImgList){
          //실제 물리적경로에 해당 파일명을 삭제한다.
            fileUtil.fileDelete(reviewImg.getFilePathNm());
        }

        return requestReviewDao.deleteMcpRequestReviewImg(requestReviewDto);



    }

    @Override
    public List<RequestReviewDto> selectRequestReviewList(RequestReviewDto requestReviewDto, int skipResult,
            int maxResult) {
        return requestReviewDao.selectRequestReviewList(requestReviewDto,skipResult,maxResult);
    }



    @Override
    public List<RequestReviewDto> selectReviewDataList(ReviewDataInfo reviewDataInfo, int skipResult, int maxResult) {
        return requestReviewDao.selectReviewDataList(reviewDataInfo,skipResult,maxResult);
    }

    @Override
    public void modifyReviewHit(RequestReviewDto requestReviewDto) {
        requestReviewDao.modifyReviewHit(requestReviewDto);

    }

    @Override
    public RequestReviewDto selectRequestReview(RequestReviewDto requestReviewDto) {
        return requestReviewDao.selectRequestReview(requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> reviewCodeList(RequestReviewDto requestReviewDto) {
        return requestReviewDao.reviewCodeList(requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> reviewAnswerList(RequestReviewDto requestReviewDto) {
        return requestReviewDao.reviewAnswerList(requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> reviewMakeList(RequestReviewDto requestReviewDto) {
         return requestReviewDao.reviewMakeList(requestReviewDto);
    }

    @Override
    public int insertReviewMake(RequestReviewDto requestReviewDto) {
         return requestReviewDao.insertReviewMake(requestReviewDto);
    }

    @Override
    public String selectUserInfo(String contractNum) {
         return requestReviewDao.selectUserInfo(contractNum);
    }

    @Override
    public List<RequestReviewDto> selectBestReviewList(RequestReviewDto requestReviewDto) {
         return requestReviewDao.selectBestReviewList(requestReviewDto);
    }

    @Override
    public boolean deleteAnswerReview(RequestReviewDto requestReviewDto) {
        return requestReviewDao.deleteAnswerReview(requestReviewDto);
    }

    @Override
    public List<RequestReviewDto> reviewIdList(RequestReviewDto requestReviewDto) {
        return requestReviewDao.reviewIdList(requestReviewDto);
    }
}
