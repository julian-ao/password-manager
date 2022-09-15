package com.example.gruppeprosjekt1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloApplicationTest {

    @Test
    public void testApp() {
        Assertions.assertEquals(2, 2, "De er ikke lik hverandre");
    }
}