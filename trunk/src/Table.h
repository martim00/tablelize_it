#pragma once
#ifndef INCLUDED_TABLE_H
#define INCLUDED_TABLE_H

#include "FixtureGlossary.h"
#include <stdexcept>

class Table
{
public:
   Table(const std::string &_tableName) 
      : tableName(_tableName)
   {}

   std::string TableName() const
   {
      return tableName;
   }

   Rows &GetRows()
   {
      return rows;
   }

   size_t RowsCount() const
   {
      return rows.size();
   }

   RowIterator FirstRow()
   {
      return rows.begin();
   }

   RowIterator LastRow()
   {
      return rows.end();
   }   

   size_t FieldsCount() const
   {
      if (rows.empty()) return 0;
      else return rows.begin()->size();
   }   

   std::string GetCell( const std::string &column, int row ) 
   {
      return rows[row][column];
   }

   template <class T>
   T GetCellAs(const std::string field, int row)
   {
      T value;
      std::stringstream ss(rows[row][field].c_str());
      ss >> value;
      return value;
   }

   std::string GetTableArgAt(unsigned argIndex)
   {
      if (argIndex >= args.size())
         throw std::runtime_error("cant find a table arg at " + argIndex);
      return args[argIndex];
   }

   void PushTableArg( const std::string &arg ) 
   {
      args.push_back(arg);
   }


private:
   std::string tableName;
   std::vector<std::string> args;
   Rows rows;
};

#endif // INCLUDED_TABLE_H