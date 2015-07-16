package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import constants.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Pagination;

import javax.persistence.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_event")
public class Event {

    final static Logger logger = LoggerFactory.getLogger(Event.class);

    @Id
    public Long id;

    public String type;

    public String title;

    public String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public String createBy;

    public Date createDate;

    public String getDate() {
        if (createDate != null) {
            return DateFormatUtils.format(createDate, Constants.PATTERN_YYYYMMDD_SLASH);
        }
        return null;
    }

    public static Map<String, List<Event>> findByUser(String userId, String date) {
        try {
            ExpressionList<Event> expList = Ebean.find(Event.class).where();
            Date lastDate = DateUtils.parseDate(date, Constants.PATTERN_YYYYMMDD);
            lastDate = DateUtils.addDays(lastDate, 1);
            Date startDate = DateUtils.addDays(lastDate, -6);
            expList.where().eq("user.id", userId);
            expList.where().ge("createDate", startDate);
            expList.where().lt("createDate", lastDate);
            expList.orderBy("createDate asc");
            return getFinalResult(expList.findList());
        } catch (Exception e) {
            logger.error("[findByUser] -> [exception]", e);
        }
        return null;
    }

    public static Map<String, List<Event>> getFinalResult(List<Event> list) {
        Map<String, List<Event>> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Event e : list) {
                String date = e.getDate();
                if (!map.containsKey(date)) {
                    List<Event> events = new ArrayList<>();
                    events.add(e);
                    map.put(date, events);
                } else {
                    map.get(date).add(e);
                }
            }
        }
        return map;
    }

}
