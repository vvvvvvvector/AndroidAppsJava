package com.example.androidjetpack;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    // attributes
    private final List<MainData> mainDataList;
    private final Activity context;
    RoomDB database;

    // constructor
    public MainAdapter(List<MainData> mainDataList, Activity context) {
        this.mainDataList = mainDataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MainData data = mainDataList.get(position);

        database = RoomDB.getInstance(context);

        holder.textView.setText(data.getText());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainData d = mainDataList.get(holder.getAdapterPosition());

                final int id = d.getId();
                String text = d.getText();

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);

                dialog.show();

                final EditText editText = dialog.findViewById(R.id.edit_text);
                Button updateButton = dialog.findViewById(R.id.update_button);

                editText.setText(text);

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String updatedText = editText.getText().toString().trim();
                        database.mainDao().update(id, updatedText);
                        mainDataList.clear();
                        mainDataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainData d = mainDataList.get(holder.getAdapterPosition());

                database.mainDao().delete(d);
                int position = holder.getAdapterPosition();
                mainDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mainDataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView editButton, removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.card_view_textview_id);
            editButton = itemView.findViewById(R.id.img_edit);
            removeButton = itemView.findViewById(R.id.img_remove);
        }
    }
}
