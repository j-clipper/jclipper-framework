package jclipper.common.utils.scanclass;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @author 蒹葭残辉
 * @since 2021/7/1 13:58.
 * @see <a href="https://blog.csdn.net/a729913162/article/details/81698109">史上最完整扫描包下所有类（含Jar包扫描，maven子项目扫描）</a>
 */
public class ScanExecutor implements Scan {

    private volatile static ScanExecutor instance;

    @Override
    public Set<Class<?>> search(String packageName, Predicate<Class<?>> predicate) {
        Scan fileSc = new FileScanner();
        Set<Class<?>> fileSearch = fileSc.search(packageName, predicate);
        Scan jarScanner = new JarScanner();
        Set<Class<?>> jarSearch = jarScanner.search(packageName, predicate);
        fileSearch.addAll(jarSearch);
        return fileSearch;
    }

    private ScanExecutor() {
    }

    public static ScanExecutor getInstance() {
        if (instance == null) {
            synchronized (ScanExecutor.class) {
                if (instance == null) {
                    instance = new ScanExecutor();
                }
            }
        }
        return instance;
    }

}

