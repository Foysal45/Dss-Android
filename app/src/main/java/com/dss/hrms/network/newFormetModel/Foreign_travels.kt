import com.google.gson.annotations.SerializedName




data class Foreign_travels (

	@SerializedName("purpose") val purpose : String?,
	@SerializedName("purpose_bn") val purpose_bn : String?,
	@SerializedName("from_date") val from_date : String?,
	@SerializedName("to_date") val to_date : String?,
	@SerializedName("country") val country : Country
)