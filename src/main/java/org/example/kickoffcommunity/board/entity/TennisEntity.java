package org.example.kickoffcommunity.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class TennisEntity { //경기장 예약을 위해 생성한 Entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    private Integer reservedtime;

    private String location;

    private String sport;

    private Integer capacity;

    private String maintext;


    public TennisEntity() {
    }

    public TennisEntity(Integer id, LocalDate date, Integer reservedtime, String location, String sport, Integer capacity, String maintext) {
        this.id = id;
        this.date = date;
        this.reservedtime = reservedtime;
        this.location = location;
        this.sport = sport;
        this.capacity = capacity;
        this.maintext = maintext;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSport() {
        return this.sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getMaintext() {
        return this.maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public TennisEntity id(Integer id) {
        setId(id);
        return this;
    }

    public TennisEntity date(LocalDate date) {
        setDate(date);
        return this;
    }

    public TennisEntity reservedtime(Integer reservedtime) {
        setReservedtime(reservedtime);
        return this;
    }

    public TennisEntity location(String location) {
        setLocation(location);
        return this;
    }

    public TennisEntity sport(String sport) {
        setSport(sport);
        return this;
    }

    public TennisEntity capacity(Integer capacity) {
        setCapacity(capacity);
        return this;
    }

    public TennisEntity maintext(String maintext) {
        setMaintext(maintext);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TennisEntity)) {
            return false;
        }
        TennisEntity tennisEntity = (TennisEntity) o;
        return Objects.equals(id, tennisEntity.id) && Objects.equals(date, tennisEntity.date) && Objects.equals(reservedtime, tennisEntity.reservedtime) && Objects.equals(location, tennisEntity.location) && Objects.equals(sport, tennisEntity.sport) && Objects.equals(capacity, tennisEntity.capacity) && Objects.equals(maintext, tennisEntity.maintext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, reservedtime, location, sport, capacity, maintext);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", date='" + getDate() + "'" +
            ", reservedtime='" + getReservedtime() + "'" +
            ", location='" + getLocation() + "'" +
            ", sport='" + getSport() + "'" +
            ", capacity='" + getCapacity() + "'" +
            ", maintext='" + getMaintext() + "'" +
            "}";
    }
    
   
}
