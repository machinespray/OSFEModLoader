package com.machinespray.osfeloader.node.action;

import org.w3c.dom.Node;

import java.util.ArrayList;

public class NodeActionElement extends ExecutableNodeAction {
	public ArrayList<Node> list = new ArrayList();

	@Override
	public void run(Node node) {
		if (node.getAttributes() == null)
			return;
		list.add(node);
	}
}
