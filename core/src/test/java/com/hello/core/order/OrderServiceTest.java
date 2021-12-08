package com.hello.core.order;

import org.junit.jupiter.api.BeforeEach;

import com.hello.core.AppConfig;
import com.hello.core.member.MemberService;

class OrderServiceTest {
	
//    MemberService memberService = new MemberServiceImpl();
//    OrderService orderService = new OrderServiceImpl();
  MemberService memberService;
  OrderService orderService;
  
  /*
    @Test
   void createOrder() {
       long memberId = 1L;
       Member member = new Member(memberId, "memberA", Grade.VIP);
       memberService.join(member);
       
       Order order = orderService.createOrder(memberId, "itemA", 10000);
       Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
   }
   */
  
  @BeforeEach // 각 테스트를 실행하기 전에 호출 
  public void beforeEach() {
      AppConfig appConfig = new AppConfig();
      memberService = appConfig.memberService();
      orderService = appConfig.orderService();
}
}