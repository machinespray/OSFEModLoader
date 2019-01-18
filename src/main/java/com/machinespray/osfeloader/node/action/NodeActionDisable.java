package com.machinespray.osfeloader.node.action;

import com.machinespray.osfeloader.Helper;
import org.w3c.dom.Node;

public class NodeActionDisable extends ExecutableNodeAction {
	@Override
	public void run(Node node) {
		if(node.getAttributes()==null)
			return;
		NodeActionFindTag findTag = new NodeActionFindTag();
		Helper.iterateNodes(node.getChildNodes(),findTag);
		if(findTag.tag==null)
			return;
		String tags = "";
		findTag.tag.setTextContent(tags);
	}
	private class NodeActionFindTag extends ExecutableNodeAction{
		public Node tag;
		@Override
		public void run(Node node) {
			if(node.getNodeName().equals("Tags"))
				tag = node;
		}
	}
}
