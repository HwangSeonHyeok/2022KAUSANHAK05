package com.example.takeeat.ui.myrecipe

import android.content.Context
import android.net.Uri
import com.example.takeeat.ui.myrecipe.S3UploadUtils.getS3ImageURL
import com.example.takeeat.ui.myrecipe.data.DetailRecipe

class Order (var order: Int, var text:String, var image: Uri?, var imageFilePath: String?) {
    var uri: Uri? = null
    fun toDetailRecipe(context: Context): DetailRecipe {
        return DetailRecipe(text, if (image != null) getS3ImageURL(image!!, imageFilePath, context) else null)
    }
}

fun Order.setOrderText(orderText: String) {
    this.text = orderText
}



