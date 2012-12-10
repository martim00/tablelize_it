package tablelize;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public abstract class Fixture {

	private Hashtable<String, ArrayList<Table>> tables = new Hashtable<String, ArrayList<Table>>();

	public abstract void execute() throws Exception;

	public void LoadingData(String data) throws Exception {

		TableParser parser = new TableParser();

		tables = parser.LoadTables(data, "");
		execute();
	}
	
	public void LoadingDataFromFile(String fileName) throws Exception
	{  		 
		TableParser parser = new TableParser();
		tables= parser.LoadTablesFromFile(fileName);
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

	public int tablesCount() {
		int tableCount = 0;
		for (Map.Entry<String, ArrayList<Table>> entry : tables.entrySet()) {
			tableCount+= entry.getValue().size();
		}	
		return tableCount;
	}
	public List<Table> getTablesWithName(String tableName) throws Exception {

		if (!this.tables.containsKey(tableName))
			throw new Exception("cant find a table with this name -> " + tableName);

		return this.tables.get(tableName);
	}
	
	public Table getTableWithArg(String tableName, int argIndex, String argValue) throws Exception {
		
		List<Table> tablesWithName = getTablesWithName(tableName);
		
		for (Table table : tablesWithName) {
			String arg = table.getTableArgAt(argIndex);
			if (arg.equals(argValue))
				return table;
		}
		
		throw new Exception("cant find a table with name " + tableName + "and arg at " + argIndex + "with value " + argValue );
		
//		TableRange tables= GetTableRangeWithName(tableName);
//		TableIterator it= tables.first;     
//
//		for (; it != tables.second; it++)
//		{
//			std::string arg= (*it).second->GetTableArgAt(argIndex);
//			if (!arg.compare(argValue))
//			{
//				return it->second;
//			}
//		}
//
//		std::stringstream ss;
//		ss << "cant find a table with name " << tableName << "and arg at " << argIndex << "with value " << argValue << std::endl;
//		throw std::runtime_error(ss.str());
//
//		return null;
	}
}
