<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<script type="text/javascript">
$(document).ready(function () {
	
	// 수정하기
    $("#updateBtn").click(function () {
    	alert("update 클릭!!!");
        if (!$("#name").val()) {
            alert("이름 입력!!!");
            return;
        } else if (!$("#pw").val()) {
            alert("비밀번호 입력!!!");
            return;
        } else if ($("#pw").val() != $("#pwcheck").val()) {
            alert("비밀번호 확인!!!");
            return;
        } else if (!$("#addr").val()) {
            alert("주소 입력!!!");
            return;
        } else if (!$("#tel").val()) {
            alert("전화번호 입력!!!");
            return;
        } else {
        	alert("update 실행!!!");
            $("#memberform").attr("action", "${root}/user/modify").submit();
        }
    });
 	
 	// 탈퇴하기
    $("#deleteBtn").click(function () {
    	alert("delete 클릭!!!");
        if (!$("#pw").val()) {
            alert("비밀번호 입력!!!");
            return;
        } else if ($("#pw").val() != $("#pwcheck").val()) {
            alert("비밀번호 확인!!!");
            return;
        } else {
            $("#memberform").attr("action", "${root}/user/delete").submit();
        }
    });
 	
 	// 돌아가기
    $("#returnBtn").click(function () {
        history.go(-1);
    }); 	
 	
});
</script>

<div class="container text-center mt-3">
	<div class="col-lg-8 mx-auto">
		<h2 class="p-3 mb-3 shadow bg-light">
			<mark class="orange">회원정보</mark>
		</h2>
		<form id="memberform" class="text-left mb-3" method="post" action="">
			<input type="hidden" id="email" name="email">
			<div class="form-group">
				<label for="name">이름</label> <input type="text" class="form-control"
					id="name" name="name" value="${userinfo.name}">
			</div>
			<div class="form-group">
				<label for="id">아이디</label> <input type="text" class="form-control"
					id="id" name="id" value="${userinfo.id}" readonly>
				<div id="idresult" class="mt-1"></div>
			</div>
			<div class="form-group">
				<label for="pw">비밀번호</label> <input type="password"
					class="form-control" id="pw" name="pw" placeholder="비밀번호입력...">
			</div>
			<div class="form-group">
				<label for="pwcheck">비밀번호재입력</label> <input type="password"
					class="form-control" id="pwcheck" name="pwcheck"
					placeholder="비밀번호재입력...">
			</div>
			<div class="form-group">
				<label for="addr">주소</label> <input type="text" class="form-control"
					id="addr" name="addr" value="${userinfo.addr}">
			</div>

			<div class="form-group">
				<label for="tel">전화번호</label> <input type="text"
					class="form-control" id="tel" name="tel" value="${userinfo.tel}">
			</div>

			<div class="form-group text-center">
				<button type="button" id="updateBtn"
					class="btn btn-outline-primary">수정하기</button>
				<button type="button" id="deleteBtn" 
					class="btn btn-outline-danger">탈퇴하기</button>
				<button type="button" id="returnBtn"
					class="btn btn-outline-success">돌아가기</button>
			</div>
		</form>
	</div>
</div>

</body>
</html>