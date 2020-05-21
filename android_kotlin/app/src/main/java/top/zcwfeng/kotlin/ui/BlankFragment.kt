package top.zcwfeng.kotlin.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.arraySetOf
import androidx.databinding.ObservableField

import top.zcwfeng.kotlin.R
import top.zcwfeng.kotlin.bean.MyName
import top.zcwfeng.kotlin.databinding.BlankFragmentBinding
import java.util.*

class BlankFragment : Fragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }

    private lateinit var viewModel: BlankViewModel
    var myName: MyName = MyName(ObservableField("Alice"))

//    TODO: databinding 必须写一个检测初始化的函数 不起作用
    lateinit var databinding: BlankFragmentBinding//null 安全检测
    fun isDatabindingInitial(function: () -> Unit) = ::databinding.isInitialized

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        isDatabindingInitial() {
            databinding?.myName = myName
            databinding?.btnKotlin.setOnClickListener {
                rollDice(it)
            }
        }
        return inflater.inflate(R.layout.blank_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BlankViewModel::class.java)
        // TODO: Use the ViewModel
        Log.d("zcw:::", "BlankFragment")
        val combinedArraySet = arraySetOf(1, 2, 3) + arraySetOf(4, 5, 6)
        val newArraySet = combinedArraySet + 7 + 8
        Log.e("zcw:::", combinedArraySet.toString())
        Log.e("zcw:::", newArraySet.toString())



    }

    override fun onAttach(context: Context) {
        super.onAttach(context)


    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        isDatabindingInitial {
            databinding.btnKotlin.setOnClickListener(null)
        }
    }

    private fun rollDice(view: View) {
        var RandomInt = Random().nextInt(6) + 1

        var drawableResource = when (RandomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6

        }

        isDatabindingInitial {
            databinding.apply {

                databinding.textName.setText("Finshy")
                databinding.diceImage.setImageResource(drawableResource)
                invalidateAll()
            }
        }
    }


}
