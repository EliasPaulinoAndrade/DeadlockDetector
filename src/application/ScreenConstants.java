package application;

import java.util.Dictionary;
import java.util.Hashtable;

public class ScreenConstants {
	
	@SuppressWarnings("serial")
	public static final Dictionary<String, String> defaultResourcesNames = new Hashtable<String, String>() {{
			put("Teclado", "file:src/application/images/teclado.png");
			put("Impressora", "file:src/application/images/impressora.png");
			put("Mouse", "file:src/application/images/mouse.png");
		}
	};
	
	public static final String defaultProcessImage = "file:src/application/images/processo.png";
}
