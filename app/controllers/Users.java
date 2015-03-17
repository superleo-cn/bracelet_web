package controllers;

import java.util.Date;

import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Pagination;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Constants;
import constants.Messages;

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
	
	public static Result store() {
		ObjectNode result = Json.newObject();
		User form = new User();
		try {
			form = Form.form(User.class).bindFromRequest().get();
			if(form.id != null && form.id > 0){
				User dbUser = User.findById(form.id);
				dbUser.realname = form.realname;
				dbUser.userType = form.userType;
				dbUser.status = form.status;
				dbUser.modifiedDate = new Date();
				dbUser.modifiedBy = session(Constants.CURRENT_USERID);
				User.store(dbUser);
			}else{
				form.password = "123456";
				form.createDate = new Date();
				form.createBy = session(Constants.CURRENT_USERID);
				User.store(form);
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
