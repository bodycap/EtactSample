package bodycap.com.etactsample.controls;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;

import bodycap.com.bluetoothetact.bodyException.BluetoothAdapterException;
import bodycap.com.bluetoothetact.bodyException.PermissionsException;
import bodycap.com.bluetoothetact.broadcasts.IBluetoothDataListener;
import bodycap.com.bluetoothetact.broadcasts.IBluetoothStatusBroadcast;
import bodycap.com.bluetoothetact.connection.EtactServiceConnection;
import bodycap.com.etactsample.R;
import bodycap.com.etactsample.models.DataModel;
import bodycap.com.etactsample.views.PatchFragment;
import bodycap.com.etactsample.views.PatchFragment.IPatchFragmentListener;


public class PatchActivity extends AppCompatActivity implements IBluetoothStatusBroadcast, IBluetoothDataListener, IPatchFragmentListener {

    private PatchFragment patchFragment;
    private EtactServiceConnection myBluetoothConnection;
    private BluetoothDevice mDevice;

    private ArrayList<DataModel> models;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_content);

        // Get the Intent that started this activity and extract the bluetooth device
        Intent intent = getIntent();
        mDevice = intent.getParcelableExtra(MainActivity.EXTRA_MESSAGE);


        patchFragment = new PatchFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, patchFragment);
        transaction.commit();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        try {
            myBluetoothConnection = new EtactServiceConnection(PatchActivity.this, bluetoothAdapter);
        } catch (BluetoothAdapterException e) {
            e.printStackTrace();
        } catch (PermissionsException e) {
            e.printStackTrace();
        }


        myBluetoothConnection.startConnection();

        models = new ArrayList<>();

    }

    @Override
    protected void onDestroy() {
        super.onPause();
        if (myBluetoothConnection != null) {
            myBluetoothConnection.disableDataNotification(mDevice.getAddress());
            myBluetoothConnection.stopConnection();
        }
    }

    @Override
    protected void onResume() {
        myBluetoothConnection.activateDataNotification(mDevice.getAddress());
        super.onResume();
    }

    ///////////////////////////////////////////////////////
    //                  OVERRIDE METHODS                 //
    ///////////////////////////////////////////////////////
    @Override
    public void rssiReceived(String macAddress, int rssi) {

    }

    @Override
    public void hardwareVersionReceived(String macAddress, String hardVersion) {

    }

    @Override
    public void manufacturerNameReceived(String macAddress, String manufacturerName) {

    }

    @Override
    public void firmwareVersionReceived(String macAddress, String firmwareVersion) {

    }

    @Override
    public void pnpIdReceived(String macAddress, String pnpId) {

    }

    @Override
    public void shutdownReceived(String macAddress) {
        if (macAddress.equals(mDevice.getAddress())) {
            finish();
        }
    }

    @Override
    public void patchNameReceived(String macAddress, String name) {

    }

    @Override
    public void patchDatasReceived(String macAddress, float battery, float temperature, float xAxis, float yAxis, float zAxis, float activity) {
        // UPDATE VIEW HERE !!!!!

        Log.d("SearchBluetoothEtact", "Datas received");

        models.clear();

        DecimalFormat df = new DecimalFormat("#.##");
        DataModel modelTemp = new DataModel(DataModel.TEMPERATURE, df.format(temperature) + "°C");
        DataModel modelAct = new DataModel(DataModel.ACTIVITY, String.valueOf((int) activity));
        DataModel modelBatt = new DataModel(DataModel.BATTERY, String.valueOf((int) battery) + "%");
        DataModel modelAxes = new DataModel(DataModel.AXES, String.valueOf((int) xAxis) + ", " + String.valueOf((int) yAxis) + ", " + String.valueOf((int) zAxis));

        models.add(modelBatt);
        models.add(modelTemp);
        models.add(modelAct);
        models.add(modelAxes);

        patchFragment.updateDatas(models);

    }

    @Override
    public void isConnected(String macAddress) {

    }

    @Override
    public void connectionError(String macAddress) {

    }

    @Override
    public void isDisconnected(String macAddress) {

    }

    @Override
    public void scanFinish() {
    }


    ///////////////////////////////////////////////////////
    //                  FRAGMENT LISTENERS               //
    ///////////////////////////////////////////////////////
    @Override
    public void onFabClickListener() {
        myBluetoothConnection.shutdownPatch(mDevice.getAddress());
    }
}
