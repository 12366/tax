package com.demo.common;

import com.demo.login.Login;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;


public class GlobalInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		// 得到Session
		Login loginUser = controller.getSessionAttr("loginUser");
		String methodName = ai.getMethodName();
		String controllerKey = ai.getControllerKey();
		if (loginUser != null) {
			ai.invoke();
		} else {
			if (methodName.equals("login")||controllerKey.equalsIgnoreCase("/common")) {
				ai.invoke();
			} else {
				controller.redirect("http://localhost/timeout");
			}
		}
	}
}
