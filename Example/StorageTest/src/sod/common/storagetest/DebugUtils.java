package sod.common.storagetest;


public class DebugUtils {
	@SuppressWarnings({ "unused", "null" })
	public static void throwException(){
		Object o = null;
		int val = o.hashCode();
		return;
	}
}
