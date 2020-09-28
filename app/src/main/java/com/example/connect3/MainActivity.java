package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0; //0 for yellow and 1 for red

    boolean gameIsActive = true;

    int[] gameState= {2,2,2,2,2,2,2,2,2,2}; //2 means the box is un played
    //set of indexes which leads the player to win - brute force approach
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};


    public void dropIn(View view){

        ImageView counter = (ImageView) view;


        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer; //activePlayer will tell us who is currently playing

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1; //change of turn
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;//change of turn
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            //to check if any one has won or not
            //we loop through each array in winningPositions
            for(int[] winningPosition : winningPositions)
            {   // if continuously three elements have the same gameState other than two then that activePlayer has won the match
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                && gameState[winningPosition[0]] !=2)
                {
                    gameIsActive = false;
                    //someone has won
                    String winner = "Red";
                    if(gameState[winningPosition[0]] == 0)
                    {
                        winner = "Yellow";
                    }



                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                    winnerMessage.setText(winner + " has won!!");

                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);

                    layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    boolean gameIsOver = true;
                    for(int counterState : gameState)
                    {
                        if(counterState == 2)
                        {
                            gameIsOver = false;
                        }
                    }
                    if(gameIsOver){
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                        winnerMessage.setText("It's a draw!!");

                        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);

                        layout.setVisibility(View.VISIBLE);
                    }

                }
            }
        }
    }
    public void playAgain(View view)
    {

        gameIsActive = true;
        //making the message disappear
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);

        layout.setVisibility(View.INVISIBLE);
        //making the values back to the default  ones
        activePlayer = 0;

        for(int i = 0; i< gameState.length; i++){
            gameState[i] = 2;
        }
        //removing the images of coins
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0;i<gridLayout.getChildCount(); i++)
        {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}