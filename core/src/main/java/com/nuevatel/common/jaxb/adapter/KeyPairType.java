package com.nuevatel.common.jaxb.adapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author asalazar
 */
@XmlRootElement(name = "keyPair")
@XmlAccessorType(XmlAccessType.NONE)
public class KeyPairType<K, V> {

    @XmlElement(name = "key")
    private K key;

    @XmlElement(name = "value")
    private V value;

    public KeyPairType() {
        // No op. Compatibility with JAXB
    }

    public KeyPairType(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }


}
