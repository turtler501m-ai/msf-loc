<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="titleAttr">휴대폰 안심보험 가입신청</t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=24.10.30"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
           <script type="text/javascript" src="/resources/js/mobile/mypage/reqInsr.js?version=24.10.30"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <form name="selfFrm" id="selfFrm" action="/m/mypage/reqInsr.do" method="post">
        <input type="hidden" name="ncn" value="" >
    </form>
    <input type="hidden" id="isAuth" name="isAuth">
    <input type="hidden" id="insrType" name="insrType" value="${insrType}">
      <input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">

    <div class="ly-content" id="main-content">
        <div class="ly-page-sticky">
            <h2 class="ly-page__head">휴대폰 안심보험 가입신청</h2>
        </div>
          <div class="callhistory-info">
              <div class="callhistory-info__list">
                <h3>휴대폰 안심보험이란?</h3>
                <ul>
                  <li>휴대폰 개통 후 분실, 도난, 파손, 화재, 침수 등으로 인한 사고 발생시 정해진 한도에 따라 보상해주는 서비스 입니다.</li>
                </ul>
            </div>
        </div>
        <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
        <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
        <br/>
        <div class="callhistory-info" id="divNotIng" style="display:none;">
            <div class="callhistory-info__list c-box c-box--type2" style="text-align:center;">
                <h3><span class="u-co-red">고객님께서는 이미 안심보험 가입 신청중입니다.</span></h3><br/></br>
            </div>
        </br>
        </div>

        <div class="callhistory-info" id="divNotA" style="display:none;">
            <div class="callhistory-info__list c-box c-box--type2" style="text-align:center;">
                <h3><span class="u-co-red">고객님께서는 안심보험 가입 중이므로 가입 대상이 아닙니다.<br/>자세한 내용은 고객센터(114/무료)로 문의 바랍니다.</span></h3><br/></br>
            </div>
        </br>
        </div>

        <div class="callhistory-info" id="divNotB" style="display:none;">
            <div class="callhistory-info__list c-box c-box--type2" style="text-align:center;">
                <h3><span class="u-co-red">고객님께서는 고객센터(114/무료)를 통해 보험 가입 가능 여부를 확인 부탁드립니다.</span></h3><br/></br>
            </div>
        </br>
        </div>

        <div class="callhistory-info" id="divNew" style="display:none;">
            <div class="callhistory-info__list c-box c-box--type2" style="text-align:center;">
                <h3><span class="u-co-red">kt엠모바일 홈페이지에서 "새 휴대폰 구매/개통"을 이용하시는 고객님께서 가입 가능하신 보험상품입니다.</span></h3><br/></br>
            </div>
            <br/>
            <div class="callhistory-info__list c-box c-box--type5">
                <h3>[안내사항]</h3>
                <ul>
                      <li>새 휴대폰을 신규로 구매하거나 기기변경한 고객님께서는 개통일 포함 45일 이내 가입 가능합니다.</li>
                      <li>이미 분실되거나 파손된 휴대폰은 가입하실 수 없습니다.</li>
                      <li>분실이나 도난으로 휴대폰 보상을 두 번 받으면 마지막 보상일로부터 1년간 다시 가입할 수 없습니다.</li>
                      <li>보험상품의 보상범위(단말기 출고가 초과/이하) 기준에 따라 가입이 제한될 수 있습니다.</li>
                      <li>보험청구를 위한 자기부담금과 보상한도액을 꼭 확인해주시고 가입신청해주시기 바랍니다.</li>
                </ul>
                </br>
            </div>
            <div class="callhistory-guide">
                <h3>[처리절차]</h3>
                <ul class="callhistory-guide__list">
                    <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">1</span>고객님</strong>
                        <p>보험신청</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">2</span>고객님</strong>
                        <p>보험상품 가입</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">3</span>보험사</strong>
                        <p>보험사 심사</p>
                      </li>
                      <li class="callhistory-guide__item--type2 u-mt--26">
                          <div class="choice-list">
                            <strong class="choice"><i class="c-icon c-icon--triangle_bluegreen-bottom" aria-hidden="true"></i></strong>
                        </div>
                        <div class="callhistory-guide__item--content">
                            <strong><span class="c-sticker c-sticker--type3 u-fs-13">적격</span></strong>
                            <p>보험개시</p>
                        </div>
                        <div class="callhistory-guide__item--content">
                            <strong><span class="c-sticker c-sticker--type2 u-fs-13">부적격</span></strong>
                            <p>부가서비스 자동해지 및 고객센터에서 전화드림</p>
                        </div>
                      </li>
                  </ul>
            </div>
        <br/>
        </div>

        <div class="callhistory-info" id="divUsim" style="display:none;">
            <div class="callhistory-info__list c-box c-box--type2" style="text-align:center;">
                <span class="u-co-red">kt엠모바일 홈페이지에서 "자급제 단말", "중고폰 구매/개통", "유심 요금제 가입"을 이용하시는 고객님께서 가입 가능하신 보험상품입니다.</span>
            </div>
            <br/>
            <div class="callhistory-info__list c-box c-box--type5">
                <h3>[안내사항]</h3>
                <ul>
                      <li>자급제 단말을 이용하시는 고객님께서는 구매일 포함 45일 이내 가입 가능하며, 구매영수증 증빙이 필수입니다.</li>
                      <li class="u-co-red">쓰던 휴대폰, 중고폰 보험 상품을 가입 희망하는 고객님께서는 개통일 포함 45일 이내 가입 가능합니다.</li>
                      <li class="u-co-red">보험상품 선택 및 신청 후 고객님께서 등록하신 별도 연락처로 단말기 상태 등록을 위한 URL이 발송되며, 사진을 등록해 주시면 보험사 심사결과를 고객님께 안내드립니다.</li>
                      <li>이미 분실되거나 파손된 휴대폰은 가입하실 수 없습니다.</li>
                      <li>분실이나 도난으로 휴대폰 보상을 두 번 받으면 마지막 보상일로부터 1년간 다시 가입할 수 없습니다.</li>
                      <li>보험상품의 보상범위(단말기 출고가 초과/이하) 기준에 따라 가입이 제한될 수 있습니다.</li>
                      <li>보험청구를 위한 자기부담금과 보상한도액을 꼭 확인해주시고 가입신청해주시기 바랍니다.</li>
                </ul>
                </br>
            </div>
              <div class="callhistory-guide">
                <h3>신청절차</h3>
                <ul class="callhistory-guide__list">
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">1</span>고객님</strong>
                        <p>보험신청</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">2</span>고객님</strong>
                        <p>보험상품 가입</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">3</span>고객센터</strong>
                        <p>휴대폰 외관사진 등록 URL 문자발송</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">4</span>고객님</strong>
                        <p>고객님께서 보유한 휴대폰 사진 촬영 및 등록(7일이내)</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">5</span>보험사</strong>
                        <p>보험사 심사</p>
                    </li>
                      <li class="callhistory-guide__item--type2 u-mt--26">
                          <div class="choice-list">
                            <strong class="choice"><i class="c-icon c-icon--triangle_bluegreen-bottom" aria-hidden="true"></i></strong>
                        </div>
                        <div class="callhistory-guide__item--content">
                            <strong><span class="c-sticker c-sticker--type3 u-fs-13">적격</span></strong>
                            <p>보험개시</p>
                        </div>
                        <div class="callhistory-guide__item--content">
                            <strong><span class="c-sticker c-sticker--type2 u-fs-13">부적격</span></strong>
                            <p>부가서비스 자동해지 및 고객센터에서 전화드림</p>
                        </div>
                      </li>
                </ul>
            </div>
            <br/>
        </div>

        <div class="callhistory-info" id="divSelf" style="display:none;">
            <div class="callhistory-info__list c-box c-box--type2" style="text-align:center;">
                <span class="u-co-red">kt엠모바일 홈페이지에서 "자급제 단말"을 이용하시는 고객님께서 가입 가능하신 보험상품입니다.</span>
            </div>
            <br/>
            <div class="callhistory-info__list c-box c-box--type5">
                <h3>[안내사항]</h3>
                <ul>
                      <li>자급제 단말을 이용하시는 고객님께서는 구매일 포함 45일 이내 가입 가능하며, 구매영수증 증빙이 필수입니다.</li>
                      <li class="u-co-red">보험상품 선택 및 신청 후 고객님께서 등록하신 별도 연락처로 단말기 상태 등록을 위한 URL이 발송되며, 사진을 등록해 주시면 보험사 심사결과를 고객님께 안내드립니다.</li>
                      <li>이미 분실되거나 파손된 휴대폰은 가입하실 수 없습니다.</li>
                      <li>분실이나 도난으로 휴대폰 보상을 두 번 받으면 마지막 보상일로부터 1년간 다시 가입할 수 없습니다.</li>
                      <li>보험상품의 보상범위(단말기 출고가 초과/이하) 기준에 따라 가입이 제한될 수 있습니다.</li>
                      <li>보험청구를 위한 자기부담금과 보상한도액을 꼭 확인해주시고 가입신청해주시기 바랍니다.</li>
                </ul>
                </br>
            </div>
              <div class="callhistory-guide">
                <h3>신청절차</h3>
                <ul class="callhistory-guide__list">
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">1</span>고객님</strong>
                        <p>보험신청</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">2</span>고객님</strong>
                        <p>보험상품 가입</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">3</span>고객센터</strong>
                        <p>휴대폰 외관사진 등록 URL 문자발송</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">4</span>고객님</strong>
                        <p>고객님께서 보유한 휴대폰 사진 촬영 및 등록(7일이내)</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">5</span>보험사</strong>
                        <p>보험사 심사</p>
                    </li>
                      <li class="callhistory-guide__item--type2 u-mt--26">
                          <div class="choice-list">
                            <strong class="choice"><i class="c-icon c-icon--triangle_bluegreen-bottom" aria-hidden="true"></i></strong>
                        </div>
                        <div class="callhistory-guide__item--content">
                            <strong><span class="c-sticker c-sticker--type3 u-fs-13">적격</span></strong>
                            <p>보험개시</p>
                        </div>
                        <div class="callhistory-guide__item--content">
                            <strong><span class="c-sticker c-sticker--type2 u-fs-13">부적격</span></strong>
                            <p>부가서비스 자동해지 및 고객센터에서 전화드림</p>
                        </div>
                      </li>
                </ul>
            </div>
            <br/>
        </div>

        <!-- 신규단말 가입 후 45일 이내  -->
        <div class="callhistory-info" id="divNew2" style="display:none;">
            <div class="callhistory-info__list c-box c-box--type2" style="text-align:center;">
                <span class="u-co-red">Kt엠모바일 홈페이지에서 “m mobile 신규 단말“을 이용하시는 고객님께서 가입 가능하신 보험상품입니다.</span>
            </div>
            <br/>
            <div class="callhistory-info__list c-box c-box--type5">
                <h3>[안내사항]</h3>
                <ul>
                      <li>새 휴대폰을 신규로 구매하거나 기기변경한 고객님께서는 개통일 포함 45일 이내 가입 가능합니다.</li>
                      <li>이미 분실되거나 파손된 휴대폰은 가입하실 수 없습니다.</li>
                      <li>분실이나 도난으로 휴대폰 보상을 두 번 받으면 마지막 보상일로부터 1년간 다시 가입할 수 없습니다.</li>
                      <li>보험상품의 보상범위(단말기 출고가 초과/이하) 기준에 따라 가입이 제한될 수 있습니다.</li>
                      <li>보험청구를 위한 자기부담금과 보상한도액을 꼭 확인해주시고 가입신청해주시기 바랍니다.</li>
                </ul>
                </br>
            </div>
              <div class="callhistory-guide">
                <h3>신청절차</h3>
                <ul class="callhistory-guide__list">
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">1</span>고객님</strong>
                        <p>보험신청</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">2</span>고객님</strong>
                        <p>보험상품 가입</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">3</span>보험사</strong>
                        <p>보험사 심사</p>
                    </li>
                      <li class="callhistory-guide__item--type2 u-mt--26">
                          <div class="choice-list">
                            <strong class="choice"><i class="c-icon c-icon--triangle_bluegreen-bottom" aria-hidden="true"></i></strong>
                        </div>
                        <div class="callhistory-guide__item--content">
                            <strong><span class="c-sticker c-sticker--type3 u-fs-13">적격</span></strong>
                            <p>보험개시</p>
                        </div>
                        <div class="callhistory-guide__item--content">
                            <strong><span class="c-sticker c-sticker--type2 u-fs-13">부적격</span></strong>
                            <p>부가서비스 자동해지 및 고객센터에서 전화드림</p>
                        </div>
                      </li>
                </ul>
            </div>
            <br/>
        </div>

        <div class="u-mt--20" id="divTypeNew" style="display:none;">
            <div class="c-form-group" role="group" aira-labelledby="receiveTitle">
                 <h5 class="c-group--head" id="receiveTitle">[보험종류]</h5>
               </div>
            <div class="c-table c-table--x-scroll u-mt--10 reqInsr">
                <table>
                      <caption>폴드 180, 분실파손 150, 분실파손 100, 분실파손 70, 파손 50으로 구성된 스마트폰(아이폰 제외) 안내 정보</caption>
                    <colgroup>
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                    </colgroup>
                    <thead>
                        <tr>
                            <th rowspan="2">구분</th>
                            <th colspan="5">스마트폰(아이폰 제외)</th>
                        </tr>
                          <tr>
                            <th scope="col">폴드 180</th>
                            <th scope="col">분실파손<br />150</th>
                            <th scope="col">분실파손<br />100</th>
                            <th scope="col">분실파손<br />70</th>
                            <th scope="col">파손 50</th>
                          </tr>
                    </thead>
                    <tbody>
                          <tr>
                            <th>보험료(월)</th>
                            <td>7,700원</td>
                            <td>4,000원</td>
                            <td>3,600원</td>
                            <td>3,300원</td>
                            <td>2,800원</td>
                          </tr>
                          <tr>
                            <th>가입가능<br />출고가</th>
                            <td>갤럭시 폴드</td>
                            <td>100만원<br />이상</td>
                            <td>70만원<br />이상</td>
                            <td colspan="2">제한없음</td>
                          </tr>
                          <tr>
                            <th>보상범위</th>
                            <td colspan="2">분실, 도난, 파손(화재, 침수, 완파) 및 피싱해킹</td>
                            <td colspan="2">분실, 도난, 파손(화재, 침수, 완파)</td>
                            <td>파손(화재, 침수, 완파)</td>
                          </tr>
                          <tr>
                            <th>자기부담금 및 보상한도</th>
                            <td colspan="5"><a href="/m/content/insrView.do"><span class="c-text-label c-text-label--type2 c-text-label--mint-type1">자세히 보기</span></a></td>
                          </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="u-mt--20" id="divTypeUsim" style="display:none;">
            <div class="c-form-group" role="group" aira-labelledby="receiveTitle">
                 <h5 class="c-group--head" id="receiveTitle">[보험종류]</h5>
               </div>
            <div class="c-table c-table--x-scroll u-mt--10 reqInsr">
                <table>
                      <caption>폴드 180, 분실파손 150, 분실파손 100, 분실파손 70, 파손 50, i-분실파손 150, i-분실파손 90, i-파손 50으로 구성된 스마트폰(아이폰 제외)/아이폰(자급제) 안내 정보</caption>
                    <colgroup>
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                    </colgroup>
                    <thead>
                        <tr>
                            <th rowspan="2">구분</th>
                            <th colspan="5">스마트폰(아이폰 제외)</th>
                            <th colspan="3">아이폰(자급제)</th>
                            <th colspan="2">중고(유심가입)</th>
                        </tr>
                          <tr>
                            <th scope="col">폴드 180</th>
                            <th scope="col">분실파손<br />150</th>
                            <th scope="col">분실파손<br />100</th>
                            <th scope="col">분실파손<br />70</th>
                            <th scope="col">파손 50</th>
                            <th scope="col">i-분실파손<br />150</th>
                            <th scope="col">i-분실파손<br />90</th>
                            <th scope="col">i-파손<br />50</th>
                            <th scope="col">중고 파손<br />100</th>
                            <th scope="col">중고 파손<br />40</th>
                          </tr>
                    </thead>
                    <tbody>
                          <tr>
                            <th>보험료(월)</th>
                            <td>7,700원</td>
                            <td>4,000원</td>
                            <td>3,600원</td>
                            <td>3,300원</td>
                            <td>2,800원</td>
                            <td>4,000원</td>
                            <td>3,300원</td>
                            <td>2,800원</td>
                            <td>6,000원</td>
                            <td>3,700원</td>
                          </tr>
                          <tr>
                            <th>가입가능<br />출고가</th>
                            <td>갤럭시 폴드</td>
                            <td>100만원<br />이상</td>
                            <td>70만원<br />이상</td>
                            <td colspan="2">제한없음</td>
                            <td>100만원<br />이상</td>
                            <td colspan="4">제한없음</td>
                          </tr>
                          <tr>
                            <th>보상범위</th>
                            <td colspan="2">분실, 도난, 파손(화재, 침수, 완파) 및 피싱해킹</td>
                            <td colspan="2">분실, 도난, 파손(화재, 침수, 완파)</td>
                            <td>파손(화재, 침수, 완파)</td>
                            <td>분실, 도난, 파손(화재, 침수, 완파) 및 피싱해킹</td>
                            <td>분실, 도난, 파손(화재, 침수, 완파)</td>
                            <td colspan="3">파손(화재, 침수, 완파)</td>
                          </tr>
                          <tr>
                            <th>자기부담금 및 보상한도</th>
                            <td colspan="10"><a href="/m/content/insrView.do"><span class="c-text-label c-text-label--type2 c-text-label--mint-type1">자세히 보기</span></a></td>
                          </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="u-mt--20" id="divTypeSelf" style="display:none;">
            <div class="c-form-group" role="group" aira-labelledby="receiveTitle">
                 <h5 class="c-group--head" id="receiveTitle">[보험종류]</h5>
               </div>
            <div class="c-table c-table--x-scroll u-mt--10 reqInsr">
                <table>
                      <caption>폴드 180, 분실파손 150, 분실파손 100, 분실파손 70, 파손 50, i-분실파손 150, i-분실파손 90, i-파손 50으로 구성된 스마트폰(아이폰 제외)/아이폰(자급제) 안내 정보</caption>
                    <colgroup>
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                    </colgroup>
                    <thead>
                        <tr>
                            <th rowspan="2">구분</th>
                            <th colspan="5">스마트폰(아이폰 제외)</th>
                            <th colspan="3">아이폰(자급제)</th>
                        </tr>
                          <tr>
                            <th scope="col">폴드 180</th>
                            <th scope="col">분실파손<br />150</th>
                            <th scope="col">분실파손<br />100</th>
                            <th scope="col">분실파손<br />70</th>
                            <th scope="col">파손 50</th>
                            <th scope="col">i-분실파손<br />150</th>
                            <th scope="col">i-분실파손<br />90</th>
                            <th scope="col">i-파손<br />50</th>
                          </tr>
                    </thead>
                    <tbody>
                          <tr>
                            <th>보험료(월)</th>
                            <td>7,700원</td>
                            <td>4,000원</td>
                            <td>3,600원</td>
                            <td>3,300원</td>
                            <td>2,800원</td>
                            <td>4,000원</td>
                            <td>3,300원</td>
                            <td>2,800원</td>
                          </tr>
                          <tr>
                            <th>가입가능<br />출고가</th>
                            <td>갤럭시 폴드</td>
                            <td>100만원<br />이상</td>
                            <td>70만원<br />이상</td>
                            <td colspan="2">제한없음</td>
                            <td>100만원<br />이상</td>
                            <td colspan="2">제한없음</td>
                          </tr>
                          <tr>
                            <th>보상범위</th>
                            <td colspan="2">분실, 도난, 파손(화재, 침수, 완파) 및 피싱해킹</td>
                            <td colspan="2">분실, 도난, 파손(화재, 침수, 완파)</td>
                            <td>파손(화재, 침수, 완파)</td>
                            <td>분실, 도난, 파손(화재, 침수, 완파) 및 피싱해킹</td>
                            <td>분실, 도난, 파손(화재, 침수, 완파)</td>
                            <td>파손(화재, 침수, 완파)</td>
                          </tr>
                          <tr>
                            <th>자기부담금 및 보상한도</th>
                            <td colspan="8"><a href="/m/content/insrView.do"><span class="c-text-label c-text-label--type2 c-text-label--mint-type1">자세히 보기</span></a></td>
                          </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- 신규단말 가입 후 45일 이내  -->
        <div class="u-mt--20" id="divTypeNew2" style="display:none;">
            <div class="c-form-group" role="group" aira-labelledby="receiveTitle">
                 <h5 class="c-group--head" id="receiveTitle">[보험종류]</h5>
               </div>
            <div class="c-table c-table--x-scroll u-mt--10 reqInsr">
                <table>
                      <caption>폴드 180, 분실파손 150, 분실파손 100, 분실파손 70, 파손 50으로 구성된 스마트폰 안심보험 안내 정보</caption>
                    <colgroup>
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                          <col style="width:4.25rem">
                    </colgroup>
                    <thead>
                        <tr>
                            <th rowspan="2">구분</th>
                            <th colspan="5">스마트폰(아이폰 제외)</th>
                        </tr>
                          <tr>
                            <th scope="col">폴드 180(M)</th>
                            <th scope="col">분실파손<br />150(M)</th>
                            <th scope="col">분실파손<br />100(M)</th>
                            <th scope="col">분실파손<br />70(M)</th>
                            <th scope="col">파손 50(M)</th>
                          </tr>
                    </thead>
                    <tbody>
                          <tr>
                            <th>보험료(월)</th>
                            <td>7,700원</td>
                            <td>4,000원</td>
                            <td>3,600원</td>
                            <td>3,300원</td>
                            <td>2,800원</td>
                          </tr>
                          <tr>
                            <th>가입가능<br />출고가</th>
                            <td>갤럭시 폴드</td>
                            <td>100만원<br />이상</td>
                            <td>70만원<br />이상</td>
                            <td colspan="2">제한없음</td>
                          </tr>
                          <tr>
                            <th>보상범위</th>
                            <td colspan="2">분실, 도난, 파손(화재, 침수, 완파) 및 피싱해킹</td>
                            <td colspan="2">분실, 도난, 파손(화재, 침수, 완파)</td>
                            <td>파손(화재, 침수, 완파)</td>
                          </tr>
                          <tr>
                            <th>자기부담금 및 보상한도</th>
                            <td colspan="5"><a href="/m/content/insrView.do"><span class="c-text-label c-text-label--type2 c-text-label--mint-type1">자세히 보기</span></a></td>
                          </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="callhistory-info u-mt--20" id="divExplain" style="display:none;">
            <div class="callhistory-info__list c-box c-box--type5">
                <h3>[보험가입 유의사항]</h3>
                <ul>
                      <li>휴대폰 안심보험은 해지 환급금이 없는 소멸성 상품이며, 보험은 의무가입기간이 없어 중도 해지 가능합니다.</li>
                      <li>미성년자, 법인고객님께서는 kt엠모바일 가입 휴대폰 : 114(무료) / 타통신사 휴대폰 또는 유선전화 : 1899-5000(유료)를 통해 보험 가입 가능여부 확인이 필요합니다.</li>
                      <li>가입 및 해지 시 보험료는 사용일수만큼 계산되어 익월 청구됩니다.</li>
                      <li>보상단말기의 출고가가 보상기준가격을 초과하는 경우 그 초과된 금액은 고객님께서 부담해야 합니다.</li>
                      <li>자세한 사항은 당사 홈페이지 – 상품소개 – 휴대폰 안심보험을 통해 확인해주시기 바랍니다.</li>
                      <li>보상센터 : 유선 연락처 1899-4058(평일 9시~18시 운영), FAX번호 02-6280-4059</li>
                </ul>
                </br>
            </div>
        </div>

        <div class="c-row c-row--lg" id="divAuth" style="display:none;">
            <!-- 본인인증 방법 선택 -->
            <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
                <jsp:param name="controlYn" value="N"/>
                <jsp:param name="reqAuth" value="CNAT"/>
            </jsp:include>
        </div>


        <!-- 신규단말보험 선택 -->
        <div class="c-row c-row--lg u-mt--40 product" id="divSelNew" style="display:none;">
            <div class="c-form-group" role="group" aira-labelledby="receiveTitle">
                 <h4 class="c-group--head" id="receiveTitle">신규단말보험 선택</h4>
               </div>
            <ul class="c-card-list">
                <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4"  id="rdNew1" type="radio" name="rdInsrNew" value="PL245L228">
                               <label class="c-card__label" for="rdNew1">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>폴드 180</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>7,700 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>갤럭시 폴드 전용</dd>
                                <dt>보상범위</dt>
                                <dd>최대 180만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdNew2" type="radio" name="rdInsrNew" value="PL245L229">
                               <label class="c-card__label" for="rdNew2">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 150</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>4,000 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>100만원 이상</dd>
                                <dt>보상범위</dt>
                                <dd>최대 150만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdNew3" type="radio" name="rdInsrNew" value="PL245L230">
                               <label class="c-card__label" for="rdNew3">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 100</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>3,600 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>70만원 이상</dd>
                                <dt>보상범위</dt>
                                <dd>최대 100만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdNew4" type="radio" name="rdInsrNew" value="PL245L231">
                               <label class="c-card__label" for="rdNew4">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 70</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>3,300 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 70만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdNew5" type="radio" name="rdInsrNew" value="PL245L232">
                               <label class="c-card__label" for="rdNew5">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>파손 50</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>2,800 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 50만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
              </ul>
        </div>

        <!-- 유심보험 선택 -->
        <div class="c-row c-row--lg u-mt--60 product" id="divSelUsim" style="display:none;">
            <div class="c-form-group" role="group" aira-labelledby="receiveTitle">
                 <h4 class="c-group--head" id="receiveTitle">유심보험 선택</h4>
               </div>
               <ul class="c-card-list">
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4"  id="rdSelf9" type="radio" name="rdInsrSelf" value="PL245L228">
                               <label class="c-card__label" for="rdSelf9">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>폴드 180</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>7,700 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>갤럭시 폴드 전용</dd>
                                <dt>보상범위</dt>
                                <dd>최대 180만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf10" type="radio" name="rdInsrSelf" value="PL245L229">
                               <label class="c-card__label" for="rdSelf10">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 150</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>4,000 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>100만원 이상</dd>
                                <dt>보상범위</dt>
                                <dd>최대 150만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf11" type="radio" name="rdInsrSelf" value="PL245L230">
                               <label class="c-card__label" for="rdSelf11">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 100</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>3,600 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>70만원 이상</dd>
                                <dt>보상범위</dt>
                                <dd>최대 100만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf12" type="radio" name="rdInsrSelf" value="PL245L231">
                               <label class="c-card__label" for="rdSelf12">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 70</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>3,300 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 70만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf13" type="radio" name="rdInsrSelf" value="PL245L232">
                               <label class="c-card__label" for="rdSelf13">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>파손 50</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>2,800 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 50만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf14" type="radio" name="rdInsrSelf" value="PL245L235">
                               <label class="c-card__label" for="rdSelf14">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>i-분실파손 150</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 아이폰형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--apple" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>4,000 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>아이폰 자급제 단말기와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>100만원 이상</dd>
                                <dt>보상범위</dt>
                                <dd>최대 150만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf15" type="radio" name="rdInsrSelf" value="PL245L236">
                               <label class="c-card__label" for="rdSelf15">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>i-분실파손 90</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 아이폰형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--apple" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>3,300 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>아이폰 자급제 단말기와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 90만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf16" type="radio" name="rdInsrSelf" value="PL245L237">
                               <label class="c-card__label" for="rdSelf16">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>i-파손 50</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 아이폰형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--apple" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>2,800 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>아이폰 자급제 단말기와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 50만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf17" type="radio" name="rdInsrSelf" value="PL245L233">
                               <label class="c-card__label" for="rdSelf17">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>중고 파손 100</strong>
                               <ul class="c-card__sub">
                                 <li>유심결합</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--apple" aria-hidden="true"></i>
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>6,000 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 100만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf18" type="radio" name="rdInsrSelf" value="PL245L234">
                               <label class="c-card__label" for="rdSelf18">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>중고 파손 40</strong>
                               <ul class="c-card__sub">
                                 <li>유심결합</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--apple" aria-hidden="true"></i>
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>3,700 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 40만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
              </ul>
        </div>

        <!-- 자급제전용보험 선택 -->
           <div class="c-row c-row--lg u-mt--60 product" id="divSelSelf" style="display:none;">
               <div class="c-form-group" role="group" aira-labelledby="receiveTitle">
                 <h4 class="c-group--head" id="receiveTitle">자급제전용보험 선택</h4>
               </div>
               <ul class="c-card-list">
                <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4"  id="rdSelf1" type="radio" name="rdInsrSelf" value="PL245L228">
                               <label class="c-card__label" for="rdSelf1">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>폴드 180</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>7,700 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>갤럭시 폴드 전용</dd>
                                <dt>보상범위</dt>
                                <dd>최대 180만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf2" type="radio" name="rdInsrSelf" value="PL245L229">
                               <label class="c-card__label" for="rdSelf2">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 150</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>4,000 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>100만원 이상</dd>
                                <dt>보상범위</dt>
                                <dd>최대 150만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf3" type="radio" name="rdInsrSelf" value="PL245L230">
                               <label class="c-card__label" for="rdSelf3">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 100</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>3,600 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>70만원 이상</dd>
                                <dt>보상범위</dt>
                                <dd>최대 100만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf4" type="radio" name="rdInsrSelf" value="PL245L231">
                               <label class="c-card__label" for="rdSelf4">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 70</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>3,300 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 70만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf5" type="radio" name="rdInsrSelf" value="PL245L232">
                               <label class="c-card__label" for="rdSelf5">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>파손 50</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>2,800 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 또는, 자급제 단말기(안드로이드형)와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 50만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf6" type="radio" name="rdInsrSelf" value="PL245L235">
                               <label class="c-card__label" for="rdSelf6">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>i-분실파손 150</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 아이폰형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--apple" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>4,000 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>아이폰 자급제 단말기와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>100만원 이상</dd>
                                <dt>보상범위</dt>
                                <dd>최대 150만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf7" type="radio" name="rdInsrSelf" value="PL245L236">
                               <label class="c-card__label" for="rdSelf7">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>i-분실파손 90</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 아이폰형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--apple" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>3,300 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>아이폰 자급제 단말기와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 90만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdSelf8" type="radio" name="rdInsrSelf" value="PL245L237">
                               <label class="c-card__label" for="rdSelf8">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>i-파손 50</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 아이폰형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--apple" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>2,800 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>아이폰 자급제 단말기와 USIM을 결합 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 50만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
            </ul>
           </div>

           <!-- 신규단말 가입 후 45일 이내  -->
           <div class="c-row c-row--lg u-mt--40 product" id="divSelNew2" style="display:none;">
            <div class="c-form-group" role="group" aira-labelledby="receiveTitle">
                 <h4 class="c-group--head" id="receiveTitle">신규단말보험 선택</h4>
               </div>
            <ul class="c-card-list">
                <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4"  id="rdInsrNew1" type="radio" name="rdInsrNew2" value="PL248N660">
                               <label class="c-card__label" for="rdInsrNew1">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>폴드 180(M)</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>7,700 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>갤럭시 폴드 전용</dd>
                                <dt>보상범위</dt>
                                <dd>최대 180만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdInsrNew2" type="radio" name="rdInsrNew2" value="PL248N661">
                               <label class="c-card__label" for="rdInsrNew2">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 150(M)</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>4,000 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>100만원 이상</dd>
                                <dt>보상범위</dt>
                                <dd>최대 150만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdInsrNew3" type="radio" name="rdInsrNew2" value="PL248N662">
                               <label class="c-card__label" for="rdInsrNew3">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 100(M)</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>3,600 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>70만원 이상</dd>
                                <dt>보상범위</dt>
                                <dd>최대 100만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdInsrNew4" type="radio" name="rdInsrNew2" value="PL248M658">
                               <label class="c-card__label" for="rdInsrNew4">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>분실파손 70(M)</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>3,300 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 70만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                   <span class="c-text-label c-text-label--gray">분실</span>
                                   <span class="c-text-label c-text-label--gray">도난</span>
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
                   <li class="c-card c-card--type3">
                       <div class="c-card__box">
                           <div class="c-chk-wrap">
                               <input class="c-radio c-radio--type4" id="rdInsrNew5" type="radio" name="rdInsrNew2" value="PL248N659">
                               <label class="c-card__label" for="rdInsrNew5">
                                 <span class="c-hidden">선택</span>
                               </label>
                           </div>
                           <div class="c-card__title">
                               <strong>파손 50(M)</strong>
                               <ul class="c-card__sub">
                                 <li>단말결합 안드로이드형</li>
                               </ul>
                           </div>
                           <div class="c-card__price-wrap">
                               <i class="c-icon c-icon--android" aria-hidden="true"></i>
                               <div class="c-card__price u-ml--auto">
                                 <span class="c-card__price-title">월 이용료</span>
                                 <b>2,800 원</b>
                               </div>
                           </div>
                           <div class="c-card__content">
                               <dl class="product-desc">
                                <dt>설명</dt>
                                <dd>M모바일 신품 단말기 개통 시 가입 가능한 상품입니다.</dd>
                                <dt>가입가능 출고가</dt>
                                <dd>제한없음</dd>
                                <dt>보상범위</dt>
                                <dd>최대 50만원 보장</dd>
                               </dl>
                               <div class="c-text-label-wrap">
                                <span class="c-text-label c-text-label--gray">화재</span>
                                <span class="c-text-label c-text-label--gray">파손</span>
                                <span class="c-text-label c-text-label--gray">침수</span>
                               </div>
                           </div>
                       </div>
                   </li>
              </ul>
        </div>

        <div class="c-form-wrap" id="divPhone" style="display:none;">
            <div class="c-form" id="fax">
                <span class="c-form__title">사진전송을 위한 별도 연락처</span>
                <div class="c-form__input u-mt--0">
                      <input class="c-input onlyNum" id="cstmrReceiveTel" type="tel" name="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="">
                      <label class="c-form__label" for="cstmrReceiveTel">연락 가능 연락처(필수기재)</label>
                </div>
            </div>
        </div>

        <div class="c-form-wrap" id="divInsur" style="display:none;">
            <div class="c-accordion c-accordion--type5 acc-agree">
                <h4 class="c-heading c-heading--type2">휴대폰안심보험 신청 동의</h4>
                <div class="c-accordion__box">
                    <div class="c-accordion__item">
                        <div class="c-accordion__head">
                            <div class="c-accordion__check">
                                <input class="c-checkbox" id="btnInsrAllCheck" type="checkbox">
                                <label class="c-label" for="btnInsrAllCheck">휴대폰안심보험 가입 동의에 대하여 내용을 읽어보았으며, 보험가입에 동의합니다. (일괄 동의)</label>
                            </div>
                            <button class="c-accordion__button is-active" type="button" aria-expanded="false" data-acc-header="#insr_agreeA1" >
                                <div class="c-accordion__trigger"> </div>
                            </button>
                        </div>
                        <div class="c-accordion__panel expand open" id="insr_agreeA1">
                            <div class="c-accordion__inside">
                                <div class="c-agree__item">
                                    <input id="clauseInsrProdFlag" code="ClauseInsur01M" cdgroupid1="ClauseInsur" docver="0" type="checkbox" class="c-checkbox c-checkbox--type2 _agreeInsr">
                                    <label class="c-label" for="clauseInsrProdFlag">이동통신 단말기 보험 상품 설명서 교부 확인<span class="u-co-gray">(필수)</span>
                                    </label>
                                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseInsrProdFlag');openPage('termsPop','/termsPop.do','cdGroupId1=ClauseInsur&cdGroupId2=ClauseInsur01M');">
                                        <span class="c-hidden">상세보기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                </div>

                                <div class="c-agree__item">
                                    <input id="clauseInsrProdFlag02" code="ClauseInsur02" cdgroupid1="ClauseInsur" docver="0" type="checkbox"  class="c-checkbox c-checkbox--type2 _agreeInsr">
                                    <label class="c-label" for="clauseInsrProdFlag02">개인정보 수집 및 이용동의 <span class="u-co-gray">(필수)</span>
                                    </label>
                                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseInsrProdFlag02');openPage('termsPop','/termsPop.do','cdGroupId1=ClauseInsur&cdGroupId2=ClauseInsur02');">
                                        <span class="c-hidden">상세보기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                </div>

                                <div class="c-agree__item">
                                    <input id="clauseInsrProdFlag03" code="ClauseInsur03" cdgroupid1="ClauseInsur" docver="0" type="checkbox"  class="c-checkbox c-checkbox--type2 _agreeInsr">
                                    <label class="c-label" for="clauseInsrProdFlag03">개인정보 제 3자 제공동의<span class="u-co-gray">(필수)</span>
                                    </label>
                                    <button class="c-button c-button--xsm" onclick="fnSetEventId('clauseInsrProdFlag03');openPage('termsPop','/termsPop.do','cdGroupId1=ClauseInsur&cdGroupId2=ClauseInsur03');">
                                        <span class="c-hidden">상세보기</span>
                                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="c-button-wrap" id="divSubmit" style="display:none;">
              <button class="c-button c-button--full c-button--primary" id="btnRequest" disabled>가입신청</button>
        </div>
    </div>
    </t:putAttribute>
</t:insertDefinition>