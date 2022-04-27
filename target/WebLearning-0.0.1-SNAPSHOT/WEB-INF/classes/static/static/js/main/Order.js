var client = null;
var ID;
var wodedizhibu = false;
var jijianlianjie = false;


//注册
function register() {
	var formData = new FormData();
	var role = $("#role").val();
	var address = $("#address").val();
	formData.append("role", role);
	formData.append("customer_name", $("#fname").val());
	formData.append("mobile", $("#tel").val());
	formData.append("District", $("#addr-show02").val());
	formData.append("addresses", address);
	formData.append("password", $("#pass").val());
	if (role == "way") {
		formData.append("company", $("#Wcompany").val());
	} else if (role == "boss") {
		formData.append("company", $("#Bcompany").val());
	}
	formData.append("bossPhoNum", $("#bossNum").val());


	$.ajax({
		//上传映射
		url: "/register",
		type: 'POST',
		async: false,
		data: formData,
		processData: false, // 使数据不做处理
		contentType: false, // 不要设置Content-Type请求头
		success: function(data) {
			alert(data.msg);
		},
		error: function() {}
	});
}
//查询附近快递员

function queryExpreMan() {
	var formData = new FormData();
	formData.append("district", $("#sendDistrict").val());

	$.ajax({
		//上传映射
		url: "/queryExpreMan",
		type: 'POST',
		async: false,
		data: formData,
		processData: false, // 使数据不做处理
		contentType: false, // 不要设置Content-Type请求头
		success: function(data) {
			alert(data.msg);
			$("#douxing").empty();
			for (var i = 0; i < data.obj.length; i++) {
				var name = data.obj[i].customer_name;
				var company = data.obj[i].role;
				var grade = data.obj[i].password;
				var phoneNum = data.obj[i].mobile;
				$("#douxing").append("<option value =" + name + '#' + phoneNum + ">" + name + "  评分：" + grade + "  " + company +
					"</option>");
			}

		},
		error: function() {
			alert("查询失败！")
		}
	});
}

//连接后台socket，仅连接并监听那种，卵用没有
function connect() {
	if (jijianlianjie == false) {
		client = Stomp.over(new SockJS('/chat'));
		client.connect({}, function(frame) {
			client.subscribe('/user/queue/try', function(message) {
				// called when the client receives a STOMP message from the server
				$("#orderShow").append("<div>" + message.body + "</div>")
			});
		});
	};
	jijianlianjie = true;
}
//快递员的连接socket，添加了响应后的数据处理（仅限于订单处理）
function ExpressManConnect() {
	client = Stomp.over(new SockJS('/chat'));
	client.connect({}, function(frame) {
		client.subscribe('/user/queue/try', function(message) {
			// a 的href指向对应聊天div 与名字相关
			var ordermessage = JSON.parse(message.body);
			$("#message").append("<li><a href='#'><div class='notification-content'><small class='notification-timestamp pull-right'>"+ordermessage.order.orderTime+"</small><div class='notification-heading'>寄件订单</div><div class='notification-text'>"+ordermessage.from+" </div></div></a></li>"); 
			alert("有新订单，请查看！")
		});
	});
}
//商家获取快递员信息
function getExpreInfor() {
	$.ajax({
		url: "/getStaffinfo",
		type: 'POST',
		async: false,
		processData: false,
		contentType: false,
		success: function(data) {
			$("#ExpressManage").empty();
			for (var i = 0; i < data.length; i++) {
				$("#ExpressManage").append("<tr><td>" + data[i].customer_name +
					"</td><td><button type='button' class='badge badge-primary'>联系TA</button></td><td class='color-success' >" +
					data[i].mobile + "</td><td >" + data[i].addresses + "</td></tr>");
			}
		},
	});
}


