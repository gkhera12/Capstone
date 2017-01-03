package com.example.gkhera.myapplication.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gkhera on 1/01/2017.
 */

public class TrailersData {
    private static Trailer onlineShopping = new Trailer(0,0, "Online Shopping",
            "http://media.radiomirchi.com/audios/audio_content/ONLINESHOPPING-1460632266.mp3");
    private static List<Trailer> mirchiMurga = new ArrayList<>();
    private static Trailer doctorPatient = new Trailer(5,0, "doctor patients",
            "https://drive.google.com/file/d/0B09_qhQm-nIUd3ZRNWVDaXFoZDA/view?usp=sharing");
    private static List<Trailer> ehsaanQuereshi = new ArrayList<>();
    static{
        mirchiMurga.add(onlineShopping);
        ehsaanQuereshi.add(doctorPatient);
    }
    private static HashMap<Integer, List<Trailer>> trailersHashMap = new HashMap<Integer, List<Trailer>>();

    static {
        trailersHashMap.put(0, mirchiMurga);
        trailersHashMap.put(1, ehsaanQuereshi);
    }
    public static HashMap<Integer, List<Trailer>> getTrailersHashMap() {
        return trailersHashMap;
    }
}
