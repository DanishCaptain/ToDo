package org.mendybot.android.todo.ads.model.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class BannerAdRequestTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void checkGson() {
        StringBuilder ut = new StringBuilder();
        ut.append("{");
        ut.append("\"id\":\"4487159888663217856\",");
        ut.append("\"adUnitsToBidUpon\":[");
        ut.append("{");
        ut.append("\"id\":\"2\",");
        ut.append("\"adUnitCode\":\"freestar-slot-footer-ad2\",");
        ut.append("\"size\":\"300x250\",");
        ut.append("\"promo_sizes\":\"300x250\"");
        ut.append("}");
        ut.append("],");
        ut.append("\"type\":\"banner\",");
        ut.append("\"app\":\"628\",");
        ut.append("\"page\":\"/zcashs-coming-hard-fork-pave-way-even\",");
        ut.append("\"_fshash\":\"38b2680f7b\",");
        ut.append("\"_fsloc\":\"?i=US&c=TG9zIEFuZ2VsZXM=\",");
        ut.append("\"_fsuid\":\"41624435-5e3e-47c6-b382-a938abb79283\",");
        ut.append("\"_fssid\":\"2eb39786-92d7-4477-98ed-8a69e016b9af\"");
        ut.append("}");

        BannerAdRequest ad = new BannerAdRequest();
        ad.setCountry("US");
        ad.setCity("Los Angeles");
        ad.setSid(UUID.fromString("2eb39786-92d7-4477-98ed-8a69e016b9af"));
        ad.setUid(UUID.fromString("41624435-5e3e-47c6-b382-a938abb79283"));
        BannerAdUnit adUnit = new BannerAdUnit();
        ad.add(adUnit);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(ad);
        assertEquals(ut.toString(), json);
    }
}