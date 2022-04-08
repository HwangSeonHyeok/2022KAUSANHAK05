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


            val items = itemTestList[rowPos]

            holder.apply {
                txtItemName.text = items.itemName
                txtItemExp.text = items.itemExpiration.toString()+"일"
                txtItemQuan.text = items.itemQuantity.toString()+"개"

        }
    }

    override fun getItemCount(): Int {
        return itemTestList.size
    }


}