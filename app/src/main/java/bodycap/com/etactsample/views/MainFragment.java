package bodycap.com.etactsample.views;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import bodycap.com.etactsample.R;
import bodycap.com.etactsample.util.ListViewAdapter;


public class MainFragment extends Fragment {
    public interface IActivityFragmentListener {
        void onItemClickListener(int position);

        void onFabClickListener();
    }

    private IActivityFragmentListener mListener;

    private View mRoot;
    private ListView mList;
    private ListViewAdapter mAdapter;
    private FloatingActionButton mFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.activity_main, container, false);

        mList = (ListView) mRoot.findViewById(R.id.patchs_list);
        mFab = (FloatingActionButton) mRoot.findViewById(R.id.fab);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onItemClickListener(position);
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFabClickListener();
            }
        });


        return mRoot;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (IActivityFragmentListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement IActivityFragmentListener");
        }
    }

    public void updateListView(ArrayList<BluetoothDevice> devices) {
        if (devices.size() != 0) {
            if (mAdapter == null) {
                mAdapter = new ListViewAdapter(getContext(), devices);
                mList.setAdapter(mAdapter);
            } else mAdapter.updateList(devices);

            mAdapter.notifyDataSetChanged();
        }
    }
}
