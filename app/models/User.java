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
	private Long id;

	@Required(message = "Username cannot be empty")
	private String username;

	@Required(message = "Password cannot be empty")
	private String password;

	private String realname;

	@Required(message = "User type cannot be empty")
	private String userType;

	@Required(message = "Status cannot be empty")
	private Boolean status;

	private String createBy, modifiedBy;

	private Date createDate, modifiedDate, lastLoginDate;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	public List<Bracelet> bracelets;

	/* the following are service methods */
	public static User login(LoginForm form) {
		List<User> users = Ebean.find(User.class).select("id, username, realname, userType, status, lastLoginDate").where().eq("username", form.getUsername())
				.eq("password", form.getPassword()).eq("status", true).findList();
		if (CollectionUtils.size(users) > 0) {
			return users.get(0);
		}
		return null;
	}

	public static User findById(Long id) {
		return Ebean.find(User.class, id);
	}

	public static User store(User user) {
		if (user != null && user.id > 0) {
			Ebean.update(user);
		} else {
			Ebean.save(user);
		}
		return user;
	}

	public static List<User> findAll() {
		ExpressionList<User> expList = Ebean.find(User.class).where();
		expList.orderBy("createDate desc");
		return expList.findList();
	}

	public static Pagination findAll(Pagination pagination) {
		pagination = pagination == null ? new Pagination() : pagination;
		ExpressionList<User> expList = Ebean.find(User.class).where();
		PagingList<User> pagingList = expList.findPagingList(pagination.pageSize);
		pagingList.setFetchAhead(false);
		expList.orderBy("createDate desc");
		Page<User> page = pagingList.getPage(pagination.currentPage - 1);
		pagination.recordList = page.getList();
		pagination.pageCount = page.getTotalPageCount();
		pagination.recordCount = page.getTotalRowCount();
		return pagination;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public List<Bracelet> getBracelets() {
		return bracelets;
	}

	public void setBracelets(List<Bracelet> bracelets) {
		this.bracelets = bracelets;
	}

}
