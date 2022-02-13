package jclipper.common.page;


import jclipper.common.base.IPageRequest;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页对象
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/2/3 16:06.
 */
public class Pager<T> implements IPageRequest, Serializable {
    public static final int OFFSET_IGNORE = -1;
    public static final int DFT_CUR_PAGE = 1;
    public static final int DFT_PAGE_SIZE = 10;
    public static final int DFT_PAGE_COUNT = 10;

    public static final int NO_ROW_OFFSET = 0;

    public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;

    private int limit;

    /**
     * 当前页码
     */
    private int curPage = DFT_CUR_PAGE;
    /**
     * 每页数据条数
     */
    private int pageSize = DFT_PAGE_SIZE;
    /**
     * 数据总条数
     */
    private int totalSize;
    /**
     * 数据总页数
     */
    private int totalPage;

    /**
     * 当直接设置 offset 时，使用该 offset 值,而不从 curPage 和 pageSize 来计算
     */
    private int offset = OFFSET_IGNORE;

    private List<T> dataList = Collections.emptyList();

    /**
     * 分页显示的页数
     */
    private int pageCount = DFT_PAGE_COUNT;

    public Pager() {
        this(DFT_CUR_PAGE, DFT_PAGE_SIZE);
    }

    public Pager(int curPage, int pageSize) {
        this.curPage = curPage;
        this.pageSize = pageSize;
    }

    @Override
    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = Math.max(curPage, DFT_CUR_PAGE);
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? DFT_PAGE_SIZE : pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;

        if (0 != totalSize) {
            totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
            this.curPage = this.curPage > totalPage ? totalPage : curPage;
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 获取 offset
     */
    @Override
    public int getOffset() {
        if (offset >= 0) {
            return offset;
        } else {
            return curPage > 0 ? (curPage - 1) * pageSize : 0;
        }
    }

    /**
     * 设置 offset
     *
     * @param offset
     * @return 当前对象
     */
    public Pager<T> offset(int offset) {
        this.offset = offset;
        return this;
    }

    /**
     * 获取 limit
     */
    public int getLimit() {
        return pageSize;
    }

    /**
     * 设置 limit
     *
     * @param pageSize
     * @return
     */
    public Pager limit(int pageSize) {
        setPageSize(pageSize);
        return this;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    /**
     * @return the pageCount
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * @param pageCount the pageCount to set
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount <= 0 ? DFT_PAGE_COUNT : pageCount;
    }

    public int getStartPage() {
        int endPage = getEndPage();
        int startPage = endPage - pageCount + 1;
        if (startPage < 1) {
            startPage = 1;
        }
        return startPage;
    }

    public int getEndPage() {
        int endPage = curPage + pageCount / 2 - 1;
        if (endPage < pageCount) {
            endPage = pageCount;
        }
        if (endPage > totalPage) {
            endPage = totalPage;
        }
        return endPage;
    }
}
