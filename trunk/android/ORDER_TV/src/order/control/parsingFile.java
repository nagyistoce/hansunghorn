package order.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

import order.bean.ItemBean;
import order.bean.StorageDataBean;

public class parsingFile {
	//StorageControl store;
	BufferedReader in;
	String temp;
	boolean recommend=false;
    int startIndex;
    int endIndex;
    
	public ItemBean Parsing(String file)throws Exception
	{
		if(file==null)
			return null;
	in =  new BufferedReader(new FileReader(file));
	ItemBean bean=new ItemBean();
	int state = 0;
	    while((temp = in.readLine()) != null) {
	    	System.out.println(temp);
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
	    			state=4;
	    		}
	    		break;
	    	case 4:
	    		if(temp.contains(StorageDataBean.BEFORE_RATING))
	    		{
	    			startIndex=temp.indexOf(">");
	    			endIndex=temp.indexOf(StorageDataBean.AFTER_RATING);
	    			bean.setRating(temp.substring(startIndex+1,endIndex));
	    			state=5;
	    		}
	    		break;
	    	case 5:
	    		if(temp.contains(StorageDataBean.BEFORE_RECOMMAND))
	    		{
	    			startIndex=temp.indexOf(">");
	    			endIndex=temp.indexOf(StorageDataBean.AFTER_RECOMMAND);
	    			if(temp.substring(startIndex+1,endIndex).equals("true"));
	    			{
	    				 recommend=true;
	    			}
	    			bean.setRecommandFlag(recommend);
	    			state=6;
	    		}
	    		break;
	    	case 6:
	    		if(temp.contains(StorageDataBean.BEFORE_REVIEW))
	    		{
	    			startIndex=temp.indexOf(">");
	    			endIndex=temp.indexOf(StorageDataBean.AFTER_REVIEW);
	    			bean.setRating(temp.substring(startIndex+1,endIndex));
	    			state=7;
	    		}
	    		break;
	    	case 7:
	    		if(temp.contains(StorageDataBean.BEFORE_BITMAP))
	    		{
	    			startIndex=temp.indexOf(">");
	    			endIndex=temp.indexOf(StorageDataBean.AFTER_BITMAP);
	    			bean.setUrl(temp.substring(startIndex+1,endIndex));
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
