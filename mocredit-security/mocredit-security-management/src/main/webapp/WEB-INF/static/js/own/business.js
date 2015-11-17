$("#mendian").find('[data-ride="datatables"]').each(function() {
	var oTable = $(this).dataTable( {
		"processing": true,
		"ajax": {
			"url": "common/js/data/mendian.json",
			"dataSrc": "aaData"
		},
		"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
		"paginationType": "full_numbers",
		"autoWidth":true,
		//"pageLength": 5,
		//"serverSide":true,
		"columns": [
			{ "data": "mendianName" },
			{ "data": "shangjiaBelong" },
			{ "data": "province" },
			{ "data": "city" },
			{ "data": "pos" },
			{ "data": null }
		],
		"columnDefs": [
			{
				"render": function(oObj, type, full, meta ) {
					return '<a href="#addMendian" data-toggle="modal">查看</a>'
						+ '<a href="#addMendian" data-toggle="modal">编辑</a>'
						+ '<a href="#">删除</a>';
				},
				"sortable": false,
				"targets": 5
			}
		]

	} );
});

$("#shangjia").find('[data-ride="datatables"]').each(function() {
	var oTable = $(this).dataTable( {
		"processing": true,
		"ajax": {
			"url": "common/js/data/shangjia.json",
			"dataSrc": "aaData"
		},
		"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
		"paginationType": "full_numbers",
		"autoWidth":true,
		//"pageLength": 5,
		//"serverSide":true,
		"columns": [
			{ "data": "shangjiaName" },
			{ "data": "createTime" },
			{ "data": "mendianCount" },
			{ "data": null }
		],
		"columnDefs": [
			{
				"render": function(oObj, type, full, meta ) {
					return '<a href="#addShangjia" data-toggle="modal">查看</a>'
						+ '<a href="#addShangjia" data-toggle="modal">编辑</a>'
						+ '<a href="#">删除</a>';
				},
				"sortable": false,
				"targets": 3
			}
		]

	} );
});

