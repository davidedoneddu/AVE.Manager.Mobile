package it.sal.disco.unimib.avemanager.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.activity.EventMainActivity;
import it.sal.disco.unimib.avemanager.ui.model.Evento;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final List<Evento> eventList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Evento event);
    }

    public EventAdapter(List<Evento> events, OnItemClickListener listener) {
        this.eventList = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usa il layout xml che hai creato, ad esempio event_item.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_title_description, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Evento event = eventList.get(position);
        holder.titleTextView.setText(event.getName());
        holder.descriptionTextView.setText(event.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, EventMainActivity.class);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewName);
            descriptionTextView = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
