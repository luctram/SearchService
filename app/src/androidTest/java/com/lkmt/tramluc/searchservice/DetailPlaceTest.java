package com.lkmt.tramluc.searchservice;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class DetailPlaceTest {

    @Test
    public void main() throws IOException{
            ReviewService reviewPlace;
            Boolean openNow;
            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonData = mapper.readTree(new URL("https://jsonplaceholder.typicode.com/posts/42")).toString();
                Log.d("jSON", "getDetailPlace: " + jsonData);
            }
            catch (MalformedURLException err){
                System.out.println(err);
            }
        }

}