;


$(document).ready(function (){

    $("._btnView").click(function(){
        let iframeSrc = $(this).data("iframeSrc");
        let title = $(this).data("title");


        $("#youTubeIframe").attr('src', iframeSrc);
        $("#modalContentTitle").html(title);



        var el = document.querySelector('#modalContent');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();
    });

});

