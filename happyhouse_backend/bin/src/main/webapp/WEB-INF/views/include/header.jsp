<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SSAFY</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="${root}/css/common.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script src="https://kit.fontawesome.com/6478f529f2.js"crossorigin="anonymous"></script>
</head>
<body>

		<style>
			.navbar {
				background-color: #c0f2ae;
			}
			.navbar-nav li {
				margin-top: auto;
				margin-bottom: auto;
			}
			.navbar-nav i {
				margin-right: 7px;
			}
			.user {
				margin-right: 7px;
			}
			#banner {
				object-fit: cover;
    			opacity: 0.8;
			}
		</style>

<!-- header nav start -->
	<div>
		
		<nav class="navbar navbar-expand-sm navbar-light rounded" >
		
			<div class="container">
				<ul class="navbar-nav">
					<li class="nav-item navbar-brand active">
						<a class="nav-link" href="/">Home</a>
					</li>
					<li class="nav-item dropdown navbar-brand active">
						<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
							미니 게임</a>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="/game/omok">오목</a>
							<a class="dropdown-item" href="#">사다리 타기</a>
							<a class="dropdown-item" href="#">준비중...</a>
							<a class="dropdown-item" href="#">준비중...</a>
							<!-- <a class="dropdown-item" href="#">상권 정보</a>
							<a class="dropdown-item" href="#">환경 정보</a> -->
						</div>
					</li>
					<li class="nav-item">
						<a class="nav-link navbar-brand active" href="/board/list">공지사항</a>
					</li>
					<li class="nav-item">
						<a class="nav-link navbar-brand active" href="#">뉴스</a>
					</li>
					<li class="nav-item">
						<a class="nav-link navbar-brand active" href="#">게시판</a>
					</li>
					
				</ul>
				
				<ul class="navbar-nav mx-auto">
					<img id="logo" src="/img/happyhouselogo.png" height="150px" weight="150px">
				</ul>
		
				<ul class="navbar-nav">
					<c:if test="${empty userinfo}">
						
							<i class="far fa-user fa"></i> <a class="user" href="/user/register" style="color: black">회원가입</a>
						
				      		<i class="fas fa-lock"></i> <a class="user" href="/user/login" style="color: black">로그인</a><br />
				      	
			      	</c:if>
			      	
					<c:if test="${!empty userinfo}">
					
						<strong>${userinfo.name}</strong>(${userinfo.id})님 반갑습니다.<br/>
					
						
				      		<i class="fas fa-user-alt-slash"></i><a class="user" href="/user/logout" style="color: black">로그아웃</a>
				      		<i class="far fa-user fa"></i> <a class="user" href="/user/info" style="color: black">회원정보</a>
				      	
				      	<c:if test="${userinfo.id eq 'admin1'}">
				      	
				      		<i class="fas fa-users"></i><a class="user" href="${root}/user/list" style="color: black">회원목록</a><br />
				      	
				      	</c:if>
	      			</c:if>			
				</ul>
				
				
				<%-- <ul class="navbar-nav">
					<c:if test="${empty userinfo}">
						<li class="nav-item">
							<i class="far fa-user fa"></i> <a href="/user/register" style="color: black">회원가입</a>&nbsp;&nbsp;&nbsp;&nbsp;
						</li>
						<li class="nav-item">
				      		<i class="fas fa-lock"></i> <a href="/user/login" style="color: black">로그인</a><br />
				      	</li>
			      	</c:if>
			      	
					<c:if test="${!empty userinfo}">
						<strong>${userinfo.name}</strong>(${userinfo.id})님 반갑습니다.&nbsp;<br />
						<li class="nav-item">
				      		<i class="fas fa-user-alt-slash"></i><a href="/user/logout" style="color: black">로그아웃</a>&nbsp;&nbsp;&nbsp;&nbsp;
				      	</li>
				      	<li class="nav-item">
				      		<i class="far fa-user fa"></i> <a href="/user/info" style="color: black">회원정보</a>&nbsp;&nbsp;&nbsp;&nbsp;
				      	</li>
				      	<c:if test="${userinfo.id eq 'admin1'}">
				      	<li class="nav-item">
				      		<i class="fas fa-users"></i><a href="${root}/user/list" style="color: black">회원목록</a><br />
				      	</li>
				      </c:if>
	      			</c:if>			
				</ul> --%>
				
				
			</div>
			
			
			
		</nav>
		<!-- nav-bar 끝 -->
		
	
		<img id="banner" src="/img/banner.jpeg" width="100%"  height="300px">
		
	

	</div>
	<!-- header nav end -->