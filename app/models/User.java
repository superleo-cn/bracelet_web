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

import forms.LoginForm;

@Entity
@Table(name = "tb_user")
public class User {

	final static Logger logger = LoggerFactory.getLogger(User.class);

	@Id
	public Long id;

	@Required(message = "Username cannot be empty")
	public String username;

	@Required(message = "Password cannot be empty")
	public String password;

	public String realname;

	@Required(message = "User type cannot be empty")
	public String userType;

	@Required(message = "Status cannot be empty")
	public Boolean status;

	public String createBy, modifiedBy;

	public Date createDate, modifiedDate, lastLoginDate;

	@Transient
	public List<Bracelet> bracelets;

	/* the following are service methods */
	public static User login(LoginForm form) {
		List<User> users = Ebean.find(User.class).select("id, username, realname, userType, status, lastLoginDate").where().eq("username", form.username)
				.eq("password", form.password).eq("status", true).findList();
		if (CollectionUtils.size(users) > 0) {
			User user = users.get(0);
			List<Bracelet> list = Bracelet.findByUserId(user.id);
			user.bracelets = list;
			return user;
		}
		return null;
	}

}
