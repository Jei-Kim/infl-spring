package hello.servlet.web.frontcontroller.v2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.MyView;

// 컨트롤러가 뷰를 반환 
public interface ControllerV2 {

	// 반환 타입 = MyView
	MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
		
	
}
