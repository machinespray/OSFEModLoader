package com.machinespray.osfeloader.node;

public class LinkedSimpleMutableNode extends LinkedDefaultMutableNode {

	public LinkedSimpleMutableNode(String s) {
		super(s);
	}

	@Override
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
