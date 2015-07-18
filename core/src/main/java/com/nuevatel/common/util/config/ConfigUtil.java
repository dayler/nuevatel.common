package com.nuevatel.common.util.config;

import com.nuevatel.common.util.xml.XmlHash;
import com.nuevatel.common.util.xml.XmlHashImpl;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Get config xml file common operations.
 *
 * @author asalazar
 */
public class ConfigUtil {

    /**
     * @param filePath Relative root path, used to locate the config xml file.
     * @return XML HASH with config xml configuration.
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    public static XmlHash getConfigXmlHash(String filePath) throws SAXException,
            ParserConfigurationException, IOException {
        // Load config.xml. Get global configuration.
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document configDoc = builder.parse(filePath);
        XmlHash confXmlHash = new XmlHashImpl(configDoc);

        return confXmlHash;
    }
}
