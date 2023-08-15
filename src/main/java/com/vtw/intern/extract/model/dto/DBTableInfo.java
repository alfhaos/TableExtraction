package com.vtw.intern.extract.model.dto;

public class DBTableInfo {

	private int rowNumber;
	private String tableName;
	private String columnName;
	private String dataType;
	private String dataLength;
	private String nullable;
	private String dataDefault;
	private String constraintName;
	private String conType;
	private String constraintType;
	private String primaryKey;
	private String foreignKey;
	private String deleteRule;
	private String columnComment;
	
	public DBTableInfo() {
		super();
	}

	
	public DBTableInfo(int rowNumber, String columnComment, String tableName, String columnName, String dataType, String dataLength,
			String nullable, String dataDefault, String constraintName, String conType, String constraintType,
			String deleteRule) {
		super();
		this.rowNumber = rowNumber;
		this.tableName = tableName;
		this.columnName = columnName;
		this.dataType = dataType;
		this.dataLength = dataLength;
		this.nullable = nullable;
		this.dataDefault = dataDefault;
		this.constraintName = constraintName;
		this.conType = conType;
		this.constraintType = constraintType;
		this.deleteRule = deleteRule;
		this.columnComment = columnComment;
	}


	public DBTableInfo(int rowNumber, String tableName, String columnName, String dataType, String dataLength,
			String nullable, String dataDefault, String constraintName, String conType, String constraintType,
			String primaryKey, String foreignKey, String deleteRule, String columnComment) {
		super();
		this.rowNumber = rowNumber;
		this.tableName = tableName;
		this.columnName = columnName;
		this.dataType = dataType;
		this.dataLength = dataLength;
		this.nullable = nullable;
		this.dataDefault = dataDefault;
		this.constraintName = constraintName;
		this.conType = conType;
		this.constraintType = constraintType;
		this.primaryKey = primaryKey;
		this.foreignKey = foreignKey;
		this.deleteRule = deleteRule;
		this.columnComment = columnComment;
	}

	public DBTableInfo(int rowNumber, String tableName, String columnName, String dataType, String dataLength,
			String nullable, String dataDefault, String constraintName, String conType, String constraintType,
			String deleteRule) {
		super();
		this.rowNumber = rowNumber;
		this.tableName = tableName;
		this.columnName = columnName;
		this.dataType = dataType;
		this.dataLength = dataLength;
		this.nullable = nullable;
		this.dataDefault = dataDefault;
		this.constraintName = constraintName;
		this.conType = conType;
		this.constraintType = constraintType;
		this.deleteRule = deleteRule;
	}

	public String getColumnComment() {
		return columnComment;
	}
	
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}

	public int getRowNumber() {
		return rowNumber;
	}


	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}


	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataLength() {
		return dataLength;
	}

	public void setDataLength(String dataLength) {
		this.dataLength = dataLength;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getDataDefault() {
		return dataDefault;
	}

	public void setDataDefault(String dataDefault) {
		this.dataDefault = dataDefault;
	}

	public String getConstraintName() {
		return constraintName;
	}

	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}

	public String getConstraintType() {
		return constraintType;
	}

	public void setConstraintType(String constraintType) {
		this.constraintType = constraintType;
	}

	public String getDeleteRule() {
		return deleteRule;
	}

	public void setDeleteRule(String deleteRule) {
		this.deleteRule = deleteRule;
	}
	
}
