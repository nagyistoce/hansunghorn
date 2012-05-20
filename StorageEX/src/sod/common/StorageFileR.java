package sod.common;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StorageFileR extends StorageFile {
	
	FileInputStream fileInputStream;
	
	BufferedInputStream in;
	
	/**
	 * @param mFile
	 * @throws IOException
	 * 파일 생성에 실패하면 IOException을 던진다.
	 * @throws NullPointerException
	 * 인자로 넘어온 값이 Null이면 NullPointerException을 던진다.
	 */
	static public StorageFile createStorageFile(File mFile)throws IOException, NullPointerException{
		if(mFile == null)
			throw new NullPointerException();
		
		StorageFileR storageFileR = new StorageFileR();
		
		storageFileR.file = mFile;	
		storageFileR.file.createNewFile();//
		
		storageFileR.fileInputStream = new FileInputStream(storageFileR.file);
		
		storageFileR.in = new BufferedInputStream(storageFileR.fileInputStream);
		
		return storageFileR;
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
		
		StorageFileR storageFileR = new StorageFileR();
		
		storageFileR.file = mFile;
		
		storageFileR.fileInputStream = new FileInputStream(storageFileR.file);
		
		
		storageFileR.in = new BufferedInputStream(storageFileR.fileInputStream);
		
		return storageFileR;
	}
	
	/**
	 * byte배열에 파일에 있는 모든 데이터를 담아온다.
	 * 
	 * @param buf
	 * @throws IOException
	 * @throws EOFException
	 */
	public void read(byte[] buf) throws IOException,  EOFException{
		while(in.read(buf) != -1) {;}
		
	}
	
	/**
	 * 해당 파일의 데이터를 읽는다.
	 * @param buf
	 * 파일에서 읽은 내용을 담기위한 버퍼
	 * @param index
	 * 파일에서 데이터를 읽을 때, 버퍼에서 담기 시작할 위치
	 * @param length
	 * 버퍼를 사용하여 읽어올 길이
	 * @throws  IOException
	 * 이미 close가 실행됐거나 파일에 읽기를 실패하면  IOException을 던진다.
	 * @throws EOFException
	 * 파일을 읽다가 파일의 끝에 도달하면 EOFException을 던진다.
	 */
	public void read(byte[] buf, int index, int length) throws IOException, EOFException{
		
		for(int i = 0 ; i<index ; i++){
			if (in.read() == -1)
				break;
		}
		
		int returnInt;	
		returnInt = in.read(buf, 0, length);
		
		
		if(returnInt == -1)
			throw new EOFException();
	}
	
	
	/**
	 * 미구현
	 */
	@Override
	public void getPosition() {
		// TODO Auto-generated method stub
	}


	/**
	 * 미구현
	 */
	@Override
	public void seek(int offset, SeekOrigin seekorigin) throws EOFException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 파일의 내용을 저장소 상에 기록한다.
	 * @throws IOException 
	 * 이미 닫혀있으면 IOException 을 던진다.
	 */
	public void flush() throws IOException{
		in.close();
		fileInputStream.close();

		fileInputStream = new FileInputStream(file);
		in = new BufferedInputStream(fileInputStream);
	}
	
	/**
	 * 파일을 닫고 저장한다.
	 * @throws IOException 
	 * 이미 닫혀있으면 IOException 을 던진다.
	 */
	public void close() throws IOException{
		in.close();
		fileInputStream.close();
		
	}

}
