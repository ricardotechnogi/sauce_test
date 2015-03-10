/**
 * Copyright: <Project Copyright>
 * <p/>
 * wbesite-ci-sauce-test
 * <Module> - PACKAGE_NAME
 *
 * @author rmoguel
 *         <p/>
 *         Contributors:
 * @version <version>
 * @description <description>
 */
public class Utils {

    public static String readPropertyOrEnv(String key, String defaultValue) {
        String v = System.getProperty(key);
        if (v == null)
            v = System.getenv(key);
        if (v == null)
            v = defaultValue;
        return v;
    }
}

