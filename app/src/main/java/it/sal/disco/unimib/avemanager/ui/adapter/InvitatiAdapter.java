package it.sal.disco.unimib.avemanager.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import java.util.ArrayList;
import java.util.List;

import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.model.Invitato;
import it.sal.disco.unimib.avemanager.util.OnInvitatoActionListener;

public class InvitatiAdapter extends RecyclerView.Adapter<InvitatiAdapter.InvitatoViewHolder> {

    private final List<Invitato> invitati = new ArrayList<>();
    private int expandedPosition = RecyclerView.NO_POSITION;
    private boolean isAppending = false;
    private OnInvitatoActionListener actionListener;

    public void setOnInvitatoActionListener(OnInvitatoActionListener listener) {
        this.actionListener = listener;
    }

    @NonNull
    @Override
    public InvitatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invitato_list_item, parent, false);
        return new InvitatoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InvitatoViewHolder holder, int position) {
        Invitato invitato = invitati.get(position);
        holder.bind(invitato, position == expandedPosition, actionListener);

        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) return;

            if (expandedPosition == currentPosition) {
                int prev = expandedPosition;
                expandedPosition = RecyclerView.NO_POSITION;
                notifyItemChanged(prev);
            } else {
                int prev = expandedPosition;
                expandedPosition = currentPosition;
                notifyItemChanged(prev);
                notifyItemChanged(expandedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return invitati.size();
    }

    public void submitList(List<Invitato> newList, boolean append) {
        isAppending = append;

        if (!append) {
            invitati.clear();
            expandedPosition = RecyclerView.NO_POSITION;
            invitati.addAll(newList);
            notifyDataSetChanged();
        } else {
            int startPos = invitati.size();
            invitati.addAll(newList);
            notifyItemRangeInserted(startPos, newList.size());
        }
    }

    public Invitato getItem(int position) {
        if (position >= 0 && position < invitati.size()) {
            return invitati.get(position);
        } else {
            return null;
        }
    }

    static class InvitatoViewHolder extends RecyclerView.ViewHolder {

        private final TextView nomeTextView;
        private final TextView aziendaTextView;
        private final ConstraintLayout actionsLayout;
        private final ImageView arrowExpand;
        private final ImageView checkInIcon;
        private final ImageView btnEdit;
        private final ImageView deleteBtn;
        private final ImageView mailBtn;

        public InvitatoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeTextView);
            aziendaTextView = itemView.findViewById(R.id.aziendaTextView);
            checkInIcon = itemView.findViewById(R.id.avatarImage);
            actionsLayout = itemView.findViewById(R.id.actionsLayout);
            arrowExpand = itemView.findViewById(R.id.arrowExpand);
            btnEdit = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            mailBtn = itemView.findViewById(R.id.mailBtn);
        }

        void bind(Invitato invitato, boolean isExpanded, OnInvitatoActionListener editClickListener) {
            String nomeCompleto = (invitato.getInvNome() != null ? invitato.getInvNome() : "")
                    + " " + (invitato.getInvCognome() != null ? invitato.getInvCognome() : "");
            nomeTextView.setText(nomeCompleto.trim());

            aziendaTextView.setText(invitato.getInvRagSoc() != null ? invitato.getInvRagSoc() : "Azienda non definita");


            if(Boolean.TRUE.equals(invitato.getInvPresente())){
                checkInIcon.setImageResource(R.drawable.ic_circle_success);
                checkInIcon.setColorFilter(R.color.md_theme_success);
            } else {
                checkInIcon.setImageResource(R.drawable.ic_circle_error);
                checkInIcon.setColorFilter(R.color.md_theme_error);
            }

            btnEdit.setOnClickListener(v -> {
                if (editClickListener != null) {
                    editClickListener.onEditClick(invitato);
                }
            });

            deleteBtn.setOnClickListener(v -> {
                if (editClickListener != null) editClickListener.onDeleteClick(invitato);
            });

            mailBtn.setOnClickListener(v -> {
                if (editClickListener != null) editClickListener.onSendEmailClick(invitato);
            });
            TransitionManager.beginDelayedTransition((ViewGroup) itemView);
            arrowExpand.animate().rotation(isExpanded ? 270f : 0f).start();
            actionsLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        }
    }
}
