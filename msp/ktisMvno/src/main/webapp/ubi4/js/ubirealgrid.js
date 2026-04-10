// ------------------------------------------------------------
// UbiReport
// ------------------------------------------------------------
var currRowIdx = 0;
var currColIdx = 0;
var colnames = [];
var headerColNames = [];
var rowCount = 0;
var _rs_ = String.fromCharCode(30);
var _cs_ = String.fromCharCode(31);
var realgrid;

var groups = [];
var designData = [];
var bodydata = [];
var summdata = [];
var runtimeData = [];
var dispColumnNames = [];


/*
	*** MAIN 함수 : 컬럼명 도출 및 디자인 도출***
	 
	메인함수 매커니즘
	 
	1.헤더 그룹형태 분석하여 정보 반환.
    getHeaderColumns(layout[i]);

    2.바디 그룹형태 분석하여 정보 반환.
    getBodyColumns(layout[i]);

	3.Header 정보 계산해서 디자인만들고, ssv데이터 던진 후 복귀.
    getHeaderDesign(headerColObjects);

    4.Body 정보 계산해서 디자인만들고, ssv데이터 던진 후 복귀.
    getBodyDesign(bodyColObjects);

	5.Body 부분 Cell 정보 및 Data 설정 정보 생성.
	
*/
function getColumns(column, dataProv) {

    var layout = [];

    layout = realgrid.saveColumnLayout(); // layout의 정보를 담은 객체를 반환.

    for (i = 0; i < layout.length; i++) {
        if (layout[i].items != null) {
            groups.push(layout[i].items);
        } else {
            groups.push(layout[i]);
        }
    }

    var headerColObjects = [];
    var bodyColObjects = [];
    for (var i = 0; i < layout.length; i++) {
        if (groups.length > 0) {
            if (layout[i].items) {
                currRowIdx = 0;
                headerColNames = [];
                bodyColNames = [];
                //1.헤더 그룹형태 분석 함수 호출
                getHeaderColumns(layout[i]);
                //2.바디 그룹형태 분석 함수 호출
                getBodyColumns(layout[i]);
				addNum = 0;
                headerColObjects.push(headerColNames);
                bodyColObjects.push(bodyColNames);
            } else {
                headerColObjects.push(layout[i].column); //layout[i]가 단일원소 일때.
                bodyColObjects.push(layout[i].column); //layout[i]가 단일원소 일때.
            }
        } else {
            headerColObjects.push(layout[i].name);
            bodyColObjects.push(layout[i].name);
        }
    }

    // 컬럼 displayIndex 로 Sorting
    if (groups.length > 0) {

    } else {
        headerColObjects.sort(function(col1, col2) {
            if (realgrid.getColumnProperty(col1, "displayIndex") > realgrid.getColumnProperty(col2, "displayIndex")) {
                return 1;
            }
            if (realgrid.getColumnProperty(col1, "displayIndex") < realgrid.getColumnProperty(col2, "displayIndex")) {
                return -1;
            }
            return 0;
        });
    }

    //3.Header 정보 계산해서 디자인만들고, ssv데이터 던진 후 복귀.
    getHeaderDesign(headerColObjects);
    //4.Body 정보 계산해서 디자인만들고, ssv데이터 던진 후 복귀.
    getBodyDesign(bodyColObjects);

    designData.push(bodydata.join(""));
    if (groups.length > 0) {
        designData.push(summdata.join(""));
    }


    // 5.Body 부분 Cell 정보
    /* HTML 뷰어인 경우 설정 처리 */
    designData.push("Dataset:dsGrid" + _rs_);
    designData.push("_RowType_" + _cs_);
    for (var i = 0; i < dispColumnNames.length; i++) {
        if (i == (dispColumnNames.length - 1)) {
            designData.push("column" + i + ":string(256)" + _rs_);
        } else {
            designData.push("column" + i + ":string(256)" + _cs_);
        }
    }

    // data 정보 도출
    var rowCount = Math.max(dataProv.getRowCount(), realgrid.getItemCount());
    var dataRow;
    var datas = [];

	let fields = dataProv.getFields();

    for (var i = 0; i < rowCount; i++) {
        dataRow = realgrid.getDataRow(i);

        if (dataRow != -1) {
            runtimeData.push("I" + _cs_);
            datas[i] = dataProv.getOutputRow({
                datetimeFormat: "yyyy/MM/dd"
            }, dataRow);

            for (var j = 0; j < dispColumnNames.length; j++) {
				var coldata = datas[i][dispColumnNames[j]];
				
				//셀렉트박스 적용	
				var isPartialMatch = realgrid.columnByField(dispColumnNames[j]);
				if (isPartialMatch == null) {//테스트
					var temp = realgrid.columnByName(dispColumnNames[j]);
					//console.log(temp.fieldName);
                    coldata = datas[i][temp.fieldName];
                }

                //체크박스 적용
                var isCheckboxColumn = realgrid.columnByField(dispColumnNames[j]);

                if (isCheckboxColumn != null) {
                    if (isCheckboxColumn.renderer != null) {
                        if (isCheckboxColumn.renderer.type != null) {
                            if (isCheckboxColumn.renderer.type == 'check') {
                                if (coldata == true) {
                                    coldata = '☑';
                                } else {
                                    coldata = '☐';
                                }
                            }
                        }
                    }
                }
                
                if (coldata == undefined) {
                    coldata = '';
                }

				//숫자형 기본 포멧 적용
                var numformat = '';
                var column = realgrid.columnByField(dispColumnNames[j]);
                numformat = realgrid.getColumnProperty(column, "numberFormat");
                var valueType = realgrid.getColumnProperty(column, "valueType");
				if(valueType == 'number') {
					if (numformat != null) {
	                    coldata = coldata.format();
	                    if (coldata == 'NaN') {
	                        coldata = "";
	                    }
	                } else {
						coldata = parseFloat(coldata).toFixed(1);
						coldata = numFormat(coldata+"",1);
					}	
				}
				
				//suffix, prefix 적용
				var prefix = "";
				var suffix = "";
				prefix = realgrid.getColumnProperty(column, "prefix");
				suffix = realgrid.getColumnProperty(column, "suffix");
				if(suffix == undefined){
					suffix = '';
				}
				if(prefix == undefined){
					prefix = '';
				}
				coldata = prefix+coldata+suffix;
				
                
                if (j == (dispColumnNames.length - 1)) {
                    runtimeData.push(coldata + _rs_);
                } else {
                    runtimeData.push(coldata + _cs_);
                }
            }
        } else {
	
            var item = realgrid.getGroupModel(i);
            var aModel = realgrid.getModel(i);
            if (aModel) {
                var footer = realgrid.getFooter();

                if (aModel.type != "group") {
                    aModel = realgrid.getParentModel(aModel);
                    if (aModel && aModel.type == "group") {
                        runtimeData.push("I" + _cs_);
                        for (var j = 0; j < dispColumnNames.length; j++) {
                            var summary = realgrid.getGroupSummary(aModel, dispColumnNames[j]);
                            if (summary) {
                                var column = realgrid.columnByField(dispColumnNames[j]);

                                var coldata = "";
                                var footertxt = "";
                                var expression = "";
                                if (realgrid.getColumnProperty(column, "footer").groupText) {
                                    footertxt = realgrid.getColumnProperty(column, "footer").groupText;
                                }

                                if (realgrid.getColumnProperty(column, "footer").groupExpression != null) {
                                    expression = realgrid.getColumnProperty(column, "footer").groupExpression;
                                }
                                if (expression == "count") {
                                    coldata = summary.count;
                                } else if (expression == "sum") {
                                    coldata = summary.sum;
                                } else if (expression == "max") {
                                    coldata = summary.max;
                                } else if (expression == "min") {
                                    coldata = summary.min;
                                } else if (expression == "avg") {
                                    coldata = summary.avg;
                                } else {
                                    coldata = footertxt; // text
                                }
                                if (j == (dispColumnNames.length - 1)) {
                                    runtimeData.push(coldata + _rs_);
                                } else {
                                    runtimeData.push(coldata + _cs_);
                                }
                            }
                        }
                    }
                }
            }


        }

    }

    this.runtimedata.push(runtimeData.join(""));
    console.log(designData.join(""));
    //console.log(this.runtimedata);
    return designData.join("");
    designData = [];
}


