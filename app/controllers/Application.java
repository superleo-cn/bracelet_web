package controllers;

import play.mvc.Result;
import constants.Constants;

public class Application extends Basic {

	public static Result index() {
		String id = session().get(Constants.CURRENT_USERID);
		String username = session().get(Constants.CURRENT_USERNAME);
		String realname = session().get(Constants.CURRENT_USER_REALNAME);
		String braceletId = session().get(Constants.CURRENT_BRACELET_ID);
		if (Basic.isUser()) {
			
		}
		return ok(views.html.index.render(id, braceletId, username, realname));
	}

}
