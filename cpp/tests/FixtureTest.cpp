#include <igloo/igloo.h>
using namespace igloo;

#include "..\src\Fixture.h"
#include "..\src\TableParser.h"

struct SimpleFixture : public Fixture
{
   void execute()
   {
   
   }
};

Context(FixtureTest)
{
	
   Spec(ShouldReadExampleFromString)
   {
      SimpleFixture fixture;
		fixture.LoadingData("|some|\n|column|\n|50|\n\n");	
      Table *table= fixture.GetTable("some");
      Assert::That(table->RowsCount(), Is().EqualTo(1));
   }

   Spec(MoreThanOneTable)
   {
      SimpleFixture fixture;
      fixture.LoadingData("|some|\n"
                          "|column|\n"
                          "|50|\n\n"
                          "|other table|\n"
                          "|other|\n"
                          "|45|\n\n");	
      Table *table= fixture.GetTable("some");
      Table *otherTable= fixture.GetTable("other table");

      Assert::That(fixture.TablesCount(), Is().EqualTo(2));

      Assert::That(table->RowsCount(), Is().EqualTo(1));
      Assert::That(otherTable->RowsCount(), Is().EqualTo(1));      
   }   

   Spec(GetTableShouldThrowIfThereIsMoreThanOneTableWithTheSameName)
   {
      SimpleFixture fixture;
      fixture.LoadingData(      
         "|TableWithArgs|someArg|\n"
         "|field1|field2|\n"
         "|1.74|73|\n\n"
         "|TableWithArgs|otherArg|\n"
         "|field1|field2|\n"
         "|474|21|\n\n"
      );

      try {
         fixture.GetTable("TableWithArgs");
         Assert::Failure("should throw an exception here");

      } catch (std::runtime_error& ) {}
   }

   Spec(AsAFixtureIWantToIterateOverTablesWithSameName)
   {
      SimpleFixture fixture;
      fixture.LoadingData(      
         "|TableWithArgs|someArg|\n"
         "|field1|field2|\n"
         "|1.74|73|\n\n"
         "|TableWithArgs|otherArg|\n"
         "|field1|field2|\n"
         "|474|21|\n\n"
         );

      std::vector<Table*> tablesWithSameName= fixture.GetTablesWithName("TableWithArgs");

      Assert::That(tablesWithSameName.size(), Is().EqualTo(2));

      Table *firstTable= tablesWithSameName[0];
      Table *secondTable= tablesWithSameName[1];

      Assert::That(firstTable->RowsCount(), Is().EqualTo(1));
      Assert::That(firstTable->GetCell("field1", 0), Is().EqualTo("1.74"));

      Assert::That(secondTable->RowsCount(), Is().EqualTo(1));
      Assert::That(secondTable->GetCell("field1", 0), Is().EqualTo("474"));
            
   }

   Spec(AsAFixtureIWantToRecoverTablesByTheArgs)
   {
      SimpleFixture fixture;
      fixture.LoadingData(      
         "|TableWithArgs|someArg|\n"
         "|field1|field2|\n"
         "|1.74|73|\n\n"
         "|TableWithArgs|otherArg|\n"
         "|field1|field2|\n"
         "|474|21|\n\n"
         );
      

      Table *tableWithArg= fixture.GetTableWithArg("TableWithArgs", 0, "someArg");
      
      Assert::That(tableWithArg->RowsCount(), Is().EqualTo(1));
      Assert::That(tableWithArg->GetCell("field1", 0), Is().EqualTo("1.74"));

      Table *otherTableWithArg= fixture.GetTableWithArg("TableWithArgs", 0, "otherArg");

      Assert::That(otherTableWithArg->RowsCount(), Is().EqualTo(1));
      Assert::That(otherTableWithArg->GetCell("field1", 0), Is().EqualTo("474"));
   }
};