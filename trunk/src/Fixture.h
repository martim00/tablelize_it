#ifndef INCLUDED_FIXTURE_H
#define INCLUDED_FIXTURE_H

#include <map>
#include <string>
#include <vector>
#include "Table.h"
#include "TableParser.h"
#include "FixtureGlossary.h"

class Fixture
{
public:
   virtual ~Fixture()
   {
      TableIterator it= FirstTable();
      for (; it != LastTable(); it++)
         delete (*it).second;
   }
	
	void LoadingData(const std::string &data)
   {
		tables= TableParser().LoadTables(data);    
   	execute();
   }

	void LoadingDataFromFile(const std::string &fileName)
	{  		 
      tables= TableParser().LoadTablesFromFile(fileName);
	   execute();
	}   

   virtual void execute()= 0;
   

   template <class T>
   T GetCellAs(const std::string tableName, const std::string field, int row)
   {
      return GetTable(tableName)->GetCellAs<T>(field, row);
   }

   /*int ExampleCount() const
   {
      return tables.GetRows().size();
   }*/

   typedef std::multimap<std::string, Table*>::iterator TableIterator;
   typedef std::pair<TableIterator, TableIterator> TableRange;

   TableRange GetTableRangeWithName(const std::string &tableName)
   {
      return tables.equal_range(tableName);
   }

   std::vector<Table*> GetTablesWithName(const std::string &tableName)
   {
      TableRange range= GetTableRangeWithName(tableName);
      TableIterator it= range.first;
      std::vector<Table*> tablesWithName;

      for (; it != range.second; it++)
      {
         tablesWithName.push_back(it->second);
      }
      return tablesWithName;
   }

   Table *GetTable(const std::string &tableName)
   {
      size_t count= tables.count(tableName);
      if (count == 0)
         throw std::runtime_error("cant find a table with name " + tableName + "\nMaybe you forget to put two empty lines in the end of file.");

      if (count > 1)
         throw std::runtime_error("found more than one table with name " + tableName + ". Maybe you want to see the GetTableWithArg method");

      return GetTableRangeWithName(tableName).first->second;
   }  

   Table *GetTableWithArg(const std::string &tableName, unsigned argIndex, const std::string &argValue)
   {
      TableRange tables= GetTableRangeWithName(tableName);
      TableIterator it= tables.first;     

      for (; it != tables.second; it++)
      {
         std::string arg= (*it).second->GetTableArgAt(argIndex);
         if (!arg.compare(argValue))
         {
            return it->second;
         }
      }

      std::stringstream ss;
      ss << "cant find a table with name " << tableName << "and arg at " << argIndex << "with value " << argValue << std::endl;
      throw std::runtime_error(ss.str());
   }

   std::string GetCell( const std::string &tableName, const std::string &column, int row ) 
   {
      return GetTable(tableName)->GetRows()[row][column];
   }    
   
   size_t TablesCount() const 
   {
      return tables.size();
   }

   TableIterator FirstTable()
   {
      return tables.begin();
   }

   TableIterator LastTable()
   {
      return tables.end();
   }

protected:
   std::multimap<std::string, Table*> tables;
   //std::vector<Table*> tables;
   //Table table;
   std::vector<std::string> fields;
};

#endif //INCLUDED_FIXTURE_H