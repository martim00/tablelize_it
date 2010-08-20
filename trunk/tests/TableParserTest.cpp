#include <igloo/igloo.h>
using namespace igloo;

#include "..\src\TableParser.h"

Context(TableParserTest)
{
	Spec(Tokenizer)
	{
		TableParser parser;		
		Table *table= parser.LoadTable("|ImcTable|\n|altura|peso|imc|\n|1.74|73|24.11|\n|1.63|62|23.33|\n|1.52|51|22.07|");
		Assert::That(table->RowsCount(), Is().EqualTo(3));	
		Assert::That(table->FieldsCount(), Is().EqualTo(3));	

		delete table;
	}   

	Spec(ParserFromFile)
	{		
		Table *table= TableParser().LoadTableFromFile("tests/TableParserTestContents.txt");
		Assert::That(table->RowsCount(), Is().EqualTo(3));	
		delete table;
	}
	
	Spec(ParserShouldAllowTableArgs)
	{
		TableParser parser;		
		Table *table= parser.LoadTable("|TableWithArgs|someArg|\n"
			"|field1|field2|\n"
			"|1.74|73|\n\n");
		Assert::That(table->GetTableArgAt(0), Is().EqualTo(std::string("someArg")));
		delete table;
	}	
};