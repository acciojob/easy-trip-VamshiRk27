package com.driver;

import com.driver.model.Airport;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AirportRepository {
    private HashMap<String,Airport> airportDb; // Name as Key , Airport Object as value
    private HashMap<Integer,Flight> flightDb; // flightId(int) as Key , Flight Object as value
    private HashMap<Integer,Passenger> passengerDb; // passengerID(int) as Key , Passenger Object as value
    private HashMap<Integer, List<Integer>> flightToPassengerDb; // flightId(int) as key , List of Passenger as Value
    public AirportRepository() {
        this.airportDb = new HashMap<>();
        this.flightDb = new HashMap<>();
        this.passengerDb = new HashMap<>();
        this.flightToPassengerDb = new HashMap<>();
    }

    public String addAirport(Airport airport){
        String name=airport.getAirportName();
        String ans=null;
        if(!airportDb.containsKey(name)){
            airportDb.put(name,airport);
            ans="SUCCESS";
        }
        return ans;
    }
    //6. Book a ticket
    public String bookATicket(int flightId,int passengerId){
        List<Integer> tickets=flightToPassengerDb.get(flightId);
        if(tickets==null){
            tickets=new ArrayList<Integer>();
        }
        Flight flight=flightDb.get(flightId);
        if(tickets.size()>=flight.getMaxCapacity()){
            return "FAILURE";
        }
        if(tickets.contains(passengerId)){
            return "FAILURE";
        }
        tickets.add(passengerId);
        flightToPassengerDb.put(flightId,tickets);
        return "SUCCESS";
    }
    //9. Add flight
    public String addFlight(Flight flight){
        int id=flight.getFlightId();
        String ans=null;
        if(!flightDb.containsKey(id)){
            flightDb.put(id,flight);
            ans="SUCCESS";
        }
        return ans;
    }
    //12. Add passenger
    public String addPassenger(Passenger passenger){
        int id=passenger.getPassengerId();
        String ans="";
        if(!passengerDb.containsKey(id)){
            passengerDb.put(id,passenger);
            ans="SUCCESS";
        }
        else{
            ans=null;
        }
        return ans;
    }

    // Get all Airport Db
    public HashMap<String,Airport> getAllAirports(){
        return airportDb;
    }
    // Get all flights Db
    public HashMap<Integer,Flight> getAllFlights(){
        return flightDb;
    }
    // Get all Passenger Db
    public HashMap<Integer,Passenger> getAllPassengers(){
        return passengerDb;
    }
    // Get all Flight to Passenger Db
    public HashMap<Integer,List<Integer>> getAllFlightToPassengers(){
        return flightToPassengerDb;
    }
}
