package com.poscodx.web.mvc.board;

import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.ActionFactory;

public class BoardActionFactory implements ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;

		if ("insertboard".equals(actionName)) {
			action = new InsertBoardAction();
		} else if ("insertboardform".equals(actionName)) {
			action = new InsertBoardForm();
		} else if ("insertreply".equals(actionName)) {
			action = new InsertReplyAction();
		} else if ("detailform".equals(actionName)) {
			action = new DetailForm();
		} else if ("deleteform".equals(actionName)) {
			action = new DeleteFormAction();
		} else if ("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if ("update".equals(actionName)) {
			action = new UpdateAction();
		} else if ("updateform".equals(actionName)) {
			action = new UpdateFormAction();
		} else if ("insertreplyform".equals(actionName)) {
			action = new InsertReplyFormAction();
		} 
		else {
			action = new BoardListAction();
		}

		return action;
	}
}
