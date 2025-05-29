package cr.ac.ucr.paraiso.progra2.xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;

public class EquipoFutbolXmlData {
    private Document document;
    private Element raiz;
    private String rutaDocumento;

    private EquipoFutbolXmlData(String rutaDocumento, String nombreRaiz) throws FileNotFoundException, IOException {
        this.rutaDocumento = rutaDocumento;
        this.raiz = new Element(nombreRaiz);
        this.document = new Document(raiz);
        guardar();
    }
    private EquipoFutbolXmlData(String rutaDocumento) throws JDOMException, IOException{
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setIgnoringElementContentWhitespace(true);
        //parseo
        this.document = saxBuilder.build(rutaDocumento);
        this.raiz = document.getRootElement();
        this.rutaDocumento = rutaDocumento;
    }

    public static EquipoFutbolXmlData abrirDocumento(String rutaDocumento) throws JDOMException, IOException{
        if (new File(rutaDocumento).exists()){
            return new EquipoFutbolXmlData(rutaDocumento);
        } else return new EquipoFutbolXmlData(rutaDocumento, "equipos");
    }

    public void insertarEquipo(EquipoFutbol equipoFutbol) throws IOException{
        Element eEquipo = new Element("equipoFutbol");
        eEquipo.setAttribute("codEquipo", equipoFutbol.getCodEquipo());
        Element eNombre = new Element("nombre");
        eNombre.addContent(equipoFutbol.getNombre());

        Element eCantidadJugadoresInscritos = new Element("cantidadJugadoresInscritos");
        eCantidadJugadoresInscritos.addContent(
                String.valueOf(equipoFutbol.getCantidadJugadoresInscritos())
        );

        eEquipo.addContent(eNombre);
        eEquipo.addContent(eCantidadJugadoresInscritos);
        raiz.addContent(eEquipo);
        guardar();
    }

    public void guardar() throws FileNotFoundException, IOException {
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.output(this.document, new PrintWriter(this.rutaDocumento));

        xmlOutputter.output(this.document, System.out);
    }

    public ArrayList<EquipoFutbol> getEquipos() {
        List<Element> eListaEquipos = raiz.getChildren();
        ArrayList<EquipoFutbol> equipos = new ArrayList<>();
        for (Element e:eListaEquipos){
            EquipoFutbol equipoActual = new EquipoFutbol();
            equipoActual.setCodEquipo(e.getAttributeValue("codEquipo"));
            equipoActual.setNombre(e.getChildText("nombre"));
            equipoActual.setCantidadJugadoresInscritos(
                    Integer.parseInt(e.getChildText("cantidadJugadoresInscritos")));
            equipos.add(equipoActual);
        }
        return equipos;
    }
}
