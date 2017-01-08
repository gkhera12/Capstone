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
    private static Trailer amazingPerformance = new Trailer(3,1, "Politics in India",
            "https://drive.google.com/file/d/0B09_qhQm-nIUNldZWGswcW9JZjg/view?usp=sharing");
    private static Trailer newsReporter= new Trailer(3,0, "News Reporter Comedy",
            "https://drive.google.com/file/d/0B09_qhQm-nIUOEdJUEZMQjVRUlk/view?usp=sharing");
    private static List<Trailer> rajuSrivastava = new ArrayList<>();
    private static Trailer doctorPatient = new Trailer(3,0, "Doctor Patients",
            "https://drive.google.com/file/d/0B09_qhQm-nIUd3ZRNWVDaXFoZDA/view?usp=sharing");
    private static List<Trailer> ehsaanQuereshi = new ArrayList<>();
    private static Trailer firstAct = new Trailer(1,0, "First Act",
            "https://drive.google.com/file/d/0B09_qhQm-nIUMkdOWlVQdFkyQzQ/view?usp=sharing");
    private static Trailer indianRecord = new Trailer(1,1, "Indian Records",
            "https://drive.google.com/file/d/0B09_qhQm-nIUcHdoeFQ2QzlSOVE/view?usp=sharing");
    private static List<Trailer> kapilSharma = new ArrayList<>();
    private static List<Trailer> bhartiSingh= new ArrayList<>();
    private static List<Trailer> krushnaAbhishek= new ArrayList<>();
    static{
        mirchiMurga.add(onlineShopping);
        kapilSharma.add(firstAct);
        kapilSharma.add(indianRecord);
        rajuSrivastava.add(newsReporter);
        rajuSrivastava.add(amazingPerformance);
        ehsaanQuereshi.add(doctorPatient);
    }
    private static HashMap<Integer, List<Trailer>> trailersHashMap = new HashMap<Integer, List<Trailer>>();

    static {
        trailersHashMap.put(0, mirchiMurga);
        trailersHashMap.put(1, kapilSharma);
        trailersHashMap.put(2, bhartiSingh);
        trailersHashMap.put(3, rajuSrivastava);
        trailersHashMap.put(4, krushnaAbhishek);
        trailersHashMap.put(5, ehsaanQuereshi);
    }
    public static HashMap<Integer, List<Trailer>> getTrailersHashMap() {
        return trailersHashMap;
    }
}
