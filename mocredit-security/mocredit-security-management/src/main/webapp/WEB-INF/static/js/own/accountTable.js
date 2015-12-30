$('[data-ride="datatables"]').each(function() {
	var oTable = $(this).dataTable( {
		"processing": true,
		"ajax": {
			"url": "common/js/data/accountTable.json",
			"dataSrc": "aaData"
		},
		"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
		"paginationType": "full_numbers",
		"autoWidth":true,
		//"pageLength": 10,
		//"serverSide":true,
		"columns": [
			{ "data": "no" },
			{ "data": "contractName" },
			{ "data": "enterprise" },
			{ "data": "receivable" },
			{ "data": "returned" },
			{ "data": null }
		],
		"columnDefs": [
			{
				"render": function(oObj, sVal) {
					return '<a href="#showDetail" data-toggle="modal">查看详情</a>'
				},
				"sortable": false,
				"targets": 5
			}
		]

	} );
});
$('#showDetail').on('shown.bs.modal', function (e) {
	var relatedTarget = $(e.relatedTarget);
	var contractName = relatedTarget.parents("tr").find("td:eq(1)").text();
	$("#myModalLabel").text(contractName + " / 回款详情");
})
// 验证
var form = $("#addEnterprise").find("form").parsley();
$("#addEnterprise").on('hidden.bs.modal', function (e) {
	form.reset();
});
