package sod.common;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageFileW extends StorageFile{
	FileOutputStream fileOutputStream;
	BufferedOutputStream out;
	StringBuilder mBuf;
	
	
	/**
	 * 
	 * @param mFile
	 * @throws IOException
	 * 파일 생성에 실패하면 IOException을 던진다.
	 * @throws NullPointerException
	 * 인자로 넘어온 값이 Null이면 NullPointerException을 던진다.
	 */
	static public StorageFile createStorageFile(File mFile)throws IOException, NullPointerException{
		if(mFile == null)
			throw new NullPointerException();
		
		StorageFileW storageFileW = new StorageFileW();
		
		storageFileW.file = mFile;	
		storageFileW.file.createNewFile();//
		storageFileW.fileOutputStream = new FileOutputStream(storageFileW.file);
		storageFileW.out = new BufferedOutputStream(storageFileW.fileOutputStream);

		
		return storageFileW;
	}
	
	/**
	 * 
	 * @param mFile
	 * @return
	 * @throws IOException
	 * 파일 생성에 실패하면 IOException을 던진다.
	 * @throws NullPointerException
	 * 인자로 넘어온 값이 Null이면 NullPointerException을 던진다.
	 */
	static public StorageFile getStorageFile(File mFile)throws IOException, NullPointerException{
		if(mFile == null)
			throw new NullPointerException();
		
		StorageFileW storageFileW = new StorageFileW();
		storageFileW.file = mFile;
		storageFileW.fileOutputStream = new FileOutputStream(storageFileW.file);
		storageFileW.out = new BufferedOutputStream(storageFileW.fileOutputStream);
		
		
		return storageFileW;
	}
	
	public void write(byte [] buf) throws IOException{
		out.write(buf);
	}
	
	/**
	 * 해당 파일에 데이터를 쓴다.
	 * @param buf
	 * 파일에 쓰고자하는 내용을 바이트 배열로 직렬화한 것
	 * @param index
	 * 버퍼에서의 쓰고자하는 부분의 시작위치
	 * @param length
	 * 쓰고자하는 바이트 배열의 index부터의 길이
	 * @throws  IOException
	 * 파일에 쓰기를 실패하면  IOException을 던진다.
	 */
	public void write(byte[] buf, int index, int length) throws IOException{
		
		out.write(buf, index, length);
		
	}
	
	/**
	 * 파일의 내용을 저장소 상에 기록한다.
	 * @throws IOException 
	 * 이미 닫혀있으면 IOException 을 던진다.
	 */
	public void flush() throws IOException{

		out.close();
		fileOutputStream.close();
		
		fileOutputStream = new FileOutputStream(file);
		out = new BufferedOutputStream(fileOutputStream);
	}
	
	/**
	 * 파일을 닫고 저장한다.
	 * @throws IOException 
	 * 이미 닫혀있으면 IOException 을 던진다.
	 */
	public void close() throws IOException{
		out.close();
		fileOutputStream.close();
	}

}
