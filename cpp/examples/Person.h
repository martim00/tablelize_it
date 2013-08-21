#ifndef INCLUDED_PERSON_H
#define INCLUDED_PERSON_H

//this is a simple class to be used as an example

class Person
{
public:
	~Person(){}
	Person(double _weight,double _height)
		:weight(_weight),height(_height){}

	
	/**
	 *  Body Mass Index
	 *  @return const double 
	*/
	const double BMI()
	{
		return weight / (height*height);
	}
	        
private:
	double weight,
		    height;
};

#endif //INCLUDED_PERSON_H