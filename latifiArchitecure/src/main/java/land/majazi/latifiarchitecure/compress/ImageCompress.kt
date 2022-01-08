package land.majazi.latifiarchitecure.compress

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Size
import java.io.File
import java.io.FileOutputStream
import kotlin.math.roundToInt

class ImageCompress(private val file: File) {

    private fun getImageSize(): Size {
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath,option)
        return Size(option.outWidth, option.outHeight)
    }

    fun resize(): File {
        val desiredSize = 800
        val size = getImageSize()
        val resize = if (size.height > size.width) {
            if (size.width > desiredSize) {
                val temp = size.width.toDouble() / desiredSize.toDouble()
                Size(800, (size.height.toDouble() / temp).roundToInt())
            } else
                size
        } else {
            if (size.height > desiredSize) {
                val temp = size.height.toDouble() / desiredSize.toDouble()
                Size((size.width.toDouble() / temp).roundToInt(), 800)
            } else
                size
        }

        val option = BitmapFactory.Options()
        val bitmap = BitmapFactory.decodeFile(file.absolutePath,option)
        val resizeBitmap = Bitmap.createScaledBitmap(bitmap, resize.width, resize.height, false)
        val fos = FileOutputStream(file)
        resizeBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.close()
        return file
    }


}