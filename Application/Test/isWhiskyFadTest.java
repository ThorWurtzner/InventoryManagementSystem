package Application.Test;

import Application.Controller.Controller;
import Application.Model.DestillatMængde;
import Application.Model.DestillatPaaFad;
import Application.Model.Destillering;
import Application.Model.Fad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class isWhiskyFadTest {
    private Fad fad;
    private DestillatPaaFad destillatPaaFad;
    private LocalDate treAarSiden;

    @BeforeEach
    void setUp()
    {
        fad = Controller.opretFad("001", "el Bødker", "sherry", 1, 200);
        destillatPaaFad = Controller.opretDestillatPaaFad(LocalDate.of(2019, 11, 19), fad);
        treAarSiden = (LocalDate.of(2020,12,04));

    }

    @Test
    void TC1_valid_VilkårligVærdiOver3År() {
        //Arrange
       DestillatMængde dm = destillatPaaFad.createDestillatMængde(20,null,LocalDate.of(2012,12,04));

        //Act
        boolean iswhisky = Controller.isWhiskyFad(fad, treAarSiden);

        // Assert
        assertEquals(true, iswhisky);
    }
    @Test
    void TC2_invalid_Grænseværdi_1DagUnder3År() {
        //Arrange
        DestillatMængde dm = destillatPaaFad.createDestillatMængde(20,null,LocalDate.of(2020,12,05));

        //Act
        boolean iswhisky = Controller.isWhiskyFad(fad, treAarSiden);

        // Assert
        assertEquals(false, iswhisky);
    }

    @Test
    void TC3_valid_Grænseværdi_PåDagenFor3År() {
        //Arrange
        DestillatMængde dm = destillatPaaFad.createDestillatMængde(20,null,LocalDate.of(2020,12,04));

        //Act
        boolean iswhisky = Controller.isWhiskyFad(fad, treAarSiden);

        // Assert
        assertEquals(true, iswhisky);
    }

    @Test
    void TC4_invalid_FlereDestillater_SenesteDestillatUnder3År() {
        //Arrange
        DestillatMængde dm = destillatPaaFad.createDestillatMængde(20,null,LocalDate.of(2019,9,12));
        DestillatMængde dm2 = destillatPaaFad.createDestillatMængde(20,null,LocalDate.of(2022,12,9));

        //Act
        boolean iswhisky = Controller.isWhiskyFad(fad, treAarSiden);

        // Assert
        assertEquals(false, iswhisky);
    }

}