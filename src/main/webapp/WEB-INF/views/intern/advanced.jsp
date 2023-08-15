<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script>
var schemaName;
var tableName;
// 0일경우 유저테이블, 1 -> 스키마 선택후 테이블, 2 -> 스키마 선택 div 클릭했을떄
var state = 0;
let functionName = ["selectColumn","selectColumn","checkSchema"];

$(() => {
	$("#adMainContainer").hide();
	$("#stSelectContainer").hide();
	$("#advancedFooter").hide();
	
	$(jdbcForm).submit((e) => {
		e.preventDefault();
	
		var userName = $("#name").val();
		var pwd = $("#pwd").val();
		
		$.ajax({
			url:"${pageContext.request.contextPath}/adRestController/connect",
			method:"GET",
			data:{
				userName : userName,
				pwd : pwd
			},
			success(resp){	
				$("#jdbcFormContainer").hide();
				$("#stSelectContainer").show();
				$("#advancedFooter").show();
			},
		});
		
	});
	
	$("#schemaSelectContainer").click((e) => {
		$("#content").empty();
		$("#userName").empty();
		$("#progressText").empty();
		
		state = 2;
		focus($("#tableSelectContainer"),$("#columnSelectContainer"), $("#schemaSelectContainer"));
		
		$.ajax({
			url:"${pageContext.request.contextPath}/adRestController/allSchema",
			method:"GET",
			success(resp){	
				let schemaList ="";
				
				$(resp).each((i,schema) => {
					schemaList += `<button type = 'button' class = 'btn btn-light' id = 'schemaBtn' value = '\${schema}' onClick="checkSchema(this.value)">\${schema}</button>`;			
	
				});
				
				$("#content").append(schemaList);
				
			},
		});
		
	});
	
	$("#tableSelectContainer").click((e) => {
		$(".content").empty();
		$("#progressText").empty();
		
		state = 0;
		
		focus($("#columnSelectContainer"), $("#schemaSelectContainer"), $("#tableSelectContainer"));
		
		$.ajax({
			url:"${pageContext.request.contextPath}/adRestController/userTable",
			method:"GET",
			success(resp){
				let html ="";
				
				var tableList = resp.tableList;
				schemaName = resp.user;
				
				$(tableList).each((i,table) => {

					
					html += `<button type = 'button' class = 'btn btn-light' id = 'schemaBtn' value = '\${table}' onClick="selectColumn(this.value)">\${table}</button>`;
				
				});
				$("#userName").text("Schema : " + schemaName);
				$("#progressText").append(schemaName + " > ");
				$("#content").append(html);
				
			}
		});
	});
	
	$("#inputSearch").keyup(function(e){
		
		var searchWord = $(e.target).val();
		
		let searchResult ="";

		$.ajax({
			url:"${pageContext.request.contextPath}/adRestController/searchResult",
			method:"GET",
			data: {
				searchWord : searchWord,
				schemaName : schemaName,
				state : state
			},
			success(resp){
				
				$(resp).each((i,result) => {
					//state 에따라 버튼 다르게 생성해야됨
					searchResult +=  `<button type = 'button' class = 'btn btn-light' id = 'schemaBtn' value = '\${result}' onClick="\${functionName[state]}(this.value)">\${result}</button>`;
				});
				
				$("#content").empty();
				$("#content").append(searchResult);
			}
		});
		
	});
	
	$("#extractAd").click((e) => {
		var array = new Array();
		$("input:checkbox[name=column]:checked").each(function(){
			array.push(this.value);
		})
		
		if(array.length == 0){
			alert("컬럼을 선택하세요");
			return false;
		}
		
		if(state == 1){
			
			$.ajax({
				url:"${pageContext.request.contextPath}/adRestController/extractTable",
				method:"GET",
				data: {
					userName : schemaName,
					tableName : tableName,
					columnArray : array
				},
				// ajax통신시 배열값을 넘길수있게 해준다.
				traditional:true,
				success(resp){	
					let tableInfo;
					
					var DBTableInfo = resp.DBTableInfo;
					var commentsTable = resp.commentsTable;
					
					$(DBTableInfo).each((i,DBTableInfo) => {
						$("#tableContainer").show();
						
						const{rowNumber,columnComment,tableName,columnName,dataType,dataLength,nullable,dataDefault,constraintName,conType,constraintType,primaryKey,foreignKey,deleteRule} = DBTableInfo;
						console.log(columnName);
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
					$("#advancedContainer").hide();
					$("#adMainContainer").show();
					$("#tableName").text(tableName);
					$("#commentsTable").text(commentsTable);
					$("#adTable").append(tableInfo);
				},
			});
		}
		else if(state == 0){
			$.ajax({
				url:"${pageContext.request.contextPath}/adRestController/extractUserTable",
				method:"GET",
				data: {
					tableName : tableName,
					columnArray : array
				},
				traditional:true,
				success(resp){	
					let tableInfo;
					
					var DBTableInfo = resp.DBTableInfo;
					var commentsTable = resp.commentsTable;
					
					$(DBTableInfo).each((i,DBTableInfo) => {
						$("#tableContainer").show();
						
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
					$("#advancedContainer").hide();
					$("#adMainContainer").show();
					$("#tableName").text(tableName);
					$("#commentsTable").text(commentsTable);
					$("#adTable").append(tableInfo);
				},
			});
		}
		
	});
	
	$("#excelCreate").click((e) => {
		var fileName = $.trim($("#inputFileName").val());
		
		if(fileName == ""){
			alert("파일명을 입력해주세요");
		}
		else{
			console.log(fileName);
			
			$.ajax({
				url:"${pageContext.request.contextPath}/adRestController/excelCreate",
				method:"GET",
				data: {
					fileName : fileName
				},
				success(resp){
					
				}
			});
		}
		
	});
	
	$("#returnStep").click((e) => {
		location.href = "${pageContext.request.contextPath}/page/advanced";
	});

});
function checkSchema(name){
	this.schemaName = name;
	focus($("#columnSelectContainer"), $("#schemaSelectContainer"), $("#tableSelectContainer"));
	$("#progressText").append(name + " > ");
	$.ajax({
		url:"${pageContext.request.contextPath}/adRestController/selectTable",
		method:"GET",
		data:{
			userName : schemaName
		},
		success(resp){	
			let tableList ="";
			state = 1;

			$(resp).each((i,table) => {
			
				tableList += `<button type = 'button' class = 'btn btn-light' id = 'schemaBtn' value = '\${table}' onClick="selectColumn(this.value)">\${table}</button>`;
			
			});
			
			$("#content").empty();
			$("#userName").text("Schema : " + schemaName);
			$("#content").append(tableList);
		},
	});

}

function selectColumn(tableName){
	this.tableName = tableName;
	focus($("#schemaSelectContainer"), $("#tableSelectContainer"), $("#columnSelectContainer"));
	
	if(state == 1){

		$.ajax({
			url:"${pageContext.request.contextPath}/adRestController/selectColumn",
			method:"GET",
			data:{
				userName : schemaName,
				tableName : tableName
			},
			success(resp){	
				let columnList ="";
				
				$(resp).each((i,column) => {
					
					columnList += `<div class = 'ckColumn'>\${column}<input type="checkbox" name="column" value="\${column}"/></div>`;
				
				});
				$("#progressText").append(tableName + " > " + "COLUMNS");
				$(".content").empty();
				$(".content").append(columnList);
			},
		});
	}
	else if(state == 0){
		$.ajax({
			url:"${pageContext.request.contextPath}/adRestController/selectUserTableColumn",
			method:"GET",
			data:{
				tableName : tableName
			},
			success(resp){	
				let columnList ="";

				$(resp).each((i,column) => {
					
					columnList += `<div class = 'ckColumn'>\${column}<input type="checkbox" name="column" value="\${column}"/></div>`;
				
				});
				$("#progressText").append(tableName + " > " + "COLUMNS");
				$(".content").empty();
				$("#content").append(columnList);
			},
		});
	}
}

function focus(before1, before2, after){
	
	before1.css("border", "1px solid #00000070");
	before2.css("border", "1px solid #00000070");
	after.css("border-right","2px solid black");
	after.css("border-left","2px solid black");
	after.css("border-top","2px solid black");
	
	
	$("#search").css("border-top","2px solid black");
	$("#search").css("border-right","2px solid black");
	$("#search").css("border-left","2px solid black");
	$("#content").css("border-right","2px solid black");
	$("#content").css("border-left","2px solid black");
	$("#content").css("border-bottom","2px solid black");
}

</script>
<div class="step1Text">
		1.DB연결
</div>
<div class="step2Text">
		2.DB테이블 정보 추출
</div>
<div class="step3Text">
		3.파일 생성
</div>
<div class="advancedContainer" id = "advancedContainer">

	<div class="jdbcFormContainer" id="jdbcFormContainer">
		<form id="jdbcForm">
			<div class = "inputUser">
				<label>User</label>
				<input type="text" id = "name" class = "user" value="TESTER">
			</div>
			
			<div class = "inputPwd">
				<label>Pwd</label>
				<input type="text" id = "pwd" class = "pwd" value="tester">
			</div>
			
			<div class = "btnConnect">
				<input value="DB연결" type="submit" class = "DBConnect">
			</div>
		</form>
	</div>
	
	
	<div class = "stSelectContainer" id="stSelectContainer">
		<div class = "schemaSelectContainer" id = "schemaSelectContainer">
			<span>스키마 선택</span>
			<span class="material-icons" id = "more">
				expand_more
			</span>
		</div>
		
		<div class = "tableSelectContainer" id = "tableSelectContainer">
			<span>테이블 선택</span>
		</div>
		
		<div class = "columnSelectContainer" id = "columnSelectContainer">
			<span>항목 선택</span>
		</div>
		
		<div class = "search" id = "search">
			<input type="text" placeholder="검색어 입력" class ="inputSearch" id = "inputSearch">
			<span class = "userName" id = "userName"></span>
		</div>
		
		<div class = "content" id = "content">

		</div>
		
		
	</div>
	<div class="advancedFooter" id = "advancedFooter">
		<span class = "progressText" id = "progressText"></span>
		<button type="button" class="btn btn-dark" id = "extractAd" disabled="disabled">테이블 추출</button>
	</div>
</div> 

<div class = "adMainContainer" id="adMainContainer">
	<div class="returnStep" id="returnStep">
		<span class="material-icons" id="stepReturnCircle">circle</span>
		<span class="material-icons" id="stepReturnLeft">west</span>
	</div>
	
	<div id ="createContainer" class="createContainer">
		
		<input type="text" id = "inputFileName" class="inputFileName" placeholder="파일 이름 입력">
		<span class="material-icons" id = "excelCreate">note_add</span>
			
	</div>
	
	<div id ="tableContainer" class="tableContainer">
		<table id="adTable" class="adTable">
				 
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