//快递员刷新下单信息
function getOrder(role, state) {
	var formData = new FormData();
	formData.append("state", state);
	if (role == "expressMan") {
		if (state == 0) {
			$.ajax({
				//上传映射
				url: "/getOrder",
				type: 'POST',
				async: false,
				data: formData,
				processData: false, // 使数据不做处理
				contentType: false, // 不要设置Content-Type请求头
				success: function(data) {
					$("#OrderToSolve").empty();
					for (var i = 0; i < data.length; i++) {
						$("#OrderToSolve").append("<tr><td>" + data[i].sendName +
							"</td><td><button type='button' class='badge badge-primary'>联系TA</button></td><td class='color-primary'>" +
							data[i].sendPhoneNum + "</td><td>" + data[i].sendDistrict + data[i].sendAddress + "</td><td >" + data[i].receiveDistrict +
							data[i].receiveAddress +
							"</td><td><button type='button' id='processOrder' onclick = 'javascript:document.getElementById(\"Process" +
							i +
							"\").hidden=false;' class='badge badge-info'>处理订单</button><div id=\"Process" + i +
							"\" hidden=\"true\"><label style='font-size:16px'>价格：<input id='price" + i +
							"' style='height: 30px;width: 200px' type='text' class='form-control input-default ' placeholder='请输入订单价格'></label><div><input type='file' name='file' accept='image/jpeg' value='选择图片' /><button class='badge badge-primary' onclick='imageUpload(" +
							i +
							")'><i class='ti-new-window'>&nbsp;</i>上传图片</button><br></div><a class='badge badge-warning' href='' hidden='true' target='_blank'><i class='ti-gallery'>&nbsp;</i>查看图片</a><br><button class='badge badge-success' onclick='EMansubmit(" +
							i +
							")'><i class='ti-new-window'>&nbsp;</i>提交</button><button class='badge badge-success' disabled='true' onclick='printSome(" +
							i + ")' id='print" + i + "' value=''><i class='ti-printer'>&nbsp;</i>打印</button></div></td> </tr>"
						);
					}
				},
				error: function() {
					alert("查询失败！")
				}
			});
		} else {
			$.ajax({
				//上传映射
				url: "/getOrder",
				type: 'POST',
				async: false,
				data: formData,
				processData: false, // 使数据不做处理
				contentType: false, // 不要设置Content-Type请求头
				success: function(data) {
					$("#haddeal").empty();
					for (var i = 0; i < data.length; i++) {
						$("#haddeal").append("<tr><td>" + data[i].sendName + "</td><td class='color-primary'>" +
							data[i].sendPhoneNum + "</td><td >" + data[i].receiveDistrict +
							"</td><td>" + data[i].number + "</td><td>" + data[i].price + "</td></tr>"
						);
					}
				},
				error: function() {
					alert("查询失败！")
				}
			});
		}



	}

	if (role == "customer") {
		$.ajax({
			url: "/getOrder",
			type: 'POST',
			async: false,
			data: formData,
			processData: false, // 使数据不做处理
			contentType: false, // 不要设置Content-Type请求头
			success: function(data) {
				$("#orderhadsend").empty();
				for (var i = 0; i < data.length; i++) {
					$("#orderhadsend").append("<tr><td class='color-primary'>" + data[i].expressName +
						"</td><td><button type='button' class='badge badge-primary'>联系TA</button></td><td >" + data[i].receiveDistrict +
						"</td> </tr>");
				}
			},
			error: function() {
				alert("查询失败！")
			}
		});
	}
	if (role == "boss") {
		$.ajax({
			url: "/getOrder",
			type: 'POST',
			async: false,
			processData: false, // 使数据不做处理
			contentType: false, // 不要设置Content-Type请求头
			success: function(data) {
				$("#staffOrders").empty();
				for (var i = 0; i < data.length; i++) {
					$("#staffOrders").append("<tr><td class='color-primary'>" + data[i].expressName +
						"</td><td>" + data[i].number + "</td><td >" + data[i].price +
						"</td> </tr>");
				}
			},
			error: function() {
				alert("查询失败！")
			}
		});
	}
}


