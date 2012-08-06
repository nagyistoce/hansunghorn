package sod.common;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;


import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * 
 * @author MB
 *
 * Write & read object via stream
 */
public class Serializer {
	final static String Tag_Packet 		= "Packet";
	final static String Tag_Item 		= "Item";
	final static String Name_Signiture 	= "signiture";
	final static String Name_Type 	= "type";

	byte[] readbuf;
	
	protected String toBase64(byte[] src){
		return new String(Base64.encode(src));
	}
	
	protected byte[] fromBase64(String src){
		return Base64.decode(src);
	}
	
	public Serializer(){
		readbuf = new byte[Transceiver.TransferUnit];
	}
	
	/**
	 * ��Ŷ ��ü�� ���޹޾� ����ȭ�� �Ŀ� OutputStream�� �̿��Ͽ� OutputStream�� ����.
	 * ����ȭ�� Json�� �̿��Ͽ� �̷������.
	 * �۽��ϴ� ������ ����ҿ� �������� ȣ��Ǵ� �޼ҵ��̴�.
	 * @param dest 
	 * ��ü�� ����ȭ�� �Ŀ� ������ ��Ʈ��
	 * @param pkt 
	 * ����ȭ�� �ϱ����� ��ü
	 * @throws IllegalArgumentException
	 * �Ѿ�� ��Ŷ ��ü�� NULL�̰ų� ��Ʈ���� NULL�̸� IllegalArgumentException�� ������.
	 */
	public void serialize(OutputStream dest, Packet pkt) throws IllegalArgumentException{
		if(dest == null || pkt == null)
			throw new IllegalArgumentException();
		
		Document doc = XmlBuilder.createDoc();
		Element root = doc.createElement(Tag_Packet);
		Element item;
		
		root.setAttribute(Name_Signiture, Integer.toString(pkt.signiture));
		
		while(pkt.getElementCount() > 0)
		{
			String type = pkt.getTopElementType();
			item = doc.createElement(Tag_Item);
			item.setAttribute(Name_Type, type);
			
			Object data = pkt.pop();
			if(type != Packet.DataType_ByteArray)
				item.setTextContent(data.toString());
			else
				item.setTextContent(toBase64((byte[])data));
			
			root.appendChild(item);
		}
		doc.appendChild(root);
		

		try {        
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			XmlBuilder.getTransformer().transform(new DOMSource(doc), result);
			
			String xml = sw.toString();
			dest.write(xml.getBytes("UTF-8"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ������ȭ�� �ϱ����� InputStream�� �Ѱ��ָ� ���� �Ѱ��� ��Ŷ ��ü�� �̿��Ͽ� ��Ŷ ��ü�� ������ȭ�� �Ѵ�.
	 * @param src
	 * ������ȭ�� �Ϸ��� �ϴ� ��Ʈ��
	 * @param pkt
	 * ������ȭ�� ��, ��ü�� ������� ��Ŷ ��ü
	 * @throws IllegalArgumentException
	 * �Ѿ�� ��Ŷ ��ü�� NULL�̰ų� ��Ʈ���� NULL�̸� IllegalArgumentException ������.
	 */
	public void deserialize(InputStream src, Packet pkt) throws IllegalArgumentException{
		if(src == null || pkt == null)
			throw new IllegalArgumentException();

//		int length;
//		String xml = null;
//		
//		try {
//			length = src.read(readbuf);
//			xml = new String(readbuf, 0, length, "UTF-8");
//		} catch (Exception ex) {
//			// TODO Auto-generated catch block
//			ex.printStackTrace();
//		}
		
		Document doc;
		try {
			doc = XmlBuilder.parse(src);
		} catch (Exception ex) {
			return;
		}
		if (doc == null)
			return;
		
		
		Element root = (Element)doc.getElementsByTagName(Tag_Packet).item(0);		
		NamedNodeMap attrs = root.getAttributes();
		Node sign = attrs.getNamedItem(Name_Signiture);
		pkt.signiture = Integer.parseInt(sign.getTextContent());
		
		NodeList nodes = doc.getElementsByTagName(Tag_Item);
		
		int count = nodes.getLength();
		for(int u = 0; u < count; ++u)
		{
			Element el = (Element)nodes.item(u);
			String type = el.getAttribute(Name_Type);
			String content = el.getTextContent();
			
			Object obj;			
			if(type.equals(Packet.DataType_Int))
				obj = Integer.parseInt(content);
//			else if(type.equals(Packet.DataType_Long))
//				obj = Long.parseLong(content);
			else if(type.equals(Packet.DataType_Float))
				obj = Float.parseFloat(content);
			else if(type.equals(Packet.DataType_Double))
				obj = Double.parseDouble(content);
			else if(type.equals(Packet.DataType_ByteArray))
				obj = fromBase64(content);
			else
				obj = content;
			
			pkt.push(obj);		
		}
		
	}
}
