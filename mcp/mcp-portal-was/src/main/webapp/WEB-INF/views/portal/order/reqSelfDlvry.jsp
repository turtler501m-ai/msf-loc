<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<div class="c-modal" id="delivery-inquiry-dialog" role="dialog" tabindex="-1" aria-describedby="#delivery-inquiry-title">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="delivery-inquiry-title">가입신청 배송조회</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="c-flex c-flex--jfy-between u-mt--32">
            <h3 class="c-heading c-heading--type5 u-mt--0 u-mb--0">주문번호 3395693</h3>
            <span class="u-co-gray-7">2021.11.30</span>
          </div>
          <ol class="deli-step c-expand u-mt--16">
            <!-- 상태값 있을 경우, 클래스 .is-active 추가-->
            <li>
              <i class="c-icon c-icon--accept-ready" aria-hidden="true"></i>
              <span>접수대기</span>
            </li>
            <li>
              <i class="c-icon c-icon--delivery-ready" aria-hidden="true"></i>
              <span>배송대기</span>
            </li>
            <li class="is-active">
              <i class="c-icon c-icon--delivery" aria-hidden="true"></i>
              <span>배송중</span>
            </li>
            <li>
              <i class="c-icon c-icon--open-ready" aria-hidden="true"></i>
              <span>개통대기</span>
            </li>
            <li>
              <i class="c-icon c-icon--open-complete" aria-hidden="true"></i>
              <span>개통완료</span>
            </li>
          </ol>
          <h3 class="c-heading c-heading--type2 u-mb--12">주문 상품정보</h3>
          <div class="c-item c-item--type3 u-mt--0">
            <div class="c-item__list">
              <div class="c-item__image">
                <img src="../../resources/images/mobile/content/img_product_01.png" alt="">
              </div>
              <div class="c-item__item">
                <span class="c-text c-text--type4 u-co-point-4">신청완료</span>
                <div class="c-item__title">
                  <strong>갤럭시 A32</strong>
                </div>
                <div class="c-item__info">
                  <span class="c-text c-text--type3 u-co-gray">64GB/화이트</span>
                  <span class="c-text c-text--type3 u-co-gray">374,000원</span>
                </div>
              </div>
              <button class="c-button c-button--sm c-button--white">배송조회</button>
            </div>
          </div>
          <h3 class="c-heading c-heading--type2 u-mb--12">주문 상세내역</h3>
          <div class="c-table">
            <table>
              <caption>주문 상세내역</caption>
              <colgroup>
                <col style="width: 32%">
                <col style="width: 68%">
              </colgroup>
              <tbody>
                <tr>
                  <th>신청요금제</th>
                  <td class="u-ta-left">LTE 데이터 알뜰 200MB/30분</td>
                </tr>
                <tr>
                  <th>요금납부방법</th>
                  <td class="u-ta-left">계좌이체 (농협 4171**********)</td>
                </tr>
                <tr>
                  <th>배송지 정보</th>
                  <td class="u-ta-left"> 03026
                    <br>서울 특별시 종로구 사직로 1가길
                    <br>**************
                  </td>
                </tr>
                <tr>
                  <th>주문자명</th>
                  <td class="u-ta-left">홍길*</td>
                </tr>
                <tr>
                  <th>연락처</th>
                  <td class="u-ta-left">010-12**-*5678</td>
                </tr>
                <tr>
                  <th>이메일 주소</th>
                  <td class="u-ta-left">ktworld01@kt.com</td>
                </tr>
              </tbody>
            </table>
          </div>
          <hr class="c-hr c-hr--type1 c-expand"><!-- case1 : 주문 상품이 휴대폰인 경우-->
          <h3 class="c-heading c-heading--type5">단말기/월 요금 상세</h3>
          <p class="c-bullet c-bullet--dot u-co-gray">프로모션 대상 요금제의 경우 익월 청구서를 통해 할인내역을 확인하실 수 있습니다.</p>
          <hr class="c-hr c-hr--type2 u-mt--24">
          <div class="c-addition-wrap">
            <span class="c-text-label c-text-label--mint u-m--0 u-mb--20">갤럭시 A32</span>
            <dl class="c-addition c-addition--type1">
              <dt>단말요금(A)</dt>
              <dd class="u-ta-right">
                <b>7,088</b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>단말기 출고가</dt>
              <dd class="u-ta-right">374,000 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>공통지원금</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- 186,000</b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>추가지원금</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- 27,900</b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>할부원금</dt>
              <dd class="u-ta-right">160,100 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>총할부수수료</dt>
              <dd class="u-ta-right">10,012 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>실구매가</dt>
              <dd class="u-ta-right">170,112 원</dd>
            </dl>
            <hr class="c-hr c-hr--type2">
            <span class="c-text-label c-text-label--mint u-m--0 u-mb--20">LTE 데이터 알뜰 200MB/30분</span>
            <dl class="c-addition c-addition--type1">
              <dt>월 통신 요금</dt>
              <dd class="u-ta-right">
                <b>14,190</b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>기본요금</dt>
              <dd class="u-ta-right">21,890 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>기본할인</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- 7,700</b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <hr class="c-hr c-hr--type2">
            <dl class="c-addition c-addition--type1">
              <dt>가입 유심비 별도</dt>
              <dd class="u-ta-right"> </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>가입비(3개월 분납)</dt>
              <dd class="u-ta-right">
                <span class="c-text c-text--strike">7,200</span>(무료)
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>USIM(최초 1회)</dt>
              <dd class="u-ta-right">6,600 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>번호이동 수수료</dt>
              <dd class="u-ta-right">800 원</dd>
            </dl>
            <hr class="c-hr c-hr--type2">
            <dl class="c-addition c-addition--type1 c-addition--sum">
              <dt> 월 납부금액 (A+B)
                <br>(부가세 포함)
              </dt>
              <dd class="u-ta-right">
                <b>39,700</b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>
                <p>갤럭시 A32 / 64GB / 화이트</p>
                <p>LTE 데이터 알뜰 0MB / 30분</p>
              </dt>
            </dl>
          </div><!-- case2 : 주문 상품이 유심인 경우-->
          <h3 class="c-heading c-heading--type5">월 요금 상세</h3>
          <p class="c-bullet c-bullet--dot u-co-gray">프로모션 대상 요금제의 경우 익월 청구서를 통해 할인내역을 확인하실 수 있습니다.</p>
          <hr class="c-hr c-hr--type2 u-mt--24">
          <div class="c-addition-wrap">
            <span class="c-text-label c-text-label--mint u-m--0 u-mb--20">통화 맘껏 300MB</span>
            <dl class="c-addition c-addition--type1">
              <dt>월 통신 요금</dt>
              <dd class="u-ta-right">
                <b>14,190</b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>기본요금</dt>
              <dd class="u-ta-right">21,890 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>기본할인</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- 7,700</b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <hr class="c-hr c-hr--type2">
            <dl class="c-addition c-addition--type1">
              <dt>가입 유심비 별도</dt>
              <dd class="u-ta-right"> </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>가입비(3개월 분납)</dt>
              <dd class="u-ta-right">
                <span class="c-text c-text--strike">7,200</span>(무료)
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>USIM(최초 1회)</dt>
              <dd class="u-ta-right">6,600 원</dd>
            </dl>
            <hr class="c-hr c-hr--type2">
            <dl class="c-addition c-addition--type1 c-addition--sum">
              <dt> 월 납부금액
                <br>(부가세 포함)
              </dt>
              <dd class="u-ta-right">
                <b>39,700</b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>
                <p>유심</p>
                <p>통화 맘껏 300MB</p>
              </dt>
            </dl>
          </div>
          <hr class="c-hr c-hr--type2 u-mt--24">
          <p class="c-bullet c-bullet--dot u-co-gray">가입비, 유심비 면제 여부는 가입신청 시의 프로모션 내용을 기준합니다.</p>
        </div>
      </div>
    </div>
  </div>
