import com.google.gson.annotations.SerializedName




data class Publications (

	@SerializedName("publication_type") val publication_type : Publication_type,
	@SerializedName("publication_name") val publication_name : String,
	@SerializedName("publication_name_bn") val publication_name_bn : String,
	@SerializedName("publication_details") val publication_details : String,
	@SerializedName("publication_details_bn") val publication_details_bn : String
)