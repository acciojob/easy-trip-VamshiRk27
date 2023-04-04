package com.driver;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class AirportService {
    AirportRepository airportRepository=new AirportRepository();
    public String addAirport(Airport airport){
        return airportRepository.addAirport(airport);
    }
    //2. Get The Largest airport in terms of terminals
    public String getLargestAirportName(){
        int max=0;
        String ans="";
        HashMap<String,Airport> airportDb=airportRepository.getAllAirports();
        for(Airport airport:airportDb.values()){
            if(airport.getNoOfTerminals()>max){
                max=airport.getNoOfTerminals();
                ans=airport.getAirportName();
            }
            else if(airport.getNoOfTerminals()==max && airport.getAirportName().length()<ans.length()){
                ans=airport.getAirportName();
            }
        }
        return ans;
    }
    //3. get the shortest time travel between cities
    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        double duration =Double.MAX_VALUE;
        HashMap<Integer,Flight> flightDb=airportRepository.getAllFlights();
        for(Flight flight : flightDb.values()){
            if((flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity)) && flight.getDuration()<duration){
                duration=flight.getDuration();
            }
        }
        if(duration>0){
            return duration;
        }
        else{
            return -1;
        }
    }
    //4. Get no of people on airport on date
    public int getNumberOfPeopleOn(Date date,String airportName){
        HashMap<String,Airport> airportDb=airportRepository.getAllAirports();
        HashMap<Integer,Flight> flightDb=airportRepository.getAllFlights();
        HashMap<Integer, List<Integer>> flightToPassengerDb=airportRepository.getAllFlightToPassengers();
        int count=0;
        City city=null;
        if(!airportDb.containsKey(airportName)){
            return 0;
        }
        for(Airport airport:airportDb.values()){
            if(airport.getAirportName().equals(airportName)){
                city=airport.getCity();
            }
        }
        for(Flight flight:flightDb.values()){
            if(flight.getFlightDate().equals(date) && (flight.getFromCity().equals(city) || flight.getToCity().equals(city))){
                int flightId=flight.getFlightId();
                count+=flightToPassengerDb.get(flightId).size();
            }
        }
        return count;
    }
    //5. Calculate Fare
    public int calculateFlightFare(Integer flightId){
        HashMap<Integer, List<Integer>> flightToPassengerDb=airportRepository.getAllFlightToPassengers();
        List<Integer> tickets=flightToPassengerDb.get(flightId);
        if(tickets==null){
            return 0;
        }
        int alreadyBooked=tickets.size();
        return 3000+(alreadyBooked*50);
    }
    //6. Book a ticket
    public String bookATicket(int flightId,int passengerId){
        return airportRepository.bookATicket(flightId, passengerId);
    }
    //7. Cancel a ticket
    public String cancelATicket(Integer flightId,Integer passengerId){
        String status="FAILURE";
        HashMap<Integer,Flight> flightDb=airportRepository.getAllFlights();
        HashMap<Integer, List<Integer>> flightToPassengerDb=airportRepository.getAllFlightToPassengers();
        if(!flightDb.containsKey(flightId)){
            return status;
        }
        List<Integer> tickets=flightToPassengerDb.get(flightId);
        if(tickets.contains(passengerId)){
            tickets.remove(passengerId);
            status="SUCCESS";
        }
        return status;
    }
    //8.Get bookings count by a passenger
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        HashMap<Integer,List<Integer>> flightToPassengerDb=airportRepository.getAllFlightToPassengers();
        int count=0;
        for(List<Integer> tickets:flightToPassengerDb.values()) {
            for(Integer ticket:tickets){
                if(Objects.equals(ticket,passengerId)){
                    count++;
                }
            }
        }
        return count;
    }
    //9. Add flight
    public String addFlight(Flight flight){
        return airportRepository.addFlight(flight);
    }
    //10. get airport name from flight takeoff
    public String getAirportNameFromFlightId(Integer flightId){
        String airportName=null;
        City city=null;
        HashMap<Integer,Flight> flightDb=airportRepository.getAllFlights();
        for(Flight flight:flightDb.values()){
            if(flight.getFlightId()==flightId){
                city=flight.getFromCity();
                break;
            }
        }
        HashMap<String,Airport> airportDb=airportRepository.getAllAirports();
        for(Airport airport:airportDb.values()){
            if(airport.getCity()==city){
                airportName=airport.getAirportName();
            }
        }
        return airportName;
    }
    //11. Calculate revenue collected from a flight
    public int calculateRevenueOfAFlight(Integer flightId){
        HashMap<Integer,List<Integer>> flightToPassengerDb=airportRepository.getAllFlightToPassengers();
        List<Integer> passengers=flightToPassengerDb.get(flightId);
        int n=passengers.size();
        int a=3000,diff=50;
        int lastTicketPrice=a+((n-1)*diff);
        return (a+lastTicketPrice)*n/2;
    }
    //12. add passenger
    public String addPassenger(Passenger passenger){
        return airportRepository.addPassenger(passenger);
    }
}
