package controllers;

import models.Bracelet;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Constants;

public class Bracelets extends Controller {

	public static Result findAll() {
		ObjectNode result = Json.newObject();
		result.put(Constants.CODE, Constants.SUCCESS);
		result.put(Constants.DATAS, Json.toJson(Bracelet.findList()));
		return ok(result);
	}
	
	public static Result findByDate(String date) {
		ObjectNode result = Json.newObject();
		result.put(Constants.CODE, Constants.SUCCESS);
		result.put(Constants.DATAS, Json.toJson(Bracelet.findList(date)));
		return ok(result);
	}
}
