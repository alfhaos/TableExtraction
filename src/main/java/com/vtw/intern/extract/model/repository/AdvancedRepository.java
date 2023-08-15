package com.vtw.intern.extract.model.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.vtw.intern.extract.model.dto.DBTableInfo;
import com.vtw.intern.extract.model.dto.Name;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AdvancedRepository {
	
	private JdbcTemplate jdbcTemplate;
	

	private String[] arr = {"OWNER","COMMENTS","TABLE_NAME","COLUMN_NAME","DATA_TYPE","DATA_LENGTH","NULLABLE","DATA_DEFAULT","CONSTRAINT_NAME","CON_TYPE","CONSTRAINT_TYPE","DELETE_RULE"};
	
	public String connect(DataSource dataSource) {
		String sql = "SELECT SYS_CONTEXT('USERENV', 'CURRENT_SCHEMA') AS \"CURRENT_SCHEMA\" FROM DUAL";
		jdbcTemplate = new JdbcTemplate(dataSource);
		Map<String,Object> map = new HashMap<>();
		
		map = jdbcTemplate.queryForMap("SELECT SYS_CONTEXT('USERENV', 'CURRENT_SCHEMA') AS \"CURRENT_SCHEMA\" FROM DUAL");
		
		
		return (String) map.get("CURRENT_SCHEMA");
		
	}


	public List<String> allSchema() {
		String sql = "SELECT USERNAME FROM ALL_USERS ORDER BY USERNAME";
		
		List<String> list = jdbcTemplate.queryForList(sql, String.class);
		
		return list;
	}


	public List<String> selectTABLE(String userName) {
		String sql = "SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER = ?";
		
		List<String> list = jdbcTemplate.queryForList(sql, String.class, new Object[]{userName});

		return list;
	}


	public List<String> selectColumn(String userName, String tableName) {
		String sql = "SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = ? AND OWNER = ?";
		
		List<String> list = jdbcTemplate.queryForList(sql, String.class, new Object[] {tableName, userName});
		
		return list;
	}


	public List<DBTableInfo> extractTable(String userName, String tableName, String[] columnArray) {
		StringBuilder sb = new StringBuilder();
		String sql = "\r\n"
				+ "SELECT\r\n"
				+ "	ROW_NUMBER() OVER(ORDER BY DATA_TYPE) AS ROW_NUMBER,\r\n"
				+ "	ATC.OWNER,\r\n"
				+ "	ACC.COMMENTS,\r\n"
				+ "	ATC.TABLE_NAME,\r\n"
				+ "	ATC.COLUMN_NAME,\r\n"
				+ "	ATC.DATA_TYPE,\r\n"
				+ "	ATC.DATA_LENGTH,\r\n"
				+ "	ATC.NULLABLE,\r\n"
				+ "	ATC.DATA_DEFAULT,\r\n"
				+ "	RES.CONSTRAINT_NAME,\r\n"
				+ "	RES.CON_TYPE,\r\n"
				+ "	RES.CONSTRAINT_TYPE,\r\n"
				+ "	RES.DELETE_RULE\r\n"
				+ "FROM\r\n"
				+ "	ALL_TAB_COLUMNS ATC LEFT JOIN (\r\n"
				+ "	SELECT\r\n"
				+ "		DISTINCT UC.CONSTRAINT_NAME,\r\n"
				+ "		UC.CON_TYPE,\r\n"
				+ "		UC.CONSTRAINT_TYPE,\r\n"
				+ "		UC.TABLE_NAME,\r\n"
				+ "		UC.DELETE_RULE,\r\n"
				+ "		AC.COLUMN_NAME\r\n"
				+ "	FROM\r\n"
				+ "		ALL_CONSTRAINTS UC JOIN ALL_CONS_COLUMNS AC ON UC.CONSTRAINT_NAME = AC.CONSTRAINT_NAME\r\n"
				+ "	WHERE\r\n"
				+ "		UC.TABLE_NAME = ? AND UC.CONSTRAINT_TYPE IN ('P','R') AND UC.OWNER = ?) RES ON ATC.COLUMN_NAME = RES.COLUMN_NAME\r\n"
				+ "	JOIN (select COMMENTS,COLUMN_NAME from ALL_COL_COMMENTS WHERE OWNER = ? AND TABLE_NAME = ?) ACC ON ATC.COLUMN_NAME = ACC.COLUMN_NAME\r\n"
				+ "WHERE\r\n"
				+ "	ATC.OWNER = ? AND ATC.TABLE_NAME = ? AND ATC.COLUMN_NAME IN (";
		
		sb.append(sql);
		
		for(int i = 0 ; i < columnArray.length ; i++) {
			if(i == (columnArray.length -1)) {
				sb.append("'"+columnArray[i]+"'");
			}
			else {
				sb.append("'"+columnArray[i] + "', ");
			}
			
		}
		sb.append(")");

		sql = sb.toString();
		List<DBTableInfo> list = jdbcTemplate.query(sql,new RowMapper<DBTableInfo>() {
			@Override
			public DBTableInfo mapRow(ResultSet rs, int count) throws SQLException{
				DBTableInfo dt = new DBTableInfo(
						rs.getInt("ROW_NUMBER"),
						rs.getString("COMMENTS"),
						rs.getString("TABLE_NAME"),
						rs.getString("COLUMN_NAME"),
						rs.getString("DATA_TYPE"),
						rs.getString("DATA_LENGTH"),
						rs.getString("NULLABLE"),
						rs.getString("DATA_DEFAULT"),
						rs.getString("CONSTRAINT_NAME"),
						rs.getString("CON_TYPE"),
						rs.getString("CONSTRAINT_TYPE"),
						rs.getString("DELETE_RULE"));
				return dt;
			}
		}, new Object[] {tableName,userName,userName,tableName,userName,tableName});

		return list;
	}


	public String selectCommentsTable(String userName, String tableName) {
		String sql = "SELECT\r\n"
				+ "	B.COMMENTS\r\n"
				+ "FROM\r\n"
				+ "	ALL_TABLES A JOIN ALL_TAB_COMMENTS B ON A.OWNER = B.OWNER AND A.TABLE_NAME = B.TABLE_NAME\r\n"
				+ "WHERE\r\n"
				+ "	A.OWNER = ? AND A.TABLE_NAME = ?";
		
		String comments = jdbcTemplate.queryForObject(sql, new Object[] {userName, tableName}, String.class);
		
		return comments;
	}


	public List<String> userTable() {
		String sql = "SELECT TABLE_NAME FROM USER_TABLES";
		
		List<String> list = jdbcTemplate.queryForList(sql, String.class);
		
		return list;
	}


	public List<String> selectUserTableColumn(String tableName) {
		String sql = "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ?";
		
		List<String> list = jdbcTemplate.queryForList(sql, String.class, new Object[] {tableName});
		
		return list;
	}


	public List<DBTableInfo> extractUserTable(String tableName, String[] columnArray) {
		StringBuilder sb = new StringBuilder();
		String sql = "	SELECT \r\n"
				+ "			ROW_NUMBER() OVER(ORDER BY DATA_TYPE) AS ROW_NUMBER,\r\n"
				+ "			ACC.COMMENTS,\r\n"
				+ "			ATC.TABLE_NAME,\r\n"
				+ "			ATC.COLUMN_NAME,\r\n"
				+ "			ATC.DATA_TYPE,\r\n"
				+ "			ATC.DATA_LENGTH,\r\n"
				+ "			ATC.NULLABLE,\r\n"
				+ "			ATC.DATA_DEFAULT,\r\n"
				+ "			RES.CONSTRAINT_NAME,\r\n"
				+ "			RES.CON_TYPE,\r\n"
				+ "			RES.CONSTRAINT_TYPE,\r\n"
				+ "			RES.DELETE_RULE\r\n"
				+ "		\r\n"
				+ "		FROM \r\n"
				+ "			USER_TAB_COLUMNS ATC LEFT JOIN (\r\n"
				+ "			SELECT\r\n"
				+ "				DISTINCT UC.CONSTRAINT_NAME,\r\n"
				+ "				UC.CON_TYPE,\r\n"
				+ "				UC.CONSTRAINT_TYPE,\r\n"
				+ "				UC.DELETE_RULE,\r\n"
				+ "				AC.COLUMN_NAME\r\n"
				+ "			FROM\r\n"
				+ "				USER_CONSTRAINTS UC JOIN ALL_CONS_COLUMNS AC\r\n"
				+ "				ON UC.CONSTRAINT_NAME = AC.CONSTRAINT_NAME\r\n"
				+ "			WHERE\r\n"
				+ "				AC.TABLE_NAME = ?  AND CONSTRAINT_TYPE IN ('P','R')) RES ON ATC.COLUMN_NAME = RES.COLUMN_NAME\r\n"
				+ "			JOIN (SELECT COMMENTS,COLUMN_NAME FROM USER_COL_COMMENTS WHERE TABLE_NAME = ?) ACC ON ATC.COLUMN_NAME = ACC.COLUMN_NAME	\r\n"
				+ "		WHERE \r\n"
				+ "			ATC.TABLE_NAME = ? AND ATC.COLUMN_NAME IN (";
		
		sb.append(sql);
		
		for(int i = 0 ; i < columnArray.length ; i++) {
			if(i == (columnArray.length -1)) {
				sb.append("'"+columnArray[i]+"'");
			}
			else {
				sb.append("'"+columnArray[i] + "', ");
			}
			
		}
		sb.append(")");
		
		sql = sb.toString();
		
		List<DBTableInfo> list = jdbcTemplate.query(sql,new RowMapper<DBTableInfo>() {
			@Override
			public DBTableInfo mapRow(ResultSet rs, int count) throws SQLException{
				DBTableInfo dt = new DBTableInfo(
						rs.getInt("ROW_NUMBER"),
						rs.getString("COMMENTS"),
						rs.getString("TABLE_NAME"),
						rs.getString("COLUMN_NAME"),
						rs.getString("DATA_TYPE"),
						rs.getString("DATA_LENGTH"),
						rs.getString("NULLABLE"),
						rs.getString("DATA_DEFAULT"),
						rs.getString("CONSTRAINT_NAME"),
						rs.getString("CON_TYPE"),
						rs.getString("CONSTRAINT_TYPE"),
						rs.getString("DELETE_RULE"));
				return dt;
			}
		}, new Object[] {tableName, tableName, tableName});
		
		return list;
	}


	public String selectCommentsUserTable(String tableName) {

		String sql = " 	SELECT \r\n"
				+ " 		B.COMMENTS\r\n"
				+ "		FROM\r\n"
				+ "		  	USER_TABLES A, USER_TAB_COMMENTS B\r\n"
				+ "		WHERE  \r\n"
				+ "			A.TABLE_NAME = B.TABLE_NAME AND  A.TABLE_NAME = ?\r\n"
				+ "		ORDER BY \r\n"
				+ "			A.TABLE_NAME";
		
		String comments = jdbcTemplate.queryForObject(sql, new Object[] {tableName}, String.class);
		
		return comments;
	}


	public String currentSchema() {
		String sql = "SELECT\r\n"
					+ "   SYS_CONTEXT('USERENV', 'CURRENT_SCHEMA') AS \"CURRENT_SCHEMA\"\r\n"
					+ "FROM\r\n"
					+ "   DUAL";
		String user = jdbcTemplate.queryForObject(sql, String.class);
		
		return user;
	}


	public List<String> searchUserTable(String searchWord) {
		
		String sql = "SELECT TABLE_NAME FROM USER_TABLES WHERE TABLE_NAME LIKE ?";
		
		List<String> list = jdbcTemplate.queryForList(sql, String.class, new Object[] {searchWord});

		return list;
	}


	public List<String> searchSchemaTable(String searchWord, String schemaName) {

		String sql = "SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER = ? AND TABLE_NAME LIKE ?";
		
		List<String> list = jdbcTemplate.queryForList(sql, String.class, new Object[] {schemaName, searchWord});
		
		return list;
	}


	public List<String> searchSchema(String searchWord) {
		String sql = "SELECT USERNAME FROM ALL_USERS WHERE USERNAME LIKE ?";
		
		List<String> list = jdbcTemplate.queryForList(sql, String.class, new Object[] {searchWord});
		
		return list;
	}

}
