import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.R
import com.example.takeeat.ui.refrigerator.RefrigeratorFragment


class RefrigeratorAdapter(private val itemTestList: List<RefrigeratorFragment.RefrigeratorItemModel>) : RecyclerView.Adapter<RefrigeratorAdapter.RowViewHolder>() {

    class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtItemName: TextView = itemView.findViewById(R.id.refrigerator_item_name)
        val txtItemExp: TextView = itemView.findViewById(R.id.refrigerator_item_expiration)
        val txtItemQuan: TextView = itemView.findViewById(R.id.refrigerator_item_quantity)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_refrig, parent, false)
        return RowViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowPos = holder.absoluteAdapterPosition

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            holder.apply{
                txtItemName.text = "이름"
                txtItemExp.text = "유통기한"
                txtItemQuan.text = "갯수"
            }

        } else {
            val items = itemTestList[rowPos - 1]

            holder.apply {
                txtItemName.text = items.itemName
                txtItemExp.text = items.itemExpiration.toString()+"일"
                txtItemQuan.text = items.itemQuantity.toString()+"개"
            }
        }
    }

    override fun getItemCount(): Int {
        return itemTestList.size + 1 // one more to add header row
    }


}