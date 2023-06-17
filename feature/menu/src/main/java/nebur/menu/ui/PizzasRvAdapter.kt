package nebur.menu.ui

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import nebur.menu.R
import nebur.menu.databinding.RvPizzasBinding
import nebur.menu.model.DishesModel

class PizzasRvAdapter(

): ListAdapter<DishesModel, PizzasRvAdapter.ViewHolder>(Comparator()) {

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val b = RvPizzasBinding.bind(v)
    }

    class Comparator : DiffUtil.ItemCallback<DishesModel>() {
        override fun areItemsTheSame(oldItem: DishesModel, newItem: DishesModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DishesModel, newItem: DishesModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_pizzas, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(h: ViewHolder, position: Int) {
        val model = getItem(position)

        h.b.txt1.text = model.name
        h.b.txt2.text = model.description
        h.b.btnAction.text = "от ${model.price} р"
        Picasso.get().load(model.image_url).error(nebur.common.R.color.white).fit().centerCrop(
            Gravity.RIGHT
        ).into(h.b.img)

    }
}















