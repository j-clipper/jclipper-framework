package jclipper.common.utils.scanclass;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @author 蒹葭残辉
 * @since 2021/7/1 13:58.
 * @see <a href="https://blog.csdn.net/a729913162/article/details/81698109">史上最完整扫描包下所有类（含Jar包扫描，maven子项目扫描）</a>
 */
public class ClassScannerUtils {

    public static Set<Class<?>> searchClasses(String packageName) {
        return searchClasses(packageName, null);
    }

    public static Set<Class<?>> searchClasses(String packageName, Predicate<Class<?>> predicate) {
        return ScanExecutor.getInstance().search(packageName, predicate);
    }


    public static  Set<Class<?>> searchImplementsClasses(String packageName, Class<?> interfaceClass) {
        return searchClasses(packageName, interfaceClass::isAssignableFrom);
    }

}
