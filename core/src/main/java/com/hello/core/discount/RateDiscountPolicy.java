package com.hello.core.discount;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.hello.core.member.Grade;
import com.hello.core.member.Member;

@Component
// @Qualifier("mainDiscountPolicy") // 추가 구분자 
@Primary
public class RateDiscountPolicy implements DiscountPolicy{

	private int discountPercent = 10;
	
	@Override
	public int discount(Member member, int price) {
		if (member.getGrade() == Grade.VIP) {
			return price * discountPercent / 100;
		} else {
			return 0;	
		}
	}	
}
