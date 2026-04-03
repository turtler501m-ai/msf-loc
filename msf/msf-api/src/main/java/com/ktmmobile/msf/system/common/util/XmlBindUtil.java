package com.ktmmobile.msf.system.common.util;

import com.ktmmobile.msf.system.common.dto.XmlWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XmlBindUtil {

    @SuppressWarnings("unchecked")
    public static <T> T bindFromXml(Class<T> clazz, File xmlFile) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        T xmlObject;
        try(InputStream is = new FileInputStream(xmlFile)) {
            xmlObject = (T) unmarshaller.unmarshal(is);
        }
        return xmlObject;
    }

    public static <T> T bindFromXml(Class<T> clazz, String xmlPath) throws JAXBException, IOException {
        return bindFromXml(clazz, new File(xmlPath));
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> bindListFromXml(Class<T> clazz, File xmlFile) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlWrapper.class, clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        XmlWrapper<T> xmlWrapperObject;
        try(InputStream is = new FileInputStream(xmlFile)) {
            xmlWrapperObject = (XmlWrapper<T>) unmarshaller.unmarshal(is);
        }
        return xmlWrapperObject.getItems();
    }

    public static <T> List<T> bindListFromXml(Class<T> clazz, String xmlPath) throws JAXBException, IOException {
        return bindListFromXml(clazz, new File(xmlPath));
    }
}
