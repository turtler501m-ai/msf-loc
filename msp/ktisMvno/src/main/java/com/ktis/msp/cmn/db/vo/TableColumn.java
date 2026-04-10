package com.ktis.msp.cmn.db.vo;

import java.util.HashMap;

public class TableColumn 
{
	public static final int ATTR_KEY = 0x1000;	
	public static final int ATTR_CHAR = 0x0001;	
	public static final int ATTR_NUM = 0x0002;	
	public static final int ATTR_TIME = 0x0004;	
	public static final int ATTR_DATE = 0x0008;	
	public static final int ATTR_BOOL = 0x0010;	
	public static final int ATTR_FLOAT = 0x0020;	
	public static final int ATTR_DOUBLE = 0x0040;	
	
	private String tableName;
	private String columnId;
	private String columnName;
	private String dataType;
	private String dataLength;
	private String nullable;
	private String comment;
	private String constraint;
	private String javaType;
	
	public int _length;
	public int _attr = 0;
	
	public String getTable_name() {
		return tableName;
	}
	public void setTable_name(String table_name) {
		this.tableName = table_name;
	}

	public String getColumn_id() {
		return columnId;
	}
	public void setColumn_id(String column_id) {
		this.columnId = column_id;
	}
	
	public String getColumn_name() {
		return columnName;
	}
	public void setColumn_name(String column_name) {
		this.columnName = column_name;
	}

	public String getData_type() {
		return dataType;
	}
	public void setData_type(String data_type) {
		this.dataType = data_type;
		
		if  (0 <= data_type.indexOf("CHAR"))
		{
			this._attr |=  ATTR_CHAR;
			this.javaType = "String";
		}
		else if (0 <= data_type.indexOf("NUMBER"))
		{
			this._attr |=  ATTR_NUM;
			this.javaType = "int";
		}
		else if (0 <= data_type.indexOf("DATE"))
		{
			this._attr |=  ATTR_DATE;
			this.javaType = "String";
		}
		else if (0 <= data_type.indexOf("TIME"))
		{
			this._attr |=  ATTR_TIME;
			this.javaType = "String";
		}
		else if (0 <= data_type.indexOf("FLOAT"))
		{
			this._attr |=  ATTR_FLOAT;
			this.javaType = "Float";
		}
		else if (0 <= data_type.indexOf("DOUBLE"))
		{
			this._attr |=  ATTR_DOUBLE;
			this.javaType = "Double";
		}
	}

	public String  getData_length() {
		return dataLength;
	}
	public void setData_length(String dataLength) {
		this.dataLength = dataLength;
		
		this._length = 1;
		if ((dataLength != null) && (!dataLength.isEmpty()))
			this._length = Integer.valueOf(dataLength);
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
		return this.javaType;
	}
	
    public String GenVariable(HashMap<String,String>valmap)
	{
		if (valmap == null)
			return "#{" + this.columnName + "}";

		String value = valmap.get(this.columnName);
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
			return this.columnName + " =  #{" + this.columnName + "}";
		
		String value = valmap.get(this.columnName);
		if (value == null)
			value = "";
		
		if (0 < (this._attr &  (ATTR_NUM|ATTR_FLOAT|ATTR_DOUBLE)))
		{
			if (value.isEmpty())
				return this.columnName + " =  0";
			return this.columnName + " =  " + value.replaceAll(",", "");
		}
		else
			return this.columnName + " =  '" + value + "'";
	}    

	public String GenRealStatement(HashMap<String,String>valmap)
	{
		if (valmap == null)
			return this.columnName + " =  #{" + this.columnName + "}";
		
		String value = valmap.get(this.columnName);
		if (value == null)
			value = "";
		
		if (0 < (this._attr &  (ATTR_NUM|ATTR_FLOAT|ATTR_DOUBLE)))
		{
			if (value.isEmpty())
				return this.columnName + " =  0";
			return this.columnName + " =  " + value.replaceAll(",", "");
		}
		else if (0 < (this._attr &  ATTR_CHAR))
			return this.columnName + " LIKE  '%" + value + "%'";
		else
			return this.columnName + " =  '" + value + "'";
	}    
	
	public String GenRealPreparedStatement(HashMap<String,String>valmap)
	{
		String value = valmap.get(this.columnName);
		if (value == null)
			value = "";
		
		if (0 < (this._attr &  (ATTR_NUM|ATTR_FLOAT|ATTR_DOUBLE)))
		{
			return this.columnName + " = ?";
		}
		else if (0 < (this._attr &  ATTR_CHAR))
			return this.columnName + " LIKE ?";
		else
			return this.columnName + " = ?";
	}
	
//	private static java.sql.Date getCurrentDate() 
//	{
//	    java.util.Date today = new java.util.Date();
//	    return new java.sql.Date(today.getTime());
//	}
	
	//ps.setString(1, "data");
//	public void AppendParam(boolean exactvalue, PreparedStatement ps, int idx, String value)
//	{
//		if (value == null)
//			value = "";
//		try
//		{
//			if (0 < (this._attr &  (ATTR_NUM|ATTR_FLOAT|ATTR_DOUBLE)))
//			{
//				if (value.isEmpty())
//				{
//					ps.setInt(idx, 0);
//					System.out.printf("ps.setInt(%d, 0)\r\n", idx);
//				}
//				else
//				{
//					String sdigit = value.replaceAll(",", "");
//					ps.setInt(idx, Integer.parseInt(sdigit));
//					System.out.printf("ps.setInt(%d, %d)\r\n", idx, Integer.parseInt(sdigit));
//				}
//			}
//			else if (0 < (this._attr & ATTR_CHAR))
//			{
//				if (exactvalue)
//				{
//					ps.setString(idx, value);
//					System.out.printf("ps.setString(%d, %s)\r\n", idx, value);
//				}
//				else
//				{
//					ps.setString(idx, "%" + value + "%");
//					System.out.printf("ps.setString(%d, %% %s %%)\r\n", idx, value);
//				}
//			}
//			else if (0 < (this._attr & ATTR_DATE))
//			{
//				ps.setDate(idx, getCurrentDate());
//				System.out.printf("ps.setDate(%d, getCurrentDate())\r\n", idx);
//				//ps.setString(idx, "SYSDATE");
//				//System.out.printf("ps.setString(%d, SYSDATE)\r\n", idx);
//				//ps.setString(idx, "'" + value + "'");
//			}
//			else if (0 < (this._attr & ATTR_TIME))
//			{
//				ps.setDate(idx, getCurrentDate());
//				System.out.printf("ps.setDate(%d, getCurrentDate())\r\n", idx);
//				//ps.setString(idx, "SYSDATE");
//				//System.out.printf("ps.setString(%d, SYSDATE)\r\n", idx);
//			}
//		} catch(Exception e)
//		{
//		}
//	}  

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("TableColumn [");
		buffer.append(" table_name = ").append(this.tableName);
		buffer.append(" column_name = ").append(this.columnName);
		buffer.append(" data_type = ").append(this.dataType);
		buffer.append(" data_length = ").append(this.dataLength);
		buffer.append(" nullable = ").append(this.nullable);
		buffer.append("] = ");
		buffer.append(super.toString());
		return buffer.toString();
	}	
}
