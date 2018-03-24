package id.bismillah.e_procumahku

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.bismillah.e_procumahku.extensions.stringResource
import kotlinx.android.synthetic.main.fragment_procurement.*

/**
 * Created by Subkhan Sarif on 21/03/2018.
 */
class ProcurementFragment : BaseFragment() {

    lateinit var adapter: ProcurementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_procurement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(context)
        adapter = ProcurementAdapter(context, ProcurementModel().generate())
        recycler_view.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showActionBar()
        changeTitle(context.stringResource(R.string.lbl_my_procurements))
    }
}

class ProcurementAdapter(var context: Context, var list: List<ProcurementModel>) : RecyclerView.Adapter<ProcurementAdapter.ViewHolder>() {

    var dataList: MutableList<ProcurementModel> = list.toMutableList()

    fun updateData(newData: List<ProcurementModel>) {
        dataList.clear()
        dataList.addAll(newData)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_procurement, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = dataList[position]
        holder?.title?.text = item.title
        holder?.description?.text = item.description
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var description: TextView = view.findViewById(R.id.description)
    }
}