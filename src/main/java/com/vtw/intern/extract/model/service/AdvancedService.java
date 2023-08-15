package com.vtw.intern.extract.model.service;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtw.intern.extract.controller.AdvancedRestController;
import com.vtw.intern.extract.model.dto.DBConnectDto;
import com.vtw.intern.extract.model.dto.DBTableInfo;
import com.vtw.intern.extract.model.dto.Name;
import com.vtw.intern.extract.model.repository.AdvancedRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdvancedService {
	
	@Autowired
	private CommonService commonService;
	
	private static AdvancedRepository advancedRepository = new AdvancedRepository();
	
	private Map<String,Object> extractTable = new HashedMap<>();
	
	public static BasicDataSource basicDataSource;
	
	public DataSource createDataSource(DBConnectDto DBConnect) {
		
		basicDataSource = new BasicDataSource();
		
		basicDataSource.setDriverClassName("com.tmax.tibero.jdbc.TbDriver");
		basicDataSource.setUrl("jdbc:tibero:thin:@10.47.39.125:8629:DB_D_GMD");
		basicDataSource.setUsername(DBConnect.getUserName());
		basicDataSource.setPassword(DBConnect.getPwd());
	
		return basicDataSource;
	}
	
	public String connect(DBConnectDto DBConnect) {

		return advancedRepository.connect(createDataSource(DBConnect));
	
	}

	public List<String> allSchema() {
		
		return advancedRepository.allSchema();
	}


	public List<String> selectTable(String userName) {
		
		return advancedRepository.selectTABLE(userName);
	}

	public List<String> selectColumn(String userName, String tableName) {
		
		return advancedRepository.selectColumn(userName, tableName);
	}

	public  Map<String,Object> extractTable(String userName, String tableName, String[] columnArray) {
		
		
		List<DBTableInfo> list = advancedRepository.extractTable(userName, tableName, columnArray);
		String commentsTable = advancedRepository.selectCommentsTable(userName, tableName);
		
		extractTable.put("commentsTable", commentsTable);
		extractTable.put("DBTableInfo", commonService.distinctKey(list));
		
		return extractTable;
	}
	
	public Map<String, Object> extractUserTable(String tableName, String[] columnArray) {

		List<DBTableInfo> list = advancedRepository.extractUserTable(tableName, columnArray);
		
		String commentsTable = advancedRepository.selectCommentsUserTable(tableName);
		
		extractTable.put("commentsTable", commentsTable);
		extractTable.put("DBTableInfo", commonService.distinctKey(list));
		
		return extractTable;
	}

	public Map<String, Object> selectUserTable() {
		
		List<String> list =  advancedRepository.userTable();
		String user = advancedRepository.currentSchema();
		
		extractTable.put("user", user);
		extractTable.put("tableList", list);
		
		return extractTable;
	}

	public List<String> selectUserTableColumn(String tableName) {
		
		return advancedRepository.selectUserTableColumn(tableName);
	}

	public List<String> searchResult(String searchWord, String schemaName, int state) {
		List<String> list = null;
		searchWord = ("%" + searchWord + "%").toUpperCase();
		
		if(state == 0) {
			list = advancedRepository.searchUserTable(searchWord);
		}
		
		else if(state == 1) {
			list = advancedRepository.searchSchemaTable(searchWord, schemaName);
		}
		else if(state == 2) {
			list = advancedRepository.searchSchema(searchWord);
		}
		return list;
	}

	

}
