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
    private static Trailer juddGaye= new Trailer(0,1, "Humaare Saath Judd Gaye",
            "http://media.radiomirchi.com/audios/audio_content/JUDDGAYE-1460632097.mp3");
    private static List<Trailer> mirchiMurga = new ArrayList<>();
    private static Trailer amazingPerformance = new Trailer(3,1, "Politics in India",
            "https://drive.google.com/uc?export=download&id=0B09_qhQm-nIUNldZWGswcW9JZjg");
    private static Trailer newsReporter= new Trailer(3,0, "News Reporter Comedy",
            "https://drive.google.com/uc?export=download&id=0B09_qhQm-nIUOEdJUEZMQjVRUlk");
    private static List<Trailer> rajuSrivastava = new ArrayList<>();
    private static Trailer mallikaJi = new Trailer(5,1,"Mallika ji",
        "https://drive.google.com/uc?export=download&id=0B09_qhQm-nIUSXV6cDNrUGw4Rk0");
    private static Trailer doctorPatient = new Trailer(5,0, "Doctor Patients",
            "https://drive.google.com/uc?export=download&id=0B09_qhQm-nIUd3ZRNWVDaXFoZDA");
    private static List<Trailer> ehsaanQuereshi = new ArrayList<>();
    private static Trailer firstAct = new Trailer(1,0, "First Act",
            "https://drive.google.com/uc?export=download&id=0B09_qhQm-nIUMkdOWlVQdFkyQzQ");
    private static Trailer indianRecord = new Trailer(1,1, "Indian Records",
            "https://drive.google.com/uc?export=download&id=0B09_qhQm-nIUcHdoeFQ2QzlSOVE");
    private static Trailer krushnaComedy= new Trailer(4,0, "Comedy Circus",
            "https://drive.google.com/uc?export=download&id=0B09_qhQm-nIUWVBiRHYzWnd5bU0");
    private static Trailer krushnaComedd2= new Trailer(4,1, "Krushna as gabbar",
            "https://drive.google.com/uc?export=download&id=0B09_qhQm-nIUcTNhdG1McnV3MGc");
    private static List<Trailer> kapilSharma = new ArrayList<>();
    private static List<Trailer> bhartiSingh= new ArrayList<>();
    private static Trailer bhartiComedy2= new Trailer(2,1, "Comedy Naya Daur",
            "https://drive.google.com/uc?export=download&id=0B09_qhQm-nIUb2M3NGhleXgwRmM");
    private static Trailer bhartiComedy1= new Trailer(2,0, "Comedy with Sidarth",
            "https://drive.google.com/uc?export=download&id=0B09_qhQm-nIUcTNhdG1McnV3MGc");
    private static List<Trailer> krushnaAbhishek= new ArrayList<>();
    static{
        mirchiMurga.add(onlineShopping);
        mirchiMurga.add(juddGaye);
        kapilSharma.add(firstAct);
        kapilSharma.add(indianRecord);
        rajuSrivastava.add(newsReporter);
        rajuSrivastava.add(amazingPerformance);
        ehsaanQuereshi.add(doctorPatient);
        ehsaanQuereshi.add(mallikaJi);
        krushnaAbhishek.add(krushnaComedy);
        krushnaAbhishek.add(krushnaComedd2);
        bhartiSingh.add(bhartiComedy1);
        bhartiSingh.add(bhartiComedy2);
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
