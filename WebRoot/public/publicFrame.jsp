<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Author:guanghe -->
<nav class="navbar navbar-default top-navbar" role="navigation">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".sidebar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="#">EtherWallet</a>
	</div>
	<ul class="nav navbar-top-links navbar-right">
		<!-- /.dropdown -->
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#" aria-expanded="false"> <i
				class="fa fa-tasks fa-fw"></i>币种选择&nbsp;<i class="fa fa-caret-down"></i>
		</a>
			<ul class="dropdown-menu dropdown-tasks"
				style="min-width: 100px; width: 122px; text-align: center">
				<li><a href="#">
						<div>
							<p>
								<strong>BTC</strong>
							</p>
						</div>
				</a></li>
				<li class="divider"></li>
				<li><a href="#">
						<div>
							<p>
								<strong>ETH</strong>
							</p>
						</div>
				</a></li>
				<li class="divider"></li>
				<li><a href="#">
						<div>
							<p>
								<strong>USDT</strong>
							</p>
						</div>
				</a></li>
				<li class="divider"></li>
				<li><a href="#">
						<div>
							<p>
								<strong>EOS</strong>
							</p>
						</div>
				</a></li>
				<li class="divider"></li>
				<li><a href="#">
						<div>
							<p>
								<strong>NEO</strong>
							</p>
						</div>
				</a></li>
			</ul> <!-- /.dropdown-tasks --></li>
	</ul>
</nav>
<nav class="navbar-default navbar-side" role="navigation">
	<div class="sidebar-collapse">
		<ul class="nav" id="main-menu">
			<li><a href="index.jsp" class="<%="0".equals(request.getParameter("menu"))?"active-menu":"" %>"><i
					class="fa fa-fw fa-file"></i>简介</a></li>
			<li><a href="newAccount.jsp" class="<%="1".equals(request.getParameter("menu"))?"active-menu":"" %>"><i
					class="fa fa-dashboard"></i>创建账户</a></li>
			<li><a href="getBalance.jsp" class="<%="2".equals(request.getParameter("menu"))?"active-menu":"" %>"><i
					class="fa fa-desktop"></i>查询余额</a></li>
			<li><a href="sendTransaction.jsp" class="<%="3".equals(request.getParameter("menu"))?"active-menu":"" %>"><i
					class="fa fa-bar-chart-o"></i>发起交易</a></li>
			<li><a href="getTransaction.jsp" class="<%="4".equals(request.getParameter("menu"))?"active-menu":"" %>"><i
					class="fa fa-qrcode"></i>查询交易</a></li>
		</ul>
	</div>
</nav>