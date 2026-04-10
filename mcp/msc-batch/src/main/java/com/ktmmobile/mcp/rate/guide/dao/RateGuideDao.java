package com.ktmmobile.mcp.rate.guide.dao;

import com.ktmmobile.mcp.rate.guide.dto.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RateGuideDao {
    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSession bootoraSqlSession;


    public List<Integer> getRateAdsvcGdncSeqList() {
        return bootoraSqlSession.selectList("RateGuideMapper.getRateAdsvcGdncSeqList");
    }

    public RateAdsvcGdncBasXML getRateAdsvcGdncBasXml(int rateAdsvcGdncSeq) {
        return bootoraSqlSession.selectOne("RateGuideMapper.getRateAdsvcGdncBasXml", rateAdsvcGdncSeq);
    }

    public List<RateAdsvcBnfitGdncDtlXML> getRateAdsvcBnfitGdncDtlXmlList(int rateAdsvcGdncSeq) {
        return bootoraSqlSession.selectList("RateGuideMapper.getRateAdsvcBnfitGdncDtlXmlList", rateAdsvcGdncSeq);
    }

    public List<RateAdsvcGdncDtlXML> getRateAdsvcGdncDtlXmlList(int rateAdsvcGdncSeq) {
        return bootoraSqlSession.selectList("RateGuideMapper.getRateAdsvcGdncDtlXmlList", rateAdsvcGdncSeq);
    }

    public List<RateAdsvcGdncLinkDtlXML> getRateAdsvcGdncLinkDtlXmlList(int rateAdsvcGdncSeq) {
        return bootoraSqlSession.selectList("RateGuideMapper.getRateAdsvcGdncLinkDtlXmlList", rateAdsvcGdncSeq);
    }

    public List<RateGdncBannerDtlDTO> getRateAdsvcGdncBannerDtlXmlList(int rateAdsvcGdncSeq) {
        return bootoraSqlSession.selectList("RateGuideMapper.getRateAdsvcGdncBannerDtlXmlList", rateAdsvcGdncSeq);
    }

    public List<RateGdncPropertyDtlDTO> getRateGdncPropertyList(int rateAdsvcGdncSeq) {
        return bootoraSqlSession.selectList("RateGuideMapper.getRateGdncPropertyList", rateAdsvcGdncSeq);
    }

    public RateGdncEffPriceDtlDTO getRateGdncEffPriceList(int rateAdsvcGdncSeq) {
        return bootoraSqlSession.selectOne("RateGuideMapper.getRateGdncEffPriceList", rateAdsvcGdncSeq);
    }

    public List<Long> getNotLatestVersionSeqs() {
        return bootoraSqlSession.selectList("RateGuideMapper.getNotLatestVersionSeqs");
    }

    public int updateGdncVersionExpired(long seq) {
        return bootoraSqlSession.update("RateGuideMapper.updateGdncVersionExpired", seq);
    }

    public List<RateAdsvcGdncVersionDto> getExpiredVersions() {
        return bootoraSqlSession.selectList("RateGuideMapper.getExpiredVersions");

    }

    public int updateGdncVersionDeleted(long seq) {
        return bootoraSqlSession.update("RateGuideMapper.updateGdncVersionDeleted", seq);
    }

    public int updateGdncVersionFailed(RateAdsvcGdncVersionDto rateAdsvcGdncVersionDto) {
        return bootoraSqlSession.update("RateGuideMapper.updateGdncVersionFailed", rateAdsvcGdncVersionDto);
    }
}
