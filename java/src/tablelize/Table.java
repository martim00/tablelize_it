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

}
