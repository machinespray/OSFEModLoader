package com.machinespray.osfeloader.node;

import org.w3c.dom.Node;

import javax.swing.tree.DefaultMutableTreeNode;

public class LinkedDefaultMutableNode extends DefaultMutableTreeNode {
	private final Node n;
	protected Boolean enabled = true;

	public LinkedDefaultMutableNode(String s) {
		super(s);
		this.n = null;
	}

	public LinkedDefaultMutableNode(String s, Node n) {
		super(s);
		this.n = n;
		this.allowsChildren = false;
	}

	public Node getNode() {
		return n;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		setEnabled(enabled, false);
	}

	private void setEnabled(Boolean enabled, boolean fromParent) {
		//Set the value of enabled
		this.enabled = enabled;
		//If this object is a "folder" node, set it's children to match
		if (this.allowsChildren && enabled != null)
			for (Object child : children)
				((LinkedDefaultMutableNode) child).setEnabled(enabled);
		if (!fromParent)
			if (this.getParent() instanceof LinkedDefaultMutableNode)
				((LinkedDefaultMutableNode) this.getParent()).updateEnabled();

	}

	private void updateEnabled() {
		if (!allowsChildren)
			return;
		if (children == null)
			return;
		Boolean test = ((LinkedDefaultMutableNode) children.get(0)).isEnabled();
		for (int i = 1; i < children.size(); i++)
			if (((LinkedDefaultMutableNode) children.get(i)).isEnabled() != null) {
				if (!((LinkedDefaultMutableNode) children.get(i)).isEnabled().equals(test))
					test = null;
			} else {
				test = null;
				break;
			}
		enabled = test;
		if (this.getParent() instanceof LinkedDefaultMutableNode)
			((LinkedDefaultMutableNode) this.getParent()).updateEnabled();
	}

	@Override
	public String toString() {
		if (!allowsChildren)
			return (isEnabled() ? "+" : "-") + super.toString();
		if (enabled != null)
			return (isEnabled() ? "+" : "-") + super.toString();
		return "~" + super.toString();
	}

}
