package top.zcwfeng.customui.baseui.view.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import kotlinx.android.synthetic.main.activity_view_pager.*
import top.zcwfeng.customui.R
import top.zcwfeng.customui.baseui.view.SimpleColorChangeView2
import top.zcwfeng.customui.baseui.view.TAG
import top.zcwfeng.customui.baseui.view.fragment.TagFragment

class ViewPagerActivity : AppCompatActivity() {
    private val mTitles = arrayOf<String>("关注", "热点", "推荐", "长沙")
    private val mTabFragments = arrayOfNulls<TagFragment>(mTitles.size)
    private val mTabs = mutableListOf<SimpleColorChangeView2>()
    private var mAdapter:FragmentPagerAdapter? = null
    private var mViewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        Log.e(TAG, "onCreate: ");
        initViews()
        initDatas()
        initEvents()


    }


    private fun initEvents() {

        mViewPager?.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageSelected(position: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {
                if (positionOffset > 0) {
                    val left: SimpleColorChangeView2 = mTabs[position]
                    val right: SimpleColorChangeView2 = mTabs[position + 1]
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        left.direction = (SimpleColorChangeView2.DIRECTION_RIGHT)
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        right.direction = (SimpleColorChangeView2.DIRECTION_LEFT)
                    }
                    Log.v(TAG, positionOffset.toString() + "")
                    left.progress = (1 - positionOffset)
                    right.progress = (positionOffset)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

    }
    private fun initDatas() {
        for (i in 0 until mTabs.size) mTabFragments[i] = TagFragment.newInstance(this.mTitles[i])

        mAdapter = object : FragmentPagerAdapter(supportFragmentManager,0) {
            override fun getCount(): Int {
                return mTitles.size
            }

            override fun getItem(position: Int): Fragment {
                return mTabFragments[position]!!
            }
        }

        mViewPager?.adapter = mAdapter
        mViewPager?.currentItem = 0
    }

    private fun initViews() {
        mViewPager = id_viewpager
        mTabs.add(id_tab_01)
        mTabs.add(id_tab_02)
        mTabs.add(id_tab_03)
        mTabs.add(id_tab_04)
    }

    init {
        Log.e(TAG, "init: ");

    }


}