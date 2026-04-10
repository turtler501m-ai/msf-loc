$(document).ready(function(){

    let previousWidth = window.innerWidth;

    window.addEventListener('resize', function () {
        let currentWidth = window.innerWidth;

        //모바일 -> PC로 변할 때
        if (previousWidth < 768 && currentWidth >= 768) {
            location.reload();
            $('.board-list').empty();
            goPaging(1,'N');
        }

        //PC -> 모바일로 변할 때
        if (previousWidth >= 768 && currentWidth < 768) {
            location.reload();
            $('.board-list').empty();
            loadMoreData(1);
        }

        previousWidth = currentWidth;
    });

    let currentPage = 1; // 초기 페이지
    let totalPageCount = parseInt($("#totalCount").text()); // 총 페이지 수

    // 더보기 버튼 클릭 시 이벤트
    $("#paging-more-btn").click(function () {
        if (currentPage < totalPageCount) {
            currentPage++;
            loadMoreData(currentPage);
        }
    });
});

//pc 페이징
function goPaging(pageNo,flag='Y'){

    // 활성화된 페이징 버튼만 클릭하도록 제한
    if(!$(event.currentTarget).hasClass('paging-number') && flag == 'Y'){
        if (!$(event.currentTarget).hasClass('active')) {
            return;
        }
    }

    let form = $('<form></form>');
    form.attr('method', 'get');
    form.attr('action', '/company/noticeList.do');

    let hiddenInput = $('<input>', {
        type: 'hidden',
        name: 'pageNo',
        value: pageNo
    });

    form.append(hiddenInput);
    $('body').append(form);
    form.submit();
}

//mo 더보기
function loadMoreData(pageNo) {
    $.ajax({
        url: '/company/moreNoticeList.do',
        method: 'POST',
        data: {
            pageNo: pageNo
        },
        success: function (response) {

            const board = response.boardList;
            const totalPageCount = response.totalPageCount;
            const currentPage = response.currentPage;

            // 데이터를 리스트에 추가
            board.forEach(board => {
                const newsItemHtml = `
                   <li class="board-notice-item">
                        <a href="javascript:void(0);"
                           onclick="goDetail(${board.boardSeq}, ${currentPage});"
                           data-seq="${board.boardSeq}">
                            <div class="board-notice-title-wrap">
                                <p class="board-notice-title">
                                    <span class="board-notice-number">${board.boardNum}</span>
                                    ${board.boardSubject}
                                </p>
                                <p class="board-notice-post">
                                    <span>${board.boardWriteDt}</span>
                                </p>
                            </div>
                            <p class="board-notice-post mo">
                                <span>${board.boardWriteDt}</span>
                            </p>
                        </a>
                    </li>
                `;
                $('.board-notice-list').append(newsItemHtml);
            });

            //더보기 숫자 변경
            $('#listCount').text(currentPage);

            // 페이지가 마지막 페이지에 도달하면 "더보기" 버튼 숨기기
            if (currentPage >= totalPageCount) {
                $('.paging-more-wrap').hide();
            }
        },
        error: function (error) {
            alert("새로고침 후 다시 시도해주세요.");
        }
    });
}

//상세 페이지 이동
function goDetail(seq, page) {

   /* let newsDatSeq = $(event.currentTarget).data('seq');
    let linkTarget = $(event.currentTarget).data('target');*/

    let form = $('<form></form>');
    form.attr('method', 'get');
    form.attr('action', '/company/noticeDetail.do');

    let seqInput = $('<input>', {
        type: 'hidden',
        name: 'boardSeq',
        value: seq
    });
    let pageNo = $('<input>', {
        type: 'hidden',
        name: 'pageNo',
        value: page
    });
/*    let targetInput = $('<input>', {
        type: 'hidden',
        name: 'linkTarget',
        value: linkTarget
    });*/

    form.append(seqInput);
    form.append(pageNo);
    //form.append(targetInput);

    $('body').append(form);
    form.submit();
}




// 날짜 포맷 함수
function formatDate(date) {
    let d = new Date(date);
    let year = d.getFullYear();
    let month = ('0' + (d.getMonth() + 1)).slice(-2);
    let day = ('0' + d.getDate()).slice(-2);
    return year + '.' + month + '.' + day;
}

