import com.google.gson.annotations.SerializedName



data class References (

	@SerializedName("name") val name : String,
	@SerializedName("name_bn") val name_bn : String,
	@SerializedName("relation") val relation : String,
	@SerializedName("relation_bn") val relation_bn : String,
	@SerializedName("contact_no") val contact_no : Int,
	@SerializedName("contact_no_bn") val contact_no_bn : String,
	@SerializedName("address") val address : String,
	@SerializedName("address_bn") val address_bn : String
)