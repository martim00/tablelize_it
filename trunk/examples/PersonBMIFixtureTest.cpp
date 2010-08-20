#ifndef INCLUDED_FIXTURE_H
#include "..\Fixture.h"
#endif //INCLUDED_FIXTURE_H

#ifndef INCLUDED_PERSON_H
#include "Person.h"
#endif //INCLUDED_PERSON_H

//igloo is the test framework used in this example
#include <igloo/igloo.h>
using namespace igloo;

//1° is necessary to create your class Fixture
class PersonBMIFixtureTest : public Fixture
{
	//2° the execute method you will test all the data in table
	void execute(){
		Table *BMITable= GetTable("BMI");
		for (unsigned i= 0; i < BMITable->GetRows().size(); i++)
		{
			double height= GetCellAs<double>("BMI", "height", i);
			double weight= GetCellAs<double>("BMI", "weight", i);
			double expectedResult= GetCellAs<double>("BMI", "expectedResult", i);			
			Person person(weight,height);			
			
			//3° use the test framework of your choice
			Assert::That(person.BMI()-expectedResult,IsLessThan(0.01));     
		}      
	}
};

//5° it is not necessary here, this one just because I'm using the test framework igloo this example 
Context(Examples)
{
	Spec(PersonBMI)
	{
		PersonBMIFixtureTest().LoadingDataFromFile("examples/PersonBMIContents.txt");	
	}
};

