package com.ktmmobile.mcp.benefit.service;

import com.ktmmobile.mcp.benefit.dao.BenefitDao;
import com.ktmmobile.mcp.benefit.dto.BenefitTabViewDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BenefitServiceImpl implements BenefitService {

    public static final String MONTHLY_PAYMENT_AMOUNT = "MonthlyPaymentAmount";

    @Autowired
    private BenefitDao benefitDao;

    @Override
    public List<NmcpCdDtlDto> getMonthlyPaymentAmountList() {
        return NmcpServiceUtils.getCodeList(MONTHLY_PAYMENT_AMOUNT);
    }

    @Override
    public List<BenefitTabViewDto> getBenefitTabViewCountList() {
        return benefitDao.getBenefitTabViewCountList();
    }

    @Override
    public void increaseTabViewCount(String tabName) {
        switch (tabName) {
            case "shopping":
            case "card":
            case "coupon":
            case "market":
                benefitDao.increaseTabViewCount(tabName);
                break;
            default:
                throw new McpCommonJsonException("99999", "유효하지 않은 탭 이름입니다.");
        }
    }
}
