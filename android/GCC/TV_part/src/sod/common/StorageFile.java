package sod.common;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;


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
public  class StorageFile {
	File file;
	String filePath;
	
	FileInputStream fileInputStream;
	BufferedInputStream in;
	
	FileOutputStream fileOutputStream;
	BufferedOutputStream out;
	StringBuilder mBuf;
	
	public final static int READ = 0;
	public final static int WRITE = 1;
	public final static int WRITE_PLUS = 2;
	
	int mode = 0;
	
	int position;
	
	StorageFile() {
		
		
	}
	

	
	
	/**
	 * �ش����� StorageFile�� �����Ѵ�.
	 * @param mFile 
	 * @param filePath
	 * @return �ش����� StorageFile
	 * @throws IOException ���� ������ �����ϸ� IOException�� ������.
	 * @throws NullPointerException ���ڷ� �Ѿ�� ���� Null�̸� NullPointerException�� ������.
	 */
	 static public StorageFile createStorageFile(File mFile, String filePath)throws IOException, NullPointerException{
	 
		StorageFile storageFile = new StorageFile();
	 
		storageFile.file = mFile;	
		storageFile.filePath = filePath;
		storageFile.fileOutputStream = new FileOutputStream(storageFile.file);
		
		storageFile.out = new BufferedOutputStream(storageFile.fileOutputStream);
		storageFile.mode = WRITE;
		
		return storageFile;
	 }
	
	/**
	 * �ش����� StorageFile�� �޾ƿ´�.
	 * @param mFile
	 * @return �ش����� StorageFile
	 * @throws IOException
	 * ���� ������ �����ϸ� IOException�� ������.
	 * @throws NullPointerException
	 * ���ڷ� �Ѿ�� ���� Null�̸� NullPointerException�� ������.
	 */
	static public StorageFile getStorageFile(File mFile, int mode, String filePath)throws IOException, NullPointerException{
		StorageFile storageFile = new StorageFile();
		
		storageFile.file = mFile;	
		storageFile.filePath = filePath;
		
		switch(mode){
		case READ:
			storageFile.fileInputStream = new FileInputStream(storageFile.file);
			
			storageFile.in = new BufferedInputStream(storageFile.fileInputStream);
			storageFile.mode = READ;
			break;
		case WRITE:
			storageFile.fileOutputStream = new FileOutputStream(storageFile.file);
			
			storageFile.out = new BufferedOutputStream(storageFile.fileOutputStream);
			storageFile.mode = WRITE;
			break;
		case WRITE_PLUS:
			storageFile.fileInputStream = new FileInputStream(storageFile.file);
			storageFile.in = new BufferedInputStream(storageFile.fileInputStream);
			storageFile.mode = WRITE_PLUS;
			
			byte [] buf = new byte[storageFile.getLength()];
			while(storageFile.in.read(buf) != -1) {;}
			String sBuf = new String(buf);
			storageFile.in.close();
			storageFile.fileInputStream.close();
			
			storageFile.fileOutputStream = new FileOutputStream(storageFile.file);
			storageFile.out = new BufferedOutputStream(storageFile.fileOutputStream);
			storageFile.write(sBuf.getBytes());
			storageFile.flush();
			
			break;
		}
		return storageFile;
	}

	/**
	 * byte�迭�� ���Ͽ� �ִ� ��� �����͸� ��ƿ´�.
	 * 
	 * @param buf
	 * ���Ͽ��� ���� ������ ������� ����
	 * @throws IOException
	 * �̹� close�� ����ưų� ���Ͽ� �б⸦ �����ϸ�  IOException�� ������.
	 * @throws EOFException
	 * ������ �дٰ� ������ ���� �����ϸ� EOFException�� ������
	 * @throws NullPointerException
	 * mode�� WRITE�ε� read�� ȣ���ϸ�  NullPointerException�� ������.
	 */
	public void read(byte[] buf) throws IOException,  EOFException{
		
		if(mode != READ)
			DebugUtils.throwException();
		
		while(in.read(buf) != -1) {;}
		
	}
	
