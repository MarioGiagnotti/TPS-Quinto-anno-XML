package DOMParser;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class DOMParser {

    //definisco le costanti ANSI per i colori
    public static final String ANSI_RESET = "\u001B[0m"; 
    public static final String ANSI_RED = "\u001B[31m"; 
    public static final String ANSI_GREEN = "\u001B[32m"; 
    public static final String ANSI_YELLOW = "\u001B[33m"; 
    public static final String ANSI_BLUE = "\u001B[34m"; 
    public static final String ANSI_PURPLE = "\u001B[35m"; 
    public static final String ANSI_CYAN = "\u001B[36m"; 
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        try {
            //Definisco il nome e il percorso del file XMLx
            String path = "";           
            String nome = "personaggi.xml";

            // Carico il file XML
            File xmlFile = new File(path + nome);

            /* Creo un'istanza di DocumentBuilderFactory.Questa è una classe fornita dalla API JAVA 
            per elaborare file XML. Oltre a servire ad istanziare il parser effettivo DocumentBuilder 
            server costruisce un albero di oggetti Document, si può abilitare o no la validazione,
            il namespace e altre funzionalità*/
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Creo un'istanza di DocumentBuilder, il parser vero e proprio
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parse (analizzo) il file XML e ottengo un oggetto Document
            Document doc = dBuilder.parse(xmlFile);

            /* Normalizza il documento XML. Serve a eliminare eventuali nodi vuoti o ridondanti, 
            e garantisce che l'albero del documento sia in una forma coerente e navigabile */
            doc.getDocumentElement().normalize();

            //Ottengo la root del documento
            Node root = doc.getDocumentElement();

            //chiamo il metodo per la stampa passando la root come parametro
            printNode(root, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo combinato per stampare tutte le informazioni relative ai nodi con indentazione
    private static void printNode(Node node, int indent) {
        String elencoAttributi;
        short nodeType = node.getNodeType();

        //Se il nodo è di tipo Element ricavo le informazioni e le stampo
        if (nodeType == Node.ELEMENT_NODE) {
            // Indentazione per una migliore leggibilità
            for (int i = 0; i < indent; i++) {
                System.out.print("    ");
            }

            // Stampa il nome del nodo
            System.out.print(ANSI_BLUE + "Nome nodo: "+ ANSI_RESET + node.getNodeName());

            // Stampa il valore del nodo se presente
            String valoreNodo = analizza(node);
            if (!valoreNodo.trim().equalsIgnoreCase("")) {
                System.out.print(ANSI_CYAN + " Valore: "+ ANSI_RESET + valoreNodo);
            }

            // Stampa gli attributi se presenti
            NamedNodeMap attributi = node.getAttributes();
            elencoAttributi = estraiAttributi(attributi);
            if (!elencoAttributi.equals("assenti")) {
                System.out.print(ANSI_YELLOW + "\tAttributi: " + ANSI_RESET + elencoAttributi);
            }
            System.out.print("\n");
        }
        
        //Chiamata ricorsiva per estrarre informazioni da tutti i nodi a meno che non sia un elemento terminale
        int nrFigli = node.getChildNodes().getLength();
        if (node.hasChildNodes()) {
            NodeList figli = node.getChildNodes();
            for (int i = 0; i < nrFigli; i++) {
                printNode(figli.item(i), indent + 1);
            }
        }
    }

    private static String analizza(Node nodoCorrente) {
        String testo = "";
        if (nodoCorrente.hasChildNodes()) {
            Node testoFiglio = nodoCorrente.getChildNodes().item(0);
            testo = testoFiglio.getNodeValue();
        }
        return testo;
    }

    private static String estraiAttributi(NamedNodeMap nnm) {
        String elenco = "";
        if (nnm != null && nnm.getLength() > 0) {
            for (int i = 0; i < nnm.getLength(); i++) {
                elenco += ANSI_RED;
                elenco += nnm.item(i).getNodeName();
                elenco += ":";
                elenco += ANSI_RESET;
                elenco += nnm.item(i).getNodeValue();
                elenco += ";";
            }
            return elenco;
        } else {
            return "assenti";
        }
    }
}
