package com.example.takeeat.ui.myrecipe

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.*
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.example.takeeat.BuildConfig
import java.io.File

object S3UploadUtils {
    fun getS3ImageURL(uri: Uri, filePath: String?,  context: Context): String {

        var AccessKey:String = BuildConfig.ACCESS_KEY
        var SecretKey:String = BuildConfig.SECRET_KEY
        val file = File(filePath ?: uri.toFilePath(context.contentResolver))

        val awsCredentials = BasicAWSCredentials(AccessKey, SecretKey)
        val s3Client = AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2))


        val transferUtility = TransferUtility.builder().s3Client(s3Client).context(context).build()
        TransferNetworkLossHandler.getInstance(context)
        val uploadObserver: TransferObserver = transferUtility.upload("amplify-takeeat-dev-211354-deployment", file.name, file)
        uploadObserver.setTransferListener(object: TransferListener {
            override fun onStateChanged(id: Int, state: TransferState?) {
                if(state == TransferState.COMPLETED) {
                    // 업로드 완료
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                // 업로드 중 진행과정을 알 수 있다.
                Log.d("progress -- ", "${(bytesCurrent / bytesTotal) * 100}")
            }

            override fun onError(id: Int, ex: Exception?) {
                // Exception 발생
                Log.e("error to upload", "${ex?.message}")
            }
        })
        return "$URL_PREFIX${file.name}"
    }

    private fun Uri.toFilePath(contentResolver: ContentResolver): String? {
        var fullPath = ""
        var cursor = contentResolver.query(this, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            var documentId = cursor.getString(0)
            if (documentId == null) {
                for (i in 0 until cursor.columnCount) {
                    if (COLUMN.equals(cursor.getColumnName(i), true)) {
                        fullPath = cursor.getString(i)
                        break
                    }
                }
            } else {
                documentId = documentId.substring(documentId.lastIndexOf(":")+1)
                cursor.close()
                val projection = arrayOf(COLUMN)
                try {
                    cursor = contentResolver.query(
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Images.Media._ID + "=?",
                        arrayOf(documentId),
                        null
                    )
                    if(cursor != null) {
                        cursor.moveToFirst()
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN))
                    }
                } finally {
                    if (cursor != null) cursor.close()
                }
            }
        }
        return fullPath
    }
    
    private const val URL_PREFIX = "https://amplify-takeeat-dev-211354-deployment.s3.ap-northeast-2.amazonaws.com/"
    private const val COLUMN = "_data"
}

