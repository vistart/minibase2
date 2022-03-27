package ed.inf.adbs.minibase;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class CatalogSingleton {
	private static CatalogSingleton INSTANCE;
	private HashMap<String, ArrayList<String>> schemas;
	private HashMap<String, String> csvDirs;
	
	private CatalogSingleton(String databaseDir) {
		this.schemas = this.parseSchemas(databaseDir + "\\schema.txt");
		this.csvDirs = this.parseCSVdir(databaseDir + "\\files");
	}
	
	//parse schemas as hashmap from the given file dir
	private HashMap<String, ArrayList<String>> parseSchemas(String schemaDir) {
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		try {
			File f = new File(schemaDir);
			try (Scanner myReader = new Scanner(f)) {
				while(myReader.hasNextLine()) {
					String line = myReader.nextLine();
					String[] cols = line.split(" ");
					ArrayList<String> types = new ArrayList<String>();
					for(int i = 1; i<cols.length; i++) {
						types.add(cols[i]);
					}
					map.put(cols[0], types);
				}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//parse relation path as hashmap from the given file dir
	private HashMap<String, String> parseCSVdir(String databaseDir) {
		HashMap<String,String> map = new HashMap<String,String>();
		File files = new File(databaseDir);
		String[] dirs = files.list();
		for(int i=0; i<dirs.length; i++) {
			String s = dirs[i];
			int idx = s.indexOf(".csv");
			if(idx >= 0) {
				map.put(s.substring(0, idx), databaseDir+ "\\" +s);
			}
		}
		return map;
	}
	
	public static CatalogSingleton getInstance(String dbDir){
		if (INSTANCE == null) {  
			INSTANCE = new CatalogSingleton(dbDir);  
        }  
        return INSTANCE; 
    }
	public HashMap<String, ArrayList<String>> getSchemas(){
		return this.schemas;
	}
	public HashMap<String, String> getCSVdir(){
		return this.csvDirs;
	}
	
	
	/////////////////////Delete before submit//////////////////////////
	public static void main(String[] args) {
//		String dir = ".\\data\\evaluation\\db\\schema.txt";
//		try {
//			File f = new File(dir);
//			Scanner myReader = new Scanner(f);
//			while(myReader.hasNextLine()) {
//				String line = myReader.nextLine();
//				String[] cols = line.split(" ");
//				ArrayList<String> types = new ArrayList<String>();
//				for(int i = 1; i<cols.length; i++) {
//					types.add(cols[i]);
//				}
//				System.out.println(cols[0]);
//				System.out.println(types.toString());
//			}
//		} catch(FileNotFoundException e) {
//			e.printStackTrace();
//		}
		
		//////////////////////////////////////////////
		String dir = ".\\data\\evaluation\\db\\files";
		File files = new File(dir);
		String[] dirs = files.list();
		for(int i=0; i<dirs.length; i++) {
			String s = dirs[i];
			int idx = s.indexOf(".csv");
			if(idx >= 0) {
				System.out.println(s.substring(0,idx));
				System.out.println(dir+"\\"+s);
			}
		}
	}
	/////////////////////Delete before submit//////////////////////////
}
