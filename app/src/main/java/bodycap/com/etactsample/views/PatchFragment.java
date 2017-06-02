package bodycap.com.etactsample.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import bodycap.com.etactsample.R;
import bodycap.com.etactsample.models.DataModel;
import bodycap.com.etactsample.util.DataAdapter;
import bodycap.com.etactsample.util.GridSpacingItemDecoration;

/**
 * Created by Laurent on 24/05/2017.
 */

public class PatchFragment extends Fragment {

    public interface IPatchFragmentListener {
        void onFabClickListener();
    }
    private IPatchFragmentListener mListener ;

    private View mRoot ;
    private ImageView mFab ;
    private RecyclerView mRecyclerView;
    private DataAdapter adapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot =  inflater.inflate(R.layout.activity_patch, container, false);

        mFab = (ImageView) mRoot.findViewById(R.id.shutdown);
        mRecyclerView = (RecyclerView)mRoot.findViewById(R.id.data_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFabClickListener();
            }
        });


        return mRoot ;
    }

    public void updateDatas(ArrayList<DataModel> models){
        adapter = new DataAdapter(models);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (IPatchFragmentListener)context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement IPatchFragmentListener");
        }
    }
}
