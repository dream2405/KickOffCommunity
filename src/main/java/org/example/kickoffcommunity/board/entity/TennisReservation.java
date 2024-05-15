package org.example.kickoffcommunity.board.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class TennisReservation { //경기장 별 예약 현황을 저장하기 위한 Entity
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String location;
    private LocalDate date;

    private Integer reservedtime;


    public TennisReservation() {
    }

    public TennisReservation(Integer id, String location, LocalDate date, Integer reservedtime) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.reservedtime = reservedtime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getReservedtime() {
        return this.reservedtime;
    }

    public void setReservedtime(Integer reservedtime) {
        this.reservedtime = reservedtime;
    }

    public TennisReservation id(Integer id) {
        setId(id);
        return this;
    }

    public TennisReservation location(String location) {
        setLocation(location);
        return this;
    }

    public TennisReservation date(LocalDate date) {
        setDate(date);
        return this;
    }

    public TennisReservation reservedtime(Integer reservedtime) {
        setReservedtime(reservedtime);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TennisReservation)) {
            return false;
        }
        TennisReservation tennisReservation = (TennisReservation) o;
        return Objects.equals(id, tennisReservation.id) && Objects.equals(location, tennisReservation.location) && Objects.equals(date, tennisReservation.date) && Objects.equals(reservedtime, tennisReservation.reservedtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, date, reservedtime);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", location='" + getLocation() + "'" +
            ", date='" + getDate() + "'" +
            ", reservedtime='" + getReservedtime() + "'" +
            "}";
    }
    
   
}
