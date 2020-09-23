import com.google.gson.annotations.SerializedName


data class Childs (

	@SerializedName("name_of_children") val name_of_children : String?,
	@SerializedName("name_of_children_bn") val name_of_children_bn : String?,
	@SerializedName("date_of_birth") val date_of_birth : String?,
	@SerializedName("gender") val gender : Gender
)