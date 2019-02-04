package com.machinespray.osfeloader.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SimpleXmlHolder extends XmlHolder {
	public SimpleXmlHolder(String name) {
		super(name);
	}

	@Override
	public void initXMLObjects() throws ParserConfigurationException, SAXException, IOException {
		InputStream xmlReader = tryLoadXML("/" + name);
		defaultSpellDoc = parseXml(xmlReader);
		loadCustomSpells();
	}

	@Override
	protected void addNodeFromXml(File f, String prefix) throws IOException, SAXException, ParserConfigurationException {
		InputStream xmlReader = tryLoadXML(f.getCanonicalPath());
		Element doc = parseXml(xmlReader).getDocumentElement();
		NodeList nodeList = doc.getChildNodes();
		customs.add(doc);
	}


}
