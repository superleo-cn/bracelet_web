package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.Expose;

@Entity
@Table(name = "tb_user")
public class User {

	final static Logger logger = LoggerFactory.getLogger(User.class);

	@Id
	public Long id;

	@Expose
	@Required(message = "Username cannot be empty")
	public String username;

	@Expose
	@Required(message = "Password cannot be empty")
	public String password;

	@Expose
	public String realname;

	@Expose
	@Required(message = "User type cannot be empty")
	public String userType;

	@Expose
	@Required(message = "Status cannot be empty")
	public Boolean status;

	@Expose
	public String createBy, modifiedBy;

	@Expose
	public Date createDate, modifiedDate, lastLoginDate;

	@Transient
	public List<Bracelet> bracelets;

	/* the following are service methods */
	public static User login(User user) {
		List<User> users = Ebean.find(User.class).select("id, username, realname, userType, status, lastLoginDate").where().eq("username", user.username).eq("status", true)
				.findList();
		if (CollectionUtils.size(users) > 0) {
			return users.get(0);
		}
		return null;
	}

}
