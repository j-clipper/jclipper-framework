package jclipper.mybatis.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MyBatisPlusBaseModel<T extends Model> extends Model{
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 本条记录创建人，insert操作的时候自动为该字段赋值
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    /**
     * 本条记录创建时间，insert操作的时候自动为该字段赋值
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdOn;

    /**
     * 本条记录更新人，insert或update操作的时候自动为该字段赋值，select = false
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private Long modifiedBy;

    /**
     * 本条记录更新时间，insert或update操作的时候自动为该字段赋值，select = false
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private LocalDateTime modifiedOn;

    @TableLogic
    @TableField(select = false)
    private Boolean isDeleted;
}
