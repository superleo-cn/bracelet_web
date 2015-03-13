package constants;

import java.util.Arrays;
import java.util.List;

public class Constants {

	// website URL
	public static String WEBSITE_URL = "http://pos.emd.com.sg/";
	// public static String WEBSITE_URL = "http://192.168.0.15:9000/";
	public static String PICTURE_URL = WEBSITE_URL + "public/upload/";

	// code status
	public static String CODE = "code";
	public static String CODE_SUCCESS = "200";
	public static String CODE_NOT_FOUND = "404";
	public static String CODE_FAILURE = "500";
	public static String DATAS = "datas";
	public static String STATUS = "status";
	public static String MESSAGE = "message";
	public static String COUNT = "count";
	public static String HAVE_MORE = "havemore";
	public static String NA = "N.A.";

	public static String SUCCESS = "1";
	public static String FAILURE = "0";
	public static String ERROR = "-1";

	// date format pattern
	public static String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static String PATTERN_YYYYMMDD = "yyyyMMdd";

	// current user info
	public static String USERTYPE_SUPER_ADMIN = "SUPERADMIN";
	public static String USERTYPE_ADMIN = "ADMIN";
	public static String USERTYPE_OPERATOR = "OPERATOR";
	public static String USERTYPE_CASHIER = "CASHIER";
	public static List<String> LOGIN_USERTYPS = Arrays.asList(new String[] { USERTYPE_SUPER_ADMIN, USERTYPE_ADMIN, USERTYPE_OPERATOR });

	// current user info
	public static String CURRENT_USERID = "current_id";
	public static String CURRENT_USERNAME = "current_username";
	public static String CURRENT_USER_REALNAME = "current_realname";
	public static String CURRENT_ROLE = "current_role";
	public static String CURRENT_BRACELET_ID = "current_bracelet_id";

}
