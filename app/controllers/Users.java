package controllers;

import java.util.Date;

import models.User;

import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Pagination;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Constants;
import constants.Messages;
import forms.UserForm;

public class Users extends Controller {

	public static Result findAll() {
		ObjectNode result = Json.newObject();
		Pagination pagination = new Pagination();
		try {
			pagination = Form.form(Pagination.class).bindFromRequest().get();
			pagination = User.findAll(pagination);
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.DATAS, Json.toJson(pagination));
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.QUERY_ERROR);

		}
		return ok(result);
	}

	public static Result findById(Long id) {
		ObjectNode result = Json.newObject();
		try {
			User user = User.findById(id);
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.DATAS, Json.toJson(user));
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.QUERY_ERROR);

		}
		return ok(result);
	}

	@Transactional
	public static Result store() {
		ObjectNode result = Json.newObject();
		UserForm form = new UserForm();
		try {
			form = Form.form(UserForm.class).bindFromRequest().get();
			if (form.getId() != null && form.getId() > 0) {
				User dbUser = User.findById(form.getId());
				dbUser.setRealname(form.getRealname());
				dbUser.setUserType(form.getUserType());
				dbUser.setStatus(form.getStatus());
				dbUser.setModifiedDate(new Date());
				dbUser.setModifiedBy(session(Constants.CURRENT_USERID));
				User.store(dbUser);
			} else {
				User newUser = new User();
				BeanUtils.copyProperties(form, newUser);
				newUser.setCreateDate(new Date());
				newUser.setCreateBy(session(Constants.CURRENT_USERID));
				User.store(newUser);
			}
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.MESSAGE, "Store User Successfully.");
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.QUERY_ERROR);

		}
		return ok(result);
	}

}
