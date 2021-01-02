package top.zcwfeng.glide

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import top.zcwfeng.glide.databinding.ActivityGlideTestBinding

class GlideTest : AppCompatActivity() {
    lateinit var binding: ActivityGlideTestBinding
    val imgUrl = "https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_glide_test)
        binding.showImg.setOnClickListener {

            Glide.with(this)
                    .load(imgUrl)
                    .skipMemoryCache(false)
//                    .thumbnail()
                    .into(binding.contentImg)


        }
    }
}

val doc1 = """
    Glide 加载图片的方式
    // 加载本地图片
    File file = new File(getExternalCacheDir() + "/image.jpg");
    Glide.with(this).load(file).into(imageView);

    // 加载应用资源
    int resource = R.drawable.image;
    Glide.with(this).load(resource).into(imageView);

    // 加载二进制流
    byte[] image = getImageBytes();
    Glide.with(this).load(image).into(imageView);

    // 加载Uri对象
    Uri imageUri = getImageUri();
    Glide.with(this).load(imageUri).into(imageView);
"""

val doc2 = """
    Glide 占位图，和一些属性实例
    Glide.with(this)
     .load(url)
     .asBitmap()
     .placeholder(R.drawable.loading)
     .error(R.drawable.error)
     .diskCacheStrategy(DiskCacheStrategy.NONE)
     .into(imageView);
     
    Glide.with(this).load(url)
     .asGif()
     .placeholder(R.drawable.loading).error(R.drawable.error).diskCacheStrategy(DiskCacheStrategy.NONE)
     .into(imageView);
     
    Glide.with(this).load(url).placeholder(R.drawable.loading.error(R.drawable.error).diskCacheStrategy(DiskCacheStrategy.NONE)
     .override(100, 100)
     .into(imageView);
"""

