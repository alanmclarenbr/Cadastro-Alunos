package br.com.caelum.cadastro.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import br.com.caelum.cadastro.fragment.MapaFragment;

/**
 * Class Created by android6196 on 19/11/16.
 */
public class AtualizadorDeLocalizacao implements LocationListener {

    private GoogleApiClient client;
    private MapaFragment mapa;
    private Context ctx;

    public AtualizadorDeLocalizacao(Context ctx, MapaFragment mapa) {
        this.mapa = mapa;
        this.ctx = ctx;
        Configurador config = new Configurador(this);
        this.client = new GoogleApiClient.Builder(ctx).addApi(LocationServices.API)
                .addConnectionCallbacks(config).build();
        this.client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng local = new LatLng(latitude, longitude);
        this.mapa.centralizaNo(local, this.mapa.getGoogleMap());

    }

    public void inicia(LocationRequest request) {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    public void cancela(){
        LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        this.client.disconnect();
    }
}
