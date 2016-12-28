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
import java.util.HashMap;
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
        HashMap<Integer, Comedy> map = ComedyData.getComedyHashMap();
        for(int i =0; i<map.size();i++){
            comedies.add(map.get(i));
        }
        results.setResults(comedies);
        return results;
    }

}