	/**
	 * �ش� ������ �����͸� �д´�.
	 * @param buf
	 * ���Ͽ��� ���� ������ ������� ����
	 * @param index
	 * ���Ͽ��� �����͸� ���� ��, ���ۿ��� ��� ������ ��ġ
	 * @param length
	 * ���۸� ����Ͽ� �о�� ����
	 * @throws  IOException
	 * �̹� close�� ����ưų� ���Ͽ� �б⸦ �����ϸ�  IOException�� ������.
	 * @throws EOFException
	 * ������ �дٰ� ������ ���� �����ϸ� EOFException�� ������.
	 * @throws NullPointerException
	 * mode�� WRITE�ε� read�� ȣ���ϸ�  NullPointerException�� ������.
	 */
	public void read(byte[] buf, int index, int length) throws IOException, EOFException{
		
		if(mode != READ)
			DebugUtils.throwException();
		
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
	 * �ش����Ͽ� �����͸� ����.
	 * @param buf
	 * ���Ͽ��� ���� ������ ������� ����
	 * @throws IOException
	 *  �̹� close�� ����ưų� ���Ͽ� ���⸦ �����ϸ�  IOException�� ������.
	 * @throws NullPointerException
	 * mode�� READ�ε� write�� ȣ���ϸ�  NullPointerException�� ������.
	 */
	public void write(byte [] buf) throws IOException{
		
		if(mode == READ)
			DebugUtils.throwException();
		
		out.write(buf);
	}
	
	/**
	 * �ش� ���Ͽ� �����͸� ����.
	 * @param buf
	 * ���Ͽ� �������ϴ� ������ ����Ʈ �迭�� ����ȭ�� ��
	 * @param index
	 * ���ۿ����� �������ϴ� �κ��� ������ġ
	 * @param length
	 * �������ϴ� ����Ʈ �迭�� index������ ����
	 * @throws  IOException
	 * ���Ͽ� ���⸦ �����ϸ�  IOException�� ������.
	 * @throws NullPointerException
	 * mode�� READ�ε� write�� ȣ���ϸ�  NullPointerException�� ������.
	 */
	public void write(byte[] buf, int index, int length) throws IOException{
		
		if(mode != WRITE)
			DebugUtils.throwException();
		
		out.write(buf, index, length);

	}
	
	/**
	 * ����ҿ� �ִ� ���Ͽ� �̹����� ���� ����Ѵ�.
	 * �����ϴ� Ȯ���ڴ� jpg, png�̴�.
	 * @param img
	 * �̹��� ������ ����ִ� ��ü
	 * @throws IOException
	 *  �̹� close�� ����ưų� ���Ͽ� ���⸦ �����ϸ�  IOException�� ������.
	 *  ���� Ȯ���ڰ� jpg, png�� �ƴϿ���
	 * @throws NullPointerException
	 * mode�� READ�ε� write�� ȣ���ϸ�  NullPointerException�� ������.
	 */
	public void writeImage(Bitmap img) throws IOException{
		
		if(mode == READ)
			DebugUtils.throwException();
		
		String fileName = file.getName();
		String [] splitStr = fileName.split(".");
		
		if( splitStr[splitStr.length - 1].equals("jpg") )
			img.compress(CompressFormat.JPEG, 100, out);
		else if( splitStr[splitStr.length - 1].equals("png")){
			img.compress(CompressFormat.PNG, 100, out);
		}
		else
			DebugUtils.throwException();
	//	out.write(buf);
	}

	/**
	 * ���ϳ��� ���� �����Ͱ� ����ġ�� ���� offset��ŭ �̵��Ѵ�.
	 * (���� �̱���)
	 * @param offset
	 * ���������͸� �̵��ϰ��� �ϴ� ��ġ
	 * @param seekorigin
	 * ������ �̵��ϱ� ���� ������������ ��ġ
	 * @throws EOFException
	 * ���� �����͸� �̵��ϴٰ� ������ ���� �����ϸ� EOFException�� ������.
	 */
	public void seek(int offset, SeekOrigin seekorigin) throws EOFException{
		
	}
	
	/**
	 * ������ ������������ ��ġ�� ��´�.
	 * (���� �̱���)
	 */
	public void getPosition(){
		
	}
	
	/**
	 * ������ ���̸� �����´�.
	 * ������ byte
	 */
	public int getLength(){
		return (int) file.length();
		
		
	}
	
	/**
	 * ������ ������ ����� �� ����Ѵ�.
	 * (��������)
	 * @throws IOException 
	 * �̹� ���������� IOException �� ������.
	 * @throws NullPointerException
	 * mode�� READ�� ���¿��� flush�� ȣ���ϸ� NullPointerException�� ������.
	 */
	public void flush() throws IOException{
		switch(mode){
		case READ:
			DebugUtils.throwException();
			break;
			
		case WRITE:
		case WRITE_PLUS:
			out.flush();
			fileOutputStream.flush();
			break;
		}
	}
	
	/**
	 * StorageFile�� �̸��� ��ȯ�Ѵ�.
	 * @return StorageFile�� �̸�
	 */
	public String getName(){
		return file.getName();
	}
	
	/**
	 * ������ �ݰ� �����Ѵ�.
	 * @throws IOException 
	 * �̹� ���������� IOException �� ������.
	 */
	public void close() throws IOException{
		switch(mode){
		case READ:
			in.close();
			fileInputStream.close();
			break;
			
		case WRITE:
		case WRITE_PLUS:
			out.close();
			fileOutputStream.close();
			break;
		}
	}
	
	/**
	 * storageFile�� SOD��Ʈ ����� ���� ����θ� ��ȯ�Ѵ�.
	 * @return SOD��Ʈ ����� ���� �����
	 */
	public String getRelativeFilePath(){
		return filePath;
	}
	
	/**
	 * storageFile�� �����θ� ��ȯ�Ѵ�.
	 * @return ������
	 */
	public String getAbsoluteFilePath(){
		return file.getAbsolutePath();
	}
	
	
}
