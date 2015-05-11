package controllers;

import java.util.Date;
import java.util.List;

import models.Bracelet;

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
import forms.BraceletForm;

public class Bracelets extends Controller {

	public static Result findAll() {
		ObjectNode result = Json.newObject();
		Pagination pagination = new Pagination();
		try {
			pagination = Form.form(Pagination.class).bindFromRequest().get();
			pagination = Bracelet.findAll(pagination);
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
			Bracelet bracelet = Bracelet.findById(id);
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.DATAS, Json.toJson(bracelet));
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.QUERY_ERROR);

		}
		return ok(result);
	}
	
	public static Result findByUserId(Long id) {
		ObjectNode result = Json.newObject();
		try {
			List<Bracelet> bracelets = Bracelet.findByUserId(id);
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.DATAS, Json.toJson(bracelets));
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.QUERY_ERROR);

		}
		return ok(result);
	}

	@Transactional
	public static Result store() {
		ObjectNode result = Json.newObject();
		BraceletForm form = new BraceletForm();
		try {
			form = Form.form(BraceletForm.class).bindFromRequest().get();
			if (form.getId() != null && form.getId() > 0) {
				Bracelet dbBracelet = Bracelet.findById(form.getId());
				dbBracelet.setName(form.getName());
				dbBracelet.setType(form.getType());
				dbBracelet.setStatus(form.getStatus());
				dbBracelet.setModifiedDate(new Date());
				dbBracelet.setModifiedBy(session(Constants.CURRENT_USERID));
				Bracelet.store(dbBracelet);
			} else {
				Bracelet newBracelet = new Bracelet();
				BeanUtils.copyProperties(form, newBracelet);
				newBracelet.setCreateDate(new Date());
				newBracelet.setCreateBy(session(Constants.CURRENT_USERID));
				Bracelet.store(newBracelet);
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
