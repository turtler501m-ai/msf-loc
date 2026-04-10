package com.ktmmobile.mcp.renew.dao;

import com.ktmmobile.mcp.renew.dto.RenewReserveDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RenewReserveDao {

    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSession bootoraSqlSession;


    public List<RenewReserveDto> getRenewReserveList(String reserveDate) {
        return bootoraSqlSession.selectList("RenewReserveMapper.getRenewReserveList", reserveDate);
    }

    public int updateRenewReservePending(int renewSeq) {
        return bootoraSqlSession.update("RenewReserveMapper.updateRenewReservePending", renewSeq);
    }

    public int updateRenewReserveSuccess(RenewReserveDto renewReserve) {
        return bootoraSqlSession.update("RenewReserveMapper.updateRenewReserveSuccess", renewReserve);
    }

    public int updateRenewReserveFail(RenewReserveDto renewReserve) {
        return bootoraSqlSession.update("RenewReserveMapper.updateRenewReserveFail", renewReserve);
    }
}
