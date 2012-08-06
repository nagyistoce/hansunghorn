package order.bean;

import java.util.Vector;

public class StorageDataBean {
	public static String STORAGEID="order";
	public static String BEFORE_CATEGORY="<category>";
	public static String AFTER_CATEGORY="</category>";
	public static String BEFORE_NAME="<name>";
	public static String AFTER_NAME="</name>";
	public static String BEFORE_PRICE="<price>";
	public static String AFTER_PRICE="</price>";
	public static String BEFORE_DESCRIPTION="<description>";
	public static String AFTER_DESCRIPTION="</description>";
	public static String BEFORE_RATING="<rating>";
	public static String AFTER_RATING="</rating>";
	public static String BEFORE_REVIEW="<review>";
	public static String AFTER_REVIEW="</review>";
	public static String BEFORE_BITMAP="<bitmap>";
	public static String AFTER_BITMAP="</bitmap>";
	public static String BEFORE_RECOMMAND="<recommand>";
	public static String AFTER_RECOMMAND="</recommand>";
	public static String SEPARATOR="|";
	public static Vector<ItemBean> ORDERITEM=new Vector<ItemBean>();
}
