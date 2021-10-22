package com.example.officediary.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.officediary.R;
import com.example.officediary.model.Employer;

import java.util.ArrayList;
import java.util.List;

public class EmployersAdapter extends RecyclerView.Adapter<EmployersAdapter.EmployerViewHolder> {

    private final Context mContext;
    private List<Employer> employerList;
    private CardClickListener cardClickListener;

    public EmployersAdapter(Context context, List<Employer> employers, CardClickListener cardClickListener){
        this.mContext = context;
        this.employerList = employers;
        this.cardClickListener = cardClickListener;
    }

    @NonNull
    @Override
    public EmployerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new EmployerViewHolder(view, cardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployerViewHolder holder, int position) {
        if(employerList!=null && employerList.size()>0){
            if(!TextUtils.isEmpty(employerList.get(position).getName())) holder.name.setText(employerList.get(position).getName());
            if(!TextUtils.isEmpty(employerList.get(position).getEmail())) holder.email.setText(employerList.get(position).getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return employerList != null ? employerList.size() : 0;
    }

    public static class EmployerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private TextView email;
        private CardView cardView;
        private CardClickListener mCardClickListener;

        public EmployerViewHolder(@NonNull View itemView, CardClickListener cardClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.emp_name);
            email = itemView.findViewById(R.id.emp_email);
            cardView = itemView.findViewById(R.id.card_item);
            this.mCardClickListener = cardClickListener;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCardClickListener.onCardItemClick(getAdapterPosition());
        }
    }

    public void setEmployersList(List<Employer> employersList){
        this.employerList = new ArrayList<>(employersList);
        notifyDataSetChanged();
    }

    public interface CardClickListener {
        void onCardItemClick(int position);
    }

}
