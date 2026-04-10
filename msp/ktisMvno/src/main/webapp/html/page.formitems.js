var _FORMITEMS_ = {

form1 : [
	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0}

	//조회기간
	,{type:"block", list:[
		{type:"settings"}
		,{type:"select", width:100, label:"조회기간", options:[{value:"1", text:"입금일자1", selected:true},{value:"2", text:"입금일자2"}]}
		,{type:"newcolumn"}
		,{type:"calendar", width:100}
		,{type:"newcolumn"}
		,{type:"calendar", label:"~", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
	]},

	//조직ID
	,{type:"block", list:[
		{type:"input", label:"조직ID", width:100}
		,{type:"newcolumn"}
		,{type:"button", value:"검색"}
		,{type:"newcolumn"}
		,{type:"input", width:100, value:"readonly", readonly: true}
	]},

	//입금일련번호, 주문번호
	,{type:"block", list:[
		{type:"input", width:100, label:"입금일련번호"}
		,{type:"newcolumn"}
		,{type:"input", width:100, label:"주문번호", labelWidth:60, offsetLeft:60, value:"readonly"}
	]},

	//매출항목
	,{type:"newcolumn", offset:80}
	,{type:"select", width:100, label:"매출항목", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}

	//상계상태
	,{type:"select", width:100, label:"상계상태", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}

	//조회 버튼
	,{type:"newcolumn", offset:50}
	,{type:"button", value:"조회", className:"btn_search2"}/* btn_search(열갯수1~4) */
],

form2 : [
	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}

	//매출항목
	,{type:"newcolumn"}
	,{type:"select", width:100, label:"매출항목", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}

	//상계상태
	,{type:"select", width:100, label:"상계상태", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}

	//매출항목
	,{type:"newcolumn", offset:40}
	,{type:"select", width:100, label:"매출항목", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}

	//상계상태
	,{type:"select", width:100, label:"상계상태", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}

],

form3 : [
	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}

	//조직ID
	,{type:"block", list:[
		{type:"input", label:"조직ID", width:100}
		,{type:"newcolumn"}
		,{type:"button", value:"검색"}
		,{type:"newcolumn"}
		,{type:"input", width:100}
	]},

	//결산년월
	,{type:"newcolumn", offset:30}
	,{type:"calendar", label:"결산년월", width:100}

	//매출분류
	,{type:"newcolumn", offset:30}
	,{type:"select", width:100, label:"매출분류", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}

	//조회 버튼
	,{type:"newcolumn", offset:30}
	,{type:"button", value:"조회", className:"btn_search1"}/* btn_search(열갯수1~4) */
],

form4 : [
	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}

	//사번
	,{type:"input", label:"사번", width:100}

	//성별
	,{type: "label", label: "성별", list:[
		{type:"settings", position:"label-right", labelWidth:20}
		,{type: "radio", name: "", value: "", label: "남", checked: true}
		,{type:"newcolumn"}
		,{type: "radio", name: "", value: "", label: "여"}
	]},

	//입사일자
	,{type:"calendar", label:"입사일자", width:100}

	//직급
	,{type:"select", label:"직급", width:100, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}


	//이름
	,{type:"newcolumn", offset:60}
	,{type:"input", label:"이름", width:100}

	//나이
	,{type:"input", label:"나이", width:100}

	//부서
	,{type:"block", list:[
		{type:"input", label:"부서", width:100}
		,{type:"newcolumn"}
		,{type:"button", value:"찾기"}
		,{type:"newcolumn"}
		,{type:"input", width:100}
	]},

	//취미
	,{type: "label", label: "취미", list:[
		{type:"settings", position:"label-right", labelWidth:30}
		,{type: "checkbox", label: "등산", checked: true}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "낚시"}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "바둑"}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "영화감상", labelWidth:60}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "게임1"}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "게임2"}

	]}
],

form5 : [
	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}

	//조직ID
	,{type:"block", list:[
		{type:"input", label:"조직ID", width:100}
		,{type:"newcolumn"}
		,{type:"button", value:"검색"}
		,{type:"newcolumn"}
		,{type:"input", width:100}
	]},

	//결산년월
	,{type:"newcolumn", offset:30}
	,{type:"calendar", label:"결산년월", width:100}

	//매출분류
	,{type:"newcolumn", offset:30}
	,{type:"select", width:100, label:"매출분류", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}

	//조회 버튼
	,{type:"newcolumn", offset:30}
	,{type:"button", value:"조회", className:"btn_search1"}/* btn_search(열갯수1~4) */
],

