var contextMenus = [ 'all', 'popular', 'recommandation', 'setting' ];
var hasHardwareAcceleration = false;
$(document).bind("mobileinit", function() {
	// 네임스페이스를 설정하는 부분입니다.
	// 기본값은 "" 으로 변경시 data-role 와 같은 부분응ㄹ data-네임스페이스-role 와 같이 사용해야 합니다.
	$.mobile.ns = "";

	// 서브페이지 URL 키 값 설정
	// 같은 html 파일 내에 있는 서브페이지로 이동하기 위한 키 값을 어떤것으로 설정할 것인지 선택하는 부분입니다.
	// sample.html&ui-page=subpage
	$.mobile.subPageUrlKey = "ui-page";

	$.mobile.nonHistorySelectors = "dialog";

	// 활성화된 페이지 Class 설정
	$.mobile.activePageClass = "ui-page-active";

	// 활성화된 버튼 Class 설정
	$.mobile.activeBtnClass = "ui-page-active";

	// ajax 기능 사용 여부
	$.mobile.ajaxEnabled = true;

	$.mobile.hashListeningEnabled = true;

	// 기본 화면 전환 효과
	$.mobile.defaultPageTransition = "slide";

	// 기본 다이얼로그 창 전환 효과
	$.mobile.defaultDialogTransition = "pop";

	$.mobile.minScrollBack = "150";

	// 외부 페이지 로딩시 보여지는 메세지
	$.mobile.loadingMessage = "loading";

	// 외부 페이지 에러시 보여지는 메세지
	$.mobile.pageLoadErrorMessage = "Error Loading Page";

	$.mobile.gradeA
});

document.addEventListener('click', function(evt) {
	if (evt.target.getAttribute('class') == 'OrderContextMenu') {
		var beforeMenuElement = evt.target;
		var beforeMenu = beforeMenuElement.id;
		var afterMenu, afterMenuElement;

		contextMenus.forEach(function(MenuId, i) {
			if (MenuId == beforeMenu) {
				if ((i + 1) < contextMenus.length) {
					afterMenu = contextMenus[i + 1];
				} else {
					afterMenu = contextMenus[0];
				}
				afterMenuElement = document.getElementById(afterMenu);
			}
		});
		if (hasHardwareAcceleration) {
			afterMenuElement.style.webkitTransform = ' translate(0px,0px)';
			beforeMenuElement.style.webkitTransform = 'translate(-320,0px)';
		} else {
			afterMenuElement.style.display = 'block';
			beforeMenuElement.style.display = 'none';
		}

	}

});
document.addEventListener('DOMContentLoaded', function() {
	hasHardwareAcceleration = !!(navigator.userAgent.match("iPhone"));
	function emptyClicker() {
	}
	;
	contextMenus.forEach(function(MenuId) {
		var MenuElement = document.getElementById(MenuId);
		if (!hasHardwareAcceleration) {
			MenuElement.style.webkitTransform = 'translate(0px,0px)';
			if (MenuId != 'all') {
				MenuElement.style.display = 'none';
			}
		}
		MenuElement.addEventListener('click', emptyClicker);
	});
});