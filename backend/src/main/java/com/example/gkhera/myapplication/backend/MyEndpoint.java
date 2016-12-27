/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.gkhera.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.ArrayList;
import java.util.List;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "comedyBox",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.gkhera.example.com",
                ownerName = "backend.myapplication.gkhera.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "getComedyData")
    public ComedyResults getComedyData() {
        ComedyResults results = new ComedyResults();
        List<Comedy> comedies = new ArrayList<>();
        Comedy comedy = new Comedy();
        comedy.setArtist("Naved-mirchi murga");
        comedy.setId(1);
        comedy.setRelease_date("27-12-2016");
        comedy.setOverview("mirchi murga");
        comedy.setPoster_path("http://media.radiomirchi.com/audios/audio_content/thumbnail_1430304758.jpg");
        comedies.add(comedy);
        results.setResults(comedies);
        return results;
    }

}
