import com.google.gson.annotations.SerializedName




data class Publication_type (

	@SerializedName("name") val name : String,
	@SerializedName("name_bn") val name_bn : String
)