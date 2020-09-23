import com.google.gson.annotations.SerializedName




data class Spouses (

	@SerializedName("name") val name : String,
	@SerializedName("name_bn") val name_bn : String,
	@SerializedName("email_address") val email_address : String,
	@SerializedName("occupation") val occupation : String,
	@SerializedName("occupation_bn") val occupation_bn : String,
	@SerializedName("phone_no") val phone_no : Int,
	@SerializedName("phone_no_bn") val phone_no_bn : String,
	@SerializedName("mobile_no") val mobile_no : Int,
	@SerializedName("mobile_no_bn") val mobile_no_bn : String,
	@SerializedName("distric") val distric : Distric,
	@SerializedName("religion") val religion : String
)