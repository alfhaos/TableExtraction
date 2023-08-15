package com.vtw.intern.extract.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vtw.intern.extract.model.dto.DBTableInfo;

@Mapper
public interface BasicRepository {

	List<DBTableInfo> extractTable(String tableName);

	String selectCommentsTable(String tableName);
	
}
