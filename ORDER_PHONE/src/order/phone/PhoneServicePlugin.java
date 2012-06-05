package order.phone;

import order.bean.ConnectionBean;
import order.bean.ItemBean;
import order.bean.StorageDataBean;
import order.control.StorageControl;
import order.phoneBean.DownLoad;

import org.json.JSONArray;
import org.json.JSONException;

import com.phonegap.api.PluginResult;

public class PhoneServicePlugin extends PhoneServiceNet {
	public String AnswerData = "";

	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {
		ReceiveString receiving = new ReceiveString();
		while (true) {
			if (action.equals("receiveData")) {
				receiving.alldata();
				String temp = ConnectionBean.Message;
				ConnectionBean.Message = "";
				return new PluginResult(PluginResult.Status.OK, temp);
			} else if (action.equals("sendData")) {
				try {

					SendData(data.toString());
				} catch (Exception e) {
				}
				return new PluginResult(PluginResult.Status.OK, "크하하하하");
			} else if (action.equals("receiveCategory")) {
				String temp = ConnectionBean.TemporaryData1;
				ConnectionBean.TemporaryData1 = "";
				return new PluginResult(PluginResult.Status.OK, temp);
			} else if (action.equals("CategoryData")) {
				String category = "";
				try {
					category = data.getString(0);
				} catch (JSONException e) {
					return new PluginResult(PluginResult.Status.OK, "잘못됬어");
				}

				receiving.categorydata(category);
				ConnectionBean.TemporaryData1 += category + "|"
						+ ConnectionBean.Message;
				ConnectionBean.Message = "";
				return new PluginResult(PluginResult.Status.OK, true);
			} else if (action.equals("OrderAdd")) {
				String orderadd = "";
				try {
					orderadd = data.getString(0);
					ItemBean bean = receiving.FindItem(orderadd);
					StorageDataBean.ORDERITEM.add(bean);
				} catch (JSONException e) {
					return new PluginResult(PluginResult.Status.OK, "잘못됬어");
				}

			} else if (action.equals("Review")) {

				String review = "";
				try {
					review = data.getString(0);
					ItemBean bean = receiving.FindItem(review);
					bean.setReview(review);
					StorageControl storagecontrol = new StorageControl();
					try {
						storagecontrol.StoreSave(storagecontrol
								.StoreFileInitConfig(bean.getName(),
										bean.getCategory(), bean.getPrice(),
										bean.getCategory(), null));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (JSONException e) {
					return new PluginResult(PluginResult.Status.OK, "잘못됬어");
				}
				StorageDataBean.ORDERITEM.remove(review);
			} else if (action.equals("Remove")) {
				String remove = "";
				try {
					remove = data.getString(0);
					for (int i = 0; i < StorageDataBean.ORDERITEM.size(); i++) {
						if (StorageDataBean.ORDERITEM.get(i).getName()
								.equals(remove))
							StorageDataBean.ORDERITEM.remove(i);
					}
				} catch (Exception e) {
				}
			} else if (action.equals("OptionMenu")) {
				try {
					receiving.optiondata(Integer.parseInt(data.getString(0)));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String temp = ConnectionBean.TemporaryData2;
				ConnectionBean.TemporaryData2 = "";
				return new PluginResult(PluginResult.Status.OK, temp);
			} else
				return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
		}
	}
}
