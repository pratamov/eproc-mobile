package id.bismillah.e_procumahku

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.bismillah.e_procumahku.core.database.ProcurementDbProvider
import id.bismillah.e_procumahku.core.database.UserDbProvider
import id.bismillah.e_procumahku.extensions.stringResource
import id.bismillah.e_procumahku.model.Proposal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.*

/**
 * Created by Subkhan Sarif on 21/03/2018.
 */
class DashboardFragment : BaseFragment() {

    lateinit var adapter: DashboardAdapter
    var listProposal: MutableList<Proposal> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listProposal.add(Proposal("1", "Proposal Pembangunan Rumah Ibu Turaih", "Murah", "1", "1", "http://proposal",50000000, 25))
        adapter = DashboardAdapter(context, listProposal, {
            val intent = Intent(context, ProposalDetailActivity::class.java)
            intent.putExtra(EXTRA_BUNDLE_DATA, Gson().toJson(it))
            context.startActivity(intent)
        })
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.onDestroy()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showActionBar()
        changeTitle(context.stringResource(R.string.lbl_dashboard))
    }

    companion object {
        const val EXTRA_BUNDLE_DATA = "data"
    }
}

class DashboardAdapter(var context: Context, var list: List<Proposal>, var onItemClick: (Proposal) -> Unit = {}) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    val disposable: CompositeDisposable = CompositeDisposable()
    var dataList: MutableList<Proposal> = list.toMutableList()
    val procurementDb = ProcurementDbProvider(context)
    val userDb = UserDbProvider(context)

    fun updateData(newData: List<Proposal>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dashboard, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = dataList[position]

        holder?.title?.text = item.title
        holder?.description?.text = item.description

        procurementDb.getById(item.procurement)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    holder?.procurement?.text = it.title
                }.also { disposable.add(it) }

        userDb.getById(item.contractor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val rating = it.rating ?: 0.0
                    val pp = it.ppURL

                    holder?.contractor?.text = it.instansi ?: it.username
                    holder?.starProg?.let {
                        val offset = holder.stars.layoutParams.width / 5
                        it.layoutParams.width = (holder.stars.layoutParams.width - rating * offset).toInt()
                    }
                    holder?.imgProposal?.let {
                        Picasso.get()
                                .load(pp)
                                .into(it)
                    }
                }.also { disposable.add(it) }

        holder?.container?.setOnClickListener { onItemClick(item) }
    }

    fun onDestroy() {
        disposable.clear()
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var container: ConstraintLayout = view.findViewById(R.id.container)
        var title: TextView = view.findViewById(R.id.txt_title)
        var description: TextView = view.findViewById(R.id.txt_description)
        var procurement: TextView = view.findViewById(R.id.txt_procurement)
        var contractor: TextView = view.findViewById(R.id.txt_contractor)
        var imgProposal: ImageView = view.findViewById(R.id.img_proposal)
        var stars: ImageView = view.findViewById(R.id.stars)
        var starProg: View = view.findViewById(R.id.stars_prog)
    }
}
