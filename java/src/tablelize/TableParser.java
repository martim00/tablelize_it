package tablelize;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class TableParser {

	public Hashtable<String, ArrayList<Table>> LoadTables(String contents, String originFile) throws Exception {

		Hashtable<String, ArrayList<Table>> tables = new Hashtable<String, ArrayList<Table>>();

		int order = 0;
		String tableContents = new String();		
		Scanner scanner =  new Scanner(contents);
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
				
				String tableName = loadedTable.tableName();
				
				if (!tables.contains(tableName))
					tables.put(tableName, new ArrayList<Table>());
				
				tables.get(tableName).add(loadedTable);
				tableContents = "";
			}
		}

		if (!contents.isEmpty() && tables.isEmpty())
			throw new Exception("There are contents to read but we cant parse a table. Maybe you forgot to put two empty lines at the end of the file");

		return tables;
	}

	/**
	 *  Tokenize a string with the separator passed.
	 *
	 *  @return std::vector<std::string>
	 *  @param  std::string content
	 *  @param  char separator
	 */
	List<String> tokenize(String content, String separator)
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

		for (String field : fields) {
			table.pushTableArg(field);
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

	private Table LoadTable(String tableContents) {

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

}
