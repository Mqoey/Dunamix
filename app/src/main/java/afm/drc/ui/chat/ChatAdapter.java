package afm.drc.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import afm.drc.dunamix.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatModel> messagesList;

    public ChatAdapter( List<ChatModel> messagesList) {
        this.messagesList = messagesList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, messages,time;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            this.name =  view.findViewById(R.id.convo_name);
            this.messages =  view.findViewById(R.id.convo_message);
            this.time =  view.findViewById(R.id.convo_time);
            linearLayout =  view.findViewById(R.id.linearLayout2);
        }
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ChatModel album = messagesList.get(position);
        holder.name.setText(album.getName());
        holder.messages.setText(album.getMessages());
        holder.time.setText(album.getTime());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }
}