#pragma once
#ifndef INCLUDED_FLUENTINTERFACEHELPER_H
#define INCLUDED_FLUENTINTERFACEHELPER_H

/*
*	This is a set of macros to help the construction of fluent interfaces.
*  Work as follows:
*
*  If you have a class that wants to expose a fluent interface you can define it like that..
*  
*  struct TimeGrammar
*  {  
*      DECLARE_SYNTAX(int, hours, TimeGrammar);
*      DECLARE_SYNTAX(int, minutes, TimeGrammar);
*      DECLARE_SYNTAX(int, seconds, TimeGrammar);
*
*      void now()
*      {
*         //do something with..
*         //you can access the attributes with hours(), minutes() and seconds()
*      }  
*  };  
*
*   Usage:
*   TimeGrammar().hours(2).minutes(15).seconds(50).now();
*
*   Thats it..
*   @version 0.0.1
*   @author martim
*/

#define DECLARE_SYNTAX(type, name, classType) \
   type _##name; \
   classType &name(type param) \
   {  \
   _##name= param;  \
   return *this;  \
   }  \
   type &name() \
   {  \
   return _##name;  \
   }

#define DECLARE_SUGAR(name, classType) \
   classType &name() { return *this; }

//#define DECLARE_COLUMN(name, klass)  \
//   klass &name(const std::string &_##name)   \
//   {  \
//      columns[_##name]= Rows();  \
//      return *this;  \
//   }  


#define DECLARE_COLUMN(name, klass)  \
   klass &name(const std::string &_##name)   \
   {  \
   fields.push_back(_##name);  \
   return *this;  \
   }  


#endif //INCLUDED_FLUENTINTERFACEHELPER_H