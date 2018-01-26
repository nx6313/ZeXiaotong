<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>无操作权限</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.8.2.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style_error.css" />
<script type="text/javascript">
	$(function(){
		parent.closeLoading();
	});
</script>

</head>
<body>
	<div id="container">
		<img class="png"
			src="${pageContext.request.contextPath}/images/error/404.png" /> <img
			class="png msg"
			src="${pageContext.request.contextPath}/images/error/404_msg.png" />
		<p>
			<a href="skipIndex" target="_parent"><img class="png"
				src="${pageContext.request.contextPath}/images/error/404_to_index.png" /></a>
		</p>
	</div>
	<div id="cloud" class="png"></div>
</body>
</html>
