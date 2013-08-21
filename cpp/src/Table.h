#pragma once
#ifndef INCLUDED_TABLE_H
#define INCLUDED_TABLE_H

#include "FixtureGlossary.h"
#include <stdexcept>

class Table
{
public:
   Table(const std::string &_tableName, unsigned int positionInFile= 0) 
      : tableName(_tableName), positionInFile(positionInFile)
   {}

   std::string TableName() const
   {
      return tableName;
   }

   /**
   *  Retorna o arquivo de origem dessa tabela. 
   *
   *  Caso a tabela não tenha surgido de um arquivo mas diretamente 
   *  de uma string (usando a api Fixture::LoadTables(const std::string &)) 
   *  esse método retornará vazio.
   *
   *  @return std::string originFile - o arquivo de origem ou uma string 
   *                                   vazia caso essa Table tenha sido 
   *                                   originada de uma string
   */
   std::string GetOriginFile() const
   {
      return originFile;
   }

   /**
   *  Seta o arquivo de origem dessa tabela.
   *  @param const std::string & _originFile
   */
   void SetOriginFile(const std::string &_originFile)
   {
      originFile= _originFile;
   }

   void SetPositionInFile(unsigned int position)
   {
      positionInFile= position;   
   }

   unsigned int GetPositionInFile() const
   {
      return positionInFile;
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
   std::string originFile;
   std::vector<std::string> args;
   Rows rows;
   unsigned int positionInFile; // the table position in the file
};

#endif // INCLUDED_TABLE_H