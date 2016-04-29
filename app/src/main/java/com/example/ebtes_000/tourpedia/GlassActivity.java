package com.example.ebtes_000.tourpedia;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

public class GlassActivity extends AppCompatActivity {


    TextView message;
    Context context =this;
    BluetoothAdapter mBluetoothAdapter;
    Handler mHandler;
    File pictureFile;
    FileOutputStream fos;
    static ConnectedThread connectedThread;

    //========Constants=========
    final int REQUEST_ENABLE_BLUETOOTH = 1;
    final int MESSAGE_READ = 5;
    final int MEDIA_TYPE_IMAGE=1;//repeated constant
    final String NAME= "TOURPEDIA";
    final int DONE_READ =2;
    final UUID MY_UUID = UUID.fromString("36d36d58-63e5-4be8-840b-bcac5022149a");//Generated by a UUID generator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_glass);

        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(GlassActivity.this, home.class);
                startActivity(intent);


            }
        });
        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(GlassActivity.this, settings.class);
                startActivity(intent);


            }
        });
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(GlassActivity.this, filter.class);
                startActivity(intent);


            }
        });
        message = (TextView)findViewById(R.id.connctionMessage);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if(mBluetoothAdapter!=null)
        requestBluetooth();

        //TODO: (else) and maybe create something for the toast.
         try{
             createFile();
        fos = new FileOutputStream(pictureFile);}catch (IOException e){
             Log.d("debug","fos creation: "+e.getMessage());
         }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {



                switch (msg.what) {
                    //case STATE_CONNECTION_STARTED:
                        //text.setText(msg.getData().getString("CONN"));
                    //    break;
                   // case STATE_CONNECTION_LOST:
                   //     break;
                   // case READY_TO_CONN:
                        //startListening();
                   //     break;

                    case MESSAGE_READ:
                        //TODO:put message or something
                        break;
                    case DONE_READ:
                        try{

                            fos.close();
                            //Log.d("debug", "Msg read please!! ");
                            uploadImage();

                        }catch (IOException e){
                            Log.d("debug", "Error closing file: " + e.getMessage());

                            break;
                        }
                        break;
                    default:
                        break;
                }
            }
        };

    }


    public void requestBluetooth(){

            VariablesAndConstants.isFromGlass=true;//In order to send the info to the glass later on.
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);

    }


     public static void sendToGlass(String result){


         connectedThread.write(result.getBytes());


     }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {

            //referance: http://android-er.blogspot.com/2011/05/turn-on-bluetooth-using-intent-of.html

            if (mBluetoothAdapter == null) {

               message.setText("Make sure of the blutoothAdabter!!");//TODO:This is for devices with no bluetooth or that what i think
                message.setContentDescription("Make sure of the blutoothAdabter!!");
            } else if (mBluetoothAdapter.isEnabled()) {

                if (mBluetoothAdapter.isDiscovering()) { //TODO:See the documentation for isDiscovering()

                    message.setText("Now discovering..");
                    message.setContentDescription("Now discovering..");
                } else {

                    //Now everything is okay :)
                    message.setText("Waiting for the Glass.."); //TODO: I need to check for sudden bt close
                    message.setContentDescription("Waiting for the Glass..");

                     new AcceptThread().start();


                }
            } else {

                //Someone ignored the request :(
                message.setText("Bluetooth is not enabled!!");
                message.setContentDescription("Bluetooth is not enabled!!");
                Toast toast=Toast.makeText(this, "Can not work without Bluetooth enabled", Toast.LENGTH_SHORT);
                toast.show();
                finish();//No Bluetooth NO Glass
                           }



        }
    }

    public void createFile(){

        pictureFile = ImageHandler.saveImage(MEDIA_TYPE_IMAGE);
        if (pictureFile == null){
            Log.d("debug", "Error creating media file, check storage permissions! ");
            return;
        }

    }


    public void uploadImage(){
        Intent intent = new Intent(this, imgDescription.class);
        startActivity(intent);
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    Log.d("debug", "before accept!");
                    socket = mmServerSocket.accept();
                    Log.d("debug", "after accept!");

                } catch (IOException e) {
                    Log.d("debug", "Error in socket accept: " + e.getMessage());

                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    manageConnectedSocket(socket);
                   // mmServerSocket.close();
                    cancel();
                    break;
                }
            }
        }

        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.d("debug", "Error socket close: " + e.getMessage());

            }
        }

       public void  manageConnectedSocket(BluetoothSocket socket){

           //TODO: Call the connectedThread.
           connectedThread=new ConnectedThread(socket);
           connectedThread.start();


       }



    }


    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        public int bytesReceived=0;
        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        int totalBytesToReceieve;
        int headerBytes=0;

        private void parseHeader(byte[] bytes) {
            ByteBuffer bb = ByteBuffer.wrap(bytes);
            totalBytesToReceieve = (int) bb.getLong();
            Log.d("debug", "Bytes to receive: " + totalBytesToReceieve);
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            boolean Done=false;
            boolean Header=true;
            boolean headerReceived=false;
            try {
            // Keep listening to the InputStream until an exception occurs



                while (!Done) {// Read from the InputStream

               // if(mmInStream.available()>0){
                bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    //TODO:Here is all the work start :)
                Log.d("debug","bytes before: "+ bytes);
                  //  mHandler.obtainMessage(MESSAGE_READ, bytes, 0, buffer)
                            //.sendToTarget();
                   if(Header){

                       headerBytes=bytes;

                     //  if(headerBytes>0&&!headerReceived) {
                           parseHeader(buffer);
                          // headerReceived=true;
                      // }
                      // if(headerBytes>=1024) {
                          Header = false;
                       if(headerBytes<1024){
                           mmInStream.read(buffer,0,1024-headerBytes);
                          // int a= headerBytes-1024;
                          // int b= bytes-a;

                          // fos.write(buffer, b-1, bytes-b);

                       }
                       //}
                   }/*else if(headerBytes<1024&&!headerReceived){
                       headerReceived=true;

                        int a=1024-headerBytes;
                       fos.write(buffer,a , bytes-a);

                   } */else
                   {

                        fos.write(buffer, 0, bytes);
                         bytesReceived+=bytes;
                       Log.d("debug", "totalBytesToReceieve = " + totalBytesToReceieve);
                       Log.d("debug","bytesReceived = "+bytesReceived);
                    if(totalBytesToReceieve<=bytesReceived )
                        Done=true;}
                //  }



            }
                Log.d("debug", "DOne in outside exeption yaaaaaaaaaaaaaaaaaaaaay!");
                mHandler.obtainMessage(DONE_READ).sendToTarget();

            } catch (IOException e) {

                mHandler.obtainMessage(DONE_READ).sendToTarget();
                Log.d("debug","DOne in exeption: "+e.getMessage());

            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {

                Log.d("debug","ERROR while writing: "+e.getMessage());

            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }



}
