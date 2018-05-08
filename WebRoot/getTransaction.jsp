<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- Author:guanghe -->
<!DOCTYPE html>
<html>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="public/publicResource.jsp">
	<jsp:param name="title" value="查询交易" />
</jsp:include>
<body>
	<div id="wrapper">
		<jsp:include page="public/publicFrame.jsp">
			<jsp:param name="menu" value="4" />
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
												<label>请输入交易ID</label> <input class="form-control">
											</div>
											<div class="form-group">
												<label></label> <label
													class="radio-inline"> <input type="radio"
													name="optionsRadiosInline" onchange="switchGetTransOpt()" id="optionsRadiosInline1"
													value="0" checked="checked">按交易ID查询
												</label> <label class="radio-inline"> <input type="radio"
													name="optionsRadiosInline" onchange="switchGetTransOpt()" id="optionsRadiosInline2"
													value="1">按用户ID查询
												</label>
											</div>
											<p class="help-block">&nbsp;</p>
											<button type="button" onclick="getTransaction()"
												class="btn btn-primary">获取用户交易信息</button>
										</form>
									</div>
								</div>
								<div class="row" id="list" style="display: none">
									<div class="col-md-12">
										<!-- Advanced Tables -->
										<div class="panel panel-default">
											<div class="panel-heading">交易信息</div>
											<div class="panel-body">
												<div class="table-responsive">
													<table
														class="table table-striped table-bordered table-hover"
														id="dataTables-example">
														<thead>
															<tr>
																<th>序号</th>
																<!-- <th>用户ID</th> -->
																<th>交易ID</th>
																<th>金额（ether）</th>
																<th>交易时间</th>
															</tr>
														</thead>
														<tbody id="tbody"></tbody>
													</table>
												</div>
											</div>
										</div>
										<!--End Advanced Tables -->
									</div>
								</div>
								<div class="row" id="result" style="display: none">
									<div class="col-md-12">
										<div class="jumbotron">
											<h2></h2>
											<p></p>
											<p>
												<a class="btn btn-primary btn-lg" onclick="operation(4)"
													role="button">返回</a>
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