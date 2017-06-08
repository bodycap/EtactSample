package bodycap.com.etactsample.controls;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import bodycap.com.bluetoothetact.bodyException.BluetoothAdapterException;
import bodycap.com.bluetoothetact.bodyException.PermissionsException;
import bodycap.com.bluetoothetact.broadcasts.IBluetoothDataListener;
import bodycap.com.bluetoothetact.broadcasts.IBluetoothStatusBroadcast;
import bodycap.com.bluetoothetact.connection.EtactServiceConnection;
import bodycap.com.etactsample.R;
import bodycap.com.etactsample.views.MainFragment;
import bodycap.com.etactsample.views.MainFragment.IActivityFragmentListener;

public class MainActivity extends AppCompatActivity implements IBluetoothStatusBroadcast,IBluetoothDataListener, IActivityFragmentListener {

    private EtactServiceConnection myBluetoothConnection ;
    private ProgressDialog mProgressDialog ;
    private MainFragment mainView ;

    public static final String EXTRA_MESSAGE = "com.bodycap.etactsample.BLUETOTH_DEVICE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content);

        mainView = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, mainView);
        transaction.commit();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            myBluetoothConnection = new EtactServiceConnection(MainActivity.this, bluetoothAdapter);
        } catch (BluetoothAdapterException e) {
            e.printStackTrace();
        } catch (PermissionsException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(myBluetoothConnection != null)
            myBluetoothConnection.stopConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myBluetoothConnection != null) {
            myBluetoothConnection.startConnection();
        }
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

    }

    @Override
    public void patchNameReceived(String macAddress, String name) {

    }

    @Override
    public void patchDatasReceived(String macAddress, float battery, float temperature, float xAxis, float yAxis, float zAxis, float activity) {

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
        mProgressDialog.dismiss();
        mainView.updateListView((ArrayList<BluetoothDevice>) myBluetoothConnection.getConnectedPatchs());
    }

    ///////////////////////////////////////////////////////
    //                  FRAGMENT LISTENERS               //
    ///////////////////////////////////////////////////////
    @Override
    public void onItemClickListener(int position) {
        Intent newActivity = new Intent(this, PatchActivity.class);
        newActivity.putExtra(EXTRA_MESSAGE, myBluetoothConnection.getConnectedPatchs().get(position));
        startActivity(newActivity);
    }

    @Override
    public void onFabClickListener() {
        if(myBluetoothConnection != null) {
            myBluetoothConnection.searchEtactDevice();
            mProgressDialog= ProgressDialog.show(MainActivity.this, "Please wait",
                    "Scan starts...", true);
            mProgressDialog.show();
        }
        else Log.d("SearchBluetoothEtact", "pas de connexion !!!");
    }
}
