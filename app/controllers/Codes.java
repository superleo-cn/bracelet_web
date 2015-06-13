package controllers;

import models.Code;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Pagination;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Constants;
import constants.Messages;

public class Codes extends Controller {

	public static Result findAll() {
		ObjectNode result = Json.newObject();
		try {
			Pagination pagination = Form.form(Pagination.class).bindFromRequest().get();
			pagination = Code.findAll(pagination);
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.DATAS, Json.toJson(pagination));
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.QUERY_ERROR);

		}
		return ok(result);
	}

}
