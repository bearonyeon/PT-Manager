package controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
	private String forwardUrl;
	
	public ForwardController(String forwardUrl) {
		if (forwardUrl == null) {
			throw new NullPointerException("forwardUrl is null.\n이동할 URL을 입력하세요.");
		}
		this.forwardUrl = forwardUrl;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return forwardUrl;
	}

}