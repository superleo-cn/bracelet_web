package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import constants.Constants;

public class Application extends Controller {

	public static Result index() {
		String id = session().get(Constants.CURRENT_USERID);
		String username = session().get(Constants.CURRENT_USERNAME);
		String realname = session().get(Constants.CURRENT_USER_REALNAME);
		return ok(views.html.index.render(id, username, realname));
	}

}
