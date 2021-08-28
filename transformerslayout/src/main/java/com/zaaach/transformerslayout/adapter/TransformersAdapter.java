package com.zaaach.transformerslayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zaaach.transformerslayout.holder.Holder;
import com.zaaach.transformerslayout.holder.TransformersHolderCreator;
import com.zaaach.transformerslayout.listener.OnTransformersItemClickListener;

import java.util.List;

/**
 * @Author: Zaaach
 * @Date: 2019/11/22
 * @Email: zaaach@aliyun.com
 * @Description:
 */
public class TransformersAdapter<T> extends RecyclerView.Adapter<Holder<T>> {
    private Context mContext;
    private List<T> mData;
    private TransformersHolderCreator<T> holderCreator;
    private RecyclerView mRecyclerView;
    private int mWidth;
    private int spanCount;
    private OnTransformersItemClickListener onTransformersItemClickListener;

    public void setOnTransformersItemClickListener(OnTransformersItemClickListener listener) {
        this.onTransformersItemClickListener = listener;
    }

    public TransformersAdapter(Context context, RecyclerView recyclerView){
        mContext = context;
        mRecyclerView = recyclerView;
    }

    public void setData(List<T> data){
        mData = data;
        notifyDataSetChanged();
    }

    public void onWidthChanged(int width){
        this.mWidth = width;
        notifyDataSetChanged();
    }

    public void setSpanCount(int spanCount){
        this.spanCount = spanCount;
    }

    public void setHolderCreator(TransformersHolderCreator<T> creator){
        holderCreator = creator;
    }

    @NonNull
    @Override
    public Holder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = holderCreator.getLayoutId();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        //每个item平分整个屏幕的宽度
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        mWidth = mWidth == 0 ? mRecyclerView.getMeasuredWidth() : mWidth;
        params.width = mWidth / spanCount;
        return holderCreator.createHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder<T> holder, final int position) {
        if (mData.get(position) == null){
            holder.itemView.setVisibility(View.INVISIBLE);
        }else {
            holder.itemView.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mData.get(position) != null) {
                    if (onTransformersItemClickListener != null) {
                        onTransformersItemClickListener.onItemClick(position);
                    }
                }
            }
        });
        holder.onBind(mContext, mData, mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
