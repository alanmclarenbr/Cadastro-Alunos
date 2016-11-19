package br.com.caelum.cadastro.util;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Class Created by android6196 on 19/11/16.
 */
public class Configurador implements GoogleApiClient.ConnectionCallbacks{

    private AtualizadorDeLocalizacao atualizador;

    public Configurador(AtualizadorDeLocalizacao atualizador){
        this.atualizador = atualizador;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(50);

        atualizador.inicia(locationRequest);
    }

    @Override
    public void onConnectionSuspended(int i) {
        atualizador.cancela();
    }
}
