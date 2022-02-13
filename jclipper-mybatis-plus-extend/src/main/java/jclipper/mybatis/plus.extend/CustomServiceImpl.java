package jclipper.mybatis.plus.extend;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/6/11 16:46.
 */
public class CustomServiceImpl<M extends CustomBaseMapper<T>, T> extends ServiceImpl<M, T> implements IService<T> {

    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "")
    public boolean saveBatch(Collection<T> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return false;
        }
        return super.saveBatch(entityList);
    }

}
