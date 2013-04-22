package tablelize;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class TableParser {
	
	public Table LoadTable(String tableContents) {

		Scanner scanner = new Scanner(tableContents);

		Table table = this.parseTableName(scanner.nextLine());
		String header = scanner.nextLine();
		List<String> fields = this.tokenize(header, "\\|");

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.isEmpty()) continue;

			List<String> lineParams = this.tokenize(line, "\\|");
			this.addTableRow(table, fields, lineParams);
		}

		return table;
	}

	public Hashtable<String, ArrayList<Table>> LoadTables(String contents, String originFile) throws Exception {

		Hashtable<String, ArrayList<Table>> tables = new Hashtable<String, ArrayList<Table>>();

		int order = 0;
		String tableContents = new String();		
		Scanner scanner =  new Scanner(contents);
		do {
			//process each line in some way
			String line = scanner.nextLine();

			if (!line.isEmpty()) {

				tableContents+= line;
				tableContents+= "\n";

			} 
			
			if (!tableContents.isEmpty() && (line.isEmpty() || !scanner.hasNextLine())) {

				Table loadedTable = LoadTable(tableContents);
				loadedTable.setPositionInFile(order++);
				loadedTable.setOriginFile(originFile);
				
				String tableName = loadedTable.tableName();
				
				if (!tables.containsKey(tableName))
					tables.put(tableName, new ArrayList<Table>());
				
				tables.get(tableName).add(loadedTable);
				tableContents = "";
			}
		} while (scanner.hasNextLine());

		return tables;
	}

	/**
	 *  Tokenize a string with the separator passed.
	 *
	 *  @return std::vector<std::string>
	 *  @param  std::string content
	 *  @param  char separator
	 */
	private List<String> tokenize(String content, String separator)
	{
		String[] tokens = content.split(separator);
		ArrayList<String> tokensAsList = new ArrayList<String>(Arrays.asList(tokens));
		tokensAsList.remove(0); //TODO:  removing the first element, because split is returning an empty string.
		return tokensAsList;
//		ArrayList<String> new ArrayList<String>();
		
//		List<String> tokens = new ArrayList<String>();
//		Scanner scanner = new Scanner(content);
//		scanner.useDelimiter(separator);
//
//		while (scanner.hasNext()) {
//			tokens.add(scanner.next());
//		}
//
//		return tokens;
	}

	private void pushTableArgs( List<String> fields, Table table ) 
	{
		if (fields.size() < 2) return;
		
		for (int i = 1; i < fields.size(); i++) {
			
			table.pushTableArg(fields.get(i));
		}
	}


	private Table parseTableName(String line)
	{
		List<String> tokens= tokenize(line, "\\|");
		assert(!tokens.isEmpty());

		String tableName= tokens.get(0);

		Table table= new Table(tableName);

		pushTableArgs(tokens, table);

		return table;

	}
	private void addTableRow( Table table, List<String> fieldNames, 
			List<String> fieldValues ) 
	{
		assert(fieldNames.size() == fieldValues.size());
		Row row = new Row();
		
		for (int i = 0; i < fieldNames.size(); i++) {
			row.addField(fieldNames.get(i), fieldValues.get(i));
		}
		
		table.addRow(row);
	}

	public Hashtable<String, ArrayList<Table>> LoadTablesFromFile(String filename) throws Exception {
		String content = new Scanner( new File(filename) ).useDelimiter("\\Z").next();
		return LoadTables(content, filename);
	}

}
