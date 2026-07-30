package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.datasource.smartform.SmartFormDataSourceConfig;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.MsfRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.ProductInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.ProductSmartInfoWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDocVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestRecVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.ProductInventoryRequest;
import com.ktmmobile.msf.domains.form.form.newchange.service.ProductInfoService;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeFormInfoResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.field.OwnerChangeFieldMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerChgMsfSvc {

    private final MsfRequestRepositoryImpl msfRequestRepository;
    private final OwnerChangeFieldMapper ownerChangeFieldMapper;
    private final ProductSmartInfoWriteMapper productSmartInfoWriteMapper;
    private final ProductInfoReadMapper productInfoReadMapper;
    private final ProductInfoService productInfoService;
    private final OwnerChgMcpSvc ownerChgMcpSvc;

    @Transactional(transactionManager = SmartFormDataSourceConfig.SMARTFORM_TX_MANAGER)
    public void save(MsfRequestNameChgVo request) {

        log.info("명의변경 msf 테이블 insert *********");
        log.info("명의변경 paramter MsfRequestNameChgVo => {}", request);

        //
        // /*** msf 데이터 저장  ***/
        //

        // 유심 구매인 경우 (가족 간 승계만 유심 구매 가능)
        if (
            (
                StringUtils.hasText(request.getUsimSn())
                    && !"Y".equals(request.getUsimSuccYn())
            ) || StringUtils.hasText(request.getSoc())
        ) {

            request.setUsimNm(productInfoService.getUsimModelNm(request.getIccId()));

            // 가입비 및 유심비 조회
            PriceJoinUsimRequest priceJoinUsimRequest = new PriceJoinUsimRequest();
            priceJoinUsimRequest.setDataType(request.getDataType());
            priceJoinUsimRequest.setUsimKindsCd(request.getUsimKindCd());
            priceJoinUsimRequest.setPriceGubun("NAC3" + request.getDataType());
            PriceJoinUsimResponse response = productInfoReadMapper.selectJoinUsimPrice(priceJoinUsimRequest);
            MsfRequestSaleVo vo = ownerChangeFieldMapper.toMsfRequestSaleVo(request);

            if (StringUtils.hasText(request.getUsimSn())) {
                vo.setUsimPrice(Long.parseLong(response.getSimPrice()));

                // 재고정리
                //신청서 등록 완료한 경우 휴대폰/USIM 재고 MSF_PROD_STOR_INVENTORY_TXN 재고 데이터 ‘접수완료’로 변경 처리
                //use_sttus_cd  = ‘R’ ----(N : 미사용 / R : 접수완료 / A : 사용완료)
                ProductInventoryRequest productInventoryRequest = new ProductInventoryRequest();
                String agentCd = request.getAgentCd();
                String reqUsimSn = request.getUsimSn(); //유심 일련번호 (구매한 경우에만)

                productInventoryRequest.setUseSttusCd("R"); //접수완료
                productInventoryRequest.setAgentCd(agentCd); //신청서에 저장된 조직코드
                productInventoryRequest.setProdSn(reqUsimSn);
                productSmartInfoWriteMapper.updateMsfProdStorInventoryTxn(productInventoryRequest);
            }

            MspSaleSubsdMstRequest mspSaleSubsdMstRequest = new MspSaleSubsdMstRequest();
            mspSaleSubsdMstRequest.setAgentCd(request.getCntpntShopCd());
            mspSaleSubsdMstRequest.setRateCd(request.getSoc());
            vo.setDisPrmtAmt(ownerChgMcpSvc.ownerChangeFormPrmtAmtGet(mspSaleSubsdMstRequest));
            request.setDisPrmtId(mspSaleSubsdMstRequest.getDisPrmtId());
            msfRequestRepository.insertMsfRequestSale(vo);

        }

        // 명의변경신청정보 저장
        msfRequestRepository.insertMsfRequestNameChg(request);
        // 명의변경양도인정보 저장
        msfRequestRepository.insertMsfRequestNameTrns(request);

        // 가입신청정보 저장
        MsfRequestCstmrVo msfRequestCstmrVo = ownerChangeFieldMapper.toMsfRequestCstmrVo(request);
        List<String> list = List.of("FM", "FN");
        if (list.contains(request.getCstmrTypeCd())) {
            msfRequestCstmrVo.setCstmrNativeRrn(null);
        }

        log.info("명의변경 가입신청정보 저장 msfRequestCstmrVo => {}", msfRequestCstmrVo);
        msfRequestRepository.insertMsfRequestCstmr(msfRequestCstmrVo);

        List<String> government = List.of("JP", "GO"); // 법인, 공공기관

        // 법인, 공공기관 대리인인 경우
        if (government.contains(request.getCstmrTypeCd()) && "VDP".equals(request.getCstmrVisitTypeCd())) {
            MsfRequestAgentVo msfRequestAgentVo = ownerChangeFieldMapper.toMsfRequestAgentVo(request);
            msfRequestAgentVo.setCstmrTypeCode(request.getCstmrTypeCd());
            msfRequestAgentVo.setupData();
            msfRequestRepository.insertMsfRequestAgent(msfRequestAgentVo);
        }

        // 가입신청대리인정보 저장(미성년자인 경우)
        if ("NM".equals(request.getTrnsCstmrTypeCd()) || "NM".equals(request.getCstmrTypeCd())
            || "FM".equals(request.getTrnsCstmrTypeCd()) || "FM".equals(request.getCstmrTypeCd())
        ) {
            MsfRequestAgentVo msfRequestAgentVo = ownerChangeFieldMapper.toMsfRequestAgentVo(request);
            msfRequestAgentVo.setCstmrTypeCode(request.getCstmrTypeCd());
            msfRequestAgentVo.setupData();
            log.info("명의변경 가입신청대리인정보 저장 msfRequestAgentVo => {}", msfRequestAgentVo);
            msfRequestRepository.insertMsfRequestAgent(msfRequestAgentVo);
        }
        // 가입신청청구신청정보 저장
        MsfRequestBillReqVo msfRequestBillReqVo = ownerChangeFieldMapper.toMsfRequestBillReqVo(request);
        log.info("명의변경 가입신청청구신청정보 저장 msfRequestBillReqVo => {}", msfRequestBillReqVo);
        msfRequestRepository.insertMsfRequestBillReq(msfRequestBillReqVo);


        // 가입신청약관동의정보
        //MsfRequestClauseVo clauseVo = ownerChangeFieldMapper.toMsfRequestClauseVo(request);
        // msfRequestRepository.insertMsfRequestClause(clauseVo);

        // 가입신청수신방법정보
        //MsfRequestJoinFormVo joinFormVo = ownerChangeFieldMapper.toMsfRequestJoinFormVo(request);
        // msfRequestRepository.insertMsfRequestJoinForm(joinFormVo);
        // 가입신청구비서류정보
        for (MsfRequestNameChgVo.RequestDocList doc: request.getMsfRequestDocList()) {
            MsfRequestDocVo docVo = ownerChangeFieldMapper.toMsfRequestDocVo(doc);
            docVo.setRequestKey(request.getRequestKey());
            log.info("명의변경 가입신청구비서류정보 저장 docVo => {}", docVo);
            msfRequestRepository.insertMsfRequestDoc(docVo);
        }
        // 가입신청녹취정보
        MsfRequestRecVo recVo = ownerChangeFieldMapper.toMsfRequestRecVo(request);
        log.info("명의변경 가입신청녹취정보 저장 recVo => {}", recVo);
        msfRequestRepository.insertMsfRequestRec(recVo);

    }

    public OwnerChangeFormInfoResponse selectOwnerChgInfo(Long requestKey) {
        return msfRequestRepository.selectMsfRequestOwnerChgInfo(requestKey);
    }

    public void delete(Long requestKey) {
        // 명의변경신청정보 삭제
        msfRequestRepository.deleteMsfRequestNameChg(requestKey);
        // 명의변경양도인정보 삭제
        msfRequestRepository.deleteMsfRequestNameTrns(requestKey);
        // 가입신청정보 삭제
        msfRequestRepository.deleteMsfRequestCstmr(requestKey);
        // 가입신청대리인정보 삭제(미성년자인 경우)
        msfRequestRepository.deleteMsfRequestAgent(requestKey);
        // 가입신청청구신청정보 삭제
        msfRequestRepository.deleteMsfRequestBillReq(requestKey);
        // 가입신청수신방법정보
        msfRequestRepository.deleteMsfRequestJoinForm(requestKey);
        // 가입신청구비서류정보
        msfRequestRepository.deleteMsfRequestDoc(requestKey);
        // 가입신청녹취정보
        msfRequestRepository.deleteMsfRequestRec(requestKey);
    }
}
