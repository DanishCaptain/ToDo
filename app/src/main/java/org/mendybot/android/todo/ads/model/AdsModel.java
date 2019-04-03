package org.mendybot.android.todo.ads.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.PriorityQueue;

public final class AdsModel implements Runnable{
    private static AdsModel singleton;
    private PriorityQueue<AdHandler> queue = new PriorityQueue<>();
    private Thread t = new Thread(this);
    private boolean running;
    private Activity activity;
    //https://www.survivalkit.com/blog/wp-content/uploads/2015/12/shutterstock_180365039-300x200.jpg

    private AdsModel() {
    }

    public void init(Activity activity) {
        this.activity = activity;

        //SharedPreferences sp = activity.getSharedPreferences("FS_ADS", Context.MODE_PRIVATE);
        //int oink = sp.getInt("TEST_OINK", 22);
        //sp.edit().putInt("TEST_OINK", 100).commit();

        if (!running) {
            t.setDaemon(true);
            t.setName("AdsModel");
            t.start();
        }
    }

    public void add(AdHandler ad) {
        synchronized (this) {
            queue.add(ad);
            notifyAll();
        }
    }

    @Override
    public void run() {
        running = true;
        while(running) {
            AdHandler handler;
            synchronized(this) {
                while (queue.size() == 0) {
                    try {
                        wait(10000);
                    } catch (InterruptedException e) {
                        running = false;
                        break;
                    }
                }
                handler = queue.remove();

                try {
                    Activity a = handler.getActivity();
                    final ImageButton i = (ImageButton) handler.getView();
                    String imageUrl = "https://www.survivalkit.com/blog/wp-content/uploads/2015/12/shutterstock_180365039-300x200.jpg";
//                    String imageUrl = "<image src='http://www.survivalkit.com/blog/wp-content/uploads/2015/12/shutterstock_180365039-300x200.jpg'>";
                    final Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            i.setImageBitmap(bitmap);
                        }
                    });

//                    ImageButton ib = new ImageButton(a);
//                    ib.setImageBitmap(bitmap);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }
    }

    public synchronized static AdsModel getInstance() {
        if (singleton == null) {
            singleton = new AdsModel();
        }
        return singleton;
    }

}