//快递员 提交处理后的订单（考虑快递员可以临时更改信息），返回打印内容
function EMansubmit(i) {
	var formData = new FormData();

	formData.append("sendName", $("#OrderToSolve td:eq(0)").text() + "#" + $("#OrderToSolve td:eq(2)").text());
	formData.append("price", $("#price" + i).val());
	formData.append("pictureLocation", $("#OrderToSolve td:eq(5) div a").prop("href"));

	$.ajax({
		//上传映射
		url: "/submit",
		type: 'POST',
		async: false,
		data: formData,
		processData: false, // 使数据不做处理
		contentType: false, // 不要设置Content-Type请求头
		success: function(data) {
			//返回的是订单图片的路径
			//打印可操作
			alert("提交成功！")
			$("#print" + i).prop("disabled", false);
			$("#print" + i).prop("value", data);
		},
		error: function() {}
	});
}

//收件
function toReceive(state) {
	var formData = new FormData();
	formData.append("state", state);
	if (state == 3) {
		$.ajax({
			url: "/getToReceive",
			type: 'POST',
			async: false,
			data: formData,
			processData: false, // 使数据不做处理
			contentType: false, // 不要设置Content-Type请求头
			success: function(data) {
				$("#getToReceive").empty();
				for (var i = 0; i < data.length; i++) {
					$("#getToReceive").append("<tr><td>" + data[i].sendName +
						"</td><td class='color-primary'>" + data[i].sendDistrict + data[i].sendAddress + "</td><td >" + data[i].number +
						"</td><td><button type='button' class='badge badge-primary'>查看详情</button></td></tr>"
					);
				}
			},
			error: function() {
				alert("无相关信息！")
			}
		});
	}
	if (state == 4) {
		$.ajax({
			url: "/getToReceive",
			type: 'POST',
			async: false,
			data: formData,
			processData: false, // 使数据不做处理
			contentType: false, // 不要设置Content-Type请求头
			success: function(data) {
				$("#hadReceive").empty();
				for (var i = 0; i < data.length; i++) {
					$("#hadReceive").append("<tr><td>" + data[i].expressName +
						"</td><td class='color-primary'>" + data[i].receivePhoneNum + "</td><td >" + data[i].number +
						"</td><td>" + data[i].solveTime +
						"</td><td><div><span class='pStars2'><span class='star1' v='1'></span><span class='star2' v='2'></span><span class='star3' v='3'></span> <span class = 'star4' v = '4' ></span><span class = 'star5'v = '5'></span> </span> <button type = 'button'id = 'commentOrder" +
						i + "' onclick = 'javascript:document.getElementById(\"comment" + i +
						"\").hidden=false;' class = 'badge badge-info' > 评价 </button><div id=\"comment" + i +
						"\" hidden=\"true\"><label style = 'font-size:14px'>参考标签：&nbsp;穿戴整齐；态度和蔼；按规定收费;</label><textarea id='PingLun' class='form-control input-default' onkeyup='keypress2()' onblur = 'keypress2()' style = 'resize:vertical;width: 300px;' placeholder = '对快递员此次服务还满意吗？快来评价吧'></textarea><label id = 'pinglun'> 100 </label><span>/ </span><label>100</label><button class = 'badge badge-success' id = 'cuotomer_submit' onclick = 'submitComments(" +
						i + ")'><i class='ti-new-window'></i>提交</button></div></div></td></tr>"
					);
				}
			},
			error: function() {
				alert("无相关信息！")
			}
		});
	}
}

//提交评价
function submitComments(i) {
	var formData = new FormData();
	formData.append("comment_time", $("#hadReceive tr:eq(" + i + ") td:eq(3)").text());
	formData.append("expreMan_name", $("#hadReceive tr:eq(" + i + ") td:eq(0)").text() + "#" + $("#hadReceive tr:eq(" + i +
		") td:eq(1)").text());
	formData.append("comments", $("#comment" + i + " textarea").val()); //拼接
	formData.append("number", $("#hadReceive tr:eq(" + i + ") td:eq(2)").text());
	formData.append("grade", $(".sactive").attr("v"));
	$.ajax({
		url: "/submitComments",
		type: 'POST',
		async: false,
		data: formData,
		processData: false, // 使数据不做处理
		contentType: false, // 不要设置Content-Type请求头
		success: function(data) {
			alert(data);
		}
	});
}

