package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.Test;

import tablelize.Table;
import tablelize.TableParser;
import java.io.File;

public class TableParserTest {

	@Test
	public void testTokenizer()
	{
		TableParser parser = new TableParser();		
		Table table= parser.LoadTable("|ImcTable|\n|altura|peso|imc|\n|1.74|73|24.11|\n|1.63|62|23.33|\n|1.52|51|22.07|");

		assertEquals(3, table.rowsCount());
		assertEquals(3, table.fieldsCount());
	}   

	@Test
	public void testParserFromFile() throws Exception
	{		
		TableParser parser = new TableParser();		
		String file = new File(".").getCanonicalPath();
		System.out.println(file);
		Hashtable<String, ArrayList<Table>> tables = parser.LoadTablesFromFile("src/test/TableParserTestContents.txt");
		
		assertEquals(1, tables.size());
		assertEquals(3, tables.get("ImcTable").get(0).rowsCount());
	}
	
	@Test
	public void testParserShouldAllowTableArgs() throws Exception
	{
		TableParser parser = new TableParser();		
		Table table= parser.LoadTable("|TableWithArgs|someArg|\n|field1|field2|\n|1.74|73|\n\n");
		assertEquals("someArg", table.getTableArgAt(0));
	}	


}
