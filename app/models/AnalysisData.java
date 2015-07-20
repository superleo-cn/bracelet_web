package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MyDateUtils;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "v_analysis_data")
public class AnalysisData {

    final static Logger logger = LoggerFactory.getLogger(AnalysisData.class);

    @EmbeddedId
    public AnalysisDataKey id;

    public float avgMotionState = 0;
    public float minMotionState = 0;
    public float maxMotionState = 0;

    public float avgPulseState = 0;
    public float minPulseState = 0;
    public float maxPulseState = 0;

    public float avgTemperature = 0;
    public float minTemperature = 0;
    public float maxTemperature = 0;

    public float avgSbp = 0;
    public float minSbp = 0;
    public float maxSbp = 0;

    public float avgDbp = 0;
    public float minDbp = 0;
    public float maxDbp = 0;

    // realtime
    public static List<AnalysisData> findAnalysisData(String braceletId, String type, String date) {
        try {
            ExpressionList<AnalysisData> expList = Ebean.find(AnalysisData.class).where();
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
                expList.where().ge("id.dataDate", days.get(0));
                expList.where().le("id.dataDate", days.get(1));
                expList.where().eq("id.braceletId", braceletId);
            }
            expList.orderBy("id.dataDate desc");
            return expList.findList();
        } catch (Exception e) {
            logger.error("[findAnalysisData] -> [exception]", e);
        }
        return null;
    }

    public AnalysisDataKey getId() {
        return id;
    }

    public void setId(AnalysisDataKey id) {
        this.id = id;
    }

    public float getAvgMotionState() {
        return avgMotionState;
    }

    public void setAvgMotionState(float avgMotionState) {
        this.avgMotionState = avgMotionState;
    }

    public float getMinMotionState() {
        return minMotionState;
    }

    public void setMinMotionState(float minMotionState) {
        this.minMotionState = minMotionState;
    }

    public float getMaxMotionState() {
        return maxMotionState;
    }

    public void setMaxMotionState(float maxMotionState) {
        this.maxMotionState = maxMotionState;
    }

    public float getAvgPulseState() {
        return avgPulseState;
    }

    public void setAvgPulseState(float avgPulseState) {
        this.avgPulseState = avgPulseState;
    }

    public float getMinPulseState() {
        return minPulseState;
    }

    public void setMinPulseState(float minPulseState) {
        this.minPulseState = minPulseState;
    }

    public float getMaxPulseState() {
        return maxPulseState;
    }

    public void setMaxPulseState(float maxPulseState) {
        this.maxPulseState = maxPulseState;
    }

    public float getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(float avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public float getAvgSbp() {
        return avgSbp;
    }

    public void setAvgSbp(float avgSbp) {
        this.avgSbp = avgSbp;
    }

    public float getMinSbp() {
        return minSbp;
    }

    public void setMinSbp(float minSbp) {
        this.minSbp = minSbp;
    }

    public float getMaxSbp() {
        return maxSbp;
    }

    public void setMaxSbp(float maxSbp) {
        this.maxSbp = maxSbp;
    }

    public float getAvgDbp() {
        return avgDbp;
    }

    public void setAvgDbp(float avgDbp) {
        this.avgDbp = avgDbp;
    }

    public float getMinDbp() {
        return minDbp;
    }

    public void setMinDbp(float minDbp) {
        this.minDbp = minDbp;
    }

    public float getMaxDbp() {
        return maxDbp;
    }

    public void setMaxDbp(float maxDbp) {
        this.maxDbp = maxDbp;
    }
}
