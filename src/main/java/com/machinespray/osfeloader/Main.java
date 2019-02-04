package com.machinespray.osfeloader;

import com.machinespray.osfeloader.frame.DialogDisplaySpells;
import com.machinespray.osfeloader.frame.DialogError;
import com.machinespray.osfeloader.xml.XmlHolder;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Main {
	public static final int revision = 1;
	public static final String targetVersion = "v158";
	//TODO(there's definitly a better way to do this logistically)
	public static final XmlHolder[] simpleSolutions = initXml(new String[]{"Spells.xml", "Artifacts.xml", "Structures.xml", "Heroes.xml"});
	private static DialogDisplaySpells dialog;


	public static void main(String[] args) {
		try {
			for (XmlHolder holder : simpleSolutions)
				holder.initXMLObjects();
			startUi();
		} catch (Exception e) {
			handleError(e);
		}
	}

	private static XmlHolder[] initXml(String[] locations) {
		XmlHolder[] temp = new XmlHolder[locations.length];
		for (int i = 0; i < locations.length; i++)
			temp[i] = new XmlHolder(locations[i]);
		return temp;
	}

	private static void startUi() {
		dialog = new DialogDisplaySpells();
		dialog.pack();
		dialog.setTitle(
				String.format("OSFE Mod Loader for %s (Rev:%d)", targetVersion, revision)
		);
		dialog.setVisible(true);
	}

	public static DialogDisplaySpells getDialog() {
		return dialog;
	}

	//Don't just randomly crash, but instead give users an option to copy the stacktrace to their keyboard;
	public static void handleError(Exception e) {
		StringWriter stackTrace = new StringWriter();
		e.printStackTrace(new PrintWriter(stackTrace));
		DialogError error = new DialogError(stackTrace.toString());
		error.pack();
		error.setVisible(true);
		System.exit(0);
	}
}
