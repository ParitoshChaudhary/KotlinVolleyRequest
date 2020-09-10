package com.example.volleyrequest.activities

import android.app.Application
import android.net.SSLCertificateSocketFactory
import android.net.SSLSessionCache
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.volleyrequest.R
import com.example.volleyrequest.data.RecipeListAdapter
import com.example.volleyrequest.models.PARENT_LINK
import com.example.volleyrequest.models.QUERY
import com.example.volleyrequest.models.Recipe
import kotlinx.android.synthetic.main.activity_recipe.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.Socket
import java.net.UnknownHostException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class RecipeActivity : AppCompatActivity() {

    private var volleyRequest: RequestQueue? = null
    private var recipeList: ArrayList<Recipe>? = null
    private var recipeAdapter: RecipeListAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val extras =intent.extras
        val ingredients = extras?.get("ingredients")
        val search = extras?.get("search")
        url = if (extras != null && ingredients != "" && search != ""){
            PARENT_LINK + ingredients + QUERY + search
        }else{
            "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3"
        }

        recipeList = ArrayList<Recipe>()
        volleyRequest = Volley.newRequestQueue(
            this)

        getRecipe(url!!)
    }

    private fun getRecipe(url: String){
        val recipeRequest = JsonObjectRequest(Request.Method.GET, url, { response: JSONObject ->
            try {
                val resultArray = response.getJSONArray("results")
                for (i in 0 until resultArray.length()) {
                    val recipeObj = resultArray.getJSONObject(i)

                    val title = recipeObj.getString("title")
                    val href = recipeObj.getString("href")
                    val thumbnail = recipeObj.getString("thumbnail")
                    val ingredients = recipeObj.getString("ingredients")
                    Log.e("Result =====>> ", title)

                    val recipe = Recipe()
                    recipe.title = title
                    recipe.link = href
                    recipe.thumbnail = thumbnail
                    recipe.ingredients = "Ingredients: $ingredients"
                    recipeList?.add(recipe)

                    recipeAdapter = recipeList?.let { RecipeListAdapter(it, this) }
                    layoutManager = LinearLayoutManager(this)

                    rcv_recipe.adapter = recipeAdapter
                    rcv_recipe.layoutManager = layoutManager

                }

                recipeAdapter?.notifyDataSetChanged()

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        },
            { error: VolleyError? ->
                try {
                    Log.d("Volley Error: ", error.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            })
        volleyRequest?.add(recipeRequest)
    }
}