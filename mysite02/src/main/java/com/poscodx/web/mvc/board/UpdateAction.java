package com.poscodx.web.mvc.board;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.web.mvc.Action;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		String no = request.getParameter("no");
//		String title = request.getParameter("title");
//		String contents = request.getParameter("contents");
//
//		 new BoardDao().updateTitleAndContentsByNo(title, contents, no);
//
//		response.sendRedirect(request.getContextPath() + "/board?a=detailform&no=" + no + "&title=" + title
//				+ "&contents=" + contents);
		String no = request.getParameter("no");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		// URL에 포함될 값들을 UTF-8로 인코딩
		String encodedTitle = URLEncoder.encode(title, "UTF-8");
		String encodedContents = URLEncoder.encode(contents, "UTF-8");

		new BoardDao().updateTitleAndContentsByNo(title, contents, no);

		// 인코딩된 값을 URL에 추가하여 리다이렉트
		String redirectURL = request.getContextPath() + "/board?a=detailform&no=" + no + "&title=" + encodedTitle
		        + "&contents=" + encodedContents;
		response.sendRedirect(redirectURL);

	}

}
