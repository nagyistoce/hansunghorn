var contextMenus = [ 'all', 'popular', 'recommandation', 'setting' ];
var hasHardwareAcceleration = false;

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
document.addEventListener('DOMContentLoaded',function(){
	hasHardwareAcceleration = !!(navigator.userAgent.match("iPhone"));
	function emptyClicker(){};
	contextMenus.forEach(function (MenuId){
		var MenuElement = document.getElementById(MenuId);
	if(!hasHardwareAcceleration){
		MenuElement.style.webkitTransform = 'translate(0px,0px)';
		if(MenuId != 'all'){
			MenuElement.style.display ='none';
		}
	}
	MenuElement.addEventListener('click',emptyClicker);
});
});