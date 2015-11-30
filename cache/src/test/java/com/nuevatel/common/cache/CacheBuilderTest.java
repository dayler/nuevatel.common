/**
 * 
 */
package com.nuevatel.common.cache;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author dayler
 *
 */
public class CacheBuilderTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // No op
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // No op
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // No op
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        // No op
    }

    /**
     * Test method for {@link com.nuevatel.common.cache.CacheBuilder#buildSimpleLoadingCache(com.nuevatel.common.cache.CacheLoader)}.
     */
    @Test
    public void testBuildSimpleLoadingCache() {
        LoadingCache<String, String>loadingCache = CacheBuilder.newCacheBuilder()
                                                               .setSize(4)
                                                               .setExpireAfterWriteTime(2000L)
                                                               .setExpireAfterReadTime(1000L)
                                                               .buildSimpleLoadingCache((key)->{return "test";});
        assertNotNull("null loading cache", loadingCache);
    }

}
