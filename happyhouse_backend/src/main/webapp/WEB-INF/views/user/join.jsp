<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<script type="text/javascript">
        $(document).ready(function () {
        	var isId = false;
        	// 아이디 중복검사
        	$("#id").keyup(function () {
        		var ckid = $("#id").val();
        		if(ckid.length < 6 || ckid.length > 16) {
        			$("#idresult").text("아이디는 6자이상 16자이하입니다.").removeClass('text-primary').removeClass('text-danger').addClass('text-dark');
        			isId = false;
        		} else {
	                $.ajax({
	                	url: '${root}/user/idcheck',
	                	data: {'ckid': ckid},
	                  	type: 'GET',
	                  	dataType: 'json',
	                  	success: function (response) {
	                  		console.log(response);
	                    	var cnt = response.idcount;
	                    	if(cnt == 0) {
	                    		$("#idresult").text(ckid + "는 사용가능합니다.").removeClass('text-dark').removeClass('text-danger').addClass('text-primary');
	                    		isId = true;
	                    	} else {
	                    		$("#idresult").text(ckid + "는 사용할 수 없습니다.").removeClass('text-dark').removeClass('text-primary').addClass('text-danger');
	                    		isId = false;
	                    	}
	                  	}, 
	                  	error: function(request, status, error) {
	                  		console.log("status : " + request.status + "\tmsg : " + error);
	                  	}
					});
        		}
			}); 
        	
        	// 회원가입
            $("#registerBtn").click(function () {
                if (!$("#name").val()) {
                    alert("이름 입력!!!");
                    return;
                } else if (!isId) {
                    alert("아이디 확인!!!");
                    return;
                } else if (!$("#pw").val()) {
                    alert("비밀번호 입력!!!");
                    return;
                } else if ($("#pw").val() != $("#pwcheck").val()) {
                    alert("비밀번호 확인!!!");
                    return;
                } else {
                    $("#memberform").attr("action", "${root}/user/register").submit();
                }
            });
        });
    </script>

<div class="container text-center mt-3">
	<div class="col-lg-8 mx-auto">
		<h2 class="p-3 mb-3 shadow bg-light">
			<mark class="orange">회원가입</mark>
		</h2>
		<form id="memberform" class="text-left mb-3" method="post" action="">
			<input type="hidden" id="email" name="email">
			<div class="form-group">
				<label for="name">이름</label> <input type="text" class="form-control"
					id="name" name="name" placeholder="이름입력...">
			</div>
			<div class="form-group">
				<label for="id">아이디</label> <input type="text" class="form-control"
					id="id" name="id" placeholder="아이디입력...">
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
					id="addr" name="addr" placeholder="">
			</div>

			<div class="form-group">
				<label for="tel">전화번호</label> <input type="text"
					class="form-control" id="tel" name="tel" placeholder="">
			</div>

			<div class="form-group text-center">
				<button type="button" id="registerBtn"
					class="btn btn-outline-primary">회원가입</button>
				<button type="reset" class="btn btn-outline-danger">초기화</button>
			</div>
		</form>
	</div>
</div>

</body>
</html>