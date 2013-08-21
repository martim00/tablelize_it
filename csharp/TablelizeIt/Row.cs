using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TablelizeIt
{
    public class Row
    {
        private Dictionary<string, string> row = new Dictionary<string, string>();
	
        public void AddField(string fieldName, string fieldValue) 
        {
            this.row.Add(fieldName, fieldValue);
        }
	
        public string GetFieldValue(string fieldName) 
        {
            if (!this.row.ContainsKey(fieldName))
                throw new Exception("cant find a column with name: " + fieldName);
                
            return this.row[fieldName];
        }
	
        public int GetFieldsCount() 
        {
            return this.row.Count;
        }
    }
}
