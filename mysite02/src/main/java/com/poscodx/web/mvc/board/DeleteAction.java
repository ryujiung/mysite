package com.poscodx.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.web.mvc.Action;

public class DeleteAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		
		
		new BoardDao().deleteByNo(Long.parseLong(no));
		
		
		response.sendRedirect(request.getContextPath() + "/board?begin=1&i=1");
	}

}
