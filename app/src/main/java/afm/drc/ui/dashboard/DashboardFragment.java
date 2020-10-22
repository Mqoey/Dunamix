package afm.drc.ui.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import afm.drc.bible.BibleActivity;
import afm.drc.bible_quiz.MyActivity;
import afm.drc.dunamix.R;
import afm.drc.online_payments.OnlinePaymentsActivity;
import afm.drc.ui.gallery.GalleryFragment;

public class DashboardFragment extends Fragment {

    Button bible, bible_quiz, onlinePayments,gallery,facebook, youtube;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        bible = root.findViewById(R.id.bible);
        bible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BibleActivity.class);
                startActivity(intent);
            }
        });

        bible_quiz = root.findViewById(R.id.bible_quiz);
        bible_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyActivity.class);
                startActivity(intent);
            }
        });

        onlinePayments = root.findViewById(R.id.online_payments);
        onlinePayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OnlinePaymentsActivity.class);
                startActivity(intent);
            }
        });

        gallery = root.findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GalleryFragment.class);
                startActivity(intent);
            }
        });

        facebook = root.findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(getOpenFacebookIntent());
            }
        });

        youtube = root.findViewById(R.id.youtube);
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW,   Uri.parse("http://www.youtube.com/channel/UCKli7uuKU05rs4696WwZrRw")));
            }
        });

        return root;
    }

    public Intent getOpenFacebookIntent() {
        try {
            getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/dunamis.revival.9"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/dunamis.revival.9"));
        }
    }
}