package com.example.registeration;

public class Question {
    String question;
    String email;
    String date;

    public Question(String question, String email, String date) {
        this.question = question;
        this.email = email;
        this.date = date;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
