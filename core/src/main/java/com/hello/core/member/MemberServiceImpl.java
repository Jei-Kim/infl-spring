package com.hello.core.member;

public class MemberServiceImpl implements MemberService{

	
	//private final MemberRepository memberRepository = new MemoryMemberRepository(); //AppCongfig 이전 코드 
	private final MemberRepository memberRepository;
	
	// 6 -> 7로 변경하고 이렇게 생성자를 만들어 줌  
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	
	@Override
	public void join(Member member) {
		memberRepository.save(member);	
	}

	@Override
	public Member findMember(Long memberId) {
		return 	memberRepository.findById(memberId);	
	}

}
