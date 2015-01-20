package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;

import constants.Constants;

@Entity
@Table(name = "tb_health_data")
public class Bracelet {

	@Id
	public int id;

	public String motionState;

	public int pulseState;

	public float temperature;

	public String braceletId;

	public Date createDate;

	public static List<Bracelet> findList() {
		return Ebean.find(Bracelet.class).findList();
	}

	public static List<Bracelet> findList(String date) {
		try {
			ExpressionList<Bracelet> expList = Ebean.find(Bracelet.class).where();
			if (StringUtils.isNotEmpty(date)) {
				Date lastDate = DateUtils.parseDate(date, Constants.PATTERN_YYYYMMDDHHMMSS);
				Date startDate = DateUtils.addSeconds(lastDate, -30);
				expList.where().ge("createDate", startDate);
				expList.where().le("createDate", lastDate);
			}
			return expList.findList();
		} catch (Exception e) {

		}
		return null;
	}

}
