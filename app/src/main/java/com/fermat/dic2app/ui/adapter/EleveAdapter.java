package com.fermat.dic2app.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.fermat.dic2app.R;
import com.fermat.dic2app.models.Eleve;

public class EleveAdapter extends ListAdapter<Eleve, EleveAdapter.ViewHolder> {
    // listener
    private OnItemClickListener listener;

    // constructeur
    public EleveAdapter() {
        super(DIFF_CALLBACK);
    }

    // callback
    private static final DiffUtil.ItemCallback<Eleve> DIFF_CALLBACK = new DiffUtil.ItemCallback<Eleve>() {
        @Override
        public boolean areItemsTheSame(Eleve oldItem, Eleve newItem) {
            // verification unicité
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Eleve oldItem, Eleve newItem) {
            // verification unicité
            return oldItem.getPrenom().equals(newItem.getPrenom()) &&
                    oldItem.getNom().equals(newItem.getNom()) &&
                    oldItem.getClasse().equals(newItem.getClasse());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflater
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eleve_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // recuperation de l'element visuel
        Eleve model = getEleve(position);
        holder.tvPrenom.setText(model.getPrenom());
        holder.tvNom.setText(model.getNom());
        holder.tvClasse.setText(model.getClasse());
        holder.labelPrenom.setText(R.string.label_prenom);
        holder.labelNom.setText(R.string.label_nom);
        holder.labelClasse.setText(R.string.label_classe);
        byte[] bytes = model.getAvatar();
        Bitmap bm = BitmapFactory.decodeByteArray(bytes , 0, bytes .length);
        if (bm != null){
            holder.ivAvatar.setImageBitmap(bm);
        }else{
            holder.ivAvatar.setImageResource(R.drawable.logo_icon);
        }

    }

    // recuperation eleve par position (liste affichée)
    public Eleve getEleve(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // textviews
        TextView tvPrenom, tvNom, tvClasse, labelPrenom, labelNom, labelClasse;
        ImageView ivAvatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // matching
            tvPrenom = itemView.findViewById(R.id.idElevePrenom);
            tvNom = itemView.findViewById(R.id.idEleveNom);
            tvClasse = itemView.findViewById(R.id.idEleveClasse);
            labelPrenom = itemView.findViewById(R.id.idlabelPrenom);
            labelClasse = itemView.findViewById(R.id.idlabelClasse);
            labelNom = itemView.findViewById(R.id.idlabelNom);
            ivAvatar = itemView.findViewById(R.id.idAvatar);
            // listener
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Eleve model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


