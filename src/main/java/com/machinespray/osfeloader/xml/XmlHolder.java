package com.machinespray.osfeloader.xml;

import com.machinespray.osfeloader.Helper;
import com.machinespray.osfeloader.Main;
import com.machinespray.osfeloader.node.LinkedDefaultMutableNode;
import com.machinespray.osfeloader.node.LinkedSimpleMutableNode;
import com.machinespray.osfeloader.node.action.NodeActionList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlHolder {
	private static final Pattern groupPattern = Pattern.compile("name=\"(.+)\"");
	public final String name;
	public Document defaultSpellDoc;
	public ArrayList<Element> customs = new ArrayList<>();
	public HashMap<Node, LinkedDefaultMutableNode> nodeToTree = new HashMap<>();
	private DefaultMutableTreeNode defaultMutableTreeNode;

	public XmlHolder(String name) {
		this.name = name;
	}

	public void initXMLObjects() throws ParserConfigurationException, SAXException, IOException {
		InputStream xmlReader = tryLoadXML("/" + name);
		defaultSpellDoc = parseXml(xmlReader);
		NodeActionList defaultSpells = new NodeActionList();
		Helper.iterateNodes(defaultSpellDoc.getDocumentElement().getChildNodes(), defaultSpells);
		initiateTree(defaultSpells);
		loadCustomSpells();
	}

	protected InputStream tryLoadXML(String path) throws FileNotFoundException {
		File f = new File(path);
		if (!f.isDirectory() && f.exists())
			return new FileInputStream(f);
		return Main.class.getResourceAsStream(path);
	}

	protected Document parseXml(InputStream reader) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(reader);
		doc.getDocumentElement().normalize();
		return doc;
	}

	protected void initiateTree(NodeActionList defaultSpells) {
		DefaultMutableTreeNode defaultNode = new DefaultMutableTreeNode("Categories");
		//TODO change to LinkedSimpleMutableNode and make softlocks not possible
		LinkedSimpleMutableNode vanilla = new LinkedSimpleMutableNode("Vanilla");
		//nodeToTree.put(null,vanilla);
		defaultNode.add(vanilla);
		for (String s : defaultSpells.list)
			vanilla.add(new DefaultMutableTreeNode(s));
		defaultMutableTreeNode = defaultNode;
	}

	public JTree getTree() {
		return new JTree(defaultMutableTreeNode);
	}

	protected void loadCustomSpells() throws IOException, SAXException, ParserConfigurationException {
		File modsFolder = new File("./Mods");
		if (modsFolder.isDirectory())
			for (File f : Objects.requireNonNull(modsFolder.listFiles()))
				if (f.isDirectory())
					checkCategory(f);
	}

	private void checkCategory(File folder) throws ParserConfigurationException, SAXException, IOException {
		for (File f : Objects.requireNonNull(folder.listFiles()))
			if (!f.isDirectory())
				if (f.getName().endsWith(name))
					addNodeFromXml(f, folder.getName());

	}

	protected void addNodeFromXml(File f, String prefix) throws IOException, SAXException, ParserConfigurationException {
		InputStream xmlReader = tryLoadXML(f.getCanonicalPath());
		Element doc = parseXml(xmlReader).getDocumentElement();
		NodeList nodeList = doc.getChildNodes();
		DefaultMutableTreeNode classNode = new LinkedDefaultMutableNode(prefix + ":" + f.getName());
		addSpellsToNode(nodeList, classNode);
		for (int i = 0; i < nodeList.getLength(); i++)
			if (nodeList.item(i).getNodeName().equals("Group")) {
				Matcher matcher = groupPattern.matcher(nodeList.item(i).getAttributes().item(0).toString());
				if (!matcher.find())
					continue;
				DefaultMutableTreeNode groupNode = new LinkedDefaultMutableNode(matcher.group(1));
				addSpellsToNode(nodeList.item(i).getChildNodes(), groupNode);
				classNode.add(groupNode);
			}
		defaultMutableTreeNode.add(classNode);
		customs.add(doc);
	}

	private void addSpellsToNode(NodeList nodeList, DefaultMutableTreeNode main) {
		NodeActionList spells = new NodeActionList();
		Helper.iterateNodes(nodeList, spells);
		for (int i = 0; i < spells.list.size(); i++) {
			if (!spells.listN.get(i).hasAttributes())
				continue;
			LinkedDefaultMutableNode treeNode = new LinkedDefaultMutableNode(spells.list.get(i), spells.listN.get(i));
			main.add(treeNode);
			nodeToTree.put(spells.listN.get(i), treeNode);
		}
	}

	@Override
	public String toString() {
		return this.name;
	}


}
