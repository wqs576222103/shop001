<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>羽帆商城后台管理系统</title>
<jsp:include page="/commons/common-js.jsp"></jsp:include>
<style type="text/css">
.content {
	padding: 10px 10px 10px 10px;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"
		style="height: 60px; padding: 5px; background: #F3F3F3">
		<span class="northTitle">羽帆商城后台管理系统</span> <span class="loginInfo">登录用户：admin&nbsp;&nbsp;姓名：管理员&nbsp;&nbsp;角色：系统管理员</span>
	</div>

	<div data-options="region:'west',title:'菜单',split:true"
		style="width: 180px;">
		<ul id="menu" class="easyui-tree"
			style="margin-top: 10px; margin-left: 5px;">
			<li><span>商品管理</span>
				<ul>
					<li data-options="attributes:{'url':'${pageContext.request.contextPath}/page/item-add'}">新增商品</li>
					<li data-options="attributes:{'url':'${pageContext.request.contextPath}/page/item-list'}">查询商品</li>
					<li data-options="attributes:{'url':'${pageContext.request.contextPath}/page/item-param-list'}">规格参数</li>
				</ul></li>
			<li><span>网站内容管理</span>
				<ul>
					<li data-options="attributes:{'url':'${pageContext.request.contextPath}/page/content-category'}">内容分类管理</li>
					<li data-options="attributes:{'url':'${pageContext.request.contextPath}/page/content'}">内容管理</li>
				</ul></li>
			<li><span>索引库管理</span>
				<ul>
					<li data-options="attributes:{'url':'${pageContext.request.contextPath}/page/index-item'}">solr索引库维护</li>
				</ul></li>
		</ul>
	</div>

	<!-- 页脚信息 -->
	<div data-options="region:'south',border:false"
		style="height: 20px; background: #F3F3F3; padding: 2px; vertical-align: middle;">
		<span id="sysVersion">	系统版本：V1.0</span> <span id="nowTime"></span>
	</div>
	<div data-options="region:'center',title:''">
		<div id="tabs" class="easyui-tabs">
			<div title="首页" style="padding: 20px;"></div>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#menu').tree({
				onClick : function(node) {
					if ($('#menu').tree("isLeaf", node.target)) {
						var tabs = $("#tabs");
						var tab = tabs.tabs("getTab", node.text);
						if (tab) {
							tabs.tabs("select", node.text);
						} else {
							tabs.tabs('add', {
								title : node.text,
								href : node.attributes.url,
								closable : true,
								bodyCls : "content"
							});
						}
					}
				}
			});
		});
	</script>
</body>
</html>