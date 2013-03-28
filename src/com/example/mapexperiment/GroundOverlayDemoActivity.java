package com.example.mapexperiment;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 * This shows how to add a ground overlay to a map.
 */
public class GroundOverlayDemoActivity extends FragmentActivity implements
		OnSeekBarChangeListener {

	private static final int TRANSPARENCY_MAX = 100;
	private static final LatLng NEWARK = new LatLng(40.714086, -74.228697);

	private GoogleMap mMap;
	private GroundOverlay mGroundOverlay;
	private SeekBar mTransparencyBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ground_overlay_demo);

		mTransparencyBar = (SeekBar) findViewById(R.id.transparencySeekBar);
		mTransparencyBar.setMax(TRANSPARENCY_MAX);
		mTransparencyBar.setProgress(0);

		setUpMapIfNeeded();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		Geocoder geocoder = new Geocoder(getApplicationContext());
		List<Address> addr = null;
		try {
			addr = geocoder.getFromLocationName("Bhutan", 5);
			Toast.makeText(
					getApplicationContext(),
					addr.get(0).getCountryName() + " " , Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		LatLng newLatLng = new LatLng(addr.get(0).getLatitude(), addr.get(0)
//				.getLongitude());

		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NEWARK, 11));

		mGroundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
				.image(BitmapDescriptorFactory
						.fromResource(R.drawable.newark_nj_1922)).anchor(0, 1)
				.position(NEWARK, 8600f, 6500f));

		mTransparencyBar.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (mGroundOverlay != null) {
			mGroundOverlay.setTransparency((float) progress
					/ (float) TRANSPARENCY_MAX);
		}
	}
}
