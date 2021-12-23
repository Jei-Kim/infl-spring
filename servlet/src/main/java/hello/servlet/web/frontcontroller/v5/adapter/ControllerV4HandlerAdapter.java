package hello.servlet.web.frontcontroller.v5.adapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {

	// ControllerV4 을 처리할 수 있는 어댑터를 뜻한다.
	@Override
	public boolean supports(Object handler) {
		return (handler instanceof ControllerV4);
	}

	@Override
	public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException, IOException {
		ControllerV4 controller = (ControllerV4) handler;
		
		Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();
        
        String viewName = controller.process(paramMap, model);
        
        /*
         * 어댑터가 호출하는 ControllerV4 는 뷰의 이름을 반환한다. 
         * 그런데 어댑터는 뷰의 이름이 아니라 ModelView 를 만들어서 반환해야 한다.
         * ControllerV4 는 뷰의 이름을 반환했지만, 
         * 어댑터는 이것을 ModelView로 만들어서 형식을 맞추어 반환한다.
         */
        ModelView mv = new ModelView(viewName);
        mv.setModel(model);
        return mv; 
	}

	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
				.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}

}