// Header Column 도출
function getHeaderColumns(group) {

    var groupcolumns = group.items; 
	var addNum = 0;
	
	if (!headerColNames[currRowIdx] && group.header.visible) {
	    headerColNames[currRowIdx] = [];
	    headerColNames[currRowIdx].push(group.name);
	    currRowIdx++;
	}

    if (groupcolumns) { //
        if (group.direction == 'vertical' && groupcolumns.length > 1) {
            addNum = 1;
        } else {
            addNum = 0;
        }
       
        for (var i = 0; i < groupcolumns.length; i++) {
            if (groupcolumns[i].items) {                
				if(groupcolumns[i].vindex > 0 && currRowIdx > 1 && groupcolumns.length > 1) {
					currRowIdx--;	
					headerColNames[currRowIdx].push(groupcolumns[i].name);
					currRowIdx++;
				}
                getHeaderColumns(groupcolumns[i]);

            } else {
                if (!headerColNames[currRowIdx]) {
                    headerColNames[currRowIdx] = [];
                }
                headerColNames[currRowIdx].push(groupcolumns[i].column); 
            }
            currRowIdx += addNum;
            rowCount = Math.max(rowCount, currRowIdx);
        }
    }
}

// Body Column 도출
function getBodyColumns(group) {

    var groupcolumns = group.items; 
    var addNum = 0;
    var currRowIdx = 0; 

    if (groupcolumns) { 
        if (group.direction == 'vertical') {
            addNum = 1;
        } else {
            addNum = 0;
        }

        for (var i = 0; i < groupcolumns.length; i++){
            if (groupcolumns[i].items) {
                if (addNum == 0) {
                    currRowIdx = 0;
                }
                getBodyColumns(groupcolumns[i]);
            } else {
                if (!bodyColNames[currRowIdx]) {
                    bodyColNames[currRowIdx] = [];
                }
                bodyColNames[currRowIdx].push(groupcolumns[i].column); 
            }
            currRowIdx += addNum;

            rowCount = Math.max(rowCount, currRowIdx);
        }
    }
}



