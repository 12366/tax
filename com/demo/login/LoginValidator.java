package com.demo.login;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * BlogValidator.
 */
public class LoginValidator extends Validator {

	protected void validate(Controller controller) {
		Login loginUser = new Login();
		String username = controller.getPara("login.username");
		String password = controller.getPara("login.password");
		String inputRandomCode = controller.getPara("inputRandomCode");
		loginUser.set("username", username);
		loginUser.set("password", password);
		controller.setAttr("login", loginUser);
		if (null == username || "".equals(username.trim())) {
			addError("msg", "请输入用户名!");
			return;
		}
		if (null == password || "".equals(password.trim())) {
			addError("msg", "请输入密码!");
			return;
		}
		if (null == inputRandomCode || "".equals(inputRandomCode.trim())) {
			addError("msg", "请输入验证码!");
			return;
		}
	}

	protected void handleError(Controller controller) {
		controller.keepModel(Login.class);
		controller.render("login.html");
	}
}
