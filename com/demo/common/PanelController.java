package com.demo.common;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

/**
 * CommonController
 */
public class PanelController extends Controller {

	@ActionKey("/panel")
	public void panel() {
		render("panel.html");
	}

}
