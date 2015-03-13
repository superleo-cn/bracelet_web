package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Pagination;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;

@Entity
@Table(name = "tb_code")
public class Code {

	final static Logger logger = LoggerFactory.getLogger(Code.class);

	@Id
	public Long id;

	public String code;

	public Float value;

	public String description;

	public Boolean status;

	public String createBy, modifiedBy;

	public Date createDate, modifiedDate;

	public static Pagination findAll(Pagination pagination) {
		try {
			pagination = pagination == null ? new Pagination() : pagination;
			ExpressionList<Code> expList = Ebean.find(Code.class).where();
			PagingList<Code> pagingList = expList.findPagingList(pagination.pageSize);
			pagingList.setFetchAhead(false);
			expList.orderBy("code asc");
			Page<Code> page = pagingList.getPage(pagination.currentPage - 1);
			pagination.recordList = page.getList();
			pagination.pageCount = page.getTotalPageCount();
			pagination.recordCount = page.getTotalRowCount();
		} catch (Exception e) {
			logger.error("[findAll] -> [exception]", e);
		}
		return pagination;

	}

}
