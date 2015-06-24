package com.nuevatel.common.jaxb.adapter;

import com.nuevatel.common.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;

/**
 * @author clvelarde
 */
public class MarshalXMLString {

    public static String getXMLString(Class contextClass, Object instanceClass) throws JAXBException {
        String xmlToString = StringUtils.EMPTY;

        if (instanceClass != null) {
            JAXBContext context = JAXBContext.newInstance(contextClass);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ByteArrayOutputStream outByteArray = new ByteArrayOutputStream();
            marshaller.marshal(instanceClass, outByteArray);
            xmlToString = new String(outByteArray.toByteArray());
        }

        return xmlToString;
    }
}
