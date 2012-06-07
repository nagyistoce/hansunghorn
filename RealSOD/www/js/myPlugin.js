var receiver = {
	receiveData : function(onSuccess,onError,data){
		return Cordova.exec(onSuccess,onError,"myPlugin","receiveData",data);
	}
};

var sender = {
    sendData : function(onSuccess,onError,data){
		return Cordova.exec(onSuccess,onError,"myPlugin","sendData",data);
	}    
};