// headerColObjects로 헤더 디자인정보 만드는 함수 --> columnArray, colspanArray, rowspanArray 도출
function getHeaderDesign(headerColObjects) {
    // 행열의 크기 도출
    var rowcount = 0;
    var colcount = 0;
    for (var i = 0; i < headerColObjects.length; i++) {
        if (typeof(headerColObjects[i]) == 'object') {
            if (groups.length > 0) {
                var groupColount = 0;
                for (var j = 0; j < headerColObjects[i].length; j++) {
                    for (var k = 0; k < headerColObjects[i][j].length; k++) {
                        groupColount = Math.max(groupColount, headerColObjects[i][j].length);
                    }
                }
                colcount += groupColount;
                rowcount = Math.max(rowcount, headerColObjects[i].length);
            } else {
                colcount++;
                rowcount = 0;
            }
        } else {
            colcount++;
        }
    }

    if (rowcount == 0) {
        rowcount = 1;
    }

    // 행열 크기에 맞는 col, row index 정보 생성
    var columnArray = new Array(rowcount);
    var rowspanArray = new Array(rowcount);
    var colspanArray = new Array(rowcount);
    var widthArray = new Array(rowcount);
    for (var i = 0; i < rowcount; i++) {
        columnArray[i] = new Array(colcount);
        colspanArray[i] = new Array(colcount);
        rowspanArray[i] = new Array(colcount);
        widthArray[i] = new Array(colcount);

        // rowspan 초기화
        for (var j = 0; j < colcount; j++) {
            rowspanArray[i][j] = 1;
        }
    }

    var colidx = 0;
    var rowidx = 0;

    for (var i = 0; i < headerColObjects.length; i++) {
        
        if (typeof(headerColObjects[i]) == 'object') {
            if (groups.length > 0) {
                var groupColount = 0;
                for (var j = 0; j < headerColObjects[i].length; j++) {
                    for (var k = 0; k < headerColObjects[i][j].length; k++) {
						
                        // 컬럼 이름(columnByName)으로 컬럼정보 도출
                        var columnByName = realgrid.columnByName(headerColObjects[i][j][k]);
                        if (columnByName == null) { //그룹헤더라면..
                            columnByName = realgrid.layoutByName(headerColObjects[i][j][k]);
                            columnArray[j][colidx + k] = columnByName;
                            colspanArray[0][colidx + k] = 1;
                        	rowspanArray[0][colidx + k] = 1;
                        } else { //컬럼헤더라면..
                            columnArray[j][colidx + k] = columnByName;
                            colspanArray[0][colidx + k] = 1;
                            if(headerColObjects[i][j].length < rowcount) {
								rowspanArray[j][colidx + k] = rowcount - headerColObjects[i].length + 1;
							} else {
								rowspanArray[j][colidx + k] = 1;
							}
                        	
                        }
                        // 컬럼 숨김 정보 도출
                        var visible = realgrid.getColumnProperty(headerColObjects[i][j][k], "visible");

                        if (visible == true) {
                            widthArray[j][colidx + k] = realgrid.layoutByName(headerColObjects[i][j][k]).cellWidth; //넓이 방식 수정 : layoutByName()

                        } else {
                            if (visible == undefined) { //그룹헤더의 경우에는 undefined 떠서 layoutByName메서드로 visible 정보 가져온다. 
                                visible = realgrid.layoutByName(headerColObjects[i][j][k]).visible;
                                if (visible == true) {
                                    widthArray[j][colidx + k] = realgrid.layoutByName(headerColObjects[i][j][k]).cellWidth;
                                } else if (visible == false) {
                                    widthArray[j][colidx + k] = 0;
                                }

                            } else {
                                widthArray[j][colidx + k] = 0;
                            }
                        }
                        groupColount = Math.max(groupColount, headerColObjects[i][j].length);
                    }
                    
                }
                colidx += groupColount;
            } else {
                columnArray[0][colidx] = headerColObjects[i].fieldName;
                colspanArray[0][colidx] = 1;
                rowspanArray[0][colidx] = rowcount;
                
                var visible = realgrid.getColumnProperty(headerColObjects[i], "visible");
                if (visible == true) {
                    widthArray[0][colidx] = realgrid.getColumnProperty(headerColObjects[i], "displayWidth");
                } else {
                    widthArray[0][colidx] = 0;
                }
                colidx++;
            }
        } else { //headerColObjects[i]원소가 단일 원소일때 ..
            columnArray[0][colidx] = realgrid.columnByField(headerColObjects[i]);
            colspanArray[0][colidx] = 1;
            rowspanArray[0][colidx] = rowcount;
            var visible = realgrid.getColumnProperty(headerColObjects[i], "visible");
            if (visible == true) {
                widthArray[0][colidx] = realgrid.getColumnProperty(headerColObjects[i], "displayWidth");
            } else {
                widthArray[0][colidx] = 0;
            }
            colidx++;
        }
    }

    // 넓이 정보 생성
    var widths = new Array(colcount);

    for (var i = 0; i < colcount; i++) {
        var width = 0;
        for (var j = 0; j < rowcount; j++) {
            if (widthArray[j][i]) {
                if (width == 0) {
                    width = widthArray[j][i];
                } else {
                    width = Math.min(width, widthArray[j][i]);
                }
            }
        }
        widths[i] = width;
    }

    // 높이 정보 생성 (header, body, summary 모두 반영)
    var heights;

    if (groups.length > 0) {
        heights = new Array(rowcount * 2 * 2);
        for (var i = 0; i < rowcount * 2 * 2; i++) {
            heights[i] = 20; // 추후 수정.
        }
    } else {
        heights = new Array(rowcount * 2); // @@@ body까지만 반영 추후 summ 부분 반영할것
        for (var i = 0; i < rowcount * 2; i++) {
            heights[i] = 20; // 추후 수정.
        }
    }

    // colspan 정보 생성
    for (var i = 0; i < rowcount; i++) {
        for (var j = 0; j < colcount; j++) {
            if (j < (colcount - 1) && columnArray[i][j] && !columnArray[i][j + 1]) {
                // 넓이를 이용하여 colspan 도출
                colspanArray[i][j] = 0;
                var sum = 0;
                for (var k = j; k < colcount; k++) {
                    sum += widths[k];
                    if (widthArray[i][j] >= sum) {
                        colspanArray[i][j]++;
                    } else {
                        break;
                    }
                }
            } else {
                colspanArray[i][j] = 1;
            }
        }
    }

    // ----------------------------------------------------
    // Runtime Design data 생성
    // ----------------------------------------------------
    designData = [];
    runtimeData = [];
    var type = "";

    designData.push("Dataset:dsDesign" + _rs_);
    designData.push("_RowType_" + _cs_);
    designData.push("type:string(256)" + _cs_);
    designData.push("row:string(256)" + _cs_);
    designData.push("col:string(256)" + _cs_);
    designData.push("rowspan:string(256)" + _cs_);
    designData.push("colspan:string(256)" + _cs_);
    designData.push("align:string(256)" + _cs_);
    designData.push("text:string(256)" + _cs_);
    designData.push("merge:string(256)" + _cs_);
    designData.push("suppressing:string(256)" + _cs_);
    designData.push("background:string(256)" + _cs_); //bgcolor->background
    designData.push("backgroundexpr:string(256)" + _cs_);
    designData.push("leftmargin:string(256)" + _cs_);
    designData.push("topmargin:string(256)" + _cs_);
    designData.push("rightmargin:string(256)" + _cs_);
    designData.push("bottommargin:string(256)" + _cs_);
    designData.push("foreground:string(256)" + _rs_); //폰트색 추가 : foreground 
    type = "widths";
    designData.push("I" + _cs_);
    designData.push(type + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + widths + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _rs_);
    type = "heights";
    designData.push("I" + _cs_);
    designData.push(type + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + heights + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _cs_);
    designData.push("" + _rs_);

    // Header 정보 생성
    type = "head";

    for (var i = 0; i < rowcount; i++) {
        for (var j = 0; j < colcount; j++) {
            if (columnArray[i][j]) {
                // header
                designData.push("I" + _cs_); // RowType
                designData.push(type + _cs_); // type
                designData.push("" + i + _cs_); // row
                designData.push("" + j + _cs_); // col
                designData.push("" + rowspanArray[i][j] + _cs_); // rowspan
                designData.push("" + colspanArray[i][j] + _cs_); // colspan

                var headertxt = '';
                var align = '';
                var hexbgcolor = 'ffffff';
                var headerStyleName = '';
                var suppress = 'false';
                var merge = '';
                var visible = '';
                var foreground = '';
                if (groups.length > 0) {
					
                    headertxt = columnArray[i][j].header.text; //text
                    if(headertxt == undefined) {
						headertxt = columnArray[i][j].name;
					}
                    styleName = realgrid.getColumnProperty(columnArray[i][j], "styleName"); //수정
                    designData.push("center" + _cs_); //헤더의 align은 center로 세팅

                    suppress = realgrid.getColumnProperty(columnArray[i][j], "equalBlank");
                    merge = realgrid.getColumnProperty(columnArray[i][j], "mergeRule");
                    visible = realgrid.getColumnProperty(columnArray[i][j], "visible");
                } else {
                    var column = realgrid.columnByField(columnArray[i][j]); //
                    headertxt = realgrid.getColumnProperty(column, "header").text;
                    styleName = realgrid.getColumnProperty(column, "styleName"); //수정

                    if (styleName != null && styleName == "left-column") {
                        align = 'left';
                        designData.push("" + align + _cs_); // align : left
                    } else if (styleName != null && styleName == "right-column") {
                        align = 'right';
                        designData.push("" + align + _cs_); // align : right
                    } else if (styleName != null && styleName == "center-column") {
                        align = 'center';
                        designData.push("" + align + _cs_); // align : center
                    } else {
                        /*RealGrid2에서는 배경색이나 폰트색(=전경색), 정렬을 css로 해결하기 때문에 경우가 많습니다.
                        	따라서, else문에는 위에 정의해준 경우에 해당하지 않는 경우인, '초기값'을 세팅해줍니다.
                        */
                        designData.push("" + _cs_); // align : 초기값 (""=center) 
                    }
                    suppress = realgrid.getColumnProperty(column, "equalBlank");
                    merge = realgrid.getColumnProperty(column, "mergeRule");
                    visible = realgrid.getColumnProperty(column, "visible");
                }

                designData.push("" + headertxt + _cs_); // text
                designData.push("" + _cs_); // merge
                designData.push("" + _cs_); // suppressing	
                if (realgrid.getColumnProperty(columnArray[i][j], "header") != null) {
                    headerStyleName = realgrid.getColumnProperty(columnArray[i][j], "header").styleName;
                    if (headerStyleName != null && headerStyleName == "orange-column") {
                        designData.push("" + "243.156.18" + _cs_); // bgcolor : orange-column
                    } else {
                        designData.push("" + "233.233.233" + _cs_); // bgcolor	//그룹헤더의 컬럼 부분
                    }
                } else {
                    designData.push("" + "211.211.211" + _cs_); // bgcolor	//그룹헤더의 이름 부분

                }
                //designData.push(""+ "243.156.18" +_cs_);	// backgroundexpr : orange-column
                designData.push("" + "255.255.255" + _cs_); // backgroundexpr
                designData.push("1" + _cs_);
                designData.push("1" + _cs_);
                designData.push("1" + _cs_);
                designData.push("1" + _cs_);
                if (headerStyleName != null && headerStyleName == "orange-column") {
                    //foreground = '243.156.18';//오랜지 폰트색
                    foreground = ''; //검정
                    designData.push("" + foreground + _rs_); //foreground
                } else if (styleName != null && styleName == "blue-column") {
                    foreground = '0.0.255';
                    designData.push("" + foreground + _rs_); //foreground : blue
                } else if (styleName != null && styleName == "yellow-column") {
                    foreground = '255.255.0';
                    designData.push("" + foreground + _rs_); //foreground : yellow
                } else {
                    designData.push("" + _rs_); //foreground : 폰트색 초기값 : 검정
                } 
            }
        }
    }
}



