package tablelize;

import java.util.List;
import java.util.ArrayList;

public class Table {

	private String tableName;
	private int positionInFile = 0;
	private List<String> args = new ArrayList<String>();
	private List<Row> rows = new ArrayList<Row>();

	public Table(String tableName) {
		this.tableName = tableName;
	}

	public void setPositionInFile(int positionInFile) {
		this.positionInFile = positionInFile;
	}

	public void setOriginFile(String originFile) {
		// TODO Auto-generated method stub

	}
	
	public void pushTableArg( String arg ) {
		args.add(arg);
	}

	public String tableName() {
		return this.tableName;
	}
	
	public void addRow(Row row) {
		rows.add(row);
	}

	public int rowsCount() {
		return rows.size();
	}

	
	/**
	 * Returns the cell table by column and row 
	 * @param column the column name
	 * @param row the row index
	 * @return String cell content
	 * @throws Exception if the column name passed does not exist or if the row index is out of bounds 
	 */
	public String getCell(String column, int row) throws Exception {		
		
		if (row >= this.rows.size() || row < 0)
			throw new Exception("the row index passed is out of range"); // TODO: create exception for this
		
		return this.rows.get(row).getFieldValue(column);
	}
	
	public String getTableArgAt(int argIndex) throws Exception {
		if (argIndex >= args.size())
			throw new Exception("cant find a table arg at " + argIndex);
		return args.get(argIndex);
	}

	public int fieldsCount() {
		if (rows.isEmpty()) 
			return 0;
		else return rows.get(0).getFieldsCount();
	}

}
