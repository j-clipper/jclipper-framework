package jclipper.springboot.validation;

/**
 * 正则表达式常量
 */
public interface RegexConst {
	/**
	 * 正则表达式：验证手机号
	 */
	String REGEX_MOBILE = "^1[3456789]\\d{9}$";

	/**
	 * 正则表达式：验证邮箱
	 */
	String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 正则表达式：验证身份证号
	 */
	String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
}
