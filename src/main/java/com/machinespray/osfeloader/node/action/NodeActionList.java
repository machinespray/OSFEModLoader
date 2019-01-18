package com.machinespray.osfeloader.node.action;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class NodeActionList extends ExecutableNodeAction {
	public ArrayList<String> list = new ArrayList();
	public ArrayList<Node> listN = new ArrayList<>();

	@Override
	public void run(Node node) {
		if (node.getAttributes() == null)
			return;
		String s = node.getAttributes().item(0).toString();
		Matcher matcher = getID.matcher(s);
		if (matcher.find()) {
			list.add(matcher.group(1));
			listN.add(node);
		}
	}
}
