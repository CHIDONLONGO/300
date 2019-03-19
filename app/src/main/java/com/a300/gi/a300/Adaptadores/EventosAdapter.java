package com.a300.gi.a300.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a300.gi.a300.R;
import com.a300.gi.a300.entidades.EventClass;

import java.util.List;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.EventosHolder>implements View.OnClickListener {

    private List<EventClass> listaEventos;
    private View.OnClickListener listener;

    public EventosAdapter(List<EventClass> listaEventos) {
        this.listaEventos = listaEventos;
    }

    @NonNull
    @Override
    public EventosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.formato_evento_item, viewGroup, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);


        vista.setOnClickListener(this);
        return new EventosHolder(vista);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    };

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }

    }

    public class EventosHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtNombre, txtDirecccion, txtDescripcion, txtFecha;

        public EventosHolder(View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.TvIdEvento);
            txtNombre = (TextView) itemView.findViewById(R.id.TvNombreEvento);
            txtDirecccion = (TextView) itemView.findViewById(R.id.TvDireccion);
            txtDescripcion = (TextView) itemView.findViewById(R.id.TvDescripcion);
            txtFecha = (TextView) itemView.findViewById(R.id.TvFecha);


        }
    }

    @Override
    public void onBindViewHolder(@NonNull EventosHolder eventosHolder, int position) {

        eventosHolder.txtId.setText(listaEventos.get(position).getId().toString());
        eventosHolder.txtNombre.setText(listaEventos.get(position).getName().toString());
        eventosHolder.txtDirecccion.setText(listaEventos.get(position).getAdress().toString());
        eventosHolder.txtDescripcion.setText(listaEventos.get(position).getDescription().toString());
        eventosHolder.txtFecha.setText(listaEventos.get(position).getDate().toString());


    }

    @Override
    public int getItemCount() {
        return listaEventos.size();
    }
}
