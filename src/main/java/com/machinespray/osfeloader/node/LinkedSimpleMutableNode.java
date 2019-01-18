package com.machinespray.osfeloader.node;

import org.w3c.dom.Node;

import javax.swing.tree.DefaultMutableTreeNode;

public class LinkedSimpleMutableNode extends LinkedDefaultMutableNode {

	public LinkedSimpleMutableNode(String s) {
		super(s);
	}
	@Override
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
