package com.onethefull.dasomtutorial.adapter


import android.graphics.drawable.StateListDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.onethefull.dasomtutorial.R
import com.onethefull.dasomtutorial.databinding.ItemOptionsLayoutBinding

/**
 * Created by sjw on 2021/11/10
 */
class OptionsAdapter : RecyclerView.Adapter<OptionsAdapter.OptionsViewHolder>() {
    var choiceList: MutableList<String>? = null

    fun setChoiceist(mutableList: MutableList<String>?) {
        this.choiceList = mutableList
        selectedOption = -1;
        notifyDataSetChanged()
    }

    var selectedOption: Int = -1
    var flashCheckBoxes: Boolean = false

    fun setNoItemSelected() {
        flashCheckBoxes = true
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {

        holder.checkBoxView.text = choiceList?.get(position)
        holder.checkBoxView.buttonDrawable = StateListDrawable()
        if (flashCheckBoxes) {
            flashCheckBoxes = false
        }

        if (position == selectedOption) {
            holder.checkBoxView.isChecked = true
//            holder.checkBoxView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.left_arrow_options, 0)
            holder.parentLayout.setBackgroundColor(
                ContextCompat.getColor(holder.checkBoxView.context, R.color.colorOptionsSurface)
            )
        } else {
            holder.checkBoxView.isChecked = false
//            holder.checkBoxView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            holder.parentLayout.setBackgroundColor(
                ContextCompat.getColor(holder.checkBoxView.context, R.color.colorOptionsSurface)
            )
        }
    }


    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val bindng: ItemOptionsLayoutBinding = ItemOptionsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionsViewHolder(bindng)
    }


    var onItemClick: ((String) -> Unit)? = null

    inner class OptionsViewHolder(bindng: ItemOptionsLayoutBinding) : RecyclerView.ViewHolder(bindng.root) {
        val checkBoxView: CheckBox = bindng.choiceChip
        val parentLayout: LinearLayout = bindng.parentLayout

        init {
            checkBoxView.setOnClickListener {
                selectedOption = adapterPosition
                choiceList?.get(adapterPosition)?.let { it1 -> onItemClick?.invoke(it1) };
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return choiceList?.size ?: 0
    }


    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int, viewId: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType, it.id)
        }
        return this
    }
}

