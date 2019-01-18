package com.machinespray.osfeloader.node.action;

import org.w3c.dom.Node;

import java.util.regex.Pattern;

public abstract class ExecutableNodeAction {
	static final Pattern getID = Pattern.compile("itemID=\"(.+)\"");

	public static ExecutableNodeAction print() {
		return new ExecutableNodeAction() {
			@Override
			public void run(Node node) {
				System.out.println(node.getNodeName());
			}
		};
	}

	public abstract void run(Node node);
}
