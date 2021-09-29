package com.example.a2in1app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Main Activity")

        var butNumGame=findViewById<Button>(R.id.button3)
        var butPhraseGame=findViewById<Button>(R.id.button4)

        butNumGame.setOnClickListener(){
            goNumGame()
        }
        butPhraseGame.setOnClickListener(){
            goPhraseGame()
        }

    }

    //add our menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.mi_numGame -> {
                goNumGame()
                return true
            }
            R.id.mi_phraseGame -> {
                goPhraseGame()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun goNumGame(){
        intent=Intent(this, NumberGame::class.java)
        startActivity(intent)
    }
    fun goPhraseGame(){
        intent=Intent(this, PhraseGame::class.java)
        startActivity(intent)
    }

}