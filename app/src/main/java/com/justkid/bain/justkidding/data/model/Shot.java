package com.justkid.bain.justkidding.data.model;

import java.util.Date;
import java.util.List;


public class Shot {
    public final long id;
    public final String title;
    public String description;
    public long width;
    public long height;
    public Images images;
    public long views_count;
    public long likes_count;
    public long comments_count;
    public long attachments_count;
    public long rebounds_count;
    public long buckets_count;
    public Date created_at;
    public Date updated_at;
    public String html_url;
    public String attachments_url;
    public String buckets_url;
    public String comments_url;
    public String likes_url;
    public String projects_url;
    public String rebounds_url;
    public boolean animated;
    public List<String> tags;

    public User user;
    public Team team;

    public Shot(long id,
                String title,
                String description,
                long width,
                long height,
                Images images,
                long views_count,
                long likes_count,
                long comments_count,
                long attachments_count,
                long rebounds_count,
                long buckets_count,
                Date created_at,
                Date updated_at,
                String html_url,
                String attachments_url,
                String buckets_url,
                String comments_url,
                String likes_url,
                String projects_url,
                String rebounds_url,
                boolean animated,
                List<String> tags,
                User user,
                Team team) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.width = width;
        this.height = height;
        this.images = images;
        this.views_count = views_count;
        this.likes_count = likes_count;
        this.comments_count = comments_count;
        this.attachments_count = attachments_count;
        this.rebounds_count = rebounds_count;
        this.buckets_count = buckets_count;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.html_url = html_url;
        this.attachments_url = attachments_url;
        this.buckets_url = buckets_url;
        this.comments_url = comments_url;
        this.likes_url = likes_url;
        this.projects_url = projects_url;
        this.rebounds_url = rebounds_url;
        this.animated = animated;
        this.tags = tags;
        this.user = user;
        this.team = team;
    }

    public static class Builder {
        private long id;
        private String title;
        private String description;
        private long width;
        private long height;
        private Images images;
        private long views_count;
        private long likes_count;
        private long comments_count;
        private long attachments_count;
        private long rebounds_count;
        private long buckets_count;
        private Date created_at;
        private Date updated_at;
        private String html_url;
        private String attachments_url;
        private String buckets_url;
        private String comments_url;
        private String likes_url;
        private String projects_url;
        private String rebounds_url;
        private boolean animated;
        private List<String> tags;
        private User user;
        private Team team;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setWidth(long width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(long height) {
            this.height = height;
            return this;
        }

        public Builder setImages(Images images) {
            this.images = images;
            return this;
        }

        public Builder setViewsCount(long views_count) {
            this.views_count = views_count;
            return this;
        }

        public Builder setLikesCount(long likes_count) {
            this.likes_count = likes_count;
            return this;
        }

        public Builder setCommentsCount(long comments_count) {
            this.comments_count = comments_count;
            return this;
        }

        public Builder setAttachmentsCount(long attachments_count) {
            this.attachments_count = attachments_count;
            return this;
        }

        public Builder setReboundsCount(long rebounds_count) {
            this.rebounds_count = rebounds_count;
            return this;
        }

        public Builder setBucketsCount(long buckets_count) {
            this.buckets_count = buckets_count;
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

        public Builder setHtmlUrl(String html_url) {
            this.html_url = html_url;
            return this;
        }

        public Builder setAttachmentsUrl(String attachments_url) {
            this.attachments_url = attachments_url;
            return this;
        }

        public Builder setBucketsUrl(String buckets_url) {
            this.buckets_url = buckets_url;
            return this;
        }

        public Builder setCommentsUrl(String comments_url) {
            this.comments_url = comments_url;
            return this;
        }

        public Builder setLikesUrl(String likes_url) {
            this.likes_url = likes_url;
            return this;
        }

        public Builder setProjectsUrl(String projects_url) {
            this.projects_url = projects_url;
            return this;
        }

        public Builder setReboundsUrl(String rebounds_url) {
            this.rebounds_url = rebounds_url;
            return this;
        }

        public Builder setAnimated(boolean animated) {
            this.animated = animated;
            return this;
        }

        public Builder setTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setTeam(Team team) {
            this.team = team;
            return this;
        }

        public Shot build() {
            return new Shot(id, title, description, width, height, images, views_count,
                    likes_count, comments_count, attachments_count, rebounds_count,
                    buckets_count, created_at, updated_at, html_url, attachments_url,
                    buckets_url, comments_url, likes_url, projects_url, rebounds_url, animated,
                    tags, user, team);
        }
    }
}

