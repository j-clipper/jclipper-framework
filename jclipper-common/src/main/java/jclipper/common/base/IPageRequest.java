package jclipper.common.base;


import jclipper.common.page.Pager;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/10/26 11:08.
 */
public interface IPageRequest {
    /**
     * 当前页码
     *
     * @return
     */
    int getCurPage();


    /**
     * 每页数据条数
     *
     * @return
     */
    int getPageSize();

    /**
     * 当前偏移量
     * 当直接设置 offset 时，使用该 offset 值,而不从 curPage 和 pageSize 来计算
     *
     * @return
     */
    int getOffset();

    /**
     * 将curPage、pageSize以及offset传递给{@code com.wf2311.common.page.Pager}对象
     *
     * @param page
     */
    default void transferTo(Pager<?> page) {
        page.setCurPage(this.getCurPage() <= 0 ? Pager.DFT_CUR_PAGE : this.getCurPage());
        page.setPageSize(this.getPageSize() <= 0 ? Pager.DFT_PAGE_SIZE : this.getPageSize());
        page.offset(this.getOffset());
    }
}
