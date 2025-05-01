package DOMGenerator;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import java.io.*;
import java.util.*;

public class generatoreDOM {
    private Document document;
    private Element root;
    private List<Dipendente> personale;

    public generatoreDOM() {
        try {
            // Creazione del documento e dell'elemento radice
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
            root = document.createElement("Personale");
            document.appendChild(root);

            // Inizializzazione della lista del personale
            personale = new ArrayList<>();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    // Metodo per creare un elemento dipendente
    public void creaElemento(Dipendente d) {
        Element dipendente = document.createElement("dipendente");

        Element cognome = document.createElement("cognome");
        cognome.appendChild(document.createTextNode(d.getCognome()));
        dipendente.appendChild(cognome);

        Element nome = document.createElement("nome");
        nome.appendChild(document.createTextNode(d.getNome()));
        dipendente.appendChild(nome);

        Element dipartimento = document.createElement("dipartimento");
        dipartimento.appendChild(document.createTextNode(d.getDipartimento()));
        dipendente.appendChild(dipartimento);

        Element stipendio = document.createElement("stipendio");
        stipendio.appendChild(document.createTextNode(String.valueOf(d.getStipendio())));
        dipendente.appendChild(stipendio);

        root.appendChild(dipendente);
    }

    // Metodo per esportare il documento XML
    public void esporta(String xmlFile) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFile));

            transformer.transform(domSource, streamResult);

            System.out.println("File XML creato con successo!");

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        generatoreDOM generatore = new generatoreDOM();

        // Aggiungi nuovi dipendenti
        generatore.creaElemento(new Dipendente("Rossi", "Mario", "Magazzino", 65000));
        generatore.creaElemento(new Dipendente("Bianchi", "Luca", "Vendite", 55000));
        generatore.creaElemento(new Dipendente("Verdi", "Anna", "Amministrazione", 60000));

        // Esporta il documento XML
        generatore.esporta("DOMGenerator\\personale.xml");
    }
}

