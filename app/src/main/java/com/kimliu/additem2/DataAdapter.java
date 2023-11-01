package com.kimliu.additem2;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int mSelectedPosition = -1 ;

    private static final int ITEM_NORMAL = 0;
    private static final int ITEM_ADD = 1;
    private int selectMax = 9;

    private Context mContext;

    private List<String> mDataList;

    public DataAdapter(Context context, List<String> dataList){
        mContext = context;
        mDataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_NORMAL) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_dev_default_name, parent, false);
            return new NormalViewHolder(itemView);
        } else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add, parent, false);
            return new AddViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int mPosition = position;
        if(holder instanceof NormalViewHolder){
            ((NormalViewHolder) holder).bindData(mDataList.get(position));
            ((NormalViewHolder) holder).tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mDataList.remove(mPosition);
                    notifyDataSetChanged();
                    return false;
                }
            });
            if(mSelectedPosition == position){
                ((NormalViewHolder) holder).tv.setTextColor(Color.RED);
            }else{
                ((NormalViewHolder) holder).tv.setTextColor(Color.GRAY);
            }
        }else if(holder instanceof AddViewHolder){
            ((AddViewHolder) holder).add.setOnClickListener(new View.OnClickListener() {

                private AlertDialog mAlertDialog;

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    View dlgView = LayoutInflater.from(mContext).inflate(R.layout.dlg_add, null);
                    builder.setView(dlgView);
                    EditText editText = dlgView.findViewById(R.id.et);
                    Button ok = dlgView.findViewById(R.id.ok);
                    Button cancle = dlgView.findViewById(R.id.cancel);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String input = editText.getText().toString().trim();
                            mDataList.add(input);
                            notifyDataSetChanged();
                            if(mAlertDialog != null){
                                mAlertDialog.dismiss();
                            }
                        }
                    });

                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mAlertDialog != null){
                                mAlertDialog.dismiss();
                            }
                        }
                    });


                    mAlertDialog = builder.create();
                    mAlertDialog.show();

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(mDataList.size() < selectMax){
            return mDataList.size() + 1;
        }else {
            return mDataList.size();
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(isShowAddItem(position)){
            return ITEM_ADD;
        }else {
            return ITEM_NORMAL;
        }
    }

    private boolean isShowAddItem(int position) {
        return position == mDataList.size();
    }





    public class NormalViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != mSelectedPosition){
                        mSelectedPosition = position;
                        notifyDataSetChanged();
                    }
                }
            });
        }

        public void bindData(String data){
            tv.setText(data);
        }
    }


    public class AddViewHolder extends RecyclerView.ViewHolder{

        private ImageView add;

        public AddViewHolder(@NonNull View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.add);
        }

    }
}
