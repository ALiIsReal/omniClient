package com.tn.set.service.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.util.OmniClient;
import com.tn.set.service.util.client.base.Omni;

@WebServlet("/queryBalance")
public class QueryBalance extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Omni omni = OmniClient.getOmni();

	public QueryBalance() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String option = request.getParameter("option");
		JSONObject result = new JSONObject();
		if (option == null) {
			result.put("RETCODE", -1);
			result.put("RETMSG", "option is null");
			response.getWriter().print(result);
		} else {
			Double balance = omni.balance().send().getBalance();
			String transactionid = request.getParameter("transactionid");
			switch (option) {
			case "getBalance":
				result.put("RETCODE", 0);
				result.put("RETMSG", "success");
				result.put("DATA", balance);
				break;

			case "getTransByUserId":

				break;
			default:
				System.out.println("没有找到匹配项");
			}
			response.getWriter().print(result);
		}
		response.getWriter().close();
	}

}
