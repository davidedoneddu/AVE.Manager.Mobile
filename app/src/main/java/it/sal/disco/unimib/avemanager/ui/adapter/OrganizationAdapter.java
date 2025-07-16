package it.sal.disco.unimib.avemanager.ui.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.model.Organization;
import it.sal.disco.unimib.avemanager.util.DataCallback;
import it.sal.disco.unimib.avemanager.util.ImageLoader;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.OrganizationViewHolder> {

    private final List<Organization> organizationList;
    private final OnItemClickListener listener;
    private final ImageLoader imageLoader;

    public interface OnItemClickListener {
        void onItemClick(Organization organization);
    }

    public OrganizationAdapter(List<Organization> list, OnItemClickListener listener,ImageLoader imageLoader) {
        this.organizationList = list;
        this.listener = listener;
        this.imageLoader = imageLoader;
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
//        Glide.with(holder.imageView.getContext())
//                .load(Costants.BASE_PATH + org.getImageUrl())
//                .placeholder(R.drawable.logo_welcome)  // immagine mostrata mentre carica
//                .error(R.drawable.logo_welcome)        // immagine mostrata in caso di errore o URL null
//                .into(holder.imageView);
        holder.imageView.setImageResource(R.drawable.logo_welcome);  // placeholder

        imageLoader.loadImage(org.getId(), new DataCallback<Bitmap>() {
            @Override
            public void onSuccess(Bitmap image) {
                holder.imageView.post(() -> holder.imageView.setImageBitmap(image));
            }

            @Override
            public void onFailure(Throwable t) {
                holder.imageView.post(() -> holder.imageView.setImageResource(R.drawable.logo_welcome));
            }
        });

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
