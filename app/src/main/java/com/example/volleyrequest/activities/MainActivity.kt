package com.example.volleyrequest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.volleyrequest.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_go.setOnClickListener{
            val ingredients = ed_ingredients.text.toString().trim()
            val search = ed_search_ingredient.text.toString().trim()

            val intent = Intent(this, RecipeActivity::class.java)
            intent.putExtra("ingredients", ingredients)
            intent.putExtra("search", search)
            startActivity(intent)
        }

    }
}