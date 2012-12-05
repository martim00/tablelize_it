package tablelize;

import java.util.ArrayList;
import java.util.Hashtable;

public class TableParser {

	public Hashtable<String, ArrayList<Table>> LoadTables(String data) {

		Hashtable<String, ArrayList<Table>> tables;
		std::stringstream ss(contents);      
		std::string line, tableContents;
		unsigned int order = 0;

		while (std::getline(ss, line, '\n'))
		{
			if (!line.empty()) {
				tableContents+= line;
				tableContents+= "\n";

			} else if (!tableContents.empty()) {
				Table *loadedTable= LoadTable(tableContents);
				loadedTable->SetPositionInFile(order++);
				loadedTable->SetOriginFile(originFile);
				tables.insert(std::pair<std::string, Table*>(loadedTable->TableName(), loadedTable));
				tableContents.clear();
			}
		}

		if (!contents.empty() && tables.empty())
			throw std::runtime_error("There are contents to read but we cant parse a table. Maybe you forgot to put two empty lines at the end of the file");

		return tables;

	}

}
