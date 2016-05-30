package com.justkid.bain.justkidding.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.justkid.bain.justkidding.util.ParcelableUtil;

import java.util.Date;
import java.util.Map;


public class User implements Parcelable {
    public final long id;
    public final String name;
    public final String username;
    public final String html_url;
    public final String avatar_url;
    public final String bio;
    public final String location;
    public final Map<String, String> links;
    public final long buckets_count;
    public final long comments_received_count;
    public final long followers_count;
    public final long followings_count;
    public final long likes_count;
    public final long likes_received_count;
    public final long projects_count;
    public final long rebounds_received_count;
    public final long shots_count;
    public final long teams_count;
    public final boolean can_upload_shot;
    public final String type;
    public final Boolean pro;
    public final String buckets_url;
    public final String followers_url;
    public final String following_url;
    public final String likes_url;
    public final String shots_url;
    public final String teams_url;
    public final Date created_at;
    public final Date updated_at;

    public User(long id,
                String name,
                String username,
                String html_url,
                String avatar_url,
                String bio,
                String location,
                Map<String, String> links,
                long buckets_count,
                long comments_received_count,
                long followers_count,
                long followings_count,
                long likes_count,
                long likes_received_count,
                long projects_count,
                long rebounds_received_count,
                long shots_count,
                long teams_count,
                boolean can_upload_shot,
                String type,
                Boolean pro,
                String buckets_url,
                String followers_url,
                String following_url,
                String likes_url,
                String shots_url,
                String teams_url,
                Date created_at,
                Date updated_at) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.html_url = html_url;
        this.avatar_url = avatar_url;
        this.bio = bio;
        this.location = location;
        this.links = links;
        this.buckets_count = buckets_count;
        this.comments_received_count = comments_received_count;
        this.followers_count = followers_count;
        this.followings_count = followings_count;
        this.likes_count = likes_count;
        this.likes_received_count = likes_received_count;
        this.projects_count = projects_count;
        this.rebounds_received_count = rebounds_received_count;
        this.shots_count = shots_count;
        this.teams_count = teams_count;
        this.can_upload_shot = can_upload_shot;
        this.type = type;
        this.pro = pro;
        this.buckets_url = buckets_url;
        this.followers_url = followers_url;
        this.following_url = following_url;
        this.likes_url = likes_url;
        this.shots_url = shots_url;
        this.teams_url = teams_url;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    protected User(Parcel in) {
        id = in.readLong();
        name = in.readString();
        username = in.readString();
        html_url = in.readString();
        avatar_url = in.readString();
        bio = in.readString();
        location = in.readString();
        links = ParcelableUtil.readStringMap(in);
        buckets_count = in.readLong();
        comments_received_count = in.readLong();
        followers_count = in.readLong();
        followings_count = in.readLong();
        likes_count = in.readLong();
        likes_received_count = in.readLong();
        projects_count = in.readLong();
        rebounds_received_count = in.readLong();
        shots_count = in.readLong();
        teams_count = in.readLong();
        can_upload_shot = in.readByte() != 0;
        type = in.readString();
        byte proVal = in.readByte();
        pro = proVal == 0x02 ? null : proVal != 0x00;
        buckets_url = in.readString();
        followers_url = in.readString();
        following_url = in.readString();
        likes_url = in.readString();
        shots_url = in.readString();
        teams_url = in.readString();
        long tmpCreated_at = in.readLong();
        created_at = tmpCreated_at != -1 ? new Date(tmpCreated_at) : null;
        long tmpUpdated_at = in.readLong();
        updated_at = tmpUpdated_at != -1 ? new Date(tmpUpdated_at) : null;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(html_url);
        dest.writeString(avatar_url);
        dest.writeString(bio);
        dest.writeString(location);
        ParcelableUtil.writeStringMap(links, dest);
        dest.writeLong(buckets_count);
        dest.writeLong(comments_received_count);
        dest.writeLong(followers_count);
        dest.writeLong(followings_count);
        dest.writeLong(likes_count);
        dest.writeLong(likes_received_count);
        dest.writeLong(projects_count);
        dest.writeLong(rebounds_received_count);
        dest.writeLong(shots_count);
        dest.writeLong(teams_count);
        dest.writeByte((byte) (can_upload_shot ? 1 : 0));
        dest.writeString(type);
        if (pro == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (pro ? 0x01 : 0x00));
        }
        dest.writeString(buckets_url);
        dest.writeString(followers_url);
        dest.writeString(following_url);
        dest.writeString(likes_url);
        dest.writeString(shots_url);
        dest.writeString(teams_url);
        dest.writeLong(created_at != null ? created_at.getTime() : -1L);
        dest.writeLong(updated_at != null ? updated_at.getTime() : -1L);
    }

    public static class Builder {
        private long id;
        private String name;
        private String username;
        private String html_url = null;
        private String avatar_url;
        private String bio = null;
        private String location = null;
        private Map<String, String> links = null;
        private long buckets_count = 0;
        private long comments_received_count=0;
        private long followers_count = 0;
        private long followings_count = 0;
        private long likes_count = 0;
        private long likes_received_count = 0;
        private long projects_count = 0;
        private long rebounds_received_count = 0;
        private long shots_count = 0;
        private long teams_count = 0;
        private boolean can_upload_shot = false;
        private String type = null;
        private Boolean pro = null;
        private String buckets_url = null;
        private String followers_url = null;
        private String following_url = null;
        private String likes_url = null;
        private String shots_url = null;
        private String teams_url = null;
        private Date created_at = null;
        private Date updated_at = null;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setAvatarUrl(String avatar_url) {
            this.avatar_url = avatar_url;
            return this;
        }

        public Builder setHtmlUrl(String html_url) {
            this.html_url = html_url;
            return this;
        }

        public Builder setBio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setLinks(Map<String, String> links) {
            this.links = links;
            return this;
        }

        public Builder setBucketsCount(long buckets_count) {
            this.buckets_count = buckets_count;
            return this;
        }

        public Builder setCommentsReceivedCount(long comments_received_count){
            this.comments_received_count = comments_received_count;
            return this;
        }

        public Builder setFollowersCount(long followers_count) {
            this.followers_count = followers_count;
            return this;
        }

        public Builder setFollowingsCount(long followings_count) {
            this.followings_count = followings_count;
            return this;
        }

        public Builder setLikesCount(long likes_count) {
            this.likes_count = likes_count;
            return this;
        }

        public Builder setLikesReceivedCount(long likes_received_count){
            this.likes_received_count = likes_received_count;
            return this;
        }

        public Builder setProjectsCount(long projects_count) {
            this.projects_count = projects_count;
            return this;
        }

        public Builder setReboundsReceivedCount(long rebounds_received_count){
            this.rebounds_received_count = rebounds_received_count;
            return this;
        }

        public Builder setShotsCount(long shots_count) {
            this.shots_count = shots_count;
            return this;
        }

        public Builder setTeamsCount(long teams_count) {
            this.teams_count = teams_count;
            return this;
        }

        public Builder setCanUploadShot(boolean can_upload_shot){
            this.can_upload_shot = can_upload_shot;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setPro(Boolean pro) {
            this.pro = pro;
            return this;
        }

        public Builder setBucketsUrl(String buckets_url) {
            this.buckets_url = buckets_url;
            return this;
        }

        public Builder setFollowersUrl(String followers_url) {
            this.followers_url = followers_url;
            return this;
        }

        public Builder setFollowingUrl(String following_url) {
            this.following_url = following_url;
            return this;
        }

        public Builder setLikesUrl(String likes_url) {
            this.likes_url = likes_url;
            return this;
        }

        public Builder setShotsUrl(String shots_url) {
            this.shots_url = shots_url;
            return this;
        }

        public Builder setTeamsUrl(String teams_url) {
            this.teams_url = teams_url;
            return this;
        }

        public Builder setCreatedAt(Date created_at) {
            this.created_at = created_at;
            return this;
        }

        public Builder setUpdatedAt(Date updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public User build() {
            return new User(id,
                    name,
                    username,
                    html_url,
                    avatar_url,
                    bio,
                    location,
                    links,
                    buckets_count,
                    comments_received_count,
                    followers_count,
                    followings_count,
                    likes_count,
                    likes_received_count,
                    projects_count,
                    rebounds_received_count,
                    shots_count,
                    teams_count,
                    can_upload_shot,
                    type,
                    pro,
                    buckets_url,
                    followers_url,
                    following_url,
                    likes_url,
                    shots_url,
                    teams_url,
                    created_at,
                    updated_at);
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public long getBuckets_count() {
        return buckets_count;
    }

    public long getComments_received_count() {
        return comments_received_count;
    }

    public long getFollowers_count() {
        return followers_count;
    }

    public long getFollowings_count() {
        return followings_count;
    }

    public long getLikes_count() {
        return likes_count;
    }

    public long getLikes_received_count() {
        return likes_received_count;
    }

    public long getProjects_count() {
        return projects_count;
    }

    public long getRebounds_received_count() {
        return rebounds_received_count;
    }

    public long getShots_count() {
        return shots_count;
    }

    public long getTeams_count() {
        return teams_count;
    }

    public boolean isCan_upload_shot() {
        return can_upload_shot;
    }

    public String getType() {
        return type;
    }

    public Boolean getPro() {
        return pro;
    }

    public String getBuckets_url() {
        return buckets_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public String getShots_url() {
        return shots_url;
    }

    public String getTeams_url() {
        return teams_url;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }
}

