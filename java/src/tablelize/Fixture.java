package tablelize;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public abstract class Fixture {

	private Hashtable<String, ArrayList<Table>> tables = new Hashtable<String, ArrayList<Table>>();

	public abstract void execute() throws Exception;

	public void loadData(String data) throws Exception 
	{
		TableParser parser = new TableParser();
		tables = parser.LoadTables(data, "");
		execute();
	}
	
	public void loadDataFromFile(String fileName) throws Exception
	{  	
		System.out.println("loading file: " + fileName);
		TableParser parser = new TableParser();		
		tables= parser.LoadTablesFromFile(fileName);		
		execute();
	}   
	
	public boolean hasTable(String tableName) {
		return tables.containsKey(tableName);
	}

	public Table getTable(String tableName) throws Exception
	{
		ArrayList<Table> tablesWithName = tables.get(tableName);
		
		if (tablesWithName.isEmpty())
			throw new Exception("cant find a table with name " + tableName);

		if (tablesWithName.size() > 1)
			throw new Exception("found more than one table with name " + tableName + ". Maybe you want to see the GetTableWithArg method");

		return tablesWithName.get(0);
	}  

	public int tablesCount() 
	{
		int tableCount = 0;
		for (Map.Entry<String, ArrayList<Table>> entry : tables.entrySet()) {
			tableCount+= entry.getValue().size();
		}	
		return tableCount;
	}
	
	public List<Table> getTablesWithName(String tableName) throws Exception 
	{
		if (!this.tables.containsKey(tableName))
			throw new Exception("cant find a table with this name -> " + tableName);

		return this.tables.get(tableName);
	}
	
	public Table getTableWithArg(String tableName, int argIndex, String argValue) throws Exception 
	{
		List<Table> tablesWithName = getTablesWithName(tableName);
		
		for (Table table : tablesWithName) {
			String arg = table.getTableArgAt(argIndex);
			if (arg.equals(argValue))
				return table;
		}
		
		throw new Exception("cant find a table with name " + tableName + "and arg at " + argIndex + "with value " + argValue );
	}
}
