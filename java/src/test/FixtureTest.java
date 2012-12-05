package test;

import static org.junit.Assert.*;

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
	public void testShouldReadExampleFromString() {
		
		SimpleFixture fixture = new SimpleFixture();
		
		fixture.LoadingData("|some|\n|column|\n|50|\n\n");
		
		Table table = fixture.GetTable("some");
		
		assertEquals(1, table.rowsCount());
		
	}

}
