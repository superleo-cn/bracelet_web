package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_bracelet")
public class Bracelet {

	final static Logger logger = LoggerFactory.getLogger(Bracelet.class);

	@Id
	public Long id;

	@Required(message = "Bracelet Id cannot be empty")
	public String braceletId;

	public String name;

	public String type;

	@Required(message = "Status cannot be empty")
	public Boolean status;

	public String createBy, modifiedBy;

	public Date createDate, modifiedDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	public User user;

	/* the following are service methods */
	public static Bracelet findById(Long id) {
		return Ebean.find(Bracelet.class, id);
	}

	public static Bracelet findByBraceletId(String braceletId) {
		return Ebean.find(Bracelet.class).select("id, braceletId, name, type, status").findUnique();
	}

	public static List<Bracelet> findByUserId(Long userId) {
		return Ebean.find(Bracelet.class).select("id, braceletId, name, type, status").where().eq("user.id", userId).eq("status", true).findList();
	}

}
