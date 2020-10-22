package afm.drc.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import afm.drc.dunamix.R;

//import com.bumptech.glide.Glide;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private Context mContext;
    private List<GalleryModel> galleryModelList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title2);
            count = view.findViewById(R.id.count);
            thumbnail =  view.findViewById(R.id.thumbnail);
            linearLayout = view.findViewById(R.id.linearLayout);
        }
    }

    public GalleryAdapter(Context mContext, List<GalleryModel> galleryModelList) {
        this.mContext = mContext;
        this.galleryModelList = galleryModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        GalleryModel galleryModel = galleryModelList.get(position);
        holder.title.setText(galleryModel.getName());
        holder.count.setText(galleryModel.getDescription());
      //  holder.thumbnail.setImageResource(galleryModel.getImageURL());

        // loading galleryModel cover using Glide library
        Glide.with(mContext).load(galleryModel.getImageURL()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return galleryModelList.size();
    }
}