// bodyColObjects로 바디 디자인정보 만드는 함수 --> columnArray, colspanArray, rowspanArray 도출
function getBodyDesign(bodyColObjects) {
    // 행열의 크기 도출
    var rowcount = 0;
    var colcount = 0;
    for (var i = 0; i < bodyColObjects.length; i++) {
        if (typeof(bodyColObjects[i]) == 'object') {
            if (groups.length > 0) {
                var groupColount = 0;
                for (var j = 0; j < bodyColObjects[i].length; j++) {
                    for (var k = 0; k < bodyColObjects[i][j].length; k++) {
                        groupColount = Math.max(groupColount, bodyColObjects[i][j].length);
                    }
                }
                colcount += groupColount;
                rowcount = Math.max(rowcount, bodyColObjects[i].length);
            } else {
                colcount++;
                rowcount = 0;
            }
        } else {
            colcount++;
        }
    }

    if (rowcount == 0) {
        rowcount = 1;
    }

    // 행열 크기에 맞는 col, row index 정보 생성
    var columnArray = new Array(rowcount);
    var rowspanArray = new Array(rowcount);
    var colspanArray = new Array(rowcount);
    var widthArray = new Array(rowcount);
    for (var i = 0; i < rowcount; i++) {
        columnArray[i] = new Array(colcount);
        colspanArray[i] = new Array(colcount);
        rowspanArray[i] = new Array(colcount);
        widthArray[i] = new Array(colcount);

        // rowspan 초기화
        for (var j = 0; j < colcount; j++) {
            rowspanArray[i][j] = 1;
        }
    }

    var colidx = 0;
    var rowidx = 0;

    for (var i = 0; i < bodyColObjects.length; i++) {

        if (typeof(bodyColObjects[i]) == 'object') {
            if (groups.length > 0) {
                var groupColount = 0;
                for (var j = 0; j < bodyColObjects[i].length; j++) {
                    for (var k = 0; k < bodyColObjects[i][j].length; k++) {
                        // 컬럼 이름(columnByName)으로 컬럼정보 도출
                        var columnByName = realgrid.columnByName(bodyColObjects[i][j][k]);
                        if (columnByName == null) { //그룹헤더라면..
                            columnByName = realgrid.layoutByName(bodyColObjects[i][j][k]);
                            columnArray[j][colidx + k] = columnByName;
                        } else { //컬럼헤더라면..
                            columnArray[j][colidx + k] = columnByName;
                        }

                        colspanArray[0][colidx + k] = 1;
                        rowspanArray[0][colidx + k] = 1;
                        // 컬럼 숨김 정보 도출
                        var visible = realgrid.getColumnProperty(bodyColObjects[i][j][k], "visible");

                        if (visible == true) {
                            widthArray[j][colidx + k] = realgrid.layoutByName(bodyColObjects[i][j][k]).cellWidth;; //넓이 방식 수정2

                        } else {
                            if (visible == undefined) { //그룹헤더의 경우에는 undefined 떠서 layoutByName메서드로 visible 정보 가져온다. 
                                visible = realgrid.layoutByName(bodyColObjects[i][j][k]).visible;
                                if (visible == true) {
                                    widthArray[j][colidx + k] = realgrid.layoutByName(bodyColObjects[i][j][k]).cellWidth;
                                } else if (visible == false) {
                                    widthArray[j][colidx + k] = 0;
                                }

                            } else {
                                widthArray[j][colidx + k] = 0;
                            }
                        }
                        groupColount = Math.max(groupColount, bodyColObjects[i][j].length);
                    }
                }
                colidx += groupColount;
            } else {
                columnArray[0][colidx] = bodyColObjects[i].fieldName;
                colspanArray[0][colidx] = 1;
                rowspanArray[0][colidx] = rowcount;
                var visible = realgrid.getColumnProperty(bodyColObjects[i], "visible");
                if (visible == true) {
                    widthArray[0][colidx] = realgrid.getColumnProperty(bodyColObjects[i], "displayWidth");
                } else {
                    widthArray[0][colidx] = 0;
                }
                colidx++;
            }
        } else { //bodyColObjects[i]원소가 단일 원소일때 ..
            columnArray[0][colidx] = realgrid.columnByField(bodyColObjects[i]);
            colspanArray[0][colidx] = 1;
            rowspanArray[0][colidx] = rowcount;
            var visible = realgrid.getColumnProperty(bodyColObjects[i], "visible");
            if (visible == true) {
                widthArray[0][colidx] = realgrid.getColumnProperty(bodyColObjects[i], "displayWidth");
            } else {
                widthArray[0][colidx] = 0;
            }
            colidx++;
        }
    }

    // 넓이 정보 생성
    var widths = new Array(colcount);
    for (var i = 0; i < colcount; i++) {
        var width = 0;
        for (var j = 0; j < rowcount; j++) {
            if (widthArray[j][i]) {
                if (width == 0) {
                    width = widthArray[j][i];
                } else {
                    width = Math.min(width, widthArray[j][i]);
                }
            }
        }
        widths[i] = width;
    }

    // 높이 정보 생성 (header, body, summary 모두 반영)
    var heights;
    if (groups.length > 0) {
        heights = new Array(rowcount * 2 * 2);
        for (var i = 0; i < rowcount * 2 * 2; i++) {
            heights[i] = 20; // 추후 수정.
        }
    } else {
        heights = new Array(rowcount * 2); // @@@ body까지만 반영 추후 summ 부분 반영할것
        for (var i = 0; i < rowcount * 2; i++) {
            heights[i] = 20; // 추후 수정.
        }
    }

    // colspan 정보 생성
    for (var i = 0; i < rowcount; i++) {
        for (var j = 0; j < colcount; j++) {
            if (j < (colcount - 1) && columnArray[i][j] && !columnArray[i][j + 1]) {
                // 넓이를 이용하여 colspan 도출
                colspanArray[i][j] = 0;
                var sum = 0;
                for (var k = j; k < colcount; k++) {
                    sum += widths[k];
                    if (widthArray[i][j] >= sum) {
                        colspanArray[i][j]++;
                    } else {
                        break;
                    }
                }
            } else {
                colspanArray[i][j] = 1;
            }

            if (columnArray[i][j]) {
                if (groups.length > 0) {
                    dispColumnNames.push(columnArray[i][j].name);
                } else {
                    dispColumnNames.push(columnArray[i][j]);
                }
            }
        }
    }

    bodydata = [];
    summdata = [];
    for (var i = 0; i < rowcount; i++) {
        for (var j = 0; j < colcount; j++) {
            if (columnArray[i][j]) {

                var align = '';
                var hexbgcolor = 'ffffff';
                var suppress = 'false';
                var merge = '';
                var visible = '';
                var direction = '';


                headertxt = columnArray[i][j].header.text; //text
                styleName = realgrid.getColumnProperty(columnArray[i][j], "styleName"); //수정
                suppress = realgrid.getColumnProperty(columnArray[i][j], "equalBlank");
                merge = realgrid.getColumnProperty(columnArray[i][j], "mergeRule");
                visible = realgrid.getColumnProperty(columnArray[i][j], "visible");
				vindex = realgrid.getColumnProperty(columnArray[i][j], "vindex");
				direction = columnArray[i][j].layout.parent.direction;
                // body
                bodydata.push("I" + _cs_); // RowType
                bodydata.push("body" + _cs_); // type
                bodydata.push("" + i + _cs_); // row
                bodydata.push("" + j + _cs_); // col
                bodydata.push("" + rowspanArray[i][j] + _cs_); // rowspan
                bodydata.push("" + colspanArray[i][j] + _cs_); // colspan
                /*
                	RealGrid2에서는 배경색이나 폰트색(=전경색), 정렬을 css로 해결하기 때문에 경우가 많습니다.
                	따라서, else문에는 위에 정의해준 경우에 해당하지 않는 경우인, '초기값'을 세팅해줍니다.
                */
                if (styleName != null && styleName == "left-column") {
                    align = 'left';
                    bodydata.push("" + align + _cs_); // align : left
                } else if (styleName != null && styleName == "right-column") {
                    align = 'right';
                    bodydata.push("" + align + _cs_); // align : right
                } else if (styleName != null && styleName == "center-column") {
                    align = 'center';
                    bodydata.push("" + align + _cs_); // align : center
                } else {
                    bodydata.push("" + _cs_); // align : 초기값 (""=center) 
                }

                bodydata.push("" + "" + _cs_); //text

                if(merge != null && direction == 'horizontal') {// vertical로 병합이 될수없는 상황일떄 true이면 에러가 발생.
					bodydata.push(true+ _cs_);
					bodydata.push(""+ _cs_);
				} else {
					//bodydata.push(suppress+ _cs_);
					bodydata.push(false+ _cs_);
					if(suppress == "true") {
						bodydata.push("true"+ _cs_);
					} else {
						bodydata.push(""+ "false" +_cs_);
					}
				}
                bodydata.push("" + hexToR(hexbgcolor) + "." + hexToG(hexbgcolor) + "." + hexToB(hexbgcolor) + _cs_); // bgcolor
                bodydata.push("" + hexToR(hexbgcolor) + "." + hexToG(hexbgcolor) + "." + hexToB(hexbgcolor) + _cs_); // backgroundexpr
                bodydata.push("1" + _cs_); //leftmargin : 1
                bodydata.push("1" + _cs_); //topmargin
                bodydata.push("3" + _cs_); //rightmargin : 1->3
                bodydata.push("1" + _cs_); //bottommargin
                bodydata.push("" + _rs_);//foreground 

                // summary
                if (groups.length > 0) {
                    summdata.push("I" + _cs_); // RowType
                    summdata.push("summ" + _cs_); // type
                    summdata.push("" + i + _cs_); // row
                    summdata.push("" + j + _cs_); // col
                    summdata.push("" + rowspanArray[i][j] + _cs_); // rowspan
                    summdata.push("" + colspanArray[i][j] + _cs_); // colspan
                    summdata.push("" + align + _cs_); // align

                    var footertxt = "";
                    var expression = "";

                    if (columnArray[i][j].footer != null) {
                        footertxt = columnArray[i][j].footer.text;
                    }

                    if (columnArray[i][j].footer != null) {
                        expression = columnArray[i][j].footer.expression;
                    }

                    if (expression != "") {
						Summ = Math.ceil(realgrid.getSummary(columnArray[i][j].name, expression))
						if(isNaN(Summ)){
							Summ ="";
						}
						if(Summ != "" ){//Summ = "" 이 아닐 경우
							Summ = Summ.format();							
						}else{//Summ = "" 일 경우
							Summ = "";
						}
		                
                        summdata.push("" + Summ + _cs_);
                    } else {
						if(footertxt == undefined){
							footertxt ="";
						}
                        summdata.push("" + footertxt + _cs_); // text
                    }
                    summdata.push("" + _cs_);
                    summdata.push("" + _cs_);

                    summdata.push("" + "233.233.233" + _cs_); // bgcolor
                    summdata.push("" + "233.233.233" + _cs_); // backgroundexpr
                    summdata.push("1" + _cs_); // leftmargin
                    summdata.push("1" + _cs_); // topmargin
                    summdata.push("3" + _cs_); // rightmargin
                    summdata.push("1" + _cs_); // bottommargin
                    summdata.push("" + _rs_); // foreground 
                }
            }
        }
    }
}

