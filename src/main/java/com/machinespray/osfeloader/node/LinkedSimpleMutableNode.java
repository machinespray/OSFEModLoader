package com.machinespray.osfeloader.node;

import com.machinespray.osfeloader.Main;
import com.machinespray.osfeloader.frame.DialogDisplaySpells;

public class LinkedSimpleMutableNode extends LinkedDefaultMutableNode {

	public LinkedSimpleMutableNode(String s) {
		super(s);
	}

	@Override
	public void setEnabled(Boolean enabled) {
		if(Main.getDialog().getExperimental()||enabled)
		this.enabled = enabled;
	}

}