//重置密码
function changePass() {
	var formData = new FormData();
	formData.append("password", $("#resetPass").val());
	$.ajax({
		url: "/resetPass",
		type: 'POST',
		async: false,
		data: formData,
		processData: false, // 使数据不做处理
		contentType: false, // 不要设置Content-Type请求头
		success: function(data) {
			alert(data);
		},
	});
}
//获取当前用户信息
function getThePersonNow() {
	$.ajax({
		url: "/getCustomer",
		type: 'POST',
		async: false,
		processData: false, // 使数据不做处理
		contentType: false, // 不要设置Content-Type请求头
		success: function(customer) {
			$("#employeeName").val(customer.customer_name);
			$("#employeePhonenumber").val(customer.mobile);
			$("#employeeRegion").val(customer.addresses);
		},
	});
}
//客户 提交下单信息
function send() {
	var body = JSON.stringify({
		"from": null,
		"to": $("#douxing").val(),
		"order": {
			"sendName": $("#sendName").val(),
			"sendAddress": $("#sendAddress").val(),
			"sendDistrict": $("#sendDistrict").val(),
			"sendPhoneNum": $("#sendPhoneNum").val(),

			"receiveName": $("#receiveName").val(),
			"receiveAddress": $("#receiveAddress").val(),
			"receiveDistrict": $("#receiveDistrict").val(),
			"receivePhoneNum": $("#receivePhoneNum").val(),
			"ExpressMan": $("#douxing").val()
		}
	})
	client.send("/app/xiadan", {}, body);

}
//快递员 上传图片,返回图片路径放在imagePath里
function imageUpload(i) {
	var formData = new FormData();
	var a = $("#OrderToSolve td:eq(5) div div input");
	var image = a[0].files[0];
	formData.append("image", image);
	$.ajax({
		url: "/imageupload",
		type: 'POST',
		async: false,
		data: formData,
		processData: false, // 使数据不做处理
		contentType: false, // 不要设置Content-Type请求头
		success: function(data) {

			$("#OrderToSolve td:eq(5) div a").prop("hidden", false);
			$("#OrderToSolve td:eq(5) div a").prop("href", data);
			if (data == "上传失败！") {
				alert(data);
			} else {
				alert('上传成功！');
			}
		},
		error: function() {
			alert("上传失败！")
		}
	});
}




//打印，配合前端那个div
function printSome(i) { //传入文件路径 
	var wind = window.open($("#print" + i).prop("value"), 'newwindow',
		'height=300, width=700, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no'
	);
	wind.print();
}
//用户获取地址簿，至于直接填入，见到前端代码再写不迟
function getAddresses(type) {
	$.ajax({
		url: "/showAddresses",
		type: 'POST',
		async: false,
		processData: false,
		contentType: false,
		success: function(data) {
			if (type == 0) {
				$("#addresses").empty();
				for (var i = 0; i < data.length; i++) {
					$("#addresses").append("<tr><td>" + data[i].name + "</td><td>" + data[i].phoneNum +
						"</td><td class='color-success' >" + data[i].district + "</td><td >" + data[i].compleAddre +
						"</td><td></tr>");
				}
			} else {
				$("#addressToin").empty();
				for (var i = 0; i < data.length; i++) {
					$("#addressToin").append("<tr><td>" + data[i].name + "</td><td>" + data[i].phoneNum +
						"</td><td class='color-success' >" + data[i].district + "</td><td >" + data[i].compleAddre +
						"</td><td><button type='button' class='btn btn-primary btn-rounded m-b-10 m-l-5' id='" + i +
						"' onclick='impToSend(" + i +
						")'>寄件</button> <button type = 'button'class = 'btn btn-primary btn-rounded m-b-10 m-l-5' id='10" + i +
						"' onclick = 'impToSend(10" + i + ")'>收件</button></td> </tr>");
				}
			}

		},
	});
}



