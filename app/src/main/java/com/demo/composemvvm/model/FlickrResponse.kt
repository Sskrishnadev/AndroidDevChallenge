import com.demo.composemvvm.model.FlickrItem
import com.google.gson.annotations.SerializedName

data class FlickrResponse(
    @SerializedName("items")
    val results: List<FlickrItem>
)
