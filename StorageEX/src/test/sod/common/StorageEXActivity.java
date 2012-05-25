package test.sod.common;

import java.io.FileNotFoundException;
import java.io.IOException;

import sod.common.Storage;
import sod.common.StorageFile;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class StorageEXActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        EditText editText = (EditText)findViewById(R.id.editText);
        EditText editText2 = (EditText)findViewById(R.id.editText2);
        EditText editText3 = (EditText)findViewById(R.id.editText3);
        EditText editText4 = (EditText)findViewById(R.id.editText4);
        
        Storage storage = null;
        String [] list = null;
        String str = new String();
        try {
 //			storage = Storage.crateStorage("ana");
			storage = Storage.getStorage("ana");
			editText2.setText(storage.getSODStoragePath());
			
			//getFileListTest  파일검색
			list = storage.getFileList(".docx");;
			
			for(int i =0; i < list.length ; i++){
				str+= list[i];
				str+="\n";
			}
			
			editText3.setText(str);
			
			
			
			//파일에 쓰기 테스트
//			StorageFile storageFile = storage.createFile("ex.txt");
			StorageFile storageFile = storage.openFile("ex.txt", Storage.WRITE);
			str = "MyLifeForIU\nHelloWord...";
			storageFile.write(str.getBytes());
			storageFile.close();
			
			
			
			// 파일 내용 불러오기 테스트
			
			StorageFile storageFile2 = storage.openFile("ex.txt", Storage.READ); //
			byte [] buf = new byte[storageFile2.getLength()];
			// 0
			storageFile2.read(buf);
			editText4.setText(new String(buf));
			storageFile2.close();
			
			
			//파일내용 불러와서 이어쓰기 테스트
			StorageFile storageFile3 = storage.openFile("ex.txt", Storage.WRITE_PLUS);
			String str2 = new String("AddString");
			storageFile3.write(str2.getBytes());
			storageFile3.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			editText.setText("IOException");
		} catch(IllegalArgumentException e){
			editText.setText("IllegalArgumentException");;
		}catch(NullPointerException e){
			editText3.setText("NullPointerException");;
		}
        
        /*
        try {
			storage.renameFile("b.txt", "iu.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			editText3.setText("FileNotFoundException");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			editText3.setText("NullPointerException");
		}
        
        */
        
        /*
        if(storage.checkIsFileExists("a.txt"))
        	editText3.setText("true");
        else
        	editText3.setText("false");
        */
        
        /* deleteFileTest...
        try {
			storage.deleteFile("a.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			editText3.setText("FileNotFoundException");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			editText3.setText("NullPointerException");
		}
		*/
        
        /*// openFile Test...
        try {
			storage.openFile(null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			editText3.setText("FileNotFoundException");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			editText3.setText("NullPointerException");
		}
       */
        
        
        
        /*
        try {
			editText3.setText(new String( String.valueOf(Storage.getStorageSize("ana")) ));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			editText3.setText("IOException");
		}
        */
        /*
        try {
			Storage.destroy("ana");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			editText2.setText("폴더없엉 ㅋ");
		}
		*/
        
        /*
        try {
			Storage.clear("ana");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			editText3.setText("IOException");
		}
        */
    }
}