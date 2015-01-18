package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Application extends Controller {

	public static Result index() {
		return ok(views.html.index.render());
	}

	public static Result healthData() {
		ObjectNode result = Json.newObject();
		result.put("exampleField1", "foobar");
		result.put("exampleField2", "Hello world!");
		return ok(result);
	}

}
