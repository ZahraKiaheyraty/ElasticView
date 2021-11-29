package com.example.elasticview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.elasticview.util.ElasticAnimation
import java.util.ArrayList

class Activity2 : AppCompatActivity() {

  private val data = ArrayList<ListViewItem>()
  private var adapter: ListViewAdapter? = null
  private var listView: ListView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity2)

    adapter = ListViewAdapter(this, R.layout.item, data)
    listView = findViewById(R.id.example1_listView)
    listView!!.adapter = adapter
    listView!!.onItemClickListener = ListViewItemClickListener()
  }

  fun floatingButtons(v: View) {
    val listviewitem = ListViewItem(data.size.toString() + "")
    data.add(listviewitem)
    adapter!!.notifyDataSetChanged()
    listView!!.setSelection(data.size - 1)
  }

  private inner class ListViewItemClickListener : AdapterView.OnItemClickListener {
    override fun onItemClick(adapterView: AdapterView<*>, clickedView: View, pos: Int, id: Long) {
      ElasticAnimation(clickedView)
        .setScaleX(0.9f)
        .setScaleY(0.9f)
        .setDuration(400)
        .setOnFinishListener {
          // Do something after duration time
          Toast.makeText(baseContext, "ListViewItem$pos", Toast.LENGTH_SHORT).show()
        }
        .doAction()
    }
  }

  private inner class ListViewItem(val content: String)

  private inner class ListViewAdapter(
    context: Context,
    private val layout: Int,
    private val data: ArrayList<ListViewItem>
  ) : BaseAdapter() {

    private val inflater: LayoutInflater =
      context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
      return data.size
    }

    override fun getItem(position: Int): String {
      return data[position].content
    }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
      var view = convertView

      if (view == null) view = inflater.inflate(layout, parent, false)
      val listviewitem = data[position]

      val tv_title = view!!.findViewById<TextView>(R.id.item_tv_title)
      tv_title.text = "ListViewItem" + listviewitem.content

      val tv_content = view.findViewById<TextView>(R.id.item_tv_content)
      tv_content.text = "This is ListViewItem" + listviewitem.content + "'s content"

      return view
    }
  }
}
