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
    private Integer tn_id;

    private LocalDate date;

    private Integer reservedtime;

    private String location;

    private String sport;

    private Integer capacity;

    private String maintext;

    private String teamB;

    private String teamA;

    public TennisEntity(Integer tn_id, LocalDate date, Integer reservedtime, String location, String sport, Integer capacity, String maintext, String teamB, String teamA) {
        this.tn_id = tn_id;
        this.date = date;
        this.reservedtime = reservedtime;
        this.location = location;
        this.sport = sport;
        this.capacity = capacity;
        this.maintext = maintext;
        this.teamB = teamB;
        this.teamA = teamA;
    }

    public String getTeamB() {
        return this.teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public String getTeamA() {
        return this.teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public TennisEntity teamB(String teamB) {
        setTeamB(teamB);
        return this;
    }

    public TennisEntity teamA(String teamA) {
        setTeamA(teamA);
        return this;
    }


    public TennisEntity() {
    }

    public TennisEntity(Integer tn_id, LocalDate date, Integer reservedtime, String location, String sport, Integer capacity, String maintext) {
        this.tn_id = tn_id;
        this.date = date;
        this.reservedtime = reservedtime;
        this.location = location;
        this.sport = sport;
        this.capacity = capacity;
        this.maintext = maintext;
    }

    public Integer getId() {
        return this.tn_id;
    }

    public void setId(Integer tn_id) {
        this.tn_id = tn_id;
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

    public TennisEntity tn_id(Integer tn_id) {
        setId(tn_id);
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
        return Objects.equals(tn_id, tennisEntity.tn_id) && Objects.equals(date, tennisEntity.date) && Objects.equals(reservedtime, tennisEntity.reservedtime) && Objects.equals(location, tennisEntity.location) && Objects.equals(sport, tennisEntity.sport) && Objects.equals(capacity, tennisEntity.capacity) && Objects.equals(maintext, tennisEntity.maintext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tn_id, date, reservedtime, location, sport, capacity, maintext);
    }

    @Override
    public String toString() {
        return "{" +
            " tn_id='" + getId() + "'" +
            ", date='" + getDate() + "'" +
            ", reservedtime='" + getReservedtime() + "'" +
            ", location='" + getLocation() + "'" +
            ", sport='" + getSport() + "'" +
            ", capacity='" + getCapacity() + "'" +
            ", maintext='" + getMaintext() + "'" +
            "}";
    }
    
   
}
