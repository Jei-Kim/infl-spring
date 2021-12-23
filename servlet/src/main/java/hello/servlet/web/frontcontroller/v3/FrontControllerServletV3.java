package hello.servlet.web.frontcontroller.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

	private Map<String, ControllerV3> controllerMap = new HashMap<>();
	
	public FrontControllerServletV3() {
		controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
		controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
		controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		
		
		ControllerV3 controller = controllerMap.get(requestURI);
		if (controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		Map<String, String> paramMap = createParamMap(request);
		ModelView mv = controller.process(paramMap);
		
		String viewName = mv.getViewName();
		MyView view = viewResolver(viewName);
		
		// 뷰 객체를 통해서 HTML 화면을 렌더링 한다. 뷰 객체의 render() 는 모델 정보도 함께 받는다.
		view.render(mv.getModel(), request, response);
	}
	
	// HttpServletRequest에서 파라미터 정보를 꺼내서 Map으로 변환한다. 
	// 그리고 해당 Map( paramMap )을 컨트롤러에 전달하면서 호출한다.
	private Map<String, String> createParamMap(HttpServletRequest request) {
		
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
		.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}
	
	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}
}
