package com.demo.blog;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * BlogController
 */
@Before(BlogInterceptor.class)
public class BlogController extends Controller {
	public void index() {
		setAttr("blogPage", Blog.dao.paginate(getParaToInt(0, 1), 10, "select *", "from blog order by id asc"));
		render("blog.html");
	}
	
	public void add() {
		render("add.html");
	}
	
	@Before(BlogValidator.class)
	public void save() {
		Blog blog=getModel(Blog.class);
		blog.set("id", "id_seq.nextval");
		blog.save();
		forwardAction("/blog");
	}
	
	public void edit() {
		setAttr("blog", Blog.dao.findById(getParaToInt()));
		render("add.html");
	}
	
	@Before(BlogValidator.class)
	public void update() {
		Blog blog=getModel(Blog.class);
		blog.update();
		forwardAction("/blog");
	}
	
	public void delete() {
		Blog.dao.deleteById(getParaToInt());
		forwardAction("/blog");
	}
}


