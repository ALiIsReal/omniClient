<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- Author:guanghe -->
<!DOCTYPE html>
<html>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="public/publicResource.jsp">
	<jsp:param name="title" value="发送交易" />
</jsp:include>
<body>
	<div id="wrapper">
		<jsp:include page="public/publicFrame.jsp">
			<jsp:param name="menu" value="3" />
		</jsp:include>
		<div id="page-wrapper">
			<div id="page-inner">
				<div class="row">
	                <div class="col-lg-12">
	                    <div class="panel panel-default">
	                    	<div class="panel-body">
                            <div class="row" id="mainFrame">
                                <div class="col-lg-6">
                                    <form role="form">
                                        <div class="form-group">
                                            <label>请输入收款人ID</label>
                                            <input class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label>请输入转账人ID</label>
                                            <input class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label>请输入转账人私钥</label>
                                            <input class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label>请输入转账金额（ether）</label>
                                            <input class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label>请输入您愿意支付的gas费用的价格（wei）</label>
                                            <input class="form-control">
                                            <p class="help-block">&nbsp;</p>
                                        </div>
                                        <button type="button" onclick="sendTransaction()" class="btn btn-primary">发起转账</button>
                                    </form>
                          		</div>
                          	</div>
                          	<div class="row" id="result" style="display:none">
									<div class="col-md-12">
										<div class="jumbotron">
											<h2></h2>
											<p></p>
											<p>
												<a class="btn btn-primary btn-lg" onclick="operation(3)" role="button">返回</a>
											</p>
										</div>
									</div>
								</div>
	                    </div>
	                </div>
	            </div>
			</div>
		</div>
	</div>
</body>

</html>