<div class="c-modal" id="delivery-inquiry2-dialog" role="dialog" tabindex="-1" aria-describedby="#delivery-inquiry2-title">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="delivery-inquiry2-title">유심/자급제폰 배송조회</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="c-flex c-flex--jfy-between u-mt--32">
            <h3 class="c-heading c-heading--type5 u-mt--0 u-mb--0">주문번호 3395693</h3>
            <span class="u-co-gray-7">2021.11.30</span>
          </div>
          <ol class="deli-step c-expand u-mt--16">
            <!-- 상태값 있을 경우, 클래스 .is-active 추가-->
            <li>
              <i class="c-icon c-icon--delivery" aria-hidden="true"></i>
              <span>신청완료</span>
            </li>
            <li class="is-active">
              <i class="c-icon c-icon--delivery" aria-hidden="true"></i>
              <span>배송대기</span>
            </li>
            <li>
              <i class="c-icon c-icon--delivery" aria-hidden="true"></i>
              <span>배송중</span>
            </li>
            <li>
              <i class="c-icon c-icon--delivery" aria-hidden="true"></i>
              <span>개통대기</span>
            </li>
            <li>
              <i class="c-icon c-icon--delivery" aria-hidden="true"></i>
              <span>개통완료</span>
            </li>
          </ol>
          <h3 class="c-heading c-heading--type2 u-mb--12">주문 상품정보</h3>
          <div class="c-item c-item--type3 u-mt--0">
            <div class="c-item__list">
              <div class="c-item__image">
                <img src="../../resources/images/mobile/content/img_product_01.png" alt="">
              </div>
              <div class="c-item__item">
                <span class="c-text c-text--type4 u-co-point-4">신청완료</span>
                <div class="c-item__title">
                  <strong>갤럭시 A32</strong>
                </div>
                <div class="c-item__info">
                  <span class="c-text c-text--type3 u-co-gray">64GB/화이트</span>
                  <span class="c-text c-text--type3 u-co-gray">374,000원</span>
                </div>
              </div>
              <button class="c-button c-button--sm c-button--white">배송조회</button>
            </div>
          </div><!-- case1 : 주문 상품이 유심인 경우 table-->
          <h3 class="c-heading c-heading--type2 u-mb--12">주문 상세내역</h3>
          <div class="c-table">
            <table>
              <caption>주문 상세내역</caption>
              <colgroup>
                <col style="width: 32%">
                <col style="width: 68%">
              </colgroup>
              <tbody>
                <tr>
                  <th>주문자명</th>
                  <td class="u-ta-left">홍길*</td>
                </tr>
                <tr>
                  <th>배송지 정보</th>
                  <td class="u-ta-left"> 03026
                    <br>서울 특별시 종로구 사직로 1가길
                    <br>**************
                  </td>
                </tr>
                <tr>
                  <th>수령인명</th>
                  <td class="u-ta-left">홍길*</td>
                </tr>
                <tr>
                  <th>연락처</th>
                  <td class="u-ta-left">010-12**-*5678</td>
                </tr>
              </tbody>
            </table>
          </div><!-- //-->
          <!-- case2 : 주문 상품이 자급제폰인 경우 table-->
          <h3 class="c-heading c-heading--type2 u-mb--12">주문 상세내역</h3>
          <div class="c-table">
            <table>
              <caption>주문 상세내역</caption>
              <colgroup>
                <col style="width: 32%">
                <col style="width: 68%">
              </colgroup>
              <tbody>
                <tr>
                  <th>신청요금제</th>
                  <td class="u-ta-left">LTE 데이터 알뜰 200MB/30분</td>
                </tr>
                <tr>
                  <th>요금납부방법</th>
                  <td class="u-ta-left">계좌이체 (농협 4171**********)</td>
                </tr>
                <tr>
                  <th>배송지 정보</th>
                  <td class="u-ta-left"> 03026
                    <br>서울 특별시 종로구 사직로 1가길
                    <br>**************
                  </td>
                </tr>
                <tr>
                  <th>주문자명</th>
                  <td class="u-ta-left">홍길*</td>
                </tr>
                <tr>
                  <th>연락처</th>
                  <td class="u-ta-left">010-12**-*5678</td>
                </tr>
                <tr>
                  <th>이메일 주소</th>
                  <td class="u-ta-left">ktworld01@kt.com</td>
                </tr>
              </tbody>
            </table>
          </div><!-- //-->
          <hr class="c-hr c-hr--type1 c-expand">
          <h3 class="c-heading c-heading--type5">단말기/월 요금 상세</h3>
          <p class="c-bullet c-bullet--dot u-co-gray">프로모션 대상 요금제의 경우 익월 청구서를 통해 할인내역을 확인하실 수 있습니다.</p>
          <hr class="c-hr c-hr--type2 u-mt--24">
          <div class="c-addition-wrap">
            <span class="c-text-label c-text-label--mint u-m--0 u-mb--20">갤럭시 A32</span>
            <dl class="c-addition c-addition--type1">
              <dt>단말요금(A)</dt>
              <dd class="u-ta-right">
                <b>7,088</b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>단말기 출고가</dt>
              <dd class="u-ta-right">374,000 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>공통지원금</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- 186,000</b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>추가지원금</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- 27,900</b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>할부원금</dt>
              <dd class="u-ta-right">160,100 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>총할부수수료</dt>
              <dd class="u-ta-right">10,012 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>실구매가</dt>
              <dd class="u-ta-right">170,112 원</dd>
            </dl>
            <hr class="c-hr c-hr--type2">
            <span class="c-text-label c-text-label--mint u-m--0 u-mb--20">LTE 데이터 알뜰 200MB/30분</span>
            <dl class="c-addition c-addition--type1">
              <dt>월 통신 요금</dt>
              <dd class="u-ta-right">
                <b>14,190</b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>기본요금</dt>
              <dd class="u-ta-right">21,890 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>기본할인</dt>
              <dd class="u-ta-right">
                <b class="fs-16 u-co-sub-3">- 7,700</b>
                <b class="u-co-sub-3">원</b>
              </dd>
            </dl>
            <hr class="c-hr c-hr--type2">
            <dl class="c-addition c-addition--type1">
              <dt>가입 유심비 별도</dt>
              <dd class="u-ta-right"> </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>가입비(3개월 분납)</dt>
              <dd class="u-ta-right">
                <span class="c-text c-text--strike">7,200</span>(무료)
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>USIM(최초 1회)</dt>
              <dd class="u-ta-right">6,600 원</dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>번호이동 수수료</dt>
              <dd class="u-ta-right">800 원</dd>
            </dl>
            <hr class="c-hr c-hr--type2">
            <dl class="c-addition c-addition--type1 c-addition--sum">
              <dt> 월 납부금액 (A+B)
                <br>(부가세 포함)
              </dt>
              <dd class="u-ta-right">
                <b>39,700</b>원
              </dd>
            </dl>
            <dl class="c-addition c-addition--type2">
              <dt>
                <p>갤럭시 A32 / 64GB / 화이트</p>
                <p>LTE 데이터 알뜰 0MB / 30분</p>
              </dt>
            </dl>
          </div>
          <hr class="c-hr c-hr--type2 u-mt--24">
          <p class="c-bullet c-bullet--dot u-co-gray">가입비, 유심비 면제 여부는 가입신청 시의 프로모션 내용을 기준합니다.</p>
        </div>
      </div>
    </div>
  </div>
     
          