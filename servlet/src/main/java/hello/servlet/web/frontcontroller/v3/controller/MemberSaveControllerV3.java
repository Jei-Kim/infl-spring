package hello.servlet.web.frontcontroller.v3.controller;

import java.util.Map;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

public class MemberSaveControllerV3 implements ControllerV3 {

	private MemberRepository memberRepository = MemberRepository.getInstance();
	
	@Override
	public ModelView process(Map<String, String> paramMap) {
		
		String username = paramMap.get("username"); // 파라미터 정보는 map에 담겨 있음. 
		int age = Integer.parseInt(paramMap.get("age"));
		
		Member member = new Member(username, age);
		memberRepository.save(member);
		
		ModelView mv = new ModelView("save-result");
		// 모델은 단순한 map이므로, 모델에 뷰에서 필요한 member객체를 담고 반환함 
		mv.getModel().put("member", member); 
		return mv;
	}

}
