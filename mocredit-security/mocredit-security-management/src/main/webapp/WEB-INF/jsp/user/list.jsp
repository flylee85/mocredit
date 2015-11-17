<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="mfn" uri="/WEB-INF/tld/mocredit-functions.tld" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    <%--<script src="${pageContext.request.contextPath}/static/js/bootstrap-table.20130913.js"/>--%>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/js/bootstrap-paginator.min.js">
    <style>
        ul.ztree {
            margin-top: 10px;
            border: 1px solid #617775;
            background: #f0f6e4;
            width: 220px;
            height: 200px;
            overflow-y: scroll;
            overflow-x: auto;
        }
    </style>
</head>
<body>

<c:if test="${not empty msg}">
    <div class="message">${msg}</div>
</c:if>
<div>
    <shiro:hasPermission name="user:create">
        <div class="row">
            <div class="form-group">
                <div class="col-md-12">
                    <div class="col-md-12 text-left">
                        <a class="btn btn-primary" data-toggle="modal"
                           data-target="#myModal"
                            <%--  href="${pageContext.request.contextPath}/user/create"--%>>添加用户</a>
                    </div>
                </div>
            </div>
        </div>
    </shiro:hasPermission>
    <div class="form-group">
        <div>
            <table <%--id="resultTable"--%> class="table table-bordered table-hover">
                <thead>
                <tr>
                    <th>用户编号</th>
                    <th>用户名</th>
                    <th>角色</th>
                    <th>添加时间</th>
                    <th>更新时间</th>
                    <th>状态</th>
                    <%--<th>所属组织</th>--%>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userList}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                            <%--<td>${mfn:organizationName(user.organizationId)}</td>--%>
                        <td>${user.roleName}</td>
                        <td>${user.ctime}</td>
                        <td>${user.utime}</td>
                        <td>
                            <c:if test="${!user.locked}">
                                启用
                            </c:if>
                            <c:if test="${user.locked}">
                                冻结
                            </c:if>
                        </td>
                        <td>
                            <shiro:hasPermission name="user:update">
                                <a href="${pageContext.request.contextPath}/user/${user.id}/update">修改</a>
                            </shiro:hasPermission>

                            <shiro:hasPermission name="user:delete">
                                <a href="${pageContext.request.contextPath}/user/${user.id}/delete">删除</a>
                            </shiro:hasPermission>

                                <%--<shiro:hasPermission name="user:update">
                                    <a href="${pageContext.request.contextPath}/user/${user.id}/changePassword">改密</a>
                                 </shiro:hasPermission>--%>
                        </td>
                    </tr>
                </c:forEach>
                <%--<script type="text/html">--%>
                <%--<tr>--%>
                <%--{{id}}--%>
                <%--</tr>--%>
                <%--</script>--%>
                </tbody>
            </table>
            <c:if test="${pageTotal>=1}">
                <ul class="pagination">
                    <li><a href="user?page=1">首页</a></li>
                    <c:if test="${page>1}">
                        <li><a href="user?page=${page-1 }">上一页</a></li>
                    </c:if>
                    <c:if test="${page<=1}">
                        <li class="disabled"><a href="#">上一页</a></li>
                    </c:if>
                    <li><a>${page}/${pageTotal}</a></li>
                    <c:if test="${pageTotal-page>=1}">
                        <li><a href="user?page=${page+1 }">下一页</a></li>
                    </c:if>
                    <c:if test="${pageTotal-page<1}">
                        <li class="disabled"><a href="#">下一页</a></li>
                    </c:if>
                    <li><a href="user?page=${pageTotal}">末页</a></li>
                </ul>
            </c:if>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" align="center">添加用户</h4>
            </div>
            <form action="user/create" method="post" class="form-horizontal">
                <div class="form-group has-success has-feedback">
                    <label class="control-label col-sm-3" for="username">用户名</label>

                    <div class="col-sm-9">
                        <input id="username" name="username"
                               type="text" prompt="填写用户名" min="1" max="64"
                               placeholder="填写用户名" class="form-control">
                    </div>
                </div>
                <div class="form-group has-success has-feedback">
                    <label class="control-label col-sm-3" for="password">密&nbsp;&nbsp;&nbsp;&nbsp;码</label>

                    <div class="col-sm-9">
                        <input id="password"
                               name="password" type="password" prompt="请密码" min="1"
                               max="64" placeholder="填写密码" class="form-control">
                    </div>
                </div>
                <div class="form-group has-success has-feedback">
                    <label class="control-label col-sm-3" for="roleId">角&nbsp;&nbsp;&nbsp;&nbsp;色</label>

                    <div class="col-sm-9">
                        <select id="roleId" name="roleId" class="form-control">
                            <option value="">请选择角色</option>
                            <c:forEach items="${roleList}" var="r">
                                <option value="${r.id}">${r.role}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">确认</button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script src="${pageContext.request.contextPath}/static/JQuery zTree v3.5.15/js/jquery.ztree.all-3.5.min.js"></script>
<script>
    /* var bsTable;
     $(function () {
     bsTable = $("#resultTable").bsTable({
     url: '${pageContext.request.contextPath}/user/list.json',
     ajaxType: "POST",  //ajax 提交方式 post 或者 get
     pageNo: 1,
     pageSize: 10,
     pagingAlign: "right",
     countRoot: "total",
     dataRoot: "list",
     count:1000,
     onStartLoad: function () {
     //                $("#loadMsg").css("display", 'block')
     },
     onDataShowComplete: function () {
     //                $("#loadMsg").css("display", 'none')
     },
     onLoadFail: function () {
     //                $("#loadMsg").css("display", 'none')
     //                $("#errorMsg").css("display", 'block')
     }
     });
     });*/
    $(function () {
        var setting = {
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: onClick
            }
        };

        var zNodes = [
            <c:forEach items="${organizationList}" var="o">
            <c:if test="${not o.rootNode}">
            {id:${o.id}, pId:${o.parentId}, name: "${o.name}"},
            </c:if>
            </c:forEach>
        ];

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("tree"),
                    nodes = zTree.getSelectedNodes(),
                    id = "",
                    name = "";
            nodes.sort(function compare(a, b) {
                return a.id - b.id;
            });
            for (var i = 0, l = nodes.length; i < l; i++) {
                id += nodes[i].id + ",";
                name += nodes[i].name + ",";
            }
            if (id.length > 0) id = id.substring(0, id.length - 1);
            if (name.length > 0) name = name.substring(0, name.length - 1);
            $("#organizationId").val(id);
            $("#organizationName").val(name);
            hideMenu();
        }

        function showMenu() {
            var cityObj = $("#organizationName");
            var cityOffset = $("#organizationName").offset();
            $("#menuContent").css({
                /*       left: cityOffset.left + "px",
                 top: cityOffset.top + "px"*/
            }).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);
        }

        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }

        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
                hideMenu();
            }
        }

        $.fn.zTree.init($("#tree"), setting, zNodes);
        $("#menuBtn").click(showMenu);
    });
</script>
</body>
</html>