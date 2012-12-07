package tablelize;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Fixture {

	private Hashtable<String, ArrayList<Table>> tables = new Hashtable<String, ArrayList<Table>>();

	public abstract void execute();

	public void LoadingData(String data) throws Exception {

		TableParser parser = new TableParser();

		tables = parser.LoadTables(data, "");
		execute();
	}
	
	public Table GetTable(String tableName) throws Exception
	{
		ArrayList<Table> tablesWithName = tables.get(tableName);
		if (tablesWithName.isEmpty())
			throw new Exception("cant find a table with name " + tableName + "\nMaybe you forget to put two empty lines in the end of file.");
		
		if (tablesWithName.size() > 1)
			throw new Exception("found more than one table with name " + tableName + ". Maybe you want to see the GetTableWithArg method");
		
		return tablesWithName.get(0);
	}  
}
