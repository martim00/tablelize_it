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
	
	Table *GetTable(const std::string &tableName)
	{
		size_t count= tables.count(tableName);
		if (count == 0)
			throw std::runtime_error("cant find a table with name " + tableName + "\nMaybe you forget to put two empty lines in the end of file.");

		if (count > 1)
			throw std::runtime_error("found more than one table with name " + tableName + ". Maybe you want to see the GetTableWithArg method");

		return GetTableRangeWithName(tableName).first->second;
	}  


}
