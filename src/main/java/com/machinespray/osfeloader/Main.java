package com.machinespray.osfeloader;

import com.machinespray.osfeloader.frame.DialogDisplaySpells;
import com.machinespray.osfeloader.frame.DialogError;
import com.machinespray.osfeloader.xml.XmlHolder;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Main {
	//TODO(there's definitly a better way to do this logistically)
	public static final XmlHolder spellHolder = new XmlHolder("Spells.xml");
	public static final XmlHolder artifactHolder = new XmlHolder("Artifacts.xml");
	public static final XmlHolder structureHolder = new XmlHolder("Structures.xml");
	private static DialogDisplaySpells dialog;


	public static void main(String[] args) {
		try {
			spellHolder.initXMLObjects();
			artifactHolder.initXMLObjects();
			structureHolder.initXMLObjects();
			startUi();
		} catch (Exception e) {
			handleError(e);
		}
	}

	private static void startUi() {
		dialog = new DialogDisplaySpells();
		dialog.pack();
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
