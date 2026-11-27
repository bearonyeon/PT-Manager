package controller.user;

import org.slf4j.*;

import controller.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.AccountDTO;
import model.service.UserManager;
import model.service.exception.ExistingUserException;

public class RegisterUserController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(RegisterUserController.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equals("GET")) {
			return "/user/registerForm.jsp";
		}
		
		// 파라미터 추출
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String role = request.getParameter("role");
		String heightStr = request.getParameter("height");
		double height = Double.parseDouble(heightStr);
		
		AccountDTO account = new AccountDTO(userId, password, name, email, phone, role, height);
		
		log.debug("Create User: {}", account);
		
		try {
			UserManager manager = UserManager.getInstance();
			manager.insertAccount(account);
			return "redirect:/user/main";
		} catch (ExistingUserException e) {
			request.setAttribute("registerFailed", true);
			request.setAttribute("exception", e);
			request.setAttribute("account", account);
			return "/user/registerForm.jsp";
		}
	}
	
}
