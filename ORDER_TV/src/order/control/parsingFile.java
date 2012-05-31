package order.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import order.bean.ItemBean;
import order.bean.StorageDataBean;

import android.widget.MultiAutoCompleteTextView.Tokenizer;

public class parsingFile {
	StorageControl store;
	BufferedReader in;
	String temp;
	int state = 0;
    int startIndex;
    int endIndex;
    
	public ItemBean Parsing(File file)throws Exception
	{
		if(file==null)
			return null;
	in =  new BufferedReader(new FileReader(file));
	ItemBean bean=new ItemBean();
	    while((temp = in.readLine()) != null) {
	    	switch(state)
	    	{
	    	case 0:
	    		if(temp.contains(StorageDataBean.BEFORE_CATEGORY))
	    		{
	    			startIndex=temp.indexOf(">");
	    			endIndex=temp.indexOf(StorageDataBean.AFTER_CATEGORY);
	    			bean.setCategory(temp.substring(startIndex+1,endIndex));
		    		state=1;
	    		}
	    		break;
	    	case 1:
	    		if(temp.contains(StorageDataBean.BEFORE_NAME))
	    		{
	    			startIndex=temp.indexOf(">");
	    			endIndex=temp.indexOf(StorageDataBean.AFTER_NAME);
	    			bean.setName(temp.substring(startIndex+1,endIndex));
		    		state=2;
	    		}
	    		break;
	    	case 2:
	    		if(temp.contains(StorageDataBean.BEFORE_PRICE))
	    		{
	    			startIndex=temp.indexOf(">");
	    			endIndex=temp.indexOf(StorageDataBean.AFTER_PRICE);
	    			bean.setPrice(temp.substring(startIndex+1,endIndex));
		    		state=3;
	    		}
	    		break;
	    	case 3:
	    		if(temp.contains(StorageDataBean.BEFORE_DESCRIPTION))
	    		{
	    			startIndex=temp.indexOf(">");
	    			endIndex=temp.indexOf(StorageDataBean.AFTER_DESCRIPTION);
	    			bean.setDescription(temp.substring(startIndex+1,endIndex));
	    			state=0;
	    		}
	    		break;
	    	default:
	    			break;
	    	}
	    }
	    in.close();
	   return bean;
	}
}
