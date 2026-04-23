package com.ktmmobile.msf.domains.form.form.newchange.service;

import com.ktmmobile.msf.domains.form.form.newchange.dto.MsfNewChangeInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoCondition;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeFieldMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class NewChangeService {

    private final FormCommService formCommService;
    private final NewChangeReadMapper newChangeReadMapper;
    private final NewChangeWriteMapper newChangeWriteMapper;

    //신청서 조회
    //public List<MsfNewChangeInfoDto> getNewChangeInfo(NewChangeInfoCondition condition) {
    public MsfNewChangeInfoDto getNewChangeInfo(NewChangeInfoCondition condition) {
        //List<MsfNewChangeInfoDto> msfNewchangeInfoDtoList = new ArrayList<>();
        //MsfNewChangeInfoDto msfNewchangeInfoDto = new MsfNewChangeInfoDto();
        MsfNewChangeInfoDto msfNewchangeInfoDto = new MsfNewChangeInfoDto();

        //Constant 처리필요함.
        /*newChangeInfoDto.setFormTypeCd("1"); //신청서유형 : 1-신규/변경
        newChangeInfoDto.setServiceTypeCd("PO"); //후불 (고정)
        newChangeInfoDto.setReqBuyTypeCd("MM"); //상품 (핸드폰)
        newChangeInfoDto.setOperTypeCd("MNP3"); //가입유형 (번호이동)
        newChangeInfoDto.setCstmrType("NA"); //고객유형
        newChangeInfoDto.setIdentityCertTypeCd("K"); //신분증확인방법*/

        //newChangeInfoDtoList.add(newChangeInfoDto);


        String msfRequestKey = "";
        String formTypeCd = "";
        Integer requestKey;
        MsfRequestVo msfRequestVo = new MsfRequestVo();
        MsfRequestCstmrVo msfRequestCstmrVo = new MsfRequestCstmrVo();
        MsfRequestAgentVo msfRequestAgentVo = new MsfRequestAgentVo();
        MsfRequestSaleVo msfRequestSaleVo = new MsfRequestSaleVo();
        MsfRequestBillReqVo msfRequestBillReqVo = new MsfRequestBillReqVo();

        if (StringUtils.hasText(condition.getMsfRequestKey())) {
            msfRequestKey = condition.getMsfRequestKey();
            condition.setRequestKey(Long.parseLong(msfRequestKey));

            msfRequestVo = this.getMsfRequestInfo(condition); //MSF_REQUEST
            msfRequestCstmrVo = this.getMsfRequestCstmrInfo(condition); //MSF_REQUEST_CSTMR
            msfRequestAgentVo = this.getMsfRequestAgentInfo(condition); //MSF_REQUEST_AGENT
            msfRequestSaleVo = this.getMsfRequestSaleInfo(condition); //MSF_REQUEST_SALE
            msfRequestBillReqVo = this.getMsfRequestBillReqInfo(condition); //MSF_REQUEST_BILL_REQ
        }

        //ModelMapper modelMapper = new ModelMapper();
        //msfNewchangeInfoDtoList.add(msfRequestDto);
        msfNewchangeInfoDto.setMsfRequestVo(msfRequestVo);
        msfNewchangeInfoDto.setMsfRequestCstmrVo(msfRequestCstmrVo);
        msfNewchangeInfoDto.setMsfRequestAgentVo(msfRequestAgentVo);
        //msfNewchangeInfoDto.add(msfNewchangeInfoDto);

        return msfNewchangeInfoDto;
    }

    //MSF_REQUEST 조회
    public MsfRequestVo getMsfRequestInfo(NewChangeInfoCondition condition) {
        return newChangeReadMapper.selectMsfRequestInfo(condition);
    }

    //MSF_REQUEST_CSTMR 조회
    public MsfRequestCstmrVo getMsfRequestCstmrInfo(NewChangeInfoCondition condition) {
        return newChangeReadMapper.selectMsfRequestCstmrInfo(condition);
    }
    /*public MsfRequestCstmrDto getMsfRequestCstmrInfo(Integer requestKey) {
        return Optional.ofNullable(newchangeInfoMapper.selectMsfRequestCstmrInfo(requestKey))
                .orElseThrow(() -> new NotFoundException("SmartForm 신청서 정보가 없습니다. requestKey:" + requestKey));
    }*/

    //MSF_REQUEST_AGENT 조회
    public MsfRequestAgentVo getMsfRequestAgentInfo(NewChangeInfoCondition condition) {
        return newChangeReadMapper.selectMsfRequestAgentInfo(condition);
    }
    /*public MsfRequestAgentDto getMsfRequestAgentInfo(Integer requestKey) {
        return Optional.ofNullable(newchangeInfoMapper.selectMsfRequestAgentInfo(requestKey))
                .orElseThrow(() -> new NotFoundException("SmartForm 신청서 정보가 없습니다. requestKey:" + requestKey));
    }*/

    //MSF_REQUEST_SALE 조회
    public MsfRequestSaleVo getMsfRequestSaleInfo(NewChangeInfoCondition condition) {
        return newChangeReadMapper.selectMsfRequestSaleInfo(condition);
    }
    /*public MsfRequestSaleDto getMsfRequestSaleInfo(Integer requestKey) {
        return Optional.ofNullable(newchangeInfoMapper.selectMsfRequestSaleInfo(requestKey))
                .orElseThrow(() -> new NotFoundException("SmartForm 신청서 정보가 없습니다. requestKey:" + requestKey));
    }*/

    //MSF_REQUEST_BILL_REQ 조회
    public MsfRequestBillReqVo getMsfRequestBillReqInfo(NewChangeInfoCondition condition) {
        return newChangeReadMapper.selectMsfRequestBillReqInfo(condition);
    }
    /*public MsfRequestBillReqDto getMsfRequestBillReqInfo(Integer requestKey) {
        return Optional.ofNullable(newchangeInfoMapper.selectMsfRequestBillReqInfo(requestKey))
                .orElseThrow(() -> new NotFoundException("SmartForm 신청서 정보가 없습니다. requestKey:" + requestKey));
    }*/

    //신청서 저장
    @Transactional
    public String saveAppformInfo(NewChangeInfoRequest request) {
        long requestKey = 0;

        if (request.getRequestKey() == null) {
            //INSERT
            requestKey = formCommService.generateRequestKey(); //신청서번호 생성 테스트
            request.setRequestKey(requestKey);
        }

        MsfRequestVo msfRequestVo = NewChangeFieldMapper.INSTANCE.toMsfRequestVo(request);
        MsfRequestAgentVo msfRequestAgentVo = NewChangeFieldMapper.INSTANCE.toMsfRequestAgentVo(request);
        MsfRequestCstmrVo msfRequestCstmrVo = NewChangeFieldMapper.INSTANCE.toMsfRequestCstmrVo(request);
        MsfRequestSaleVo msfRequestSaleVo = NewChangeFieldMapper.INSTANCE.toMsfRequestSaleVo(request);
        MsfRequestBillReqVo msfRequestBillReqVo = NewChangeFieldMapper.INSTANCE.toMsfRequestBillReqVo(request);

        //if (!StringUtils.hasText(request.getRequestKey().toString())) {
        if (requestKey > 0) {
            newChangeWriteMapper.insertMsfRequestTemp(msfRequestVo); //MSF_REQUEST
            newChangeWriteMapper.insertMsfRequestAgentTemp(msfRequestAgentVo); //MSF_REQUEST_AGENT
            newChangeWriteMapper.insertMsfRequestCstmrTemp(msfRequestCstmrVo); //MSF_REQUEST_CSTMR
            newChangeWriteMapper.insertMsfRequestSaleTemp(msfRequestSaleVo); //MSF_REQUEST_SALE
            newChangeWriteMapper.insertMsfRequestBillReqTemp(msfRequestBillReqVo); //MSF_REQUEST_BILL_REQ
        } else {
            //UPDATE
            request.setRequestKey(request.getRequestKey());
            newChangeWriteMapper.updateMsfRequestTemp(request);
            newChangeWriteMapper.updateMsfRequestAgentTemp(request);
            newChangeWriteMapper.updateMsfRequestCstmrTemp(request);
            newChangeWriteMapper.updateMsfRequestSaleTemp(request);
            newChangeWriteMapper.updateMsfRequestBillReqTemp(request);
        }

        return Long.toString(requestKey);

        //임시값?
        // request.setFormTypeCd("1"); // 신규/변경 신청서코드 정의 (Constant 화 처리 필요)
        // request.setServiceTypeCd("PO"); //후불 (고정)
        // request.setReqBuyTypeCd("MM"); //상품 (핸드폰)
        // request.setOperTypeCd("MNP3"); //가입유형 (번호이동)
        // request.setCstmrType("NA"); //고객유형
        // request.setIdentityCertTypeCd("K"); //신분증확인방법

        // 상품, 가입유형, 고객유형, 신분증확인, 고객명, 고객식별번호 필수값 체크
        // 예외요금제확인 후 직영(1100011741) & 신규(NAC3) & 상담사신청서(ON_OFF_TYPE:0,3) & ( eSIM 또는 USIM 번호가 있는 경우) 에
        //                고객명/셀프개통을 위한 고객CI(NICE에서 발급?)/USIM유형으로 신규개통이력 체크하여 이력정보저장 ( insertAdminAccessTrace )
        //                (스마트는 NICE 정보가 아닌 신분증 확인으로 대체해야하나)
        // 인가된 사용자 체크 ( RprsPrdtId - E0000003 가 아닌 것 )
        //                유심구매는 이름만 체크
        //                핸드폰구매는 이름과 주민번호 필수체크 (법인은 법인명과 법인번호, 공공기관은 기관명과 기관코드 ?)
        //                M포탈 기준으로 회원이 정회원이라면 KTMvno 가입자이므로 api 의 /mypage/cntrList 에서 고객정보 조회. (MCP 휴대폰 회선관리 리스트를 가져옴)
        //                이때 기기변경의 고객정보, 외국인의 고객정보는 유의하여 조회할 것
        // eSIM 확인 : fnSetDataOfeSim(appformReqDto);
        // 동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회 : /appform/checkLimitOpenFormCount
        //                지정한 이력제한이 100 으로 해당 수치를 넘을 경우 이력정보 저장 ( insertAdminAccessTrace )
        // 청소년 나이 인증 , 내국인 성인 인증
        //                스마트는 내국인 성인 및 외국인 성인의 나이 체크, 내국인 및 외국인의 미성년자 나이체크
        // 골드번호 입력 확인 : 신규가입에 한함
        //                appformSvc.containsGoldNumbers(Arrays.asList(appformReqDto.getReqWantNumber(), appformReqDto.getReqWantNumber2(), appformReqDto.getReqWantNumber3()));
        // 유심비 /  가입비 설정
        // NFC 유심 처리
        // eSIM 처리
        // join_price 및 sim_price 설정 : usimService.getSimInfo
        // 기기변경에서 고객인증 확인
        //            M포탈은 세션정보로 기기변경로 인증한 고객생년월일과 핸드폰번호 등으로 인증을 진행함.
        //            스마트는 신분증확인정보로 인증확인? 아니면 M전산을 재호출?
        //            신분증확인정보가 없는 경우, 이력저장 insertAdminAccessTrace
        //            * 계약번호 설정 : M포탈은 고객인증 후 세션에 담아둠. 스마트도 고객인증 후 세션에 담고 세션해제 시점은 협의가 필요함.
        //            * 금액정보 설정??
        //              판매정책코드, 작성자? 요금제코드 등등등이 없으면 에러처리
        //            * 현재 유심번호 설정 등등 처리
        // 신규가입 및 번호이동
        //            상품에서 유심과 휴대폰을 나눠 빈값 체크 (요건 맨 위로 올라가야겠네)
        //


    }

    // public List<AppformInfoDto> getAppformInfo(AppformInfoCondition condition) {
    //     Integer requestKey = condition.getRequestKey();
    //
    //     AppformInfoDto appformInfoDto = new AppformInfoDto();
    //     MsfRequestDto msfRequestDto = new MsfRequestDto();
    //     MsfRequestAgentDto msfRequestAgentDto = new MsfRequestAgentDto();
    //     MsfRequestCstmrDto msfRequestCstmrDto = new MsfRequestCstmrDto();
    //     MsfRequestSaleDto msfRequestSaleDto = new MsfRequestSaleDto();
    //     MsfRequestBillReqDto msfRequestBillReqDto = new MsfRequestBillReqDto();
    //
    //     List<AppformInfoDto> appformInfoDtoList = new ArrayList<>();
    //
    //     if (!StringUtils.isEmpty(requestKey)) {
    //         msfRequestDto = this.getMsfRequestInfo(requestKey);
    //         msfRequestAgentDto = this.getMsfRequestAgentInfo(requestKey);
    //         msfRequestCstmrDto = this.getMsfRequestCstmrInfo(requestKey);
    //         msfRequestSaleDto = this.getMsfRequestSaleInfo(requestKey);
    //         msfRequestBillReqDto = this.getMsfRequestBillReqInfo(requestKey);
    //
    //         appformInfoDto.setMsfRequestDto(msfRequestDto);
    //         appformInfoDto.setMsfRequestAgentDto(msfRequestAgentDto);
    //         appformInfoDto.setMsfRequestCstmrDto(msfRequestCstmrDto);
    //         appformInfoDto.setMsfRequestSaleDto(msfRequestSaleDto);
    //         appformInfoDto.setMsfRequestBillReqDto(msfRequestBillReqDto);
    //
    //         appformInfoDtoList.add(appformInfoDto);
    //     }
    //
    //     //if (StringUtils.isEmpty(requestKey)) {
    //     //formCommService.generateRequestKey(); //신청서번호 생성 테스트
    //
    //     msfRequestDto.setProdTypeCd("MM"); //휴대폰
    //     msfRequestDto.setOperTypeCd("MNP3"); //번호이동
    //     msfRequestDto.setIdentityCertTypeCd("3"); //신분증인증유형코드
    //     msfRequestDto.setCstmrTypeCd("NA"); //내국인
    //
    //     appformInfoDto.setMsfRequestDto(msfRequestDto);
    //     appformInfoDto.setMsfRequestAgentDto(msfRequestAgentDto);
    //     appformInfoDto.setMsfRequestCstmrDto(msfRequestCstmrDto);
    //     appformInfoDto.setMsfRequestSaleDto(msfRequestSaleDto);
    //     appformInfoDto.setMsfRequestBillReqDto(msfRequestBillReqDto);
    //
    //     appformInfoDtoList.add(appformInfoDto);
    //     //}
    //
    //
    //     return Optional.ofNullable(appformInfoDtoList)
    //         .orElseThrow(() -> new NotFoundException("SmartForm 신청서 정보가 없습니다. requestKey : " + requestKey));
    // }


    // public AppformInfoDto getAppformInfo(AppformInfoCondition condition) {
    //     Integer requestKey = condition.getRequestKey();
    //
    //     AppformInfoDto appformInfoDto = new AppformInfoDto();
    //     MsfRequestDto msfRequestDto = new MsfRequestDto();
    //     MsfRequestAgentDto msfRequestAgentDto = new MsfRequestAgentDto();
    //     MsfRequestCstmrDto msfRequestCstmrDto = new MsfRequestCstmrDto();
    //     MsfRequestSaleDto msfRequestSaleDto = new MsfRequestSaleDto();
    //     MsfRequestBillReqDto msfRequestBillReqDto = new MsfRequestBillReqDto();
    //
    //     if (StringUtils.isEmpty(requestKey)) {
    //         //formCommService.generateRequestKey(); //신청서번호 생성 테스트
    //
    //         msfRequestDto.setProdTypeCd("MM"); //휴대폰
    //         msfRequestDto.setOperTypeCd("MNP3"); //번호이동
    //         msfRequestDto.setIdentityCertTypeCd("3"); //신분증인증유형코드
    //         msfRequestDto.setCstmrTypeCd("NA"); //내국인
    //     }
    //
    //     if (!StringUtils.isEmpty(requestKey)) {
    //         msfRequestDto = this.getMsfRequestInfo(requestKey);
    //         msfRequestAgentDto = this.getMsfRequestAgentInfo(requestKey);
    //         msfRequestCstmrDto = this.getMsfRequestCstmrInfo(requestKey);
    //         msfRequestSaleDto = this.getMsfRequestSaleInfo(requestKey);
    //         msfRequestBillReqDto = this.getMsfRequestBillReqInfo(requestKey);
    //     }
    //
    //     appformInfoDto.setMsfRequestDto(msfRequestDto);
    //     appformInfoDto.setMsfRequestAgentDto(msfRequestAgentDto);
    //     appformInfoDto.setMsfRequestCstmrDto(msfRequestCstmrDto);
    //     appformInfoDto.setMsfRequestSaleDto(msfRequestSaleDto);
    //     appformInfoDto.setMsfRequestBillReqDto(msfRequestBillReqDto);
    //
    //     return Optional.ofNullable(appformInfoDto)
    //         .orElseThrow(() -> new NotFoundException("SmartForm 신청서 정보가 없습니다. requestKey : " + requestKey));
    //
    //     //return appformInfoDto;
    //
    //     // return Optional.ofNullable(msfRequestDto)
    //     //     .orElseThrow(() -> new NotFoundException("SmartForm 신청서 정보가 없습니다. requestKey : " + requestKey));
    //
    //     // MsfRequestDto msfRequestDto = this.getMsfRequestInfo(requestKey); //MSF_REQUEST
    //     // MsfRequestCstmrDto msfRequestCstmrDto = this.getMsfRequestCstmrInfo(requestKey); //MSF_REQUEST
    //     // MsfRequestAgentDto msfRequestAgentDto = this.getMsfRequestAgentInfo(requestKey); //MSF_REQUEST
    //     // MsfRequestBillReqDto msfRequestBillReqDto = this.getMsfRequestBillReqInfo(requestKey); //MSF_REQUEST
    //     // MsfRequestSaleDto msfRequestSaleDto = this.getMsfRequestSaleInfo(requestKey); //MSF_REQUEST
    //
    //     List<MspSalePlcyMstInfoDto> mspSalePlcyInfo = productInfoMapper.selectMspSalePlcyMstList(condition);
    //     List<MspSalePlcyMstInfoDto> distinctList = new ArrayList<>(
    //         mspSalePlcyInfo.stream()
    //             .collect(Collectors.toMap(
    //                 MspSalePlcyMstInfoDto::getSalePlcyCd,
    //                 Function.identity(),
    //                 (existing, replacement) -> existing
    //             ))
    //             .values()
    //     );
    //     return distinctList;
    // }


}
