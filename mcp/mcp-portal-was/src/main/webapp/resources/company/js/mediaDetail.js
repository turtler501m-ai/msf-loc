$(document).ready(function() {
    //이전,다음 버튼 활성화
    updatePaging();
});

//목록
function goList(pageNo){

    let form = $('<form></form>');
    form.attr('method', 'get');
    form.attr('action', '/company/mediaList.do');

    let hiddenInput = $('<input>', {
        type: 'hidden',
        name: 'pageNo',
        value: pageNo
    });

    form.append(hiddenInput);
    $('body').append(form);
    form.submit();
}

// 이전, 다음 페이지 누를 시
function goPage(direction) {

    let preSeq = $('#prevSeq').val();
    let nextSeq = $('#nextSeq').val();
    let curPage = $('#curPage').val();

    if (direction == 'prev') {
        if (preSeq == 0) {
            return;
        }
        loadPage(preSeq,curPage, direction);
    } else if (direction == 'next') {
        if (nextSeq == 0) {
            return;
        }
        loadPage(nextSeq,curPage, direction);
    }

    updatePaging();
}

// 이전, 다음 페이지로 이동
function loadPage(newsDatSeq, pageNo, direction) {
    console.log("pageNo:"+pageNo)
    let linkTarget = 'T'; //현재창 고정

    let form = $('<form></form>');
    form.attr('method', 'get');
    form.attr('action', '/company/mediaDetail.do');

    let seqInput = $('<input>', {
        type: 'hidden',
        name: 'newsDatSeq',
        value: newsDatSeq
    });
    let hiddenInput = $('<input>', {
        type: 'hidden',
        name: 'pageNo',
        value: pageNo
    });

    let direct = $('<input>', {
        type: 'hidden',
        name: 'direction',
        value: direction
    });

    form.append(hiddenInput);
    form.append(seqInput);
    form.append(direct);
    /*form.append(targetInput);*/

    $('body').append(form);
    form.submit();
}

// 이전, 다음 활성화
function updatePaging() {

    let preSeq = $('#prevSeq').val();
    let nextSeq = $('#nextSeq').val();

    if (preSeq == 0) {
        $('#prevPage').removeClass('active');
    } else {
        $('#prevPage').addClass('active');
    }

    if (nextSeq == 0) {
        $('#nextPage').removeClass('active');
    } else {
        $('#nextPage').addClass('active');
    }
}