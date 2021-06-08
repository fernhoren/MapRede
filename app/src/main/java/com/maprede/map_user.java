  package com.maprede;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link map_user#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class map_user extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    FloatingActionButton a_btn_menu_pr, b_btn_pr_atual, c_btn_pr_geon_edit, d_btn_pr_des, e_btn_pr_colec,
            a_btn_walk_talkie, a_btn_menu_map_rec,a_btn_menu_map_pause,a_btn_menu_map_stop,c_btn_menu_map_first_geon_mais,
            c_btn_menu_map_first_geon_menos,c_btn_menu_map_first_geon_drag,d_btn_menu_map_edit_shape,d_btn_menu_map_edit_circle,
            d_btn_menu_map_edit_erase,e_btn_menu_map_colec_list, a_a_geon_geonota, a_a_geon_loc, a_a_geon_foto, a_a_geon_video, a_a_geon_audio, a_a_geon_text, a_a_setting_map;
    Float translationYaxis = 100f;
    Float translationXaxis = 100f;
    Float rotationZaxis = 90f;
    Boolean menuOpen = false;
    Boolean menuOpen2 = false;
    Boolean menuOpen3 = false;
    Boolean menuOpen4 = false;
    Boolean menuOpen5 = false;
    private boolean locationPermissionGranted;



    OvershootInterpolator interpolator = new OvershootInterpolator();

  /*  // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment map_user.
     */
    /* TODO: Rename and change types and number of parameters
    public static map_user newInstance(String param1, String param2) {
        map_user fragment = new map_user();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/




    public map_user() {

        // Required empty public constructor
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map_user, container, false);
        ShowMenu();
        ShowMenu2();
        return  mView;
    }

    private void ShowMenu2(){
        a_a_geon_geonota = mView.findViewById(R.id.geonotas_prin);
        a_a_geon_loc = mView.findViewById(R.id.geonotas_loc);;
        a_a_geon_foto = mView.findViewById(R.id.geonotas_photo);;
        a_a_geon_video = mView.findViewById(R.id.geonotas_video);;
        a_a_geon_audio = mView.findViewById(R.id.geonotas_audio);;
        a_a_geon_text = mView.findViewById(R.id.geonotas_texto);;
        a_a_setting_map = mView.findViewById(R.id.geonotas_setting);;

        a_a_geon_loc.setAlpha(0f);
        a_a_geon_foto.setAlpha(0f);
        a_a_geon_video.setAlpha(0f);
        a_a_geon_audio.setAlpha(0f);
        a_a_geon_text.setAlpha(0f);
        a_a_setting_map.setAlpha(0f);

        a_a_geon_loc.setTranslationX(translationXaxis);
        a_a_geon_foto.setTranslationX(translationXaxis);
        a_a_geon_video.setTranslationX(translationXaxis);
        a_a_geon_audio.setTranslationX(translationXaxis);
        a_a_geon_text.setTranslationX(translationXaxis);
        a_a_setting_map.setTranslationX(translationXaxis);

        a_a_geon_loc.setVisibility(View.INVISIBLE);
        a_a_geon_foto.setVisibility(View.INVISIBLE);
        a_a_geon_video.setVisibility(View.INVISIBLE);
        a_a_geon_audio.setVisibility(View.INVISIBLE);
        a_a_geon_text.setVisibility(View.INVISIBLE);
        a_a_setting_map.setVisibility(View.INVISIBLE);

        a_a_geon_geonota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuOpen5){
                    Closemenu5();
                }else{
                    Opemmenu5();
                    Closemenu();
                    Closemenu3();
                    Closemenu2();
                    Closemenu4();
                }
            }
        });

        a_a_geon_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"Marker criado",Toast.LENGTH_LONG).show();
                Closemenu5();
            }
        });

        a_a_geon_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"Camera ativada",Toast.LENGTH_LONG).show();
                Closemenu5();
            }
        });

        a_a_geon_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"Video ativado",Toast.LENGTH_LONG).show();
                Closemenu5();
            }
        });

        a_a_geon_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"Audio ativado",Toast.LENGTH_LONG).show();
                Closemenu5();
            }
        });

        a_a_geon_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"Camera ativada",Toast.LENGTH_LONG).show();
                Closemenu5();
            }
        });

        a_a_geon_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"Texto ativado",Toast.LENGTH_LONG).show();
                Closemenu5();
            }
        });

        a_a_setting_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"Setting do projeto",Toast.LENGTH_LONG).show();
                Closemenu5();
            }
        });
    }

    private void Closemenu5() {
        menuOpen5 =! menuOpen5;
        a_a_geon_geonota.setImageResource(R.drawable.ic_btn_pr_geonotas);
        a_a_geon_loc.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        a_a_geon_foto.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        a_a_geon_video.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        a_a_geon_audio.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        a_a_geon_text.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        a_a_setting_map.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        a_a_geon_loc.setVisibility(View.INVISIBLE);
        a_a_geon_foto.setVisibility(View.INVISIBLE);
        a_a_geon_video.setVisibility(View.INVISIBLE);
        a_a_geon_audio.setVisibility(View.INVISIBLE);
        a_a_geon_text.setVisibility(View.INVISIBLE);
        a_a_setting_map.setVisibility(View.INVISIBLE);


    }


    private void Opemmenu5() {
        menuOpen5 =! menuOpen5;
        a_a_geon_geonota.setImageResource(R.drawable.ic_btn_pr_geonotas);
        a_a_geon_loc.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        a_a_geon_foto.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        a_a_geon_video.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        a_a_geon_audio.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        a_a_geon_text.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        a_a_setting_map.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        a_a_geon_loc.setVisibility(View.VISIBLE);
        a_a_geon_foto.setVisibility(View.VISIBLE);
        a_a_geon_video.setVisibility(View.VISIBLE);
        a_a_geon_audio.setVisibility(View.VISIBLE);
        a_a_geon_text.setVisibility(View.VISIBLE);
        a_a_setting_map.setVisibility(View.VISIBLE);


    }

    private void ShowMenu() {
        a_btn_menu_pr= mView.findViewById(R.id.menu_map_first);
        b_btn_pr_atual= mView.findViewById(R.id.menu_map_first_loc_atual);
        c_btn_pr_geon_edit= mView.findViewById(R.id.menu_map_first_geon_edit);
        d_btn_pr_des= mView.findViewById(R.id.menu_map_first_des);
        e_btn_pr_colec= mView.findViewById(R.id.menu_map_first_colect);

        a_btn_walk_talkie=mView.findViewById(R.id.menu_map_first_walkie);
        a_btn_menu_map_rec=mView.findViewById(R.id.menu_map_rec);
        a_btn_menu_map_pause=mView.findViewById(R.id.menu_map_pause);
        a_btn_menu_map_stop=mView.findViewById(R.id.menu_map_stop);

        c_btn_menu_map_first_geon_mais=mView.findViewById(R.id.menu_map_first_geon_mais);
        c_btn_menu_map_first_geon_menos=mView.findViewById(R.id.menu_map_first_geon_menos);
        c_btn_menu_map_first_geon_drag=mView.findViewById(R.id.menu_map_first_geon_drag);

        d_btn_menu_map_edit_shape=mView.findViewById(R.id.menu_map_edit_shape);
        d_btn_menu_map_edit_circle=mView.findViewById(R.id.menu_map_edit_circle);
        d_btn_menu_map_edit_erase=mView.findViewById(R.id.menu_map_edit_erase);

        e_btn_menu_map_colec_list=mView.findViewById(R.id.menu_map_colec_list);

        a_btn_walk_talkie.setAlpha(0f);
        a_btn_menu_map_rec.setAlpha(0f);
        a_btn_menu_map_pause.setAlpha(0f);
        a_btn_menu_map_stop.setAlpha(0f);

        b_btn_pr_atual.setAlpha(0f);
        c_btn_pr_geon_edit.setAlpha(0f);
        d_btn_pr_des.setAlpha(0f);
        e_btn_pr_colec.setAlpha(0f);

        c_btn_menu_map_first_geon_mais.setAlpha(0f);
        c_btn_menu_map_first_geon_menos.setAlpha(0f);
        c_btn_menu_map_first_geon_drag.setAlpha(0f);

        d_btn_menu_map_edit_shape.setAlpha(0f);
        d_btn_menu_map_edit_circle.setAlpha(0f);
        d_btn_menu_map_edit_erase.setAlpha(0f);

        e_btn_menu_map_colec_list.setAlpha(0f);

        b_btn_pr_atual.setTranslationY(translationYaxis);
        c_btn_pr_geon_edit.setTranslationY(translationYaxis);
        d_btn_pr_des.setTranslationY(translationYaxis);
        e_btn_pr_colec.setTranslationY(translationYaxis);

        a_btn_walk_talkie.setTranslationX(-10f);
        a_btn_menu_map_rec.setTranslationX(translationXaxis);
        a_btn_menu_map_pause.setTranslationX(translationXaxis);
        a_btn_menu_map_stop.setTranslationX(translationXaxis);


        a_btn_walk_talkie.setVisibility(View.INVISIBLE);
        a_btn_menu_map_rec.setVisibility(View.INVISIBLE);
        a_btn_menu_map_pause.setVisibility(View.INVISIBLE);
        a_btn_menu_map_stop.setVisibility(View.INVISIBLE);
        b_btn_pr_atual.setVisibility(View.INVISIBLE);
        c_btn_pr_geon_edit.setVisibility(View.INVISIBLE);
        d_btn_pr_des.setVisibility(View.INVISIBLE);
        e_btn_pr_colec.setVisibility(View.INVISIBLE);
        c_btn_menu_map_first_geon_mais.setVisibility(View.INVISIBLE);
        c_btn_menu_map_first_geon_menos.setVisibility(View.INVISIBLE);
        c_btn_menu_map_first_geon_drag.setVisibility(View.INVISIBLE);
        d_btn_menu_map_edit_shape.setVisibility(View.INVISIBLE);
        d_btn_menu_map_edit_circle.setVisibility(View.INVISIBLE);
        d_btn_menu_map_edit_erase.setVisibility(View.INVISIBLE);
        e_btn_menu_map_colec_list.setVisibility(View.INVISIBLE);

        a_btn_menu_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuOpen){
                    Closemenu();
                    Closemenu3();
                    Closemenu2();
                    Closemenu4();
                    zoom_compass_disable();
                }else{
                    Opemmenu();
                    Closemenu5();
                    zoom_compass_enable();
                }
            }
        });

        a_btn_walk_talkie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"Walkie Talkie",Toast.LENGTH_LONG).show();
            }
        });


        a_btn_menu_map_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"Iniciou gravação do percurso",Toast.LENGTH_LONG).show();
            }
        });

        a_btn_menu_map_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"Pausou a gravação do percurso",Toast.LENGTH_LONG).show();
            }
        });

        a_btn_menu_map_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"Parou a gravação",Toast.LENGTH_LONG).show();
            }
        });

        b_btn_pr_atual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(),"localização atual",Toast.LENGTH_LONG).show();

            }
        });

        c_btn_pr_geon_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuOpen2){
                    Toast.makeText(mView.getContext(), "Edição Geonotas", Toast.LENGTH_SHORT).show();
                    Closemenu2();
                }else{
                    Openmenu2();
                    Closemenu3();
                    Closemenu4();
                }
            }
        });

        c_btn_menu_map_first_geon_mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(), "adicionar un marker", Toast.LENGTH_SHORT).show();
            }
        });

        c_btn_menu_map_first_geon_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(), "Apagar um marker", Toast.LENGTH_SHORT).show();
            }
        });

        c_btn_menu_map_first_geon_drag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mView.getContext(), "Mover um marker", Toast.LENGTH_SHORT).show();
            }
        });

        d_btn_pr_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuOpen3){
                    Toast.makeText(mView.getContext(), "Desenho", Toast.LENGTH_SHORT).show();
                    Closemenu3();
                }else{
                    Openmenu3();
                    Closemenu2();
                    Closemenu4();
                }
            }
        });

        e_btn_pr_colec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuOpen4){
                    Closemenu4();
                    Toast.makeText(mView.getContext(), "Coletivo", Toast.LENGTH_SHORT).show();
                }else{
                    Openmenu4();
                    Closemenu2();
                    Closemenu3();
                }
            }
        });
   }


    private void zoom_compass_disable() {
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
    }

    private void zoom_compass_enable() {
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);

    }

    private void Openmenu4() {
        menuOpen4 =! menuOpen4;
        e_btn_pr_colec.setImageResource(R.drawable.ic_btn_sub_pr_colectivo);
        e_btn_menu_map_colec_list.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        e_btn_menu_map_colec_list.setVisibility(View.VISIBLE);
    }

    private void Closemenu4() {
        menuOpen4 =! menuOpen4;
        e_btn_pr_colec.setImageResource(R.drawable.ic_btn_sub_pr_colectivo);
        e_btn_menu_map_colec_list.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        e_btn_menu_map_colec_list.setVisibility(View.INVISIBLE);
    }

    private void Openmenu3() {
        menuOpen3 =! menuOpen3;
        d_btn_pr_des.setImageResource(R.drawable.ic_btn_sub_pr_desenho);
        d_btn_menu_map_edit_shape.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        d_btn_menu_map_edit_circle.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        d_btn_menu_map_edit_erase.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        d_btn_menu_map_edit_shape.setVisibility(View.VISIBLE);
        d_btn_menu_map_edit_circle.setVisibility(View.VISIBLE);
        d_btn_menu_map_edit_erase.setVisibility(View.VISIBLE);
    }


    private void Closemenu3() {
        menuOpen3 =! menuOpen3;
        d_btn_pr_des.setImageResource(R.drawable.ic_btn_sub_pr_desenho);
        d_btn_menu_map_edit_shape.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        d_btn_menu_map_edit_circle.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        d_btn_menu_map_edit_erase.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        d_btn_menu_map_edit_shape.setVisibility(View.INVISIBLE);
        d_btn_menu_map_edit_circle.setVisibility(View.INVISIBLE);
        d_btn_menu_map_edit_erase.setVisibility(View.INVISIBLE);
    }


    private void Openmenu2() {
        menuOpen2 =! menuOpen2;
        c_btn_pr_geon_edit.setImageResource(R.drawable.ic_btn_sub_pr_geonotas_edit);
        c_btn_menu_map_first_geon_mais.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        c_btn_menu_map_first_geon_menos.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        c_btn_menu_map_first_geon_drag.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        c_btn_menu_map_first_geon_mais.setVisibility(View.VISIBLE);
        c_btn_menu_map_first_geon_menos.setVisibility(View.VISIBLE);
        c_btn_menu_map_first_geon_drag.setVisibility(View.VISIBLE);
    }

    private void Closemenu2() {
        menuOpen2 =! menuOpen2;
        c_btn_pr_geon_edit.setImageResource(R.drawable.ic_btn_sub_pr_geonotas_edit);
        c_btn_menu_map_first_geon_mais.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        c_btn_menu_map_first_geon_menos.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        c_btn_menu_map_first_geon_drag.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        c_btn_menu_map_first_geon_mais.setVisibility(View.INVISIBLE);
        c_btn_menu_map_first_geon_menos.setVisibility(View.INVISIBLE);
        c_btn_menu_map_first_geon_drag.setVisibility(View.INVISIBLE);
    }

    private boolean Opemmenu() {
        menuOpen =! menuOpen;
        a_btn_menu_pr.setImageResource(R.drawable.ic_btn_pr_float_mais);
        a_btn_menu_pr.animate().rotation(45f).alpha(0.5f).setInterpolator(interpolator).setDuration(300).start();
        a_btn_walk_talkie.animate().translationX(10f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        a_btn_menu_map_rec.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        a_btn_menu_map_pause.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        a_btn_menu_map_stop.animate().translationX(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        b_btn_pr_atual.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        c_btn_pr_geon_edit.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        d_btn_pr_des.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        e_btn_pr_colec.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        a_btn_walk_talkie.setVisibility(View.VISIBLE);
        a_btn_menu_map_rec.setVisibility(View.VISIBLE);
        a_btn_menu_map_pause.setVisibility(View.VISIBLE);
        a_btn_menu_map_stop.setVisibility(View.VISIBLE);
        b_btn_pr_atual.setVisibility(View.VISIBLE);
        c_btn_pr_geon_edit.setVisibility(View.VISIBLE);
        d_btn_pr_des.setVisibility(View.VISIBLE);
        e_btn_pr_colec.setVisibility(View.VISIBLE);
        return true;
    }

    private void Closemenu() {
        menuOpen =! menuOpen;
        a_btn_menu_pr.setImageResource(R.drawable.ic_btn_pr_float_mais);
        a_btn_menu_pr.animate().rotation(rotationZaxis).alpha(0.5f).setInterpolator(interpolator).setDuration(300).start();
        a_btn_walk_talkie.animate().translationX(-10f).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        a_btn_menu_map_rec.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        a_btn_menu_map_pause.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        a_btn_menu_map_stop.animate().translationX(translationXaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        b_btn_pr_atual.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        c_btn_pr_geon_edit.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        d_btn_pr_des.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        e_btn_pr_colec.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        a_btn_walk_talkie.setVisibility(View.INVISIBLE);
        a_btn_menu_map_rec.setVisibility(View.INVISIBLE);
        a_btn_menu_map_pause.setVisibility(View.INVISIBLE);
        a_btn_menu_map_stop.setVisibility(View.INVISIBLE);
        b_btn_pr_atual.setVisibility(View.INVISIBLE);
        c_btn_pr_geon_edit.setVisibility(View.INVISIBLE);
        d_btn_pr_des.setVisibility(View.INVISIBLE);
        e_btn_pr_colec.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = mView.findViewById(R.id.map);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        int errorCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());
        switch (errorCode){
            case ConnectionResult.SERVICE_MISSING:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
                Log.d("Teste","show dialog");
                break;
            case ConnectionResult.SUCCESS:
                Log.d("Teste","Google Play Services está atualizado");

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        //mGoogleMap.getUiSettings().setCompassEnabled(true);

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mrk_loc_atual_express));
                markerOptions.title("lat " + latLng.latitude + " : lng " + latLng.longitude);

                //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
               // googleMap.addMarker(markerOptions);
                //googleMap.clear();

            }
        });


    }
}

