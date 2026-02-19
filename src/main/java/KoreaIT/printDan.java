package KoreaIT;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/printDan")
public class printDan extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		
		
		String inputDan = request.getParameter("dan");
		
		String inputLimit = request.getParameter("limit");
		
		String inputColor = request.getParameter("color");
		
		System.out.println(inputDan);
		System.out.println(inputLimit);
		System.out.println(inputColor);
		
		if(inputDan == null) {
			inputDan = "3";
		}
		
		if(inputLimit == null) {
			inputLimit = "9";
		}
		
		int dan = Integer.parseInt(inputDan);
		int limit = Integer.parseInt(inputLimit);
				
		response.getWriter().append(String.format("<div style='color:%s;'>== %d단 ==</div>", inputColor, dan));
				
		for (int i = 1; i <= limit; i++) {
			response.getWriter().append(String.format("<div style='color:%s;'>%d * %d = %d</div>", inputColor, dan, i, dan * i));
		}
	}
}
