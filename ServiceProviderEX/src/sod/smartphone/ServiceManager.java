package sod.smartphone;

import java.io.IOException;
import java.net.SocketException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

import sod.common.Packet;
import sod.common.Storage;
import sod.common.StorageFile;


/**
 * 클라이언트에서 서비스파일의 경로는 /sod/SERVICE_NAME/service
 * @author MB
 *
 */
public class ServiceManager {
	
	ServiceReceiveHandler mHandler;
	
	static final int TEXT_TYPE = 0;
	static final int IMAGE_TYPE = 1;
	

	/**
	 * 서비스가 설치되어 있는지 확인
	 * @param serviceName
	 * 서비스 이름
	 * @return
	 * 서비스가 설치되어 있으면 true 아니면 false
	 * @throws IllegalArgumentException
	 * 매게변수가 null일 때 IllegalArgumentException이 발생한다.
	 */
	public boolean findServiceByName(String serviceName) throws IllegalArgumentException{
		
		if(Storage.checkIsStorageExists(serviceName)){
			if(Storage.checkIsStorageExists(serviceName + "/service"))
				return true;
		}
		
		return false;	
	}

	/**
	 * 해당 이름의 서비스 시작
	 * @param serviceName
	 * 서비스 이름
	 * @throws IllegalArgumentException
	 * 매개변수가 null이거나 해당 이름의 서비스가 설치되지 않은 경우 발생
	 * @throws IllegalStateException
	 * 두번 이상 호출될 때 발생.
	 */
	public void startService(String serviceName) throws IllegalArgumentException, IllegalStateException{
		
	}

	/**
	 * 설치되어 있는 서비스 목록 반환
	 * @return
	 * 서비스 목록
	 */
	public String[] getServiceList() {

		Storage storage = null;

		try {
			if (Storage.checkIsStorageExists(""))
				storage = Storage.getStorage("");
			else
				storage = Storage.createStorage("");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return storage.getFileList("*");
	}

	/**
	 * 특정 서비스를 제거
	 * 
	 * @param serviceName
	 *            서비스 이름
	 * @throws IllegalArgumentException
	 *             매개변수가 null이거나 해당 serviceName에 일치하는 서비스 데이터가 없을 경우 발생.
	 */
	public void removeService(String serviceName) throws IllegalArgumentException{
		Storage storage = null;

		try {
			if (Storage.checkIsStorageExists(serviceName)){
				Storage.destroy(serviceName);
			}
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	/**
	 * 서비스 다운로드시 호출할 콜백 함수 등록
	 * @param handler
	 * 콜백함수를 구현한 객체
	 * @throws IllegalArgumentException
	 * 매개변수가 null인 경우 발생.
	 */
	public void setSeriveReceiveHandler(ServiceReceiveHandler handler) throws IllegalArgumentException{
		mHandler = handler;
	}
	
	public void installService(Packet packet){
		String serviceName = (String)packet.pop(); // 1. 패킷에서 첫번째로 나오는 것은 serviceName (String)
		String serviceFilePath = serviceName + "/service";
		
		String relativePath = (String) packet.pop();// 2. 두번째로 나오는 것은 상대 path
													// (String)
		String[] relativePathList = relativePath.split("/");

		try {
			if (!Storage.checkIsStorageExists(serviceName))
				Storage.createStorage(serviceName);

			if (!Storage.checkIsStorageExists(serviceFilePath))
				Storage.createStorage(serviceFilePath);

			Storage serviceStorage = null;
			if (relativePath.equals("/")) {
				serviceStorage = Storage.getStorage(serviceFilePath);
			} 
			else {// 상대패스가 있으면 serviceFilePath를 업데이트 한다.

				for (int i = 0; i < relativePathList.length; i++) {
					serviceFilePath = serviceFilePath + "/" + relativePathList[i];

					if (!Storage.checkIsStorageExists(serviceFilePath))
						serviceStorage = Storage.createStorage(serviceFilePath);
					else
						serviceStorage = Storage.getStorage(serviceFilePath);
				}
			}

			String fileName = (String)packet.pop();//3. 세번째로 나오는 것은 fileName
			Integer fileType = (Integer)packet.pop();//4. 네번째로 나오는 것은 파일타입
			
			
			
			
			if(fileType == ServiceManager.TEXT_TYPE){
				StorageFile serviceFile = serviceStorage.createFile(fileName); // 파일 생성
				byte [] buf = (byte [])packet.pop(); //5. 다섯번째로 나오는 것은 데이터
				serviceFile.write(buf);
				serviceFile.close();
			}
			else{//4.1 이미지 일 때
				//to Do
				Integer width = (Integer)packet.pop(); //4.1 가로 길이 가져오고
				Integer height = (Integer)packet.pop(); //4.2 세로 길이 가져오고
				
				byte [] buf = (byte [])packet.pop(); //5. 다섯번째로 나오는 것은 데이터
				Bitmap img = BitmapFactory.decodeByteArray(buf, 0, buf.length);
				Bitmap serviceImg = Bitmap.createBitmap(img, 0, 0, width, height);
				
				StorageFile serviceFile = serviceStorage.createFile(fileName);
				serviceFile.writeImage(serviceImg);
			}
			
			
			
				
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
	}

	/**
	 * 서비스가 없을시 다운로드 요청
	 * 서비스 데이터를 요청하는 패킷 만들어서 전송
	 * @param acc
	 * 데이터를 다운로드하기 위해 사용되는 서버에 연결된 AccessManager 객체
	 * @throws IllegalArgumentException
	 * 매개변수가 null이거나 send를 호출 할 수 없는 상태인 경우 발생.
	 * @throws SocketException
	 * 넘겨준 AccessManager 객체 에서 예외가 발생시 전달
	 */
	public void requestService(AccessManager acc) throws IllegalArgumentException, SocketException{
		Packet packet = new Packet();
		
		packet.signiture = Packet.RESPONSE_SERVICE_DATA;
		
		acc.send(packet);
		
	}
	
	protected boolean isText(String fileName){
		if( (fileName.indexOf(".js") != -1 ) && (fileName.indexOf(".html") != -1) && (fileName.indexOf(".css") != -1)   )
			return false;
		else
			return true;
	}
	
}
