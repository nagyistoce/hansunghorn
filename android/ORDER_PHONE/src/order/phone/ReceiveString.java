package order.phone;

import order.bean.ConnectionBean;
import order.bean.ItemBean;
import order.bean.StorageDataBean;
import order.control.StorageControl;
import order.control.parsingFile;

public class ReceiveString {
	private StorageControl storagecontrol = new StorageControl();
	private parsingFile ps = new parsingFile();
	private ItemBean bean;

	public void alldata() {
		try {
			String item[] = storagecontrol.getStoreList("*");
			bean = new ItemBean();
			for (int i = 0; i < item.length; i++) {
				String temp = item[i];
				if (temp.equals("�̹���")) {
				} else {
					temp = storagecontrol.Select(temp);

					bean = ps.Parsing(temp);
					ConnectionBean.Message += bean.getCategory()+"|";
				}

			}
//			 "`" + bean.getPrice() + "`"
//				+ bean.getRating() + "`" + bean.getRecommandFlag()
//				+ "`" + bean.getDescription() + "`" + bean.getUrl()
//				+ 
//			System.out.println(ConnectionBean.Message);
//			System.out.println(ConnectionBean.Message);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void optiondata(String category) {
		try {
		String item[] = storagecontrol.getStoreList("*");
		bean = new ItemBean();
		for (int i = 0; i < item.length; i++) {
			String temp = item[i];
			if (temp.equals("�̹���")) {
			} else {
				temp = storagecontrol.Select(temp);
				bean = ps.Parsing(temp);
				if (category.equals(bean.getCategory()) && bean.getRecommandFlag()== true) {
					ConnectionBean.Message += bean.getName() + "`"
							+ bean.getPrice() + "`" + bean.getRating()
							+ "`" + bean.getRecommandFlag() + "`"
							+ bean.getDescription() + "`" + bean.getUrl()
							+ "|";
				}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

	public void categorydata(String category) {
		try {
			String item[] = storagecontrol.getStoreList("*");
			bean = new ItemBean();
			for (int i = 0; i < item.length; i++) {
				String temp = item[i];
				if (temp.equals("�̹���")) {
				} else {
					temp = storagecontrol.Select(temp);
					bean = ps.Parsing(temp);
					if (category.equals(bean.getCategory())) {
						ConnectionBean.Message += bean.getName() + "`"
								+ bean.getPrice() + "`" + bean.getRating()
								+ "`" + bean.getRecommandFlag() + "`"
								+ bean.getDescription() + "`" + bean.getUrl()
								+ "|";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ItemBean FindItem(String Name) {

		try {
			String item[] = storagecontrol.getStoreList("*");
			bean = new ItemBean();
			for (int i = 0; i < item.length; i++) {
				String temp = item[i];
				if (temp.equals("�̹���")) {
				} else {
					temp = storagecontrol.Select(temp);
					bean = ps.Parsing(temp);
					if (Name.equals(bean.getName())) {
						ConnectionBean.Message += bean.getCategory() + "`"
								+ bean.getName() + "`" + bean.getPrice() + "`"
								+ bean.getRating() + "`"
								+ bean.getRecommandFlag() + "`"
								+ bean.getDescription() + "`" + bean.getUrl()
								+ "|";
					}
				}
			}
			return bean;
		} catch (Exception e) {

		}
		return bean;
	}
}
