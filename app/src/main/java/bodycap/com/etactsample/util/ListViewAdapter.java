package bodycap.com.etactsample.util;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bodycap.com.etactsample.R;

/**
 * Created by Laurent on 19/05/2017.
 */

public class ListViewAdapter extends ArrayAdapter<BluetoothDevice> {

    private ArrayList<BluetoothDevice> devices ;

    public ListViewAdapter(@NonNull Context context, @NonNull List<BluetoothDevice> objects) {
        super(context, 0, objects);
        this.devices = (ArrayList<BluetoothDevice>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent, false);
        }

        PatchViewHolder viewHolder = (PatchViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new PatchViewHolder();
            viewHolder.macAddress = (TextView) convertView.findViewById(R.id.macAddress);
            viewHolder.patchName = (TextView) convertView.findViewById(R.id.patchName);
            convertView.setTag(viewHolder);
        }

        BluetoothDevice device = getItem(position);


        viewHolder.macAddress.setText(device.getAddress());
        viewHolder.patchName.setText(device.getName());

        return convertView;
    }

    public void updateList(ArrayList<BluetoothDevice> devices) {
        this.devices = devices ;
    }

    private class PatchViewHolder{
        public TextView patchName;
        public TextView macAddress;
    }
}
