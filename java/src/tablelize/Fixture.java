package tablelize;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Fixture {

	private Hashtable<String, ArrayList<Table>> tables = new Hashtable<String, ArrayList<Table>>();
	
	public abstract void execute();

	public void LoadingData(String data) {
		
		TableParser parser = new TableParser();
		
		tables = parser.LoadTables(data);
		execute();
	}
}
