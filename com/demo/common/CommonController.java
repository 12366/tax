package com.demo.common;

import com.demo.login.Login;
import com.demo.login.LoginValidator;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.ext.render.CaptchaRender;

/**
 * CommonController
 */
public class CommonController extends Controller {

	private static final String RANDOM_CODE_KEY = "1";

	@ActionKey("/")
	public void index() {
		render("login.html");
	}

	@Before(LoginValidator.class)
	@ActionKey("/login")
	public void login() {
		Login loginUser = getModel(Login.class);
		String username = loginUser.get("username");
		String password = loginUser.get("password");
		String inputRandomCode = getPara("inputRandomCode");
		boolean loginSuccess = CaptchaRender.validate(this, inputRandomCode,
				RANDOM_CODE_KEY);
		String sql = "select * from login where username=? and password=?";
		Object[] paras = new Object[2];
		paras[0] = username;
		paras[1] = password;
		Login login = Login.dao.findFirst(sql, paras);
		if (loginSuccess == false) {
			this.setAttr("msg", "验证码输入不正确,请重新输入!");
			this.setAttr("login", loginUser);
			render("login.html");
		}
		else if (login == null) {
			this.setAttr("msg", "用户名或密码不正确");
			this.setAttr("login", loginUser);
			render("login.html");
		} else {
            this.setAttr("msg", "正在登陆中,请稍后...");
            render("login.html");
			// 登录信息数据放至session
			setSessionAttr("loginUser", login);
			render("full.html");
		}
	}

	@ActionKey("/logout")
	public void logout() {
		// 删除session中的属性
		removeSessionAttr("loginUser");
		this.redirect("/");
	}

	public void img() {
		CaptchaRender img = new CaptchaRender(RANDOM_CODE_KEY);
		render(img);
	}

	@ActionKey("/session")
	public void session() {
		String josnStr = "";
		Login loginUser = getSessionAttr("loginUser");
		if (loginUser != null) {
			String username = loginUser.get("username");
			String password = loginUser.get("password");
			josnStr = "{\"username\": \"" + username + "\", \"password\":\""
					+ password + "\"}";
		}
		this.renderJson("user", josnStr);
	}

    @ActionKey("/timeout")
    public void timeout() {
        render("sessiontimeout.html");
    }

    @ActionKey("/main")
    public void main() {
        render("main.html");
    }
}
