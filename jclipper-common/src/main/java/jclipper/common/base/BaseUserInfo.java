package jclipper.common.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/20 16:05.
 */
@Data
@NoArgsConstructor
public class BaseUserInfo implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String username;

}
