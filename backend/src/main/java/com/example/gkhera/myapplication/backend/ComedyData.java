package com.example.gkhera.myapplication.backend;

import java.util.HashMap;

/**
 * Created by gkhera on 28/12/2016.
 */

public class ComedyData {
    private static Comedy navedComedy = new Comedy(0,"Naved-mirchi murga",
            "http://media.radiomirchi.com/audios/audio_content/thumbnail_1430304758.jpg","Mirchi Murga",
            "27-12-2016");
    private static Comedy kapilComedy = new Comedy(1,"Kapil Sharma",
            "http://www.masala.com/sites/default/files/images/2015/09/07/kapil.jpg","Kapil Sharma",
            "27-12-2016");
    private static Comedy bhartiComedy = new Comedy(2,"Bharti Singh",
            "http://www.pmnupdates.com/wp-content/uploads/2015/09/Bharti-Singh-in-black.jpg","Bharti Singh",
            "27-12-2016");
    private static Comedy rajuComedy = new Comedy(3,"Raju Srivastava",
            "http://celebjankari.com/wp-content/uploads/2016/02/Raju-Srivastav-Education.png","Raju Srivastava",
            "27-12-2016");
    private static Comedy krushnaComedy = new Comedy(4,"Krushna Abhishek",
            "http://www.filmyfolks.com/celebrity/tellywood/images/krushna-abhishek.jpg","Krushna Abhishek",
            "27-12-2016");
    private static Comedy ehsaanComedy = new Comedy(5,"Ehsaan Kureshi",
            "http://www.cochintalkies.com/celebrities_image/thumb1/ehsaan-qureshi-movie-actor-pics-5919.jpg","Ehsaan Kureshi",
            "27-12-2016");
    private static HashMap<Integer, Comedy> comedyHashMap = new HashMap<Integer, Comedy>();

    static{
        comedyHashMap.put(navedComedy.getId(),navedComedy);
        comedyHashMap.put(kapilComedy.getId(),kapilComedy);
        comedyHashMap.put(bhartiComedy.getId(),bhartiComedy);
        comedyHashMap.put(rajuComedy.getId(),rajuComedy);
        comedyHashMap.put(krushnaComedy.getId(),krushnaComedy);
        comedyHashMap.put(ehsaanComedy.getId(),ehsaanComedy);
    }

    public static HashMap<Integer, Comedy> getComedyHashMap() {
        return comedyHashMap;
    }

}
