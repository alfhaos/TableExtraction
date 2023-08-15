package com.vtw.intern.extract.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtw.intern.extract.model.dto.DBTableInfo;
import com.vtw.intern.extract.model.repository.BasicRepository;

@Service
public class BasicService {
	
	@Autowired
	private BasicRepository basicRepository;
	
	@Autowired
	private CommonService commonService;
	
	public List<DBTableInfo> extractTable(String tableName) {
		List<DBTableInfo> list = basicRepository.extractTable(tableName);
		
		return commonService.distinctKey(list);
	}
	public String selectCommentsTable(String tableName) {
		
		return basicRepository.selectCommentsTable(tableName);
	}

}
