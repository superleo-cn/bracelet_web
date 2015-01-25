package inteceptors;

import java.lang.reflect.Method;
import java.util.Date;

import play.GlobalSettings;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http.Request;

public class TimeInterceptor extends GlobalSettings {

	public Action<?> onRequest(Request request, Method actionMethod) {
		long start = (new Date()).getTime();
		Logger.info("Action %s execution time is %d ms", actionMethod.getName(), start);
		return super.onRequest(request, actionMethod);
		// long time = (new Date()).getTime() - start;
		// Logger.info("Action %s execution time is %d ms",
		// actionMethod.getName(), time);
	}

}