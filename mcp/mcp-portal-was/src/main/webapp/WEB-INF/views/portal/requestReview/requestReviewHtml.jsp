<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<script type="text/javascript" src="/resources/js/portal/review/requestReviewHtml.js"></script>

<input type="hidden" id="pageNo" name="pageNo" value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}" />

<c:choose>
	<c:when test="${requestReviewDtoList !=null and !empty requestReviewDtoList}">
		<div class="review_title">
			<c:if test="${resDto.viewLimitYn eq 'Y'}"><!-- 어드민에서등록된 %이상일떄만 노출토록 -->
				<h4>
					<span class="red">${resDto.percent}%</span>의 고객님이 <span class="red">추천</span>합니다!
				</h4>
			</c:if>
		</div>
		<div class="review_detail">
			<c:forEach var="requestReviewDtoList" items="${requestReviewDtoList}">
			<c:set var="selectUsimRequestReviewIngList" value="${requestReviewDtoList.reviewImgList}" />
				<div class="review_fold foldArea">
					<div class="review_text">
						<div class="top">
							<c:choose>
								<c:when test="${requestReviewDtoList.commendYn eq '1'}">
									<img src="/resources/images_bak/requestReview/icon_recommend.jpg" alt="">
								</c:when>
								<c:otherwise>
									<img src="/resources/images_bak/requestReview/icon_non_recommend.jpg" alt="">
								</c:otherwise>
							</c:choose>
							<c:if test="${requestReviewDtoList.ntfYn eq '1'}"><img src="/resources/images_bak/requestReview/icon_best.jpg" alt="" class="margin_right_10"></c:if>
							${nmcp:getMaskedName(requestReviewDtoList.regNm)} 님/
							${requestReviewDtoList.reqBuyTypeNm}/
							${nmcp:getCodeNm(Constants.REVIEW_EVENT_CD, requestReviewDtoList.eventCd)}/
							<fmt:formatDate value="${requestReviewDtoList.sysRdate}" pattern="yyyy.MM.dd" />
						</div>
						<div class="cont ellipsis2">${requestReviewDtoList.reviewSbst}</div>
						<a href="javascript:;" class="viewMore"><span class="moreBtn">더보기</span></a>
					</div>
					<div class="review_photo">
						<c:if test="${selectUsimRequestReviewIngList !=null and !empty selectUsimRequestReviewIngList}">
							<a href="javascript:;" class="viewMore"> <span>${fn:length(selectUsimRequestReviewIngList)}</span>
								<img src="${selectUsimRequestReviewIngList[0].filePathNm}"  alt="" onerror="this.src='/resources/images_bak/requestReview/noimage.png'">
							</a>
						</c:if>
					</div>
				</div>
				<div class="review_orgn orgnArea" style="display: none;">
					<div class="review_text">
						<div class="top">
							<c:choose>
								<c:when test="${requestReviewDtoList.commendYn eq '1'}">
									<img src="/resources/images_bak/requestReview/icon_recommend.jpg"  alt="">
								</c:when>
								<c:otherwise>
									<img src="/resources/images_bak/requestReview/icon_non_recommend.jpg"  alt="">
								</c:otherwise>
							</c:choose>
							<c:if test="${requestReviewDtoList.ntfYn eq '1'}"><img src="/resources/images_bak/requestReview/icon_best.jpg" class="margin_right_10"  alt=""></c:if>
							${nmcp:getMaskedName(requestReviewDtoList.regNm)} 님/
							${requestReviewDtoList.reqBuyTypeNm}/
							${nmcp:getCodeNm(Constants.REVIEW_EVENT_CD, requestReviewDtoList.eventCd)}/
							<fmt:formatDate value="${requestReviewDtoList.sysRdate}" pattern="yyyy.MM.dd" />
						</div>
						<div class="cont">${requestReviewDtoList.reviewSbst}</div>
						<c:if test="${requestReviewDtoList.snsInfo ne null and requestReviewDtoList.snsInfo ne ''}">
							<div class="sns">
								<span>SNS 공유 URL</span>
								<c:choose>
									<c:when test="${fn:indexOf(requestReviewDtoList.snsInfo,'http') > -1}">
										<a href="${requestReviewDtoList.snsInfo}" class="snslink" target="_black">${requestReviewDtoList.snsInfo}</a>
									</c:when>
									<c:otherwise>
										<a href="https://${requestReviewDtoList.snsInfo}" class="snslink" target="_black">${requestReviewDtoList.snsInfo}</a>
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>
						<a href="javascript:;" class="viewFold"><span class="fold">접기</span></a>
						<div class="review_photo">
							<c:if test="${selectUsimRequestReviewIngList !=null and !empty selectUsimRequestReviewIngList}">
								<c:forEach var="selectUsimRequestReviewIngList" items="${selectUsimRequestReviewIngList}">
									<img src="${selectUsimRequestReviewIngList.filePathNm}" onerror="this.src='/resources/images_bak/requestReview/noimage.png'"  alt="">
								</c:forEach>
							</c:if>
						</div>
					</div>
				</div>
			</c:forEach>
			<div class="board_list_table_paging margin_top_20">
				<nmcp:pageInfo pageInfoBean="${pageInfoBean}"	function="goReviewPaging" />
			</div>
			<div class="reviewBtnWrite">
				<a href="/requestReView/requestReView.do"><img src="/resources/images_bak/requestReview/btn_reviewwrt.jpg"  alt=""></a>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="default">아직 작성된 사용후기가 없습니다.</span>
	</c:otherwise>
</c:choose>