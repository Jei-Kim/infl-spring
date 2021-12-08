package com.hello.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hello.core.member.Grade;
import com.hello.core.member.Member;
import com.hello.core.member.MemberService;

public class MemberApp {

	public static void main(String[] args) {
		//AppConfig appConfig = new AppConfig();
		
		//MemberService memberService = new MemberServiceImpl(); //appconfig 이전 - 메인메서드에서 직접 생성 
		
		/*
		appConfig에서 memberService 달라고 요청하면 MemberService 인터페이스를 줌 
		memberService에는 memberServiceImpl이 들어 있음
		-> AppConfig의 memberService메서드의 리턴값이 memberServiceImpl라서?
		*/
		
		//MemberService memberService = appConfig.memberService(); 
		
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
		
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);
        
        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());

	}
}
