package com.harkin.luas.models.api;

/**
 * Created by henry on 14/07/2014.
 */
public class Luas {
    private String arrivaldatetime;
    private String duetime;
    private String departuredatetime;
    private String departureduetime;
    private String scheduledarrivaldatetime;
    private String scheduleddeparturedatetime;
    private String destination;
    private String destinationlocalized;
    private String origin;
    private String originlocalized;
    private String direction;
    private String operator;
    private String additionalinformation;
    private String lowfloorstatus;
    private String route;
    private String sourcetimestamp;

    public String getArrivaldatetime() {
        return arrivaldatetime;
    }

    public void setArrivaldatetime(String arrivaldatetime) {
        this.arrivaldatetime = arrivaldatetime;
    }

    public String getDuetime() {
        return duetime;
    }

    public void setDuetime(String duetime) {
        this.duetime = duetime;
    }

    public String getDeparturedatetime() {
        return departuredatetime;
    }

    public void setDeparturedatetime(String departuredatetime) {
        this.departuredatetime = departuredatetime;
    }

    public String getDepartureduetime() {
        return departureduetime;
    }

    public void setDepartureduetime(String departureduetime) {
        this.departureduetime = departureduetime;
    }

    public String getScheduledarrivaldatetime() {
        return scheduledarrivaldatetime;
    }

    public void setScheduledarrivaldatetime(String scheduledarrivaldatetime) {
        this.scheduledarrivaldatetime = scheduledarrivaldatetime;
    }

    public String getScheduleddeparturedatetime() {
        return scheduleddeparturedatetime;
    }

    public void setScheduleddeparturedatetime(String scheduleddeparturedatetime) {
        this.scheduleddeparturedatetime = scheduleddeparturedatetime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationlocalized() {
        return destinationlocalized;
    }

    public void setDestinationlocalized(String destinationlocalized) {
        this.destinationlocalized = destinationlocalized;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginlocalized() {
        return originlocalized;
    }

    public void setOriginlocalized(String originlocalized) {
        this.originlocalized = originlocalized;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAdditionalinformation() {
        return additionalinformation;
    }

    public void setAdditionalinformation(String additionalinformation) {
        this.additionalinformation = additionalinformation;
    }

    public String getLowfloorstatus() {
        return lowfloorstatus;
    }

    public void setLowfloorstatus(String lowfloorstatus) {
        this.lowfloorstatus = lowfloorstatus;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSourcetimestamp() {
        return sourcetimestamp;
    }

    public void setSourcetimestamp(String sourcetimestamp) {
        this.sourcetimestamp = sourcetimestamp;
    }
}
