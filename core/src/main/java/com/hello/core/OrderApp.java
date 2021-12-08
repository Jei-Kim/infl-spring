package com.hello.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hello.core.member.Grade;
import com.hello.core.member.Member;
import com.hello.core.member.MemberService;
import com.hello.core.order.Order;
import com.hello.core.order.OrderService;

public class OrderApp {

	public static void main(String[] args) {
		 
		//AppConfig appConfig = new AppConfig();
		
		// memberService = new MemberServiceImpl();
		//OrderService orderService = new OrderServiceImpl();
        
		//MemberService memberService = appConfig.memberService();
        //OrderService orderService = appConfig.orderService();
        
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
		OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
		
		 long memberId = 1L;
		 
		 Member member = new Member(memberId , "memberA", Grade.VIP);
         memberService.join(member);
         
         Order order = orderService.createOrder(memberId, "itemA", 10000);
         
         System.out.println("order = " + order);
     }
}
