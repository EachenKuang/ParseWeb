<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<meta name="keywords" content="ARDGS,自动报告数据生成系统,自动报告">
	<meta name="description" content="自动报告数据生成系统">
	<meta name="author" content="Eachen">

	<link rel="stylesheet" type="text/css" href="index.css">
	<script type="text/javascript" src="http://parse-bk.qiniudn.com/jquery.min.js"></script>
	<script type="text/javascript" src="index.js"></script>

	<title>自动报告数据生成系统</title>
</head>
<body>
	<div class="container">
		<div class="header">解析成功</div>
		<div class="download">
			<a href="../download">点击下载</a>
			<a href="../index.html">返回主页</a>
		</div>
		<div class="error-msg">
			<div class="title">错误信息：</div>
			<div class="msg"><%=session.getAttribute("error") %></div>
		</div>
		<div class="footer">
			<a href="http://cips.chinapublish.com.cn/" title="中国新闻出版研究院" target="_blank">中国新闻出版研究院</a>
		</div>
	</div>
</body>
</html>