package com.example.ddb.Entities;

import com.example.ddb.Data.CitiesList;

public class Address {
    private String Country;
    private String City;
    private String Street;
    private int Number;

    public Address() {
    }

    public Address(String country, String city, String street, int number) throws Exception {
        setCountry(country);
        try {
            setCity(city);
        } catch (Exception e) {
            throw new Exception(e);
        }
        setStreet(street);
        setNumber(number);
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) throws Exception {
        boolean flag = false;
        if (CitiesList.getCitiesArray().length == 0) {
            for (String citie : CitiesList.getCitiesArray()) {
                if (city == citie) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                throw new Exception("the city dose not exist");
        }
        City = city;
    }
    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

}
