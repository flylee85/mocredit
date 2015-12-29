<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <base href="<%=basePath %>" />
    <title>亿美汇金</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="common/css/bootstrap.css">
    <link rel="stylesheet" href="common/css/font-awesome.min.css">
    <link rel="stylesheet" href="common/css/style.css">
    <link rel="stylesheet" href="common/css/plugin.css">

    <!--[if lt IE 9]>
    <script src="common/js/ie/respond.min.js"></script>
    <script src="common/js/ie/html5.js"></script>
    <![endif]-->
</head>
<body>
<input type="hidden" id="currentId">

<header id="header" class="navbar">
    <ul class="nav navbar-nav navbar-avatar pull-right">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <span class="hidden-sm-only">${username}</span>
                <span class="thumb-small avatar inline"><img src="common/images/avatar.jpg" alt="Mika Sokeil"
                                                             class="img-circle"></span>
                <b class="caret hidden-sm-only"></b>
            </a>
            <ul class="dropdown-menu">
                <li><a href="#">设置</a></li>
                <li><a href="#">配置文件</a></li>
                <li><a href="#"><span class="badge bg-danger pull-right">3</span>消息</a></li>
                <li class="divider"></li>
                <li><a href="docs.html">帮助</a></li>
                <li><a href="logout">退出</a></li>
            </ul>
        </li>
    </ul>
    <a class="navbar-brand" href="#">欢迎</a>
    <button type="button" class="btn btn-link pull-left nav-toggle hidden-lg"
            data-toggle="class:slide-nav slide-nav-left" data-target="body">
        <i class="icon-reorder icon-xlarge text-default"></i>
    </button>
    <ul class="nav navbar-nav hidden-sm">
        <li>
            <div class="m-t m-b-small" id="panel-notifications">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i
                        class="icon-comment-alt icon-xlarge text-default"></i><b
                        class="badge badge-notes bg-danger count-n">2</b></a>
                <section class="dropdown-menu m-l-small m-t-mini">
                    <section class="panel panel-large arrow arrow-top">
                        <header class="panel-heading bg-white"><span class="h5"><strong>你有 <span
                                class="count-n">2</span> 条通知</strong></span></header>
                        <div class="list-group list-group-flush m-t-n">
                            <a href="#" class="media list-group-item">
                                <span class="pull-left thumb-small"><img src="common/images/avatar.jpg" alt="John said"
                                                                         class="img-circle"></span>
                  <span class="media-body block m-b-none">
                    跳转到 Bootstrap 3.0<br>
                    <small class="text-muted">2013年5月23日</small>
                  </span>
                            </a>
                            <a href="#" class="media list-group-item">
                  <span class="media-body block m-b-none">
                    亿美汇金 v.1<br>
                    <small class="text-muted">2013年5月13日</small>
                  </span>
                            </a>
                        </div>
                        <footer class="panel-footer text-small">
                            <a href="#" class="pull-right"><i class="icon-cog"></i></a>
                            <a href="#">查看所有通知</a>
                        </footer>
                    </section>
                </section>
            </div>
        </li>
        <li class="dropdown shift" data-toggle="shift:appendTo" data-target=".nav-primary .nav">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i
                    class="icon-cog icon-xlarge visible-sm visible-sm-inline"></i>设置 <b
                    class="caret hidden-sm-only"></b></a>
            <ul class="dropdown-menu">
                <li>
                    <a href="#" data-toggle="class:navbar-fixed" data-target='body'>导航栏
                        <span class="text-active">自动</span>
                        <span class="text">固定</span>
                    </a>
                </li>
                <li class="visible-lg">
                    <a href="#" data-toggle="class:nav-vertical" data-target="#nav">菜单
                        <span class="text-active">竖向</span>
                        <span class="text">横向</span>
                    </a>
                </li>
                <li class="divider hidden-sm"></li>
                <li class="dropdown-header">颜色</li>
                <li>
                    <a href="#" data-toggle="class:bg bg-black" data-target='.navbar'>导航栏颜色
                        <span class="text-active">白色</span>
                        <span class="text">黑色</span>
                    </a>
                </li>
                <li>
                    <a href="#" data-toggle="class:bg-light" data-target='#nav'>菜单颜色
                        <span class="text-active">黑色</span>
                        <span class="text">白色</span>
                    </a>
                </li>
            </ul>
        </li>
    </ul>
    <%-- <form class="navbar-form pull-left shift" action="" data-toggle="shift:appendTo" data-target=".nav-primary">
       <i class="icon-search text-muted"></i>
       <input type="text" class="input-small form-control" placeholder="Search">
     </form>--%>
</header>
<!-- / header -->
<!-- nav -->
<nav id="nav" class="nav-primary visible-lg nav-vertical">
    <ul class="nav" data-spy="affix" data-offset-top="50">
        <li class="dropdown-submenu">
              <a href="enterprise.html" class="changePage"><i class="icon-th icon-xlarge"></i>企业管理</a>
          </li>
          <li class="dropdown-submenu">
              <a href="merchant.html" class="changePage"><i class="icon-th icon-xlarge"></i>商户管理</a>
          </li>
          <li class="dropdown-submenu">
              <a href="store.html" class="changePage"><i class="icon-th icon-xlarge"></i>门店管理</a>
          </li>
        <c:forEach items="${menus}" var="m">
            <li class="dropdown-submenu">
                <a href="${m.url}" class="changePage"><i class="icon-list icon-xlarge"></i>${m.name}</a>
                <c:if test="${!empty m.secondResourceList }">
                    <ul class="dropdown-menu">
                        <c:forEach items="${m.secondResourceList}" var="n">
                            <li><a href="${n.url}" class="changePage">${n.name}</a></li>
                        </c:forEach>
                    </ul>
                </c:if>
            </li>
            </li>
        </c:forEach>
    </ul>
</nav>
<!-- / nav -->
<section id="content"></section>
<!-- footer -->
<footer id="footer">
    <div class="text-center padder clearfix">

    </div>
</footer>
<!-- / footer -->

<!-- .modal 发送提示 -->
<div class="modal fade operaState in " data-keyboard="false" id="operaState">
    <div class="modal-dialog modal-sm" style="margin-left:-150px;">
        <div class="modal-content">
            <div class="modal-body pop-div-content">
                <p><i class="icon-success"></i></p>

                <p class="tipP">
                    <label id="promptMsg"></label>
                </p>
            </div>
        </div>
    </div>
</div>
<!-- /.modal 发送提示 -->
<script src="common/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="common/js/bootstrap.min.js" type="text/javascript"></script>
<script src="common/js/index.js" type="text/javascript"></script>
<!-- 验证 -->
<script src="common/js/parsley/parsley.js" type="text/javascript"></script>
<!-- checkbox -->
<script src="common/js/fuelux/fuelux.js" type="text/javascript"></script>

<!-- datatables -->
<script src="common/js/datatables/jquery.dataTables10.min.js" type="text/javascript"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=gOK4a4cwDT7Naf43I7SMWj7N"></script>

</body>
</html>