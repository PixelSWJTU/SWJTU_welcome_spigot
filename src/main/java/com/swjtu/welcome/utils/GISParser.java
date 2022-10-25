package com.swjtu.welcome.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GISParser {
    public List<List<List<Float>>> parse(String filename) throws FileNotFoundException {
        // read gis data from file
        //MultiLineString((402751.71237900480628014 3403667.70615032222121954, 402775.44476918858708814 3403667.44337578164413571, 402775.45482097694184631 3403656.46705779898911715, 402751.54773421789286658 3403656.84487067768350244))
        BufferedReader in = new BufferedReader(new FileReader(filename));
        List<String> data = new ArrayList<>();
        String line;
        try {
            while ((line = in.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<List<Float>>> result = new ArrayList<>();

        for (String s : data) {
            String coordinates = "";
            // regex to match the coordinates
            final String regex = "(.*) \\(\\((.*)\\)\\)";
            // match the coordinates
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                coordinates = matcher.group(2);
            } else {
                return null;
            }
            result.add(parseLine(coordinates));
        }


        return result;
    }

    public List<List<Float>> parseLine(String coordinates) {
        // split by "," or ", "
        String[] points = coordinates.split(", ");
        List<List<Float>> result = new ArrayList<>();
        for (String point : points) {
            String[] xy = point.split(" ");
            float x = Float.parseFloat(xy[0]);
            float y = Float.parseFloat(xy[1]);
            List<Float> xyList = new ArrayList<>();
            xyList.add(x);
            xyList.add(y);
            result.add(xyList);
        }
        return result;
    }

}
