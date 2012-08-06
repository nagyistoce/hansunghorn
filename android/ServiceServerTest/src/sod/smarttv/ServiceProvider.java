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
 * �������� ���������� ��δ� /sod/lib
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
	 * ������ ���丮 ���
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 */
	void setPath(String newpath) throws IllegalArgumentException{
		serviceFilePath = newpath;
	}
	
	/**
	 * ���� �����͸� ����ִ� ��Ŷ�� ��ȯ
	 * ���� ȣ��� createServicePacket()�� ���������� ȣ��ȴ�.
	 * @return
	 * ���� �����͸� ���� ��Ŷ
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
				if( isDirectory(fileList[i])){//���丮�϶��� ���� ������ ����� �ٽ� �����ͼ� ��Ŷ ����
					getServicePacketSubDirectory(serviceFilePath, fileList[i] ,fileList[i], serviceFilePackets);
				}
				else{//�����϶�...��Ŷ����
					
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
	 * ���� �����͸� ���� ��Ŷ
	 * @throws IllegalStateException
	 * ���ο� ������ ��ΰ� ���ų� �߸��Ǿ��� ��� �߻�
	 */
	protected Packet createServicePacket(StorageFile openServiceFile, String path) throws IllegalStateException{
	//	Packet packet = new packet
		Packet servicePacket = new Packet();
		
		servicePacket.signiture = Packet.RESPONSE_SERVICE_DATA; // 0. �ñ״��� ����
		
		servicePacket.push(serviceName);//1. ���񽺸� �ְ� (String)
		servicePacket.push(path);//2.���� ������ ����� (  /���񽺸�/service   �� ����� ) (String)
		servicePacket.push(openServiceFile.getName()); //3. ���� �̸��� �ִ´�. (String)
		
		if( isText(openServiceFile.getName())){ // 4.���������� Ÿ���� �ִ´� (int)
			//4. �ؽ�Ʈ���
			servicePacket.push((Integer)ServiceProvider.TEXT_TYPE);
		}
		else{//4. �̹������
			servicePacket.push((Integer)ServiceProvider.IMAGE_TYPE);
			
			String absoluteFilePath = openServiceFile.getAbsoluteFilePath();
			Bitmap img= BitmapFactory.decodeFile(absoluteFilePath);
			
			servicePacket.push((Integer)img.getWidth() );//4.1 ���α��� �ְ�
			servicePacket.push((Integer)img.getHeight() );//4.2 ���α��� �ְ�
		}
		
		byte [] buf = new byte[openServiceFile.getLength()];;//5. ������ ������ �ִ´�.
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
		servicePacket.push(buf);//5. data�� �ִ´�.
//		servicePacket.push(buf.toString());//5. data�� �ִ´�.
		
		

	
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
