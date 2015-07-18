/**
 * 
 */
package com.nuevatel.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author asalazar
 *
 */
@XmlRootElement(name="valueItems")
public class PairValueItemsContainer {

    @XmlElements({@XmlElement(name="item", type=PairValueItem.class)})
    private List<PairValueItem> items = new ArrayList<PairValueItem>();

    public PairValueItemsContainer() {
        // No op
    }

    public PairValueItemsContainer(List<PairValueItem> items) {
        this.items = Util.ifNull(items, this.items);
    }

    public List<PairValueItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
