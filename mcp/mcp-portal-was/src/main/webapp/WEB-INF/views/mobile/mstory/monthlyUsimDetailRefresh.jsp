 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


			<div class="c-modal__body">
				<div class="mstroy-view">
					<div class="swiper-container c-expand">
						<div class="swiper-wrapper" id="detailImgSwiper">
							<c:forEach var="list" items="${mstoryListDto.mstoryAttDtoList}" varStatus="status">
								<div class="swiper-slide">
									<img src="${list.imgNm}" alt="">
								</div>
							</c:forEach>
							
						</div>
						<button class="swiper-prev" type="button">
							<i class="c-icon c-icon--circle-arrow-left" aria-hidden="true"></i>
						</button>
						<button class="swiper-next" type="button">
							<i class="c-icon c-icon--circle-arrow-right" aria-hidden="true"></i>
						</button>
						<div class="swiper-pagination"></div>
					</div>
					<div class="mstroy-view__info">
						<strong class="fs-16 u-fw--bold u-block u-mb--8" id="title">[${mstoryListDto.mstoryDto.mm}월] ${mstoryListDto.mstoryDto.ntcartTitle}</strong> 
						<span class="fs-12 u-co-gray-6 u-mr--16" id="date">
							<fmt:parseDate value="${mstoryListDto.mstoryDto.ntcartDate}" var="postDt" pattern="yyyyMMdd"/>
							<fmt:formatDate value="${postDt}" pattern="yyyy.MM.DD"/>
						</span>
						<span class="fs-12 u-co-gray-6" id="hit">조회 | ${mstoryListDto.mstoryDto.hitCnt}</span>
					</div>
					<hr class="c-hr c-hr--type1 u-mt--24 u-mb--0 c-expand">
					<div class="mstroy-view__short-cut">
						<span class="u-block fs-14 u-co-sub-1">매월 새롭고 다양한 이야기</span> <strong
							class="u-block fs-20 u-fw--bold u-mt--4">월간 You 心</strong>
						<div class="mstroy-view__short-cut-swiper">
							<div class="swiper-wrapper" id="detailList">
							<c:forEach var="list" items="${mstoryListDto.mstoryDetailList}" varStatus="status">
								<c:if test="${list.mm eq mstoryListDto.mstoryDto.mm}">
									<input type="text" id="mon" value="${status.index - 1}" hidden/>
								</c:if>
								<c:if test="${list.mm ne mstoryListDto.mstoryDto.mm}">
									<div class="swiper-slide" onclick="setNewPopData(${list.ntcartSeq})">
										<a href="#n"> <img
											src="${list.thumbImgNm}"
											alt=""> <strong
											class="u-block fs-14 u-co-gray-7 u-ta-center">${list.mm}월</strong>
										</a>
									</div>
								</c:if>
							</c:forEach>
								
							</div>
						</div>
						<button class="swiper-prev short-cut" type="button">
							<i class="c-icon c-icon--anchor-reverse" aria-hidden="true"></i>
						</button>
						<button class="swiper-next short-cut" type="button">
							<i class="c-icon c-icon--anchor" aria-hidden="true"></i>
						</button>
					</div>
				</div>
			</div>