form6 : [
	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}

	//사번
	,{type:"input", label:"사번", width:100}

	//성별
	,{type: "label", label: "성별", list:[
		{type:"settings", position:"label-right", labelWidth:20}
		,{type: "radio", name: "", value: "", label: "남", checked: true}
		,{type:"newcolumn"}
		,{type: "radio", name: "", value: "", label: "여"}
	]},

	//입사일자
	,{type:"calendar", label:"입사일자", width:100}

	//직급
	,{type:"select", label:"직급", width:100, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}


	//이름
	,{type:"newcolumn", offset:60}
	,{type:"input", label:"이름", width:100}

	//나이
	,{type:"input", label:"나이", width:100}

	//부서
	,{type:"block", list:[
		{type:"input", label:"부서", width:100}
		,{type:"newcolumn"}
		,{type:"button", value:"찾기"}
		,{type:"newcolumn"}
		,{type:"input", width:100}
	]},

	//취미
	,{type: "label", label: "취미", list:[
		{type:"settings", position:"label-right", labelWidth:30}
		,{type: "checkbox", label: "등산", checked: true}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "낚시"}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "바둑"}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "영화감상", labelWidth:60}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "게임1"}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "게임2"}
	]}
],

form7 : [
	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}

	//조직ID
	,{type:"block", list:[
		{type:"input", label:"조직ID", width:100}
		,{type:"newcolumn"}
		,{type:"button", value:"검색"}
		,{type:"newcolumn"}
		,{type:"input", width:100}
	]},

	//결산년월
	,{type:"newcolumn", offset:30}
	,{type:"calendar", label:"결산년월", width:100}

	//매출분류
	,{type:"newcolumn", offset:30}
	,{type:"select", width:100, label:"매출분류", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}

	//조회 버튼
	,{type:"newcolumn", offset:30}
	,{type:"button", value:"조회", className:"btn_search1"}/* btn_search(열갯수1~4) */
],

form8 : [
	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}

	//사번
	,{type:"input", label:"사번", width:100}

	//성별
	,{type: "label", label: "성별", list:[
		{type:"settings", position:"label-right", labelWidth:20}
		,{type: "radio", name: "", value: "", label: "남", checked: true}
		,{type:"newcolumn"}
		,{type: "radio", name: "", value: "", label: "여"}
	]},


	//입사일자
	,{type:"calendar", label:"입사일자", width:100}

	//직급
	,{type:"select", label:"직급", width:100, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}


	//이름
	,{type:"newcolumn", offset:60}
	,{type:"input", label:"이름", width:100}

	//나이
	,{type:"input", label:"나이", width:100}

	//부서
	,{type:"block", list:[
		{type:"input", label:"부서", width:100}
		,{type:"newcolumn"}
		,{type:"button", value:"찾기"}
		,{type:"newcolumn"}
		,{type:"input", width:100}
	]},

	//취미
	,{type:"label", label: "취미<span class='req'>*</span>", inputWidth:600, list:[{type:"settings", position:"label-right", labelWidth:30}
		,{type: "checkbox", label: "등산", checked: true}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "낚시"}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "바둑"}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "영화감상", labelWidth:60}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "게임1"}
		,{type:"newcolumn"}
		,{type: "checkbox", label: "게임2"}
	]}

	,{type:"newcolumn"}
	,{type: "fieldset", label: "조직부가정보", inputWidth:910, list:[{type:"settings", position:"label-right", labelWidth:90}
		,{type: "checkbox", name: "", value: 1, label: "단말주문여부"}
		,{type:"newcolumn"}
		,{type: "checkbox", name: "", value: 2, label: "단말재고여부"}
		,{type:"newcolumn"}
		,{type: "checkbox", name: "", value: 3, label: "세금계산서발행여부", labelWidth:120}
		,{type:"newcolumn"}
		,{type: "checkbox", name: "", value: 4, label: "수수료지급여부"}
	]}

	,{type:"newcolumn"}
	,{type: "fieldset", label: "선순위내역", inputWidth:910, list:[{type:"settings", labelWidth:90}
		,{type:"input", label: "선순위자명1"}
		,{type:"newcolumn"}
		,{type:"input", label: "선순위자명2", offsetLeft:10}
		,{type:"newcolumn"}
		,{type:"input", label: "선순위자명3", offsetLeft:10}
		
		,{type:"newcolumn"}
		,{type:"block", blockOffset:0, list:[
			{type:"input", label: "선순위자금액1"}
			,{type:"newcolumn"}
			,{type:"input", label: "선순위자금액2", offsetLeft:10}
			,{type:"newcolumn"}
			,{type:"input", label: "선순위자금액3", offsetLeft:10}
		]},
	]}
],

