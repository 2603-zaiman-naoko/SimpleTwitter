package chapter6.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.User;

@WebFilter(urlPatterns = {"/edit", "/setting"})
public class LoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		// エラーメッセージ出力用
		List<String> errorMessages = new ArrayList<String>();

		// session領域からログインユーザ情報を取得する
		HttpSession session = ((HttpServletRequest) request).getSession();
		User user = (User) session.getAttribute("loginUser");

		// ログインしている場合
		if (user != null) {

			// サーブレットを実行
			chain.doFilter(request, response);
		} else {

			// エラーメッセージの出力
			errorMessages.add("ログインしてください");
			session.setAttribute("errorMessages", errorMessages);

			// ログイン画面へ遷移させる
			((HttpServletResponse) response).sendRedirect("./login");
		}
	}

	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void destroy() {
	}
}
