package application;

import java.util.Dictionary;
import java.util.Hashtable;

public class ScreenConstants {
	
	@SuppressWarnings("serial")
	public static final Dictionary<String, String> defaultResourcesNames = new Hashtable<String, String>() {{
			put("Teclado", "file:src/application/images/keyboard.png");
			put("Impressora", "file:src/application/images/printer.png");
			put("Mouse", "file:src/application/images/mouse.png");
			put("Arquivo", "file:src/application/images/archive.png");
			put("Pendrive", "file:src/application/images/pendrive.png");
			put("CD", "file:src/application/images/cd.png");
			put("Disquete", "file:src/application/images/floopy_disk.png");
			put("Controle", "file:src/application/images/game_pad.png");
			put("Gravador", "file:src/application/images/recorder.png");
		}
	};
	
	public static final String defaultProcessImage = "file:src/application/images/process.png";
}
