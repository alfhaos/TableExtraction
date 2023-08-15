package com.vtw.intern.extract.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtw.intern.extract.model.dto.DBTableInfo;
import com.vtw.intern.extract.model.service.BasicService;
import com.vtw.intern.extract.model.service.CommonService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/basicRestController")
@RestController
@Slf4j
public class BasicRestContoller {

	@Autowired
	private BasicService basicService;
	
	@Autowired
	private CommonService commonService;
	
	private Map<String,Object> extractTable = new HashedMap<>();
	
	@GetMapping("/extract")
	public Map<String,Object> extractTable(String tableName) throws Exception{
		
		String commentsTable = "";
		
		try {
			commentsTable = basicService.selectCommentsTable(tableName);
		}
		catch(Exception e){
			commentsTable = "";
		}
		List<DBTableInfo> list = basicService.extractTable(tableName);
		if(list.isEmpty()) {
			throw new Exception("조회된 테이블이 없습니다.");
		}
		extractTable.put("commentsTable", commentsTable);
		extractTable.put("DBTableInfo", list);
		
		return extractTable;
	}
	
	@GetMapping("/excelCreate")
	public int excelCreate(String fileName) throws Exception {
		int result = commonService.excelCreate(fileName, extractTable);
		
		return result;
 	}

}
