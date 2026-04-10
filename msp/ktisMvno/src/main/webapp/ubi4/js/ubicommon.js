var __isDebug = true;

var __gap = 6;
var __ubiViewer;
var __viewerDivId;

function UbiLoad(params, eparams) {

	if( params.ubiserverurl == undefined )
		params.ubiserverurl = params.appurl + "/UbiServer";
	if( params.resource == undefined )
		params.resource = params.appurl + "/ubi4/js";
	if( params.scale == undefined )
		params.scale = "100";
	if( params.isencrypt64 == undefined )
		params.isencrypt64 = "true";
	if( params.clienttype != undefined )
		if( params.clienttype == "grid" )
			params.clienttype = "nexacro";

	var pUbiFile = params['jrffile'];
	var runType = 'report';
	if( pUbiFile.lastIndexOf(".jef") == (pUbiFile.length - 4) )
		runType = 'eform';

	if( runType == 'eform' ) {
		if( params.saveurl == undefined )
			params.saveurl = params.appurl + "/ubi4/ubisave.jsp";
	}
	
	if( __isDebug )
		console.log('[ubicommon]runType : [' + runType + ']');

	// 한 페이지에 여러 개의 뷰어를 띄우는 경우 DIV ID가 중복되는 문제로 userdivid 속성을 추가함
	if( runType == 'report' ) {

		if( params.userdivid != undefined && params.userdivid.trim() != "") {
			params.divid = __viewerDivId = params.userdivid.trim();
		}
		else {
			params.divid = __viewerDivId = 'UbiReportViewerDiv';
		}
		openReportViewer(params, eparams);	// 리포트 뷰어 호출
	}
	else {

		if( params.userdivid != undefined && params.userdivid.trim() != "") {
			params.divid = __viewerDivId = params.userdivid.trim();
		}
		else {
			params.divid = __viewerDivId = 'UbiEFormViewerDiv';
		}
		openEformViewer(params, eparams);	// 이폼 뷰어 호출
	}
	return __ubiViewer;
};

function openReportViewer(params, eparams) {
	
	if( __isDebug ) {
		for( var param in params) {
			console.log('[ubicommon]' + param + " : [" + params[param] + ']');
		}
	}

	//뷰어가 보여질 DIV를 동적으로 생성
	if( document.getElementById(__viewerDivId) == null || document.getElementById(__viewerDivId) == 'undefined' ) {
		var div = document.createElement('div');
		div.id = __viewerDivId;

		div.style.border = '1px solid #A0A0A0';
		div.style.borderBottomWidth = '2px'; 
		document.body.style.margin = '1px';

		document.body.appendChild(div);
	}
	window.addEventListener("resize", UbiResize);

	UbiResize();
	__ubiViewer = new UbiViewer(params);

	// 데이타셑 추가. 추가시 dataset는 json구조여야 함 {"sqlid":"데이타","sqlid":"데이타",...}
	// daatsettype이 xml이 아닌경우는 stream으로 처리
	if( params.datasets != undefined ) {
        for( var key in params.datasets ) {
        	if( params.datasettype != undefined && params.datasettype == "xml")
        		__ubiViewer.setDataset(key, params.datasets[key],"TOBEXML");
        	else
            	__ubiViewer.setDataset(key, params.datasets[key]);
        }
	}

	// 이벤트 설정
	var pReportPreviewEnd = eparams['reportevent.previewend'];
	var pReportPrintEnd = eparams['reportevent.printend'];
	var pReportExportEnd = eparams['reportevent.exportend'];
	var ubiReportPrintClicked = eparams['reportevent.printClicked'];
	var ubiReportExportClicked = eparams['reportevent.exportClicked'];

	//툴바 설정
	__ubiViewer.setPrintMenu("PDF");
	__ubiViewer.setMenuTextToolbar("PRINT_UBI", "Plugin Print");
	__ubiViewer.setVisibleToolbar('PRINT_HTML',false);


	if( pReportPreviewEnd != undefined )
		__ubiViewer.showReport(pReportPreviewEnd);
	else
		__ubiViewer.showReport();
	
	if( pReportPrintEnd != undefined )
		__ubiViewer.events.printEnd = pReportPrintEnd;
	if( pReportExportEnd != undefined )
		__ubiViewer.events.exportEnd = pReportExportEnd;
	if( ubiReportPrintClicked != undefined )
		__ubiViewer.events.printClicked = ubiReportPrintClicked;
	if( ubiReportExportClicked != undefined )
		__ubiViewer.events.exportClicked = ubiReportExportClicked;

	return __ubiViewer 
};

function openEformViewer(params, eparams) {

	if( __isDebug ) {
		for( var param in params) {
			console.log('[ubicommon]' + param + " : [" + params[param] + ']');
		}
	}
	
	// 데이타셑 추가. 추가시 dataset는 json구조여야 함 {"sqlid":"데이타","sqlid":"데이타",...}
	// daatsettype이 xml이 아닌경우는 stream으로 처리
	if( params.datasets != undefined ) {
        for( var key in params.datasets ) {
        	if( params.datasettype != undefined && params.datasettype == "xml")
        		__ubiViewer.setDataset(key, params.datasets[key],"TOBEXML");
        	else
            	__ubiViewer.setDataset(key, params.datasets[key]);
        }
	}

	// 이벤트 설정
	var pEformPreviewEnd = eparams['eformevent.previewend'];
	var pEformSaveEnd = eparams['eformevent.saveend'];

	if( document.getElementById(__viewerDivId) == null || document.getElementById(__viewerDivId) == 'undefined' ) {
		var div = document.createElement('div');
		div.id = __viewerDivId;
		document.body.style.margin = '0px';
		document.body.style.padding = '0px';
		document.body.appendChild(div);
	}
	
	__ubiViewer = new ubireport.eform.Viewer( params );

	if( pEformPreviewEnd != undefined )
		__ubiViewer.showReport(pEformPreviewEnd);
	else
		__ubiViewer.showReport();
	if( pEformSaveEnd != undefined )
		__ubiViewer.events.saveEnd = pEformSaveEnd;
};

function UbiResize() {

	document.getElementById(__viewerDivId).style.width = getViewerWidth() + 'px';
	document.getElementById(__viewerDivId).style.height = getViewerHeight() + 'px';
};

function getViewerWidth() {
	return ((self.innerWidth || (document.documentElement && document.documentElement.clientWidth) || document.body.clientWidth)) - __gap;
};

function getViewerHeight() {
	return 	((self.innerHeight || (document.documentElement && document.documentElement.clientHeight) || document.body.clientHeight)) - __gap;
};
