package com.machinespray.osfeloader.handler;

import com.machinespray.osfeloader.Helper;
import com.machinespray.osfeloader.XmlHolder;
import com.machinespray.osfeloader.node.LinkedDefaultMutableNode;
import com.machinespray.osfeloader.node.action.NodeActionDisable;
import com.machinespray.osfeloader.node.action.NodeActionElement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveHandler {
	public static void doOutput(XmlHolder holder) throws IOException, TransformerException {
		if (holder.nodeToTree.get(null) != null)
			if (!holder.nodeToTree.get(null).isEnabled())
				disableNormals(holder);
		migrateCustoms(holder);
		//Setup the transformer (changes the Document to the .xml file)
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
		//Make the output better spaced
		transformer.setOutputProperty(OutputKeys.INDENT, "no");
		//HTML doesn't support the "short" notation for empty nodes, which OSFE (demo v140) doesn't recognize
		//This is the easiest way to prevent empty tags from being shortened
		//<Description />  ->  <Description></Description>
		transformer.setOutputProperty(OutputKeys.METHOD, "html");
		DOMSource source = new DOMSource(holder.defaultSpellDoc);
		File path = getFileLocation(holder.name);
		FileOutputStream output = new FileOutputStream(path);
		transformer.transform(source, new StreamResult(output));
		output.close();
	}

	private static void disableNormals(XmlHolder holder) {
		NodeList normals = holder.defaultSpellDoc.getChildNodes().item(0).getChildNodes();
		Helper.iterateNodes(normals, new NodeActionDisable());
	}

	//Moves Custom(non vanilla) spells into the main tree to prepare for exporting.
	private static void migrateCustoms(XmlHolder holder) {
		//Iterate through each "Spells.xml"
		for (Element e : holder.customs) {
			NodeActionElement elementList = new NodeActionElement();
			Helper.iterateNodes(e.getChildNodes(), elementList);
			//For every Spell in that Spells.xml, add it to the main tree
			for (Node n : elementList.list) {
				if (n.getNodeName().equals("Group")) {
					migrateGroupedCustoms(holder, n);
					continue;
				}
				LinkedDefaultMutableNode mutableNode = holder.nodeToTree.get(n);
				if (mutableNode == null)
					continue;
				if (!mutableNode.isEnabled())
					continue;
				Node clone = n.cloneNode(true);
				holder.defaultSpellDoc.adoptNode(clone);
				holder.defaultSpellDoc.getChildNodes().item(0).appendChild(clone);
			}
		}
	}

	//Deals with groups of custom spells
	//TODO make this neater, and probably combine with the above function
	private static void migrateGroupedCustoms(XmlHolder holder, Node n) {
		NodeList spells = n.getChildNodes();
		for (int i = 0; i < spells.getLength(); i++) {
			Node spell = spells.item(i);
			if (spell.getAttributes() == null)
				continue;
			if (spell.getNodeName().equals("Group")) {
				migrateGroupedCustoms(holder, spell);
				continue;
			}
			LinkedDefaultMutableNode mutableNode = holder.nodeToTree.get(n);
			if (mutableNode == null)
				continue;
			if (!mutableNode.isEnabled())
				continue;
			Node clone = spell.cloneNode(true);
			holder.defaultSpellDoc.adoptNode(clone);
			holder.defaultSpellDoc.getChildNodes().item(0).appendChild(clone);
		}
		n.getParentNode().removeChild(n);
	}

	//Find the proper location to put the output file, or output it to the running directory if it cannot be found
	public static File getFileLocation(String name) {
		if (System.getProperty("os.name").startsWith("Windows"))
			return new File(System.getProperty("user.home") + "\\AppData\\LocalLow\\Thomas Moon Kang\\One Step From Eden\\DataFiles\\" + name);
		if (System.getProperty("os.name").startsWith("Mac"))
			return new File(System.getProperty("user.home") + "/Library/Application Support/unity.Thomas Moon Kang.OneStepFromEden/DataFiles/" + name);
		return new File(name);
	}
}
