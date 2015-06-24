package com.nuevatel.common.util;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nuevatel.common.util.xml.XMLSerializer;

public class PairValueItemsContainerTest {

    private PairValueItemsContainer container;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // No op
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // No op
    }

    @Before
    public void setUp() throws Exception {
        List<PairValueItem>items = new ArrayList<PairValueItem>();
        items.add(new PairValueItem("mane1", "value1"));
        items.add(new PairValueItem("mane1", "value1"));
        items.add(new PairValueItem("mane1", "value1"));
        container = new PairValueItemsContainer(items);
    }

    @After
    public void tearDown() throws Exception {
        container = null;
    }

    @Test
    public void toXml() throws JAXBException {
        String body = new String(XMLSerializer.toXml(PairValueItemsContainer.class, container));
        assertNotNull("Could not retrieve the serialized obj..", body);
        System.out.println(body);
    }

    @Test
    public void fromXml() throws JAXBException {
        InputStream input = getClass().getResourceAsStream("/xml/pairValueItemsContainer.xml");
        assertNotNull("Could not retrieve the resource", input);
        PairValueItemsContainer cont = XMLSerializer.fromXML(PairValueItemsContainer.class, input);
        assertNotNull("Could not deserialize the obj", cont);
    }
}
