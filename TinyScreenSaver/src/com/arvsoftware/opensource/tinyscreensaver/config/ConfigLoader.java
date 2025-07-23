package com.arvsoftware.opensource.tinyscreensaver.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConfigLoader {
	
	public static Map<String, String> loadConfigFile(String filepath) {
		HashMap<String, String> keyValueMap = new HashMap<String, String>();
		
		try {
			File file = new File(filepath);
			try (Scanner scan = new Scanner(file)) {
				while (scan.hasNextLine()) {
					String[] pair = scan.nextLine().split("=");
					keyValueMap.put(pair[0], pair[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return keyValueMap;
	}
	
}
