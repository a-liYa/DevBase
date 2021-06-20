package com.aliya.base.sample.module.snap

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aliya.adapter.RecyclerAdapter
import com.aliya.adapter.RecyclerViewHolder
import com.aliya.adapter.divider.ListBuilder
import com.aliya.base.sample.R
import com.aliya.base.sample.base.ActionBarActivity
import com.aliya.base.sample.databinding.ActivityPagerSnapBinding
import com.aliya.base.sample.databinding.ItemTextCenterBinding
import com.aliya.base.sample.ktx.viewBindings

/**
 * PagerSnapHelper 定位顶部演示
 *
 * @author a_liYa
 * @date 2021/6/20 11:56.
 */
class PagerSnapActivity : ActionBarActivity() {

    private val viewBinding by viewBindings(ActivityPagerSnapBinding::bind)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager_snap)

        val array = Array(20) {
            it.toString()
        }
        viewBinding.recycler.adapter = Adapter(array.toList())
        viewBinding.recycler.addItemDecoration(
                ListBuilder(this).setColorRes(R.color.color_divider).setSpace(1f).build())
        object : PagerSnapHelper(GRAVITY_START) {

            override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager?, velocityX: Int, velocityY: Int): Int {
                val findTargetSnapPosition = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
                Log.e("TAG", "findTargetSnapPosition: $findTargetSnapPosition")
                return findTargetSnapPosition
            }


            override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
                val findSnapView = super.findSnapView(layoutManager)
                Log.e("TAG", "findSnapView: position = ${findSnapView?.tag}")
                return findSnapView
            }

            override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
                val calculateDistanceToFinalSnap = super.calculateDistanceToFinalSnap(layoutManager, targetView)
                Log.e("TAG", "calculateDistanceToFinalSnap: targetView position = ${targetView.tag}")
                return calculateDistanceToFinalSnap
            }

        }.attachToRecyclerView(viewBinding.recycler)

    }


    internal class Adapter(data: List<String>?) : RecyclerAdapter<String>(data) {
        override fun onAbsCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<*>? {
            return ViewHolder(parent)
        }

        internal class ViewHolder(parent: ViewGroup) : RecyclerViewHolder<String>(parent, R.layout.item_text_center) {
            private val viewBinding by viewBindings(ItemTextCenterBinding::bind)

            override fun bindData(data: String?) {
                viewBinding.root.tag = bindingAdapterPosition
                viewBinding.tvText.text = data
            }
        }
    }
}