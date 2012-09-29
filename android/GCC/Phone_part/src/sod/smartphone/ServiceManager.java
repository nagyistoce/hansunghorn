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
	static final int FILE_NEXT = 2;
	static final int FILE_END = 3;
	
	
	byte serviceBuf[];
	boolean isFirst;
	int sBufIndex;
	

	
	
	ServiceManager(){
		serviceBuf = new byte[10000];
		isFirst = true;
		sBufIndex = 0;
		

	}

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
		
		if(Storage.checkStorageIs(serviceName)){
			if(Storage.checkStorageIs(serviceName + "/service"))
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
			if (Storage.checkStorageIs(""))
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
			if (Storage.checkStorageIs(serviceName)){
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
	
	
	/**
	 * ���� ���� ������ �ִ� ��Ŷ���κ��� ���� ������ �����ϴ� �Լ� 
	 * @param pkt ���� ���� ������ ��� ��Ŷ
	 */
	public void installService(Packet pkt){
		String serviceName = (String)pkt.pop(); // 1. ��Ŷ���� ù��°�� ������ ���� serviceName (String)
		String serviceFilePath = serviceName + "/service";
		
		String relativePath = (String) pkt.pop();// 2. �ι�°�� ������ ���� ��� path
													// (String)


		try {

			Storage serviceStorage = null;
			if (relativePath.equals("/")) {

				if (!Storage.checkStorageIs(serviceFilePath))
					serviceStorage = Storage.createStorage(serviceFilePath);
				else
					serviceStorage = Storage.getStorage(serviceFilePath);
			} 
			else {// ����н��� ������ serviceFilePath�� ������Ʈ �Ѵ�.

				serviceFilePath = serviceFilePath + "/" + relativePath;

				if (!Storage.checkStorageIs(serviceFilePath))
					serviceStorage = Storage.createStorage(serviceFilePath);
				else
					serviceStorage = Storage.getStorage(serviceFilePath);

			}

			String fileName = (String)pkt.pop();//3. ����°�� ������ ���� fileName
			Constants.logger.log(fileName);////////////////////////////////////////////////////////debug
			Integer fileSize = (Integer)pkt.pop();// 4. �׹�°�� ���´� ���� �� fileSize
			
			if(isFirst){
				serviceBuf = new byte[fileSize];
				isFirst = false;
			}
			
			Integer fileType = (Integer)pkt.pop();//5. �ټ���°�� ������ ���� ����Ÿ��
			
			//text type�̳�, imageŸ�Կ� ���� �ٸ��� ����ȴ�.
			if(fileType == ServiceManager.TEXT_TYPE){
				
				byte [] buf = (byte [])pkt.pop(); //6. ������°�� ������ ���� ������
				//////////
				Integer nextAend = (Integer)pkt.pop(); //7. �������� �������� �ִ���
				//������ ���̸� �ۼ��� �����Ѵ�.
				if(nextAend == ServiceManager.FILE_END){ //FileEnd
					isFirst = true;
					
					for(int i = 0 ;  i < buf.length; i++ ){
						serviceBuf[sBufIndex] = buf[i];
						sBufIndex++;
					}
					sBufIndex = 0; //sBufIndex init...
					///////////
					StorageFile serviceFile = serviceStorage.createFile(fileName); // ���� ����
					serviceFile.write(serviceBuf);
					serviceFile.close();
				}
				else{//Next
					for(int i = 0 ;  i < buf.length; i++ ){
						
						if(fileSize < sBufIndex){ //debug code...
							Constants.logger.log("ffsdffsdfdsfsdfsdsfsfdsfsrgferferferferf");
						}
						else{
						serviceBuf[sBufIndex] = buf[i];
						sBufIndex++;
						}
					}
				}// end Next else...
				
			}
			else{//5.1 �̹��� �� ��
				//to Do
				Integer width = (Integer)pkt.pop(); //6.1 ���� ���� ��������
				Integer height = (Integer)pkt.pop(); //6.2 ���� ���� ��������
				
				byte [] buf = (byte [])pkt.pop(); //7. �ϰ���°�� ������ ���� ������
				//////////
				Integer nextAend = (Integer)pkt.pop(); //7. �������� �������� �ִ���
				///////////
				if(nextAend == ServiceManager.FILE_END){ //FileEnd
					isFirst = true;
					
					for(int i = 0 ;  i < buf.length; i++ ){
						serviceBuf[sBufIndex] = buf[i];
						sBufIndex++;
					}
					sBufIndex = 0; //sBufIndex init...
					///////////
					
					Bitmap img = BitmapFactory.decodeByteArray(serviceBuf, 0, serviceBuf.length);
					Bitmap serviceImg = Bitmap.createBitmap(img, 0, 0, width, height);
				
					StorageFile serviceFile = serviceStorage.createFile(fileName);
					serviceFile.writeImage(serviceImg);
					serviceFile.close();
				}
				else{//Next
					for(int i = 0 ;  i < buf.length; i++ ){
						serviceBuf[sBufIndex] = buf[i];
						sBufIndex++;
					}//end for
				}//end else...

			}//end else 5.1 �̹����϶�
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
//			Constants.logger.log("IllegalArgumentException - installService");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
//			Constants.logger.log("IOException - installService");
		}

		
	}

	
	/**
	 * �ش��ϴ� ���� ������ ����Ʈ���� ��ġ���ִ��� Ȯ���Ѵ�.
	 * @param serviceName ���� �̸�
	 * @return ��ġ�������� true, �ƴϸ� false
	 */
	public boolean checkServiceInstalled(String serviceName){
		
		return Storage.checkStorageIs(serviceName);
	}
	
	/**
	 * ������ textŸ������ Ȯ���Ѵ�.
	 * @param fileName
	 * @return textŸ���̸� true, �ƴϸ� false
	 */
	protected boolean isText(String fileName){
		if( (fileName.indexOf(".js") != -1 ) && (fileName.indexOf(".html") != -1) && (fileName.indexOf(".css") != -1)   )
			return false;
		else
			return true;
	}
	
	
	
}
