package com.wiser.cropimageview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wiser.crop.CropImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        civ_photo?.loadCropHeightImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20190702%2F6f94490ba8894cab84e9fd1fc8263f61.jpeg&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621562686&t=15d38b463736e3ba327d5ca19538a6aa",300,300)
        civ_photo1?.loadCropHeightImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20190702%2F6f94490ba8894cab84e9fd1fc8263f61.jpeg&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621562686&t=15d38b463736e3ba327d5ca19538a6aa",300,300)
        civ_photo2?.loadCropHeightImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20190702%2F6f94490ba8894cab84e9fd1fc8263f61.jpeg&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621562686&t=15d38b463736e3ba327d5ca19538a6aa",300,300)
        civ_photo3?.loadCropWidthImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01aa1f5ca07935a801214168d59afb.jpg%401280w_1l_2o_100sh.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621565659&t=848540731797f07d4a7aec482ef152ac",300,300)
        civ_photo4?.loadCropWidthImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01aa1f5ca07935a801214168d59afb.jpg%401280w_1l_2o_100sh.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621565659&t=848540731797f07d4a7aec482ef152ac",300,300)
        civ_photo5?.loadCropWidthImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01aa1f5ca07935a801214168d59afb.jpg%401280w_1l_2o_100sh.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621565659&t=848540731797f07d4a7aec482ef152ac",300,300)
    }
}