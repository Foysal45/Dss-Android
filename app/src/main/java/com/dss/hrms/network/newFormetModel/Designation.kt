import com.google.gson.annotations.SerializedName




data class Designation (

	@SerializedName("name") val name : String,
	@SerializedName("name_bn") val name_bn : String
)