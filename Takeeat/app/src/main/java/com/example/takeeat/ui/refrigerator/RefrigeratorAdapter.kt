import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.R
import com.example.takeeat.ui.refrigerator.AddRefrigeratorActivity
import com.example.takeeat.ui.refrigerator.RefItem
import com.example.takeeat.ui.refrigerator.RefItemDetailActivity
import com.example.takeeat.ui.refrigerator.RefrigeratorFragment
import java.util.*
import kotlin.time.Duration.Companion.milliseconds


class RefrigeratorAdapter(private val itemTestList: List<RefItem>) : RecyclerView.Adapter<RefrigeratorAdapter.RowViewHolder>() {

    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val txtItemName: TextView = itemView.findViewById(R.id.refrigerator_item_name)
        val txtItemExp: TextView = itemView.findViewById(R.id.refrigerator_item_expiration)
        val txtItemQuan: TextView = itemView.findViewById(R.id.refrigerator_item_quantity)
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var date = calendar.get(Calendar.DAY_OF_MONTH)
        override fun onClick(view: View?) {
            Log.d("Response","ItemTouch")
            val intent = Intent(view!!.context, RefItemDetailActivity::class.java)
            var itemData = itemTestList[this.absoluteAdapterPosition]
            intent.putExtra("Item_Data",itemData)
            view.context.startActivity(intent)
        }
        init{
            itemView.setOnClickListener {onClick(itemView)}
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_refrig, parent, false)
        val rowViewHolder = RowViewHolder(itemView)
        //rowViewHolder.onClick()
        return rowViewHolder
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowPos = holder.absoluteAdapterPosition
        val items = itemTestList[rowPos]

        holder.apply {
            txtItemName.text = items.itemname
            if(items.itemexp!=null) {
                var diffSec = (items.itemexp!!.time.minus(Date(year, month + 1, date).time))
                var diffDate = diffSec / (24 * 60 * 60 * 1000)
                txtItemExp.text = diffDate.toString() + "일"
            }
            txtItemQuan.text = items.itemamount.toString()+items.itemunit

        }
    }

    override fun getItemCount(): Int {
        return itemTestList.size
    }


}