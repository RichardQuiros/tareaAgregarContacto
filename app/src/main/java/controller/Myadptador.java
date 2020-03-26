package controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tareaagregarcontacto.R;

import java.util.ArrayList;

import model.Usuario;

public class Myadptador extends BaseAdapter {
    private ArrayList<Usuario> arrayList = new ArrayList<>();
    private Context context;
    private int celda;

    public Myadptador(ArrayList<Usuario> arrayList, Context context, int celda) {
        this.arrayList = arrayList;
        this.context = context;
        this.celda = celda;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(celda,null);

        ImageView imageView = view.findViewById(R.id.celda1PerfilImv);
        TextView nombre = view.findViewById(R.id.celda1NombreTv);
        TextView telefono = view.findViewById(R.id.celdaTelefonoTv);
        TextView email = view.findViewById(R.id.celda1EmailTv);

        imageView.setImageResource(arrayList.get(position).getImgurl());
        nombre.setText(arrayList.get(position).getNombre()+" "+arrayList.get(position).getApellido());
        telefono.setText(arrayList.get(position).getTelefono());
        email.setText(arrayList.get(position).getEmail());
        return view;
    }
}
