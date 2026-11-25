package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {
	private RequestMapping rm;
	
	public void init() throws ServletException {
		rm = new RequestMapping();
		rm.initMapping();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		
		Controller controller = rm.findController(servletPath);
		try {
			String uri = controller.execute(request, response);
			
			if (uri.startsWith("redirect:")) {
				String targetUri = contextPath + uri.substring("redirect:".length());
				response.sendRedirect(targetUri);
			} else {
				String targetUri = "/WEB-INF" + uri;
				RequestDispatcher rd = request.getRequestDispatcher(targetUri);
				rd.forward(request, response);
			}
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
	}
	
	
}
