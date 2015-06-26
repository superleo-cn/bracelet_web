package models;

import org.h2.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Created by superleo on 26/6/15.
 */
@Embeddable
public class AnalysisDataKey {
    public String braceletId;
    public String dataDate;

    @Override
    public int hashCode() {
        return braceletId.hashCode() + dataDate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        AnalysisDataKey val = (AnalysisDataKey) obj;
        if (StringUtils.equals(braceletId, val.braceletId) && StringUtils.equals(dataDate, val.dataDate)) {
            return true;
        }
        return false;
    }

    public String getBraceletId() {
        return braceletId;
    }

    public void setBraceletId(String braceletId) {
        this.braceletId = braceletId;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }
}
