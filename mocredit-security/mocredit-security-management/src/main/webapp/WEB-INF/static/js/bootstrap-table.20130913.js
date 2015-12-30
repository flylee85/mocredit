/**
 *  一个table模版  ajax异步获取并展示数据
 *
 *
 * @author:lxl
 * @email:plmmilove@foxmail.com
 */
;
(function ($) {
    $.fn.bsTable = function (settings) {
        var _ = this;
        var defaults = {
            url: null,//ajax请求数据的地址
            ajaxType: "post",  //ajax 提交方式 post 或者 get
            param: null, //ajax 提交时的附加参数
            pageNo: 1,  //当前页码
            pageNoAlias: "pageNo", //ajax 提交时page参数的别名
            pageSize: 10, //显示时每页的大小
            pageSizeAlias: "pageSize", //ajax 提交时pageSize参数的别名
            dataRoot: "list", //json数据返回时root节点的名字
            isPaging: true,//是否显示分页
            pagingAlign: "right",//分页对齐方式 “right”/"left"
            countRoot: "count",//json数据返回时数据条数节点的名字
            count: 0,//数据总条数
            datas: null,//当前的列表数据
            template: null,//html模版
            onStartLoad: null,//ajax开始加载的回调函数, beforeSend 时调用
            onLoadSuccess: null,//ajax请求成功时调用（success）,参数为ajax请求返回的参数
            onDataShowComplete: null,//数据列表生成成功时调用
            onLoadFail: null,//ajax请求失败（error）时调用
            onInit: null//组件初始化是调用（init）
        }

        //初始化table
        _.init = function (el, s) {
            _.o = $.extend(defaults, s);
            var o = _.o;
            o.template = o.template || $("tbody script[type='text/html']", el).html();
            if (typeof o.onInit == "function") {
                o.onInit();
            }
            generateTable();
        }

        //根据参数重新加载table
        _.reload = function (param) {
            if (param != null) {
                _.o.param = param;
                _.gotoPage(1);
            }
        }

        //获取数据 生成table
        function generateTable(pageNo, pageSize) {
            var o = _.o;
            if (o.url != null && typeof o.url == 'string' && o.url.trim() != '') {
                pageNo = pageNo || o.pageNo || 1;
                pageSize = pageSize || o.pageSize || 10;
                o.pageNo = pageNo;

                var param = o.param || {};
                if (typeof param == 'string') {
                    param += "&" + o.pageNoAlias + "=" + pageNo
                        + "&" + o.pageSizeAlias + "=" + pageSize;
                } else {
                    param[o.pageNoAlias] = pageNo;
                    param[o.pageSizeAlias] = pageSize;
                }

                $.ajax({
                    url: o.url,
                    type: o.ajaxType.toUpperCase(),
                    async: true,
                    data: param || {},
                    beforeSend: function (e) {
                        if (typeof  o.onStartLoad == "function") {
                            o.onStartLoad(e);
                        }
                    },
                    success: function (data) {
                        if (typeof o.onLoadSuccess == 'function') {
                            o.onLoadSuccess(data);
                        }

                        _.load(data);
                    },
                    error: function (e) {
                        if (typeof  o.onLoadFail == "function") {
                            o.onLoadFail(e);
                        }
                    }
                });
            }
        }

        //生成li元素
        function li(parentEl, cls, clickFunc, content, paging) {
            var li = $("<li></li>");
            if (cls != null) {
                li.addClass(cls);
            }

            if (typeof clickFunc == 'function') {
                li.on("click", clickFunc);
            }

            if (paging != null) {
                li.attr("data-page", paging);
            }

            $("<a href='javascript:void(0);'>" + content + "</a>").appendTo(li);
            li.appendTo(parentEl);
        }

        //生成分页组件
        function generatePagination() {
            var o = _.o;
            if (!o.isPaging) return;

            var el = $(_);
            var foot = $("tfoot", el);
            if (foot.length == 0) {
                foot = $("<tfoot></tfoot>");
                foot.appendTo(el);
            }

            var cols = $("tbody > tr:first > td", el).length;
//            var cols = $("tr td", $(o.template)).length;
            var row = $("<tr><td colspan='" + cols + "'></td></tr>").appendTo(foot);
            var td = $("td", row);
            $(".pagination", foot).parents("tr:first").remove();

            var ul = $("<ul></ul>").addClass("pagination");
            o.pagingAlign == "right" ? ul.addClass("pull-right") : true;
            ul.appendTo(td);

            var pageCount = Math.ceil(o.count / o.pageSize);
            if (o.pageNo <= 1) {
                li(ul, "disabled", null, "&laquo;&nbsp;上一页");
            } else {
                li(ul, null, function () {
                    _.previousPage();
                }, "&laquo;&nbsp;上一页");
            }

            var start = 1;
            if (o.pageNo >= 4) {
                start = o.pageNo - 1;
                li(ul, null, function () {
                    _.gotoPage(1);
                }, "1", 1);

                li(ul, "disabled", null, "...");
            }

            var end = o.pageNo + 1;
            end > pageCount ? end = pageCount : true;
            for (var i = start; i <= end; i++) {
                if (o.pageNo == i) {
                    li(ul, "active", null, i, null);
                } else {
                    li(ul, null, function () {
                        _.gotoPage(parseInt($(this).attr("data-page")));
                    }, i, i);
                }
            }

            if (end < pageCount - 1) {
                li(ul, "disabled", null, "...", null);
            }

            if (end < pageCount) {
                li(ul, null, function () {
                    _.gotoPage(parseInt($(this).attr("data-page")));
                }, pageCount, pageCount);
            }

            if (o.pageNo >= pageCount) {
                li(ul, "disabled", null, "下一页&nbsp;&raquo;");
            } else {
                li(ul, null, function () {
                    _.nextPage();
                }, "下一页&nbsp;&raquo;");
            }

            var div = $("<div></div>").css("display", "inline-block");
            $("<label>跳转到：</label>").appendTo(div);
            $("<input type='text' value='" + o.pageNo + "'>").addClass("input-sm").css("width", "60px").appendTo(div);
            $("<input type='button' value='GO'>").addClass("btn btn-primary").on("click",function () {
                _.gotoPage(parseInt($("input:first", div).val()));
            }).css("width", "60px").appendTo(div);
            $('<label>共' + o.count + '条记录</label>').appendTo(div);
            div.appendTo(ul);
        }

        function getData(data, s) {
            var arr = s.split(".");
            for (var k in arr) {
                if (data != null) {
                    data = data[arr[k].trim()];
                } else {
                    return null;
                }
            }
            return data;
        }

        function handle(data, s) {
            var index = s.indexOf("!");
            if (index > 0) {
                var d = getData(data, s.substring(0, index));
                if (d == null) {
                    return eval(s.substring(index + 1));//转换为值
                } else {
                    return d;
                }
            } else {
                index = s.indexOf("?");
                if (index > 0) {
                    var d = getData(data, s.substring(0, index));//获取数据
                    var _tempFunc = eval(s.substring(index + 1));//获取方法
                    if (typeof _tempFunc == 'function') {
                        return _tempFunc(d);
                    } else {
                        return "";
                    }
                } else {
                    return getData(data, s);
                }
            }
        }

        _.load = function (data) {
            var o = _.o;
            o.count = getData(data, o.countRoot);
            o.datas = getData(data, o.dataRoot);

            //转换string类型的json数据为object
            if (typeof  o.datas == 'string') {
                o.datas = eval("(" + o.datas + ")");
            }

            var output = "";
            for (var d in o.datas) {
                var data = o.datas[d];
                data._auto_increment_index = (o.pageNo - 1) * o.pageSize + 1 + parseInt(d);
                data._self_data = data;
                output += o.template.replace(/\{\{.+\}\}/g, function (word) {
                    return handle(data, word.substr(2, word.length - 4));
                });
            }

            var el = $(_);
            $("tbody", el).html(output);
            generatePagination();
            if (typeof o.onDataShowComplete == 'function') {
                o.onDataShowComplete();
            }
        }

        //下一页
        _.nextPage = function () {
            var pageNo = _.o.pageNo + 1;
            var maxPage = Math.ceil(_.o.count / _.o.pageSize);
            pageNo > maxPage ? pageNo = maxPage : true;
            generateTable(pageNo);
        }

        //改变分页大小
        _.changePageSize = function (n) {
            n = parseInt(n);
            if (n <= 0) {
                return;
            }
            _.o.pageSize = n;
            _.gotoPage(1);
        }

        //前一页
        _.previousPage = function () {
            var pageNo = _.o.pageNo - 1;
            pageNo < 1 ? pageNo = 1 : true;
            generateTable(pageNo);
        }

        //跳转到指定的页
        _.gotoPage = function (n) {
            if (n == null) return;
            var maxPage = Math.ceil(_.o.count / _.o.pageSize);
            n > maxPage ? n = maxPage : true;
            n < 1 ? n = 1 : true;
            generateTable(n);
        }

        //获取列表数据
        _.getDatas = function () {
            return _.o.datas;
        }

        //获取记录总条数
        _.getCount = function () {
            return _.o.count;
        }

        //获取页码
        _.getPageNo = function () {
            return _.o.pageNo;
        }

        _.init($(this), settings);
        return _;
    };
})(jQuery);