package com.nuevatel.common.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author Ariel D. Salazar H.
 *         Created by asalazar on 11/23/15.
 */
public class StrSubstitutorTest {

    private Map<String, String>map;
    private Properties props;

    @Before
    public void setUp() throws Exception {
        // map
        map = new HashMap<String, String>();
        map.put("animal", "quick brown fox");
        map.put("target", "lazy dog");
        // props
        props = new Properties();
        props.put("animal", "quick brown fox");
        props.put("target", "lazy dog");
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        props = null;
    }

    @Test
    public void newWithMap() {
        StrSubstitutor subs = new StrSubstitutor(map);
        assertNotNull("Instance null", subs);
    }

    @Test
    public void newWithProps() {
        StrSubstitutor subs = new StrSubstitutor(props);
        assertNotNull("Instance null", subs);
    }

    @Test
    public void newWithoutParams() {
        StrSubstitutor subs = new StrSubstitutor();
        assertNotNull("Instance null", subs);
    }

    @Test
    public void loadMap() {
        StrSubstitutor subs = new StrSubstitutor();
        subs.load(map);
        assertNotNull("Instance null", subs);
    }

    @Test
    public void longProps() {
        StrSubstitutor subs = new StrSubstitutor();
        subs.load(props);
        assertNotNull("Instance null", subs);
    }

    @Test
    public void testReplace() throws Exception {
        StrSubstitutor subs = new StrSubstitutor(map);
        String resolvedString = subs.replace("The ${animal} jumped over the ${target}.");
        assertEquals("The quick brown fox jumped over the lazy dog.", resolvedString);
     }
}