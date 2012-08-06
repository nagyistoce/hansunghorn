package sod.smarttv;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import sod.common.Constants;
import sod.common.Packet;
import sod.common.Storage;
import sod.common.StorageFile;

/**
 * 서버에서 서비스파일의 경로는 /sod/lib
 * @author MB
 *
 */
public class ServiceProvider {
	
	String serviceName; // /ana
	String serviceFilePath ; // /ana/lib
	
	static final int TEXT_TYPE = 0;
	static final int IMAGE_TYPE = 1;
	
	
	public ServiceProvider(String serviceName){
		this.serviceName = serviceName;
		this.serviceFilePath = "/lib";
		 
	}
	
	/**
	 * set files path where content files are stored.
	 * @param newpath
	 * 변경할 디렉토리 경로
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 */
	void setPath(String newpath) throws IllegalArgumentException{
		serviceFilePath = newpath;
	}
	
	/**
	 * 서비스 데이터를 담고있는 패킷을 반환
	 * 최초 호출시 createServicePacket()이 내부적으로 호출된다.
	 * @return
	 * 서비스 데이터를 담은 패킷
	 */
    public ArrayList<Packet> getServicePacket(){
		Storage storage;
		StorageFile [] serviceFiles = null;
		ArrayList<Packet> serviceFilePackets = new ArrayList<Packet>();
		
		try {
			
			if (Storage.checkIsStorageExists(serviceFilePath))
				storage = Storage.getStorage(serviceFilePath);
			else
				storage = Storage.createStorage(serviceFilePath);
			
			String [] fileList = storage.getFileList("*");
			
			
			for(int i = 0 ; i<fileList.length ; i++ ){
				if( isDirectory(fileList[i])){//디렉토리일때는 하위 파일의 목록을 다시 가져와서 패킷 생성
					getServicePacketSubDirectory(serviceFilePath, fileList[i] ,fileList[i], serviceFilePackets);
				}
				else{//파일일때...패킷생성
					
					StorageFile openServiceFile = storage.openFile(fileList[i], Storage.READ);
					
					serviceFilePackets.add( createServicePacket(openServiceFile, "/"));
				}
				
			}
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			Constants.logger.log("(debug:server)IllegalArgumentException\n");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Constants.logger.log("(debug:server)IOException .\n");
			return null;
		}
		
		Constants.logger.log("(debug:server)getServicePacket() Success");
		return serviceFilePackets;
	}

	protected void getServicePacketSubDirectory(String serviceFilePath,
			String subPath, String addPath, ArrayList<Packet> serviceFilePackets) {

		Storage subStorage;
		serviceFilePath = serviceFilePath + "/" + addPath;
		try {

			subStorage = Storage.getStorage(serviceFilePath);

			String[] subFileList = subStorage.getFileList();

			for (int j = 0; j < subFileList.length; j++) {

				if (isDirectory(subFileList[j])) {
					getServicePacketSubDirectory(serviceFilePath, subPath+"/"+subFileList[j] , subFileList[j], serviceFilePackets);
				} else {
					StorageFile openServiceFile = subStorage.openFile(subFileList[j], Storage.READ);
					serviceFilePackets.add(createServicePacket(openServiceFile, subPath));
				}
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
	 * create packet which contains every content files need to service.
	 * @return
	 * 서비스 데이터를 담은 패킷
	 * @throws IllegalStateException
	 * 내부에 지정된 경로가 없거나 잘못되었을 경우 발생
	 */
	protected Packet createServicePacket(StorageFile openServiceFile, String path) throws IllegalStateException{
	//	Packet packet = new packet
		Packet servicePacket = new Packet();
		
		servicePacket.signiture = Packet.RESPONSE_SERVICE_DATA; // 0. 시그니쳐 설정
		
		servicePacket.push(serviceName);//1. 서비스명 넣고 (String)
		servicePacket.push(path);//2.서비스 파일의 상대경로 (  /서비스명/service   의 상대경로 ) (String)
		servicePacket.push(openServiceFile.getName()); //3. 파일 이름을 넣는다. (String)
		
		if( isText(openServiceFile.getName())){ // 4.서비스파일의 타입을 넣는다 (int)
			//4. 텍스트라면
			servicePacket.push((Integer)ServiceProvider.TEXT_TYPE);
		}
		else{//4. 이미지라면
			servicePacket.push((Integer)ServiceProvider.IMAGE_TYPE);
			
			String absoluteFilePath = openServiceFile.getAbsoluteFilePath();
			Bitmap img= BitmapFactory.decodeFile(absoluteFilePath);
			
			servicePacket.push((Integer)img.getWidth() );//4.1 가로길이 넣고
			servicePacket.push((Integer)img.getHeight() );//4.2 세로길이 넣고
		}
		
		byte [] buf = new byte[openServiceFile.getLength()];;//5. 데이터 내용을 넣는다.
		try {
			openServiceFile.read(buf);
			openServiceFile.close();
		} catch (EOFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		servicePacket.push(buf);//5. data를 넣는다.
//		servicePacket.push(buf.toString());//5. data를 넣는다.
		
		

	
		return servicePacket;
	}
	
	protected boolean isDirectory(String fileName){
		if(fileName.indexOf(".") == -1)
			return true;
		else
			return false;
	}
	
	protected boolean isText(String fileName){
		if( (fileName.indexOf(".js") != -1 ) && (fileName.indexOf(".html") != -1) && (fileName.indexOf(".css") != -1)   )
			return false;
		else
			return true;
	}
}
