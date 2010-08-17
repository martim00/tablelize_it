#include <igloo/igloo.h>
using namespace igloo;

#include "Fixture.h"
#include "TableParser.h"
#include "FluentInterfaceHelper.h"

class CalculeIMC : public Fixture
{
public:
   DECLARE_SUGAR(ParaPessoa, CalculeIMC);
   DECLARE_SUGAR(Com, CalculeIMC);
   DECLARE_SUGAR(E, CalculeIMC);
   DECLARE_COLUMN(Altura, CalculeIMC);
   DECLARE_COLUMN(Peso, CalculeIMC);
   DECLARE_COLUMN(EsperaSe, CalculeIMC);   

   void execute()
   {   
      Table *imcTable= GetTable("ImcTable");
      for (unsigned i= 0; i < imcTable->GetRows().size(); i++)
      {
         double altura= this->GetCellAs<double>("ImcTable", "altura", i);
         double peso= this->GetCellAs<double>("ImcTable", "peso", i);
			double imcEsperado= this->GetCellAs<double>("ImcTable", "imc", i);
			Assert::That(IMC(altura,peso)-imcEsperado,IsLessThan(0.01));     
      }      
   }

	double IMC(double altura, double peso)
	{
		return peso / (altura*altura);
	}
};

struct SimpleFixture : public Fixture
{
   void execute()
   {
   
   }
};

Context(SimpleExamples)
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
		Table *table= TableParser().LoadTableFromFile("teste.txt");
		Assert::That(table->RowsCount(), Is().EqualTo(3));	
      delete table;
	}

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

   Spec(ShouldFailOnError)
   {
      CalculeIMC()
         .ParaPessoa().Com()
         .Peso("peso")
         .E()
         .Altura("altura")
         .EsperaSe("imc")      
         .LoadingDataFromFile("teste.txt");
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