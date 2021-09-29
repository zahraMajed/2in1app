package com.example.a2in1app

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_phrase_game.*

class PhraseGame : AppCompatActivity() {
    lateinit var tvPhrase: TextView  //
    lateinit var tvGuessdLitter: TextView //
    lateinit var phraseField: EditText //
    lateinit var butGuess: Button //
    lateinit var tvScore: TextView//
    lateinit var tvScoreC: TextView

    val Phrase:String="Hello to my app"///
    val secretPhraseDictionary= mutableMapOf<Int,Char>()
    var secretPhrase=""//
    var userGuessLitter="" //
    var message: ArrayList<String> = ArrayList()
    var guessCountL =0 ////
    var guessCountP=0
    var guessPhrase=true///

    lateinit var shared: SharedPreferences

    var correctLetterArray:ArrayList<Char> = ArrayList()
    var highScore:Int=0 //
    var point:Int=0 //score

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phrase_game)
        setTitle("Guess Phrase")

        for(i in Phrase.indices){
            if(Phrase[i].isWhitespace()){
                secretPhraseDictionary[i]=' '
                secretPhrase+=' '
            } else {
                secretPhraseDictionary[i]='*'
                secretPhrase+='*'
            }
        }

        tvPhrase=findViewById(R.id.tvPhrase)
        tvGuessdLitter=findViewById(R.id.tvCurrentGuessdLet)
        tvScore=findViewById(R.id.tvScore)
        tvScoreC=findViewById(R.id.tvScoreC)
        phraseField=findViewById(R.id.etPhraseGuess)
        butGuess=findViewById(R.id.butGuess)
        rv_phraseGame.adapter=RecyclerAdapter(message)
        rv_phraseGame.layoutManager = LinearLayoutManager(this)
        butGuess.setOnClickListener(){ addMsg()}
        updateString()

        shared = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        highScore = shared.getInt("High Score", 0)
        tvScore.setText("High score: $highScore ")
    }//end on create

    //////////////////////////////////menu part//////////////////////////////

    //add our menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu,menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item:MenuItem=menu!!.getItem(1)
        if (item.title.equals("Other Game"))
            item.title="Number Game"
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.mi_new_game -> {
                AlertDialog(this,"Are you sure you want start a new game?")
                return true
            }
            R.id.mi_other_game -> {
                goNumGame()
                return true
            }
            R.id.mi_back -> {
                goMain()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun goNumGame(){
        intent= Intent(this, NumberGame::class.java)
        startActivity(intent)
    }

    fun goMain(){
        intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //////////////////////////////////////////////////////////

    fun updateString(){
        tvPhrase.text="Phrase: $secretPhrase"
        tvGuessdLitter.text="Guessed Letters: ${userGuessLitter.toUpperCase()}"
        if(guessPhrase){
            phraseField.hint="Guess the full phrase"
        }else
            phraseField.hint="Guess a letter"
    }//end updateString()

    fun addMsg(){
        val msg=phraseField.text.toString()
        // when gusse a phrase turn
        if (guessPhrase){
            //if phrase are equle
            if(msg.equals(Phrase,true)){
                disabledEntry()
                secretPhrase=Phrase
                addPointPhrase()
                updateString() // change the secret phrase to render the correct phrase on screen
                highScore()
                AlertDialog(this, "You win! \n \n Play again?")
                //if not
            }else{
                message.add("Wrong guess: $msg")
                GuessCountP()
                guessPhrase=false // to change the turn to guess a letter in updateString()
                updateString() //will check guessPhrase to change hint(no changes made on secret phrase and gusssed letter so wont chage)
            }
            //when gusse a letter trun
        }else{
            if (msg.isNotEmpty()&& msg.length==1){
                secretPhrase=""///////////
                guessPhrase=true//to change the turn to guess a phrase in updateString() (inside checkLetters())
                checkLetteres(msg[0]) //
            }else{
                Snackbar.make(findViewById(R.id.CL),"Plese enter one letter only", Snackbar.LENGTH_LONG).show()
            }
        }
        phraseField.text.clear()
        phraseField.clearFocus()
        rv_phraseGame.adapter?.notifyDataSetChanged()
    }//end adddMsg()

    fun checkLetteres(guessdLetter:Char){
        var found=0
        //to check if the letter exist in the Phrase
        //add the letter if it exist in the dictionary (to ???????
        for (i in Phrase.indices){
            if (Phrase[i].equals(guessdLetter,true)){
                correctLetterArray.add(guessdLetter)
                secretPhraseDictionary[i]=guessdLetter.toUpperCase()
                found++
                point+=1
            }
        }//end for

        //update secretPhrase to be similar as secretPhraseDictionary
        //so that if a letter found in dictionary it will be updated
        for (i in secretPhraseDictionary){
            secretPhrase += secretPhraseDictionary[i.key]
        }//end for

        if (secretPhrase.equals(Phrase,true)){
            disabledEntry()
            updateString()
            highScore()
            AlertDialog(this,"You win!\n \n Play again?")
        }//end if

        //update userGuessLitter
        if (userGuessLitter.isEmpty()){
            userGuessLitter += guessdLetter
        }else {
            userGuessLitter += " , " +guessdLetter
        }

        //to print how many letter found in the phrase
        if (found>0){
            message.add("Found $found ${guessdLetter.toUpperCase()}")
        }else {
            message.add("No ${guessdLetter.toUpperCase()} is found")
        }
        GuessCountL()
        updateString()
        rv_phraseGame.scrollToPosition(message.size-1)//////////////////////////
    }//end checkLetteres()
    fun GuessCountL(){
        guessCountL++
        val guessLeft= 10-guessCountL
        if (guessCountL<10){
            message.add("$guessLeft letter guesses remaining")
        }
    }//end GuessCountL()

    fun GuessCountP(){
        guessCountP++
        val guessLeft= 10-guessCountP
        if (guessCountP<10){
            message.add("$guessLeft Phrase guesses remaining")
        }
    }//GuessCountP()
    fun addPointPhrase(){
        if (correctLetterArray.size <= 3){
            point+=15
        } else if (correctLetterArray.size <=6 ){
            point+=10
        }else if(correctLetterArray.size<=10){
            point +=5
        }else
            point+=2
    }
    fun highScore(){
        if (point>=highScore){
            highScore=point
            with(shared.edit()) {
                putInt("High Score", highScore)
                apply()}}
        tvScore.setText("High score: $highScore ")
        tvScoreC.text=("Current Score: $point")
        tvScoreC.isVisible=true
    }
    private fun disabledEntry(){
        butGuess.isEnabled = false
        butGuess.isClickable = false
        phraseField.isEnabled = false
        phraseField.isClickable = false
    }//disabledEntry()
    
}//end class