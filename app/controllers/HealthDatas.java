package controllers;

import java.util.List;

import models.HealthData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Constants;
import constants.Messages;

public class HealthDatas extends Controller {

	final static Logger logger = LoggerFactory.getLogger(HealthDatas.class);

	// realtime
	public static Result findRealtimeList(String braceletId, String date) {
		ObjectNode result = Json.newObject();
		try {
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.DATAS, Json.toJson(HealthData.findRealtimeList(braceletId, date)));
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.HEALTH_DATA_LIST_ERROR);
			logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[] { braceletId, e });
		}
		return ok(result);
	}

	// history
	public static Result findHistoryList(String braceletId, String type, String date) {
		ObjectNode result = Json.newObject();
		try {
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.DATAS, Json.toJson(HealthData.findHistoryList(braceletId, type, date)));
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.HEALTH_DATA_LIST_ERROR);
			logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[] { braceletId, e });
		}
		return ok(result);
	}

	// interface
	public static Result findByBraceletAndDate(String braceletId, String date) {
		ObjectNode result = Json.newObject();
		try {
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.DATAS, Json.toJson(HealthData.findList(braceletId, date)));
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.HEALTH_DATA_LIST_ERROR);
			logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[] { braceletId, e });
		}
		return ok(result);
	}

	public static Result findUrgentByBracelet(String braceletId) {
		ObjectNode result = Json.newObject();
		try {
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.DATAS, Json.toJson(HealthData.findUrgentList(braceletId)));
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.HEALTH_DATA_LIST_ERROR);
			logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[] { braceletId, e });
		}
		return ok(result);
	}

	public static Result findLatest(String braceletId) {
		ObjectNode result = Json.newObject();
		try {
			result.put(Constants.CODE, Constants.SUCCESS);
			result.put(Constants.DATAS, Json.toJson(HealthData.findLatest(braceletId)));
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.HEALTH_DATA_LIST_ERROR);
			logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[] { braceletId, e });
		}
		return ok(result);
	}

	public static Result updateUrgentList(List<Long> ids) {
		ObjectNode result = Json.newObject();
		try {
			HealthData.updateUrgentList(ids);
			result.put(Constants.CODE, Constants.SUCCESS);
		} catch (Exception e) {
			result.put(Constants.CODE, Constants.ERROR);
			result.put(Constants.MESSAGE, Messages.HEALTH_DATA_UPDATE_ERROR);
			logger.error(Messages.HEALTH_DATA_UPDATE_ERROR_MESSAGE, new Object[] { e });
		}
		return ok(result);
	}

}
