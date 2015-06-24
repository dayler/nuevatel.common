/**
 * 
 */
package com.nuevatel.common.util.xml;

import static com.nuevatel.common.util.Util.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author asalazar
 *
 */
public final class XMLSerializer {

    private XMLSerializer() {
        // No op
    }

    public static <T> byte[] toXml(Class<T> clazz, T obj) throws JAXBException {
        if (obj == null) {
            return null;
        }

        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ByteArrayOutputStream os = new ByteArrayOutputStream(); 
        marshaller.marshal(obj, os);

        return os.toByteArray();
    }

    public static <T> T fromXML(Class<T>clazz, InputStream input) throws JAXBException {
        if (input == null) {
            return null;
        }

        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return castAs(clazz, unmarshaller.unmarshal(input));
    }
}
