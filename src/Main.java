import models.PersonajeModel;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static ArrayList<PersonajeModel> personajeList;

    public static void main(String[] args) {

        personajeList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            opcion = menu(scanner);
            switch (opcion) {
                case 1:
                    crearPersonaje(scanner, personajeList);
                    break;
                case 2:
                    muestraPersonajes(personajeList);
                    break;
                case 3:
                    try {
                        escribirXML(scanner, personajeList);
                    } catch (ParserConfigurationException e) {
                        throw new RuntimeException(e);
                    } catch (TransformerException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 4:
                    try {
                        leerXML(scanner, personajeList);
                    } catch (ParserConfigurationException e) {
                        throw new RuntimeException(e);
                    } catch (SAXException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    break;
            }
        } while (opcion != 5);
    }

    private static void leerXML(Scanner scanner, ArrayList<PersonajeModel> personajeList) throws ParserConfigurationException, IOException, SAXException {

        System.out.println("Dime el nombre del archivo");
        String archivo = scanner.nextLine();
        File file = new File(archivo + ".xml");
        if (file.exists()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();
            personajeList.clear();

            NodeList nodos = document.getElementsByTagName("personaje");

            for (int i = 0; i < nodos.getLength(); i++) {
                Node nodo = nodos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element personaje = (Element) nodo;
                    String nombre = personaje.getElementsByTagName("nombre").item(0).getTextContent();
                    String descripcion = personaje.getElementsByTagName("descripcion").item(0).getTextContent();
                    Boolean sobrevive = Boolean.valueOf(personaje.getElementsByTagName("sobrevive").item(0).getTextContent());
                    int id = Integer.parseInt(personaje.getAttribute("id").toString());
                    PersonajeModel personaje1 = new PersonajeModel(id, nombre, descripcion, sobrevive);
                    personajeList.add(personaje1);
                }
            }
        } else {
            System.out.println("No existe ese archivo");
        }
    }

    private static void escribirXML(Scanner scanner, ArrayList<PersonajeModel> personajeList) throws ParserConfigurationException, TransformerException {
        String archivo;
        System.out.println("Dime el nombre del archivo");
        archivo = scanner.nextLine();

        String file = archivo + ".xml";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        Element raiz = doc.createElement("Personajes");
        doc.appendChild(raiz);
        for (PersonajeModel p : personajeList) {
            Element personaje = doc.createElement("personaje");
            raiz.appendChild(personaje);

            Attr id = doc.createAttribute("id");
            id.setValue(String.valueOf(p.getId()));
            personaje.setAttributeNode(id);

            Element nombre = doc.createElement("nombre");
            nombre.setTextContent(p.getNombre());
            personaje.appendChild(nombre);

            Element descripcion = doc.createElement("descripcion");
            descripcion.setTextContent(String.valueOf(p.getDescripcion()));
            personaje.appendChild(descripcion);

            Element sobrevive = doc.createElement("sobrevive");
            sobrevive.setTextContent(String.valueOf(p.isSobrevive()));
            personaje.appendChild(sobrevive);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        DOMSource ds = new DOMSource(doc);

        t.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult sr = new StreamResult(new File(file));
        t.transform(ds, sr);
    }

    private static void muestraPersonajes(ArrayList<PersonajeModel> personajeList) {
        for (PersonajeModel p : personajeList) {
            System.out.println(p);
        }
    }

    private static void crearPersonaje(Scanner scanner, ArrayList<PersonajeModel> peronajeList) {
        int id;
        String nombre;
        String descripcion;
        boolean sobrevive = false;
        String respuesta;

        do {
            System.out.println("Dime el nombre del personaje");
            nombre = scanner.nextLine();
            System.out.println("Dime el ID del personaje");
            id = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Dime una descripción del personaje");
            descripcion = scanner.nextLine();

            System.out.println("¿Sobrevive?");
            respuesta = scanner.nextLine();

            if (respuesta.equalsIgnoreCase("si")) {
                sobrevive = true;
                PersonajeModel personaje = new PersonajeModel(id, nombre, descripcion, sobrevive);
                peronajeList.add(personaje);
            }
            if (respuesta.equalsIgnoreCase("no")) {
                sobrevive = false;
                PersonajeModel personaje = new PersonajeModel(id, nombre, descripcion, sobrevive);
                peronajeList.add(personaje);
            }
            if (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
                System.out.println("Error");
            }

        } while (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no"));


    }

    private static int menu(Scanner scanner) {
        int opcion;
        do {
            System.out.println("1: Crear personaje");
            System.out.println("2: Mostrar personajes");
            System.out.println("3: Cargar personajes");
            System.out.println("4: Descargar personajes");
            System.out.println("5: Salir");

            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException exception) {
                opcion = 0;
                System.out.println("Error");
                scanner.nextLine();
            }
        } while (opcion < 1 || opcion > 5);
        scanner.nextLine();
        return opcion;
    }
}
