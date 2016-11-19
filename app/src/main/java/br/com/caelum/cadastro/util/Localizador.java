package br.com.caelum.cadastro.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Class Created by android6196 on 19/11/16.
 */
public class Localizador {

    private Geocoder geo;

    public Localizador(Context ctx){
        geo = new Geocoder(ctx);
    }

    public LatLng getCoordenada(String endereco){
        try {
            List<Address> addresses = geo.getFromLocationName(endereco, 1);
            if (!addresses.isEmpty()) {
                return new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
