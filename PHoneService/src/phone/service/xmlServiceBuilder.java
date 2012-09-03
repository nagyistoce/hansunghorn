package phone.service;

import java.io.FileWriter;
import java.io.PrintWriter;

import phone.bean.Builder;

public class xmlServiceBuilder implements Builder {
	private Builder builder;
	private String filename;
	private PrintWriter writer;
	private String node="";
	private String[] childNode={};
    
	public xmlServiceBuilder(Builder builder) {		
        this.builder = builder;
    }
   	public void makeTitle(String title) {		// ���� �̸�����
		// TODO Auto-generated method stub
			this.filename=title;
		try{
			  writer = new PrintWriter(new FileWriter(filename));
			  writer.println("<"+filename+">");
		}catch(Exception e)
		{
			  e.printStackTrace();
		}
	}

	public void makeNode(String str) {			// ���� ���
		// TODO Auto-generated method stub
		this.node=str;
		writer.println("\t<"+node+">");
	}

	public void makeChildNode(String[] items) {		// ������ ��� (���ϵ� ���)
		// TODO Auto-generated method stub
		for(int i=0;i<items.length;i++)
		{
		writer.println("\t\t<items>"+items[i]+"</items>");
		}
		writer.println("</"+node+">");
	}

	public Object getResult() {						// ���� ���� �ϼ�
		// TODO Auto-generated method stub
		writer.println("</"+filename+">");
		return null;
	}

	public void getMakeChildCount() {
		// TODO Auto-generated method stub
		
	}

	public void getMakeNodeCount() {
		// TODO Auto-generated method stub
		
	}                                        

}