function SampleColumn(Layout) {//값 콘솔 확인용 함수
	var headerlayout = Layout.items;
	//console.log(headerlayout.length);
	for(var ii = 0; ii < headerlayout.length; ii++) {
		//console.log(headerlayout[ii]);
		if(headerlayout[ii].column) {
			//console.log("Column");
		} else {
			//console.log(headerlayout[ii].header.text);
			//console.log(headerlayout[ii].header.visible);
			SampleColumn(headerlayout[ii]);
		}
	}
	
}


function getData() {
    return this.runtimedata.join("");
    this.runtimedata = [];
}


function showReportPopup(dataProv, gridid, url, file, arg, resid, showlog) {

	if( showlog != undefined && showlog ) {
		//console.log('showReportPopup=====================');
		//console.log('id=====================' + gridid);
		//console.log('url=====================' + url);
		//console.log('file=====================' + file);
		//console.log('arg=====================' + arg);
		//console.log('resid=====================' + resid);
	}
	
	this.realgrid = gridid;

    // 컬럼 정보 생성
    this.designData = [];
    this.runtimedata = [];
    this.dispColumnNames = [];
    
    var designdata = getColumns(gridid.getDisplayColumns(),dataProv);
    //var designdata = getColumns(gridid.getDisplayColumns(), viewer_type);
    var runtimedata = getData();
    var griddata = [];
    griddata.push("SSV:utf-8" + _rs_);
    griddata.push(designdata);
    griddata.push(runtimedata);

	if( showlog != undefined && showlog ) {
		//console.log('designdata========================================');
		//console.log(designdata);
		//console.log('runtimedata========================================');
		//console.log(runtimedata);
	}    
    // 새창으로 호출을 위해서 동적으로 폼을 생성
    var targetName = "__UbiReportPopup"
	var formName = "__UbiPopupForm";
	if (document.forms[formName] == undefined) {
		
		var formObj = document.createElement("form");
		formObj.id = formName;
		formObj.name = formName;
		formObj.method = 'POST';
		formObj.action = url;
		formObj.target = targetName;
		
		var input1 = document.createElement("input");
		input1.type = "hidden";
		input1.name = "file";
		input1.value = file;
		formObj.appendChild(input1);

		var input2 = document.createElement("input");
		input2.type = "hidden";
		input2.name = "arg";
		input2.value = arg;
		formObj.appendChild(input2);

		var input3 = document.createElement("input");
		input3.type = "hidden";
		input3.name = "resid";
		input3.value = resid;
		formObj.appendChild(input3);

		var input4 = document.createElement("input");
		input4.type = "hidden";
		input4.name = "griddata";
		input4.value = griddata.join("");
		formObj.appendChild(input4);

		document.body.appendChild(formObj);
	}
    window.open("", targetName, "toolbar=yes, scrollbars=yes, resizable=yes, top=0, left=0, width=1280, height=800");
    formObj.submit();
	document.body.removeChild(formObj);
}

