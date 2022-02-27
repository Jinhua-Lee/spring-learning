package cn.spring.learning.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 属性文件的解析类
 *
 * @author Jinhua
 */
public class PropertiesResolver {
    
    private static final ResourceBundle BUNDLE;

    static {
        BUNDLE = ResourceBundle.getBundle("mysql");
    }

    public static String getValue(String key) throws MissingResourceException {
        return BUNDLE.getString(key);
    }
}
