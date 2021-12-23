package hello.servlet.web.frontcontroller.v5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

	//매핑 정보의 값이 ControllerV3 , ControllerV4 같은 인터페이스에서 
	//아무 값이나 받을 수 있는 Object 로 변경되었다.
	private final Map<String, Object> handlerMappingMap = new HashMap<>();
	private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

	//생성자는 핸들러 매핑과 어댑터를 초기화(등록)한다.
	public FrontControllerServletV5() {
		initHandlerMappingMap(); //핸들러 매핑 초기화
		initHandlerAdapters(); //어댑터 초기화
	}

	private void initHandlerMappingMap() {
		handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
	
	
		//V4 추가
	      handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
	      handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
	      handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
	  }
	
	private void initHandlerAdapters() {
		handlerAdapters.add(new ControllerV3HandlerAdapter());
		handlerAdapters.add(new ControllerV4HandlerAdapter()); //V4 추가
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object handler = getHandler(request);
		if (handler == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		MyHandlerAdapter adapter = getHandlerAdapter(handler);
		ModelView mv = adapter.handle(request, response, handler); //어댑터 호출 
		MyView view = viewResolver(mv.getViewName());
		view.render(mv.getModel(), request, response);
	}
	
	//핸들러 매핑 정보인 handlerMappingMap 에서 URL에 매핑된 핸들러(컨트롤러) 객체를 찾아서 반환한다.
	 private Object getHandler(HttpServletRequest request) {
	        String requestURI = request.getRequestURI();
	        return handlerMappingMap.get(requestURI);
	}
	 
	 private MyHandlerAdapter getHandlerAdapter(Object handler) {
	     //handler 를 처리할 수 있는 어댑터를 adapter.supports(handler) 를 통해서 찾는다.   
		 for (MyHandlerAdapter adapter : handlerAdapters) {
	     //handler가 ControllerV3 인터페이스를 구현했다면, ControllerV3HandlerAdapter 객체가 반환된다.
			 if (adapter.supports(handler)) {
	                return adapter;
	         } 
	 }
	   throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
	 }  
	 
	 private MyView viewResolver(String viewName) {
         return new MyView("/WEB-INF/views/" + viewName + ".jsp");
}

}
