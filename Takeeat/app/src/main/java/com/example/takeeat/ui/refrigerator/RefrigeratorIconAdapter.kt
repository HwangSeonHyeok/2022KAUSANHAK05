import android.content.Intent
import android.content.res.TypedArray
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.R
import com.example.takeeat.ui.refrigerator.AddRefrigeratorActivity
import com.example.takeeat.ui.refrigerator.RefItem
import com.example.takeeat.ui.refrigerator.RefItemDetailActivity
import com.example.takeeat.ui.refrigerator.RefrigeratorFragment
import java.util.*
import kotlin.time.Duration.Companion.milliseconds


class RefrigeratorIconAdapter(private val itemTestList: List<RefItem>) : RecyclerView.Adapter<RefrigeratorIconAdapter.IconViewHolder>() {

    //lateinit var categoryIconArray : TypedArray
    //lateinit var ingreTagArray: Array<String>
    inner class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val txtItemName: TextView = itemView.findViewById(R.id.refrigerator_item_name_icon)
        val imgItem: ImageView = itemView.findViewById(R.id.refrigerator_item_image_icon)
        val imgItemExpWarning: ImageView = itemView.findViewById(R.id.refrigerator_item_expiration_warning_icon)
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var date = calendar.get(Calendar.DAY_OF_MONTH)
        var categoryIconArray: TypedArray = itemView.context.resources.obtainTypedArray(R.array.IngreIconArray)
        var ingreTagArray: Array<String> = itemView.context.resources.getStringArray(R.array.RefrigeratorItemTagArray)
        override fun onClick(view: View?) {
            Log.d("Response","ItemTouch")
            val intent = Intent(view!!.context, RefItemDetailActivity::class.java)
            var itemData = itemTestList[this.absoluteAdapterPosition]
            intent.putExtra("Item_Data",itemData)
            view.context.startActivity(intent)
        }
        init{
            /*categoryIconArray =
                itemView.context.resources.obtainTypedArray(R.array.IngreIconArray)
            ingreTagArray =itemView.context.resources.getStringArray(R.array.RefrigeratorItemTagArray)*/

            itemView.setOnClickListener {onClick(itemView)}
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_refrig_icon, parent, false)
        val iconViewHolder = IconViewHolder(itemView)
        return iconViewHolder
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val rowPos = holder.absoluteAdapterPosition
        val items = itemTestList[rowPos]

        holder.apply {
            if(items.itemname?.length!! > 5) {
                txtItemName.text = items.itemname?.substring(0, items.itemname!!.length.coerceAtMost(4)) + "…"
            }
            else
            {
                txtItemName.text = items.itemname
            }
            if(items.itemexp!=null) {
                var diffSec = (items.itemexp!!.time.minus(Date(year, month, date).time))
                var diffDate = diffSec / (24 * 60 * 60 * 1000)
                if(diffDate > 3) imgItemExpWarning.setVisibility(View.GONE)
            }
            if(items.itemtag!=null)
                imgItem.setImageDrawable(categoryIconArray.getDrawable(ingreTagArray.indexOf(items.itemtag)))
        }
    }

    override fun getItemCount(): Int {
        return itemTestList.size
    }


}