if ( !_loadedModule ) {
	var _isIE=(navigator.userAgent.match(/Gecko/))?false:true;
	function inc(pa) {document.write("<script type=\"text/javascript\" src=\"" + pa + "\"></script>"); 	};
	function inc_css(pa) { document.write("<link type=\"text/css\"  rel=\"stylesheet\" href=\"" + pa + "\" />"); };
	var Iacts = {};
	// load prototype
	inc("/includejs/prototype.1.6.js");
	// load common
	inc("/includejs/common.js");
	// Busan City Hall loading modules
	inc("/includejs/busan.js");
	//xtree
	inc("/includejs/xtree/xtree.js");
	inc_css("/includejs/xtree/xtree.css");
	//suggest
	inc("/includejs/suggest.js");
	// create by designers
	inc("/share/js/b_all.js");
	// favorite
	inc("/includejs/favorite.js");
	// ??
	inc("/includejs/iacts.js");
	inc("/includejs/convert.js");
	// all.js와 중복됩니다.
	//inc("/includejs/cookie.js");
	
	function doOver_line(el) {
		for(var i=0 ; i < el.childNodes.length; i++ ){
			if ( el.childNodes[i].style )
				el.childNodes[i].style.backgroundColor="#F6F6F6";
		}
	}
	function doOut_line(el) {
		for(var i=0 ; i < el.childNodes.length; i++ ){
			if ( el.childNodes[i].style )
				el.childNodes[i].style.backgroundColor="#FFFFFF";
		}
	}
	
	
	if ( parent.resizeAdminFrame ) {
		window.onload = function(e) {
			//var _tk_imsi_height = Math.max( document.body.offsetHeight, document.body.scrollHeight );
			// 에디터와 ,TEXT사이의 키기가 다르네요 그래서 그냥 계속 실행하돌고 처리.
			setInterval("parent.resizeAdminFrame(Math.max( document.body.offsetHeight, document.body.scrollHeight ))", 300);
		}
			
		
	}

}
var _loadedModule = true;
