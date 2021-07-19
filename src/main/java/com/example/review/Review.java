package com.example.review;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
    @JsonProperty
    public final int id;

    @JsonProperty
    public final String subject;

    public Review(int id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                '}';
    }
}
