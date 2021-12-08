package com.hello.core.order;

import com.hello.core.discount.DiscountPolicy;
import com.hello.core.member.Member;
import com.hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {
	
/* AppConfig 이전 코드 
	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
//	private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); //요구사항 변경 전 코드 
	private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
*/
	
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;
	
	//생성자 
	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
		this.memberRepository = memberRepository;
		this.discountPolicy = discountPolicy;
	}
	
	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {

		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice);
		return new Order(memberId, itemName, itemPrice, discountPrice);
	}
}
