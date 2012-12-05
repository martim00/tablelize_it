package tablelize;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class TableParser {

	public Hashtable<String, ArrayList<Table>> LoadTables(String contents, String originFile) throws Exception {

		Hashtable<String, ArrayList<Table>> tables = new Hashtable<String, ArrayList<Table>>();
		
		int order = 0;
		String tableContents = new String();		
		
		try (Scanner scanner =  new Scanner(contents)) {
	      while (scanner.hasNextLine()) {
	        //process each line in some way
	        String line = scanner.nextLine();
	        
	        if (!line.isEmpty()) {
	        	
	        	tableContents+= line;
	        	tableContents+= "\n";
	        	
	        } else if (!tableContents.isEmpty()) {
	        	
	        	Table loadedTable = LoadTable(tableContents);
				loadedTable.setPositionInFile(order++);
				loadedTable.setOriginFile(originFile);
				tables.get(loadedTable.tableName()).add(loadedTable);
				tableContents = "";
	        }
	      }
	    }
		
		if (!contents.isEmpty() && tables.isEmpty())
			throw new Exception("There are contents to read but we cant parse a table. Maybe you forgot to put two empty lines at the end of the file");
		
		return tables;
	}

	private Table LoadTable(String tableContents) {
		
//		Scanner scanner = new Scanner(tableContents);
//		
//		Table table = parseTableName(scanner.nextLine());
//		String header = scanner.nextLine();
//		
//		
//		std::stringstream stream(contents.c_str());
//
//		Table *table= parseTableName(getLine(stream));
//
//		std::string header= getLine(stream);
//		std::vector<std::string> fields= tokenize(header, '|');	
//
//		while (!stream.eof())
//		{
//			std::string line= getLine(stream);
//			if (line.empty()) continue;
//			std::vector<std::string> lineParams= tokenize(line, '|');
//			addParametersInTable(*table, fields, lineParams); 
//		}	   
//		return table;    

	}

}
