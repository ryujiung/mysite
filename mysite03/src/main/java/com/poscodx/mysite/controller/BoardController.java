package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/{begin}/{i}")
	public String list(@PathVariable("begin") int begin, @PathVariable("i") int i, Model model) {
		
		List<BoardVo> list = boardService.getContentsList(i);
		model.addAttribute("list",list);
		model.addAttribute("begin",begin);
		model.addAttribute("page",i);
		return "board/list";
		
	}

	   
}
