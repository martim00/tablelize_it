using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TablelizeIt.Test
{
    class SimpleFixture : Fixture
    {
        public override void Execute() { }
    }

    [TestFixture]
    public class FixtureTest
    {

        [Test]
        public void TestShouldReadExampleFromString()
        {
            SimpleFixture fixture = new SimpleFixture();
            
            fixture.LoadData("|some|\n|column|\n|50|\n\n");
            
            Table table = fixture.GetTable("some");
            
            Assert.AreEqual(1, table.RowsCount());
        }
        
        [Test]
        public void TestMoreThanOneTable() 
        {
            SimpleFixture fixture = new SimpleFixture();
            fixture.LoadData("|some|\n|column|\n|50|\n\n|other table|\n|other|\n|45|\n\n");	
            
            Table table= fixture.GetTable("some");
            Table otherTable= fixture.GetTable("other table");

            Assert.AreEqual(2, fixture.TablesCount());
            Assert.AreEqual(1, table.RowsCount());
            Assert.AreEqual(1, otherTable.RowsCount());
        }
        
        [Test]
        public void TestGetTableShouldThrowIfThereIsMoreThanOneTableWithTheSameName() 
        {
              SimpleFixture fixture = new SimpleFixture();
              fixture.LoadData("|TableWithArgs|someArg|\n|field1|field2|\n|1.74|73|\n\n|TableWithArgs|otherArg|\n|field1|field2|\n|474|21|\n\n");

              try {
                 fixture.GetTable("TableWithArgs");
                 Assert.Fail("should throw an exception here");

              } catch (Exception e) {}

        }
	
        [Test]
        public void TestAsAFixtureIWantToIterateOverTablesWithSameName() 
        {
            SimpleFixture fixture = new SimpleFixture();
            fixture.LoadData("|TableWithArgs|someArg|\n|field1|field2|\n|1.74|73|\n\n|TableWithArgs|otherArg|\n|field1|field2|\n|474|21|\n\n");
            
            List<Table> tablesWithSameName = fixture.GetTablesWithName("TableWithArgs");
            Assert.AreEqual(2, tablesWithSameName.Count);

            Table firstTable= tablesWithSameName[0];
            Table secondTable= tablesWithSameName[1];
            
            Assert.AreEqual(1, firstTable.RowsCount());
            Assert.AreEqual("1.74", firstTable.GetCell("field1", 0));

            Assert.AreEqual(1, secondTable.RowsCount());
            Assert.AreEqual("474", secondTable.GetCell("field1", 0));
        }
	
        [Test]
        public void TestAsAFixtureIWantToRecoverTablesByTheArgs() 
        {
            SimpleFixture fixture = new SimpleFixture();
            fixture.LoadData("|TableWithArgs|someArg|\n|field1|field2|\n|1.74|73|\n\n|TableWithArgs|otherArg|\n|field1|field2|\n|474|21|\n\n");

            Table tableWithArg= fixture.GetTableWithArg("TableWithArgs", 0, "someArg");

            Assert.AreEqual(1, tableWithArg.RowsCount());
            Assert.AreEqual("1.74", tableWithArg.GetCell("field1", 0));

            Table otherTableWithArg= fixture.GetTableWithArg("TableWithArgs", 0, "otherArg");

            Assert.AreEqual(1, otherTableWithArg.RowsCount());
            Assert.AreEqual("474", otherTableWithArg.GetCell("field1", 0));
        }
    }
}
