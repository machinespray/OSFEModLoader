package com.machinespray.osfeloader;

import com.machinespray.osfeloader.frame.DialogDisplaySpells;
import com.machinespray.osfeloader.frame.DialogError;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Main {
	public static final xmlHolder spellHolder = new xmlHolder("Spells.xml");
	public static final xmlHolder artifactHolder = new xmlHolder("Artifacts.xml");

	public static void main(String[] args) {
		try {
			spellHolder.initXMLObjects();
			artifactHolder.initXMLObjects();
			startUi();
		} catch (Exception e) {
			handleError(e);
		}
	}

	private static void startUi() {
		DialogDisplaySpells spellDisplay = new DialogDisplaySpells();
		spellDisplay.pack();
		spellDisplay.setVisible(true);
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