function impToSend(a) {
	if (a > 99) { //收件
		var i = a % 10;
		var receiveName = $("#addressToin tr:eq(" + i + ") td:eq(0)").text();
		var receivePhoneNum = $("#addressToin tr:eq(" + i + ") td:eq(1)").text();
		var receiveDistrict = $("#addressToin tr:eq(" + i + ") td:eq(2)").text();
		var address = $("#addressToin tr:eq(" + i + ") td:eq(3)").text();

		$("#receiveName").val(receiveName);
		$("#receivePhoneNum").val(receivePhoneNum);
		$("#receiveDistrict").val(receiveDistrict);
		$("#receiveAddress").val(address);
	} else {
		var sendName = $("#addressToin tr:eq(" + a + ") td:eq(0)").text();
		var sendPhoneNum = $("#addressToin tr:eq(" + a + ") td:eq(1)").text();
		var sendDistrict = $("#addressToin tr:eq(" + a + ") td:eq(2)").text();
		var address = $("#addressToin tr:eq(" + a + ") td:eq(3)").text();

		$("#sendName").val(sendName);
		$("#sendPhoneNum").val(sendPhoneNum);
		$("#sendDistrict").val(sendDistrict);
		$("#sendAddress").val(address);
	}
}
//添加到地址簿
function addTo(a) {
	var name;
	var phoneNum;
	var district;
	var compleAddre;

	if (a == 0) {
		district = $("#sendDistrict").val();
		compleAddre = $("#sendAddress").val();
		name = $("#sendName").val();
		phoneNum = $("#sendPhoneNum").val();
	} else {
		district = $("#receiveDistrict").val();
		compleAddre = $("#receiveAddress").val();
		name = $("#receiveName").val();
		phoneNum = $("#receivePhoneNum").val();
	}
	var formData = new FormData();
	formData.append("district", district);
	formData.append("compleAddre", compleAddre);
	formData.append("name", name);
	formData.append("phoneNum", phoneNum);

	$.ajax({
		url: "/addToAddress",
		type: 'POST',
		async: false,
		data: formData,
		processData: false,
		contentType: false,
		success: function(data) {
			alert(data);
		},
	});

}
//单号查询
function selectbynum(role) {
	var formData = new FormData();
	formData.append("number", $("#number").val());
	var a;
	if (role == "expressManpai" || role == "expressManqian") {
		a = "/final"
	}
	if (role == "way") {
		a = "/transfor"
	}

	$.ajax({
		url: a,
		type: 'POST',
		async: false,
		data: formData,
		processData: false, // 使数据不做处理
		contentType: false, // 不要设置Content-Type请求头
		success: function(response) {
			if (response.status == 500) {
				alert(response.msg);
			} else {
				if (role == "expressManpai" || role == "way") {
					$("#receiveName").val(response.obj.receiveName);
					$("#receivePhoneNum").val(response.obj.receivePhoneNum);
					$("#receiveAddress").val(response.obj.receiveDistrict + response.obj.receiveAddress);
				} else {
					$("#receiveName1").val(response.obj.receiveName);
					$("#receivePhoneNum1").val(response.obj.receivePhoneNum);
					$("#receiveAddress1").val(response.obj.receiveDistrict + response.obj.receiveAddress);
				}

			}

		},
	});
}
//物流更新
function update(role) {
	var formData = new FormData();
	formData.append("number", $("#number").val());
	formData.append("role", role);
	$.ajax({
		url: "/update",
		type: 'POST',
		data: formData,
		async: false,
		processData: false, // 使数据不做处理
		contentType: false, // 不要设置Content-Type请求头
		success: function(response) {
			alert(response);
		},
	});
}
$("#resetClick").click(function() {
	$("#resetdistric").prop("hidden", false);
});

/* $("#table").click(function() {
	$("#orderTable").prop("hidden", false);
}); */


































screenFuc();

