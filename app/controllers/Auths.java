package controllers;

import java.util.stream.Collectors;

import models.User;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.libs.Json;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Constants;
import constants.Messages;
import forms.BraceletVO;
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

	public static Result loginJson() {
		ObjectNode result = Json.newObject();
		LoginForm form = new LoginForm();
		try {
			form = Form.form(LoginForm.class).bindFromRequest().get();
			User dbUser = User.login(form);
			if (dbUser != null) {
				// user.id = dbUser.id;
				// user.lastLoginDate = new Date();
				// User.store(user);
				// dbUser.shop = dbUser.getMyShop();
				String json = copyValueForJSON(dbUser);
				session(Constants.CURRENT_USERID, String.valueOf(dbUser.getId()));
				session(Constants.CURRENT_USERNAME, dbUser.getUsername());
				session(Constants.CURRENT_USER_REALNAME, dbUser.getRealname());
				result.put(Constants.CODE, Constants.SUCCESS);
				result.put(Constants.MESSAGE, Messages.LOGIN_SUCCESS);
				result.put(Constants.DATAS, Json.toJson(dbUser));
				response().setCookie(Constants.CURRENT_USERID, String.valueOf(dbUser.getId()), 864000);
				response().setCookie(Constants.CURRENT_BRACELETS, json, 864000);
				response().setCookie(Constants.CURRENT_USERNAME, String.valueOf(dbUser.getUsername()), 864000);
				response().setCookie(Constants.CURRENT_USER_REALNAME, String.valueOf(dbUser.getRealname()), 864000);
				response().setCookie(Constants.CURRENT_ROLE, String.valueOf(dbUser.getUserType()), 864000);
				response().setCookie(Constants.CURRENT_CREATE_DATE, DateFormatUtils.format(dbUser.getCreateDate(), "dd/MM/yyyy"), 864000);
			} else {
				result.put(Constants.CODE, Constants.FAILURE);
				result.put(Constants.MESSAGE, Messages.LOGIN_FAILURE);
			}
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.LOGIN_ERROR);
			logger.error(Messages.LOGIN_ERROR_MESSAGE, new Object[] { form.getUsername(), e });
		}
		return ok(result);
	}

	public static String myBraceletIds(User dbUser) {
		if (CollectionUtils.isNotEmpty(dbUser.bracelets)) {
			return dbUser.bracelets.stream().map(e -> e.getId().toString()).collect(Collectors.joining(","));
		}
		return StringUtils.EMPTY;
	}

	public static String copyValueForJSON(User dbUser) {
		if (CollectionUtils.isNotEmpty(dbUser.bracelets)) {
			dbUser.bracelets.stream().forEach(b -> {
				BraceletVO vo = new BraceletVO();
				BeanUtils.copyProperties(b, vo);
				dbUser.getBraceletList().add(vo);
			});

		}
		JsonNode node = Json.toJson(dbUser.getBraceletList());
		return node.toString();
	}

	public static Result logoutJson() {
		ObjectNode result = Json.newObject();
		String username = null;
		try {
			username = session(Constants.CURRENT_USERNAME);
			session().clear();
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.MESSAGE, Messages.LOGOUT_SUCCESS);
			response().discardCookie(Constants.CURRENT_USERID);
			response().discardCookie(Constants.CURRENT_USERNAME);
			response().discardCookie(Constants.CURRENT_USER_REALNAME);
			response().discardCookie(Constants.CURRENT_ROLE);
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.LOGOUT_ERROR);
			logger.error(Messages.LOGOUT_ERROR_MESSAGE, new Object[] { username, e });

		}
		return ok(result);
	}

}
