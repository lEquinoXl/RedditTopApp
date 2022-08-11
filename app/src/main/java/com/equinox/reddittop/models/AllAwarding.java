package com.equinox.reddittop.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AllAwarding{
    public String giver_coin_reward;
    public String subreddit_id;
    public boolean is_new;
    public int days_of_drip_extension;
    public int coin_price;
    public String id;
    public Object penny_donate;
    public String award_sub_type;
    public int coin_reward;
    public String icon_url;
    public int days_of_premium;
    public String tiers_by_required_awardings;
    public ArrayList<ResizedIcon> resized_icons;
    public int icon_width;
    public int static_icon_width;
    public LocalDateTime start_date;
    public boolean is_enabled;
    public int awardings_required_to_grant_benefits;
    public String description;
    public LocalDateTime end_date;
    public double sticky_duration_seconds;
    public int subreddit_coin_reward;
    public int count;
    public int static_icon_height;
    public String name;
    public ArrayList<ResizedStaticIcon> resized_static_icons;
    public String icon_format;
    public int icon_height;
    public int penny_price;
    public String award_type;
    public String static_icon_url;
}
class ResizedIcon{
    public String url;
    public int width;
    public int height;
}

class ResizedStaticIcon{
    public String url;
    public int width;
    public int height;
}