function screenFuc() {
	var topHeight = $(".chatBox-head").innerHeight(); //聊天头部高度
	//屏幕小于768px时候,布局change
	var winWidth = $(window).innerWidth();
	if (winWidth <= 768) {
		var totalHeight = $(window).height(); //页面整体高度
		$(".chatBox-info").css("height", totalHeight - topHeight);
		var infoHeight = $(".chatBox-info").innerHeight(); //聊天头部以下高度
		//中间内容高度
		$(".chatBox-content").css("height", infoHeight - 46);
		$(".chatBox-content-demo").css("height", infoHeight - 46);

		$(".chatBox-list").css("height", totalHeight - topHeight);
		$(".chatBox-kuang").css("height", totalHeight - topHeight);
		$(".div-textarea").css("width", winWidth - 106);
	} else {
		$(".chatBox-info").css("height", 495);
		$(".chatBox-content").css("height", 448);
		$(".chatBox-content-demo").css("height", 448);
		$(".chatBox-list").css("height", 495);
		$(".chatBox-kuang").css("height", 495);
		$(".div-textarea").css("width", 260);
	}
}
(window.onresize = function() {
	screenFuc();
})();
//未读信息数量为空时
var totalNum = $(".chat-message-num").html();
if (totalNum == "") {
	$(".chat-message-num").css("padding", 0);
}
$(".message-num").each(function() {
	var wdNum = $(this).html();
	if (wdNum == "") {
		$(this).css("padding", 0);
	}
});


$("dr").click(function() {
	var n = $(this).index();
	$("collapse2").show();
});

//打开/关闭聊天框
$(".badge").click(function() {
	$(".chatBox").toggle(0);
	var n = $(this).index();
	$(".chatBox-head-one").show();
	$(".chatBox-head-two").show();
	$(".chatBox-list").show();
	$(".chatBox-kuang").show();

	//传名字
	$(".ChatInfoName").text($(this).children(".chat-name").children("p").eq(0).html());

	//传头像
	$(".ChatInfoHead>img").attr("src", $(this).children().eq(0).children("img").attr("src"));

	聊天框默认最底部
	$(document).ready(function() {
		$("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
	});
})


//返回列表
$(".chat-return").click(function() {
	$(".chatBox-head-one").toggle(1);
	$(".chatBox-head-two").toggle(1);
	$(".chatBox-list").fadeToggle(1);
	$(".chatBox-kuang").fadeToggle(1);
});

//      发送信息
$("#chat-fasong").click(function() {
	var textContent = $(".div-textarea").html().replace(/[\n\r]/g, '<br>')
	if (textContent != "") {
		$(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
			"<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
			"<div class=\"right\"> <div class=\"chat-message\"> " + textContent + " </div> " +
			"<div class=\"chat-avatars\"><img src=\"img/icon01.png\" alt=\"头像\" /></div> </div> </div>");
		//发送后清空输入框
		$(".div-textarea").html("");
		聊天框默认最底部
		$(document).ready(function() {
			$("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
		});
	}
});

//      发送表情
$("#chat-biaoqing").click(function() {
	$(".biaoqing-photo").toggle();
});
$(document).click(function() {
	$(".biaoqing-photo").css("display", "none");
});
$("#chat-biaoqing").click(function(event) {
	event.stopPropagation(); //阻止事件
});

$(".emoji-picker-image").each(function() {
	$(this).click(function() {
		var bq = $(this).parent().html();
		console.log(bq)
		$(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
			"<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
			"<div class=\"right\"> <div class=\"chat-message\"> " + bq + " </div> " +
			"<div class=\"chat-avatars\"><img src=\"img/icon01.png\" alt=\"头像\" /></div> </div> </div>");
		//发送后关闭表情框
		$(".biaoqing-photo").toggle();
		聊天框默认最底部
		$(document).ready(function() {
			$("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
		});
	})
});

$(document).ready(ExpressManConnect());

//      发送图片
function selectImg(pic) {
	if (!pic.files || !pic.files[0]) {
		return;
	}
	var reader = new FileReader();
	reader.onload = function(evt) {
		var images = evt.target.result;
		$(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
			"<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
			"<div class=\"right\"> <div class=\"chat-message\"><img src=" + images + "></div> " +
			"<div class=\"chat-avatars\"><img src=\"img/icon01.png\" alt=\"头像\" /></div> </div> </div>");
		聊天框默认最底部
		$(document).ready(function() {
			$("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
		});
	};
	reader.readAsDataURL(pic.files[0]);

}
