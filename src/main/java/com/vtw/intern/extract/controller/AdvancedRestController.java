package com.vtw.intern.extract.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtw.intern.extract.model.dto.DBConnectDto;
import com.vtw.intern.extract.model.dto.DBTableInfo;
import com.vtw.intern.extract.model.dto.Name;
import com.vtw.intern.extract.model.service.AdvancedService;
import com.vtw.intern.extract.model.service.CommonService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/adRestController")
public class AdvancedRestController {

	@Autowired
	private AdvancedService advancedService;
	
	@Autowired
	private CommonService commonService;
	
	private Map<String,Object> extractTable = new HashedMap<>();
	
	@GetMapping("/userTable")
	public Map<String,Object> selectUserTable(){
	
		extractTable = advancedService.selectUserTable();
		return extractTable;
	}
	
	@GetMapping("/connect")
	public String connect(DBConnectDto DBConnect) throws Exception {
		
		String user = advancedService.connect(DBConnect);

		return user;
	}
	
	@GetMapping("/allSchema")
	public List<String> allSchema(){

		return advancedService.allSchema();
	}
	
	@GetMapping("/selectTable")
	public List<String> selectTable(String userName){
		
		return advancedService.selectTable(userName);
	}
	
	@GetMapping("/selectColumn")
	public List<String> selectColumn(String userName, String tableName){

		return advancedService.selectColumn(userName, tableName);
	}
	
	@GetMapping("/selectUserTableColumn")
	public List<String> selectUserTableColumn(String tableName){
		
		return advancedService.selectUserTableColumn(tableName);
	}
	
	@GetMapping("/extractTable")
	public Map<String,Object> extractTable(String userName, String tableName, String[] columnArray) {
		
		extractTable = advancedService.extractTable(userName, tableName, columnArray);
		
		return extractTable;
	}
	@GetMapping("/extractUserTable")
	public Map<String,Object> extractTable(String tableName, String[] columnArray){
		
		extractTable = advancedService.extractUserTable(tableName, columnArray);
		
		return extractTable;
	}
	@GetMapping("/excelCreate")
	public int excelCreate(String fileName) throws Exception {
		int result = commonService.excelCreate(fileName, extractTable);
		
		return result;
	}
	
	@GetMapping("/searchResult")
	public List<String> searchResult(String searchWord, String schemaName, int state){
		List<String> list = advancedService.searchResult(searchWord, schemaName, state);
		
		return list;
	}
	
}
