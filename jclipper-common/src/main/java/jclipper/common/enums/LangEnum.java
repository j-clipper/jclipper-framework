package jclipper.common.enums;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/21 14:01.
 */
@Getter
@AllArgsConstructor
public enum LangEnum implements BaseCode<String, String> {

    CN("cn", "中文"),
    ZH_CN("zh_cn", "中文"),
    EN("en", "英文"),
    EN_US("en_us", "英文"),
    RU("ru", "俄文"),
    RU_RU("ru_ru", "俄文");

    /**
     * * 编码
     **/
    @ApiModelProperty(value = "编码")
    private String code;

    /**
     * * 名称
     **/
    @ApiModelProperty(value = "名称")
    private String name;

    public static String getLang(String acceptLang) {

        String lang;

        if (EN.getCode().equals(acceptLang) ||
                EN_US.getCode().equals(acceptLang)) {

            lang = EN_US.getCode();

        } else if (RU.getCode().equals(acceptLang) ||
                RU_RU.getCode().equals(acceptLang)) {

            lang = RU_RU.getCode();

        } else {

            lang = ZH_CN.getCode();
        }

        return lang;
    }
}
