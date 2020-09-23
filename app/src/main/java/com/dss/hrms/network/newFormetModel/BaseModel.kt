import com.google.gson.annotations.SerializedName



data class BaseModel (

	@SerializedName("status") val status : String,
	@SerializedName("message") val message : String,
	@SerializedName("code") val code : Int,
	@SerializedName("data") val data : Data
)