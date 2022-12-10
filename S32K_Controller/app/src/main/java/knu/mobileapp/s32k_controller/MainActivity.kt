package knu.mobileapp.s32k_controller

import android.Manifest
import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.PointF
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Log.i
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.lang.Math.atan2
import java.util.*


class MainActivity : AppCompatActivity() {
//    lateinit var ba : BluetoothAdapter

    val bluetoothManager: BluetoothManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.getAdapter()
    val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    /*
    private inner class AcceptThread : Thread() {

        private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
            bluetoothAdapter?.listenUsingInsecureRfcommWithServiceRecord(NAME, MY_UUID)
        }

        override fun run() {
            // Keep listening until exception occurs or a socket is returned.
            var shouldLoop = true
            while (shouldLoop) {
                val socket: BluetoothSocket? = try {
                    mmServerSocket?.accept()
                } catch (e: IOException) {
//                    Log.e(TAG, "Socket's accept() method failed", e)
                    shouldLoop = false
                    null
                }
                socket?.also {
                    manageMyConnectedSocket(it)
                    mmServerSocket?.close()
                    shouldLoop = false
                }
            }
        }

        // Closes the connect socket and causes the thread to finish.
        fun cancel() {
            try {
                mmServerSocket?.close()
            } catch (e: IOException) {
//                Log.e(TAG, "Could not close the connect socket", e)
            }
        }
    }

    private inner class ConnectThread(device: BluetoothDevice) : Thread() {

        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(MY_UUID)
        }

        public override fun run() {
            // Cancel discovery because it otherwise slows down the connection.
            bluetoothAdapter?.cancelDiscovery()

            mmSocket?.let { socket ->
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                socket.connect()

                // The connection attempt succeeded. Perform work associated with
                // the connection in a separate thread.
                manageMyConnectedSocket(socket)
            }
        }

        // Closes the client socket and causes the thread to finish.
        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "Could not close the client socket", e)
            }
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        /*
        val bluetoothManager: BluetoothManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.getAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(this@MainActivity, "Bluetooth not supported", Toast.LENGTH_SHORT).show();
            finish()
        }
        else
        {
            Toast.makeText(this@MainActivity, "Bluetooth supported", Toast.LENGTH_SHORT).show();

            if(!BluetoothAdapter.isEnabled()) {
                var bIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    val permissions = arrayOf(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN
                    )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(permissions, 1001)
                    }
                }
                startActivity(bIntent)
            }
        }*/




        val viewTouch = findViewById<View>(R.id.view_touch)
        val steering = findViewById<ImageView>(R.id.steering)

        // Radio Group - Button
        val rgroup = findViewById<RadioGroup>(R.id.radioGroup)


        // textView
        val TA = findViewById<TextView>(R.id.textAngle2)
        val TS = findViewById<TextView>(R.id.textSpeed2)
        val TM = findViewById<TextView>(R.id.textMod2)

        // Button
        val P1 = findViewById<Button>(R.id.buttonP1)
        val P2 = findViewById<Button>(R.id.buttonP2)

        // Steering Wheel
        val center = PointF()
        viewTouch.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                center.x = viewTouch.x + viewTouch.width / 2
                center.y = viewTouch.y + viewTouch.height / 2

                steering.x = center.x - steering.width / 2
                steering.y = center.y - steering.height / 2

                viewTouch.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        var prevDegree = 0.0
        var degree = 0.0
        viewTouch.setOnTouchListener { v, event ->
            val dX = event.x - center.x.toDouble()
            val dY = event.y - center.y.toDouble()

            degree = Math.toDegrees(atan2(dY, dX))

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    prevDegree = degree
                }
                MotionEvent.ACTION_MOVE -> {
                    steering.rotation = (steering.rotation + (degree - prevDegree)).toFloat()
                    TA.text = degree.toString()
                    prevDegree = degree
                }
            }
            return@setOnTouchListener true
        }

        // Radio
        rgroup.setOnCheckedChangeListener { radioGroup, ID ->
            kotlin.run {
                when(ID){
                    R.id.rg_btn1 -> {
                        TM.text = "P";
                    }
                    R.id.rg_btn2 -> {
                        TM.text = "R";
                    }
                    R.id.rg_btn3 -> {
                        TM.text = "N";
                    }
                    R.id.rg_btn4 -> {
                        TM.text = "D";
                    }
                }
            }
        }

        var speed = 0;


        // Pedal
        P1.setOnClickListener(View.OnClickListener {
            speed = 0;
            TS.text = "0";
        })

        P2.setOnClickListener(View.OnClickListener {
            if(TM.text == "D" && speed < 80){
                speed+=10;
            }else if(TM.text == "R" && speed > -80){
                speed-=10;
            }

            TS.text = speed.toString();
        })





        /*
        ba = (this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
        if (ba == null) {
            Toast.makeText(this@MainActivity, "Bluetooth not supported", Toast.LENGTH_LONG).show();
            finish()
        }
        else
            Toast.makeText(this@MainActivity, "Bluetooth supported", Toast.LENGTH_LONG).show();

            if(!ba.isEnabled())
            {
                var bIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    val permissions = arrayOf(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN
                    )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(permissions, 1001)
                    }
                }
                startActivity(bIntent)
            }*/

    }



}