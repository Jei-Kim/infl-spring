package com.hello.core.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
import com.hello.core.member.Grade;
import com.hello.core.member.Member;

public class RateDiscountPolicyTest {

	RateDiscountPolicy discountPolicy = new RateDiscountPolicy();
	
	@Test
	@DisplayName("VIP는 10% 할인이 적용되어야 한다")// JUnit5부터 지원하는 기능
	void vip_o() { // vip 할인이 적용될 경우 (성공 테스트)
		//given
		Member member = new Member(1L, "memberVIP", Grade.VIP);
		//when
		int discount = discountPolicy.discount(member, 10000);
		//then
		Assertions.assertThat(discount).isEqualTo(1000);
	}

	@Test
	@DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다")
	void vip_x() { // vip 할인이 적용되지 않을 경우 (실패 테스트)
		//given
		Member member = new Member(1L, "memberBASIC", Grade.BASIC);
		//when
		int discount = discountPolicy.discount(member, 10000);
		//then
		Assertions.assertThat(discount).isEqualTo(0);
	}
	
} 
