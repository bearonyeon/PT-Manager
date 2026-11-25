package controller.user;

import controller.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.service.UserManager;

public class LoginController implements Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equals("GET")) { // GET request
			return "/user/login.jsp";
		}
		
		// POST request
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		try {
			UserManager manager = UserManager.getInstance();
			
			// UserManager 코드 구현 후 여기에 코드 추가해야 됨
			// User user = manager.login(userId, password);
			
			/*
			 * UserManager.login(userId, password) 메서드가 로그인에 성공하면 
			 * 해당 사용자의 정보가 담긴 AccountDTO를 반환하도록 설계
			 * 세션에 사용자 아이디와 역할도 함께 저장하면 이후 다른 페이지를 이동할 때(권한 체크, 상단 메뉴 구성)도 좋음
			 */
			
			// 세션에 사용자 아이디 저장
			HttpSession session = request.getSession();
			session.setAttribute(UserSessionUtils.USER_SESSION_KEY, userId);
			//session.setAttribute("userRole", user.getRole());
			
			/*
			String role = user.getRole();
			if (role.equals("TRAINER")) {
				return "redirect:"
			} else if (role.equals("PTMEMBER")) {
				return "redirect:"
			} else {
				return "redirect:"
			}
			*/
			
			// 사용자가 어떤 역할인지 파악한 후 알맞은 view로 이동하는 코드 추가해야 됨
			
		} catch (Exception e) {
			request.setAttribute("loginFailed", true);
			request.setAttribute("exception", e);
			return "/user/login.jsp";
		}
	}
}
