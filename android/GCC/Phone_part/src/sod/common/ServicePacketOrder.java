package sod.common;

public class ServicePacketOrder {

	int order;
	
	ServicePacketOrder(){
		order = 0;
	}
	
	void initServicePacketOrder(){
		order = 0;
	}
	
	void increaseOrder(){
		order++;
	}
	
	int getOrder(){
		return order;
	}
	
	boolean isRetransmission(int number){
		
		boolean returnData;
		if(order == number)
			returnData = true;
		else
			returnData = false;
		
		return returnData;
	}
}
