package jclipper.common.base;

import lombok.Data;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/11/26 14:44.
 */
@Data
public class BasePageRequest implements IPageRequest {
    /**
     * 当前页码
     */
    private int curPage;
    /**
     * 每页数据条数
     */
    private int pageSize;

    /**
     * 当直接设置 offset 时，使用该 offset 值,而不从 curPage 和 pageSize 来计算
     */
    private int offset;
}
