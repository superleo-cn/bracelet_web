package controllers;

import play.mvc.Result;
import constants.Constants;

public class Application extends Basic {

	public static Result index() {
		String id = session().get(Constants.CURRENT_USERID);
		String username = session().get(Constants.CURRENT_USERNAME);
		String realname = session().get(Constants.CURRENT_USER_REALNAME);
		return ok(views.html.index.render(id, username, realname));
	}

	public static Result test() {
		return ok(views.html.test.render());
	}

	public static Result test2() {
		return ok(views.html.test2.render());
	}

}
