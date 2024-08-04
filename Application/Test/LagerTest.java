package Application.Test;

import Application.Model.Fad;
import Application.Model.Lager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class LagerTest {
    private Lager lager;

    @BeforeEach
    void setUp()
    {
        this.lager = new Lager("testLager");
    }

    @Test
    void TC1_getLagerNavn() {
        //Arrange
        //er i setup

        //Act
        String navn = lager.getLagerNavn();

        // Assert
        assertEquals(navn, "testLager");
    }

    @Test
    void TC2_setLagerNavn() {
        //Arrange


        //Act
        lager.setLagerNavn("testLager2");

        // Assert
        assertEquals(lager.getLagerNavn(), "testLager2");
    }

    @Test
    void TC3_addFadTilLager() {
        // Arrange
        Fad fad1 = new Fad("001", "bb", "nn", 0, 100);

        // Act
        lager.addFad(fad1);

        // Assert
        assertEquals(1, lager.getFade().size()); // Kontroller om lageret har ét fad efter tilføjelse.
        assertEquals(fad1, lager.getFade().get(0)); // Kontroller om det tilføjede fad er det forventede fad.

    }
    @Test
    void TC4_DobbeltRettetAssociering_AddFad() {
        // Arrange
        Fad fad1 = new Fad("001", "bb", "nn", 0, 100);

        // Act && Assert
        assertEquals(null, fad1.getLager()); //Tester at der ikke er et lager koblet på fadet.
        lager.addFad(fad1);
        assertEquals(fad1, lager.getFade().get(0)); // Kontroller om det tilføjede fad er det forventede fad.
        assertEquals(fad1.getLager(),lager); //tester at fad1 også har fået koblet lager på
    }


    @Test
    void TC5_removeFadFraLager() {
        // Arrange
        Fad fad1 = new Fad("001", "bb", "nn", 0, 100);
        Fad fad2 = new Fad("002", "bb", "nn", 0, 100);
        Fad fad3 = new Fad("003", "bb", "nn", 0, 100);

        lager.addFad(fad1);
        lager.addFad(fad2);
        lager.addFad(fad3);

        // Act
        lager.removeFad(fad2);

        // Assert
        assertEquals(2, lager.getFade().size()); // Lageret skal have to fade efter fjernelse.
        assertFalse(lager.getFade().contains(fad2)); // Fad2 skal ikke være til stede i lageret efter fjernelse.
    }
    @Test
    void TC6_DobbeltRettetAssociering_RemoveFad() {
        // Arrange
        Fad fad1 = new Fad("001", "bb", "nn", 0, 100);
        Fad fad2 = new Fad("002", "bb", "nn", 0, 100);
        Fad fad3 = new Fad("003", "bb", "nn", 0, 100);

        lager.addFad(fad1);
        lager.addFad(fad2);
        lager.addFad(fad3);

        // Act && Assert
        assertEquals(lager, fad1.getLager()); //Tester at der ikke er et lager koblet på fadet.
        lager.removeFad(fad1);
        assertEquals(null, fad1.getLager()); //tester at fad1 også har fået koblet lager på
    }
}