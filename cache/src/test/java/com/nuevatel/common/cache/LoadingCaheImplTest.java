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
public class LoadingCaheImplTest {
    
    private static final long TEST_EXPIRE_AFTER_WRITE_TIME = 100L;
    
    private static final long TEST_EXPIRE_AFTER_READ_TIME = 50;
    
    private static final int SIZE = 4;
    
    private LoadingCache<String, Person>cache;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // no op
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // no op
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        cache = new LoadingCaheImpl<>(SIZE,
                                      (name)->new Person(name),
                                      TEST_EXPIRE_AFTER_WRITE_TIME,
                                      TEST_EXPIRE_AFTER_READ_TIME);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        cache.shutdown();
        cache = null;
    }

    /**
     * Test method for {@link com.nuevatel.common.cache.LoadingCaheImpl#LoadingCaheImpl(int, com.nuevatel.common.cache.CacheLoader)}.
     */
    @Test
    public void newLoadingCahceWithIntAndCacheLoaderOfKV() {
        cache = new LoadingCaheImpl<>(SIZE,
                                      (name)->new Person(name));
        assertNotNull("null cache", cache);
    }

    /**
     * Test method for {@link com.nuevatel.common.cache.LoadingCaheImpl#LoadingCaheImpl(int, com.nuevatel.common.cache.CacheLoader, long, long)}.
     */
    @Test
    public void newLoadingCacheWithSizeWithCacheLoaderWithExpireWriteAndReadTime() {
        cache = new LoadingCaheImpl<>(SIZE,
                                      (name)->new Person(name),
                                      TEST_EXPIRE_AFTER_WRITE_TIME,
                                      TEST_EXPIRE_AFTER_READ_TIME);
        assertNotNull("null cache", cache);
    }

    /**
     * Test method for {@link com.nuevatel.common.cache.LoadingCaheImpl#get(java.lang.Object)}.
     * @throws InterruptedException 
     */
    @Test
    public void get() throws InterruptedException {
        Person p1 = cache.get("leyla");
        p1.setAge(23);
        assertNotNull("null cache", p1);
        Person p2 = cache.get("leyla");
        assertNotNull("null cache", p2);
        assertEquals("not same name", "leyla", p2.getName());
        assertEquals("not same age", 23, p2.getAge());
        assertEquals("not same object", p1, p2);
        // test expire
        // read before after read
        Thread.sleep(40L);
        Person p3 = cache.get("leyla");
        assertNotNull("null object", p3);
        assertEquals("not same name", "leyla", p3.getName());
        assertEquals("not same age", 23, p3.getAge());
        assertEquals("not same object", p1, p3);
        Thread.sleep(60L);
        Person p4 = cache.get("leyla");
        assertNotNull("not null object", p4);
        assertEquals("not same name", "leyla", p4.getName());
        assertNotEquals("not same age", 23, p4.getAge());
        assertNotEquals("not same object", p1, p4);
    }

    /**
     * Test method for {@link com.nuevatel.common.cache.LoadingCaheImpl#get(java.lang.Object, java.lang.Long, java.lang.Long)}.
     * @throws InterruptedException 
     */
    @Test
    public void getWithExpireAfterWriteAndRead() throws InterruptedException {
        Person p1 = cache.get("leyla", 200L, 100L);
        p1.setAge(23);
        assertNotNull("null cache", p1);
        Person p2 = cache.get("leyla");
        assertNotNull("null cache", p2);
        assertEquals("not same name", "leyla", p2.getName());
        assertEquals("not same age", 23, p2.getAge());
        assertEquals("not same object", p1, p2);
        // test expire
        // read before after read
        Thread.sleep(90L);
        Person p3 = cache.get("leyla");
        assertNotNull("null object", p3);
        assertEquals("not same name", "leyla", p3.getName());
        assertEquals("not same age", 23, p3.getAge());
        assertEquals("not same object", p1, p3);
        Thread.sleep(220L);
        Person p4 = cache.get("leyla");
        assertNotNull("not null object", p4);
        assertEquals("not same name", "leyla", p4.getName());
        assertNotEquals("not same age", 23, p4.getAge());
        assertNotEquals("not same object", p1, p4);
    }

    /**
     * Test method for {@link com.nuevatel.common.cache.LoadingCaheImpl#getUnchecked(java.lang.Object)}.
     * @throws InterruptedException 
     */
    @Test
    public void getUnchecked() throws InterruptedException {
        Person p1 = cache.get("leyla");
        p1.setAge(23);
        assertNotNull("null person", p1);
        assertNotNull("null person", cache.getUnchecked("leyla"));
        Thread.sleep(110L);
        Person p2 = cache.getUnchecked("leyla");
        assertNull("not expired object", p2);
    }

    /**
     * Test method for {@link com.nuevatel.common.cache.LoadingCaheImpl#put(java.lang.Object, java.lang.Object)}.
     * @throws InterruptedException 
     */
    @Test
    public void put() throws InterruptedException {
        Person p1 = new Person("leyla");
        cache.put("leyla", p1);
        p1.setAge(23);
        assertNotNull("null cache", p1);
        Person p2 = cache.get("leyla");
        assertNotNull("null cache", p2);
        assertEquals("not same name", "leyla", p2.getName());
        assertEquals("not same age", 23, p2.getAge());
        assertEquals("not same object", p1, p2);
        // test expire
        // read before after read
        Thread.sleep(40L);
        Person p3 = cache.get("leyla");
        assertNotNull("null object", p3);
        assertEquals("not same name", "leyla", p3.getName());
        assertEquals("not same age", 23, p3.getAge());
        assertEquals("not same object", p1, p3);
        Thread.sleep(70L);
        Person p4 = cache.get("leyla");
        assertNotNull("not null object", p4);
        assertEquals("not same name", "leyla", p4.getName());
        assertNotEquals("not same age", 23, p4.getAge());
        assertNotEquals("not same object", p1, p4);
    }

    /**
     * Test method for {@link com.nuevatel.common.cache.LoadingCaheImpl#put(java.lang.Object, java.lang.Object, java.lang.Long, java.lang.Long)}.
     * @throws InterruptedException 
     */
    @Test
    public void putWithExpireWriteAndRead() throws InterruptedException {
        Person p1 = new Person("leyla");
        cache.put("leyla", p1, 200L, 100L);
        p1.setAge(23);
        assertNotNull("null cache", p1);
        Person p2 = cache.get("leyla");
        assertNotNull("null cache", p2);
        assertEquals("not same name", "leyla", p2.getName());
        assertEquals("not same age", 23, p2.getAge());
        assertEquals("not same object", p1, p2);
        // test expire
        // read before after read
        Thread.sleep(90L);
        Person p3 = cache.get("leyla");
        assertNotNull("null object", p3);
        assertEquals("not same name", "leyla", p3.getName());
        assertEquals("not same age", 23, p3.getAge());
        assertEquals("not same object", p1, p3);
        Thread.sleep(220L);
        Person p4 = cache.get("leyla");
        assertNotNull("not null object", p4);
        assertEquals("not same name", "leyla", p4.getName());
        assertNotEquals("not same age", 23, p4.getAge());
        assertNotEquals("not same object", p1, p4);
    }

    /**
     * Test method for {@link com.nuevatel.common.cache.LoadingCaheImpl#contains(java.lang.Object)}.
     */
    @Test
    public void contains() {
        Person p1 = cache.get("leyla");
        assertNotNull("cached object null", p1);
        assertTrue("no cached object", cache.contains("leyla"));
    }

    /**
     * Test method for {@link com.nuevatel.common.cache.LoadingCaheImpl#invalidate(java.lang.Object)}.
     */
    @Test
    public void invalidate() {
        Person p1 = cache.get("leyla");
        assertNotNull("cached object null", p1);
        cache.invalidate("leyla");
        assertNull("no invalidate cached object", cache.getUnchecked("leyla"));
    }

    /**
     * Test method for {@link com.nuevatel.common.cache.LoadingCaheImpl#invalidateAll()}.
     */
    @Test
    public void invalidateAll() {
        Person p1 = cache.get("leyla");
        assertNotNull("cached object null", p1);
        Person p2 = cache.get("daniela");
        assertNotNull("cached object null", p2);
        Person p3 = cache.get("gabriela");
        assertNotNull("cached object null", p3);
        cache.invalidateAll();
        assertNull("no invalidate cached object", cache.getUnchecked("leyla"));
        assertNull("no invalidate cached object", cache.getUnchecked("daniela"));
        assertNull("no invalidate cached object", cache.getUnchecked("gabriela"));
    }
    
    /**
     * Test class for cache loading
     *
     */
    private static final class Person {
        
        private String name;
        
        private int age;
        
        public Person(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
        public int getAge() {
            return age;
        }
        
        public void setAge(int age) {
            this.age = age;
        }
    }
}
