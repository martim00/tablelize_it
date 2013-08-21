#pragma once
#ifndef INCLUDED_TABLEGENERATOR_H
#define INCLUDED_TABLEGENERATOR_H

#include <iosfwd>
#include <iomanip>
#include <fstream>

class TableGenerator
{
   const char separator;
   std::ostream &stream;

public:
   TableGenerator(std::ostream &_stream, int doublePrecision= -1)
      :  stream(_stream), separator('|')
   {
      if (doublePrecision > -1) {
         stream << std::setprecision(doublePrecision);
         stream << std::fixed;
      }
   }

   TableGenerator &beginTable( const std::string &tableName )
   {
      stream << separator << tableName << separator << std::endl;
      return *this;
   }

   template <class T>
   TableGenerator &write(T obj)
   {
      stream << separator << obj;
      return *this;
   }

   template <class T>
   TableGenerator &writeLn(T obj)
   {
      stream << separator << obj << separator << std::endl;
      return *this;
   }

   TableGenerator &endLn()
   {
      stream << separator << std::endl;
      return *this;
   }

   TableGenerator &endTable()
   {
      stream << std::endl;
      return *this;
   }
};

#endif //INCLUDED_TABLEGENERATOR_H
