package org.trimatek.digideal.ui.utils;

import java.io.IOException;
import java.util.Optional;

import org.trimatek.digideal.ui.Context;
import org.trimatek.digideal.ui.model.Address;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;

public class Geocoder {

	private static Geocoder INSTANCE;
	private GeoApiContext context;

	private Geocoder() {
		context = new GeoApiContext.Builder().apiKey(Context.GOOGLE_GEO_API_KEY).build();
	}

	public static Geocoder get() {
		if (INSTANCE == null) {
			INSTANCE = new Geocoder();
		}
		return INSTANCE;
	}

	public Optional<Address> geocode(String addressString) throws Exception {
		Address address = null;
		GeocodingResult[] results = GeocodingApi.geocode(context, addressString).await();
		if (results.length > 0 && results[0].addressComponents != null) {
			AddressComponent comps[] = results[0].addressComponents;
			address = Address.build(comps);
		}
		return Optional.ofNullable(address);
	}

	public static void main(String[] args) throws IOException, ApiException, InterruptedException {
		String address = "amenabar 2020 buenos aires";
		// geocode(address);
		/*
		 * String query =
		 * "https://maps.googleapis.com/maps/api/geocode/json?address=amenabar 2020 piso 1 dto B buenos Aires&key=AIzaSyAcLmRFJceRjP-Wkg8NQ2XOm-6cvML8A8E"
		 * ;
		 * 
		 */

	}
}
