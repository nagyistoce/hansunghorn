var myPlugin = function(){
	
	this.receiveData = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"com.phonegap.plugin.myPlugin","receiveData",[data]);
	};
	this.sendData = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"com.phonegap.plugin.myPlugin","sendData",[data]);
	};
	this.TvSerch = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"com.phonegap.plugin.myPlugin","TvSerch",[data]);
	};
};
PhoneGap.addConstructor(function(){
	PhoneGap.addPlugin('myplugin',new myPlugin());
});