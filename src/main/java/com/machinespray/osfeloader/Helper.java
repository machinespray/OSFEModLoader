package com.machinespray.osfeloader;

import com.machinespray.osfeloader.node.action.ExecutableNodeAction;
import org.w3c.dom.NodeList;

public class Helper {
	public static void iterateNodes(NodeList nodeList, ExecutableNodeAction runnable) {
		for (int i = 0; i < nodeList.getLength(); i++)
			runnable.run(nodeList.item(i));
	}

}
