package controllers;

import org.apache.commons.lang3.StringUtils;

import play.mvc.Controller;
import play.mvc.Http.Cookie;
import constants.Constants;

public class Basic extends Controller {

	public static boolean isAdmin() {
		Cookie cookie = request().cookie(Constants.CURRENT_ROLE);
		if (cookie != null) {
			if (StringUtils.equals(cookie.value(), Constants.USERTYPE_ADMIN)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isDoctor() {
		Cookie cookie = request().cookie(Constants.CURRENT_ROLE);
		if (cookie != null) {
			if (StringUtils.equals(cookie.value(), Constants.USERTYPE_DOCTOR)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isUser() {
		Cookie cookie = request().cookie(Constants.CURRENT_ROLE);
		if (cookie != null) {
			if (StringUtils.equals(cookie.value(), Constants.USERTYPE_USER)) {
				return true;
			}
		}
		return false;
	}

}