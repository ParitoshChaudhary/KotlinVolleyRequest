package com.example.volleyrequest.data

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.volleyrequest.R
import com.example.volleyrequest.models.Recipe
import com.squareup.picasso.Picasso

class RecipeListAdapter(private val list: ArrayList<Recipe>,
                val context: Context): RecyclerView.Adapter<RecipeListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recipe_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var title = itemView.findViewById<TextView>(R.id.txt_title)
        var thumbnail = itemView.findViewById<ImageView>(R.id.img_recipe)
        var ingredients = itemView.findViewById<TextView>(R.id.txt_ingredients)
        var linkButton = itemView.findViewById<ImageView>(R.id.img_link)

        fun bindView(recipe: Recipe) {
            title.text = recipe.title
            ingredients.text = recipe.ingredients

            if (!TextUtils.isEmpty(recipe.thumbnail)) {
                Picasso.get()
                    .load(recipe.thumbnail)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(thumbnail)
            }else {
                Picasso.get().load(R.mipmap.ic_launcher).into(thumbnail)
            }

            linkButton.setOnClickListener {

//                var intent = Intent(context, ShowLinkActivity::class.java)
//                intent.putExtra("link", recipe.link.toString())
//                context.startActivity(intent)
            }
        }
    }
}