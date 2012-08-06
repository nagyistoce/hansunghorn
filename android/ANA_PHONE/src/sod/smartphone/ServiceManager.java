package sod.smartphone;

import java.io.IOException;
import java.net.SocketException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

import sod.common.Constants;
import sod.common.Packet;
import sod.common.Storage;
import sod.common.StorageFile;


/**
 * Ŭ���̾�Ʈ���� ���������� ��δ� /sod/SERVICE_NAME/service
 * @author MB
 *
 */
public class ServiceManager {
	
	ServiceReceiveHandler mHandler;
	
	static final int TEXT_TYPE = 0;
	static final int IMAGE_TYPE = 1;
	

	/**
	 * ���񽺰� ��ġ�Ǿ� �ִ��� Ȯ��
	 * @param serviceName
	 * ���� �̸�
	 * @return
	 * ���񽺰� ��ġ�Ǿ� ������ true �ƴϸ� false
	 * @throws IllegalArgumentException
	 * �ŰԺ����� null�� �� IllegalArgumentException�� �߻��Ѵ�.
	 */
	public boolean findServiceByName(String serviceName) throws IllegalArgumentException{
		
		if(Storage.checkIsStorageExists(serviceName)){
			if(Storage.checkIsStorageExists(serviceName + "/service"))
				return true;
		}
		
		return false;	
	}

	/**
	 * �ش� �̸��� ���� ����
	 * @param serviceName
	 * ���� �̸�
	 * @throws IllegalArgumentException
	 * �Ű������� null�̰ų� �ش� �̸��� ���񽺰� ��ġ���� ���� ��� �߻�
	 * @throws IllegalStateException
	 * �ι� �̻� ȣ��� �� �߻�.
	 */
	public void startService(String serviceName) throws IllegalArgumentException, IllegalStateException{
		
	}

	/**
	 * ��ġ�Ǿ� �ִ� ���� ��� ��ȯ
	 * @return
	 * ���� ���
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
	 * Ư�� ���񽺸� ����
	 * 
	 * @param serviceName
	 *            ���� �̸�
	 * @throws IllegalArgumentException
	 *             �Ű������� null�̰ų� �ش� serviceName�� ��ġ�ϴ� ���� �����Ͱ� ���� ��� �߻�.
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
	 * ���� �ٿ�ε�� ȣ���� �ݹ� �Լ� ���
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 * @throws IllegalArgumentException
	 * �Ű������� null�� ��� �߻�.
	 */
	public void setSeriveReceiveHandler(ServiceReceiveHandler handler) throws IllegalArgumentException{
		mHandler = handler;
	}
	
	public void installService(Packet packet){
		String serviceName = (String)packet.pop(); // 1. ��Ŷ���� ù��°�� ������ ���� serviceName (String)
		String serviceFilePath = serviceName + "/service";
		
		String relativePath = (String) packet.pop();// 2. �ι�°�� ������ ���� ��� path
													// (String)

		try {

			Storage serviceStorage = null;
			if (relativePath.equals("/")) {

				if (!Storage.checkIsStorageExists(serviceFilePath))
					serviceStorage = Storage.createStorage(serviceFilePath);
				else
					serviceStorage = Storage.getStorage(serviceFilePath);
			} 
			else {// ����н��� ������ serviceFilePath�� ������Ʈ �Ѵ�.

				serviceFilePath = serviceFilePath + "/" + relativePath;

				if (!Storage.checkIsStorageExists(serviceFilePath))
					serviceStorage = Storage.createStorage(serviceFilePath);
				else
					serviceStorage = Storage.getStorage(serviceFilePath);

			}

			String fileName = (String)packet.pop();//3. ����°�� ������ ���� fileName
			Integer fileType = (Integer)packet.pop();//4. �׹�°�� ������ ���� ����Ÿ��
			Constants.logger.log(fileName);
			
			if(fileType == ServiceManager.TEXT_TYPE){
				StorageFile serviceFile = serviceStorage.createFile(fileName); // ���� ����
				byte [] buf = (byte [])packet.pop(); //5. �ټ���°�� ������ ���� ������
				serviceFile.write(buf);
				serviceFile.close();
			}
			else{//4.1 �̹��� �� ��
				//to Do
				Integer width = (Integer)packet.pop(); //4.1 ���� ���� ��������
				Integer height = (Integer)packet.pop(); //4.2 ���� ���� ��������
				
				byte [] buf = (byte [])packet.pop(); //5. �ټ���°�� ������ ���� ������
				Bitmap img = BitmapFactory.decodeByteArray(buf, 0, buf.length);
				Bitmap serviceImg = Bitmap.createBitmap(img, 0, 0, width, height);
				
				StorageFile serviceFile = serviceStorage.createFile(fileName);
				serviceFile.writeImage(serviceImg);
				serviceFile.close();
			}
			
			
			
				
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
//			Constants.logger.log("IllegalArgumentException - installService");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
//			Constants.logger.log("IOException - installService");
		}

		
	}

	
	
	public boolean isExistService(String serviceName){
		
		return Storage.checkIsStorageExists(serviceName);
	}
	
	
	protected boolean isText(String fileName){
		if( (fileName.indexOf(".js") != -1 ) && (fileName.indexOf(".html") != -1) && (fileName.indexOf(".css") != -1)   )
			return false;
		else
			return true;
	}
	
	
	
}
