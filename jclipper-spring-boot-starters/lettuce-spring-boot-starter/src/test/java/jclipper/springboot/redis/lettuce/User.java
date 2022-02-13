package jclipper.springboot.redis.lettuce;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/23 20:38.
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Long id;
    private Boolean sex;
    private String name;
    private Integer age;
    private String password;
    private LocalDateTime time = LocalDateTime.now();
}
