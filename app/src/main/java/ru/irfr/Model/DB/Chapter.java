package ru.irfr.Model.DB;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Chapter extends RealmObject {

    private int id;
    private String header;

    @SerializedName("type_id")
    private String typeId;
    private String code;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
