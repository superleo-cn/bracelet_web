package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Constants;
import constants.Messages;
import models.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Evetns extends Controller {

    final static Logger logger = LoggerFactory.getLogger(Evetns.class);

    public static Result findEventsByUser(String userId, String date) {
        ObjectNode result = Json.newObject();
        try {
            result.put(Constants.CODE, Constants.SUCCESS);
            result.put(Constants.DATAS, Json.toJson(Event.findByUser(userId, date)));
        } catch (Exception e) {
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, Messages.HEALTH_DATA_LIST_ERROR);
            logger.error(Messages.HEALTH_DATA_LIST_ERROR_MESSAGE, new Object[]{userId, e});
        }
        return ok(result);
    }

}
