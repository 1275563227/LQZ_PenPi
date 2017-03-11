package com.penpi.server.unuser;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

//完成Hibernate工具类
//封装配置文件读取操作
//封装Sessionfactroy创建操作
//封装session获得操作
public class HibernateUtils {

	private static SessionFactory sf;
	
	static{
		//1加载配置
		Configuration  conf = new Configuration().configure();
		//2 根据Configuration 配置信息创建 SessionFactory
		sf = conf.buildSessionFactory();
		//
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("虚拟机关闭!释放资源");
				sf.close();
			}
		}));
		
	}
	
	
	public static org.hibernate.Session  openSession(){
				
				//3 获得session
				Session session = sf.openSession();
				
				return session;
	}
	
	public static org.hibernate.Session  getCurrentSession(){
		//3 获得session
		Session session = sf.getCurrentSession();
		
		return session;
}
	
	public static void main(String[] args) {
		System.out.println(openSession());
	}
}