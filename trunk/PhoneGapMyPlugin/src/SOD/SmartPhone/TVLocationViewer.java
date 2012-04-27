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
	 * SmartTV들의 위치를 가져올 반경. 단위는 km. (최대 3)
	 * @return
	 * nearby tv list
	 * @throws IllegalArgumentException
	 * distance가 0 이하이거나 최대 값을 넘길때 발생 
	 * @throws SocketException
	 * 데이터를 가져오는데 실패하면 발생
	 */
	abstract TVLocation[] getTVLocationList(int distance) throws IllegalArgumentException, SocketException;
	
}
