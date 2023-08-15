package com.vtw.intern.extract.model.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Service;

import com.vtw.intern.extract.model.dto.DBTableInfo;

@Service
public class CommonService {

	private static final String path = "C:\\vtw_workspace\\jhb_intern_project\\src\\main\\webapp\\resources\\excel";
	
	private int[] columnWidth = {1500,3000,5400,2800,2800,2800,2800,2800,2800};
	
	private String[] tableHeader = {"번호","속성명","컬럼명","타입","길이","Not null","PK","FK","기본값"};
	
	
	public List<DBTableInfo> distinctKey(List<DBTableInfo> list){
		Map<String,Integer> map = new HashMap<>();
		int delColumn = 0;
		for (DBTableInfo dbTableInfo : list) {
			
			if(dbTableInfo.getConstraintType() != null) {
				
				if(!map.containsKey(dbTableInfo.getColumnName())) {
					
					if(dbTableInfo.getConstraintType().equals("P")) {
						dbTableInfo.setPrimaryKey("Y");
						map.put(dbTableInfo.getColumnName(),dbTableInfo.getRowNumber());
					}
					else if(dbTableInfo.getConstraintType().equals("R")) {
						dbTableInfo.setForeignKey("Y");
						map.put(dbTableInfo.getColumnName(),dbTableInfo.getRowNumber());
					}
				}
				else {
					dbTableInfo.setPrimaryKey("Y");
					dbTableInfo.setForeignKey("Y");
					delColumn = map.get(dbTableInfo.getColumnName());
					
				}

			}

		}
		if(delColumn != 0) {
			list.remove(delColumn -1);
		}
		
		return list;
	}
	
