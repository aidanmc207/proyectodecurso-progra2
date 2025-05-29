package cr.ac.ucr.paraiso.progra2.xml;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class EquipoFutbolXmlDataTest {
    private final String ruta = "C:\\Users\\Lab01\\Desktop\\progra2\\proyectodecurso-progra2\\src\\main\\java\\cr\\ac\\ucr\\paraiso\\progra2\\xml\\equipos.xml";
    private EquipoFutbolXmlData equipoFutbolXmlData;

    @Test
    void abrirDocumento() {
        try {
            equipoFutbolXmlData = EquipoFutbolXmlData.abrirDocumento(ruta);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void ingresarEquipoFutbolTest(){

        EquipoFutbol saprissa, liga;
        saprissa= new EquipoFutbol("SAP", "Deportivo Saprissa", 21);
        liga= new EquipoFutbol("LDA", "Liga Deportiva Alajuelense", 21);

        try {
            equipoFutbolXmlData = EquipoFutbolXmlData.abrirDocumento(ruta);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Assertions.assertDoesNotThrow(() -> {
            this.equipoFutbolXmlData.insertarEquipo(saprissa);
            this.equipoFutbolXmlData.insertarEquipo(liga);
        });
        ArrayList<EquipoFutbol> equipos = equipoFutbolXmlData.getEquipos();
        Assertions.assertEquals(2, equipos.size());
        Assertions.assertEquals("Deportivo Saprissa", equipos.get(0).getNombre());
    }
    @AfterEach
    void fin(){
        File file = new File(ruta);
        boolean borrado = file.delete();

        System.out.println(borrado);
    }
}