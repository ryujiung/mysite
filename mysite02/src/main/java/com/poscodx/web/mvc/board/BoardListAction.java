package com.poscodx.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class BoardListAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String begin = request.getParameter("begin");
		int page = Integer.parseInt(request.getParameter("i"));
		
		
		
		List<BoardVo> list = new BoardDao().findAll(page);
		request.setAttribute("begin", begin);
		request.setAttribute("list", list);
		WebUtil.forward("board/list", request, response);
	}

}