/*
	Util 함수 입니다. 
*/

// 숫자 타입에서 쓸 수 있도록 format() 함수 추가
Number.prototype.format = function() {
    if (this == 0) return 0;

    var reg = /(^[+-]?\d+)(\d{3})/;
    var n = (this + '');

    while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');
    return n;
};

// 문자열 타입에서 쓸 수 있도록 format() 함수 추가
String.prototype.format = function() {
    var num = parseFloat(this);
    if (isNaN(num)) return "0";
    return num.format();
};

function numFormat(val, len) {

    if (val.indexOf(".") == -1)
        val = val + ".0";

    var parts = val.toString().split(".");

    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    parts[1] = parts[1].substr(0, len);
    parts[1] = parts[1].rpad(len, '0');

    if (len < 1) {
        return parts[0];
    } else {
        return parts.join(".");
    }
}

String.prototype.lpad = function(padLen, padStr) {
    var str = this;
    if (padStr.length > padLen) {
        ////console.log("오류 : 채우고자 하는 문자열이 요청 길이보다 큽니다");
        return str + "";
    }
    while (str.length < padLen)
        str = padStr + str;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
};

/**
 * 우측문자열채우기
 * @params
 *  - padLen : 최대 채우고자 하는 길이
 *  - padStr : 채우고자하는 문자(char)
 */
