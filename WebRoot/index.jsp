<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- Author:guanghe -->
<!DOCTYPE html>
<html>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="public/publicResource.jsp">
	<jsp:param name="title" value="首页" />
</jsp:include>
<body>
	<div id="wrapper">
		<jsp:include page="public/publicFrame.jsp">
			<jsp:param name="menu" value="0" />
		</jsp:include>
		<div id="page-wrapper">
			<div id="page-inner">
				<div class="row">
	                <div class="col-lg-12">
	                    <div class="panel panel-default">
	                    	<div class="panel-body">
                            	<h1>Welcome to Ether Wallet !</h1>
                            	<p>
                            		Free, open-source, client-side interface for generating Ethereum wallets & more. Interact with the Ethereum blockchain easily & securely.
                            	</p>
                          	</div>
	                    </div>
	                </div>
	            </div>
			</div>
		</div>
	</div>
</body>
</html>