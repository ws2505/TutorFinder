package Utils;

/**
 * Created by wenbo on 2017/6/20.
 */

public class StringUtils {

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

}
