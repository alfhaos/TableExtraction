<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script>

$(() => {
	$("#allStepContainer").hide();
	
	$(".card-basic").click((e) => {
		location.href = "${pageContext.request.contextPath}/page/basic";
	});

	$(".card-advanced").click((e) => {
		location.href = "${pageContext.request.contextPath}/page/advanced";
	});
	
	$(".card-expert").click((e) => {
		location.href = "${pageContext.request.contextPath}/page/expert";
	});
	
	$(".card-basic").hover(() => {
		    
	    $(".card-basic").animate({
	        right: "3px",
	        bottom : "7px",
	        opacity : "1"
	    },200)
	}, function(){
	    $(".card-basic").animate({
	        right: "-3px",
	        bottom : "-7px",
	        opacity : "0.8"
	    },200)
	});
	
	$(".card-advanced").hover(() => {
	    
	    $(".card-advanced").animate({
	        right: "3px",
	        bottom : "7px",
	        opacity : "1"
	    },200)
	}, function(){
	    $(".card-advanced").animate({
	        right: "-3px",
	        bottom : "-7px",
	        opacity : "0.8"
	    },200)
	});
	
	$(".card-expert").hover(() => {
	    
	    $(".card-expert").animate({
	        right: "3px",
	        bottom : "7px",
	        opacity : "1"
	    },200)
	}, function(){
	    $(".card-expert").animate({
	        right: "-3px",
	        bottom : "-7px",
	        opacity : "0.8"
	    },200)
	});
	
});

</script>

<div class = "titleContainer">
</div>

<div class="projectList">
	<div class="card-basic">
		<div class="card-basic-header">
			<span class="material-icons">forward_to_inbox</span>
		</div>
		
		<div class="card-basic-body">
			<label class = "title">Basic</label>
		</div>
		
	</div>
	
	<div class="card-advanced">
		<div class="card-advanced-header">
			<span class="material-icons">create_new_folder</span>
		</div>
		
		<div class="card-advanced-body">
			<label class = "title">Advanced</label>
		</div>
		
	</div>
	
	<div class="card-expert">
		<div class="card-expert-header">
			<span class="material-icons">move_to_inbox</span>

		</div>
		
		<div class="card-expert-body">
			<label class = "title">Expert</label>
		</div>
		
	</div>
	
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>