package com.nuevatel.common.jaxb.adapter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author asalazar
 */
@XmlRootElement(name = "threeKeyMap")
@XmlAccessorType(XmlAccessType.NONE)
public class ThreeKeyMapType<K1, K2, V> {

    @XmlElementWrapper(name = "elementsList")
    @XmlElements(value = {@XmlElement(name = "keyPair", type = KeyPairType.class)})
    private List<KeyPairType<K1, KeyPairType<K2, V>>> elements = new ArrayList<KeyPairType<K1, KeyPairType<K2, V>>>();

    public ThreeKeyMapType() {
        // No op. For campatibility with JAXB
    }

    public ThreeKeyMapType(List<KeyPairType<K1, KeyPairType<K2, V>>> elements) {
        // No op. For campatibility with JAXB
        this.elements = elements;
    }

    public List<KeyPairType<K1, KeyPairType<K2, V>>> getElements() {
        return elements;
    }

    public void setElements(List<KeyPairType<K1, KeyPairType<K2, V>>> elements) {
        this.elements = elements;
    }
}
