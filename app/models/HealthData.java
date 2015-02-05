package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;

import constants.Constants;

@Entity
@Table(name = "tb_health_data")
public class HealthData {

	final static Logger logger = LoggerFactory.getLogger(HealthData.class);

	@Id
	public Long id;

	public int motionState = 0;

	public int pulseState = 0;

	public float temperature = 0.0f;

	public int warning = 0;

	public int archive = 0;

	public String braceletId;

	public Date createDate;

	// dashboard
	public static List<HealthData> findDashboardList(String braceletId, String date) {
		try {
			ExpressionList<HealthData> expList = Ebean.find(HealthData.class).where();
			if (StringUtils.isNotEmpty(braceletId) && StringUtils.isNotEmpty(date)) {
				Date lastDate = DateUtils.parseDate(date, Constants.PATTERN_YYYYMMDDHHMMSS);
				Date startDate = DateUtils.addMinutes(lastDate, -10);
				expList.where().ge("createDate", startDate);
				expList.where().le("createDate", lastDate);
			}
			expList.orderBy("createDate desc");
			return expList.findList();
		} catch (Exception e) {
			logger.error("[findDashboardList] -> [exception]", e);
		}
		return null;
	}

	// interface
	public static List<HealthData> findList(String braceletId, String date) {
		try {
			ExpressionList<HealthData> expList = Ebean.find(HealthData.class).where();
			if (StringUtils.isNotEmpty(braceletId) && StringUtils.isNotEmpty(date)) {
				Date startDate = DateUtils.parseDate(date, Constants.PATTERN_YYYYMMDD);
				Date endDate = DateUtils.addDays(startDate, 1);
				expList.where().eq("braceletId", braceletId);
				expList.where().ge("createDate", startDate);
				expList.where().le("createDate", endDate);
			}
			expList.orderBy("createDate desc");
			return expList.findList();
		} catch (Exception e) {
			logger.error("[findList] -> [exception]", e);
		}
		return null;
	}

	public static List<HealthData> findUrgentList(String braceletId) {
		try {
			ExpressionList<HealthData> expList = Ebean.find(HealthData.class).where();
			if (StringUtils.isNotEmpty(braceletId)) {
				expList.where().eq("braceletId", braceletId);
				expList.where().eq("warning", 1);
				expList.where().eq("archive", 0);
			}
			expList.orderBy("createDate desc");
			return expList.findList();
		} catch (Exception e) {
			logger.error("[findUrgentList] -> [exception]", e);
		}
		return null;
	}

	public static void updateUrgentList(List<Long> ids) {
		try {
			if (ids != null) {
				for (Long id : ids) {
					HealthData data = Ebean.find(HealthData.class, id);
					if (data != null) {
						data.archive = 1;
						Ebean.update(data);
					}
				}
			}
		} catch (Exception e) {
			logger.error("[updateUrgentList] -> [exception]", e);
		}
	}

}
