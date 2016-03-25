package com.example.ebtes_000.tourpedia;

import java.io.Serializable;
import java.util.List;

/** Implement this class from "Serializable"
* So that you can pass this class Object to another using Intents
* Otherwise you can't pass to another actitivy
* */
public class PlacesList implements Serializable {

	public String status;


	public List<Place> results;

}