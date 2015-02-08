package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Sep 9, 2009 9:50:41 AM @ [PaginationList]
 */
public class Pagination {

	public List<?> recordList = new ArrayList<>();

	public long recordCount = 0;

	public long pageCount = 0;

	public int pageSize = 15;

	public int currentPage = 1;

	public Pagination() {
	}

	public List<?> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<?> recordList) {
		this.recordList = recordList;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
