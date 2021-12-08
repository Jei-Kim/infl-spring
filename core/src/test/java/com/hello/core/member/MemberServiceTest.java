package com.hello.core.member;

import org.junit.jupiter.api.BeforeEach;

import com.hello.core.AppConfig;

class MemberServiceTest {

	//MemberService memberService = new MemberServiceImpl();
	MemberService memberService;
	
	/*
	@Test
	void join(){
	//given
    Member member = new Member(1L, "memberA", Grade.VIP);
    
    //when
    memberService.join(member);
    Member findMember = memberService.findMember(1L);
    
    //then
    Assertions.assertThat(member).isEqualTo(findMember);
	}
	*/
	
	@BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }
}
