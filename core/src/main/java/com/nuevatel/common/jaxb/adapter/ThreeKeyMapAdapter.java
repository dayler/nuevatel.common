package com.nuevatel.common.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author asalazar
 */
public class ThreeKeyMapAdapter<K1, K2, V> extends XmlAdapter<ThreeKeyMapType<K1, K2, V>, Map<K1, Map<K2, V>>> {

    @Override
    public Map<K1, Map<K2, V>> unmarshal(ThreeKeyMapType<K1, K2, V> unmarshalValue) throws Exception {
        List<KeyPairType<K1, KeyPairType<K2, V>>> elements = unmarshalValue.getElements();
        Map<K1, Map<K2, V>> resultMap = new HashMap<K1, Map<K2, V>>();

        for (KeyPairType<K1, KeyPairType<K2, V>> element : elements) {

            Map<K2, V> elementMap;
            K1 elementKey = element.getKey();

            if (resultMap.containsKey(elementKey)) {
                elementMap = resultMap.get(element.getKey());
            } else {
                elementMap = new HashMap<K2, V>();
                resultMap.put(element.getKey(), elementMap);
            }

            KeyPairType<K2, V> elementValue = element.getValue();
            elementMap.put(elementValue.getKey(), element.getValue().getValue());
        }

        return resultMap;
    }

    @Override
    public ThreeKeyMapType<K1, K2, V> marshal(Map<K1, Map<K2, V>> valueMap) throws Exception {

        List<KeyPairType<K1, KeyPairType<K2, V>>> elements = new ArrayList<KeyPairType<K1, KeyPairType<K2, V>>>();

        for (K1 keyElement : valueMap.keySet()) {
            Map<K2, V> map = valueMap.get(keyElement);

            for (K2 secondKey : map.keySet()) {
                V elementValue = map.get(secondKey);

                KeyPairType<K2, V> secondValue = new KeyPairType<K2, V>(secondKey, elementValue);
                KeyPairType<K1, KeyPairType<K2, V>> value = new KeyPairType<K1, KeyPairType<K2, V>>(keyElement, secondValue);
                elements.add(value);
            }
        }

        ThreeKeyMapType<K1, K2, V> result = new ThreeKeyMapType<K1, K2, V>(elements);

        return result;
    }

}
