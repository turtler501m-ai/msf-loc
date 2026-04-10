package com.ktmmobile.mcp.event.dto;

import java.io.Serializable;
import java.util.Date;

public class AgreeTermsEventDto implements Serializable{

      private static final long serialVersionUID = 1L;

        /** 이벤트 약관동의 일련번호 AGREE_TERMS_EVENT_SEQ SEQUENCE */

        private long agreeTermsEventSeq;

        /** CI */

        private String ci;

        /** DI */

        private String di;

        /** 유저ID */

        private String userId;

        /** 이벤트 코드 */

        private String eventCode;

        /** 이벤트 별 키 값 */

        private long eventKey;

        /** 등의일자 */

        private String cretDd;

        /** 생성자ID */

        private String cretId;

        /** 수정자ID */

        private String amdId;

        /** 등록일시 */

        private Date cretDt;

        /** 수정일시 */

        private Date amdDt;

        /** 등록아이피 */

        private String rip;

        /** 동의항목1 여부 (Y/N) */

        private String agreeArticle1;

        /** 동의항목2 여부 (Y/N) */

        private String agreeArticle2;

        /** 동의항목3 여부 (Y/N) */

        private String agreeArticle3;

        /** 동의항목4 여부 (Y/N) */

        private String agreeArticle4;

        /** 동의항목5 여부 (Y/N) */

        private String agreeArticle5;

        /** 동의항목6 여부 (Y/N) */

        private String agreeArticle6;

        public long getAgreeTermsEventSeq() {
            return agreeTermsEventSeq;
        }

        public void setAgreeTermsEventSeq(long agreeTermsEventSeq) {
            this.agreeTermsEventSeq = agreeTermsEventSeq;
        }

        public String getCi() {
            return ci;
        }

        public void setCi(String ci) {
            this.ci = ci;
        }

        public String getDi() {
            return di;
        }

        public void setDi(String di) {
            this.di = di;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getEventCode() {
            return eventCode;
        }

        public void setEventCode(String eventCode) {
            this.eventCode = eventCode;
        }

        public long getEventKey() {
            return eventKey;
        }

        public void setEventKey(long eventKey) {
            this.eventKey = eventKey;
        }

        public String getCretDd() {
            return cretDd;
        }

        public void setCretDd(String cretDd) {
            this.cretDd = cretDd;
        }

        public String getCretId() {
            return cretId;
        }

        public void setCretId(String cretId) {
            this.cretId = cretId;
        }

        public String getAmdId() {
            return amdId;
        }

        public void setAmdId(String amdId) {
            this.amdId = amdId;
        }

        public Date getCretDt() {
            return cretDt;
        }

        public void setCretDt(Date cretDt) {
            this.cretDt = cretDt;
        }

        public Date getAmdDt() {
            return amdDt;
        }

        public void setAmdDt(Date amdDt) {
            this.amdDt = amdDt;
        }

        public String getRip() {
            return rip;
        }

        public void setRip(String rip) {
            this.rip = rip;
        }

        public String getAgreeArticle1() {
            return agreeArticle1;
        }

        public void setAgreeArticle1(String agreeArticle1) {
            this.agreeArticle1 = agreeArticle1;
        }

        public String getAgreeArticle2() {
            return agreeArticle2;
        }

        public void setAgreeArticle2(String agreeArticle2) {
            this.agreeArticle2 = agreeArticle2;
        }

        public String getAgreeArticle3() {
            return agreeArticle3;
        }

        public void setAgreeArticle3(String agreeArticle3) {
            this.agreeArticle3 = agreeArticle3;
        }

        public String getAgreeArticle4() {
            return agreeArticle4;
        }

        public void setAgreeArticle4(String agreeArticle4) {
            this.agreeArticle4 = agreeArticle4;
        }

        public String getAgreeArticle5() {
            return agreeArticle5;
        }

        public void setAgreeArticle5(String agreeArticle5) {
            this.agreeArticle5 = agreeArticle5;
        }

        public String getAgreeArticle6() {
            return agreeArticle6;
        }

        public void setAgreeArticle6(String agreeArticle6) {
            this.agreeArticle6 = agreeArticle6;
        }

}
