using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TablelizeIt
{
    public class TableParser
    {
        public Table LoadTable(string tableContents) 
        {
            StringReader scanner = new StringReader(tableContents);

            Table table = this.parseTableName(scanner.ReadLine());
            string header = scanner.ReadLine();
            List<string> fields = this.tokenize(header, "\\|");

            while (true) {
                string line = scanner.ReadLine();
                if (line == null)
                    break;

                if (line.Length == 0) continue;

                List<string> lineParams = this.tokenize(line, "\\|");
                this.addTableRow(table, fields, lineParams);
            }

            return table;
        }

	public Dictionary<string, List<Table>> LoadTables(String contents, String originFile) 
    {
		Dictionary<string, List<Table>> tables = new Dictionary<string, List<Table>>();

		int order = 0;
		string tableContents = "";		
		StringReader scanner =  new StringReader(contents);
		do {
			//process each line in some way
			string line = scanner.ReadLine();
            if (line == null)
                break;

			if (!(line.Length == 0)) {

				tableContents+= line;
				tableContents+= "\n";

			} 
			
			if (!(tableContents.Length == 0) && (line.Length == 0) /*|| !scanner.hasNextLine() */) {

				Table loadedTable = LoadTable(tableContents);
				loadedTable.SetPositionInFile(order++);
				loadedTable.SetOriginFile(originFile);
				
				String tableName = loadedTable.TableName();
				
				if (!tables.ContainsKey(tableName))
					tables.Add(tableName, new List<Table>());
				
				tables[tableName].Add(loadedTable);
				tableContents = "";
			}
		} while (true);

		return tables;
	}

	/**
	 *  Tokenize a string with the separator passed.
	 *
	 *  @return std::vector<std::string>
	 *  @param  std::string content
	 *  @param  char separator
	 */
	private List<string> tokenize(string content, string separator)
	{
        char[] charArr = separator.ToCharArray();
        string[] tokens = content.Split(charArr);
        return tokens.Where(e => { return e != String.Empty; }).ToList();
        //List<string> tokensAsList = new List<string>(Arrays.asList(tokens));
        //tokensAsList.remove(0); //TODO:  removing the first element, because split is returning an empty string.
        //return tokensAsList;
	}

	private void pushTableArgs( List<String> fields, Table table ) 
	{
		if (fields.Count < 2) return;
		
		for (int i = 1; i < fields.Count; i++) {
			
			table.PushTableArg(fields[i]);
		}
	}

	private Table parseTableName(string line)
	{
		List<string> tokens= tokenize(line, "|");
		Debug.Assert(tokens.Count != 0);

		string tableName= tokens[0];

		Table table= new Table(tableName);

		pushTableArgs(tokens, table);

		return table;

	}
	private void addTableRow( Table table, List<string> fieldNames, 
			List<string> fieldValues ) 
	{
		Debug.Assert(fieldNames.Count == fieldValues.Count);
		Row row = new Row();
		
		for (int i = 0; i < fieldNames.Count; i++) {
			row.AddField(fieldNames[i], fieldValues[i]);
		}
		
		table.AddRow(row);
	}

	public Dictionary<string, List<Table>> LoadTablesFromFile(string filename) {
        string content = new StreamReader(filename).ReadToEnd();
		return LoadTables(content, filename);
	}
    }
}
