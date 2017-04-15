package ru.irfr.Model;

public class ResultDetail {

    private String date;
    private String time;
    private String countAnswer;
    private String countPoint;
    private String countErrorAnswer;
    private String result;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountErrorAnswer() {
        return countErrorAnswer;
    }

    public void setCountErrorAnswer(String countErrorAnswer) {
        this.countErrorAnswer = countErrorAnswer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCountAnswer() {
        return countAnswer;
    }

    public void setCountAnswer(String countAnswer) {
        this.countAnswer = countAnswer;
    }

    public String getCountPoint() {
        return countPoint;
    }

    public void setCountPoint(String countPoint) {
        this.countPoint = countPoint;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
