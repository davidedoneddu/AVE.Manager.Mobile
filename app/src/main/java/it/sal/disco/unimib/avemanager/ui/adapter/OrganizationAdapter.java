package it.sal.disco.unimib.avemanager.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.model.Organization;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.OrganizationViewHolder> {

    private final List<Organization> organizationList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Organization organization);
    }

    public OrganizationAdapter(List<Organization> list, OnItemClickListener listener) {
        this.organizationList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_image_title, parent, false);
        return new OrganizationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, int position) {
        Organization org = organizationList.get(position);
        holder.textView.setText(org.getName());
        Glide.with(holder.imageView.getContext())
                .load(org.getImageUrl())
                .placeholder(R.drawable.logo_welcome)  // immagine mostrata mentre carica
                .error(R.drawable.logo_welcome)        // immagine mostrata in caso di errore o URL null
                .into(holder.imageView);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(org));
    }

    @Override
    public int getItemCount() {
        return organizationList.size();
    }

    static class OrganizationViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public OrganizationViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewLogo);
            textView = itemView.findViewById(R.id.textViewName);
        }
    }
}
