using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TablelizeIt;

namespace TablelizeIt.Test
{
    [TestFixture]
    class TableParserTest
    {
        [Test]
        public void TestTokenizer()
        {
            TableParser parser = new TableParser();		
            Table table= parser.LoadTable("|ImcTable|\n|altura|peso|imc|\n|1.74|73|24.11|\n|1.63|62|23.33|\n|1.52|51|22.07|");

            Assert.AreEqual(3, table.RowsCount());
            Assert.AreEqual(3, table.FieldsCount());
        }

        [Test]
        public void TestParserFromFile() 
        {		
            TableParser parser = new TableParser();		
            Dictionary<string, List<Table>> tables = parser.LoadTablesFromFile("../../TableParserTestContents.txt");
            
            Assert.AreEqual(1, tables.Count);
            Assert.AreEqual(3, tables["ImcTable"][0].RowsCount());
        }
	
        [Test]
        public void TestParserShouldAllowTableArgs() 
        {
            TableParser parser = new TableParser();
            Table table = parser.LoadTable("|TableWithArgs|someArg|\n|field1|field2|\n|1.74|73|\n\n");
            Assert.AreEqual("someArg", table.GetTableArgAt(0));
        }	

    }
}
