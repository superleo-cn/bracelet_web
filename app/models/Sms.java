package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Pagination;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_sms")
public class Sms {

    final static Logger logger = LoggerFactory.getLogger(Sms.class);

    @Id
    public Long id;

    public String mobile;

    public Integer code;

    public Boolean status;

    public String createBy, modifiedBy;

    public Date createDate, modifiedDate;

    public static Sms findByMobile(String mobile) {
        try {
            return Ebean.find(Sms.class).where().eq("mobile", mobile).findUnique();
        } catch (Exception e) {
            logger.error("[findByMobile] -> [exception]", e);
        }
        return null;
    }

    public static Sms disable(Sms sms) {
        if (sms != null) {
            sms.status = false;
            sms.modifiedDate = new Date();
            sms.modifiedBy = sms.mobile;
            Ebean.update(sms);
        }
        return sms;
    }

    public static Sms store(String mobile, Integer code) {
        try {
            Sms sms = findByMobile(mobile);
            if (sms == null) {
                sms = new Sms();
                sms.mobile = mobile;
                sms.code = code;
                sms.status = true;
                sms.createDate = new Date();
                sms.createBy = mobile;
                Ebean.save(sms);
            } else {
                sms.code = code;
                sms.status = true;
                sms.modifiedDate = new Date();
                sms.modifiedBy = mobile;
                Ebean.update(sms);
            }
        } catch (Exception e) {
            logger.error("[store] -> [exception]", e);
        }
        return null;
    }

}
