package com.poscodx.web.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.web.mvc.Action;

public class DeleteAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		String password = request.getParameter("password");
		
		if(password.equals(new GuestBookDao().findPasswordByNo(Long.parseLong(no)))) {
			new GuestBookDao().deleteByNo(no);	
		}
		
		response.sendRedirect("/mysite02/guestbook");
	}

}