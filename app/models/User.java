package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.data.validation.Constraints.Required;
import utils.Pagination;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;

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

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	public List<Bracelet> bracelets;

	/* the following are service methods */
	public static User login(LoginForm form) {
		List<User> users = Ebean.find(User.class).select("id, username, realname, userType, status, lastLoginDate").where().eq("username", form.username)
				.eq("password", form.password).eq("status", true).findList();
		if (CollectionUtils.size(users) > 0) {
			return users.get(0);
		}
		return null;
	}

	public static List<User> findAll() {
		try {
			ExpressionList<User> expList = Ebean.find(User.class).where();
			expList.orderBy("createDate desc");
			return expList.findList();
		} catch (Exception e) {
			logger.error("[findAll] -> [exception]", e);
		}
		return null;
	}

	public static Pagination findAll(Pagination pagination) {
		try {
			pagination = pagination == null ? new Pagination() : pagination;
			ExpressionList<User> expList = Ebean.find(User.class).where();
			PagingList<User> pagingList = expList.findPagingList(pagination.pageSize);
			pagingList.setFetchAhead(false);
			expList.orderBy("createDate desc");
			Page<User> page = pagingList.getPage(pagination.currentPage - 1);
			pagination.recordList = page.getList();
			pagination.pageCount = page.getTotalPageCount();
			pagination.recordCount = page.getTotalRowCount();
		} catch (Exception e) {
			logger.error("[findAll] -> [exception]", e);
		}
		return pagination;

	}

}
