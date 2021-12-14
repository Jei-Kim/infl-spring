 package com.hello.core.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hello.core.discount.DiscountPolicy;
import com.hello.core.member.Member;
import com.hello.core.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // 롬복 -> final 필드를 모아 생성자를 자동 생성 
public class OrderServiceImpl implements OrderService {
	
/* AppConfig 이전 코드 
	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
//	private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); //요구사항 변경 전 코드 
	private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
*/
	// @Autowired - 의존 객체 주입 방법: 필드 주입 
	private final MemberRepository memberRepository;
	// @Autowired - 의존 객체 주입 방법: 필드 주입 
	private final DiscountPolicy discountPolicy;
	
	
	@Autowired //생성자에서 여러 의존관계도 한번에 주입받을 수 있다.
	// 스프링 빈에서는 생성자가 하나일 경우 @Autowired 생략 가능 
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
	
	//테스트
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
	
}
