//Author:guanghe
//切换菜单
function operation(option) {
	switch(option) {
	case 0 : 
		window.location.href = "index.jsp";
		break;
	case 1 :
		window.location.href = "newAccount.jsp";
		break;
	case 2 :
		window.location.href = "getBalance.jsp";
		break;
	case 3 :
		window.location.href = "sendTransaction.jsp";
		break;
	case 4 :
		window.location.href = "getTransaction.jsp";
		break;
	}
}
//创建账户
function newAccount() {
	//调用创建用户接口
	
	var isSuccess = true;
	if(isSuccess) {
		$("#mainFrame").hide();
		$("#result").show();
		$("#result div div h2").html("用户创建成功");
		$("#result div div p").eq(0).html("用户id：0x76661de25502950ea9163119ca2c6a2bb815ae05");		
	} else {
		$("#mainFrame").hide();
		$("#result").show();
		$("#result div div h1").html("用户创建失败");
	}
}

//查询余额
function getBalance() {
	//调用查询用户余额接口
	$.ajax({
		url:"./queryBalance",
		type:"post",
		data:{option:"getBalance"},
		dataType:"json",
		timeout:7000,
		success:function(data){
			console.log(data)
			$("#mainFrame").hide();
			$("#result").show();
			$("#result div div h2").html("查询账户余额成功");
			$("#result div div p").eq(0).html("余额："+data.DATA+" eth");		
		
		},
		error:function(){
			console.log("456")
			$("#mainFrame").hide();
			$("#result").show();
			$("#result div div h2").html("查询余额失败");
			showError("系统异常，请稍后重试",10000)
		}
		
		
	});
	/*var isSuccess = true;
	if(isSuccess) {
		$("#mainFrame").hide();
		$("#result").show();
		$("#result div div h2").html("查询账户余额成功");
		$("#result div div p").eq(0).html("用户id：0x76661de25502950ea9163119ca2c6a2bb815ae05" + "<br/>" + "余额：3.00000030000 ether");		
	} else {
		$("#mainFrame").hide();
		$("#result").show();
		$("#result div div h1").html("查询余额失败");
	}*/
}

//发起转账
function sendTransaction() {
	//调用发起转账的接口
	
	var isSuccess = true;
	if(isSuccess) {
		$("#mainFrame").hide();
		$("#result").show();
		$("#result div div h2").html("发起转账成功");
		$("#result div div p").eq(0).html("转账人ID：0x76661de25502950ea9163119ca2c6a2bb815ae05" + "<br/>" + 
				"收款人ID：0x76661de25502950ea9163119ca2c6a2bb815ae05" + "<br/>" + 
				"转账金额: 1wei" + "<br/>" + "交易ID：0x88cb6e625b57cadd6d7f71872433c2e638014fca30e47c649f2831db79b54304");		
	} else {
		$("#mainFrame").hide();
		$("#result").show();
		$("#result div div h1").html("发起转账失败");
	}
}

//根据用户ID获取交易信息
function getTransaction() {
	//调用发起转账的接口
	var transactions = [{id:"0x88cb6e625b57cadd6d7f71872433c2e638014fca30e47c649f2831db79b54304",userid:"0x76661de25502950ea9163119ca2c6a2bb815ae05",amount:"3.0000",date:"2018-04-28"},
	                      {id:"0x88cb6e625b57cadd6d7f71872433c2e638014fca30e47c649f2831db79b54304",userid:"0x76661de25502950ea9163119ca2c6a2bb815ae05",amount:"3.0000",date:"2018-04-28"},
	                      {id:"0x88cb6e625b57cadd6d7f71872433c2e638014fca30e47c649f2831db79b54304",userid:"0x76661de25502950ea9163119ca2c6a2bb815ae05",amount:"3.0000",date:"2018-04-28"}];
	var dataList = "";
	for(var i = 0; i < transactions.length; i++) {
		if(i % 2 == 0) {
			dataList += "<tr class='odd gradeX'>" + 
				"<td>" + i + "</td>" + 
				/*"<td>" + transactions[i].userid + "</td>" + */
				"<td>" + "<a href='javascript:getTransactionDetail(" + JSON.stringify(transactions[i]) + ")'>" + transactions[i].id + "</a>" + "</td>" + 
				"<td>" + transactions[i].amount + "</td>" + 
				"<td>" + transactions[i].date + "</td>"; 
		} else {
			dataList += "<tr class='odd gradeC'>" + 
			"<td>" + i + "</td>" + 
			/*"<td>" + transactions[i].userid + "</td>" + */
			"<td>" + "<a href='javascript:getTransactionDetail(" + JSON.stringify(transactions[i]) + ")'>" + transactions[i].id + "</a>" + "</td>" + 
			"<td>" + transactions[i].amount + "</td>" + 
			"<td>" + transactions[i].date + "</td>"; 
		}
	}
	$("#tbody").html(dataList);
	var isSuccess = true;
	if(isSuccess) {
		$("#mainFrame").hide();
		$("#list").show();
		$("#result").show();
		$("#result div div h2").html("交易详情");
		$("#result div div p").eq(0).html("获取详细信息请点击交易ID");		
	} else {
		$("#mainFrame").hide();
		$("#list").show();
		$("#result").show();
	}
}

//展示转账详细信息
function getTransactionDetail(data) {
	$("#result div div p").eq(0).html("转账人ID：0x76661de25502950ea9163119ca2c6a2bb815ae05" + "<br/>" + 
			"收款人ID：0x76661de25502950ea9163119ca2c6a2bb815ae05" + "<br/>" + 
			"转账金额: 1 ether" + "<br/>" + "交易ID：0x88cb6e625b57cadd6d7f71872433c2e638014fca30e47c649f2831db79b54304");		
}

//切换根据用户id或交易id查询交易
function switchGetTransOpt() {
	var radios = $("#mainFrame input:radio[name='optionsRadiosInline']");
	if(radios.eq(0).attr("checked") == "checked") {		
		radios.eq(0).attr("checked",false);
		radios.eq(1).attr("checked",true);
		$("#mainFrame div form>div>label").eq(0).html("请输入用户ID");
		$("#mainFrame div form>button").html("获取用户交易信息");
	} else {
		radios.eq(0).attr("checked",true);
		radios.eq(1).attr("checked",false);
		$("#mainFrame div form>div>label").eq(0).html("请输入交易ID");
		$("#mainFrame div form>button").html("获取交易信息");
	}
}

function showError(text,time){
    $("#su-top-msg").slideDown("fast");
    $("#errorText").text(text);
    setTimeout( function() {
        $("#su-top-msg").slideUp()}, time
    );
}





















