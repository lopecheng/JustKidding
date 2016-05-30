package com.justkid.bain.justkidding.data.model;

import java.util.Map;


public class Team {
    public long id;
    public String name;
    public String username;
    public String html_url;
    public String avatar_url;
    public String bio;
    public String location;
    public Map<String,String> links;

    public Team(long id,
                String name,
                String username,
                String html_url,
                String avatar_url,
                String bio,
                String location,
                Map<String, String> links) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.html_url = html_url;
        this.avatar_url = avatar_url;
        this.bio = bio;
        this.location = location;
        this.links = links;
    }
}