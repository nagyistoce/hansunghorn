package sod.smarttv;

import sod.common.AdminInformation;


/**
 * 
 * @author MB
 *
 */
public class TVLocationManager {

	/**
	 * 현재 서버의 위치를 지정하고 위치관리 서버에 정보를 저장한다. GPS가 사용 가능하지 않은 경우 handler를 이용해 맵을
	 * 표시하고 수동으로 지정하도록 한다. 위치가 지정되면 beginReply를 호출한다.
	 * 
	 * @param info
	 *            관리자 정보
	 * @param handler
	 *            콜백함수를 구현한 객체
	 * @throws IllegalArgumentException
	 *             매개변수가 한개 이상 null인 경우 발생
	 * @throws IllegalStateException
	 *             두 번 이상 메서드가 호출될때 발생
	 */
	
	public TVLocationManager(){
		/*
		 To
		 
		 Access To LocationMangerServer..
		 
		 */
	}
	
	public void setTVLocation(TVLocation tvLocation)
			throws IllegalArgumentException, IllegalStateException {
		
			AdminInformation infor = new AdminInformation();
			sendTVLocationToServer(infor ,tvLocation);
	}

	/**
	 * 일정 시간마다 현재 서버가 살아있음을 알려주는 패킷을 전송하는 작업 스레드를 생성한다.
	 */
	protected void beginReply() {

	}

	/**
	 * make notice to server which this tv is alive.
	 */
	protected void replyToServer() {

	}

	/**
	 * send current tv location to central-server(which controls service
	 * points).
	 */
	protected void sendTVLocationToServer(AdminInformation infor, TVLocation tvLocation) {

		//To do...
		//send To LocationManager Server..
	}
}
