<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/common.css" />
<!-- favicon 에러 제거 -->
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/basic.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/advanced.css" />
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<meta charset="UTF-8">
<title>인턴 사원 최종 직무능력 테스트 A</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
</head>
<body>


<script>
//시작 및 끝 공백 제거 유효성검사
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g,""); }

$(() => {
	$(".vtw_logo").click((e) => {
		location.href ="${pageContext.request.contextPath}/";
	});
	$("#progessContainer").hide();
});

</script>

<div class="container-header">
	<img class="vtw_logo" src="${pageContext.request.contextPath }/resources/img/vtwLogo.png" width="300px">
	<div id = "allStepContainer">
		<div id="progressContainer" class="progressContainer">
	
			<div id="bar" class="bar">
			</div>
		</div>
		
		<div class="step1Container">
			<span class="material-icons" id ="step1">
				circle
			</span>
			<span class="material-icons" id = "step1Check">
				done
			</span>
		</div>
		<div class="step2Container" id="step2Container">
			<span class="material-icons" id ="step2">
				circle
			</span>
			<span class="material-icons" id = "step2Check">
				done
			</span>
		</div>
		
		<div class="step3Container">
			<span class="material-icons" id ="step3">
				circle
			</span>
			<span class="material-icons" id = "step3Check">
				done
			</span>
		</div>
	</div>
</div>