	public int excelCreate(String fileName, Map<String,Object> extractTable) throws Exception {
		int result = 1;
		
		File dir = new File(path);
		
		String[] fileNames = dir.list();
		
		//파일 디렉토리 조회후 파일명 중복검사
		for (int i = 0; i < fileNames.length; i++) {
		    if((fileName + ".xlsx").equals(fileNames[i])) {
		    	result = 0;
		    }
		}
		List<DBTableInfo> list = (List<DBTableInfo>) extractTable.get("DBTableInfo");
		
		String tableName = list.get(0).getTableName();
		
		if(result == 1) {
			FileOutputStreamExcel(extractTable, fileName, tableName);
		}
		
		return result;
	}
	public void FileOutputStreamExcel(Map<String,Object> extractTable, String fileName, String tableName) throws Exception {
		
		Cell cell;
		int count = 4;
		Workbook workBook = new XSSFWorkbook();
	
		fileName += ".xlsx";

		//sheet 생성
		Sheet sheet0 = workBook.createSheet("basic");
		
		//cell 병합 구현
		sheet0.addMergedRegion(new CellRangeAddress(0, 1, 0, 8));
		sheet0.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
		sheet0.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));
		sheet0.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
		sheet0.addMergedRegion(new CellRangeAddress(2, 2, 6, 8));
	
		// 행 생성
		Row row1 = sheet0.createRow(0);
		Row row2 = sheet0.createRow(1);
		Row row3 = sheet0.createRow(2);
		Row row4 = sheet0.createRow(3);
		
		
		cell = row3.createCell(0);
		cell.setCellValue("테이블명");
		cell.setCellStyle(headerCellStyle(workBook));
		
		cell = row3.createCell(2);
		cell.setCellValue(tableName);
		cell.setCellStyle(bodyCellStyle(workBook));
		
		cell = row3.createCell(4);
		cell.setCellValue("엔티티명");
		cell.setCellStyle(headerCellStyle(workBook));
		
		cell = row3.createCell(6);
		cell.setCellValue((String) extractTable.get("commentsTable"));
		cell.setCellStyle(bodyCellStyle(workBook));
		
		// 셀병합으로 인한 외각 테두리 스타일 적용
		cell = row3.createCell(8);
		cell.setCellStyle(bodyCellStyle(workBook));
		
		//열 폭 수정 (size 1  => excel너비 0.35) 및 헤더 설정 반복문
 		for(int i = 0 ; i < 9 ; i++) {
			sheet0.setColumnWidth(i, columnWidth[i]);
			cell = row4.createCell(i);
			cell.setCellValue(tableHeader[i]);
			cell.setCellStyle(headerCellStyle(workBook));
			
			//topRow 스타일지정
			cell = row1.createCell(i);
			cell.setCellValue("DB테이블 정보");
			cell.setCellStyle(topRowCellStyle(workBook));
			
			cell = row2.createCell(i);
			cell.setCellStyle(topRowCellStyle(workBook));
			
		}
		List<DBTableInfo> list = (List<DBTableInfo>) extractTable.get("DBTableInfo");
		for (DBTableInfo dbTableInfo : list) {
			
			Row row = sheet0.createRow(count);
			
			cell = row.createCell(0);
			cell.setCellValue(dbTableInfo.getRowNumber());
			cell.setCellStyle(bodyCellStyle(workBook));
			
			cell = row.createCell(1);
			cell.setCellValue(dbTableInfo.getColumnComment());
			cell.setCellStyle(bodyCellStyleNotAlignment(workBook));
			
			cell = row.createCell(2);
			cell.setCellValue(dbTableInfo.getColumnName());
			cell.setCellStyle(bodyCellStyleNotAlignment(workBook));
			
			cell = row.createCell(3);
			cell.setCellValue(dbTableInfo.getDataType());
			cell.setCellStyle(bodyCellStyle(workBook));
			
			cell = row.createCell(4);
			cell.setCellValue(dbTableInfo.getDataLength());
			cell.setCellStyle(bodyCellStyle(workBook));
			
			cell = row.createCell(5);
			cell.setCellValue(dbTableInfo.getNullable());
			cell.setCellStyle(bodyCellStyle(workBook));
			
			cell = row.createCell(6);
			cell.setCellValue(dbTableInfo.getPrimaryKey());
			cell.setCellStyle(bodyCellStyle(workBook));
			
			cell = row.createCell(7);
			cell.setCellValue(dbTableInfo.getForeignKey());
			cell.setCellStyle(bodyCellStyle(workBook));
			
			cell = row.createCell(8);
			cell.setCellValue(dbTableInfo.getDataDefault());
			cell.setCellStyle(bodyCellStyle(workBook));
			
			count++;
		
		}
		
		FileOutputStream fos = new FileOutputStream(path + "\\" + fileName);
		workBook.write(fos);
		fos.close();
		workBook.close();
	}
	
	// 최상단 셀스타일 적용
	public CellStyle topRowCellStyle(Workbook workBook) {
		// 최상단 행 스타일
		CellStyle topRowStyle = commonCellStyle(workBook);
		
		//폰트 설정
		Font bold = workBook.createFont();
		bold.setFontHeight((short)(20*20));
		bold.setBold(true);

		topRowStyle.setFont(bold);
		
		return topRowStyle;
	}
	// 헤더 셀스타일 적용
	public CellStyle headerCellStyle(Workbook workBook) {
		CellStyle headerCellStyle = commonCellStyle(workBook);
		
		headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		Font haderBold = workBook.createFont();
		haderBold.setBold(true);
		
		headerCellStyle.setFont(haderBold);
		
		return headerCellStyle;
	}

	public CellStyle bodyCellStyle(Workbook workBook) {
		CellStyle bodyCellStyle = commonCellStyle(workBook);

		return bodyCellStyle;
	}
	// 글씨 가운데 정렬 안된 셀스타일
	public CellStyle bodyCellStyleNotAlignment(Workbook workBook) {
		CellStyle cellStyle = workBook.createCellStyle();
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		
		return cellStyle;
	}
	// 공통 셀스타일
	public CellStyle commonCellStyle(Workbook workBook) {
		CellStyle commonCellStyle = workBook.createCellStyle();
		
		// 글씨 정렬
		commonCellStyle.setAlignment(HorizontalAlignment.CENTER);
		commonCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		// 테두리 선
		commonCellStyle.setBorderBottom(BorderStyle.THIN);
		commonCellStyle.setBorderLeft(BorderStyle.THIN);
		commonCellStyle.setBorderRight(BorderStyle.THIN);
		commonCellStyle.setBorderTop(BorderStyle.THIN);
		
		return commonCellStyle;
	}
}
