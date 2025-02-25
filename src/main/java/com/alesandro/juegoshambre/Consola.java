package com.alesandro.juegoshambre;

import java.io.*;

/**
 * AlesandroQuirós
 */
public class Consola {

    public static byte leeByte() {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        byte x = 0;
        try {
            x = Byte.valueOf(in.readLine());
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;

    }

    public static short leeShort() {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        short x = 0;
        try {
            x = Short.valueOf(in.readLine());
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;

    }

    public static int leeInt() {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int x = 0;
        try {
            x = Integer.valueOf(in.readLine());
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;

    }

    public static long leeLong() {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        long x = 0;
        try {
            x = Long.valueOf(in.readLine());
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;

    }

    public static float leeFloat() {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        float x = 0;
        try {
            x = Float.valueOf(in.readLine());
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;

    }

    public static double leeDouble() {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        double x = 0;
        try {
            x = Double.valueOf(in.readLine());
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;

    }

    public static char leeChar() {

        char car = 0;
        BufferedInputStream b = new BufferedInputStream(System.in);
        try {
            car = (char) b.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return car;

    }

    public static String leeString() {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        try {
            str = in.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;

    }

}
