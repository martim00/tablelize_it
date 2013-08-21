using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TablelizeIt
{
    public class Table
    {
        private string tableName;
        private int positionInFile = 0;
        private List<string> args = new List<string>();
        private List<Row> rows = new List<Row>();

        public Table(string tableName) 
        {
            this.tableName = tableName;
        }

        public void SetPositionInFile(int positionInFile) 
        {
            this.positionInFile = positionInFile;
        }

        public void SetOriginFile(String originFile) 
        {
            // TODO Auto-generated method stub
        }
	
        public void PushTableArg( String arg )
        {
            args.Add(arg);
        }

        public string TableName() 
        {
            return this.tableName;
        }
	
        public void AddRow(Row row) 
        {
            rows.Add(row);
        }

        public int RowsCount() 
        {
            return rows.Count;
        }

	
        /**
         * Returns the cell table by column and row 
         * @param column the column name
         * @param row the row index
         * @return String cell content
         * @throws Exception if the column name passed does not exist or if the row index is out of bounds 
         */
        public string GetCell(string column, int row)		
        {
            if (row >= this.rows.Count || row < 0)
                throw new Exception("the row index passed is out of range"); // TODO: create exception for this
            
            return this.rows[row].GetFieldValue(column);
        }
	
        public string GetTableArgAt(int argIndex) 
        {
            if (argIndex >= args.Count)
                throw new Exception("cant find a table arg at " + argIndex);
            
            return args[argIndex];
        }

        public int FieldsCount() 
        {
            if (rows.Count == 0) 
                return 0;
            else return rows[0].GetFieldsCount();
        }
    }
}
