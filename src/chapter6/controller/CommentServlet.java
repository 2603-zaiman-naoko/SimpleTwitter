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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Comment;
import chapter6.beans.User;
import chapter6.logging.InitApplication;
import chapter6.service.CommentService;

@WebServlet(urlPatterns = { "/comment" })

public class CommentServlet  extends HttpServlet {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public CommentServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		HttpSession session = request.getSession();
		List<String> errorMessages = new ArrayList<String>();

		// ユーザーIDをセッション領域から取得する(TopServletにて設定済み)
		User user = (User) session.getAttribute("loginUser");

		// String型でJSPから取得
		String id = request.getParameter("message_id");
		String commentText = request.getParameter("text");

		// beansのインスタンスを生成する
		Comment comment = new Comment();

		// idの設定・数値チェック
		if(StringUtils.isBlank(id) || !id.matches("^[0-9]*$")) {
			log.warning("不正なパラメータが入力されました");
			errorMessages.add("不正なパラメータが入力されました");
		} else {

			// バリデーションチェック
			if (!isValid(commentText, errorMessages)) {
				session.setAttribute("errorMessages", errorMessages);
				response.sendRedirect("./");
				return;
			}

			// message_idをint型に変換
			int messageId = Integer.parseInt(id);

			// beansに設定する
			comment.setText(commentText);
			comment.setUserId(user.getId());
			comment.setMessageId(messageId);

			// Serviceのinsertを呼び出す
			new CommentService().insert(comment);
		}

		if (errorMessages.size() != 0) {
			session.setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}

		// top画面に戻す
		response.sendRedirect("./");
	}

	private boolean isValid(String text, List<String> errorMessages) {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		if (StringUtils.isBlank(text)) {
			errorMessages.add("メッセージを入力してください");
		} else if (140 < text.length()) {
			errorMessages.add("140文字以下で入力してください");
		}

		if (errorMessages.size() != 0) {
			return false;
		}

		return true;
	}
}