package jclipper.mybatis.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import jclipper.common.page.Pager;

import java.util.List;

public class PageAdapter<T> extends Pager<T> implements IPage<T> {

    private Pager<T> page;

    public PageAdapter(Pager<T> page) {
        this.page = page;
    }

    @Override
    public List<OrderItem> orders() {
        return null;
    }

    @Override
    public List<T> getRecords() {
        return page.getDataList();
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        page.setDataList(records);
        return this;
    }

    @Override
    public long getTotal() {
        return page.getTotalSize();
    }

    @Override
    public IPage<T> setTotal(long total) {
        page.setTotalSize((int) total);
        return this;
    }

    @Override
    public long getSize() {
        return page.getPageSize();
    }

    @Override
    public IPage<T> setSize(long size) {
        page.setPageSize((int) size);
        return this;
    }

    @Override
    public long getCurrent() {
        return page.getCurPage();
    }

    @Override
    public IPage<T> setCurrent(long current) {
        page.setCurPage((int) current);
        return this;
    }
}
