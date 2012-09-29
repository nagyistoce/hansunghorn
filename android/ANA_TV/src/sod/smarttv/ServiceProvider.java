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
	static final int FILE_NEXT = 2;
	static final int FILE_END = 3;
	
	static final int MTU = 10000;//0x3765;//0x000007530
	
	
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
			
			if (Storage.checkStorageIs(serviceFilePath))
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
					
					
					//������ ������ ���� ��Ŷ�� �����´�. 30kB�̻��̸� ������ �����´�.
					ArrayList<Packet> subServiceFilePackets = createServicePacketList(openServiceFile, "/");
					//�ִ´�.
					for(int j = 0 ; j < subServiceFilePackets.size() ; j++)
						serviceFilePackets.add(subServiceFilePackets.get(j));
					
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

    /**
     * �������丮�� ���Ϸκ��� ��Ŷ���� �����Ѵ�. 
     * @param serviceFilePath
     * @param subPath
     * @param addPath
     * @param serviceFilePackets
     */
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
					//������ ������ ���� ��Ŷ�� �����´�. 
					ArrayList<Packet> subServiceFilePackets = createServicePacketList(openServiceFile, subPath);
					
					//�ִ´� 
					for(int k =0 ; k < subServiceFilePackets.size() ; k++){
						serviceFilePackets.add(subServiceFilePackets.get(k));
					}
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
	protected ArrayList<Packet> createServicePacketList(StorageFile openServiceFile, String path) throws IllegalStateException{
	//	Packet packet = new packet
		ArrayList<Packet> subServicePackets = new ArrayList<Packet>();
		
		byte [] buf = new byte[openServiceFile.getLength()];//
		try {
			openServiceFile.read(buf);
		} catch (EOFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//
		int fileSize = openServiceFile.getLength();
		int packetCnt = fileSize / MTU + 1;////////////////////////////////////////////////
		int lastPacketDataSize = fileSize % MTU;
		
		for(int i = 0 ; i < packetCnt ; i++){
			Packet servicePacket = createServicePacket(openServiceFile, path); //1~4 ������ ��ħ
			
			byte [] subBuf;
			int byteSize;
			
			if (i == packetCnt - 1) { // �ش� ������ ������ ��Ŷ�϶�
				subBuf = new byte[lastPacketDataSize];
				byteSize = lastPacketDataSize;
			} else {
				subBuf = new byte[MTU];
				byteSize = MTU;
			}
			
			int k = 0;
			for(int j = i*MTU; j < (i*MTU + (byteSize)); j++){

				subBuf[k] = buf[j];
				k++;
			}//end for....
			//����� ©�� �־���ϰ�
			servicePacket.push(subBuf);//7. data�� �ִ´�. (�̹����� 7)
			
			
			//6. ������ ����������, ��� �̾������� ���θ� ǥ���Ѵ�.
			if (i == packetCnt - 1){
				servicePacket.push((Integer)ServiceProvider.FILE_END);
			}
			else{
				servicePacket.push((Integer)ServiceProvider.FILE_NEXT);
			}
			
			subServicePackets.add(servicePacket);
		}//end for....
		
		try {
			openServiceFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return subServicePackets;
	}
	
	/**
	 * ��Ŷ�� �����ؼ� ������ ������ ��´�.
	 * @param openServiceFile
	 * @param path
	 * @return
	 * @throws IllegalStateException
	 */
	protected Packet createServicePacket(StorageFile openServiceFile, String path) 
			throws IllegalStateException{
		
		Packet servicePacket = new Packet();
		
		servicePacket.signiture = Packet.RESPONSE_SERVICE_DATA; // 0. �ñ״��� ����
		
		servicePacket.push(serviceName);//1. ���񽺸� �ְ� (String)
		servicePacket.push(path);//2.���� ������ ����� (  /���񽺸�/service   �� ����� ) (String)
		servicePacket.push(openServiceFile.getName()); //3. ���� �̸��� �ִ´�. (String)
		Constants.logger.log("send : "+openServiceFile.getName());
		servicePacket.push((Integer)openServiceFile.getLength());//4. ������ �� ������
		if( isText(openServiceFile.getName())){ // 5.���������� Ÿ���� �ִ´� (int)
			// �ؽ�Ʈ���
			servicePacket.push((Integer)ServiceProvider.TEXT_TYPE);
		}
		else{//�̹������
			servicePacket.push((Integer)ServiceProvider.IMAGE_TYPE);
			
			String absoluteFilePath = openServiceFile.getAbsoluteFilePath();
			Bitmap img= BitmapFactory.decodeFile(absoluteFilePath);
			
			servicePacket.push((Integer)img.getWidth() );//6.1 ���α��� �ְ�
			servicePacket.push((Integer)img.getHeight() );//6.2 ���α��� �ְ�
		}
		
		return servicePacket;
	}
	
	/**
	 * �ش� ������ ���丮���θ� �˷��ش�.
	 * @param fileName
	 * @return ���丮�� true, �ƴϸ� false
	 */
	protected boolean isDirectory(String fileName){
		if(fileName.indexOf(".") == -1)
			return true;
		else
			return false;
	}
	
	/**
	 * ������ textŸ�� ���θ� �˷��ش�.
	 * @param fileName
	 * @return text Ÿ���̸� true, �ƴϸ� false
	 */
	protected boolean isText(String fileName){
		if( (fileName.indexOf(".js") != -1 ) && (fileName.indexOf(".html") != -1) && (fileName.indexOf(".css") != -1)   )
			return false;
		else
			return true;
	}
}
