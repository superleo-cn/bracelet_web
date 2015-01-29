package controllers;

import java.util.ArrayList;
import java.util.List;

import models.User;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.data.Form;
import play.libs.Json;
import play.mvc.Result;

import com.avaje.ebean.annotation.Transactional;
import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Constants;
import constants.Messages;
import forms.LoginForm;

public class Auths extends Basic {

	final static Logger logger = LoggerFactory.getLogger(Auths.class);

	public static Result index() {
		String name = session().get(Constants.CURRENT_USERNAME);
		if (StringUtils.isNotEmpty(name)) {
			return ok(views.html.lockscreen.render(name));
		} else {
			return ok(views.html.login.render());
		}

	}
	
	public static Result other() {
		return ok(views.html.login.render());
	}

	@Transactional
	public static Result loginJson() {
		ObjectNode result = Json.newObject();
		LoginForm form = new LoginForm();
		try {
			form = Form.form(LoginForm.class).bindFromRequest().get();
			List<User> datas = new ArrayList<User>();
			User dbUser = User.login(form);
			if (dbUser != null) {
				// user.id = dbUser.id;
				// user.lastLoginDate = new Date();
				// User.store(user);
				// dbUser.shop = dbUser.getMyShop();
				datas.add(dbUser);
				session(Constants.CURRENT_USERID, String.valueOf(dbUser.id));
				session(Constants.CURRENT_USERNAME, dbUser.username);
				session(Constants.CURRENT_USER_REALNAME, dbUser.realname);
				result.put(Constants.CODE, Constants.SUCCESS);
				result.put(Constants.MESSAGE, Messages.LOGIN_SUCCESS);
				result.put(Constants.DATAS, Json.toJson(datas));
			} else {
				result.put(Constants.CODE, Constants.FAILURE);
				result.put(Constants.MESSAGE, Messages.LOGIN_FAILURE);
			}
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.LOGIN_ERROR);
			logger.error(Messages.LOGIN_ERROR_MESSAGE, new Object[] { form.username, e });

		}
		return ok(result);
	}

	@Transactional
	public static Result logout() {
		return ok();
	}

}