package com.ktis.msp.cmn.db.vo;

import java.util.HashMap;

public class TableColumnVO 
{
	public static final int ATTR_KEY = 0x1000;	
	public static final int ATTR_CHAR = 0x0001;	
	public static final int ATTR_NUM = 0x0002;	
	public static final int ATTR_TIME = 0x0004;	
	public static final int ATTR_DATE = 0x0008;	
	public static final int ATTR_BOOL = 0x0010;	
	public static final int ATTR_FLOAT = 0x0020;	
	public static final int ATTR_DOUBLE = 0x0040;	
	
	private String table_name;
	private String column_id;
	private String column_name;
	private String data_type;
	private String data_length;
	private String nullable;
	private String comment;
	private String constraint;
	private String java_type;
	
	public int _length;
	public int _attr = 0;
	
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getColumn_id() {
		return column_id;
	}
	public void setColumn_id(String column_id) {
		this.column_id = column_id;
	}
	
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
		
		if  (0 <= data_type.indexOf("CHAR"))
		{
			this._attr |=  ATTR_CHAR;
			this.java_type = "String";
		}
		else if (0 <= data_type.indexOf("NUMBER"))
		{
			this._attr |=  ATTR_NUM;
			this.java_type = "int";
		}
		else if (0 <= data_type.indexOf("DATE"))
		{
			this._attr |=  ATTR_DATE;
			this.java_type = "String";
		}
		else if (0 <= data_type.indexOf("TIME"))
		{
			this._attr |=  ATTR_TIME;
			this.java_type = "String";
		}
		else if (0 <= data_type.indexOf("FLOAT"))
		{
			this._attr |=  ATTR_FLOAT;
			this.java_type = "Float";
		}
		else if (0 <= data_type.indexOf("DOUBLE"))
		{
			this._attr |=  ATTR_DOUBLE;
			this.java_type = "Double";
		}
	}

	public String  getData_length() {
		return data_length;
	}
	public void setData_length(String data_length) {
		this.data_length = data_length;
		
		this._length = 1;
		if ((data_length != null) && (!data_length.isEmpty()))
			this._length = Integer.valueOf(data_length);
		if (this._length == 1)
			this._attr |= ATTR_BOOL;
	}

	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getConstraint() {
		return constraint;
	}
	public void setConstraint(String constraint) {
		this.constraint = constraint;
		
		if ((constraint != null) && (constraint.equalsIgnoreCase("P")))
			this._attr |= ATTR_KEY;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getJava_type()
	{
		return this.java_type;
	}
	
    public String GenVariable(HashMap<String,String>valmap)
	{
		if (valmap == null)
			return "#{" + this.column_name + "}";

		String value = valmap.get(this.column_name);
		if (value == null)
			value = "";
		
		// 3자리 단위로 comma(,) 존재할 경우 제거함
		if (0 < (this._attr &  (ATTR_NUM|ATTR_FLOAT|ATTR_DOUBLE)))
		{
			if (value.isEmpty())
				return("0");
			return value.replaceAll(",", "");
		}
		else
			return  "'" + value + "'";
	}
	
	public String GenStatement(HashMap<String,String>valmap)
	{
		if (valmap == null)
			return this.column_name + " =  #{" + this.column_name + "}";
		
		String value = valmap.get(this.column_name);
		if (value == null)
			value = "";
		
		if (0 < (this._attr &  (ATTR_NUM|ATTR_FLOAT|ATTR_DOUBLE)))
		{
			if (value.isEmpty())
				return this.column_name + " =  0";
			return this.column_name + " =  " + value.replaceAll(",", "");
		}
		else
			return this.column_name + " =  '" + value + "'";
	}    

	public String GenRealStatement(HashMap<String,String>valmap)
	{
		if (valmap == null)
			return this.column_name + " =  #{" + this.column_name + "}";
		
		String value = valmap.get(this.column_name);
		if (value == null)
			value = "";
		
		if (0 < (this._attr &  (ATTR_NUM|ATTR_FLOAT|ATTR_DOUBLE)))
		{
			if (value.isEmpty())
				return this.column_name + " =  0";
			return this.column_name + " =  " + value.replaceAll(",", "");
		}
		else if (0 < (this._attr &  ATTR_CHAR))
			return this.column_name + " LIKE  '%" + value + "%'";
		else
			return this.column_name + " =  '" + value + "'";
	}    
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("TableColumn [");
		buffer.append(" table_name = ").append(this.table_name);
		buffer.append(" column_name = ").append(this.column_name);
		buffer.append(" data_type = ").append(this.data_type);
		buffer.append(" data_length = ").append(this.data_length);
		buffer.append(" nullable = ").append(this.nullable);
		buffer.append("] = ");
		buffer.append(super.toString());
		return buffer.toString();
	}	
}
