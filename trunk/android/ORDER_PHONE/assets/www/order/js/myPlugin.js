var PhoneServicePlugin = function(){
	
	this.receiveData = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"order.phone.PhoneServicePlugin","receiveData",[data]);
	};
	this.sendData = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"order.phone.PhoneServicePlugin","sendData",[data]);
	};
	this.categorysaveData = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"order.phone.PhoneServicePlugin","CategoryData",[data]);
	};
	this.receiveCategory = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"order.phone.PhoneServicePlugin","receiveCategory",[data]);
	};
	this.CancleOrder = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"order.phone.PhoneServicePlugin","Remove",[data]);
	};
	this.Review = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"order.phone.PhoneServicePlugin","Review",[data]);
	};
	this.OrderAdd = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"order.phone.PhoneServicePlugin","OrderAdd",[data]);
	};

	this.receiveOptionMenuList = function(onSuccess,onError,data){
		PhoneGap.exec(onSuccess,onError,"order.phone.PhoneServicePlugin","OptionMenu",[data]);
	};

}; 
PhoneGap.addConstructor(function(){
	PhoneGap.addPlugin('PhoneServicePlugin',new PhoneServicePlugin());
});