String.prototype.rpad = function(padLen, padStr) {
    var str = this;
    if (padStr.length > padLen) {
        ////console.log("오류 : 채우고자 하는 문자열이 요청 길이보다 큽니다");
        return str + "";
    }
    while (str.length < padLen)
        str += padStr;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
};

function hexToR(h) {
    return parseInt((cutHex(h)).substring(0, 2), 16)
}

function hexToG(h) {
    return parseInt((cutHex(h)).substring(2, 4), 16)
}

function hexToB(h) {
    return parseInt((cutHex(h)).substring(4, 6), 16)
}

function cutHex(h) {
    return (h.charAt(0) == "#") ? h.substring(3, 10) : h
}


function hexToRgba(hex) {
    var bigint, r, g, b, a;
    //Remove # character
    var re = /^#?/;
    var aRgb = hex.replace(re, '');
    bigint = parseInt(aRgb, 16);

    //If in #FFF format
    if (aRgb.length == 3) {
        r = (bigint >> 4) & 255;
        g = (bigint >> 2) & 255;
        b = bigint & 255;
        return "rgba(" + r + "," + g + "," + b + ",1)";
    }

    //If in #RRGGBB format
    if (aRgb.length >= 6) {
        r = (bigint >> 16) & 255;
        g = (bigint >> 8) & 255;
        b = bigint & 255;
        var rgb = r + "," + g + "," + b;

        //If in #AARRBBGG format
        if (aRgb.length == 8) {
            a = ((bigint >> 24) & 255) / 255;
            return "rgba(" + rgb + "," + a.toFixed(1) + ")";
        }
    }
    return "rgba(" + rgb + ",1)";
}