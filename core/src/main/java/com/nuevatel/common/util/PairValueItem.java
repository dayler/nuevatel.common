/**
 * 
 */
package com.nuevatel.common.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author asalazar
 *
 */
@XmlRootElement(name="valueItem")
@XmlAccessorType(XmlAccessType.NONE)
public class PairValueItem {

    @XmlElement(name="name")
    private String name;

    @XmlElement(name="value")
    private String value;

    public PairValueItem() {
        // No op
    }

    public PairValueItem(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
