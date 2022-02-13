package jclipper.mybatis.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jclipper.common.page.Pager;

public class PageUtils {

	/**
	 * 乐课网page转为mybatisPlusPage
	 *
	 * @param page
	 * @param <T>
	 * @return
	 */
	public static <T> IPage<T> convertToMPPage(Pager<T> page) {
		if (page == null) {
			return null;
		}
		return new PageAdapter<>(page);
	}


}
