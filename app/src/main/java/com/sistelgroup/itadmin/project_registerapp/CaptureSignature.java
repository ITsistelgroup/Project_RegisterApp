package com.sistelgroup.itadmin.project_registerapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class CaptureSignature extends AppCompatActivity {

    LinearLayout mContent;
    signature mSignature;
    Button mClear, mGetSign, mCancel;
    public static String tempDir;
    public int count = 1;
    public String current = null, current2=null;
    private Bitmap mBitmap;
    View mView;
    File mypath;
    TextView CIF, NomEmpresa, NomPersona, QuiVisita, Motiu, DNI, textRGPD;
    ImageView img_company;
    Intent myIntent;

    private String uniqueId;
    private Bitmap mBitmapOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_signature2);
        myIntent = getIntent();

        tempDir = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.external_dir) + "/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);

        prepareDirectory();
        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        current = uniqueId + ".png";

        mypath= new File(directory,current);

        CIF=findViewById(R.id.CIF);
        NomEmpresa=findViewById(R.id.NomEmpresa);
        NomPersona=findViewById(R.id.NomPersona);
        QuiVisita=findViewById(R.id.QuiVisita);
        Motiu=findViewById(R.id.Motiu);
        DNI=findViewById(R.id.DNI);
        img_company=findViewById(R.id.img_company);
        textRGPD=findViewById(R.id.textRGPD);


        CIF.setText("CIF: " + myIntent.getStringExtra("CIF"));
        NomEmpresa.setText(getText(R.string.NameCompany) + ": " + myIntent.getStringExtra("NomEmpresa"));
        NomPersona.setText(getText(R.string.Name) + ": " + myIntent.getStringExtra("NomPersona"));
        QuiVisita.setText(getText(R.string.whosvisiting) + ": " + myIntent.getStringExtra("QuiVisita"));
        Motiu.setText(getText(R.string.motiu) + ": " + myIntent.getStringExtra("Motiu"));
        DNI.setText(getText(R.string.dni) + ": " + myIntent.getStringExtra("DNI"));


        switch (myIntent.getStringExtra("company")){
            case "S.A.Sistel":
                 img_company.setImageResource(R.mipmap.sasistel);
                break;
            case "DigiProces S.A.":
                img_company.setImageResource(R.mipmap.digi);
                break;
            case "SmartLift S.L.":
                img_company.setImageResource(R.mipmap.smartlift);
                break;
            case "Kfew Systems S.L.":
                img_company.setImageResource(R.mipmap.kfew);
                break;
            case "6TL Engineering":
                img_company.setImageResource(R.mipmap.sixtl);
                break;
            default:

        }


        mContent = (LinearLayout) findViewById(R.id.linearLayout);
        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, 600,400/*LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT*/);
        mClear = (Button)findViewById(R.id.clear);
        mGetSign = (Button)findViewById(R.id.getsign);
        mGetSign.setEnabled(false);
        mCancel = (Button)findViewById(R.id.cancel);
        mView = mContent;

        textRGPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CaptureSignature.this, RGPD.class));
            }
        });


        mClear.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                mGetSign.setEnabled(false);
            }
        });

        mGetSign.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("log_tag", "Panel Saved");

                mView.setDrawingCacheEnabled(true);
                mSignature.save(mView);
                register();
                /*Bundle b = new Bundle();
                b.putString("status", "done");
                Intent intent = new Intent();
                intent.putExtras(b);
                setResult(RESULT_OK,intent);
                finish();*/

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("log_tag", "Panel Canceled");
                Bundle b = new Bundle();
                b.putString("status", "cancel");
                Intent intent = new Intent();
                intent.putExtras(b);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }


    @Override
    protected void onDestroy() {
        Log.w("GetSignature", "onDestory");
        super.onDestroy();
    }




    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate =     (c.get(Calendar.YEAR) * 10000) +
                ((c.get(Calendar.MONTH) + 1) * 100) +
                (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:",String.valueOf(todaysDate));
        return(String.valueOf(todaysDate));

    }

    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime =     (c.get(Calendar.HOUR_OF_DAY) * 10000) +
                (c.get(Calendar.MINUTE) * 100) +
                (c.get(Calendar.SECOND));
        Log.w("TIME:",String.valueOf(currentTime));
        return(String.valueOf(currentTime));

    }


    private boolean prepareDirectory()
    {
        try
        {
            if (makedirs())
            {
                return true;
            } else {
                return false;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean makedirs()
    {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory())
        {
            File[] files = tempdir.listFiles();
            for (File file : files)
            {
                if (!file.delete())
                {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }

    public class signature extends View
    {
        private static final float STROKE_WIDTH = 10f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs)
        {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v)
        {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if(mBitmap == null)
            {
                mBitmap =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.ARGB_8888);;
            }
            Canvas canvas = new Canvas(mBitmap);
            try
            {
                v.draw(canvas);
                //TODO: Replace php
                current2=getTodaysDate() + getCurrentTime() + myIntent.getStringExtra("DNI") + ".jpg";
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int width = mBitmap.getWidth();
                int height = mBitmap.getHeight();



                int newWidth = width/7;
                int newHeight = height/7;

                // calculamos el escalado de la imagen destino
                float scaleWidth = ((float) newWidth) / width;
                float scaleHeight = ((float) newHeight) / height;

                // para poder manipular la imagen
                // debemos crear una matriz

                Matrix matrix = new Matrix();
                // resize the Bitmap
                matrix.postScale(scaleWidth, scaleHeight);

                // volvemos a crear la imagen con los nuevos valores
                Bitmap resizedBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
                        width, height, matrix, true);

                if(mBitmapOut == null)
                {
                    mBitmapOut =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.ARGB_8888);;
                }
                mBitmapOut = BinaryBitmap(resizedBitmap, -1);
                mBitmapOut.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);

                //mBitmapOut.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

                byte[] array=byteArrayOutputStream.toByteArray();

                String encodedImage = Base64.encodeToString(array,0);
                String a = "http://192.168.4.13:8090/phpfiles/fileUpload.php?image_name="+current2+"&encoded_string="+encodedImage;
                //new CarregarDades().execute(a);
                new CarregarDades().execute(a);
                //String path = "smb://SISTELGROUP\\grosell:63r4rd.2019@\\\\192.168.4.12\\SistelGroup\\ITIntern\\Projectes\\RegistrApp\\";

                File f = new File( path + current2);
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(byteArrayOutputStream.toByteArray());

                /*FileOutputStream mFileOutStream = new FileOutputStream(mypath);




                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                String url = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
                Log.v("log_tag","url: " + url);*/
                //In case you want to delete the file
                //boolean deleted = mypath.delete();
                //Log.v("log_tag","deleted: " + mypath.toString() + deleted);
                //If you want to convert the image to string use base64 converter

            }
            catch(Exception e)
            {
                Log.v("log_tag", e.toString());
            }
        }



        public Bitmap BinaryBitmap(Bitmap source, int umb)
        {
            // Bitmap con la imagen binaria
            Bitmap target = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            // Recorrer pixel de la imagen
            for (int i = 0; i < source.getWidth(); i++)
            {
                for (int e = 0; e < source.getHeight(); e++)
                {
                    // Color del pixel
                    int col = source.getPixel(i, e);
                    int green = Color.green(col);
                    // Escala de grises
                    byte gray = (byte)(Color.red(col) * 0.3f + Color.green(col) * 0.59f + Color.blue(col) * 0.11f);
                    // Blanco o negro
                    int value = 255;
                    if (gray > umb)
                    {
                        value = 0;
                    }
                    // Asginar nuevo color

                    int newColor = Color.rgb(value,value,value);
                    target.setPixel(i, e, newColor);


                }
            }

            return target;
        }

        public void clear()
        {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++)
                    {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string){
        }

        private void expandDirtyRect(float historicalX, float historicalY)
        {
            if (historicalX < dirtyRect.left)
            {
                dirtyRect.left = historicalX;
            }
            else if (historicalX > dirtyRect.right)
            {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top)
            {
                dirtyRect.top = historicalY;
            }
            else if (historicalY > dirtyRect.bottom)
            {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY)
        {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    //Inici funcions WS
    private void register() {

        String a = "http://192.168.4.13:8090/phpfiles/sp_Registre.php?DNI="+myIntent.getStringExtra("DNI")+
                "&Emp_Vis="+myIntent.getStringExtra("CIF")+
                "&NOM_Emp_Vis="+myIntent.getStringExtra("NomEmpresa")+
                "&Nom_Visitant="+myIntent.getStringExtra("NomPersona")+
                "&ID_EmpresaVisitada="+String.valueOf(myIntent.getIntExtra("IDcompany",0))+
                "&ID_PersonaCitada="+myIntent.getStringExtra("QuiVisita")+
                "&Motiu="+myIntent.getStringExtra("Motiu");


        //new CarregarDades().execute(a);
        new CarregarDades().execute(a);
        Toast.makeText(CaptureSignature.this, R.string.Success, Toast.LENGTH_LONG).show();


        //Toast.makeText(this, missatge, LENGTH_LONG).show();
        //Toast.makeText(this,ret.toString(), Toast.LENGTH_LONG).show();


        startActivity(new Intent(this, CompanySelection.class));
    }

    public class CarregarDades extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {

                return downloadUrl(strings[0]);
            } catch (IOException e) {

                return getString(R.string.URLerror);
            }
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        myurl = myurl.replace(" ", "%20");
        InputStream stream = null;
        int len = 100000;
        try {
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(4000);
            connection.setConnectTimeout(4000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            // Open communications link (network traffic occurs here).
            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.d("reposta", "La resposta es: " + responseCode);
            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();

            //Convertir el InputString a String
            String ContentAsString = readIt(stream, len);

            return ContentAsString;

        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
    //Final funcions WS
}
