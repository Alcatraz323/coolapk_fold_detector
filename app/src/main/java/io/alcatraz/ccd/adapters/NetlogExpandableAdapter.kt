package io.alcatraz.ccd.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import io.alcatraz.ccd.beans.NetworkElement
import io.alcatraz.ccd.databinding.DebugNetlogChildItemBinding
import io.alcatraz.ccd.databinding.DebugNetlogParentItemBinding

class NetlogExpandableAdapter(
    ctx: Context,
    var data: List<NetworkElement>
) : BaseExpandableListAdapter() {
    private var lf: LayoutInflater =
        ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getGroupCount(): Int {
        return data.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getGroup(groupPosition: Int): Any {
        return data[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return combineChild(data[groupPosition])
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    @SuppressLint("SetTextI18n")
    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val currentView = DebugNetlogParentItemBinding.inflate(lf)
        val element = data[groupPosition]
        currentView.netlogUrl.text = "Url:[" + element.url + "]"
        currentView.netlogMethod.text = combineParentMethod(element)
        return currentView.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val currentView = DebugNetlogChildItemBinding.inflate(lf)
        currentView.netlogDefined.text = combineChild(data[groupPosition])
        return currentView.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    fun setNewData(data: List<NetworkElement>) {
        this.data = data
        notifyDataSetChanged()
    }

    private fun combineParentMethod(element: NetworkElement): String {
        return element.method + " " + element.timeStamp
    }

    private fun combineChild(element: NetworkElement): String {
        var out = ""
        if (element.isResponse) {
            out += "Time used:[${element.time}ms]\n\n"
        }
        out += "Headers:\n${element.headers.toString()}\n\n"
        out += element.body
        return out
    }
}