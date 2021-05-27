package controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OldalControllerTest {

    OldalController undertest;

    @BeforeEach
    void setUp() { undertest = new OldalController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void osszead() { assertEquals(4,undertest.osszead("4"));
    }
}