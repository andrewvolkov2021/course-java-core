package com.rakovets.course.java.core.practice.xml;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HibernateWriter {
    private final XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    private final XMLEvent lfEvent = eventFactory.createDTD("\n");
    private final XMLEvent tab = eventFactory.createDTD("\t");

    public void createHibernateXML(String configFilePath, HibernateConfig hibernate) {

        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        try (OutputStream outputStream = new FileOutputStream(configFilePath)) {
            XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(outputStream);
            eventWriter.add(eventFactory.createStartDocument());
            eventWriter.add(lfEvent);
            eventWriter.add(eventFactory.createDTD("<!DOCTYPE hibernate-configuration PUBLIC\n" +
                    "\t\t\"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
                    "\t\t\"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n"));
            eventWriter.add(lfEvent);
            eventWriter.add(eventFactory.createStartElement("", "", "hibernate-configuration"));
            eventWriter.add(lfEvent);
            eventWriter.add(tab);
            eventWriter.add(eventFactory.createStartElement("", "", "session-factory"));
            eventWriter.add(lfEvent);
            Map<String, String> property = hibernate.getMapProperty();
            Map<String, String> mapping = hibernate.getMapMapping();
            for (Map.Entry<String, String> set : property.entrySet()) {
                createPropertyNode(eventWriter, set.getKey(), set.getValue());
            }
            eventWriter.add(lfEvent);
            for (Map.Entry<String, String> set : mapping.entrySet()) {
                createMappingNode(eventWriter, set.getKey(), set.getValue());
            }
            eventWriter.add(tab);
            eventWriter.add(eventFactory.createEndElement("", "", "session-factory"));
            eventWriter.add(lfEvent);
            eventWriter.add(eventFactory.createEndElement("", "", "hibernate-configuration"));
            eventWriter.add(lfEvent);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void createMappingNode(XMLEventWriter eventWriter, String value, String valueAttr) throws XMLStreamException {
        XMLEvent tab = eventFactory.createDTD("\t\t");
        StartElement sElement = eventFactory.createStartElement("", "", "mapping");
        Attribute attr = eventFactory.createAttribute(valueAttr, value);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        eventWriter.add(attr);
        EndElement eElement = eventFactory.createEndElement("", "", "mapping");
        eventWriter.add(eElement);
        eventWriter.add(lfEvent);
    }

    private void createPropertyNode(XMLEventWriter eventWriter, String value, String valueAttr) throws XMLStreamException {
        XMLEvent tab = eventFactory.createDTD("\t\t");
        StartElement sElement = eventFactory.createStartElement("", "", "property");
        Attribute attr = eventFactory.createAttribute("name", value);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        eventWriter.add(attr);
        Characters characters = eventFactory.createCharacters(valueAttr);
        eventWriter.add(characters);
        EndElement eElement = eventFactory.createEndElement("", "", "property");
        eventWriter.add(eElement);
        eventWriter.add(lfEvent);
    }
}
