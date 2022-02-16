package jclipper.common.enums;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/27 11:10.
 */
public interface BaseErrorCode extends BaseEnum<Integer,String> {

    /**
     * 错误码
     *
     * @return
     */
    @Override
    Integer getCode();

    /**
     * 错误信息
     *
     * @return
     */
    @Override
    String getName();

}
