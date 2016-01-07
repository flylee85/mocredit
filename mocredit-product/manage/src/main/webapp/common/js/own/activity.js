$(function () {
	var shref=window.location.href;
	var split=shref.split("#");
	var eid=0;
	if(split.length>1){
		eid=split[1];
	}
    var jifenTable, famaTable;

    function initJifenTab(page) {
        if ($.type(jifenTable) != 'object') {
            jifenTable = $('#jifen').find('table[data-ride="datatables"]').DataTable({
                "ajax": {
                    url: "activity/queryActivityPage?type=01",
                    type: "post",
                    data:function(data){
                    	if(eid!=0){
                    		data.enterpriseId=eid;
                    	}
                    	return data;
                    }
                },
                "processing": true,
                "serverSide": true,
                "pageLength": 10,
                "pagingType": "full_numbers",
                "searchDelay": 500,
                "displayStart": parseInt(page) * 10 - 10,
                "autoWidth": true,
                "dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
                "columns": [
                    {"data": "name", "name": "name", "className": "name", "width": "180px"},
                    {"data": "storeCount", "name": "storeCount", "width": "90px", "sortable": false},
                    {"data": "orderCount", "name": "orderCount", "width": "90px", "sortable": false},
                    {"data": "createtime", "name": "createtime", "width": "130px"},
                    {"data": null, "name": "endTime", "width": "150px"},
                    {"data": null, "width": "100px"},
                    {"data": null, "width": "150px"}
                ],
                "columnDefs": [
                    {
                        "targets": 4,
                        "data": null,
                        "render": function (data, type, full) {
                            var entTime = data['endTime'];
                            return entTime.substring(0, 10);
                        }
                    },
                    {
                        "targets": 5,
                        "data": null,
                        "sortable": false,
                        "render": function (data, type, full) {
                            var html = '<div class="switch demo3">';
                            if (data['status'] == "01") {
                                html += '<input type="checkbox" checked value="' + data['status'] + '" data-id="' + data['id'] + '">';
                            } else {
                                html += '<input type="checkbox" value="' + data['status'] + '" data-id="' + data['id'] + '">';
                            }
                            html += '<label><i data-on="启用" data-off="停用"></i></label>';
                            html += '</div>';
                            return html;
                        }
                    },
                    {
                        "targets": 6,
                        "data": null,
                        "sortable": false,
                        "render": function (data, type, full) {
                            return '<a href="javascript:openUpdateJifenActivity(\'' + data['id'] + '\',0)" >查看</a>' +
                                '<a href="javascript:openUpdateJifenActivity(\'' + data['id'] + '\',1)" >编辑</a>';
                        }
                    },
                ]
            });
        }
    }

    function initFamaTab(page) {
        if ($.type(famaTable) != 'object') {
            famaTable = $('#fama').find('table[data-ride="datatables"]').DataTable({
                "ajax": {
                    url: "activity/queryActivityPage?type=02",
                    type: "post",
                    data:function(data){
                    	if(eid!=0){
                    		data.enterpriseId=eid;
                    	}
                    	return data;
                    }
                },
                "processing": true,
                "serverSide": true,
                "autoWidth": true,
                "searchDelay": 500,
                "displayStart": parseInt(page) * 10 - 10,
                "pagingType": "full_numbers",
                "pageLength": 10,
                "dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
                "columns": [
                    {"data": "name", "name": "name", "className": "name", "width": "200px"},
                    {"data": "storeCount", "name": "storeCount", "width": "90px", "sortable": false},
                    {"data": "orderCount", "name": "storeCount", "width": "90px", "sortable": false},
                    {"data": "createtime", "name": "createtime", "width": "180px"},
                    {"data": null, "name": "endTime", "width": "130px"},
                    {"data": null, "width": "70px"},
                    {"data": null, "width": "120px"},
                    {"data": null, "width": "170px"}
                ],
                "columnDefs": [
                    {"searchable": false, "targets": [1, 2]},
                    {
                        "targets": 4,
                        "data": null,
                        "render": function (data, type, full) {
                            var entTime = data['endTime'];
                            return entTime.substring(0, 10);
                        }
                    },
                    {
                        "targets": 5,
                        "data": null,
                        "sortable": false,
                        "render": function (data, type, full) {
                            var html = '<div class="switch demo3">';
                            if (data['status'] == "01") {
                                html += '<input type="checkbox" checked value="' + data['status'] + '" data-id="' + data['id'] + '">';
                            } else {
                                html += '<input type="checkbox" value="' + data['status'] + '" data-id="' + data['id'] + '">';
                            }
                            html += '<label><i data-on="启用" data-off="停用"></i></label>';
                            html += '</div>';
                            return html;
                        }
                    }, {
                        "targets": 6,
                        "data": null,
                        "sortable": false,
                        "render": function (data, type, full) {
                            return '<a href="javascript:openUpdateFamaActivity(\'' + full['id'] + '\',1)" >编辑</a>';
                        }
                    }, {
                        "targets": 7,
                        "data": null,
                        "sortable": false,
                        "render": function (data, type, full) {
                            var href;
                            if (data['sendSmsType'] == "01") {
                                href = '<a href="tima.html?' + data['id'] + '"target="_blank" >提码</a>' +
                                    '<a href="tima.html?' + data['id'] + '"target="_blank" >批次管理</a>';
                            }
                            if (data['sendSmsType'] == "02") {
                                href = '<a href="sendCode/sendcode?id=' + data['id'] + '&type=' + data['sendSmsType'] + '"target="_blank" >短信发码</a>' +
                                    '<a href="sendCode/sendcode?id=' + data['id'] + '&type=' + data['sendSmsType'] + '"target="_blank" >批次管理</a>';
                            }
                            if (data['sendSmsType'] == "03") {
                                href = '<a href="sendCode/sendcode?id=' + data['id'] + '&type=' + data['sendSmsType'] + '"target="_blank" >彩信发码</a>' +
                                    '<a href="sendCode/sendcode?id=' + data['id'] + '&type=' + data['sendSmsType'] + '"target="_blank" >批次管理</a>';
                            }
                            if (data['sendSmsType'].indexOf("02") >= 0 && data['sendSmsType'].indexOf("03") >= 0) {
                                href = '<a href="sendCode/sendcode?id=' + data['id'] + '&type=' + data['sendSmsType'] + '"target="_blank" >短彩信发码</a>' +
                                    '<a href="sendCode/sendcode?id=' + data['id'] + '&type=' + data['sendSmsType'] + '"target="_blank" >批次管理</a>';
                            }
                            return href;
                        }
                    }
                ]
            });
        }
    }

    /**
     * 初始化积分表格
     */
    $('#tabToJifen').on('show.bs.tab', function (e) {
        initJifenTab(1);
    });
    /**
     * 初始化发码表格
     */
    $('#tabToFama').on('show.bs.tab', function (e) {
        initFamaTab(1);
    });
     initJifenTab(1);
});

