package com.nuevatel.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * To replace parametrized string.
 * <br/>
 * <br/>
 * <code>
 * Map map = HashMap();<br/>
 * map.put("animal", "quick brown fox");<br/>
 * map.put("target", "lazy dog");<br/>
 * StrSubstitutor sub = new StrSubstitutor(map);<br/>
 * String resolvedString = sub.replace("The ${animal} jumped over the ${target}.");<br/>
 * </code>
 * <br/>
 *
 * @author Ariel D. Salazar H.
 *         Created by asalazar on 11/23/15.
 */
public class StrSubstitutor {

    protected Map<String, String> map;

    /**
     * Create with an empty Map.
     */
    public StrSubstitutor() {
        map = Collections.emptyMap();
    }

    /**
     *
     * @param map List of properties to replace.
     */
    public StrSubstitutor(Map<String, String> map) {
        Parameters.checkNull(map, "map");

        this.map = map;
    }

    /**
     *
     * @param props Porperties to replace.
     */
    public StrSubstitutor(Properties props) {
        this((Map)props);
    }

    public void load(Map<String, String>map) {
        this.map = map;
    }

    public void load(Properties props) {
        this.map = new HashMap<String, String>((Map)props);
    }

    /**
     *
     * @param str String to resolve.
     * @return New string with replaced parameters.
     */
    public String replace(String str) {
        StringBuffer sb = new StringBuffer();
        char[] strArray = str.toCharArray();
        int i = 0;
        while (i < strArray.length - 1) {
            if (strArray[i] == '$' && strArray[i + 1] == '{') {
                i = i + 2;
                int begin = i;
                while (strArray[i] != '}') {
                    ++i;
                }
                sb.append(getPropValue(str.substring(begin, i++)));
            } else {
                sb.append(strArray[i]);
                ++i;
            }
        }
        if(i<strArray.length) {
            sb.append(strArray[i]);
        }
        return sb.toString();
    }

    /**
     * Overwrite it to modify the way to get the this value.
     *
     * @param name Property metadata
     * @return Value to resolve metadata
     */
    protected String getPropValue(String name) {
        return map.get(name);
    }

    public void setProperty(String name, String value) {
        map.put(name, value);
    }

    /**
     *
     * @param map
     * @return Creates new <code>StrSubstitutor</code> from <code>Map</code>, but do not keep map reference, instead
     * it uses a copy.
     */
    public static StrSubstitutor fromCopyMap(Map<String, String>map) {
        Map<String, String>copy = new HashMap<String, String>(map);
        return new StrSubstitutor(copy);
    }
}
