package com.machinespray.osfeloader.handler;

import com.machinespray.osfeloader.node.LinkedDefaultMutableNode;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!e.isAltDown()) {
			super.mouseClicked(e);
			return;
		}

		if (!(e.getComponent() instanceof JTree))
			return;
		JTree tree = (JTree) e.getComponent();

		if (!(tree.getLastSelectedPathComponent() instanceof LinkedDefaultMutableNode))
			return;
		LinkedDefaultMutableNode node = (LinkedDefaultMutableNode) tree.getLastSelectedPathComponent();
		if (node.isEnabled() != null)
			node.setEnabled(!node.isEnabled());
		else
			node.setEnabled(true);
		tree.treeDidChange();
	}
}
