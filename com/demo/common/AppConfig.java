package com.demo.common;

import com.demo.login.Login;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;

/**
 * API引导式配置
 */
public class AppConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setBaseViewPath("/WEB-INF/templates/");
        me.setError404View("/WEB-INF/templates/404.html");
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/common", CommonController.class);
        me.add("/panel", PanelController.class);
		//me.add("/blog", BlogController.class);
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("classes/database.properties");
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("userName"), getProperty("password").trim());
		//配置Oracle驱动
		c3p0Plugin.setDriverClass(getProperty("jdbcDriver"));
		me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		//配置Oracle方言
		arp.setDialect(new OracleDialect());
		//配置属性名(字段名)大小不敏感容器工厂
		arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		//arp.addMapping("blog", Blog.class);	// 映射blog 表到 Blog模型
		arp.addMapping("login", Login.class);	// 映射login 表到 Login模型
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new GlobalInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {

	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
