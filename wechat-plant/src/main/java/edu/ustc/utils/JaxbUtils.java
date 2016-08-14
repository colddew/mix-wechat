package edu.ustc.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class JaxbUtils {

    public static String convertObjectToXml(Object object) throws Exception {
        return convertObjectToXml(object, "UTF-8");
    }

    public static String convertObjectToXml(Object object, String encoding) throws Exception {

        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("convert object to xml error");
        }
    }

    public static <T> T convertXmlToObject(String xml, Class<T> clazz) throws Exception {

        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("convert xml to object error");
        }
    }
} 