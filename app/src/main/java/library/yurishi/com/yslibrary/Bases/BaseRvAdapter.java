package library.yurishi.com.yslibrary.Bases;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import library.yurishi.com.yslibrary.Interfaces.IRvAdapterItemClickListener;

public abstract class BaseRvAdapter<Model> extends RecyclerView.Adapter {

    protected List<Model> list_data = new ArrayList<>();
    protected Context context;

    public IRvAdapterItemClickListener clickListener;

    public void setClickListener(IRvAdapterItemClickListener listener) {
        this.clickListener = listener;
    }

    public void setDataList(List<Model> models) {
        this.setContainerList(models);
    }

    protected void setContainerList(List<Model> list_data) {
        this.list_data.clear();
        this.list_data.addAll(list_data);
        notifyThisAdapterData();
    }

    private void notifyThisAdapterData() {
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        int size = list_data == null ? 0 : list_data.size();
        return size;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        abOnBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        final RecyclerView.ViewHolder viewHolder = abOnCreateViewHolder(parent, viewType);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null)
                    clickListener.onItemClick(viewHolder.getAdapterPosition());
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (clickListener != null) {
                    clickListener.onItemLongClick(viewHolder.getAdapterPosition());
                    return true;
                } else
                    return false;
            }
        });
        return viewHolder;
    }

    protected abstract void abOnBindViewHolder(RecyclerView.ViewHolder holder, int position);

    protected abstract RecyclerView.ViewHolder abOnCreateViewHolder(ViewGroup parent, int viewType);

    public List<Model> getDataList() {
        return this.list_data == null ? new ArrayList<Model>() : list_data;
    }
}

