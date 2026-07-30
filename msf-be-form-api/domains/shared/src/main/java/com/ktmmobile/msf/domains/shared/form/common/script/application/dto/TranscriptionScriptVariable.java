package com.ktmmobile.msf.domains.shared.form.common.script.application.dto;

import java.text.DecimalFormat;

public record TranscriptionScriptVariable(
    String cntpntCdNm,
    String userNm,
    String nflCustNm,
    String mobilePriceNm,
    String mobileMntcntAmtFee,
    String mdlNm,
    String handsetThecpl,
    String mobileMdlNm,
    String mobileHandsetThecpl,
    String dscnOptnCdNm,
    String mobileTotSupotAmnt,
    String mobileStorSupotAmnt,
    String mobileInstAmnt,
    String mobileInstInt,
    String mobileEnggPrd,
    String mobileInstPrd,
    String enggPrd,
    String mmFeeDscn,
    String totSupotAmnt,
    String storSupotAmnt,
    String mobileVasNm,
    String mobileVasTotalPrice,
    String shphcrNm
) {

    public String replace(String script) {
        return script
            .replace("@cntpntCdNm", defaultString(cntpntCdNm))
            .replace("@userNm", defaultString(userNm))
            .replace("@nflCustNm", defaultString(nflCustNm))
            .replace("@mobilePriceNm", defaultString(mobilePriceNm))
            .replace("@mobileMntcntAmtFee", defaultPrice(mobileMntcntAmtFee))
            .replace("@mdlNm", defaultString(mdlNm))
            .replace("@handsetThecpl", defaultPrice(handsetThecpl))
            .replace("@mobileMdlNm", defaultString(mobileMdlNm))
            .replace("@mobileHandsetThecpl", defaultPrice(mobileHandsetThecpl))
            .replace("@dscnOptnCdNm", defaultString(dscnOptnCdNm))
            .replace("@mobileTotSupotAmnt", defaultPrice(mobileTotSupotAmnt))
            .replace("@mobileStorSupotAmnt", defaultPrice(mobileStorSupotAmnt))
            .replace("@mobileInstAmnt", defaultPrice(mobileInstAmnt))
            .replace("@mobileInstInt", defaultPrice(mobileInstInt))
            .replace("@mobileEnggPrd", defaultString(mobileEnggPrd))
            .replace("@mobileInstPrd", defaultString(mobileInstPrd))
            .replace("@enggPrd", defaultString(enggPrd))
            .replace("@mmFeeDscn", defaultPrice(mmFeeDscn))
            .replace("@totSupotAmnt", defaultPrice(totSupotAmnt))
            .replace("@storSupotAmnt", defaultPrice(storSupotAmnt))
            .replace("@mobileVasNm", defaultString(mobileVasNm))
            .replace("@mobileVasTotalPrice", defaultPrice(mobileVasTotalPrice))
            .replace("@shphcrNm", defaultString(shphcrNm));
    }

    public static TranscriptionScriptVariable empty() {
        return new TranscriptionScriptVariable(
            "", // cntpntCdNm
            "", // userNm
            "", // nflCustNm
            "", // mobilePriceNm
            "", // mobileMntcntAmtFee
            "", // mdlNm
            "", // handsetThecpl
            "", // mobileMdlNm
            "", // mobileHandsetThecpl
            "", // dscnOptnCdNm
            "", // mobileTotSupotAmnt
            "", // mobileStorSupotAmnt
            "", // mobileInstAmnt
            "", // mobileInstInt
            "", // mobileEnggPrd
            "", // mobileInstPrd
            "", // enggPrd
            "", // mmFeeDscn
            "", // totSupotAmnt
            "", // storSupotAmnt
            "", // mobileVasNm
            "", // mobileVasTotalPrice
            ""  // shphcrNm
        );
    }

    public static TranscriptionScriptVariable ownerChange(
        String cntpntCdNm,
        String userNm,
        String nflCustNm,
        String mobilePriceNm,
        String mobileMntcntAmtFee
    ) {
        return new TranscriptionScriptVariable(
            cntpntCdNm,
            userNm,
            nflCustNm,
            mobilePriceNm,
            mobileMntcntAmtFee,
            "", // mdlNm
            "", // handsetThecpl
            "", // mobileMdlNm
            "", // mobileHandsetThecpl
            "", // dscnOptnCdNm
            "", // mobileTotSupotAmnt
            "", // mobileStorSupotAmnt
            "", // mobileInstAmnt
            "", // mobileInstInt
            "", // mobileEnggPrd
            "", // mobileInstPrd
            "", // enggPrd
            "", // mmFeeDscn
            "", // totSupotAmnt
            "", // storSupotAmnt
            "", // mobileVasNm
            "", // mobileVasTotalPrice
            ""  // shphcrNm
        );
    }

    private String defaultString(String value) {
        return value == null ? "" : value;
    }

    private String defaultPrice(String value) {
        if (value == null) {
            return "";
        } else {

            try {
                long number = Long.parseLong(value);
                DecimalFormat df = new DecimalFormat("#,###");
                return df.format(number);
            } catch (NumberFormatException e) {
                return value;
            }
        }
    }
}