form9 : [
	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}

	//매출/결산
	,{type: "fieldset", label: "매출/결산", inputWidth:270, list:[{type:"settings", labelWidth:120}

		,{type:"block", list:[{type:"settings", position:"label-left"}
			,{type: "checkbox", name: "", value: "", label: "KTIS항목여부", required: true}
			,{type:"newcolumn"}
			,{type: "label", label: "예", labelWidth:20}
		]}
		,{type:"block", list:[{type:"settings", position:"label-left"}
			,{type: "checkbox", name: "", value: "", label: "매출항목사용여부", required: true}
			,{type:"newcolumn"}
			,{type: "label", label: "예", labelWidth:20}
		]}

		,{type:"select", label:"매출유형코드", width:100, required: true, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}

		,{type:"block", list:[{type:"settings", position:"label-left"}
			,{type: "checkbox", name: "", value: "", label: "결산처리여부"}
			,{type:"newcolumn"}
			,{type: "label", label: "예", labelWidth:20}
		]}

		,{type:"select", label:"결산항목코드", width:100, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
	]}

	//입금/상계
	,{type:"newcolumn"}
	,{type: "fieldset", label: "입금/상계", inputWidth:270, offsetLeft:20, list:[{type:"settings", labelWidth:120, inputWidth:100}
		,{type:"select", label:"입금처리기준코드", required: true, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
		,{type:"input", label:"입금처리우선순위", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
		,{type:"input", label:"입금처리기준일수", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
		,{type:"select", label:"상계처리기준코드", required: true, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
		,{type:"input", label:"상계처리우선순위", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
	]}

	//기타
	,{type:"newcolumn"}
	,{type: "fieldset", label: "입금/상계", inputWidth:270, offsetLeft:20, list:[{type:"settings", labelWidth:120, position:"label-right"}

		,{type:"block", list:[{type:"settings", position:"label-left"}
			,{type: "checkbox", name: "", value: "", label: "여신적용여부", required: true}
			,{type:"newcolumn"}
			,{type: "label", label: "예", labelWidth:20}
		]}
		,{type:"block", list:[{type:"settings", position:"label-left"}
				,{type: "checkbox", name: "", value: "", label: "대리점수납여부", required: true}
				,{type:"newcolumn"}
				,{type: "label", label: "예", labelWidth:20}
		]}
		,{type:"block", list:[{type:"settings", position:"label-left"}
				,{type: "checkbox", name: "", value: "", label: "조정가능여부", required: true}
				,{type:"newcolumn"}
				,{type: "label", label: "예", labelWidth:20}
		]}
		,{type:"block", list:[{type:"settings", position:"label-left"}
				,{type: "checkbox", name: "", value: "", label: "부가세부과여부"}
				,{type:"newcolumn"}
				,{type: "label", label: "예", labelWidth:20}
		]}
		,{type:"block", list:[{type:"settings", position:"label-left"}
				,{type: "checkbox", name: "", value: "", label: "수수료대상여부"}
				,{type:"newcolumn"}
				,{type: "label", label: "예", labelWidth:20}
		]}

	]}

	//할인할증율정보
	,{type:"newcolumn"}
	,{type: "fieldset", label: "할인할증율정보", inputWidth:910, list:[{type:"settings", labelWidth:100}

		,{type:"block", list:[
			{type:"input", label:"조직ID", width:100}
			,{type:"newcolumn"}
			,{type:"button", value:"검색"}
			,{type:"newcolumn"}
			,{type:"input", width:100}
		]},

		,{type:"block", list:[
			,{type:"calendar", label:"적용기간", width:100}
			,{type:"newcolumn"}
			,{type:"calendar", label:"~", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
		]},

		,{type:"newcolumn", offset:20}
		,{type:"settings", position:"label-left", labelWidth:60}
		,{type:"select", label:"구분", width:100, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
		,{type:"input", label:"비율", width:100}

		,{type:"newcolumn"}
		,{type: "input", label: "비고", inputWidth:800, rows: 6, value: "입력란"},
	]}
]

};
