package com.swjtu.welcome.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GISParserTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void parse() throws FileNotFoundException {
        GISParser gisParser = new GISParser();
        String filename = "/home/onerain233/welcome/testfile/gis.txt";
        List<List<List<Float>>> result = gisParser.parse(filename);
        for (List<List<Float>> line : result) {
            System.out.println(line);
        }
        System.out.println(result);

    }
}