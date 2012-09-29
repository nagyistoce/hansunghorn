package sod.smarttv;

import sod.common.AdminInformation;
import sod.common.TVLocation;


/**
 * 
 * @author MB
 *
 */
public class TVLocationManager {

	/**
	 * ���� ������ ��ġ�� �����ϰ� ��ġ���� ������ ������ �����Ѵ�. GPS�� ��� �������� ���� ��� handler�� �̿��� ����
	 * ǥ���ϰ� �������� �����ϵ��� �Ѵ�. ��ġ�� �����Ǹ� beginReply�� ȣ���Ѵ�.
	 * 
	 * @param info
	 *            ������ ����
	 * @param handler
	 *            �ݹ��Լ��� ������ ��ü
	 * @throws IllegalArgumentException
	 *             �Ű������� �Ѱ� �̻� null�� ��� �߻�
	 * @throws IllegalStateException
	 *             �� �� �̻� �޼��尡 ȣ��ɶ� �߻�
	 */
	
	public TVLocationManager(){
		/*
		 To
		 
		 Access To LocationMangerServer..
		 
		 */
	}
	
	/**
	 * TV�� ��ġ�� ����Ѵ�. 
	 * @param tvLocation TV��ġ ������ ���� ��ü
	 * @throws IllegalArgumentException null�� ���ڷ� �޾��� �� �߻��Ѵ�.
	 * @throws IllegalStateException ��ġ�������� ��ſ� �̻��� �������� �߻��Ѵ�.
	 */
	public void setTVLocation(TVLocation tvLocation)
			throws IllegalArgumentException, IllegalStateException {
		
			AdminInformation infor = new AdminInformation();
			sendTVLocationToServer(infor ,tvLocation);
	}

	/**
	 * ���� �ð����� ���� ������ ��������� �˷��ִ� ��Ŷ�� �����ϴ� �۾� �����带 �����Ѵ�.
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
