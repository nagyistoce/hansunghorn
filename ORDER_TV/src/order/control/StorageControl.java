package order.control;

import java.io.File;

import order.bean.ItemBean;
import order.bean.StorageDataBean;

import android.graphics.Bitmap;
import sod.common.Storage;
import sod.common.StorageFile;

public class StorageControl {
	public static String STORAGEID = "lib";

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public StorageFile getStorageFile() {
		return storageFile;
	}

	public void setStorageFile(StorageFile storageFile) {
		this.storageFile = storageFile;
	}

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
		if (Storage.checkIsStorageExists(STORAGEID + "/order")) {
			storage = Storage.getStorage(STORAGEID + "/order");
		} else {
			storage = Storage.createStorage(STORAGEID + "/order");
		}
		if (Storage.checkIsStorageExists(STORAGEID + "/order" + "/storage")) {
			storage = Storage.getStorage(STORAGEID + "/order" + "/storage");
		} else {
			storage = Storage.createStorage(STORAGEID + "/order" + "/storage");
		}
		if (Storage.checkIsStorageExists(STORAGEID + "/order" + "/storage/"+name)) {
			storage = Storage.getStorage(STORAGEID + "/order" + "/storage/"+name);
		} else {
			storage = Storage.createStorage(STORAGEID + "/order" + "/storage/"+name);
		}
	}

	public String[] getStoreList(String extention) throws Exception {

		storage = Storage.getStorage(STORAGEID+"/order/storage");
		return storage.getFileList(extention);
	}
	public String[] getStoreList(String path,String extention) throws Exception {
		storage = Storage.getStorage(STORAGEID+"/order/storage/"+path);
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

		String rating=""+item.getRating();
		storageFile.write("        ".getBytes());
		storageFile.write(StorageDataBean.BEFORE_RATING.getBytes());
		storageFile.write(rating.getBytes());
		storageFile.write(StorageDataBean.AFTER_RATING.getBytes());
		storageFile.write("\n".getBytes());
		
		String recommand=""+item.getRecommandFlag();
		storageFile.write("          ".getBytes());
		storageFile.write(StorageDataBean.BEFORE_RECOMMAND.getBytes());
		storageFile.write(recommand.getBytes());
		storageFile.write(StorageDataBean.AFTER_RECOMMAND.getBytes());
		storageFile.write("\n".getBytes());

		String temp="";
		if(item.getReview()==null)
		{
			temp="";
		}
		else{
			for(int i=0;i<item.getReview().length;i++)
			{
				temp+=item.getReview()[i];
				temp+="\n";
			}
		}
		storageFile.write("          ".getBytes());
		storageFile.write(StorageDataBean.BEFORE_REVIEW.getBytes());
		storageFile.write(temp.getBytes());
		storageFile.write(StorageDataBean.AFTER_REVIEW.getBytes());
		storageFile.write("\n".getBytes());

		
		if(item.getUrl()==null)
		{
			temp="";
		}
		else
		{
			temp=item.getUrl();
		}
		storageFile.write("            ".getBytes());
		storageFile.write(StorageDataBean.BEFORE_BITMAP.getBytes());
		storageFile.write(temp.getBytes());
		storageFile.write(StorageDataBean.AFTER_BITMAP.getBytes());
		storageFile.write("\n".getBytes());

		storageFile.close();
	}


	public ItemBean StoreFileInitConfig(String name, String description,
			String price, String category, String imgUrl, boolean recommand) {
		ItemBean item = new ItemBean();
		if (name == null || description == null || price == null
				|| category == null) {
			return null;
		}
		item.setName(name);
		item.setDescription(description);
		item.setPrice(price);
		item.setCategory(category);
		if(recommand)
		{
			boolean a=recommand;
		}
		item.setRecommandFlag(recommand);
		item.setUrl(imgUrl);
		//item.setUrl(imgUrl);
		// if(!(img==null)){item.setImg(img);}
		this.item = item;
		return item;
	}

	public boolean DeleteStore(String name) throws Exception {
		if (Storage.checkIsStorageExists(STORAGEID + "/order/storage/" + name)) {
			Storage.destroy(STORAGEID + "/order/storage/" + name);
			return true;
		} else
			return false;
	}

	public String Select(String name){
		try{
		if (Storage.checkIsStorageExists(STORAGEID+"/order/storage")) {
			storage = Storage.getStorage(STORAGEID+"/order/storage");
		} else {
			return null;
		}
		if (Storage.checkIsStorageExists(STORAGEID + "/order/storage/" + name)) {
			storage = Storage.getStorage(STORAGEID + "/order/storage/" + name);
			if (storage.checkIsFileExists(name + ".txt")) {
				storageFile = storage.openFile(name + ".txt", Storage.READ);
				byte[] buf = new byte[storageFile.getLength()];
				return storageFile.getFileObject().toString();
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
