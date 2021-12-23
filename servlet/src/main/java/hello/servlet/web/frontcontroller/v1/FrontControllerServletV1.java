package hello.servlet.web.frontcontroller.v1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

// /front-controller/v1을 포함한 모든 하위 요청은 이 서블릿에서 받아들인다. 
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

	// controllerMap: key = 매핑 url, value = 호출될컨트롤
	private Map<String, ControllerV1> controllerMap = new HashMap<>();
	
	public FrontControllerServletV1() {
		controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
		controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
		controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
	}
	
	// 먼저 requestURI 를 조회해서 실제 호출할 컨트롤러를 controllerMap 에서 찾는다. 
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		System.out.println("FrontControllerServletV1.service");
		String requestURI = request.getRequestURI();
		
		ControllerV1 controller = controllerMap.get(requestURI);
		
		if (controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 상태코드 반환 
			return;
		}
		
		controller.process(request, response); //헤당 컨트롤러 실헹 
	}
}
