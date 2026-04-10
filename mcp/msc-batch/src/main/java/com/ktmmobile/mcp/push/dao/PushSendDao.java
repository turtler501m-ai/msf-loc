package com.ktmmobile.mcp.push.dao;

import com.ktmmobile.mcp.push.dto.PushSndProcTrgtDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PushSendDao {

    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSessionTemplate bootoraSqlSession;


    public List<PushSndProcTrgtDto> getPushSendData(PushSndProcTrgtDto pushSndCond) {
        return bootoraSqlSession.selectList("PushMapper.getPushSendData", pushSndCond);
    }

    public void sendPushSendResult(PushSndProcTrgtDto pushSndProc) {
        bootoraSqlSession.update("PushMapper.sendPushSendResult", pushSndProc);
    }

    public void updateSendPushHist(PushSndProcTrgtDto pushSndProcTrgtDto) {
        bootoraSqlSession.update("PushMapper.updateSendPushHist", pushSndProcTrgtDto);
    }

    public List<PushSndProcTrgtDto> selectPushTargets(PushSndProcTrgtDto unitCnt) {
        return this.bootoraSqlSession.selectList("PushMapper.selectPushTargets", unitCnt);
    }

    public void updatePushStanding(PushSndProcTrgtDto pushSndProcTrgtDtos) {
        this.bootoraSqlSession.update("PushMapper.updatePushStanding", pushSndProcTrgtDtos);
    }

    public PushSndProcTrgtDto selectScope(int unitCnt) {
        return this.bootoraSqlSession.selectOne("PushMapper.selectTargetCnt", unitCnt);
    }

    public int selectPushAllCount() {
        return this.bootoraSqlSession.selectOne("PushMapper.selectPushAllCount");
    }

    public List<PushSndProcTrgtDto> selectPushAllTargets() {
        return this.bootoraSqlSession.selectList("PushMapper.selectPushAllTargets");
    }

    public void updatePushAllStanding() {
        this.bootoraSqlSession.update("PushMapper.updatePushAllStanding");
    }

    public PushSndProcTrgtDto selectSendPushInfo(String pushSndDd){
        return this.bootoraSqlSession.selectOne("PushMapper.selectSendPushInfo",pushSndDd);
    }

    public int updateSendPushInfo(String pushSndDd) {
        return this.bootoraSqlSession.update("PushMapper.updateSendPushInfo",pushSndDd);
    }

    public void completeSendPushInfo(PushSndProcTrgtDto pushSndProcTrgtDtos) {
        this.bootoraSqlSession.update("PushMapper.completeSendPushInfo",pushSndProcTrgtDtos);
    }

    public void deleteSendPushAll() {
        this.bootoraSqlSession.delete("PushMapper.deleteSendPushAll");
    }

    public void insertPushSndTrgtHist(String pushSndProcSno) {
        bootoraSqlSession.insert("PushMapper.insertPushSndTrgtHist", pushSndProcSno);
    }

}
