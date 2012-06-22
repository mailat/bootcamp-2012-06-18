package com.intel.whish01;

import java.util.List;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class LocationApiActivity extends MapActivity implements LocationListener {
	
	LocationManager locationManager;
	Geocoder geocoder;
	TextView locationText;
	MapView map;	
	MapController mapController;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        locationText = (TextView)this.findViewById(R.id.placeLocation);
        
        map = (MapView)this.findViewById(R.id.placeMapview);
        map.setBuiltInZoomControls(true);
        mapController = map.getController();
        mapController.setZoom(16);
        
        locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);

        geocoder = new Geocoder(this);
        
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
        	Log.d("Demo12", "We already have a location saved: " + location.toString());
        	this.onLocationChanged(location);	
        }
    }

    @Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
	}
    
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d("Demo12", "onLocationChanged, new position: " + location.toString());

		// afiseaza datele despre loca?ie
		String text = String.format("Latitude:\t %f\nLongitude:\t %f\nAltitude:\t %f\nAccuracy:\t %f", location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy()) ;
		this.locationText.setText(text);
		
		try {
			//foloseste Geocoder pentru a afla adresa
			List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
			this.locationText.append("\nAddress: ");
			for (Address address : addresses) {
				this.locationText.append("\n" + address.getAddressLine(0));
			}
			
			int latitude = (int)(location.getLatitude() * 1000000);
			int longitude = (int)(location.getLongitude() * 1000000);

			GeoPoint point = new GeoPoint(latitude,longitude);
			mapController.animateTo(point);
			
		} catch (Throwable e) {
			Log.e("Demo12", "I cannot decode using Geocoder.", e);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}