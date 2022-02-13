package jclipper.mybatis.plus.extend;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;


/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/6/11 15:49.
 */
public interface CustomBaseMapper<T> extends BaseMapper<T> {

    int insertBatchSomeColumn(Collection<T> list);

}
