using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TablelizeIt
{
    public abstract class Fixture
    {
        private Dictionary<string, List<Table>> tables = new Dictionary<string, List<Table>>();

        public abstract void Execute();

        public void LoadData(string data)  
        {
            TableParser parser = new TableParser();
            tables = parser.LoadTables(data, "");
            Execute();
        }
	
        public void LoadDataFromFile(string fileName) 
        {  	
            Console.WriteLine("loading file: " + fileName);
            TableParser parser = new TableParser();		
            tables= parser.LoadTablesFromFile(fileName);		
            Execute();
        }   
	
        public bool HasTable(string tableName) {
            return tables.ContainsKey(tableName);
        }

        public Table GetTable(String tableName) 
        {
            if (!HasTable(tableName))
                throw new Exception("cant find a table with name " + tableName);

            List<Table> tablesWithName = tables[tableName];

            if (tablesWithName.Count > 1)
                throw new Exception("found more than one table with name " + tableName + ". Maybe you want to see the GetTableWithArg method");

            return tablesWithName[0];
        }  

        public int TablesCount() 
        {
            int tableCount = 0;
            foreach (List<Table> entry in tables.Values)
            {
                tableCount+= entry.Count;
            }

            return tableCount;
        }
        
        public List<Table> GetTablesWithName(string tableName) 
        {
            if (!HasTable(tableName))
                throw new Exception("cant find a table with this name -> " + tableName);

            return this.tables[tableName];
        }
        
        public Table GetTableWithArg(string tableName, int argIndex, string argValue) 
        {
            List<Table> tablesWithName = GetTablesWithName(tableName);
            
            foreach (Table table in tablesWithName) 
            {
                string arg = table.GetTableArgAt(argIndex);
                if (arg == argValue)
                    return table;
            }
            
            throw new Exception("cant find a table with name " + tableName + "and arg at " + argIndex + "with value " + argValue );
        }
    }
}
