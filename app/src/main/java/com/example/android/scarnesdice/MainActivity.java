package com.example.android.scarnesdice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static int userOverallScore;
    static int userTurnScore;
    static int compOverallScore;
    static int compTurnScore;

    static final int DETERMINES_WIN = 30;

    TextView mScore_TV;
    TextView mComp_Score_TV;
    ImageView mDice_IV;
    Button mRoll_Btn;
    Button mHold_Btn;
    Button mReset_Btn;

    //Handler timeHandler = new Handler();
    /*Runnable timeRunnable = new Runnable(){
        @Override
        public void run() {
            timeHandler.postDelayed(this, 500);
        }
    };*/

    int[] diceFaces = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScore_TV = (TextView) findViewById(R.id.tv_score);
        mComp_Score_TV = (TextView) findViewById(R.id.comp_score);
        mDice_IV = (ImageView) findViewById(R.id.iv_dice);
        mRoll_Btn = (Button) findViewById(R.id.btn_roll);
        mHold_Btn = (Button) findViewById(R.id.btn_hold);
        mReset_Btn = (Button) findViewById(R.id.btn_reset);

        mRoll_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roll();
            }
        });

        mHold_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                hold();
            }
        });

        mReset_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                reset();
            }
        });
    }

    //Logic to roll dice
    int rollDice() {
        Random rand = new Random();
        int randNum = rand.nextInt(6) + 1;

        mDice_IV.setImageResource(diceFaces[randNum - 1]);

        return randNum;
    }

    //mScore_TV TextView will be updated based on n
    void updateUserLabel() {
        mScore_TV.setText("Your score: " + userOverallScore + "\n" + "Your turn score: " + userTurnScore);
    }

    //mComp_Score_TV TextView will be updated based on n
    void updateCompLabel(int n) {
        if (n == 1) {
            mComp_Score_TV.setText("Computer rolled a 1");
        } else if (n > 1) {
            mComp_Score_TV.setText("Computer\'s score: " + compOverallScore + "\n" + "Computer\'s turn score: " + compTurnScore);
        } else {
            mComp_Score_TV.setText("Computer holds");
        }
    }

    //Logic for user to roll the dice during their turn
    void roll() {
        int diceValue = rollDice();
        if (diceValue == 1) {
            userTurnScore = 0;
            updateUserLabel();
            //computerTurn();
            hold();
        } else  {
            userTurnScore += diceValue;
            updateUserLabel();
        }

        if (userOverallScore >= DETERMINES_WIN) {
            mScore_TV.setText("You won!");
            mRoll_Btn.setEnabled(false);
            mHold_Btn.setEnabled(false);
        }
    }

    //Logic for computer to roll the dice during its turn
    void computerTurn() {
        mRoll_Btn.setEnabled(false);
        mHold_Btn.setEnabled(false);

        //timeHandler.postDelayed(timeRunnable, 5000);
        int diceValue = rollDice();

        if (diceValue == 1) {
            compTurnScore = 0;
            updateCompLabel(diceValue);
        } else  {
            compTurnScore += diceValue;
            updateCompLabel(diceValue);
        }


        compOverallScore += compTurnScore;
        compTurnScore = 0;

        if (compOverallScore >= DETERMINES_WIN) {
            mComp_Score_TV.setText("Computer won!");
        } else {
            updateCompLabel(2);
            //gives user access to buttons again for their turn
            mRoll_Btn.setEnabled(true);
            mHold_Btn.setEnabled(true);
        }
    }

    //User skips their turn and hands over control to the computer
    void hold() {
        userOverallScore += userTurnScore;
        userTurnScore = 0;
        updateUserLabel();
        computerTurn();
    }

    void reset() {
        userOverallScore = 0;
        userTurnScore = 0;
        compOverallScore = 0;
        compTurnScore = 0;

        mScore_TV.setText("Your Score: \n 0");
        //Number greater than one is used to show correct text
        mComp_Score_TV.setText("Comp Score: \n 0");

        //Ensures listeners are enabled once game is reset
        mRoll_Btn.setEnabled(true);
        mHold_Btn.setEnabled(true);
    }
}