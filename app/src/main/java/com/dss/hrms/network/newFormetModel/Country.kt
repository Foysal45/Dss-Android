import com.google.gson.annotations.SerializedName


data class Country (
	@SerializedName("name") val name : String?,
	@SerializedName("name_bn") val name_bn : String?
)