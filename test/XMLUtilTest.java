import static org.junit.Assert.*;

import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.chat.util.XMLUtil;


public class XMLUtilTest
{
	
	private SAXReader saxReader = new SAXReader();
	
	
	@Test
	public  void testConstructLoginXML()
	{
		try
		{
			String xml = XMLUtil.constructLoginXML("zhangsan");

			Document document = saxReader.read(new StringReader(xml));
			
			Element root = document.getRootElement();
			
			String rootName = root.getName();
			
			Element typeElement = root.element("type");
			
			String typeText = typeElement.getText();
			
			Element userElement = root.element("user");
			
			String userText = userElement.getText();
			
			assertEquals("message", rootName);
			assertEquals("1", typeText);
			assertEquals("zhangsan", userText);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testExtractUsername()
	{
		Element root = DocumentHelper.createElement("message");
		
		Document document = DocumentHelper.createDocument(root);
		
		root.addElement("user").addText("zhangsan");
		
		String xml = document.asXML();
		
		String username = XMLUtil.extractUsername(xml);
		
		assertEquals("zhangsan", username);
		
	}
	
	
}
