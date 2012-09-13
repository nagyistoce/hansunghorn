package order.phone;

import order.bean.ConnectionBean;
import order.bean.ItemBean;
import order.bean.StorageDataBean;
import order.control.StorageControl;
import order.phoneBean.DownLoad;

import org.json.JSONArray;
import org.json.JSONException;

import sod.common.Packet;

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
				return new PluginResult(PluginResult.Status.OK, "ũ��������");
			}
			
			
			
			
			
			
			
			else if (action.equals("receiveCategory")) {
				String temp = ConnectionBean.TemporaryData1;
				ConnectionBean.TemporaryData1 = "";
				return new PluginResult(PluginResult.Status.OK, temp);
			}
			
			
			
			
			
			
			
			else if (action.equals("CategoryData")) {
				String category = "";
				ConnectionBean.TemporaryData1="";
				try {
					category = data.getString(0);
				} catch (JSONException e) {
					return new PluginResult(PluginResult.Status.OK, "�߸����");
				}

				receiving.categorydata(category);
				ConnectionBean.TemporaryData1 += category + "|"
						+ ConnectionBean.Message;
				ConnectionBean.Message = "";
				return new PluginResult(PluginResult.Status.OK, true);
			} 
			
			
			
			
			
			
			
			
			
			
			
			else if (action.equals("OrderAdd")) {
				String orderadd = "";
				try {
					orderadd = data.getString(0);
					ItemBean bean = receiving.FindItem(orderadd);
					packet = new Packet();
]
					packet.push(data);		
					ConnectionBean.client.send(packet);
					//StorageDataBean.ORDERITEM.add(bean);
				} catch (JSONException e) {
					return new PluginResult(PluginResult.Status.OK, "�߸����");
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
					return new PluginResult(PluginResult.Status.OK, "�߸����");
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
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			else if (action.equals("OptionMenu")) {
				String temp = ConnectionBean.TemporaryData1;
				String category = "";
				try {
					category = data.getString(0);
				} catch (JSONException e) {
					return new PluginResult(PluginResult.Status.OK, "�߸����");
				}
				receiving.optiondata(category);
				ConnectionBean.TemporaryData1 = category + "|"
						+ ConnectionBean.Message;
				ConnectionBean.Message = "";
				return new PluginResult(PluginResult.Status.OK, ConnectionBean.TemporaryData1);
			} else
				return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
		}
	}
}
