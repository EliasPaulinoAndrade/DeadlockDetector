package application;

import java.util.Dictionary;
import java.util.Hashtable;

public class ScreenConstants {
	
	@SuppressWarnings("serial")
	public static final Dictionary<String, String> defaultResourcesNames = new Hashtable<String, String>() {{
			put("Teclado", "/application/images/keyboard.png");
			put("Impressora", "/application/images/printer.png");
			put("Mouse", "/application/images/mouse.png");
			put("Arquivo", "/application/images/archive.png");
			put("Pendrive", "/application/images/pendrive.png");
			put("CD", "/application/images/cd.png");
			put("Disquete", "/application/images/floopy_disk.png");
			put("Controle", "/application/images/game_pad.png");
			put("Gravador", "/application/images/recorder.png");
		}
	};
	
	public static final String defaultProcessImage = "/application/images/process.png";
}
