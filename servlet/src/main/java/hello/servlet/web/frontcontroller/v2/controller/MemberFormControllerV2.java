package hello.servlet.web.frontcontroller.v2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

public class MemberFormControllerV2 implements ControllerV2 {

	@Override
	public MyView process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return new MyView("/WEB-INF/views/new-form.jsp");
	}

}
/*
 * 이제 각 컨트롤러는 복잡한 dispatcher.forward() 를 직접 생성해서 호출하지 않아도 된다. 
 * 단순히 MyView 객체를 생성하고 거기에 뷰 이름만 넣고 반환하면 된다.
*/
