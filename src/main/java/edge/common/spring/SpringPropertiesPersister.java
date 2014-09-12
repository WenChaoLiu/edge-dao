package edge.common.spring;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.DefaultPropertiesPersister;

/**
 * 解决spring配置文件placeHolder来自的属性文件在内网中DTD未定义而带来的问题
 * @author: WenChao_Liu@163.com
 * @date: 2014年9月7日
 */
public class SpringPropertiesPersister extends DefaultPropertiesPersister{
	
	private static final Logger log = LogManager.getLogger(SpringPropertiesPersister.class);
	
	private static final String PROPS_DTD_URI = "http://java.sun.com/dtd/properties.dtd";

	private static final String PROPS_DTD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<!-- DTD for properties -->" + "<!ELEMENT properties ( comment?, entry* ) >"
			+ "<!ATTLIST properties" + " version CDATA #FIXED \"1.0\">" + "<!ELEMENT comment (#PCDATA) >" + "<!ELEMENT entry (#PCDATA) >" + "<!ATTLIST entry "
			+ " key CDATA #REQUIRED>";

	@Override
	public void loadFromXml(Properties props, InputStream inputStream) throws IOException {
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			builder.setEntityResolver(new Resolver());
			Document document = builder.parse(inputStream);

			NodeList entities = document.getElementsByTagName("entry");
			for (int i = 0; i < entities.getLength(); i++) {
				Node element = entities.item(i);
				String key = element.getAttributes().getNamedItem("key").getNodeValue();
				String value = element.getTextContent();
				props.setProperty(key, value);
			}
		} catch (ParserConfigurationException e) {
			log.error("read xml config folder cause error", e);
		} catch (SAXException e) {
			log.error("read xml config folder cause error", e);
		}
	}

	private static class Resolver implements EntityResolver {
		public InputSource resolveEntity(String pid, String sid) throws SAXException {
			if (sid.equals(PROPS_DTD_URI)) {
				InputSource is = new InputSource(new StringReader(PROPS_DTD));
				is.setSystemId(PROPS_DTD_URI);
				return is;
			}
			throw new SAXException("Invalid system identifier: " + sid);
		}
	}
}
