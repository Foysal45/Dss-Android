import com.google.gson.annotations.SerializedName


data class Additional_qualifications (

	@SerializedName("qualification_name") val qualification_name : String?,
	@SerializedName("qualification_name_bn") val qualification_name_bn : String?,
	@SerializedName("qualification_details") val qualification_details : String?,
	@SerializedName("qualification_details_bn") val qualification_details_bn : String?
)