package com.hello.core.discount;

import org.springframework.stereotype.Component;

import com.hello.core.member.Grade;
import com.hello.core.member.Member;

@Component
// @Qualifier("fixDiscountPolicy") // 추가 구분자 
public class FixDiscountPolicy implements DiscountPolicy {

	private int discountFixAmount = 1000; // 1000원 할인

	@Override
	public int discount(Member member, int price) {
		if (member.getGrade() == Grade.VIP) {
			return discountFixAmount;
		} else {
			return 0;
		}
	}
}
