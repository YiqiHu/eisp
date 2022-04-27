//获取当前员工信息
function getExpressMessage () {
	$.ajax({
		//上传映射
		url: "/getStaffinfo",
		type: 'POST',
		async: false,
		processData: false,
		contentType: false, 
		success: function(data) {
			data=JSON.parse(data);
			//员工姓名往哪里放
			
		},
		error: function() {
			alert("查询失败！")
		}
	});
}
//获取当前订单信息,老板名获取全部，其余按名字
function getOrder (name,role) {
	var formData = new FormData();	
	if (rele="expressMan") {
		formData.append("expreName",);
	}
	
	$.ajax({
		//上传映射
		url: "/getOrder",
		type: 'POST',
		async: false,
		data: formData,
		processData: false, // 使数据不做处理
		contentType: false, // 不要设置Content-Type请求头
		success: function(data) {
			data=JSON.parse(data);
			for (var i = 0; i < data.obj.length; i++) {
				var name=data.obj[i].customer_name;
				$("#ExpressMans").append("<option value='"+name+"'>" + name + "</option>");
			}
		},
		error: function() {
			alert("查询失败！")
		}
	});
}