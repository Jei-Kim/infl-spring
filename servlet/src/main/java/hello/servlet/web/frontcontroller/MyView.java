package hello.servlet.web.frontcontroller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// v3

public class MyView {

	private String viewPath;

	public MyView(String viewPath) {
		this.viewPath = viewPath;
	}
	//v2
	public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
		dispatcher.forward(request, response);
	}
	//v3
	public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		modelToRequestAttribute(model, request);
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
		dispatcher.forward(request, response);
	}
	//v3
	private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
		model.forEach((key, value) -> request.setAttribute(key, value));
	}
}