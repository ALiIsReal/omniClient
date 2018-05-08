<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- Author:guanghe -->
<!DOCTYPE html>
<html>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="public/publicResource.jsp">
	<jsp:param name="title" value="查询余额" />
</jsp:include>
<body>
	<div id="wrapper">
		<jsp:include page="public/publicFrame.jsp">
			<jsp:param name="menu" value="2" />
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
												<label>请输入账户ID</label> <input class="form-control">
												<p class="help-block">&nbsp;</p>
											</div>
											<button type="button" onclick="getBalance()" class="btn btn-primary">查询余额</button>
										</form>
									</div>
								</div>
								<div class="row" id="result" style="display:none">
									<div class="col-md-12">
										<div class="jumbotron">
											<h2></h2>
											<p></p>
											<p>
												<a class="btn btn-primary btn-lg" onclick="operation(2)" role="button">返回</a>
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