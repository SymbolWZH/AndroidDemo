package com.origin.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{
    private List<Msg> mMsgList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;

        TextView LeftMsg;
        TextView RightMsg;

        public ViewHolder(View view)
        {
            super(view);

            leftLayout = view.findViewById(R.id.left_layout);
            rightLayout = view.findViewById(R.id.right_layout);
            LeftMsg = view.findViewById(R.id.left_msg);
            RightMsg = view.findViewById(R.id.right_msg);
        }

    }

    public MsgAdapter(List<Msg> msgList){ mMsgList = msgList;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position ) {
        Msg msg = mMsgList.get(position);
        if (msg.getType() == Msg.TYPE_RECEIVED)
        {//如果是收到的消息，则显示左边消息的布局，将右边的消息隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.LeftMsg.setText(msg.getContent());
        }
        else if (msg.getType() == Msg.TYPE_SENT)
        {//如果是发出的消息，则隐藏左边消息的布局，将右边的消息显示
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.RightMsg.setText(msg.getContent());

        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
