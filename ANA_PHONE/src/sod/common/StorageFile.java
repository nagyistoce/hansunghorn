package sod.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


enum SeekOrigin
{
    Begin,
    Current,
    End,
}

/**
 * 
 * @author MB
 *
 */
public class StorageFile {
	File file;
	
	FileInputStream fileInputStream;
	FileOutputStream fileOutputStream;
	
	BufferedInputStream in;
	BufferedOutputStream out;
	
	
	StorageFile() {
		
		
	}
	
	/**
	 * 
	 * @param mFile
	 * @throws IOException
	 * 파일 생성에 실패하면 IOException을 던진다.
	 * @throws NullPointerException
	 * 인자로 넘어온 값이 Null이면 NullPointerException을 던진다.
	 */
	static protected StorageFile createStorageFile(File mFile)throws IOException, NullPointerException{
		if(mFile == null)
			throw new NullPointerException();
		
		StorageFile storageFile = new StorageFile();
		
		storageFile.file = mFile;	
		storageFile.file.createNewFile();//
		
		storageFile.fileInputStream = new FileInputStream(storageFile.file);
		storageFile.fileOutputStream = new FileOutputStream(storageFile.file);
		
		storageFile.in = new BufferedInputStream(storageFile.fileInputStream);
		storageFile.out = new BufferedOutputStream(storageFile.fileOutputStream);
		
		return storageFile;
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
	static protected StorageFile getStorageFile(File mFile)throws IOException, NullPointerException{
		if(mFile == null)
			throw new NullPointerException();
		
		StorageFile storageFile = new StorageFile();
		
		storageFile.file = mFile;
		
		storageFile.fileInputStream = new FileInputStream(storageFile.file);
		storageFile.fileOutputStream = new FileOutputStream(storageFile.file);
		
		storageFile.in = new BufferedInputStream(storageFile.fileInputStream);
		storageFile.out = new BufferedOutputStream(storageFile.fileOutputStream);
		
		return storageFile;
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
	
	public void read(byte[] buf) throws IOException,  EOFException{
		int returnInt;
		returnInt = in.read(buf);
		
		if(returnInt == -1)
			throw new EOFException();
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
		int returnInt;
		returnInt = in.read(buf, index, length);
		
		if(returnInt == -1)
			throw new EOFException();
	}
	
	/**
	 * 파일내에 파일 포인터가 가르치는 곳을 offset만큼 이동한다.
	 * @param offset
	 * 파일포인터를 이동하고자 하는 위치
	 * @param seekorigin
	 * 파일을 이동하기 전에 파일포인터의 위치
	 * @throws EOFException
	 * 파일 포인터를 이동하다가 파일의 끝에 도달하면 EOFException을 던진다.
	 */
	public void seek(int offset, SeekOrigin seekorigin) throws EOFException{
		
	}
	
	/**
	 * 현재의 파일포인터의 위치를 얻는다.
	 */
	public void getPosition(){
		
	}
	
	/**
	 * 파일의 길이를 가져온다.
	 * 단위는 byte
	 */
	public int getLength(){
		return (int) file.length();
	}
	
	/**
	 * 파일의 내용을 저장소 상에 기록한다.
	 * @throws IOException 
	 * 이미 닫혀있으면 IOException 을 던진다.
	 */
	public void flush() throws IOException{
		in.close();
		out.close();
		
		fileInputStream.close();
		fileOutputStream.close();
		
		fileInputStream = new FileInputStream(file);
		fileOutputStream = new FileOutputStream(file);
		
		in = new BufferedInputStream(fileInputStream);
		out = new BufferedOutputStream(fileOutputStream);
	}
	
	/**
	 * 파일을 닫는다.
	 * @throws IOException 
	 * 이미 닫혀있으면 IOException 을 던진다.
	 */
	public void close() throws IOException{
		in.close();
		out.close();
		
		fileInputStream.close();
		fileOutputStream.close();
		
	}
}
