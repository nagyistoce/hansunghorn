package SOD.SmartPhone;

import java.net.SocketException;

import SOD.SmartTV.TVLocation;

/**
 * 
 * @author MB
 *
 */
abstract public class TVLocationViewer {
	
	/**
	 * get nearby tv list from server.
	 * @param distance
	 * SmartTV���� ��ġ�� ������ �ݰ�. ������ km. (�ִ� 3)
	 * @return
	 * nearby tv list
	 * @throws IllegalArgumentException
	 * distance�� 0 �����̰ų� �ִ� ���� �ѱ涧 �߻� 
	 * @throws SocketException
	 * �����͸� �������µ� �����ϸ� �߻�
	 */
	abstract TVLocation[] getTVLocationList(int distance) throws IllegalArgumentException, SocketException;
	
}
