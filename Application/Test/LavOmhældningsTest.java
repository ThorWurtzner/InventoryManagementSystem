package Application.Test;

import Application.Controller.Controller;
import Application.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LavOmhældningsTest {
    private Destillering destillering;
    private Fad tomtFad;
    private Lager lager;
    private LocalDate omhældningsDato;
    private Fad fyldtFad1;
    private Fad fyldtFad2;
    private DestillatPaaFad destillatPaaFad1;
    private DestillatPaaFad destillatPaaFad2;
    private ArrayList<Fad> fadList = new ArrayList<>();


    @BeforeEach
    void setUp()
    {
        destillering = new Destillering("nw007", LocalDate.of(2023,12,05));
        tomtFad = new Fad("1", "MBK", "sherry", 1, 100);
        lager = new Lager("Container");
        omhældningsDato = LocalDate.of(2023,12,06);
        fyldtFad1 = new Fad("2", "MBK", "sherry", 1, 100);
        fyldtFad2 = new Fad("3", "MBK", "sherry", 1, 100);

        destillatPaaFad1 = Controller.opretDestillatPaaFad(LocalDate.of(2019, 11, 19), fyldtFad1);
        destillatPaaFad1.createDestillatMængde(30, destillering, LocalDate.of(2019, 11, 19));

        destillatPaaFad2 = Controller.opretDestillatPaaFad(LocalDate.of(2019, 11, 19), fyldtFad2);
        destillatPaaFad2.createDestillatMængde(30, destillering, LocalDate.of(2019, 11, 19));
        destillatPaaFad2.createDestillatMængde(40, destillering, LocalDate.of(2019, 11, 19));

        ArrayList<Fad> fadList;

    }
    @Test
    void TC1_test_oprettelseTilknytningAfGammeltDestillatPaaFad(){
        //Arrange
        fadList.add(fyldtFad1);

        //Act
        DestillatPaaFad nytDestillatPaaFad = Controller.lavOmhældning(tomtFad,fadList,lager,omhældningsDato);

        // Assert
        assertEquals(1, nytDestillatPaaFad.getTidligereDestillater().size());
        assertEquals(destillatPaaFad1, nytDestillatPaaFad.getTidligereDestillater().get(0));

    }
    @Test
    void TC2_test_oprettelseTilknytningAfGamleDestillatPaaFadMed2(){
        //Arrange
        fadList.add(fyldtFad1);
        fadList.add(fyldtFad2);

        //Act
        DestillatPaaFad nytDestillatPaaFad = Controller.lavOmhældning(tomtFad,fadList,lager,omhældningsDato);

        // Assert
        assertEquals(2, nytDestillatPaaFad.getTidligereDestillater().size());
        assertEquals(destillatPaaFad1, nytDestillatPaaFad.getTidligereDestillater().get(0));
        assertEquals(destillatPaaFad2, nytDestillatPaaFad.getTidligereDestillater().get(1));

    }
    @Test
    void TC3_test_overflytningAfDestillatMængder(){
        //Arrange
        fadList.add(fyldtFad1);
        fadList.add(fyldtFad2);

        //Act
        DestillatPaaFad nytDestillatPaaFad = Controller.lavOmhældning(tomtFad,fadList,lager,omhældningsDato);

        // Assert
        assertEquals(3, nytDestillatPaaFad.getDestillatMængder().size());
    }

    @Test
    void TC4_test_fadMisterAssocieringTilDestillatPaaFad(){
        //Arrange
        fadList.add(fyldtFad1);

        //Act
        DestillatPaaFad nytDestillatPaaFad = Controller.lavOmhældning(tomtFad,fadList,lager,omhældningsDato);

        // Assert
        assertEquals(null, fyldtFad1.getDestillatPaaFad());
    }
    @Test
    void TC5_test_fadMisterAssocieringTilLager(){
        //Arrange
        fadList.add(fyldtFad1);

        //Act
        DestillatPaaFad nytDestillatPaaFad = Controller.lavOmhældning(tomtFad,fadList,lager,omhældningsDato);

        // Assert
        assertEquals(null, fyldtFad1.getLager());
    }
    @Test
    void TC6_test_nytDestillatBliverLagtPåLager(){
        //Arrange
        fadList.add(fyldtFad1);

        //Act
        DestillatPaaFad nytDestillatPaaFad =Controller.lavOmhældning(tomtFad,fadList,lager,omhældningsDato);

        // Assert
        assertEquals(lager, nytDestillatPaaFad.getFad().getLager());
    }

}