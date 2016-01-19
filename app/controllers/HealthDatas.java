package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Constants;
import constants.Messages;
import models.AnalysisData;
import models.HealthData;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class HealthDatas extends Controller {

    final static Logger logger = LoggerFactory.getLogger(HealthDatas.class);

    // realtime
    public static Result records() {
        ObjectNode result = Json.newObject();
        String braceletId = "1234567";
        try {
            result.put(Constants.CODE, Constants.SUCCESS);
            List<HealthData> datas = HealthData.findRealtimeList(braceletId);
            StringBuilder sb = new StringBuilder("<table border='1' width='100%'>");
            sb.append("<tr>");
            sb.append("<td width='30%'>时间</td>");
            sb.append("<td width='10%'>体温</td>");
            sb.append("<td width='10%'>脉搏</td>");
            sb.append("<td width='10%'>跌倒</td>");
            sb.append("<td width='10%'>高压</td>");
            sb.append("<td width='10%'>低压</td>");
            sb.append("<td width='10%'>经度</td>");
            sb.append("<td width='10%'>维度</td>");
            sb.append("</tr>");
            if(datas != null){
                datas.stream().forEach(d -> {
                    sb.append("<tr>");
                    sb.append("<td>" + DateFormatUtils.format(d.createDate, "yyyy-MM-dd HH:mm:ss") +  "</td>");
                    sb.append("<td>" + d.temperature + "</td>");
                    sb.append("<td>" + d.pulseState + "</td>");
                    sb.append("<td>" + d.motionState + "</td>");
                    sb.append("<td>" + d.sbp + "</td>");
                    sb.append("<td>" + d.dbp + "</td>");
                    sb.append("<td>" + d.latitude + "</td>");
                    sb.append("<td>" + d.longitude + "</td>");
                    sb.append("</tr>");
                });
            }
           return ok(sb.toString()).as("text/html");
        } catch (Exception e) {
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, Messages.HEALTH_DATA_LIST_ERROR);
            logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[]{braceletId, e});
        }
        return ok(result);
    }


    // realtime
    public static Result day() {
        ObjectNode result = Json.newObject();
        String braceletId = "1234567";
        try {
            result.put(Constants.CODE, Constants.SUCCESS);
            List<HealthData> datas = HealthData.findRealtimeByToday(braceletId);
            StringBuilder sb = new StringBuilder("<table border='1' width='100%'>");
            sb.append("<tr>");
            sb.append("<td width='30%'>时间</td>");
            sb.append("<td width='10%'>体温</td>");
            sb.append("<td width='10%'>脉搏</td>");
            sb.append("<td width='10%'>跌倒</td>");
            sb.append("<td width='10%'>高压</td>");
            sb.append("<td width='10%'>低压</td>");
            sb.append("<td width='10%'>经度</td>");
            sb.append("<td width='10%'>维度</td>");
            sb.append("</tr>");
            if(datas != null){
                datas.stream().forEach(d -> {
                    sb.append("<tr>");
                    sb.append("<td>" + DateFormatUtils.format(d.createDate, "yyyy-MM-dd HH:mm:ss") +  "</td>");
                    sb.append("<td>" + d.temperature + "</td>");
                    sb.append("<td>" + d.pulseState + "</td>");
                    sb.append("<td>" + d.motionState + "</td>");
                    sb.append("<td>" + d.sbp + "</td>");
                    sb.append("<td>" + d.dbp + "</td>");
                    sb.append("<td>" + d.latitude + "</td>");
                    sb.append("<td>" + d.longitude + "</td>");
                    sb.append("</tr>");
                });
            }
            return ok(sb.toString()).as("text/html");
        } catch (Exception e) {
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, Messages.HEALTH_DATA_LIST_ERROR);
            logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[]{braceletId, e});
        }
        return ok(result);
    }

    // realtime
    public static Result findRealtimeList(String braceletId, String date, boolean isFirst) {
        ObjectNode result = Json.newObject();
        try {
            result.put(Constants.CODE, Constants.SUCCESS);
            if (isFirst) {
                result.put(Constants.DATAS, Json.toJson(HealthData.findFirstTime(braceletId)));
            } else {
                result.put(Constants.DATAS, Json.toJson(HealthData.findRealtimeList(braceletId, date)));
            }
        } catch (Exception e) {
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, Messages.HEALTH_DATA_LIST_ERROR);
            logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[]{braceletId, e});
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
            logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[]{braceletId, e});
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
            logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[]{braceletId, e});
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
            logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[]{braceletId, e});
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
            logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[]{braceletId, e});
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
            logger.error(Messages.HEALTH_DATA_UPDATE_ERROR_MESSAGE, new Object[]{e});
        }
        return ok(result);
    }

    public static Result findAnalysisData(String braceletId, String type, String date) {
        ObjectNode result = Json.newObject();
        try {
            result.put(Constants.CODE, Constants.SUCCESS);
            result.put(Constants.DATAS, Json.toJson(AnalysisData.findAnalysisData(braceletId, type, date)));
        } catch (Exception e) {
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, Messages.HEALTH_DATA_LIST_ERROR);
            logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[]{braceletId, e});
        }
        return ok(result);
    }

}
