package com.machinespray.osfeloader.handler;

import java.io.*;
import java.util.Objects;

public class LuaHandler {
	public void migrateLua() throws IOException {
		File luaLocation = SaveHandler.getFileLocation("LibL.txt");
		FileOutputStream output = new FileOutputStream(luaLocation);
		InputStream defaultLua = getClass().getClassLoader().getResourceAsStream("LibL.txt");
		writeToStream(defaultLua, output);
		defaultLua.close();
		File modsFolder = new File("./Mods");
		if (modsFolder.isDirectory())
			for (File f : Objects.requireNonNull(modsFolder.listFiles()))
				if (f.isDirectory())
					checkCategory(f, output);
		output.close();
	}

	private void checkCategory(File folder, OutputStream destination) throws IOException {
		PrintStream stringWriter = new PrintStream(destination, true);
		for (File f : Objects.requireNonNull(folder.listFiles())) {
			if (f.isDirectory() || !f.getName().endsWith("L.txt"))
				continue;
			stringWriter.println(String.format("--%s", f.getName()));
			FileInputStream content = new FileInputStream(f);
			writeToStream(content, stringWriter);
			content.close();
			stringWriter.print("\n");
		}
		stringWriter.close();
	}

	private void writeToStream(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[1024];
		int length;
		while ((length = input.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}
	}


}
