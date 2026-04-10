<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
	uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
  <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="termsTitle">이용약관</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="terms-wrap">
            <div class="terms-conent">
              <h2 class="tems-heading">${terms.formNm}</h2>
              <p>아래내용은 이용약관 중 주요사항에 대한 설명으로 이용약관 전문내용과 다소 상이할 수 있고 변경될 수 있으므로, 이용약관 전문 내용은 홈페이지(www.ktmmobile.com) 고객센터(1899-5000)를 통해 확인하시기 바랍니다.</p>
              <ul class="terms-list__box">
                ${terms.docContent}
              </ul>
            </div>
          </div>
        </div>
        <div class="c-modal__footer">
          <button class="c-button c-button--full c-button--primary" data-dialog-close>동의 후 닫기</button>
        </div>
      </div>