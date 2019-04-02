package org.mendybot.android.todo.ads.model.domain;

import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

public abstract class AdRequest {
    private String id = "4487159888663217856";
    private ArrayList<BannerAdUnit> adUnitsToBidUpon = new ArrayList<>();
    private String type;
    private String app = "628";
    private String page = "/zcashs-coming-hard-fork-pave-way-even";
    private String _fshash = "38b2680f7b";
    private String _fsloc;
    private String _fsuid;
    private String _fssid;
    private transient String country;
    private transient String city;


    public AdRequest(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void add(BannerAdUnit adUnit) {
        adUnitsToBidUpon.add(adUnit);
    }

    public void setCountry(String country) {
        this.country = country;
        initFsLoc();
    }

    public void setCity(String city) {
        //this.city = new String(Base64.getEncoder().encode(city.getBytes()));
        this.city = city;
        initFsLoc();
    }

    public String getCity() {
        //return new String(Base64.getDecoder().decode(city.getBytes()));
        return city;
    }

    public void setUid(UUID uuid) {
        _fsuid = uuid.toString();
    }

    public void setSid(UUID uuid) {
        _fssid = uuid.toString();
    }

    private void initFsLoc() {
        StringBuilder sb = new StringBuilder();
        if (country != null) {
            sb.append("i="+country);
        }
        if (city != null) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append("c="+city);
        }
        if (sb.length() > 0) {
            _fsloc = "?"+sb.toString();
        } else {
            _fsloc = "";
        }
    }

}
