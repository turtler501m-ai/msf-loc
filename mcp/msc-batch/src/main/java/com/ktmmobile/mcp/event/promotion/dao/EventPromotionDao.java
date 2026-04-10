package com.ktmmobile.mcp.event.promotion.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class EventPromotionDao {

    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSession bootoraSqlSession;


    public int deleteExpiredJoinList() {
        return bootoraSqlSession.delete("deleteExpiredJoinList");
    }

    public int expireEventPromotion() {
        return bootoraSqlSession.update("expireEventPromotion");
    }
}
