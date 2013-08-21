#ifndef INCLUDED_FIXTURE_H
#include "..\src\Fixture.h"
#endif //INCLUDED_FIXTURE_H

#ifndef INCLUDED_PERSON_H
#include "Person.h"
#endif //INCLUDED_PERSON_H

//igloo is the test framework used in this example
#include <igloo/igloo.h>
using namespace igloo;

/*
 * A simple example demonstrating the basic features of Tablelize-it
 *	Tests a method that calculates the body mass index of the object person with various data
 *
 * NOTE: The data tables created in text files for now need two empty lines 
 * at the end or between each table, this is a bug that will be fixed soon.
 */

//1° it is necessary to create your Fixture class 
class PersonBMIFixtureTest : public Fixture
{
	//2° implement the execute method. Here you have access to all tables /rows in the file. 
	void execute()
	{
		Table *BMITable= GetTable("BMI");
		for (unsigned i= 0; i < BMITable->GetRows().size(); i++)
		{
			double height= GetCellAs<double>("BMI", "height", i);
			double weight= GetCellAs<double>("BMI", "weight", i);
			double expectedResult= GetCellAs<double>("BMI", "expectedResult", i);
			Person person(weight,height);
			
			//3° use the test framework of your choice
			Assert::That(person.BMI() - expectedResult, IsLessThan(0.01));
		}      
	}
};

//4° Load the Fixture in your unit test
Context(Examples)
{
	Spec(PersonBMI_LoadingDataFromFile)//data can be read from a text file
	{
		//LoadingDataFromFile method will run the fixture loading all the data contained in the file
		PersonBMIFixtureTest().LoadingDataFromFile("examples/PersonBMIContents.txt");
	}

	Spec(PersonBMI_LoadingData)//or passed as parameter
	{
		//LoadingData method will run the fixture with data passed as parameter
		PersonBMIFixtureTest().LoadingData("|BMI|\n"
			                                "|height|weight|expectedResult|\n"
													  "|1.63|62|23.33|\n\n");
	}
};

