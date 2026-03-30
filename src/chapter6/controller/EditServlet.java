package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })

public class EditServlet extends HttpServlet {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public EditServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		List<String> errorMessages = new ArrayList<String>();
		Message message = null;

		String id = request.getParameter("message_id");
		try {
			int messageId = Integer.parseInt(id);

			message = new MessageService().select(messageId);

			if(message == null) {

				// messagesTBLのidが存在しない場合
				log.warning("不正なパラメータが入力されました");
				errorMessages.add("不正なパラメータが入力されました");
			}
		} catch (NumberFormatException  e) {
			log.warning("不正なパラメータが入力されました");
			errorMessages.add("不正なパラメータが入力されました");
		}

		if (errorMessages.size() != 0) {
			request.setAttribute("errorMessages", errorMessages);
			request.getRequestDispatcher("./").forward(request, response);
			return;
		}

		request.setAttribute("message", message);
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		List<String> errorMessages = new ArrayList<String>();

		// String型でJSPから取得
		String id = request.getParameter("message_id");
		String messageText = request.getParameter("text");

		Message message = new Message();

		// idをint型に変換し設定
		message.setId(Integer.parseInt(id));

		// textを設定
		message.setText(messageText);

		if (isValid(messageText, errorMessages)) {

			// update呼び出し
			new MessageService().update(message);
		}

		if (errorMessages.size() != 0) {
			request.setAttribute("message", message);
			request.setAttribute("errorMessages", errorMessages);
			request.getRequestDispatcher("/edit.jsp").forward(request, response);
			return;
		}

		request.setAttribute("message", message);
		response.sendRedirect("./");
	}

	private boolean isValid(String text, List<String> errorMessages) {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		if (StringUtils.isBlank(text)) {
			errorMessages.add("入力してください");
		} else if (140 < text.length()) {
			errorMessages.add("140文字以下で入力してください");
		}

		if (errorMessages.size() != 0) {
			return false;
		}
		return true;
	}
}