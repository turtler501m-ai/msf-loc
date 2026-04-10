<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<script type="text/javascript" src="/resources/js/portal/mcash/mcashSvcCanPop.js"></script>

<div class="c-modal__content">
  <div class="c-modal__header">
    <h1 class="c-modal__title" id="mcashServiceCnlTitle">M쇼핑할인 서비스 해지</h1>
    <button class="c-button" data-dialog-close>
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>
  <div class="c-modal__body">
    <div class="c-table">
      <table>
        <caption>M쇼핑할인 서비스 해지 회선 안내 정보</caption>
        <colgroup>
          <col>
        </colgroup>
        <tbody>
        <tr>
          <th scope="col">해지 회선</th>
          <td>${mobileNo}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="c-notice-wrap u-mt--16">
      <h5 class="c-notice__title">
        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
        <span>서비스 해지 유의사항</span>
      </h5>
      <ul class="c-notice__list">
        <li>서비스 해지 시 적립된 모든 캐시는 즉시 소멸됩니다.</li>
        <li>서비스 해지 후 재가입 시에도 소멸된 캐시는 복구 불가합니다.</li>
      </ul>
    </div>
    <div class="c-button-wrap u-mt--48">
      <button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
      <button class="c-button c-button--full c-button--primary" onclick="mcashSvcCan()">해지</button>
    </div>
  </div>
</div>