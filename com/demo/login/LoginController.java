package com.demo.login;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * UserController
 */
@Before(LoginInterceptor.class)
public class LoginController extends Controller {
	public void index() {
		setAttr("UserPage", Login.dao.paginate(getParaToInt(0, 1), 10, "select *", "from login order by loginid asc"));
		render("User.html");
	}
	
	public void add() {
		
	}
	
	@Before(LoginValidator.class)
	public void save() {
		Login User=getModel(Login.class);
		User.set("id", "id_seq.nextval");
		User.save();
		forwardAction("/User");
	}
	
	public void edit() {
		setAttr("User", Login.dao.findById(getParaToInt()));
	}
	
	@Before(LoginValidator.class)
	public void update() {
		Login User=getModel(Login.class);
		User.update();
		forwardAction("/User");
	}
	
	public void delete() {
		Login.dao.deleteById(getParaToInt());
		forwardAction("/User");
	}
}


