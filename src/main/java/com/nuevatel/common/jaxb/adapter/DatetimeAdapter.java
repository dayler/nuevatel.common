package com.nuevatel.common.jaxb.adapter;

import com.nuevatel.common.util.date.DateFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

/**
 * Generic date format adapter.
 *
 * @author asalazar
 */
public class DatetimeAdapter extends XmlAdapter<String, Date> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Date unmarshal(String value) throws Exception {
        return DateFormatter.ALL.parse(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String marshal(Date value) throws Exception {
        return DateFormatter.LONG.format(value);
    }

}
