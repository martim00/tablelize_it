package tablelize;

import java.util.Hashtable;
import java.util.Map;

public class Row {
	
	private Map<String, String> row = new Hashtable<String, String>();
	
	public void addField(String fieldName, String fieldValue) {
		this.row.put(fieldName, fieldValue);
	}
	
	public String getFieldValue(String fieldName) {
		return this.row.get(fieldName);
	}

}
