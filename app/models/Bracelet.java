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
import utils.Pagination;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_bracelet")
public class Bracelet {

	final static Logger logger = LoggerFactory.getLogger(Bracelet.class);

	@Id
	private Long id;

	@Required(message = "Bracelet Id cannot be empty")
	private String braceletId;

	private String name;

	private String type;

	@Required(message = "Status cannot be empty")
	private Boolean status;

	private String createBy, modifiedBy;

	private Date createDate, modifiedDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

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

	public static Pagination findAll(Pagination pagination) {
		try {
			pagination = pagination == null ? new Pagination() : pagination;
			ExpressionList<Bracelet> expList = Ebean.find(Bracelet.class).where();
			PagingList<Bracelet> pagingList = expList.findPagingList(pagination.pageSize);
			pagingList.setFetchAhead(false);
			expList.orderBy("createDate desc");
			Page<Bracelet> page = pagingList.getPage(pagination.currentPage - 1);
			pagination.recordList = page.getList();
			pagination.pageCount = page.getTotalPageCount();
			pagination.recordCount = page.getTotalRowCount();
		} catch (Exception e) {
			logger.error("[findAll] -> [exception]", e);
		}
		return pagination;
	}

	public static Bracelet store(Bracelet user) {
		if (user != null && user.id > 0) {
			Ebean.update(user);
		} else {
			Ebean.save(user);
		}
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBraceletId() {
		return braceletId;
	}

	public void setBraceletId(String braceletId) {
		this.braceletId = braceletId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
