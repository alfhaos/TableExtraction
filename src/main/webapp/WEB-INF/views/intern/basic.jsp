<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
var stepCount = 1;
var width = 1;

$(function(){
	
	$("#allStepContainer").show();
	$("#returnStep").hide();
	$("#createContainer").hide();
	$("#tableContainer").hide();
	
	$("#search").click((e) => {
		var tableName = $("#inputTableName").val();
		var commentsTable; 
		$.ajax({
			url:"${pageContext.request.contextPath}/basicRestController/extract",
			method:"GET",
			data:{
				tableName : tableName
			},
			success(resp){
				progressBarMove(stepCount);
				
				$(".step1Text").animate({
					fontSize: "1rem",
					opacity : 0.4
				},500);
				$(".step2Text").animate({
					fontSize: "1.5rem",
					opacity : 1
				},500);
				let tableInfo;
				var DBTableInfo = resp.DBTableInfo;
				commentsTable = resp.commentsTable;
				
				$(DBTableInfo).each((i,DBTableInfo) => {
					const{rowNumber,columnComment,tableName,columnName,dataType,dataLength,nullable,dataDefault,constraintName,conType,constraintType,primaryKey,foreignKey,deleteRule} = DBTableInfo;
					
					tableInfo += `
						<tr>
							<td>\${rowNumber}</td>
							<td>\${columnComment}</td>
							<td>\${columnName}</td>
							<td>\${dataType}</td>
							<td>\${dataLength}</td>
							<td>\${nullable}</td>
							<td>\${primaryKey}</td>
							<td>\${foreignKey}</td>
							<td>\${dataDefault}</td>
						</tr>`;
				
				});
				$("#tableName").text(tableName);
				$("#commentsTable").text(commentsTable);
				$("#basicTable").append(tableInfo);
				stepCount++;
				secondStep();
			},
			error: function(){alert("조회된 테이블이 없습니다.");}
		});
	});
	$("#excelCreate").click((e) => {
		
		var fileName = $.trim($("#inputFileName").val());

		if(fileName == ""){
			alert("파일명을 입력해주세요");
		}
		else{
			$.ajax({
				url:"${pageContext.request.contextPath}/basicRestController/excelCreate",
				method:"GET",
				data: {
					fileName : fileName
				},
				success(resp){
					if(resp == 1){
						
						$(".step2Text").animate({
							fontSize: "1rem",
							opacity : 0.4
						},500);
						$(".step3Text").animate({
							fontSize: "1.5rem",
							opacity : 1
						},500);
						
						stepCount++;
						progressBarMove(stepCount);
						thirdStep();
					}
					else if(resp ==0){
						alert("동일한 파일명이 존재합니다.");
					}
				}
			});
		}
		
	});
	
	$("#returnStep").click((e) => {
		location.href = "${pageContext.request.contextPath}/page/basic";
	});
	
});

function progressBarMove(stepCount){
	
	var id = setInterval(frame,10);

	function frame(){
		if(width >= stepCount * 50 || width == 100){
			clearInterval(id);
		} else{
			width++;
			$("#bar").width(width + '%');
		}
	}
}

function secondStep(){
	$("#extractContainer").animate({
		marginLeft : "50px",
		opacity: 0.2
	},500,function(){
		$("#extractContainer").hide();
		$("#tableContainer").show();
		$("#createContainer").show();
		$("#returnStep").show();
	});
	

	setTimeout(function() {
		$("#step2").animate({
			color : "#00a9cc"
		},500);
		
		$("#step2Check").animate({
			color : "white"
		},500);
	}, 500);
}

function thirdStep(){

	setTimeout(function() {
		$("#step3").animate({
			color : "#00a9cc"
		},500);
		
		$("#step3Check").animate({
			color : "white"
		},500,function(){
			alert("파일생성 성공");
		});
	}, 500);

	
}

</script>

<div class="step1Text">
		1.DB테이블 선택
</div>
<div class="step2Text">
		2.DB테이블 정보 추출
</div>
<div class="step3Text">
		3.파일 생성
</div>

<div class = "basicMainContainer">
	<div class="returnStep" id="returnStep">
		<span class="material-icons" id="stepReturnCircle">circle</span>
		<span class="material-icons" id="stepReturnLeft">west</span>
	</div>
	
	<div id="extractContainer" class ="extractContainer">
	
		<input type="text" id = "inputTableName" class="inputTableName" placeholder="추출할 테이블 입력">
		<span class="material-icons" id="search">search</span>

	</div>
	
	<div id ="createContainer" class="createContainer">
	
		<input type="text" id = "inputFileName" class="inputFileName" placeholder="파일 이름 입력">
		<span class="material-icons" id = "excelCreate">note_add</span>
		
	</div>
	
	<div id ="tableContainer" class="tableContainer">
		<table id="basicTable" class="basicTable">
		 
		  <tr>
		    <td colspan="9" class="tableTopRow">DB테이블 정보</td>
		  </tr>
		  
		  <tr>
		  	<td colspan="2" class="tableHeader">테이블명</td>
		  	<td colspan="2" id = "tableName"></td>
		  	<td colspan="2" class="tableHeader">엔티티명</td>
		  	<td colspan="3" id="commentsTable"></td>
		  </tr>
		  
		  <tr>
			<th>번호</th>
			<th>속성명</th>
			<th>컬럼명</th>
			<th>타입</th>
			<th>길이</th>
			<th>Not Null</th>
			<th>PK</th>
			<th>FK</th>
			<th>기본값</th>
		  </tr>
		  
		</table>
	
	</div>

</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
