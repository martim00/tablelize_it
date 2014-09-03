package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import tablelize.Fixture;
import tablelize.Table;

public class FixtureTest {
	
	
	private class SimpleFixture extends Fixture
	{
		@Override
		public void execute() {
		}

	}

	@Test
	public void testShouldReadExampleFromString() throws Exception {
		
		Fixture fixture = new SimpleFixture();
		
		fixture.loadData("|some|\n|column|\n|50|\n\n");
		
		Table table = fixture.getTable("some");
		
		assertEquals(1, table.rowsCount());
	}
	
	@Test
	public void testMoreThanOneTable() throws Exception {

		Fixture fixture = new SimpleFixture();
		fixture.loadData("|some|\n|column|\n|50|\n\n|other table|\n|other|\n|45|\n\n");	
		
		Table table= fixture.getTable("some");
		Table otherTable= fixture.getTable("other table");

		assertEquals(2, fixture.tablesCount());
		assertEquals(1, table.rowsCount());
		assertEquals(1, otherTable.rowsCount());

	}
	
	@Test
	public void testGetTableShouldThrowIfThereIsMoreThanOneTableWithTheSameName() throws Exception {
		
	      Fixture fixture = new SimpleFixture();
	      fixture.loadData("|TableWithArgs|someArg|\n|field1|field2|\n|1.74|73|\n\n|TableWithArgs|otherArg|\n|field1|field2|\n|474|21|\n\n");

	      try {
	         fixture.getTable("TableWithArgs");
	         fail("should throw an exception here");

	      } catch (Exception e) {}

	}
	
	@Test
	public void testAsAFixtureIWantToIterateOverTablesWithSameName() throws Exception
	{
		Fixture fixture = new SimpleFixture();
		fixture.loadData("|TableWithArgs|someArg|\n|field1|field2|\n|1.74|73|\n\n|TableWithArgs|otherArg|\n|field1|field2|\n|474|21|\n\n");
		
		List<Table> tablesWithSameName = fixture.getTablesWithName("TableWithArgs");
		assertEquals(2, tablesWithSameName.size());

		Table firstTable= tablesWithSameName.get(0);
		Table secondTable= tablesWithSameName.get(1);
		
		assertEquals(1, firstTable.rowsCount());
		assertEquals("1.74", firstTable.getCell("field1", 0));

		assertEquals(1, secondTable.rowsCount());
		assertEquals("474", secondTable.getCell("field1", 0));
	}
	
	@Test
	public void testAsAFixtureIWantToRecoverTablesByTheArgs() throws Exception
	{
		Fixture fixture = new SimpleFixture();
		fixture.loadData("|TableWithArgs|someArg|\n|field1|field2|\n|1.74|73|\n\n|TableWithArgs|otherArg|\n|field1|field2|\n|474|21|\n\n");

		Table tableWithArg= fixture.getTableWithArg("TableWithArgs", 0, "someArg");

		assertEquals(1, tableWithArg.rowsCount());
		assertEquals("1.74", tableWithArg.getCell("field1", 0));

		Table otherTableWithArg= fixture.getTableWithArg("TableWithArgs", 0, "otherArg");

		assertEquals(1, otherTableWithArg.rowsCount());
		assertEquals("474", otherTableWithArg.getCell("field1", 0));
	}

}
