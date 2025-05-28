package com.example.surnameserver;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/me")
public class SurnameResource {

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Zhytnetskyi";
    }

}
