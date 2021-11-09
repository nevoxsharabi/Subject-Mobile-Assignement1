package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView car;
    private ArrayList<ImageView> rocks;
    private ArrayList<ImageView> hearts;
    private Random random;
    private int randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = new Random();
        car = findViewById(R.id.imageViewCar1);
        hearts = new ArrayList<ImageView>();
        for (int i = 0; i < 3; i++) {
            hearts.add(findViewById(getResources().getIdentifier("imageViewHeart" + i, "id", getPackageName())));
        }
        rocks = new ArrayList<ImageView>();
        for (int i = 0; i < 21; i++) {
            rocks.add(findViewById(getResources().getIdentifier("imageViewRock" + i, "id", getPackageName())));
        }
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dropRocks();
                handler.postDelayed(this, 600);
            }
        };
        handler.post(runnable);

    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

    }

    public void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

    }

    private void startNewGame() {
        for (int i = 20; i >= 0; i--) {
            rocks.get(i).setVisibility(View.INVISIBLE);
        }
        car = findViewById(R.id.imageViewCar1);
        car.setVisibility(View.VISIBLE);
        findViewById(R.id.imageViewCar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.imageViewCar2).setVisibility(View.INVISIBLE);
    }


    public void dropRocks() {
        if (checkCrush()) {
            crush();
        }
        for (int i = 20; i >= 0; i--) {
            if (i > 17) {
                rocks.get(i).setVisibility(View.INVISIBLE);
            } else {
                if (rocks.get(i).getVisibility() == View.VISIBLE) {
                    rocks.get(i).setVisibility(View.INVISIBLE);
                    rocks.get(i + 3).setVisibility(View.VISIBLE);
                }
            }
        }

        randomNumber = random.nextInt(1000) % 5;
        ///making sure there isn't a blocking
        if (randomNumber < 3) {
            if (randomNumber == 0) {
                if (rocks.get(4).getVisibility() == View.INVISIBLE | rocks.get(8).getVisibility() == View.INVISIBLE) {
                    rocks.get(randomNumber).setVisibility(View.VISIBLE);
                }
            } else if (randomNumber == 2) {
                if (rocks.get(4).getVisibility() == View.INVISIBLE | rocks.get(6).getVisibility() == View.INVISIBLE) {
                    rocks.get(randomNumber).setVisibility(View.VISIBLE);
                }
            } else {
                rocks.get(randomNumber).setVisibility(View.VISIBLE);
            }
        }
    }

    public void crush() {
        if (hearts.get(0).getVisibility() == View.VISIBLE) {
            hearts.get(0).setVisibility(View.INVISIBLE);
            vibrate();
            toast("you got hit");

        } else if (hearts.get(1).getVisibility() == View.VISIBLE) {
            hearts.get(1).setVisibility(View.INVISIBLE);
            vibrate();
            toast("You got one life left, be careful");
        } else {
            vibrate();
            hearts.get(0).setVisibility(View.VISIBLE);
            hearts.get(1).setVisibility(View.VISIBLE);
            startNewGame();
            toast("you lost ,new game just started good luck");
        }
    }


    public boolean checkCrush() {
        int carTag = Integer.parseInt(car.getTag().toString());
        switch (carTag) {
            case 0:
                if (rocks.get(18).getVisibility() == View.VISIBLE) {
                    return true;
                } else
                    return false;


            case 1:
                if (rocks.get(19).getVisibility() == View.VISIBLE) {
                    return true;
                } else
                    return false;

            case 2:
                if (rocks.get(20).getVisibility() == View.VISIBLE) {
                    return true;
                } else
                    return false;

        }
        return false;

    }


    public void moveCar(View view) {
        int arrowTag = Integer.parseInt(view.getTag().toString());
        int carTag = Integer.parseInt(car.getTag().toString());
        if (arrowTag == 0) {
            //move left
            if (carTag != 0) {
                car.setVisibility(View.INVISIBLE);
                if (carTag == 1) {
                    car = findViewById(R.id.imageViewCar0);
                } else {
                    car = findViewById(R.id.imageViewCar1);
                }
                car.setVisibility(View.VISIBLE);
            }

        } else {
            if (carTag != 2) {
                car.setVisibility(View.INVISIBLE);
                if (carTag == 1) {
                    car = findViewById(R.id.imageViewCar2);
                } else {
                    car = findViewById(R.id.imageViewCar1);
                }
                car.setVisibility(View.VISIBLE);

            }

        }
    }
}