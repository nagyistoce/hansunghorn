package order.control;

import java.io.File;

import order.bean.ItemBean;
import order.bean.StorageDataBean;

import android.graphics.Bitmap;
import sod.common.Storage;
import sod.common.StorageFile;

public class StorageControl {
	public static String STORAGEID = "order";

	private Storage storage;
	private StorageFile storageFile;
	private String[] list;
	private ItemBean item;
	private String readString = "";

	public void Store(String name) throws Exception {
		if (Storage.checkIsStorageExists(STORAGEID)) {
			storage = Storage.getStorage(STORAGEID);
		} else {
			storage = Storage.createStorage(STORAGEID);
		}
		if (Storage.checkIsStorageExists(STORAGEID + "/" + name)) {
			storage = Storage.getStorage(STORAGEID + "/" + name);
		} else {
			storage = Storage.createStorage(STORAGEID + "/" + name);
		}
	}

	public String[] getStoreList(String extention) throws Exception {

		storage = Storage.getStorage(STORAGEID);
		return storage.getFileList(extention);
	}
	public String[] getStoreList(String path,String extention) throws Exception {
		storage = Storage.getStorage(STORAGEID+"/"+path);
		return storage.getFileList(extention);
	}
	public void StoreSave(ItemBean item) throws Exception {
		if (!storage.checkIsFileExists(item.getName() + ".txt")) {
			storageFile = storage.createFile(item.getName() + ".txt");
		} else {
			storageFile = storage.openFile(item.getName() + ".txt",
					Storage.WRITE);
		}
		storageFile.write(StorageDataBean.BEFORE_CATEGORY.getBytes());
		storageFile.write(item.getCategory().getBytes());
		storageFile.write(StorageDataBean.AFTER_CATEGORY.getBytes());
		storageFile.write("\n".getBytes());

		storageFile.write("  ".getBytes());
		storageFile.write(StorageDataBean.BEFORE_NAME.getBytes());
		storageFile.write(item.getName().getBytes());
		storageFile.write(StorageDataBean.AFTER_NAME.getBytes());
		storageFile.write("\n".getBytes());

		String price = "" + item.getPrice();
		storageFile.write("    ".getBytes());
		storageFile.write(StorageDataBean.BEFORE_PRICE.getBytes());
		storageFile.write(price.getBytes());
		storageFile.write(StorageDataBean.AFTER_PRICE.getBytes());
		storageFile.write("\n".getBytes());

		storageFile.write("      ".getBytes());
		storageFile.write(StorageDataBean.BEFORE_DESCRIPTION.getBytes());
		storageFile.write(item.getDescription().getBytes());
		storageFile.write(StorageDataBean.AFTER_DESCRIPTION.getBytes());
		storageFile.write("\n".getBytes());

		storageFile.close();
	}

	public void StoreSave(Bitmap img) // 저장소에 이미지 저장
	{

	}

	public ItemBean StoreFileInitConfig(String name, String description,
			String price, String category) {
		ItemBean item = new ItemBean();
		if (name == null || description == null || price == null
				|| category == null) {
			return null;
		}
		item.setName(name);
		item.setDescription(description);
		item.setPrice(price);
		item.setCategory(category);
		// if(!(img==null)){item.setImg(img);}
		this.item = item;
		return item;
	}

	public boolean DeleteStore(String name) throws Exception {
		if (Storage.checkIsStorageExists(STORAGEID + "/" + name)) {
			Storage.destroy(STORAGEID + "/" + name);
			return true;
		} else
			return false;
	}

	public File Select(String name){
		try{
		if (Storage.checkIsStorageExists(STORAGEID)) {
			storage = Storage.getStorage(STORAGEID);
		} else {
			return null;
		}
		if (Storage.checkIsStorageExists(STORAGEID + "/" + name)) {
			storage = Storage.getStorage(STORAGEID + "/" + name);
			if (storage.checkIsFileExists(name + ".txt")) {
				storageFile = storage.openFile(name + ".txt", Storage.READ);
				byte[] buf = new byte[storageFile.getLength()];
				return storageFile.getFileObject();
			}
			else {
				return null;
			}
			
		}
		}catch(Exception e){ 
			String abc=""+e;
			System.out.println(abc);
		}
		return null;
	}

	public ItemBean Arrange(String[] name) {
		return null;
	}
}
