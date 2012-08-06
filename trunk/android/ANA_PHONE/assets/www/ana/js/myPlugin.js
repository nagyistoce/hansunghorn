var AnAServicePlugin = function(){
	
	this.receiveData = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"ana.phone.AnAServicePlugin","receiveData",[data]);
	};
	this.sendData = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"ana.phone.AnAServicePlugin","sendData",[data]);
	};
	this.TvSerch = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"ana.phone.AnAServicePlugin","TvSerch",[data]);
	};
}; 
PhoneGap.addConstructor(function(){
	PhoneGap.addPlugin('AnAServicePlugin',new AnAServicePlugin());
});