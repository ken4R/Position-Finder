package com.example.findme;

public class Position {
    int id;
    String numero,latitude,longitude;

    public Position(int id, String numero, String latitude, String longitude) {

        this.id = id;
        this.numero = numero;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
