package banty.com.repository.local.files

import android.os.Parcel
import android.support.annotation.Nullable
import android.util.Log
import banty.com.datamodels.ContextProvider
import banty.com.datamodels.response.BitcoinApiResponseModel
import java.io.*

/**
 * Created by Banty on 07/01/19.
 *
 * Performs file input/output operation
 */
class FileManager {

    val logTag = "FileManager"

    /*
    * Saves the object in the file
    * */
    fun saveObjectToFile(filePath: String, dataToWrite: BitcoinApiResponseModel?) {
        try {
            val fout = FileOutputStream("${ContextProvider.getContext().filesDir}/$filePath")
            val oos = ObjectOutputStream(fout)
            oos.writeObject(dataToWrite)
            oos.close()
            Log.d(logTag, "file stored: $filePath")
        } catch (ex: IOException) {
            Log.d(logTag, "unable to store file: $filePath")
        }
    }

    /*
    * Returns the object from file.
    * returns Empty class if file is not present or could not read
    * */
    @Nullable
    fun getObjectFromFile(filePath: String): BitcoinApiResponseModel? {
        Log.d(logTag, "read object:  $filePath")
        var data: BitcoinApiResponseModel
        try {
            val fin = FileInputStream("${ContextProvider.getContext().filesDir}/$filePath")
            val ois = ObjectInputStream(fin)
            data = ois.readObject() as BitcoinApiResponseModel
            ois.close()
            Log.d(logTag, "object read from file successfully, file: $filePath")
        } catch (ex: Exception) {
            data = BitcoinApiResponseModel.createFromParcel(Parcel.obtain()) // empty
            ex.printStackTrace()
        }

        return data
    }

}