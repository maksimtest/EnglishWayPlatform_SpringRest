package platform.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtil {
    public static List<Integer> getMarksByStr(String str) {
        if (str == null || str.isEmpty()) {
            return new ArrayList<Integer>();
        }
        System.out.println("StringUtil.getMarksByStr_1, str=" + str);

        List<Integer> arr = Arrays.stream(str.split(","))
                .map(Integer::parseInt).toList();
        System.out.println("StringUtil.getMarksByStr_2, arr=" + arr);
        return arr;
    }
}
