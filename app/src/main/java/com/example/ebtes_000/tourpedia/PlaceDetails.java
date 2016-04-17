package com.example.ebtes_000.tourpedia;

import com.google.api.client.util.Key;

import java.io.Serializable;

/** Implement this class from "Serializable"
* So that you can pass this class Object to another using Intents
* Otherwise you can't pass to another actitivy
* */
public class PlaceDetails implements Serializable {


	@Key
	public String status;
	@Key
	public Place results;

	@Override
	public String toString() {
		if (results!=null) {
			return results.toString();
		}
		return super.toString();
	}
}
