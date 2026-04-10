// JavaScript Document
// Tab Content
function initTabMenu(tabContainerID) {
	var tabContainer = document.getElementById(tabContainerID);
	if(tabContainer==null || tabContainer=='undefined') return;
	var tabAnchor = tabContainer.getElementsByTagName('a');
	var current;
	var sw_img = !arguments[1];
	var sw_history = arguments[2];

	for(i = 0, cnt = tabAnchor.length; i < cnt; i++) {
		tabAnchor.item(i).onclick = function () {
			this.targetEl = document.getElementById(this.href.split('#')[1]);
			this.imgEl = this.getElementsByTagName('img').item(0);

			if (this == current) {
				return false;
			}

			if (current) {
				current.targetEl.style.display = 'none';
				if (sw_img && current.imgEl) {
					current.imgEl.src = current.imgEl.src.replace('_on.png', '.png');
				} else {
					//current.className = current.className.replace(' on', '');
					current.parentNode.className = current.parentNode.className.replace(' on', '');
				}
			}
			this.targetEl.style.display = 'block';
			if (sw_img && this.imgEl) {
				this.imgEl.src = this.imgEl.src.replace('.png', '_on.png');
			} else {
				//this.className += ' on';
				this.parentNode.className += ' on';
			}
			current = this;

			// 주소창에 해시 출력하기. (history에 추가하기). 2015.02.18.dusthand
			if(sw_history) {
				if(history.pushState) history.pushState('', '', this.hash);
			}

			return false;
		};
	}
	tabAnchor.item(0).click();
}