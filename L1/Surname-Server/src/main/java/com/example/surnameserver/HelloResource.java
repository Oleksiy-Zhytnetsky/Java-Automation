package com.example.surnameserver;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/me")
public class HelloResource {

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Zhytnetskyi";
    }

}
