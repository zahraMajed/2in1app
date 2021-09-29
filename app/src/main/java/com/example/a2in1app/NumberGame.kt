package com.example.a2in1app

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_number_game.*
import kotlin.random.Random

class NumberGame : AppCompatActivity() {
    lateinit var NumField: EditText //etGuessdNum
    lateinit var butGuess: Button //butGuess
    var guessCount = 4
    var randomNum = Random.nextInt(11)///
    var msgArray: ArrayList<String> = arrayListOf()///

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_game)
        NumField=findViewById(R.id.etGuessdNum)
        butGuess=findViewById(R.id.butGuess)
        setTitle("Numbers Game")


        rv_main.adapter = RecyclerAdapter(msgArray)
        rv_main.layoutManager = LinearLayoutManager(this)

        butGuess.setOnClickListener { showStatus() }

    }

    //////////////////////////////////menu part//////////////////////////////

    //add our menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu,menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item: MenuItem =menu!!.getItem(1)
        if (item.title.equals("Other Game"))
            item.title="Guess Phrase"
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.mi_new_game -> {
                //goNumGame()
                AlertDialog(this, "Are you sure you want start a new game?")
                return true
            }
            R.id.mi_other_game -> {
                goPhraseGame()
                return true
            }
            R.id.mi_back -> {
                goMain()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun goPhraseGame(){
        intent=Intent(this, PhraseGame::class.java)
        startActivity(intent)
    }

    fun goMain(){
        intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //////////////////////////////////////////////////////////

    //showStatus
    private fun showStatus(){
        val userGuess = NumField.text.toString()
        if (userGuess.isNotEmpty()) {
            if (guessCount > 0) {
                if (userGuess.toInt() == randomNum) {
                    display()
                    AlertDialog(this,"You win! Play again?")
                } else {
                    guessCount--
                    msgArray.add("You guessed $userGuess")
                    msgArray.add("you have $guessCount left")
                }
            }
            if (guessCount == 0) {
                display()
                msgArray.add("You lose, the correct answer was $randomNum . \n\n Game over!")
                AlertDialog(this,"You lose!, The correct answer was $randomNum. Play again?")
            }
            NumField.text.clear()
            NumField.clearFocus()
            rv_main.adapter?.notifyDataSetChanged()
        } else {
            Snackbar.make(findViewById<ConstraintLayout>(R.id.CL),
                "please enter a number",
                Snackbar.LENGTH_LONG).show()
        }
    }//showStatus

    private fun display() {
        butGuess.isEnabled = false
        butGuess.isClickable = false
        NumField.isEnabled = false
        NumField.isClickable = false
    }//display

}