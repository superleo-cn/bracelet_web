package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.MyDateUtils;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;

import constants.Constants;
import utils.Pagination;

@Entity
@Table(name = "tb_health_data")
public class HealthData {

    final static Logger logger = LoggerFactory.getLogger(HealthData.class);

    @Id
    public Long id;

    public int motionState = 0;

    public int pulseState = 0;

    public float temperature = 0.0f;

    public int sbp = 0;

    public int dbp = 0;

    public int warning = 0;

    public int archive = 0;

    public Float latitude = 0.0f;

    public Float longitude = 0.0f;

    public String braceletId;

    public Date createDate;

    // test only
    public static List<HealthData> findRealtimeList(String braceletId) {
        try {
            Pagination pagination = new Pagination();
            pagination.setPageSize(24);
            ExpressionList<HealthData> expList = Ebean.find(HealthData.class).where();
            expList.where().eq("braceletId", braceletId);
            PagingList<HealthData> pagingList = expList.findPagingList(50);
            pagingList.setFetchAhead(false);
            expList.orderBy("createDate desc");
            Page<HealthData> page = pagingList.getPage(0);
            return page.getList();
        } catch (Exception e) {
            logger.error("[findRealtimeList] -> [exception]", e);
        }
        return null;
    }

    public static List<HealthData> findRealtimeByToday(String braceletId) {
        try {
            return findList(braceletId, DateFormatUtils.format(new Date(), Constants.PATTERN_YYYYMMDD));
        } catch (Exception e) {
            logger.error("[findRealtimeByToday] -> [exception]", e);
        }
        return null;
    }

    // realtime
    public static List<HealthData> findRealtimeList(String braceletId, String date) {
        try {
            ExpressionList<HealthData> expList = Ebean.find(HealthData.class).where();
            if (StringUtils.isNotEmpty(braceletId) && StringUtils.isNotEmpty(date)) {
                Date lastDate = DateUtils.parseDate(date, Constants.PATTERN_YYYYMMDDHHMMSS);
                Date startDate = DateUtils.addSeconds(lastDate, -5);
                expList.where().eq("braceletId", braceletId);
                expList.where().ge("createDate", startDate);
                expList.where().le("createDate", lastDate);
            }
            expList.orderBy("createDate desc");
            return expList.findList();
        } catch (Exception e) {
            logger.error("[findRealtimeList] -> [exception]", e);
        }
        return null;
    }

    public static List<HealthData> findFirstTime(String braceletId) {
        try {
            Pagination pagination = new Pagination();
            pagination.setPageSize(24);
            ExpressionList<HealthData> expList = Ebean.find(HealthData.class).where();
            PagingList<HealthData> pagingList = expList.findPagingList(pagination.pageSize);
            pagingList.setFetchAhead(false);
            expList.orderBy("createDate desc");
            Page<HealthData> page = pagingList.getPage(pagination.currentPage - 1);
            return page.getList();
        } catch (Exception e) {
            logger.error("[findRealtimeList] -> [exception]", e);
        }
        return null;
    }

    // history
    public static List<HealthData> findHistoryList(String braceletId, String type, String date) {
        try {
            ExpressionList<HealthData> expList = Ebean.find(HealthData.class).where();
            if (StringUtils.isNotEmpty(braceletId) && StringUtils.isNotEmpty(date)) {
                List<Date> days = null;
                if (StringUtils.equals(type, "day")) {
                    days = MyDateUtils.getDateByDay();
                } else if (StringUtils.equals(type, "week")) {
                    days = MyDateUtils.getDateByWeek();
                } else if (StringUtils.equals(type, "month")) {
                    days = MyDateUtils.getDateByMonth();
                } else {
                    days = MyDateUtils.getDateByDay();
                }
                expList.where().ge("createDate", days.get(0));
                expList.where().le("createDate", days.get(1));
                expList.where().eq("braceletId", braceletId);
            }
            expList.orderBy("createDate desc");
            return expList.findList();
        } catch (Exception e) {
            logger.error("[findHistoryList] -> [exception]", e);
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

    // interface
    public static HealthData findLatest(String braceletId) {
        try {
            ExpressionList<HealthData> expList = Ebean.find(HealthData.class).where();
            if (StringUtils.isNotEmpty(braceletId)) {
                PagingList<HealthData> pagingList = expList.findPagingList(1);
                expList.where().eq("braceletId", braceletId);
                pagingList.setFetchAhead(false);
                expList.orderBy("createDate desc");
                Page<HealthData> page = pagingList.getPage(0);
                List<HealthData> list = page.getList();
                if (CollectionUtils.isNotEmpty(list)) {
                    return list.get(0);
                }
            }
        } catch (Exception e) {
            logger.error("[findLatest] -> [exception]", e);
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
