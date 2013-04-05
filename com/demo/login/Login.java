package com.demo.login;

import com.jfinal.plugin.activerecord.Model;

/**
 * User model.
 */
@SuppressWarnings("serial")
public class Login extends Model<Login> {
	
	public static final Login dao = new Login();
	
	 private String inputRandomCode;

	public String getInputRandomCode() {
		return inputRandomCode;
	}

	public void setInputRandomCode(String inputRandomCode) {
		this.inputRandomCode = inputRandomCode;
	}
}