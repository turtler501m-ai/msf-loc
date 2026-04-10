var _CODEFINDER_ = {

    "USER" : {
        title : "사용자찾기",
        url : ROOT + "/org/userinfomgmt/searchUserInfo.do",
        width: 830,
        height: 600
    },


    "ORG" : {
        title : "조직찾기",
        url : ROOT + "/msp_org_pu_1001.do",
        width: 990,
        height: 600
    },

    "CUST" : {
        title : "개통고객찾기",
        url : ROOT + "/msp_cmn_pu_0001.do",
        width: 790,
        height: 600
    },    
    
    "ORGHEAD" : {
        title : "본사조직찾기",
        url : ROOT + "/msp_org_pu_1010.do?typeCd=10",
        width: 990,
        height: 600
    },
    
    
    "ORGAGENCY" : {
        title : "대리점조직찾기",
        url : ROOT + "/msp_org_pu_1010.do?typeCd=20",
        width: 990,
        height: 600
    },
    
    "APPRORGAGENCY" : {
        title : "대리점조직찾기",
        url : ROOT + "/cmn/login/msp_org_pu_1010_1.do?typeCd=20",
        width: 990,
        height: 600
    },
    
    
    "ORGHEADAGENCY" : {
        title : "본사/대리점조직찾기",
        url : ROOT + "/msp_org_pu_1010.do",
        width: 990,
        height: 600
    },
    
    // 대리점/판매점 조직찾기 2022.06.07 추가  [ 조직명과 조직유형으로 검색 ]
    "ORGSALESNEW" : {
        title : "대리점/판매점 조직 찾기",
        url : ROOT + "/msp_org_pu_1014.do",
        width: 990,
        height: 600
    },
    
    
    "ORGSHOP" : {
        title : "판매점조직찾기",
        url : ROOT + "/msp_org_pu_1011.do",
        width: 990,
        height: 600
    },
    
    "ORGSHOPNEW" : {
        title : "판매점조직찾기",
        url : ROOT + "/msp_org_pu_1012.do",
        width: 990,
        height: 600
    },

    "MNFCT" : {
        title : "업체찾기",
        url : ROOT + "/org/mnfctmgmt/searchMnfctInfo.do",
        width: 960,
        height: 500
    },

    "HNDSET" : {
        title : "색상모델찾기",
        url : ROOT + "/cmn/intmmdl/intmColrMdlForm.do",
//        url : ROOT + "/org/hndsetmgmt/searchHmdstModel.do",
        width: 990,
        height: 560
    },
    "RPRSMLDSET" : {
        title : "대표모델찾기",
        url : ROOT + "/cmn/intmmdl/intmMdlForm.do",
//        url : ROOT + "/org/hndsetmgmt/searchHmdstModel.do",
        width: 990,
        height: 560
    },
    //대표모델 출고단가 찾기 팝업 -- 단종모델 제외
    "RPRSMLDSETAMT" : {
        title : "모델출고단가찾기",
        url : ROOT + "/cmn/intmmdl/intmMdlAmtForm.do",
        width: 990,
        height: 560
    },
    //대표모델 출고단가 찾기 팝업 -- 단종모델 포함
    "RPRSMLDSETAMTAll" : {
        title : "모델출고단가찾기",
        url : ROOT + "/cmn/intmmdl/intmMdlAmtAllForm.do",
        width: 990,
        height: 560
    },
    //대표모델의 중고여부 단말 찾기 팝업 -- 단종모델 제외
    "RPRSMLDSETAMTOLDYN" : {
        title : "모델리스트",
        url : ROOT + "/cmn/intmmdl/intmMdlOldYnForm.do",
        width: 990,
        height: 560
    },
    //대표모델의 중고여부 단말 찾기 팝업 -- 단종모델 포함
    "RPRSMLDSETAMTOLDYNALL" : {
        title : "모델리스트",
        url : ROOT + "/cmn/intmmdl/intmMdlOldYnAllForm.do",
        width: 990,
        height: 560
    },

    "ZIP" : {
        title : "우편번호찾기",
        url : ROOT + "/cmn/codefind/zipCdMstForm.do",
        width: 930,
        height: 550
    },

    // 물류용 대리점 팝업
    "ORG_AGNT" : {
        title : "대리점찾기",
        url : ROOT + "/lgs/ngrtnmgmt/findAgntOrgForm.do",
        width: 990,
        height: 620
    },

    // 물류용 단말기 팝업
    "HNDSET_PHONE" : {
        title : "단말기찾기",
        url : ROOT + "/lgs/ngrtnmgmt/findHmdstPhoneModelForm.do",
        width: 930,
        height: 570
    },

    // 물류용 물류센터 팝업
    "ORG_LGS_CNTR" : {
        title : "물류센터찾기",
        url : ROOT + "/lgs/genrtnmgmt/findLgsCntrOrgForm.do",
        width: 990,
        height: 620
    },
    
    // 대리점/판매점 조직 찾기 [ 조직명,조직유형,조직상태로 검색 ] 
    "ORGSALES" : {
        title : "대리점/판매점 조직 찾기",
        url : ROOT + "/msp_org_pu_1013.do",
        width: 990,
        height: 600
    },

    // 유심접점 찾기  
    "USIMORGNM" : {
        title : "유심접점 찾기 찾기",
        url : ROOT + "/searchUsimOrgPopup.do",
        width: 990,
        height: 600
    },

    // K-NOTE 접점 찾기
    "KNOTEORG" : {
        title : "K-Note 접점 찾기",
        url : ROOT + "/searchKnoteOrgPopup.do",
        width: 990,
        height: 600
    },

    "REQORG" : {
        title : "신청등록 조직찾기",
        url : ROOT + "/msp_org_pu_1001.do?pageType=Req",
        width: 990,
        height: 600
    },

    "M2MSHOP" : {
        title : "M2M판매점조직찾기",
        url : ROOT + "/msp_org_pu_1012.do?m2mYn=Y",
        width: 990,
        height: 600
    },

    "CSTMR" : {
        title : "고객 찾기",
        url : ROOT + "/msp_cmn_pu_0002.do",
        width: 715,
        height: 450
    },

    DUMMY: ""

};


if (! window.top._CODEFINDER_)  window.top._CODEFINDER_ = _CODEFINDER_;

