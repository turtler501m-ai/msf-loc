class NmcpCommonComponent {
	constructor () {

	};

	set respValue(value){
		if(!value){
			return;
		}
		this._respValue = value;
	}

	get respValue(){
		return this._respValue;
	}

	/**init */
	init(){
		KTM.initialize();
	}

	/** Common method */
	toHTML(sourceString) {
		var div = document.createElement("div");
		div.innerHTML = sourceString.trim();
		return div.firstChild;
	}

	/** AJAX */
	ajaxGet(url,data,type,callback){
		$.ajax({
			url: url,
			type: 'GET',
			cache : false,
			dataType: type,
			data: data,
			async: false,
			success: function(resp) {
				if( typeof callback == 'function' ) {
					callback(resp);
					return ;
				}else if(typeof callback == 'string'){
					$(callback).html(resp);
					return;
				}else{
					this._respValue = resp;
				}
				return resp;
			}
		});
	}

	ajaxPost(url,data,type,callback){
		$.ajax({
			url: url,
			type: 'POST',
			dataType: type,
			data: data,
			async: false,
			success: function(resp) {
				if( typeof callback == 'function' ) {
					callback(resp);
					return ;
				}else if(typeof callback == 'string'){
					$(callback).html(resp);
					return;
				}else{
					this._respValue = resp;
				}
				return resp;
			}
		});
	}



	/** TAB method*/
	addTab(targetTab,htmlTab,targetBody,htmlBody,callback){

		$(targetBody).append(htmlBody);
		$(targetTab).append(htmlTab);
		if(typeof targetBody == 'object'){
			var el = targetBody
		}else{
			var el = $(targetBody)[0];
		}
		var instance =KTM.Tab.getInstance(el);
		instance.update();
		var tabId = $(htmlBody).attr('id');
		if( typeof callback == 'function' ) {
			callback('#'+tabId);
		}
	}

	addSingleTab(tabId,buttonClass,html,callback){
		$(tabId).append(html);
		$(buttonClass).off().on('click', function() {});
		$(buttonClass).on("click", function(){
			$(buttonClass).removeClass('is-active');
			$(this).addClass('is-active');
			$(buttonClass).attr('aria-hidden',"false");
			$(this).attr('aria-hidden',"true");

		});
		if( typeof callback == 'function' ) {
			callback('#'+tabId);
		}
	}



	/** Accordion method */
	addAccordion(target,html,callback){
		if(typeof target == 'object'){
			var accContainer = target;
		}else{
			var accContainer = document.querySelector(target);
		}

		var accEl = accContainer.closest('.c-accordion');
		var instance = KTM.Accordion.getInstance(accEl);
		accContainer.appendChild(this.toHTML(html));
		instance.update();
		if( typeof callback == 'function' ) {
			callback(target);
		}
	}

	/** Accordion2 method */

	addAccordion2(target){
		var el = document.querySelector(target);
		var instance = KTM.Accordion.getInstance(el) ? KTM.Accordion.getInstance(el) : new KTM.Accordion(el);
		instance.update();
	}

	/**alert */
	alert(msg, callback, eventType = 'closed'){
		if( typeof callback == 'function' ) {
			new KTM.Alert(msg, {
				[eventType]: function() {
					callback();
				},
			});
		}else{
			new KTM.Alert(msg);
		}
	}

	/** dialog */

	closePopup(target){
		//var el = document.querySelector(target);
		//var instance = KTM.Dialog.getInstance(el);
		//instance.close();
		//new KTM.Dialog(document.querySelector("#modalArs")).close();

		var el = document.querySelector(target);
		var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
		modal.close();
	}

	openPopup(target){
		var el = document.querySelector(target);
		var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
		modal.open();
	}

	confirmReverse(msg,callback){
		if( typeof callback == 'function' ) {
			new KTM.Confirm(msg, {
				open: function(){
					$(".c-modal.is-active").find("button").remove();
					html = '';
					html += '<button class="c-button c-button--primary c-button--full" data-dialog-close>취소</button>';
					html += '<button class="c-button c-button--primary c-button--full" data-dialog-confirm>확인</button>';

					$(".c-modal.is-active").find(".c-button-wrap").html(html);
					init();
				},
				closed: function() {
					callback();
				},
			})

		}
	}

	/** */
	//data-ignore="true"

	//1)  data-dialog-trigger 버튼을 클릭하면 팝업이 오픈됩니다.
	//2)  팝업에  data-dialog-close

	// 팝업 기능 추가 예정 : open, opened, close, closed

}; 

