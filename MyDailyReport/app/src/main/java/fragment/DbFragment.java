package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laizexin.mydailyreport.R;

/**
 * Created by laizexin on 2017/9/12.
 */

public class DbFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    private View v;

    public static DbFragment newInstance(int position) {
        DbFragment fragment = new DbFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_db,container,false);
        return v;
    }
}
