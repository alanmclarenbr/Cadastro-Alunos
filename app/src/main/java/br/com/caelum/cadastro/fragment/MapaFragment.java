package br.com.caelum.cadastro.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.dao.AlunoDao;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.util.AtualizadorDeLocalizacao;
import br.com.caelum.cadastro.util.Localizador;

public class MapaFragment extends SupportMapFragment {
    private GoogleMap googleMap;

    @Override
    public void onResume() {
        super.onResume();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Localizador localizador = new Localizador(getActivity());
                AtualizadorDeLocalizacao atualizador = new AtualizadorDeLocalizacao(getActivity(), MapaFragment.this);

                MapaFragment.this.googleMap = googleMap;

                //MapaFragment.this.centralizaNo(local, googleMap);

                AlunoDao alunoDao = new AlunoDao(getActivity());
                List<Aluno> alunos = alunoDao.getAll();
                alunoDao.close();
                for(Aluno aluno : alunos){
                    LatLng coordenada = localizador.getCoordenada(aluno.getEndereco());

                    if(coordenada!=null){
                        MarkerOptions marcador = new MarkerOptions().position(coordenada)
                                .title(aluno.getNome()).snippet(aluno.getEndereco());
                        googleMap.addMarker(marcador);
                    }

                }
            }
        });

    }

    public void centralizaNo(LatLng local, GoogleMap mapa){
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 18));
    }

    public GoogleMap getGoogleMap(){
        return this.googleMap;
    }
}
